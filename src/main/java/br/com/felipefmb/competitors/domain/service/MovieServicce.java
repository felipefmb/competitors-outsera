package br.com.felipefmb.competitors.domain.service;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.in.MoviePort;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.MovieRepositoryJpa;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MovieServicce implements MoviePort {

    private final MovieRepositoryJpa repository;

    public MovieServicce(MovieRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveAll(Collection<Movie> movies) {
        if (movies == null || movies.isEmpty()) return;
        List<MovieEntity> entities = movies.stream()
                .map(m -> new MovieEntity(m.year(), m.title(), m.studio(), m.producer(), m.winner()))
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

    private static Movie toDomain(MovieEntity entity) {
        return new Movie(
                entity.getId(),
                entity.getYear(),
                entity.getTitle(),
                entity.getStudio(),
                entity.getProducer(),
                entity.isWinner()
        );
    }

}
