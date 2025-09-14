package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;

import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieRepositoryJpa repository;

    public MovieRepositoryImpl(MovieRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Movie save(Movie movie) {
        return repository.save();
    }

    @Override
    public List<Movie> findAll() {
        return List.of();
    }
}
