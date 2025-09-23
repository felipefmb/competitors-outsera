package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.MovieCsvSource;
import org.springframework.stereotype.Component;

@Component
public class LoadMoviesFromCSVUseCase {

    private final MovieCsvSource movieCsvSource;
    private final MovieUseCase movieUseCase;

    public LoadMoviesFromCSVUseCase(MovieCsvSource movieCsvSource, MovieUseCase movieUseCase) {
        this.movieCsvSource = movieCsvSource;
        this.movieUseCase = movieUseCase;
    }

    public void execute(String fileName) {
        var movies = movieCsvSource.load(fileName);
        movieUseCase.saveWinnersMovies(movies);
    }

}
