package com.ftoggleit.domain;

import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class FeatureToggleTest {
    public void shouldThrowExceptionForNullId() {
        try {
            new FeatureToggle(null, null);
            fail("Should have throw IllegalArgumentException for null ID");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldSuccessfullyCreate() {
        UUID id = UUID.randomUUID();
        FeatureToggle ft = new FeatureToggle(id, "foo");
        assertEquals(ft.getId(), id);
        assertEquals(ft.getName(), "foo");
    }

    public void shouldDefaultToDisabled() {
        FeatureToggle ft = createFeatureToggle();
        assertFalse(ft.isEnabled());
    }

    public void shouldHonorEnabledState() {
        FeatureToggle ft = new FeatureToggle(UUID.randomUUID(), "foo", true);
        assertTrue(ft.isEnabled());
    }

    public void shouldHonorScopeId() {
        UUID scopeId = UUID.randomUUID();
        FeatureToggle ft = new FeatureToggle(UUID.randomUUID(), "foo", scopeId);
        assertEquals(ft.getScopeId(), scopeId);
    }

    public void shouldUpdateName() {
        FeatureToggle ft = createFeatureToggle();
        assertFalse(ft.getName().equals("baz"));
        ft.setName("baz");
        assertEquals(ft.getName(), "baz");
    }

    public void shouldEnable() {
        FeatureToggle ft = createFeatureToggle();
        assertFalse(ft.isEnabled());
        ft.setEnabled(true);
        assertTrue(ft.isEnabled());
    }

    public void shouldDisable() {
        FeatureToggle ft = new FeatureToggle(UUID.randomUUID(), "foo", true);
        assertTrue(ft.isEnabled());

        ft.setEnabled(false);
        assertFalse(ft.isEnabled());
    }

    public void shouldUpdateScopeId() {
        FeatureToggle ft = createFeatureToggle();
        assertNull(ft.getScopeId());

        UUID scopeId = UUID.randomUUID();
        ft.setScopeId(scopeId);
        assertEquals(ft.getScopeId(), scopeId);
    }

    private FeatureToggle createFeatureToggle() {
        FeatureToggle ft = new FeatureToggle(UUID.randomUUID(), "foo");
        assertEquals(ft.getName(), "foo");
        return ft;
    }
}