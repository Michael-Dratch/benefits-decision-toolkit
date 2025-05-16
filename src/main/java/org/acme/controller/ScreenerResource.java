package org.acme.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import jakarta.ws.rs.core.Response;
import org.acme.model.Screener;
import org.acme.service.ScreenerService;

@Path("/api/screener/{screenerName}")
public class ScreenerResource {

    @Inject
    ScreenerService screenerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("screenerName") String screenerName) {

        Screener form = screenerService.getPublishedScreener(screenerName);

        return Response.ok(form, MediaType.APPLICATION_JSON).build();
    }
}