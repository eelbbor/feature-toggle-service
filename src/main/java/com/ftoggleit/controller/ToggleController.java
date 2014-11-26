package com.ftoggleit.controller;

import com.ftoggleit.domain.Toggle;
import com.ftoggleit.domain.dto.ToggleDTO;
import com.ftoggleit.service.ToggleService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

@Path(ToggleController.PATH_QUALIFIER)
public class ToggleController {
    public static final String PATH_QUALIFIER = "/toggle/";
    private ToggleService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ToggleDTO toggleDTO) {
        Toggle toggle = new Toggle(UUID.randomUUID(), UUID.fromString(toggleDTO.getAccountId()), "this is a dummy for now");
        return Response.created(URI.create("toggle/" + toggle.getId())).entity(new ToggleDTO(toggle)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response query() {
        return createOkResponse(new Toggle(UUID.randomUUID(), UUID.randomUUID(), "response dummy"));
    }

    @GET
    @Path("{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(@PathParam("id") String id) {
        return createOkResponse(new Toggle(UUID.fromString(id), UUID.randomUUID(), "this is a dummy for now"));
    }

    @PUT
    @Path("{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id) {
        return createOkResponse(new Toggle(UUID.fromString(id), UUID.randomUUID(), "this is a dummy for now"));
    }

    @DELETE
    @Path("{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}")
    public Response delete(@PathParam("id") String id) {
        return Response.ok().build();
    }

    private Response createOkResponse(Toggle toggle) {
        ToggleDTO dto = new ToggleDTO(toggle);
        return Response.ok(dto).build();
    }

    private ToggleService getService() {
        if(service == null) {
            service = new ToggleService();
        }
        return service;
    }
}
