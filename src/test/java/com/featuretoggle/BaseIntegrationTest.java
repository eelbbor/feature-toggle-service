package com.featuretoggle;

import com.featuretoggle.db.dao.AbstractDAO;
import org.postgresql.ds.PGSimpleDataSource;
import org.testng.annotations.BeforeClass;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public abstract class BaseIntegrationTest {
    @BeforeClass
    protected void initializeTestContext() {
        String initialContextFactory = System.getProperty(Context.INITIAL_CONTEXT_FACTORY);
        if(initialContextFactory == null) {
            registerTestDatasource();
        }
    }

    private void registerTestDatasource() {
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, TestContextFactory.class.getName());
            new InitialContext().bind(AbstractDAO.JDBC_DATASOURCE, new TestDataSource());
        } catch (NamingException e) {
            throw new RuntimeException("Failed to initialize test DataSource", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize test DataSource", e);
        }
    }

    private class TestDataSource implements DataSource {
        private PGSimpleDataSource dataSource;

        public TestDataSource() throws SQLException {
            dataSource = new PGSimpleDataSource();
            dataSource.setUrl(System.getProperty("testJdbcUrl"));
            dataSource.setUser(System.getProperty("testJdbcUser"));
            dataSource.setPassword(System.getProperty("testJdbcPassword"));
        }

        @Override
        public Connection getConnection() throws SQLException {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            Connection connection = dataSource.getConnection(username, password);
            connection.setAutoCommit(false);
            return connection;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return dataSource.getLogWriter();
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
            dataSource.setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            dataSource.setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return dataSource.getLoginTimeout();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return dataSource.getParentLogger();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return dataSource.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return dataSource.isWrapperFor(iface);
        }
    }
}
