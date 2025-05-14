package org.acme.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import jakarta.ws.rs.core.Response;
import org.acme.model.Form;
import org.acme.service.FormService;

@Path("/api/form/{screenerName}")
public class FormResource {

    @Inject
    FormService formService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("screenerName") String screenerName) {

        Form form = formService.getPublishedFormModel(screenerName);

        return Response.ok(form, MediaType.APPLICATION_JSON).build();
    }
}