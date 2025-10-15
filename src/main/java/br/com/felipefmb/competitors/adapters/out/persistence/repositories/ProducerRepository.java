package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.domain.ports.out.ProducerRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<ProducerEntity> findProducersWithMultipleMovies(Pageable pages) {
        Page<ProducerEntity> entities = producerRepositoryJpa.findProducersWithMultipleMovies(pages);
        while (entities.hasNext()) {
            pages = entities.nextPageable();
            entities = producerRepositoryJpa.findProducersWithMultipleMovies(pages);
        }
        return entities.getContent();
    }
}
