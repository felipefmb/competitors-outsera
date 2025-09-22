package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.ProducerRepository;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProducerService {

    private final ProducerRepository repository;
    private final ProducerMapper mapper;

    public ProducerService(ProducerRepository repository) {
        this.repository = repository;
        this.mapper = new ProducerMapper();
    }

    public Set<Producer> save(Set<Producer> producers) {
        Set<ProducerEntity> entities = mapper.toEntities(producers);
        repository.saveAll(entities);
        return mapper.toDomains(entities);
    }

    public Producer save(Producer producer) {
        ProducerEntity entity = mapper.toEntity(producer);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    public List<Producer> findByName(String name) {
        return repository.findByName(name).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
