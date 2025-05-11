package org.acme.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.repository.FormModelRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class FormFileRepository implements FormModelRepository {

    @Override
    public String getFormModal(String name) {

        String fileName = name + ".json";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                return "";
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
