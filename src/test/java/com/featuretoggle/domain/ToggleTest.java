package com.featuretoggle.domain;

import org.testng.annotations.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class ToggleTest {
    public void shouldThrowExceptionForNullAccountId() {
        try {
            new Toggle(randomUUID(), null, null);
            fail("Should have throw IllegalArgumentException for null ID");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldSuccessfullyCreate() {
        UUID id = randomUUID();
        UUID accountId = randomUUID();
        Toggle ft = new Toggle(id, accountId, "foo");
        assertEquals(ft.getId(), id);
        assertEquals(ft.getAccountId(), accountId);
        assertEquals(ft.getName(), "foo");
    }

    public void shouldDefaultToDisabled() {
        Toggle ft = createToggle();
        assertFalse(ft.isEnabled());
    }

    public void shouldHonorEnabledState() {
        Toggle ft = new Toggle(randomUUID(), randomUUID(), "foo", true);
        assertTrue(ft.isEnabled());
    }

    public void shouldHonorScopeId() {
        UUID scopeId = randomUUID();
        Toggle ft = new Toggle(randomUUID(), randomUUID(), "foo", scopeId);
        assertEquals(ft.getScopeId(), scopeId);
    }

    public void shouldUpdateName() {
        Toggle ft = createToggle();
        assertFalse(ft.getName().equals("baz"));
        ft.setName("baz");
        assertEquals(ft.getName(), "baz");
    }

    public void shouldEnable() {
        Toggle ft = createToggle();
        assertFalse(ft.isEnabled());
        ft.setEnabled(true);
        assertTrue(ft.isEnabled());
    }

    public void shouldDisable() {
        Toggle ft = new Toggle(randomUUID(), randomUUID(), "foo", true);
        assertTrue(ft.isEnabled());

        ft.setEnabled(false);
        assertFalse(ft.isEnabled());
    }

    public void shouldUpdateScopeId() {
        Toggle ft = createToggle();
        assertNull(ft.getScopeId());

        UUID scopeId = randomUUID();
        ft.setScopeId(scopeId);
        assertEquals(ft.getScopeId(), scopeId);
    }

    private Toggle createToggle() {
        Toggle ft = new Toggle(randomUUID(), randomUUID(), "foo");
        assertEquals(ft.getName(), "foo");
        return ft;
    }
}