package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUseCase {

    private final ProducerService producerService;

    private final ProducerMapper mapper;

    public ProducerUseCase(ProducerService producerService) {
        this.producerService = producerService;
        this.mapper = new ProducerMapper();
    }

    public Producer save(Producer producer) {
        return producerService.save(producer);

    }

    public List<Producer> findProducersWithMultipleMovies() {
        Log.info("Finding producers");
        List<ProducerEntity> producers = new ArrayList<>(producerService.findAll());
        producers.removeIf(p -> p.getMovies().size() <= 1);
        if (producers.isEmpty()) return List.of();
        Log.info("Producers founded");
        return mapper.toDomains(producers);
    }
}
