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
        Movie movie = new Movie();
        movie.setId(entity.getId());
        movie.setReleaseYear(entity.getReleaseYear());
        movie.setTitle(entity.getTitle());
        movie.setStudios(studioMapper.toDomainsListFromSet(entity.getMovieStudios()));
        movie.setWinner(entity.isWinner());
        return movie;
    }

    @Override
    public MovieEntity toEntity(Movie domain) {
        MovieEntity entity = new MovieEntity();
        entity.setId(domain.getId());
        entity.setReleaseYear(domain.getReleaseYear());
        entity.setTitle(domain.getTitle());
        entity.setMovieStudios(studioMapper.toEntityListFromSet(domain.getStudios()));
        entity.setWinner(domain.isWinner());
        return entity;
    }
}
