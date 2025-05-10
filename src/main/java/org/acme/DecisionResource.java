package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/decision")
public class DecisionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> get() {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dish.dmn");


        // create a default DMN engine
        DmnEngine dmnEngine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration()
                .buildEngine();

        // read a DMN model instance from a file
        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);


        // parse the decisions
        List<DmnDecision> decisions = dmnEngine.parseDecisions(dmnModelInstance);

        DmnDecision decision = decisions.getFirst();

        VariableMap variables = Variables.createVariables()
                .putValue("input1", "fall")
                .putValue("InputClause_0hmkumv", 2);

        DmnDecisionResult results = dmnEngine.evaluateDecision(decision,variables);

        List<Map<String, Object>> result = results.getResultList();

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("TEST", "HELLO WORLD");

        if (result.size() > 0){
            return result.getFirst();
        }
        else {
            return null;
        }


    }
}