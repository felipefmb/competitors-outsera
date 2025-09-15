package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.Collection;
import java.util.List;

public interface MovieRepository {
    Movie save(Movie movie);
    List<Movie> findAll();
}
