package org.acme.repository;

import org.acme.model.Screener;

import java.util.Optional;

public interface ScreenerRepository {
    public Optional<Screener> getScreenerByName(String name);
}
