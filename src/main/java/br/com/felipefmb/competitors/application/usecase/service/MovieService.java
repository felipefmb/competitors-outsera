package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MovieMapper;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepositoryPort movieRepositoryPort;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepositoryPort movieRepositoryPort) {
        this.movieRepositoryPort = movieRepositoryPort;
        this.movieMapper = new MovieMapper();
    }

    public Movie save(Movie movie) {
        var entity = movieMapper.toEntity(movie);
        entity = movieRepositoryPort.save(entity);
        return movieMapper.toDomain(entity);
    }

    public Movie findByTitle(String title) {
        MovieEntity entity = movieRepositoryPort.findByTitle(title);
        return movieMapper.toDomain(entity);
    }

}
