package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProducerRepositoryPort {
    ProducerEntity save(ProducerEntity entity);

    Page<ProducerEntity> findAll(Pageable pagination);
}
