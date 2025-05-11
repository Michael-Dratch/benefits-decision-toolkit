package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.repository.DmnModelRepository;
import org.acme.service.DmnService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/decision")
public class DecisionResource {

    @Inject
    DmnModelRepository dmnModelRepository;

    @Inject
    DmnService dmnService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {

        InputStream inputStream = dmnModelRepository.getDmnModel("dish");

        Map<String, Object> variables = new HashMap<>();
        variables.put("season", "Winter");
        variables.put("guestCount", 8);

        List<Map<String, Object>> result = dmnService.evaluateDecision(inputStream, variables);

        if (result.size() > 0){
            return result.getFirst();
        }
        else {
            return null;
        }


    }

}