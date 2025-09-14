package br.com.felipefmb.competitors.infrastructure.persistence.repositories;

import br.com.felipefmb.competitors.application.Log;
import br.com.felipefmb.competitors.core.domain.models.Movie;
import br.com.felipefmb.competitors.core.ports.out.MovieRepositoryPort;

import java.util.Collection;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepositoryPort {


    @Override
    public void saveAll(Collection<Movie> movies) {
        Log.info("Salvar todos");
    }

    @Override
    public List<Movie> findAll() {
        Log.info("Listar todos");
        return List.of();
    }

}
