package br.com.felipefmb.competitors.domain.ports.out;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProducerRepositoryPort {
    ProducerEntity save(ProducerEntity entity);

    List<ProducerEntity> findAll();

    List<ProducerEntity> findProducersWithMultipleMovies(Pageable pages);
}
