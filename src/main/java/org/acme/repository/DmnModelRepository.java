package org.acme.repository;

import java.io.InputStream;

public interface DmnModelRepository {
    InputStream getDmnModel(String name);
}
