package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.StudioMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.StudioRepository;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioService {

    private final StudioRepository repository;
    private final StudioMapper mapper;

    public StudioService(StudioRepository repository) {
        this.repository = repository;
        this.mapper = new StudioMapper();
    }

    public List<Studio> save(List<Studio> studios) {
        List<StudioEntity> entities = mapper.toEntities(studios);
        repository.saveAll(entities);
        return mapper.toDomain(entities);
    }

    public Studio save(Studio studio) {
        StudioEntity entity = mapper.toEntity(studio);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    public List<Studio> findByName(String name) {
        return repository.findByName(name).stream()
                .map(mapper::toDomain)
                .toList();
    }
}

