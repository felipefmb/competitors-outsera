package br.com.felipefmb.competitors.application.service;

import br.com.felipefmb.competitors.core.domain.models.Movie;
import br.com.felipefmb.competitors.core.ports.in.MoviePort;
import br.com.felipefmb.competitors.infrastructure.persistence.entities.MovieEntity;
import br.com.felipefmb.competitors.infrastructure.persistence.repositories.JpaMovieRepository;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MovieServicce implements MoviePort {

    private final JpaMovieRepository repository;

    public MovieServicce(JpaMovieRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveAll(Collection<Movie> movies) {
        if (movies == null || movies.isEmpty()) return;
        List<MovieEntity> entities = movies.stream()
                .map(m -> new MovieEntity(m.year(), m.title(), m.studios(), m.producers(), m.winner()))
                .toList();

        repository.saveAll(entities);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll().stream()
                .map(MovieServicce::toDomain)
                .sorted(Comparator.comparing(Movie::title))
                .toList();
    }

    private static Movie toDomain(MovieEntity e) {
        return new Movie(
                e.getYear(),
                e.getTitle(),
                e.getStudios(),
                e.getProducers(),
                e.isWinner()
        );
    }

}
