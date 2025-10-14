package br.com.felipefmb.competitors.application.usecase.service;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepositoryPort;
import br.com.felipefmb.competitors.domain.ports.out.ProducerRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final ProducerRepositoryPort repository;
    private final ProducerMapper mapper;
    private final MovieRepositoryPort movieRepository;

    public ProducerService(ProducerRepositoryPort repository, MovieRepositoryPort movieRepository) {
        this.repository = repository;
        this.mapper = new ProducerMapper();
        this.movieRepository = movieRepository;
    }

    public Producer save(Producer producer) {
        ProducerEntity entity = mapper.toEntity(producer);
        Set<MovieEntity> movies = entity.getMovies().stream()
                .map(f -> movieRepository.findById(f.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        entity.setMovies(movies);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }


    public List<ProducerEntity> findProducersWithMultipleMovies(Pageable pages) {
        return repository.findProducersWithMultipleMovies(pages);
    }
}
