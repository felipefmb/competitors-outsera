package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Pageable.ofSize;

@Component
public class ProducerUseCase {

    private final ProducerService producerService;

    private final ProducerMapper mapper;

    private static final int ITEMS_PAGE = 100;

    public ProducerUseCase(ProducerService producerService) {
        this.producerService = producerService;
        this.mapper = new ProducerMapper();
    }

    public Producer save(Producer producer) {
        return producerService.save(producer);

    }

    public List<Producer> findProducersWithMultipleMovies() {
        Log.info("Finding producers");
        List<Producer> producers = new ArrayList<>(producerService.findProducersWithMultipleMovies(ofSize(ITEMS_PAGE)));
        if (producers.isEmpty()) return List.of();
        Log.info("Producers founded");
        return producers;
    }
}
