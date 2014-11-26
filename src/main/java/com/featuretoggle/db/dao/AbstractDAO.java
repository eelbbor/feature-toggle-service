package com.featuretoggle.db.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDAO {
    public static final String SCHEMA_NAME = System.getenv("schema");
    private Connection connection;

    public Connection getConnection() {
        try {
            if (connection == null) {
                InitialContext context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup("jdbc/datasource");
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
