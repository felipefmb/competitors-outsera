package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class MovieRepository implements MovieRepositoryPort {

    private final MovieRepositoryJpa movieRepositoryJpa;

    public MovieRepository(MovieRepositoryJpa movieRepositoryJpa) {
        this.movieRepositoryJpa = movieRepositoryJpa;
    }

    @Override
    public Optional<MovieEntity> findById(BigInteger id) {
        return movieRepositoryJpa.findById(id);
    }

    @Override
    public MovieEntity findByTitle(String title) {
        return movieRepositoryJpa.findByTitle(title);
    }

    @Override
    @Transactional
    public MovieEntity save(MovieEntity entity) {
        return movieRepositoryJpa.save(entity);
    }
}
