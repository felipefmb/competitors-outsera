package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.Objects;

public class MoviePersistenceMapper {

    public Movie toDomain(MovieEntity entity) {
        if (Objects.isNull(entity)) return null;
        return new Movie(
                entity.getId(),
                entity.getYear(),
                entity.getTitle(),
                entity.getStudio(),
                entity.getProducer(),
                entity.isWinner()
        );
    }

    public MovieEntity toEntity(Movie d) {
        if (d == null) return null;
        return new MovieEntity(
                d.year(),
                d.title(),
                d.studio(),
                d.producer(),
                d.winner()
        );
    }

}
