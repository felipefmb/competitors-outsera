package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface MovieRepository extends JpaRepository<MovieEntity, BigInteger> {
    MovieEntity findByTitle(String title);
}
