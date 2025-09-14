package br.com.felipefmb.competitors.core.ports.in;

import br.com.felipefmb.competitors.core.domain.models.Movie;

import java.util.Collection;
import java.util.List;

public interface MoviePort {

    void saveAll(Collection<Movie> movies);

    List<Movie> findAll();

}
