package com.featuretoggle.db.dao;

import com.featuretoggle.BaseIntegrationTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Test
public class AbstractDAOTest extends BaseIntegrationTest {
    public void shouldCreateAccount() {
        try {
            new AccountDAO().insert("Some Damn Account");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}