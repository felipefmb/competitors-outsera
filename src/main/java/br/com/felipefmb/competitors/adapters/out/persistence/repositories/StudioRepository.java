package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface StudioRepository extends JpaRepository<StudioEntity, BigInteger> {

    StudioEntity findByName(String name);
}
