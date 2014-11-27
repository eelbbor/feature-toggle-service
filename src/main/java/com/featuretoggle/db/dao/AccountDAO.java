package com.featuretoggle.db.dao;

import java.sql.SQLException;
import java.util.UUID;

public class AccountDAO extends AbstractDAO {
    public static final String TABLE_NAME = "account";
    public static final String[] FIELDS = {"id", "name"};

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public void insert(String name) throws SQLException {
        UUID accountId = UUID.randomUUID();
        Object[] values = {accountId, name};
        insert(FIELDS, values);
    }
}
