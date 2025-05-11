package org.acme.repository.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.repository.DmnModelRepository;

import java.io.InputStream;

@ApplicationScoped
public class DmnFileRepository implements DmnModelRepository {

    @Override
    public InputStream getDmnModel(String name) {

        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(name + ".dmn");
            return inputStream;
        } catch (Exception e){
            Log.error("Error creating inputStream from dmn file", e);
            return InputStream.nullInputStream();
        }

    }
}
