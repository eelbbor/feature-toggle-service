package com.ftoggleit.service;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class FeatureToggleServiceTest {
    private FeatureToggleService service;

    @BeforeMethod
    protected void setUp() {
        service = new FeatureToggleService();
    }

    public void shouldCreateFeatureToggle() {
        service.createToggle("ft_name");
    }

}