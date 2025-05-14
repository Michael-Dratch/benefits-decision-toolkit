package org.acme.repository;

import org.acme.model.Form;

public interface FormModelRepository {
    public Form getFormModel(String name);
}
