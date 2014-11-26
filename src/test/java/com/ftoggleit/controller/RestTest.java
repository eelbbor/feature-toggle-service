package com.ftoggleit.controller;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.annotations.Test;

import javax.ws.rs.core.Application;

@Test
public abstract class RestTest extends JerseyTestNg.ContainerPerClassTest {
    protected abstract Class getTestClass();

    @Override
    protected Application configure() {
        return new ResourceConfig(getTestClass());
    }
}
