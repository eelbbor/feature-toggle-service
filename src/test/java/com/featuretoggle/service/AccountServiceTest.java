package com.featuretoggle.service;

import com.featuretoggle.db.dao.AccountDAO;
import com.featuretoggle.domain.Account;
import com.featuretoggle.service.exception.ServiceException;
import com.featuretoggle.service.exception.ServiceInvalidInputException;
import com.featuretoggle.service.exception.ServicePersistenceException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static com.featuretoggle.service.AccountService.ACCOUNT_NAME_INPUT_ERROR_MSG;
import static com.featuretoggle.service.AccountService.ID_INPUT_ERROR_MESSAGE;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class AccountServiceTest {
    private AccountService service;
    private AccountDAO dao;

    @BeforeMethod
    protected void setUp() {
        dao = mock(AccountDAO.class);
        service = new AccountService(dao);
    }

    public void shouldThrowInvalidInputExceptionForNullNameCreateAccount() throws ServicePersistenceException {
        try {
            service.createAccount(null);
            fail("should have thrown input exception for null name");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }

    public void shouldThrowInvalidInputExceptionForEmptyNameCreateAccount() throws ServicePersistenceException {
        try {
            service.createAccount("");
            fail("should have thrown input exception for empty name");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }

    public void shouldThrowInvalidInputExceptionForAccountNotCreatedWithoutSqlException() throws Exception {
        String name = randomUUID().toString();
        when(dao.insert(name)).thenReturn(null);
        try {
            service.createAccount(name);
            fail("should have thrown input exception when not created");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, "Unable to create account with input of: name = " + name);
        }
    }

    public void shouldTranslateSqlExceptionOnCreate() throws Exception {
        String name = randomUUID().toString();
        SQLException sqlException = new SQLException("matters not");
        when(dao.insert(name)).thenThrow(sqlException);
        try {
            service.createAccount(name);
            fail("should have thrown persistence exception when not created");
        } catch (ServicePersistenceException e) {
            validateErrorMessages(e, "Unable to create account with input of: name = " + name);
            assertEquals(e.getCause(), sqlException);
        }
    }

    public void shouldCreateAccount() throws Exception {
        Account expected = new Account(randomUUID(), randomUUID().toString());
        when(dao.insert(expected.getName())).thenReturn(expected.getId());
        when(dao.findById(expected.getId())).thenReturn(expected);
        Account actual = service.createAccount(expected.getName());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
    }

    public void shouldThrowInvalidInputExceptionForNullIdUpdateAccount() throws ServicePersistenceException {
        try {
            service.updateAccount(null, "randomname");
            fail("should have thrown input exception for null id");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ID_INPUT_ERROR_MESSAGE);
        }
    }

    public void shouldThrowInvalidInputExceptionForNullNameUpdateAccount() throws ServicePersistenceException {
        try {
            service.updateAccount(randomUUID(), null);
            fail("should have thrown input exception for null name");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }

    public void shouldThrowInvalidInputExceptionForEmptyNameUpdateAccount() throws ServicePersistenceException {
        try {
            service.updateAccount(randomUUID(), null);
            fail("should have thrown input exception for empty name");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }

    public void shouldThrowInvalidInputExceptionForBothNullNameAndNullIdUpdateAccount() throws ServicePersistenceException {
        try {
            service.updateAccount(null, null);
            fail("should have thrown input exception for null name and null id");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ID_INPUT_ERROR_MESSAGE, ACCOUNT_NAME_INPUT_ERROR_MSG);
        }
    }

    public void shouldThrowInvalidInputExceptionForAccountNotUpdatedWithoutSqlException() throws Exception {
        UUID id = randomUUID();
        String name = randomUUID().toString();
        when(dao.updateAccountName(id, name)).thenReturn(null);
        try {
            service.updateAccount(id, name);
            fail("should have thrown input exception when not updated");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, "Unable to update account with input of: id = " + id + " name = " + name);
        }
    }

    public void shouldTranslateSqlExceptionOnUpdate() throws Exception {
        UUID id = randomUUID();
        String name = randomUUID().toString();
        SQLException sqlException = new SQLException("matters not");
        when(dao.updateAccountName(id, name)).thenThrow(sqlException);
        try {
            service.updateAccount(id, name);
            fail("should have thrown persistence exception when not updated");
        } catch (ServicePersistenceException e) {
            validateErrorMessages(e, "Unable to update account with input of: id = " + id + " name = " + name);
            assertEquals(e.getCause(), sqlException);
        }
    }

    public void shouldUpdateAccount() throws Exception {
        Account expected = new Account(randomUUID(), randomUUID().toString());
        when(dao.updateAccountName(expected.getId(), expected.getName())).thenReturn(expected);
        Account actual = service.updateAccount(expected.getId(), expected.getName());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
    }

    public void shouldThrowInvalidInputExceptionForNullIdDeleteAccount() throws ServicePersistenceException {
        try {
            service.deleteAccount(null);
            fail("should have thrown input exception for null id");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ID_INPUT_ERROR_MESSAGE);
        }
    }

    public void shouldThrowInvalidInputExceptionForAccountNotDeletedWithoutSqlException() throws Exception {
        UUID id = randomUUID();
        when(dao.delete(id)).thenReturn(0);
        try {
            service.deleteAccount(id);
            fail("should have thrown input exception when not deleted");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, "Unable to delete account with input of: id = " + id);
        }
    }

    public void shouldTranslateSqlExceptionOnDelete() throws Exception {
        UUID id = randomUUID();
        SQLException sqlException = new SQLException("matters not");
        when(dao.delete(id)).thenThrow(sqlException);
        try {
            service.deleteAccount(id);
            fail("should have thrown persistence exception when not updated");
        } catch (ServicePersistenceException e) {
            validateErrorMessages(e, "Unable to delete account with input of: id = " + id);
            assertEquals(e.getCause(), sqlException);
        }
    }

    public void shouldDeleteAccount() throws Exception {
        UUID id = randomUUID();
        when(dao.delete(id)).thenReturn(1);
        assertEquals(service.deleteAccount(id), 1);
    }

    public void shouldThrowInvalidInputExceptionForNullIdQueryAccount() throws ServicePersistenceException {
        try {
            service.queryAccount(null);
            fail("should have thrown input exception for null id");
        } catch (ServiceInvalidInputException e) {
            validateErrorMessages(e, ID_INPUT_ERROR_MESSAGE);
        }
    }

    public void shouldTranslateSqlExceptionOnQuery() throws Exception {
        UUID id = randomUUID();
        SQLException sqlException = new SQLException("matters not");
        when(dao.findById(id)).thenThrow(sqlException);
        try {
            service.queryAccount(id);
            fail("should have thrown persistence exception when not able to query");
        } catch (ServicePersistenceException e) {
            validateErrorMessages(e, "Unable to query account with input of: id = " + id);
            assertEquals(e.getCause(), sqlException);
        }
    }

    public void shouldQueryAccount() throws Exception {
        Account expected = new Account(randomUUID(), randomUUID().toString());
        when(dao.findById(expected.getId())).thenReturn(expected);
        Account actual = service.queryAccount(expected.getId());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
    }

    private void validateErrorMessages(ServiceException e, String... errors) {
        Set<String> errorMessages = e.getErrorMessages();
        assertEquals(errorMessages.size(), errors.length);
        assertTrue(errorMessages.containsAll(Arrays.asList(errors)));
    }
}