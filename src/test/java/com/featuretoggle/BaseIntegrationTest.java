package com.featuretoggle;

import com.featuretoggle.db.dao.AbstractDAO;
import org.postgresql.ds.PGSimpleDataSource;
import org.testng.annotations.BeforeClass;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;

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
            InitialContext ic = new InitialContext();
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUrl(System.getProperty("testJdbcUrl"));
            dataSource.setUser(System.getProperty("testJdbcUser"));
            dataSource.setPassword(System.getProperty("testJdbcPassword"));
            ic.bind(AbstractDAO.JDBC_DATASOURCE, dataSource);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to initialize test DataSource", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize test DataSource", e);
        }
    }
}
