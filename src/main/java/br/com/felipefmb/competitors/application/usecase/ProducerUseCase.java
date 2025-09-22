package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ProducerUseCase {

    private final ProducerService producerService;

    private final ProducerMapper mapper;

    public ProducerUseCase(ProducerService producerService) {
        this.producerService = producerService;
        this.mapper = new ProducerMapper();
    }

    public Producer save(Producer producer) {
        ProducerEntity producerEntity = producerService.save(producer);
        return mapper.toDomain(producerEntity);
    }

    public List<Producer> findAll() {
        Log.info("Finding producers");
        List<ProducerEntity> producers = producerService.findAll();
        Log.info("Finding producers", producers);
        return mapper.toDomains(producers);
    }
}
