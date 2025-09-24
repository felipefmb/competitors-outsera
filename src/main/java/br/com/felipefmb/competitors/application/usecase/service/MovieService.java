package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MovieMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.MovieRepository;
import br.com.felipefmb.competitors.domain.model.Movie;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final StudioService studioService;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, StudioService studioService) {
        this.movieRepository = movieRepository;
        this.studioService = studioService;
        this.movieMapper = new MovieMapper();
    }

    public MovieEntity save(Movie movie) {
        var entity = movieMapper.toEntity(movie);
        entity.setMovieStudios(entity.getMovieStudios().stream().map(e -> studioService.findById(e.getId())).collect(Collectors.toSet()));
        return movieRepository.save(entity);
    }

    public List<MovieEntity> findAll() {
        return movieRepository.findAll();
    }

    public MovieEntity findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public MovieEntity findById(BigInteger id) {
        return movieRepository.findById(id).orElse(null);
    }
}
