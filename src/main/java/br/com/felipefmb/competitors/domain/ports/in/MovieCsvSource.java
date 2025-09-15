package br.com.felipefmb.competitors.domain.ports.in;

import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.List;

public interface MovieCsvSource {
    List<Movie> loadMovies();
}
