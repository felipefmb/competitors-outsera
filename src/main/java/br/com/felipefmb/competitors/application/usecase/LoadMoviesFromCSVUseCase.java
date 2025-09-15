package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.in.MovieCsvSource;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadMoviesFromCSVUseCase {

    private final MovieCsvSource csvSource;
    private final MovieUseCase movieUseCase;

    public LoadMoviesFromCSVUseCase(MovieCsvSource csvSource, MovieUseCase movieUseCase) {
        this.csvSource = csvSource;
        this.movieUseCase = movieUseCase;
    }

    public List<Movie> execute() {
        var movies = csvSource.loadMovies();
        return movieUseCase.save(movies);
    }

}
