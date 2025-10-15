package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.*;
import br.com.felipefmb.competitors.domain.model.Producer;

public class ProducerMapper implements Mapper<Producer, ProducerEntity> {

    private final MovieMapper movieMapper;

    public ProducerMapper() {
        this.movieMapper = new MovieMapper();
    }

    @Override
    public Producer toDomain(ProducerEntity entity) {
        Producer domain = new Producer();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setMovies(movieMapper.toDomainsListFromSet(entity.getMovies()));
        return domain;
    }

    @Override
    public ProducerEntity toEntity(Producer domain) {
        ProducerEntity entity = new ProducerEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setMovies(movieMapper.toEntityListFromSet(domain.getMovies()));
        return entity;
    }

}
