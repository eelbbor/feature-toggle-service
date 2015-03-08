package com.featuretoggle.controller;

import com.featuretoggle.domain.dto.IdentifiableDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Produces(MediaType.APPLICATION_JSON)
public abstract class Controller {
    public static final String ID_PATH = "{id:[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}}";

    @GET
    @Path(ID_PATH)
    public abstract Response query(@PathParam("id") String id);

    protected Response getServerErrorResponse(Exception e) {
        return Response.serverError().build();
    }

    protected Response getOkResponse(IdentifiableDTO dto) {
        if (dto == null) {
            return Response.ok().build();
        }
        return Response.ok(dto).build();
    }

    protected Response get404Response() {
        return Response.noContent().build();
    }

    protected Response getCreatedResponse(String path, IdentifiableDTO dto) {
        return Response.created(URI.create(path + dto.getId())).entity(dto).build();
    }
}
