package br.com.felipefmb.competitors.domain.ports.in;

import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.Collection;
import java.util.List;

public interface MoviePort {

    void saveAll(Collection<Movie> movies);

    List<Movie> findAll();

}
