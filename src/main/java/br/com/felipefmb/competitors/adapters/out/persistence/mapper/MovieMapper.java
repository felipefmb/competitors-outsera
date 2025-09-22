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
        return null;
    }
}
