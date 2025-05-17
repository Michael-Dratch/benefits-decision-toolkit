package org.acme.repository;

import org.acme.model.Screener;

import java.io.InputStream;
import java.util.Optional;

public interface ScreenerRepository {
    public Optional<Screener> getScreenerByName(String name);

    public Optional<InputStream> getScreenerDmnModelByName(String name);
}
