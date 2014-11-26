package com.featuretoggle.service;

import com.featuretoggle.db.dao.ToggleDAO;
import com.featuretoggle.domain.Toggle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@Test
public class ToggleServiceTest {
    private ToggleService service;
    private ToggleDAO dao;

    @BeforeMethod
    protected void setUp() {
        dao = mock(ToggleDAO.class);
        service = new ToggleService(dao);
    }

    public void shouldCreateToggle() throws Exception {
        when(dao.insert("ft_name")).thenReturn(new Toggle(randomUUID(), randomUUID(), "ft_name"));
        Toggle ft = service.createToggle("ft_name");
        assertEquals(ft.getName(), "ft_name");
    }
}