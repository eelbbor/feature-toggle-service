package com.featuretoggle.service;

import com.featuretoggle.db.dao.AccountDAO;
import com.featuretoggle.domain.Account;
import com.featuretoggle.service.exception.ServiceInvalidInputException;
import com.featuretoggle.service.exception.ServicePersistenceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountService {
    public static final String ACCOUNT_NAME_INPUT_ERROR_MSG = "Account Name cannot be null nor empty";
    public static final String ID_INPUT_ERROR_MESSAGE = "ID cannot be null";

    private AccountDAO dao;

    public AccountService() {
        this(null);
    }

    protected AccountService(AccountDAO dao) {
        this.dao = dao == null ? new AccountDAO() : dao;
    }

    public Account createAccount(String name) throws ServiceInvalidInputException, ServicePersistenceException {
        List<String> errors = new ArrayList<>();
        validateAccountName(errors, name);
        if (errors.size() > 0) {
            throw new ServiceInvalidInputException("Invalid input for Account creation.", errors);
        }
        UUID accountId = null;
        String unableToCreateErrorMsg = "Unable to create account with input of: name = " + name;
        try {
            accountId = dao.insert(name);
            if (accountId == null) {
                errors.add(unableToCreateErrorMsg);
                throw new ServiceInvalidInputException("Unable to create Account.", errors);
            }
        } catch (SQLException e) {
            errors.add(unableToCreateErrorMsg);
            throw new ServicePersistenceException("Unable to create Account.", e, errors);
        }
        return queryAccount(accountId);
    }

    public Account queryAccount(UUID id) throws ServiceInvalidInputException, ServicePersistenceException {
        List<String> errors = new ArrayList<>();
        validateAccountId(errors, id);
        if (errors.size() > 0) {
            throw new ServiceInvalidInputException("Invalid input for Account query.", errors);
        }

        Account account = null;
        try {
            //Should be looking for cache hit first and if no luck then hit the DB
            account = dao.findById(id);
        } catch (SQLException e) {
            errors.add("Unable to query account with input of: id = " + id);
            throw new ServicePersistenceException("Unable to query Account", e, errors);
        }
        return account;
    }

    public Account updateAccount(UUID id, String newName) throws ServiceInvalidInputException, ServicePersistenceException {
        List<String> errors = new ArrayList<>();
        validateAccountInput(errors, id, newName);
        if (errors.size() > 0) {
            throw new ServiceInvalidInputException("Invalid input for Account update.", errors);
        }

        Account account = null;
        String unableToUpdateErrorMessage = "Unable to update account with input of: id = " + id + " name = " + newName;
        try {
            account = dao.updateAccountName(id, newName);
            if (account == null) {
                errors.add(unableToUpdateErrorMessage);
                throw new ServiceInvalidInputException("Unable to update Account.", errors);
            }
            //Object change messages and cache updates need to live here potentially
        } catch (SQLException e) {
            errors.add(unableToUpdateErrorMessage);
            throw new ServicePersistenceException("Unable to update Account.", e, errors);
        }
        return account;
    }

    public int deleteAccount(UUID id) throws ServiceInvalidInputException, ServicePersistenceException {
        List<String> errors = new ArrayList<>();
        validateAccountId(errors, id);
        if (errors.size() > 0) {
            throw new ServiceInvalidInputException("Invalid input for Account deletion.", errors);
        }

        int deleteCount = 0;
        String unableToDeletedErrorMessage = "Unable to delete account with input of: id = " + id;
        try {
            deleteCount = dao.delete(id);
            if (deleteCount < 1) {
                errors.add(unableToDeletedErrorMessage);
                throw new ServiceInvalidInputException("Unable to delete Account.", errors);
            }
        } catch (SQLException e) {
            errors.add(unableToDeletedErrorMessage);
            throw new ServicePersistenceException("Unable to delete Account.", e, errors);
        }
        return deleteCount;
    }

    private void validateAccountInput(List<String> errors, UUID id, String name) {
        validateAccountId(errors, id);
        validateAccountName(errors, name);
    }

    private void validateAccountId(List<String> errors, UUID id) {
        if(id == null) {
            errors.add(ID_INPUT_ERROR_MESSAGE);
        }
    }

    private void validateAccountName(List<String> errors, String name) {
        if (name == null || name.isEmpty()) {
            errors.add(ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }
}
