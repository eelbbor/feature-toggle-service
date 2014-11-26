package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Identifiable;
import com.featuretoggle.domain.Toggle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class IdentifiableDTOTest {
    private IdentifiableDTO dto;
    private Identifiable identifiable;

    @BeforeMethod
    public void setUpClass() {
        identifiable = new Toggle(UUID.randomUUID(), UUID.randomUUID(), null);
        dto = new TestIdentifiableDTO(identifiable);
    }

    public void shouldUpdateTheStringValueOfTheId() {
        String newId = UUID.randomUUID().toString();
        dto.setId(newId);
        assertEquals(dto.getId(), newId);
        assertFalse(dto.getId().equals(identifiable.getId().toString()));
    }

    public void shouldReturnStringRepresentationOfTheID() {
        assertEquals(dto.getId(), identifiable.getId().toString());
    }

    private class TestIdentifiableDTO extends IdentifiableDTO {
        public TestIdentifiableDTO(Identifiable identifiable) {
            super(identifiable);
        }

        @Override
        protected Class getTypeClass() {
            return this.getClass();
        }
    }
}