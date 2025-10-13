package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.domain.ports.out.ProducerRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProducerRepository implements ProducerRepositoryPort {

    private final ProducerRepositoryJpa producerRepositoryJpa;

    public ProducerRepository(ProducerRepositoryJpa producerRepositoryJpa) {
        this.producerRepositoryJpa = producerRepositoryJpa;
    }

    @Override
    @Transactional
    public ProducerEntity save(ProducerEntity entity) {
        return producerRepositoryJpa.save(entity);
    }

    @Override
    public Page<ProducerEntity> findAll(Pageable pagination) {
        return producerRepositoryJpa.findAll(pagination);
    }
}
