package com.featuretoggle.domain;

import org.testng.annotations.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test
public class AccountTest {
    public void shouldThrowExceptionForNullName() {
        try {
            new Account(randomUUID(), null);
            fail("Should have throw IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForEmptyName() {
        try {
            new Account(randomUUID(), "");
            fail("Should have throw IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldSuccessfullyCreate() {
        UUID id = randomUUID();
        String name = randomUUID().toString();
        Account account = new Account(id, name);
        assertEquals(account.getId(), id);
        assertEquals(account.getName(), name);
    }

    public void shouldThrowExceptionIfTryToSetNameToNull() {
        UUID id = randomUUID();
        String name = randomUUID().toString();
        Account account = new Account(id, name);
        try {
            account.setName(null);
            fail("Should have throw IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionIfTryToSetNameToEmpty() {
        UUID id = randomUUID();
        String name = randomUUID().toString();
        Account account = new Account(id, name);
        try {
            account.setName("");
            fail("Should have throw IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
        }
    }

}