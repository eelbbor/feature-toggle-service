package com.featuretoggle.db.dao;

import com.featuretoggle.domain.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AccountDAO extends AbstractDAO<Account> {
    public static final String TABLE_NAME = "account";
    public static final String[] FIELDS = {"id", "name"};

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Account processResultSetRow(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        String name = resultSet.getString("name");
        return new Account(id, name);
    }

    public Account findById(UUID id) throws SQLException {
        String[] fields = {"id"};
        List<Object> values = new ArrayList<Object>(Arrays.asList(id));
        Set<Account> result = equalFetchAllQuery(fields, values, true);
        return result.size() > 0 ? (Account) result.toArray()[0] : null;
    }

    public UUID insert(String name) throws SQLException {
        UUID accountId = UUID.randomUUID();
        List<Object> values = new ArrayList<>();
        values.add(accountId);
        values.add(name);
        return insert(FIELDS, values) == 1 ? accountId : null;
    }

    public Account updateAccountName(UUID id, String newName) throws SQLException {
        String[] updateFields = {"name"};
        List<Object> updateValues = new ArrayList<Object>(Arrays.asList(newName));
        String[] queryFields = {"id"};
        List<Object> queryValues = new ArrayList<Object>(Arrays.asList(id));
        int updateCount = update(updateFields, updateValues, queryFields, queryValues, true);
        if(updateCount == 1) {
            return findById(id);
        }
        return null;
    }
}
