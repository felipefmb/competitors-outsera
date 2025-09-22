package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.StudioMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.StudioRepository;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class StudioService {

    private final StudioRepository repository;
    private final StudioMapper mapper;

    public StudioService(StudioRepository repository) {
        this.repository = repository;
        this.mapper = new StudioMapper();
    }

    public List<StudioEntity> save(List<Studio> studios) {
        List<StudioEntity> entities = mapper.toEntities(studios);
        return repository.saveAll(entities);
    }

    public StudioEntity save(Studio studio) {
        StudioEntity entity = mapper.toEntity(studio);
        return repository.save(entity);
    }

    public StudioEntity findByName(String name) {
        return repository.findByName(name);
    }

    public StudioEntity findById(BigInteger id) {
        return repository.findById(id).orElse(null);
    }
}

