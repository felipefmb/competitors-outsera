package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.StudioMapper;
import br.com.felipefmb.competitors.application.usecase.service.StudioService;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Objects;

@Component
public class StudioUseCase {

    private final StudioService service;
    private final StudioMapper mapper;

    public StudioUseCase(StudioService service) {
        this.service = service;
        this.mapper = new StudioMapper();
    }

    public Studio save(Studio studio) {
        var entity = service.save(studio);
        return mapper.toDomain(entity);
    }

    public Studio findByName(String name) {
        var entities = service.findByName(name);
        if (Objects.isNull(entities)) {
            return null;
        }
        return mapper.toDomain(entities);
    }

    public Studio findById(BigInteger id) {
        StudioEntity entity = service.findById(id);
        return mapper.toDomain(entity);
    }
}
