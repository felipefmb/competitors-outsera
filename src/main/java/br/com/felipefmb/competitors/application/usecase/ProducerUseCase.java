package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.exceptions.ProducerException;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class ProducerUseCase {

    private final ProducerService producerService;

    private final ProducerMapper mapper;

    public ProducerUseCase(ProducerService producerService) {
        this.producerService = producerService;
        this.mapper = new ProducerMapper();
    }

    public List<Producer> save(Collection<MovieCsvSourceDTO> movies) {
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
                .map(producerName -> new Producer(null, producerName))
                .collect(Collectors.toSet());
        var producerEntity = producerService.save(producers);
        if (Objects.isNull(producerEntity) || producerEntity.isEmpty()) {
            throw new ProducerException("Failed on save producer");
        }
        return mapper.toDomains(producerEntity);
    }

    public Producer save(Producer producer) {
        ProducerEntity producerEntity = producerService.save(producer);
        return mapper.toDomain(producerEntity);
    }

    public List<Producer> findByName(String name) {
        var producerEntities = producerService.findByName(name);
        if (Objects.isNull(producerEntities) || producerEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.toDomains(producerEntities);
    }

    public Producer findById(BigInteger id) {
        ProducerEntity entity = producerService.findById(id);
        return mapper.toDomain(entity);
    }

    public List<Producer> findAll() {
        Log.info("Finding producers");
        List<ProducerEntity> producers = producerService.findAll();
        Log.info("Finding producers", producers);
        return mapper.toDomains(producers);
    }
}
