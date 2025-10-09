package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MovieMapper;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepositoryPort movieRepositoryPort;
    private final StudioService studioService;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepositoryPort movieRepositoryPort, StudioService studioService) {
        this.movieRepositoryPort = movieRepositoryPort;
        this.studioService = studioService;
        this.movieMapper = new MovieMapper();
    }

    public Movie save(Movie movie) {
        var entity = movieMapper.toEntity(movie);
        entity.setMovieStudios(entity.getMovieStudios().stream().map(e -> studioService.findById(e.getId())).collect(Collectors.toSet()));
        entity = movieRepositoryPort.save(entity);
        return movieMapper.toDomain(entity);
    }

    public Movie findByTitle(String title) {
        MovieEntity entity = movieRepositoryPort.findByTitle(title);
        return movieMapper.toDomain(entity);
    }

    public Movie findById(BigInteger id) {
        Optional<MovieEntity> optionalMovieEntity = movieRepositoryPort.findById(id);
        return optionalMovieEntity.map(movieMapper::toDomain).orElse(null);
    }
}
