package org.acme.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.constants.CollectionNames;
import org.acme.constants.FieldNames;
import org.acme.repository.DmnModelRepository;
import org.acme.repository.utils.FirebaseUtils;

import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class DmnRepositoryImpl implements DmnModelRepository {

    @Override
    public Optional<byte[]> getDmnModelByName(String name) {

        Optional<Map<String, Object>> dataOptional = FirebaseUtils.getFirestoreDocByUniqueField(
                CollectionNames.DMN_COLLECTION,
                FieldNames.DMN_NAME,
                name);

        if (dataOptional.isEmpty()){
            return Optional.empty();
        }

        Map<String, Object> data = dataOptional.get();

        Optional<Boolean> isPublishedOptional = FirebaseUtils.getOptionalField(data, FieldNames.IS_PUBLISHED, Boolean.class);
        Optional<String> dmnPathOptional = FirebaseUtils.getOptionalField(data, FieldNames.DMN_PATH, String.class);

        if (dmnPathOptional.isEmpty() || isPublishedOptional.isEmpty() || !isPublishedOptional.get()) {
            return Optional.empty();
        }

        String dmnPath = dmnPathOptional.get();

        return FirebaseUtils.getFileFromStorage(dmnPath);
    }
}
