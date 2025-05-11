package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.repository.DmnModelRepository;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/decision")
public class DecisionResource {

    @Inject
    DmnModelRepository dmnModelRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {

        InputStream inputStream = dmnModelRepository.getDmnModel("dish");

        Map<String, Object> variables = new HashMap<>();
        variables.put("season", "Fall");
        variables.put("guestCount", 2);

        List<Map<String, Object>> result = evaluateDecision(inputStream, variables);

        if (result.size() > 0){
            return result.getFirst();
        }
        else {
            return null;
        }


    }

    private static List<Map<String, Object>> evaluateDecision(InputStream inputStream, Map<String, Object> inputs) {
        // create a default DMN engine
        DmnEngine dmnEngine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration()
                .buildEngine();

        // read a DMN model instance from a file
        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);


        // parse the decisions
        List<DmnDecision> decisions = dmnEngine.parseDecisions(dmnModelInstance);

        DmnDecision decision = decisions.getFirst();
        VariableMap variables = Variables.createVariables();
        for (String key : inputs.keySet()){
            variables = variables.putValue(key, inputs.get(key));

        }

        DmnDecisionResult results = dmnEngine.evaluateDecision(decision,variables);

        List<Map<String, Object>> result = results.getResultList();
        return result;
    }
}