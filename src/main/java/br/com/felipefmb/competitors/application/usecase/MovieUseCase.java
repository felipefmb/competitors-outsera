package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class MovieUseCase {

    private final MovieRepository repository;

    public MovieUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Movie save(Movie domain) {
        return repository.save(domain);
    }

    @Transactional
    public List<Movie> save(List<Movie> domain) {
        return repository.save(domain);
    }

    private List<Movie> findMovies() {
        try {
            Log.info("Starting query of all films with winners");
            return repository.findWinners();
        } catch (Exception e) {
            throw new GoldenRaspberryAwardsException(e);
        }
    }


    public List<Movie> getMovies() {
        var movies = findMovies();
        if (Objects.isNull(movies) || movies.isEmpty()) {
            throw new NotFoundException("Records not found");
        }
        return movies;
    }


}
