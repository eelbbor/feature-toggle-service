package com.ftoggleit.domain.dto;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test
public class AbstractDTOTest {
    private AbstractDTO dto;

    @BeforeClass
    public void setUpClass() {
        dto = new TestAbstractDTO();
    }

    public void shouldThrowUnsupportedOperationExceptionIfTryingToSetTheType() {
        try {
            dto.setType("foo");
            fail("Should have thrown exception for setting the type");
        } catch (UnsupportedOperationException e) {}
    }

    public void shouldReturnLowerCaseSimpleNameForType() {
        assertEquals(dto.getType(), TestAbstractDTO.class.getSimpleName().toLowerCase());
    }

    private class TestAbstractDTO extends AbstractDTO {
        @Override
        protected Class getTypeClass() {
            return this.getClass();
        }
    }
}