package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;

import java.util.List;

public interface ProducerRepositoryPort {
    ProducerEntity save(ProducerEntity entity);

    List<ProducerEntity> findAll();
}
