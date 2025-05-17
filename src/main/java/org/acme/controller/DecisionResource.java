package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.repository.DmnModelRepository;
import org.acme.service.DmnService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/decision")
public class DecisionResource {

    @Inject
    DmnModelRepository dmnModelRepository;

    @Inject
    DmnService dmnService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, Object> post(Map<String, Object> inputData) {

        Optional<byte[]> dmnDataOpt = dmnModelRepository.getDmnModelByName((String) inputData.get("screenerName"));

        if (dmnDataOpt.isEmpty()){
            throw new NotFoundException();
        }

        InputStream dmnFileInputStream = new ByteArrayInputStream(dmnDataOpt.get());
        List<Map<String, Object>> result = dmnService.evaluateDecision(dmnFileInputStream, inputData);

        if (result.size() > 0){
            return result.getFirst();
        }

        else {
            throw new InternalServerErrorException();
        }
    }
}