package com.featuretoggle.db.dao;

import com.featuretoggle.domain.Identifiable;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class AbstractDAOTest {
    private TestDAO dao;
    private Connection connection;
    private PreparedStatement statement;

    private final String[] fields = {"id", "is_true", "name"};
    private final UUID id = UUID.randomUUID();
    private final List<Object> values = new ArrayList<Object>(Arrays.asList(id, true, "sam"));

    @BeforeMethod
    protected void setUp() {
        dao = new TestDAO();
        connection = mock(Connection.class);
    }

    @AfterMethod
    protected void tearDown() throws Exception {
        int connectionCount = dao.connectionCount;
        if (statement != null || connectionCount > 0) {
            assertEquals(1, connectionCount);
            verify(connection, times(connectionCount)).commit();
            verify(connection, times(connectionCount)).close();
            statement = null;
        }
    }

    public void shouldThrowExceptionOnEqualFetchAllWithNullFields() throws SQLException {
        try {
            dao.equalFetchAllQuery(null, values, true);
            fail("Should have thrown exception due to null fields");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnEqualFetchAllWithNullValues() throws SQLException {
        try {
            dao.equalFetchAllQuery(fields, null, true);
            fail("Should have thrown exception due to null values");
        } catch (IllegalArgumentException e) {
        }

    }

    public void shouldThrowExceptionOnEqualFetchAllWithEmptyArguments() throws SQLException {
        try {
            String[] fields = {};
            dao.equalFetchAllQuery(fields, new ArrayList<>(), true);
            fail("Should have thrown exception due to empty query fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnEqualFetchAllWithInconsistentFieldValueLengths() throws SQLException {
        try {
            String[] fields = {"id"};
            List<Object> values = new ArrayList<Object>(Arrays.asList("1234", "baz"));
            dao.equalFetchAllQuery(fields, values, true);
            fail("Should have thrown exception due to inconsistent length for fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldReturnResultsEqualFetchAllAndCondition() throws SQLException {
        final Identifiable[] expectedResults = {new TestIdentifiable(), new TestIdentifiable()};
        createPreparedStatement(0, expectedResults);
        String whereClause = fields[0] + " = ? and " + fields[1] + " = ? and " + fields[2] + " = ?";
        when(connection.prepareStatement("SELECT * FROM " + dao.getTableName() + " WHERE " + whereClause)).thenReturn(statement);
        Set results = dao.equalFetchAllQuery(fields, values, true);
        verifyStatement(values);
        assertEquals(results.size(), expectedResults.length);
        assertTrue(results.containsAll(Arrays.asList(expectedResults)));
    }

    public void shouldReturnResultsEqualFetchAllOrCondition() throws SQLException {
        final Identifiable[] expectedResults = {new TestIdentifiable(), new TestIdentifiable()};
        createPreparedStatement(0, expectedResults);
        String whereClause = fields[0] + " = ? or " + fields[1] + " = ? or " + fields[2] + " = ?";
        when(connection.prepareStatement("SELECT * FROM " + dao.getTableName() + " WHERE " + whereClause)).thenReturn(statement);
        Set results = dao.equalFetchAllQuery(fields, values, false);
        verifyStatement(values);
        assertEquals(results.size(), expectedResults.length);
        assertTrue(results.containsAll(Arrays.asList(expectedResults)));
    }

    public void shouldThrowExceptionOnUpdateWithNullUpdateFields() throws SQLException {
        try {
            dao.update(null, values, fields, values, true);
            fail("Should have thrown exception due to null updateFields");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithNullUpdateValues() throws SQLException {
        try {
            dao.update(fields, null, fields, values, true);
            fail("Should have thrown exception due to null updateValues");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithEmptyUpdateArguments() throws SQLException {
        try {
            String[] fields = {};
            dao.update(fields, new ArrayList<>(), this.fields, this.values, true);
            fail("Should have thrown exception due to empty update fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithUnequalLengthUpdateArguments() throws SQLException {
        try {
            String[] fields = {"id"};
            List<Object> values = new ArrayList<Object>(Arrays.asList("1234", "foo"));
            dao.update(fields, values, this.fields, this.values, true);
            fail("Should have thrown exception due to empty unequal length update fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithNullQueryFields() throws SQLException {
        try {
            dao.update(fields, values, null, values, true);
            fail("Should have thrown exception due to null queryFields");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithNullQueryValues() throws SQLException {
        try {
            dao.update(fields, values, fields, null, true);
            fail("Should have thrown exception due to null queryValues");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithEmptyQueryArguments() throws SQLException {
        try {
            String[] fields = {};
            dao.update(this.fields, this.values, fields, new ArrayList<>(), true);
            fail("Should have thrown exception due to empty query fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnUpdateWithUnequalLengthQueryArguments() throws SQLException {
        try {
            String[] fields = {"id"};
            List<Object> values = new ArrayList<Object>(Arrays.asList("1234", "foo"));
            dao.update(this.fields, this.values, fields, values, true);
            fail("Should have thrown exception due to empty unequal length query fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldUpdateForValidAndClause() throws SQLException {
        int expectedUpdateCount = 1;
        createMutatingPreparedStatement(expectedUpdateCount);
        String[] uFields = {fields[1], fields[2]};
        List<Object> uValues = new ArrayList<>(Arrays.asList(values.get(1), values.get(2)));
        String updateFields = uFields[0] + " = ? , " + uFields[1] + " = ?";
        String whereClause = fields[0] + " = ? and " + fields[1] + " = ? and " + fields[2] + " = ?";
        when(connection.prepareStatement("UPDATE " + dao.getTableName() + " SET " + updateFields + " WHERE " + whereClause)).thenReturn(statement);
        assertEquals(dao.update(uFields, uValues, fields, values, true), expectedUpdateCount);

        uValues.addAll(values);
        verifyStatement(uValues);
    }

    public void shouldUpdateForValidOrClause() throws SQLException {
        int expectedUpdateCount = 1;
        createMutatingPreparedStatement(expectedUpdateCount);
        String[] uFields = {fields[1], fields[2]};
        List<Object> uValues = new ArrayList<>(Arrays.asList(values.get(1), values.get(2)));
        String updateFields = uFields[0] + " = ? , " + uFields[1] + " = ?";
        String whereClause = fields[0] + " = ? or " + fields[1] + " = ? or " + fields[2] + " = ?";
        when(connection.prepareStatement("UPDATE " + dao.getTableName() + " SET " + updateFields + " WHERE " + whereClause)).thenReturn(statement);
        assertEquals(dao.update(uFields, uValues, fields, values, false), expectedUpdateCount);

        uValues.addAll(values);
        verifyStatement(uValues);
    }

    public void shouldThrowExceptionOnInsertWithNullFields() throws SQLException {
        try {
            dao.insert(null, values);
            fail("Should have thrown exception due to null fields");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnInsertWithNullValues() throws SQLException {
        try {
            dao.insert(fields, null);
            fail("Should have thrown exception due to null values");
        } catch (IllegalArgumentException e) {
        }

    }

    public void shouldThrowExceptionOnInsertWithEmptyArguments() throws SQLException {
        try {
            String[] fields = {};
            dao.insert(fields, new ArrayList<>());
            fail("Should have thrown exception due to empty fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionOnInsertWithInconsistentFieldValueLengths() throws SQLException {
        try {
            String[] fields = {"id"};
            List<Object> values = new ArrayList<Object>(Arrays.asList("1234", "baz"));
            dao.insert(fields, values);
            fail("Should have thrown exception due to unequal lengths for fields and values");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldInsertForValidValues() throws SQLException {
        int expectedUpdateCount = 1;
        createMutatingPreparedStatement(expectedUpdateCount);
        String insertFields = fields[0] + " , " + fields[1] + " , " + fields[2];
        when(connection.prepareStatement("INSERT INTO " + dao.getTableName() + " (" + insertFields + ") VALUES (? , ? , ?)")).thenReturn(statement);
        assertEquals(dao.insert(fields, values), expectedUpdateCount);
        verifyStatement(values);
    }

    public void shouldThrowExceptionOnDeleteWithNullId() throws Exception {
        try {
            dao.delete(null);
            fail("Should have thrown exception due to null fields");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldDeleteForValidValues() throws SQLException {
        int expectedUpdateCount = 1;
        createMutatingPreparedStatement(expectedUpdateCount);
        when(connection.prepareStatement("DELETE FROM " + dao.getTableName() + " WHERE id = ?")).thenReturn(statement);
        assertEquals(dao.delete(id), expectedUpdateCount);
        List<Object> values = new ArrayList<Object>(Arrays.asList(id));
        verifyStatement(values);
    }

    private void createMutatingPreparedStatement(int updateCount) throws SQLException {
        createPreparedStatement(updateCount, null);
    }

    private void createPreparedStatement(final int updateCount, final Identifiable... results) throws SQLException {
        statement = mock(PreparedStatement.class);
        final ResultSet resultSet = results == null ? null : mock(ResultSet.class);

        //handle update count
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                when(statement.getUpdateCount()).thenReturn(updateCount);
                when(statement.getResultSet()).thenReturn(resultSet);
                return resultSet != null;
            }
        }).when(statement).execute();

        //handle results
        if (resultSet != null) {
            doAnswer(getResultSetNextAnswer(resultSet, results)).when(resultSet).next();
        }
    }

    private Answer getResultSetNextAnswer(final ResultSet resultSet, final Identifiable... results) {
        return new Answer() {
            private int currentIndex;

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                if (results != null && currentIndex < results.length) {
                    when(resultSet.getObject("id")).thenReturn(results[currentIndex].getId());
                    currentIndex++;
                    return true;
                }
                return false;
            }
        };
    }

    private void verifyStatement(List<Object> values) throws SQLException {
        int argIndex = 1;
        for (Object value : values) {
            verify(statement).setObject(argIndex, values.get(argIndex - 1));
            argIndex++;
        }
        verify(statement).close();
    }

    private class TestDAO extends AbstractDAO {
        private String tableName = UUID.randomUUID().toString().replaceAll("-", "");
        private int connectionCount = 0;

        @Override
        public String getTableName() {
            return tableName;
        }

        @Override
        protected Identifiable processResultSetRow(ResultSet resultSet) throws SQLException {
            UUID id = (UUID) resultSet.getObject("id");
            return new TestIdentifiable(id);
        }

        @Override
        protected Connection getConnection() throws SQLException {
            connectionCount++;
            return connection;
        }
    }

    private class TestIdentifiable extends Identifiable {
        protected TestIdentifiable() {
            this(UUID.randomUUID());
        }

        public TestIdentifiable(UUID uuid) {
            super(uuid);
        }
    }
}