package br.com.felipefmb.competitors.infrastructure.persistence.repositories;

import br.com.felipefmb.competitors.infrastructure.persistence.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMovieRepository extends JpaRepository<MovieEntity, Long> {



}
