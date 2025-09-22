package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.model.Movie;

public class MovieMapper implements Mapper<Movie, MovieEntity> {

    private final StudioMapper studioMapper;
    private final ProducerMapper producerMapper;

    public MovieMapper() {
        this.studioMapper = new StudioMapper();
        this.producerMapper = new ProducerMapper();
    }

    @Override
    public Movie toDomain(MovieEntity entity) {
        return null;
    }

    @Override
    public MovieEntity toEntity(Movie domain) {
        return new MovieEntity(
                domain.id(),
                domain.releaseYear(),
                domain.title(),
                studioMapper.toEntities(domain.studios()),
                producerMapper.toEntities(domain.producers()),
                domain.winner()
        );
    }
}
