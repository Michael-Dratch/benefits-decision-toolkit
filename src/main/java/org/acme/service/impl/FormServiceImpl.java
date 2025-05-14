package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.acme.controller.FormResource;
import org.acme.model.Form;
import org.acme.repository.FormModelRepository;
import org.acme.service.FormService;

import java.util.Map;

@ApplicationScoped
public class FormServiceImpl implements FormService {

    @Inject
    FormModelRepository formModelRepository;

    public Form getPublishedFormModel(String screenerName) {
        Form form = formModelRepository.getFormModel(screenerName);

        if (form.getModel().isEmpty()) {
            String responseMessage = String.format("Form %s was not found", screenerName);
            throw new NotFoundException(responseMessage);
        }

        if (!form.isPublished()) {
            String responseMessage = String.format("Form %s was not found", screenerName);
            throw new NotFoundException(responseMessage);
        }

        return form;
    }
}
