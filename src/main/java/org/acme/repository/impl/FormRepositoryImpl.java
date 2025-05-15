package org.acme.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Form;

import org.acme.repository.FormModelRepository;

import java.io.ByteArrayInputStream;
import java.util.Map;

@ApplicationScoped
public class FormRepositoryImpl implements FormModelRepository {

    @Override
    public Form getFormModel(String name) {

        try{
            String filePath = "forms/test.json";
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.get(filePath);

            if (blob == null || !blob.exists()) {
                throw new RuntimeException("File not found in Firebase Storage");
            }

            // Step 3: Read blob content as byte array
            byte[] content = blob.getContent();

            // Step 4: Parse JSON into Map<String, Object>
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> formModel = mapper.readValue(new ByteArrayInputStream(content),new TypeReference<Map<String, Object>>() {});

            return new Form(formModel, true);

        } catch (Exception e) {
            Log.error("Error reading form data file:", e);
            return new Form();
        }
    }
}
