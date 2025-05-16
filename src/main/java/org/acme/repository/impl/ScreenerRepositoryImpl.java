package org.acme.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.constants.FieldNames;
import org.acme.model.Screener;

import org.acme.repository.ScreenerRepository;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ScreenerRepositoryImpl implements ScreenerRepository {

    @Override
    public Optional<Screener> getScreenerByName(String name) {

        Optional<Map<String, Object>> dataOptional = getScreenerDataFromFireStore(name);

        if (dataOptional.isEmpty()){
            return Optional.empty();
        }

        Map<String, Object> data = dataOptional.get();

        Optional<Boolean> isPublishedOptional = getOptionalField(data, FieldNames.IS_PUBLISHED, Boolean.class);
        Optional<String> formPathOptional = getOptionalField(data, FieldNames.FORM_PATH, String.class);

        Screener screener = new Screener();

        isPublishedOptional.ifPresent(screener::setPublished);

        if (formPathOptional.isPresent()){
            String formPath = formPathOptional.get();
            Map<String, Object> formModel = getFormModelFromStorage(formPath);
            screener.setFormModel(formModel);
        }
        return Optional.of(screener);
    }

    private Optional<Map<String, Object>> getScreenerDataFromFireStore(String name) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = db.collection("screener")
                    .whereEqualTo("name", name)
                    .limit(1)
                    .get();
            List<QueryDocumentSnapshot> documents;
            documents = query.get().getDocuments();

            if (documents.isEmpty()) {
                return Optional.empty();
            }
            QueryDocumentSnapshot document = documents.getFirst();

            return Optional.of(document.getData());

            }catch(Exception e){
                Log.error("Error fetching screener document from firestore: ", e);
                return Optional.empty();
            }
    }

    private static Map<String, Object> getFormModelFromStorage(String filePath) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.get(filePath);

            if (blob == null || !blob.exists()) {
                throw new RuntimeException("File not found in Firebase Storage");
            }

            byte[] content = blob.getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> formModel = mapper.readValue(new ByteArrayInputStream(content), new TypeReference<Map<String, Object>>() {
            });

            return formModel;

        } catch (Exception e){
            Log.error("Error fetching form model from firebase storage: ", e);
            return new HashMap<>();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptionalField(Map<String, Object> doc, String fieldName, Class<T> clazz) {
        Object value = doc.get(fieldName);
        if (clazz.isInstance(value)) {
            return Optional.of((T) value);
        } else {
            return Optional.empty();
        }
    }
}

