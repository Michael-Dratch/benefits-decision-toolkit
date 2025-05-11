package org.acme.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import org.acme.repository.FormModelRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Path("/screener/{screenerName}")
public class ScreenerResource {

    @Inject
    Template screener;

    @Inject
    FormModelRepository formModelRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("screenerName") String screenerName) {

        String form = formModelRepository.getFormModal(screenerName);
        if (form.isBlank()) {
            //Return form not found template
            return null;
        }
        return screener.data("form", form);
    }
}