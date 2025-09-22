package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.*;
import br.com.felipefmb.competitors.domain.model.Producer;

public class ProducerMapper implements Mapper<Producer, ProducerEntity> {

    @Override
    public Producer toDomain(ProducerEntity entity) {
        return new Producer(
                entity.getId(),
                entity.getName(),
                new MovieMapper().toDomains(entity.getMovies())
        );
    }

    @Override
    public ProducerEntity toEntity(Producer domain) {
        return new ProducerEntity(
                domain.id(),
                domain.name(),
                new MovieMapper().toEntities(domain.movies())
        );
    }

}
