package org.acme.model;

import java.util.HashMap;
import java.util.Map;

public class Form {
    private Map<String, Object> model;
    private boolean isPublished;

    public Form(Map<String, Object> model, boolean isPublished){
        this.model = model;
        this.isPublished = isPublished;
    }

    public Form(){
        this.model = new HashMap<>();
        this.isPublished = false;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public boolean isPublished() {
        return isPublished;
    }
}
