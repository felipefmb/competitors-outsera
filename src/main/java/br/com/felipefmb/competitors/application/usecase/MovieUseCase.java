package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

}
