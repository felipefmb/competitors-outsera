package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepositoryJpa extends JpaRepository<MovieEntity, Long> {

}
