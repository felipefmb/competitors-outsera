package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public List<MovieEntity> findAll(Pageable page) {
        Page<MovieEntity> entities = movieRepositoryJpa.findAll(page);
        while (entities.hasNext()) {
            page = entities.nextPageable();
            entities = movieRepositoryJpa.findAll(page);
        }
        return entities.getContent();
    }
}
