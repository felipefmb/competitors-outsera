package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public interface ProducerRepository extends JpaRepository<ProducerEntity, BigInteger> {

    List<ProducerEntity> findByName(String name);
}
