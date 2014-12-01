package com.featuretoggle.db.dao;

import com.featuretoggle.domain.Identifiable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//TODO: AbstractDAO needs to be updated to use the connection to hydrate object on insert and update rather than connect again

public abstract class AbstractDAO<T extends Identifiable> {
    public static final String JDBC_DATASOURCE = "jdbc/datasource";
    public static final String SELECT_STATEMENT = "SELECT %s FROM %s WHERE %s";
    public static final String INSERT_STATEMENT = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE %s";
    public static final String DELETE_STATEMENT = "DELETE FROM %s WHERE %s";

    abstract public String getTableName();

    abstract protected T processResultSetRow(ResultSet resultSet) throws SQLException;

    protected Set<T> equalFetchAllQuery(String[] fields, List<Object> values, boolean andWhereClauses) throws SQLException {
        if (fields == null || values == null || fields.length != values.size() || fields.length == 0) {
            throw new IllegalArgumentException("The fields and values must be of equal length and cannot be empty");
        }
        String query = createQueryString("*", fields, andWhereClauses);
        return executePreparedStatement(query, values).results;
    }

    protected int insert(String[] fields, List<Object> values) throws SQLException {
        if (fields == null || values == null || fields.length != values.size() || fields.length == 0) {
            throw new IllegalArgumentException("The fields and values must be of equal length and cannot be empty");
        }
        return executePreparedStatement(createInsertString(fields), values).updateCount;
    }

    protected int update(String[] updateFields, List<Object> updateValues, String[] queryFields, List<Object> queryValues, boolean isAndQuery) throws SQLException {
        if (updateFields == null || updateValues == null || updateFields.length != updateValues.size() || updateFields.length == 0) {
            throw new IllegalArgumentException("The updateFields and updateValues must be of equal length and cannot be empty");
        }
        if (queryFields == null || queryValues == null || queryFields.length != queryValues.size() || queryFields.length == 0) {
            throw new IllegalArgumentException("The queryFields and queryValues must be of equal length and cannot be empty");
        }
        List<Object> values = new ArrayList<>(updateValues);
        values.addAll(queryValues);
        return executePreparedStatement(createUpdateString(updateFields, queryFields, isAndQuery), values).updateCount;
    }

    public int delete(UUID id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("The id cannot be null");
        }
        String statement = String.format(DELETE_STATEMENT, getTableName(), "id = ?");
        return executePreparedStatement(statement, new ArrayList<Object>(Arrays.asList(id))).updateCount;
    }

    private ResultWrapper<T> executePreparedStatement(String sql, List<Object> values) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            preparedStatement.execute();
            connection.commit();
            ResultWrapper<T> result = processResultSet(preparedStatement);
            preparedStatement.close();
            return result;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private ResultWrapper<T> processResultSet(PreparedStatement statement) throws SQLException {
        ResultWrapper<T> result = new ResultWrapper<T>(statement.getUpdateCount());
        ResultSet resultSet = statement.getResultSet();
        if (resultSet != null) {
            while (resultSet.next()) {
                result.addResult(processResultSetRow(resultSet));
            }
        }
        return result;
    }

    private String createUpdateString(String[] updateFields, String[] queryFields, boolean andClauses) {
        String setString = createBindingEqualitySeparatedString(updateFields, ",");
        String whereClause = createWhereClause(queryFields, andClauses);
        return String.format(UPDATE_STATEMENT, getTableName(), setString, whereClause);
    }

    private String createQueryString(String fetch, String[] queryFields, boolean andClauses) {
        return String.format(SELECT_STATEMENT, fetch, getTableName(), createWhereClause(queryFields, andClauses));
    }

    private String createWhereClause(String[] fields, boolean andClauses) {
        return createBindingEqualitySeparatedString(fields, andClauses ? "and" : "or");
    }

    private String createBindingEqualitySeparatedString(String[] fields, String separator) {
        StringBuilder bindingString = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (bindingString.length() > 0) {
                bindingString.append(" " + separator + " ");
            }
            bindingString.append(fields[i] + " = ?");
        }
        return bindingString.toString();
    }

    private String createInsertString(String[] fields) {
        StringBuilder fieldList = new StringBuilder();
        StringBuilder valuesList = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (fieldList.length() > 0) {
                fieldList.append(" , ");
                valuesList.append(" , ");
            }
            fieldList.append(fields[i]);
            valuesList.append("?");
        }
        return String.format(INSERT_STATEMENT, getTableName(), fieldList.toString(), valuesList.toString());
    }

    protected Connection getConnection() throws SQLException {
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(JDBC_DATASOURCE);
            return dataSource.getConnection();
        } catch (NamingException e) {
            throw new SQLException("Unable to retrieve JNDI DataSource.", e);
        }
    }

    private class ResultWrapper<T> {
        private int updateCount;
        private Set<T> results;

        private ResultWrapper (int updateCount) {
            this.updateCount = updateCount;
            results = new LinkedHashSet<T>();
        }

        private void addResult(T result) {
            results.add(result);
        }
    }
}
