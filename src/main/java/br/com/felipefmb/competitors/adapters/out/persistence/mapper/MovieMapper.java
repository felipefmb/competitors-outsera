package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.model.Movie;

public class MovieMapper implements Mapper<Movie, MovieEntity> {


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
                new StudioMapper().toEntities(domain.studios()),
                new ProducerMapper().toEntities(domain.producers()),
                domain.winner()
        );
    }
}
