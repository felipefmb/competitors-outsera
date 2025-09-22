package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.ProducerRepository;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProducerService {

    private final ProducerRepository repository;
    private final ProducerMapper mapper;

    public ProducerService(ProducerRepository repository) {
        this.repository = repository;
        this.mapper = new ProducerMapper();
    }

    public List<ProducerEntity> save(Set<Producer> producers) {
        Set<ProducerEntity> entities = mapper.toEntities(producers);
        return repository.saveAll(entities);
    }

    public ProducerEntity save(Producer producer) {
        ProducerEntity entity = mapper.toEntity(producer);
        return repository.save(entity);
    }

    public List<ProducerEntity> findByName(String name) {
        return repository.findByName(name);
    }

    public ProducerEntity findById(BigInteger id) {
        Optional<ProducerEntity> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<ProducerEntity> findAll() {
        return repository.findAll();
    }
}
