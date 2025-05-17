package org.acme.repository;

import java.io.InputStream;
import java.util.Optional;

public interface DmnModelRepository {
    Optional<byte[]> getDmnModelByName(String name);
}
