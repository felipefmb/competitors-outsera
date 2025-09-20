package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.MovieCsvSource;
import org.springframework.stereotype.Component;

@Component
public class LoadMoviesFromCSVUseCase {

    private final MovieCsvSource csvSource;
    private final MovieUseCase movieUseCase;

    public LoadMoviesFromCSVUseCase(MovieCsvSource csvSource, MovieUseCase movieUseCase) {
        this.csvSource = csvSource;
        this.movieUseCase = movieUseCase;
    }

    public void execute() {
        var movies = csvSource.load("movielist");
        movieUseCase.save(movies);
    }

}
