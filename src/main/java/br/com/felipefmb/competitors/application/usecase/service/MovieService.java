package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MovieMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.MovieRepository;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.ProducerRepository;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = new MovieMapper();
    }

    public Movie save(Movie movie) {
        var entity = movieMapper.toEntity(movie);
        entity = movieRepository.save(entity);
        return movieMapper.toDomain(entity);
    }
}
