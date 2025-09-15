package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.in.MovieCsvSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadMoviesFromCSVUseCase {

    private final MovieCsvSource csvSource;


    public LoadMoviesFromCSVUseCase(MovieCsvSource csvSource) {
        this.csvSource = csvSource;
    }

    public List<Movie> execute() {
        return csvSource.loadMovies();
    }

}
