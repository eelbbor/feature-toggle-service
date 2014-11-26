package com.featuretoggle.controller;

import com.featuretoggle.domain.dto.ToggleDTO;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
public class ToggleControllerTest extends RestTest {
    @Override
    protected Class getTestClass() {
        return ToggleController.class;
    }

    public void shouldHandleCreate() {
        ToggleDTO dto = new ToggleDTO();
        dto.setAccountId(UUID.randomUUID().toString());
        Entity<ToggleDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE);
        final Response response = target().path(ToggleController.PATH_QUALIFIER).request().post(entity);
        assertEquals(response.getStatus(), CREATED.getStatusCode());
        ToggleDTO toggle = response.readEntity(ToggleDTO.class);
        assertEquals(toggle.getAccountId(), dto.getAccountId());
    }

    public void shouldHandleGenericQuery() {
        final Response response = target().path(ToggleController.PATH_QUALIFIER).request().get();
        assertEquals(response.getStatus(), OK.getStatusCode());
        ToggleDTO toggle = response.readEntity(ToggleDTO.class);
        assertNotNull(toggle);
    }

    public void shouldHandleIdQuery() {
        String id = UUID.randomUUID().toString();
        final Response response = target().path(ToggleController.PATH_QUALIFIER + id).request().get();
        assertEquals(response.getStatus(), OK.getStatusCode());
        ToggleDTO toggle = response.readEntity(ToggleDTO.class);
        assertEquals(toggle.getId(), id);
    }

    public void shouldNotHandleIDQueryWithInvalidID() {
        final Response response = target().path(ToggleController.PATH_QUALIFIER + "foo").request().get();
        assertEquals(response.getStatus(), NOT_FOUND.getStatusCode());
    }

    public void shouldHandleUpdate() {
        ToggleDTO dto = new ToggleDTO();
        dto.setId(UUID.randomUUID().toString());
        Entity<ToggleDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE);
        final Response response = target().path(ToggleController.PATH_QUALIFIER + dto.getId()).request().put(entity);
        assertEquals(response.getStatus(), OK.getStatusCode());
        ToggleDTO toggle = response.readEntity(ToggleDTO.class);
        assertEquals(toggle.getId(), dto.getId());
    }

    public void shouldNotHandleUpdateWithInvalidID() {
        ToggleDTO dto = new ToggleDTO();
        dto.setId(UUID.randomUUID().toString());
        Entity<ToggleDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE);
        final Response response = target().path(ToggleController.PATH_QUALIFIER + "foo").request().put(entity);
        assertEquals(response.getStatus(), NOT_FOUND.getStatusCode());
    }

    public void shouldHandleDelete() {
        final Response response = target().path(ToggleController.PATH_QUALIFIER + UUID.randomUUID()).request().delete();
        assertEquals(response.getStatus(), OK.getStatusCode());
    }

    public void shouldNotHandleDeleteWithInvalidID() {
        final Response response = target().path(ToggleController.PATH_QUALIFIER + "foo").request().delete();
        assertEquals(response.getStatus(), NOT_FOUND.getStatusCode());
    }

}