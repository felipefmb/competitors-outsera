package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import br.com.felipefmb.competitors.domain.ports.out.StudioRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MovieRepository implements MovieRepositoryPort {

    private final MovieRepositoryJpa movieRepositoryJpa;
    private final StudioRepositoryPort studioRepositoryPort;

    public MovieRepository(MovieRepositoryJpa movieRepositoryJpa, StudioRepositoryPort studioRepositoryPort) {
        this.movieRepositoryJpa = movieRepositoryJpa;
        this.studioRepositoryPort = studioRepositoryPort;
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
        Set<StudioEntity> studioEntities = entity.getMovieStudios().stream()
                .map(e -> studioRepositoryPort.findById(e.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        entity.setMovieStudios(studioEntities);
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
