package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.MovieMapper;
import br.com.felipefmb.competitors.application.usecase.service.MovieService;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Studio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class MovieUseCase {

    private final MovieService movieService;
    private final StudioUseCase studioUseCase;
    private final ProducerUseCase producerUseCase;
    private final MovieMapper mapper;

    public MovieUseCase(MovieService movieService, StudioUseCase studioUseCase, ProducerUseCase producerUseCase) {
        this.movieService = movieService;
        this.studioUseCase = studioUseCase;
        this.producerUseCase = producerUseCase;
        this.mapper = new MovieMapper();
    }


    @Transactional
    public void saveWinnersMovies(Collection<MovieCsvSourceDTO> moviesCsvDto) {
        Collection<MovieCsvSourceDTO> moviesCsvDtoWinners = filteringByWinners(moviesCsvDto);
        save(moviesCsvDtoWinners);
    }

    private Collection<MovieCsvSourceDTO> filteringByWinners(Collection<MovieCsvSourceDTO> moviesCsvDto) {
        return moviesCsvDto.stream()
                .filter(MovieCsvSourceDTO::winner)
                .toList();
    }

    @Transactional
    private void save(Collection<MovieCsvSourceDTO> moviesCsvDtoWinners) {
        Log.info("moviesCsvDtoWinners", moviesCsvDtoWinners);
        Set<Producer> cacheProducer = new HashSet<>();
        Set<Studio> cacheStudio = new HashSet<>();
        Set<Movie> cacheMovie = new HashSet<>();
        moviesCsvDtoWinners.forEach(csv -> {
            generateStudios(csv, cacheStudio);
            generateMovie(csv, cacheStudio, cacheMovie);
        });
        generateProducers(moviesCsvDtoWinners, cacheMovie, cacheProducer);
    }


    private void generateStudios(MovieCsvSourceDTO movieCsvSourceDTO, Set<Studio> cacheStudio) {
        var studios = movieCsvSourceDTO.studio().stream().map(studioName ->
                cacheStudio.stream()
                        .filter(a -> a.name().equalsIgnoreCase(studioName))
                        .findFirst()
                        .orElseGet(() -> new Studio(null, studioName))
        ).collect(Collectors.toSet());
        studios.stream().map(studioUseCase::save).forEach(cacheStudio::add);
    }

    private void generateMovie(MovieCsvSourceDTO movieCsvSourceDTO, Set<Studio> cacheStudio, Set<Movie> cacheMovie) {
        Set<Studio> studios = movieCsvSourceDTO.studio().stream().map(studio ->
                cacheStudio.stream()
                        .filter(x -> x.name().equalsIgnoreCase(studio))
                        .findFirst()
                        .orElseGet(() -> {
                            Studio foundStudio = studioUseCase.findByName(studio);
                            cacheStudio.add(foundStudio);
                            return foundStudio;
                        })
        ).collect(Collectors.toSet());
        Movie movie = new Movie(null, movieCsvSourceDTO.releaseYear(), movieCsvSourceDTO.title(), studios, movieCsvSourceDTO.winner());
        MovieEntity entity = movieService.save(movie);
        cacheMovie.add(mapper.toDomain(entity));
    }


    private void generateProducers(Collection<MovieCsvSourceDTO> moviesCsvDtoWinners, Set<Movie> cacheMovie, Set<Producer> cacheProducer) {
        Set<Producer> producers = new HashSet<>();
        moviesCsvDtoWinners.stream()
                .collect(Collectors.groupingBy(MovieCsvSourceDTO::producer))
                .forEach((producersNames, moviesByProducer) ->
                        producersNames.stream()
                                .filter(Objects::nonNull)
                                .filter(p -> !p.isBlank())
                                .forEach(producerName -> {
                                    Producer producerCache = cacheProducer.stream().filter(c -> c.name().equalsIgnoreCase(producerName)).findFirst().orElse(null);
                                    Set<Movie> movies = moviesByProducer.stream()
                                            .map(m -> cacheMovie.stream()
                                                    .filter(c -> c.title().equalsIgnoreCase(m.title()))
                                                    .findFirst()
                                                    .orElseGet(() -> {
                                                        MovieEntity movieEntity = movieService.findByTitle(m.title());
                                                        Movie movie = mapper.toDomain(movieEntity);
                                                        cacheMovie.add(movie);
                                                        return movie;
                                                    }))
                                            .collect(Collectors.toSet());
                                    if (Objects.nonNull(producerCache)) {
                                        producerCache.movies().addAll(movies);
                                    } else {
                                        Producer producer = new Producer(null, producerName, movies);
                                        producers.add(producer);
                                        cacheProducer.add(producer);
                                    }
                                })
                );
        producers.forEach(producerUseCase::save);
    }
}

