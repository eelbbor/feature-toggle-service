package com.featuretoggle.domain;

import org.testng.annotations.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test
public class IdentifiableTest {
    public void shouldThrowExceptionForNullId() {
        try {
            new TestIdentifiable(null);
            fail("Should have throw IllegalArgumentException for null ID");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldSuccessfullyCreate() {
        UUID id = randomUUID();
        Identifiable ft = new TestIdentifiable(id);
        assertEquals(ft.getId(), id);
    }

    private class TestIdentifiable extends Identifiable {
        protected TestIdentifiable(UUID id) {
            super(id);
        }
    }
}