package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, Object> post(Map<String, Object> data) {

        InputStream inputStream = dmnModelRepository.getDmnModel((String) data.get("screenerName"));

        List<Map<String, Object>> result = dmnService.evaluateDecision(inputStream, data);

        if (result.size() > 0){
            return result.getFirst();
        }
        else {
            return null;
        }
    }
}