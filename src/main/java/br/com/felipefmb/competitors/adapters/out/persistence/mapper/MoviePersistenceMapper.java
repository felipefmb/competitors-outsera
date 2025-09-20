package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MoviePersistenceMapper {

    private MoviePersistenceMapper() {}

    public static Movie toDomain(MovieEntity entity) {
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

    public static List<Movie> toDomain(List<MovieEntity> entities) {
        if (Objects.isNull(entities)) return Collections.emptyList();
        return entities.stream().map(MoviePersistenceMapper::toDomain).toList();
    }

    public static MovieEntity toEntity(Movie domain) {
        if (Objects.isNull(domain)) return null;
        return new MovieEntity(
                domain.year(),
                domain.title(),
                domain.studio(),
                domain.producer(),
                domain.winner()
        );
    }

    public static List<MovieEntity> toEntity(List<Movie> domains) {
        if (Objects.isNull(domains)) return Collections.emptyList();
        return domains.stream().map(MoviePersistenceMapper::toEntity).toList();
    }

}
