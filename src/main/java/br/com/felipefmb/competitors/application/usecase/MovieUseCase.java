package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Movie> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new GoldenRaspberryAwardsException(e);
        }
    }

    public List<Movie> findWinners() {
        try {
            return repository.findWinners();
        } catch (Exception e) {
            throw new GoldenRaspberryAwardsException(e);
        }
    }


}
