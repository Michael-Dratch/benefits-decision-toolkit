package org.acme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import org.acme.model.Form;
import org.acme.repository.FormModelRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Path("/screener/{screenerName}")
public class ScreenerResource {

    @Inject
    Template screener;

    @Inject
    FormModelRepository formModelRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("screenerName") String screenerName) throws JsonProcessingException {

        Form form = formModelRepository.getFormModel(screenerName);
        if (form.getModel().isEmpty()) {
            //Return form not found template
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(form);
        return screener.data("form", form).data("screenerName", screenerName);
    }
}