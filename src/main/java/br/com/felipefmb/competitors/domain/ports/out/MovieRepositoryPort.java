package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;

import java.math.BigInteger;
import java.util.Optional;

public interface MovieRepositoryPort {
    Optional<MovieEntity> findById(BigInteger id);

    MovieEntity findByTitle(String title);

    MovieEntity save(MovieEntity entity);
}
