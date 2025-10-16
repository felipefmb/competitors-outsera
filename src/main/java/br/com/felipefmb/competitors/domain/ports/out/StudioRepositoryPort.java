package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface StudioRepositoryPort {

    List<StudioEntity> saveAll(List<StudioEntity> entities);

    StudioEntity save(StudioEntity entity);

    StudioEntity findByName(String name);

    Optional<StudioEntity> findById(BigInteger id);
}
