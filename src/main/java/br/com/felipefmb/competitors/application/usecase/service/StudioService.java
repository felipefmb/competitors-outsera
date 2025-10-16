package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.StudioMapper;
import br.com.felipefmb.competitors.domain.model.Studio;
import br.com.felipefmb.competitors.domain.ports.out.StudioRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Service
public class StudioService {

    private final StudioRepositoryPort repository;
    private final StudioMapper mapper;

    public StudioService(StudioRepositoryPort repository) {
        this.repository = repository;
        this.mapper = new StudioMapper();
    }

    public List<Studio> save(List<Studio> studios) {
        List<StudioEntity> entities = mapper.toEntitiesListFromList(studios);
        entities = repository.saveAll(entities);
        return mapper.toDomains(entities);
    }

    public Studio save(Studio studio) {
        StudioEntity entity = mapper.toEntity(studio);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    public Studio findByName(String name) {
        var entity = repository.findByName(name);
        if (Objects.isNull(entity)) {
            return null;
        }
        return mapper.toDomain(entity);
    }

    public Studio findById(BigInteger id) {
        return repository.findById(id).map(mapper::toDomain).orElse(null);
    }
}

