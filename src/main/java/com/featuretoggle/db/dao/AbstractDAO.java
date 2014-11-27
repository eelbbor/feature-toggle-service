package com.featuretoggle.db.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAO {
    public static final String JDBC_DATASOURCE = "jdbc/datasource";
    public static final String INSERT_STATEMENT = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String SELECT_STATEMENT = "SELECT * FROM %s WHERE %s=?";

    private Connection connection;

    abstract public String getTableName();

    protected void insert(String[] fields, Object[] values) throws SQLException {
        if (fields == null || values == null || fields.length != values.length) {
            throw new IllegalArgumentException("The fields and values must be of equal length and not null");
        }
        StringBuilder fieldList = new StringBuilder();
        StringBuilder valuesList = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (fieldList.length() > 0) {
                fieldList.append(" , ");
                valuesList.append(" ,");
            }
            fieldList.append(fields[i]);
            valuesList.append("?");
        }
        String insert = String.format(INSERT_STATEMENT, getTableName(), fieldList.toString(), valuesList.toString());

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(insert);
            for (int i = 0; i < fields.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    protected Connection getConnection() {
        try {
            if (connection == null) {
                InitialContext context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup(JDBC_DATASOURCE);
                connection = dataSource.getConnection();
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
