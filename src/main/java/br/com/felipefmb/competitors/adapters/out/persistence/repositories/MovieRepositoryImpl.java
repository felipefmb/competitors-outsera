package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MoviePersistenceMapper;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MovieRepositoryImpl implements MovieRepository {

    private final MovieRepositoryJpa repository;

    public MovieRepositoryImpl(MovieRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Movie save(Movie movie) {
        var entity = MoviePersistenceMapper.toEntity(movie);
        if (Objects.isNull(entity)) return null;
        entity = repository.save(entity);
        return MoviePersistenceMapper.toDomain(entity);
    }

    @Override
    public List<Movie> findAll() {
        var entities = repository.findAll();
        return MoviePersistenceMapper.toDomain(entities);
    }
}
