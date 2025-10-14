package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface ProducerRepositoryJpa extends JpaRepository<ProducerEntity, BigInteger> {

    @Query("SELECT p FROM ProducerEntity p JOIN p.movies m GROUP BY p HAVING COUNT(m) > 1")
    Page<ProducerEntity> findProducersWithMultipleMovies(Pageable pages);
}
