package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.application.usecase.service.StudioService;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.springframework.stereotype.Component;

@Component
public class StudioUseCase {

    private final StudioService service;

    public StudioUseCase(StudioService service) {
        this.service = service;
    }

    public Studio save(Studio studio) {
        return service.save(studio);
    }

    public Studio findByName(String name) {
        return service.findByName(name);
    }
}
