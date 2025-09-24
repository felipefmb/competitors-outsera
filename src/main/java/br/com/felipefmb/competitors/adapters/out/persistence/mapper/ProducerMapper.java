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
        return new Producer(
                entity.getId(),
                entity.getName(),
                movieMapper.toDomains(entity.getMovies())
        );
    }

    @Override
    public ProducerEntity toEntity(Producer domain) {
        return new ProducerEntity(
                domain.id(),
                domain.name(),
                movieMapper.toEntities(domain.movies())
        );
    }

}
