package org.acme.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Form;
import org.acme.repository.FormModelRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class FormFileRepository implements FormModelRepository {

    @Override
    public Form getFormModel(String name) {

        Map<String, Object> formModel = new HashMap<>();

        String fileName = name + ".json";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                return new Form();
            }

            ObjectMapper mapper = new ObjectMapper();
            formModel = mapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            return new Form(formModel, true);

        } catch (Exception e) {
            Log.error("Error reading form data file:", e);
            return new Form();
        }
    }
}
