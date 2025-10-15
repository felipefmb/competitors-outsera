package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MovieRepositoryPort {
    Optional<MovieEntity> findById(BigInteger id);

    MovieEntity findByTitle(String title);

    MovieEntity save(MovieEntity entity);

    List<MovieEntity> findAll(Pageable page);
}
