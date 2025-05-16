package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.acme.model.Screener;
import org.acme.repository.ScreenerRepository;
import org.acme.service.ScreenerService;

import java.util.Optional;

@ApplicationScoped
public class ScreenerServiceImpl implements ScreenerService {

    @Inject
    ScreenerRepository screenerRepository;

    public Screener getPublishedScreener(String screenerName) {

        Optional<Screener> screenerOptional = screenerRepository.getScreenerByName(screenerName);

        String notFoundResponseMessage = String.format("Form %s was not found", screenerName);
        if (screenerOptional.isEmpty()){
            throw new NotFoundException(notFoundResponseMessage);
        }

        Screener screener = screenerOptional.get();
        if (screener.getFormModel().isEmpty() || !screener.isPublished()) {
            throw new NotFoundException(notFoundResponseMessage);
        }

        return screener;
    }
}
