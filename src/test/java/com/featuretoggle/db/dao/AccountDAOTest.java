package com.featuretoggle.db.dao;

import com.featuretoggle.BaseIntegrationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class AccountDAOTest extends BaseIntegrationTest {
    private AccountDAO dao;

    @BeforeClass
    protected void setUpClass() {
        dao = new AccountDAO();
    }

    public void shouldCreateAccount() throws Exception {
        dao.insert("Some Damn Account");
    }
}