package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class ProducerUseCase {

    private final ProducerService producerService;

    public ProducerUseCase(ProducerService producerService) {
        this.producerService = producerService;
    }

    public Set<Producer> save(Collection<MovieCsvSourceDTO> movies) {
        var producers = movies.stream()
                .collect(Collectors.groupingBy(MovieCsvSourceDTO::producer))
                .entrySet()
                .stream()
                .flatMap(x -> Stream.of(x.getKey()))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(x -> !x.isBlank())
                .map(StringUtils::trim)
                .distinct()
                .map(name -> new Producer(null, name, null))
                .collect(Collectors.toSet());
        return producerService.save(producers);
    }

    public Producer save(Producer producer) {
        return producerService.save(producer);
    }

    public List<Producer> findByName(String name) {
        var producers = producerService.findByName(name);
        if (Objects.isNull(producers) || producers.isEmpty()) {
            return Collections.emptyList();
        }
        return producers;
    }
}
