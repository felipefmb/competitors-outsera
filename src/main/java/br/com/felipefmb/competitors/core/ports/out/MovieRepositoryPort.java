package br.com.felipefmb.competitors.core.ports.out;

import br.com.felipefmb.competitors.core.domain.models.Movie;

import java.util.Collection;
import java.util.List;

public interface MovieRepositoryPort {
    void saveAll(Collection<Movie> movies);

    List<Movie> findAll();

}
