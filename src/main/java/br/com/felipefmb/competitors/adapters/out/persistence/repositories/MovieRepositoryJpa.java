package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepositoryJpa extends JpaRepository<MovieEntity, Long> {

    @Query("select m from MovieEntity as m where m.winner = true")
    List<MovieEntity> findWinners();
}
