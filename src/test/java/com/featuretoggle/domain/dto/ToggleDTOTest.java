package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Toggle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@Test
public class ToggleDTOTest {
    private Toggle toggle;
    private ToggleDTO dto;

    @BeforeMethod
    public void setUpClass() {
        toggle = new Toggle(UUID.randomUUID(), UUID.randomUUID(), null);
        dto = new ToggleDTO(toggle);
    }

    public void shouldExposeTypeAsToggle() {
        assertEquals(dto.getType(), Toggle.class.getSimpleName().toLowerCase());
    }

    public void shouldNotAllowForSettingTheAccountId() {
        String newId = UUID.randomUUID().toString();
        dto.setAccountId(newId);
        assertEquals(dto.getAccountId(), newId);
        assertFalse(dto.getAccountId().equals(toggle.getAccountId().toString()));
    }

    public void shouldAllowForGettingTheAccountId() {
        assertEquals(dto.getAccountId(), toggle.getAccountId().toString());
    }

    public void shouldAllowForSettingTheName() {
        assertNull(dto.getName());
        dto.setName("foo");
        assertEquals(dto.getName(), "foo");
    }

    public void shouldAllowForSettingTheEnabledState() {
        assertFalse(dto.isEnabled());
        dto.setEnabled(true);
        assertTrue(dto.isEnabled());
    }
}