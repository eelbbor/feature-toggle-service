package com.featuretoggle.db.dao;

import com.featuretoggle.BaseIntegrationTest;
import com.featuretoggle.domain.Account;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@Test
public class AccountDAOTest extends BaseIntegrationTest {
    private AccountDAO dao;
    private Set<UUID> createdAccounts;

    @BeforeClass
    protected void setUpClass() {
        dao = new AccountDAO();
        createdAccounts = new TreeSet<UUID>();
    }

    @AfterClass
    protected void tearDownClass() {
        for(UUID id : createdAccounts) {
            try {
                dao.delete(id);
            } catch (SQLException e) {}
        }
    }

    public void shouldReturnNullForInvalidIdQuery() throws SQLException {
        assertNull(dao.findById(UUID.randomUUID()));
    }

    public void shouldCreateAccount() throws Exception {
        String name = UUID.randomUUID().toString();
        UUID id = createAccount(name);
        validateAccount(id, name);
    }

    public void shouldUpdateAccount() throws Exception {
        String name = UUID.randomUUID().toString();
        UUID id = createAccount(name);
        validateAccount(id, name);

        String newName = UUID.randomUUID().toString();
        assertFalse(name.equals(newName));
        validateAccount(dao.updateAccountName(id, newName));
    }

    public void shouldReturnNullForInvalidIdUpdateAccount() throws Exception {
        String name = UUID.randomUUID().toString();
        UUID id = createAccount(name);
        validateAccount(id, name);

        UUID invalidId = UUID.randomUUID();
        String newName = UUID.randomUUID().toString();
        assertNull(dao.updateAccountName(invalidId, newName));
    }

    public void shouldDeleteAccount() throws Exception {
        UUID id = createAccount(UUID.randomUUID().toString());
        assertEquals(dao.delete(id), 1);
    }

    public void shouldReturnZeroForInvalidIdDeleteAccount() throws Exception {
        createAccount(UUID.randomUUID().toString());
        assertEquals(dao.delete(UUID.randomUUID()), 0);
    }

    private UUID createAccount(String name) throws Exception {
        UUID id = dao.insert(name);
        createdAccounts.add(id);
        return id;
    }

    private void validateAccount(UUID id, String name) throws Exception {
        validateAccount(new Account(id, name));
    }

    private void validateAccount(Account account) throws Exception {
        Account actualAccount = dao.findById(account.getId());
        assertNotNull(actualAccount);
        assertEquals(actualAccount.getId(), account.getId());
        assertEquals(actualAccount.getName(), account.getName());
    }
}