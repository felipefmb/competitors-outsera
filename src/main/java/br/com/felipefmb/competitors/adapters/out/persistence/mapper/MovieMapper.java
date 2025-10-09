package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.model.Movie;

public class MovieMapper implements Mapper<Movie, MovieEntity> {

    private final StudioMapper studioMapper;

    public MovieMapper() {
        this.studioMapper = new StudioMapper();
    }

    @Override
    public Movie toDomain(MovieEntity entity) {
        return new Movie(
                entity.getId(),
                entity.getReleaseYear(),
                entity.getTitle(),
                studioMapper.toDomains(entity.getMovieStudios()),
                entity.isWinner()
        );
    }

    @Override
    public MovieEntity toEntity(Movie domain) {
        MovieEntity entity = new MovieEntity();
        entity.setId(domain.id());
        entity.setReleaseYear(domain.releaseYear());
        entity.setTitle(domain.title());
        entity.setMovieStudios(studioMapper.toEntities(domain.studios()));
        entity.setWinner(domain.winner());
        return entity;
    }
}
