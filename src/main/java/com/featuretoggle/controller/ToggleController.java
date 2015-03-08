package com.featuretoggle.controller;

import com.featuretoggle.domain.Toggle;
import com.featuretoggle.domain.dto.ToggleDTO;

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
import java.util.UUID;

@Path(ToggleController.PATH_QUALIFIER)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ToggleController extends Controller {
    public static final String PATH_QUALIFIER = "/toggles/";

    @POST
    public Response create(ToggleDTO toggleDTO) {
        Toggle toggle = new Toggle(UUID.randomUUID(), UUID.fromString(toggleDTO.getAccountId()), "this is a dummy for now");
        return getCreatedResponse(PATH_QUALIFIER, new ToggleDTO(toggle));
    }

    @GET
    public Response query() {
        Toggle toggle = new Toggle(UUID.randomUUID(), UUID.randomUUID(), "response dummy");
        return getOkResponse(new ToggleDTO(toggle));
    }

    @GET
    @Path(ID_PATH)
    public Response query(@PathParam("id") String id) {
        Toggle toggle = new Toggle(UUID.fromString(id), UUID.randomUUID(), "this is a dummy for now");
        return getOkResponse(new ToggleDTO(toggle));
    }

    @PUT
    @Path(ID_PATH)
    public Response update(@PathParam("id") String id) {
        Toggle toggle = new Toggle(UUID.fromString(id), UUID.randomUUID(), "this is a dummy for now");
        return getOkResponse(new ToggleDTO(toggle));
    }

    @DELETE
    @Path(ID_PATH)
    public Response delete(@PathParam("id") String id) {
        return Response.ok().build();
    }
}
