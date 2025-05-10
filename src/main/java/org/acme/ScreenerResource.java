package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Path("/screener")
public class ScreenerResource {

    @Inject
    Template screener;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("phlPropertyTaxRelief.json")) {
            if (is == null) {
                throw new RuntimeException("data.json not found");
            }

            String form = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(form);
            return screener.data("form", form);
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }
}