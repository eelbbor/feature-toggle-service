package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Account;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;

@Test
public class AccountDTOTest {
    private Account account;
    private AccountDTO dto;
    private String name;

    @BeforeMethod
    public void setUpClass() {
        name = UUID.randomUUID().toString();
        account = new Account(UUID.randomUUID(), name);
        dto = new AccountDTO(account);
    }

    public void shouldExposeTypeAsAccount() {
        assertEquals(dto.getType(), Account.class.getSimpleName().toLowerCase());
    }

    public void shouldAllowForSettingTheName() {
        assertEquals(dto.getName(), name);
        String newName = UUID.randomUUID().toString();
        dto.setName(newName);
        assertEquals(dto.getName(), newName);
    }
}