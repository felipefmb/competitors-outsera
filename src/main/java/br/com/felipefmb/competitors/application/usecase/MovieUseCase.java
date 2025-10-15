package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.application.usecase.service.MovieService;
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

    public MovieUseCase(MovieService movieService, StudioUseCase studioUseCase, ProducerUseCase producerUseCase) {
        this.movieService = movieService;
        this.studioUseCase = studioUseCase;
        this.producerUseCase = producerUseCase;
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
                        .filter(a -> a.getName().equalsIgnoreCase(studioName))
                        .findFirst()
                        .orElseGet(() -> {
                            Studio studio = new Studio();
                            studio.setId(null);
                            studio.setName(studioName);
                            return studio;
                        })
        ).collect(Collectors.toSet());
        studios.stream().map(studioUseCase::save).forEach(cacheStudio::add);
    }

    private void generateMovie(MovieCsvSourceDTO movieCsvSourceDTO, Set<Studio> cacheStudio, Set<Movie> cacheMovie) {
        List<Studio> studios = movieCsvSourceDTO.studio().stream().map(studio ->
                cacheStudio.stream()
                        .filter(x -> x.getName().equalsIgnoreCase(studio))
                        .findFirst()
                        .orElseGet(() -> {
                            Studio foundStudio = studioUseCase.findByName(studio);
                            cacheStudio.add(foundStudio);
                            return foundStudio;
                        })
        ).toList();
        Movie movie = new Movie();
        movie.setId(null);
        movie.setReleaseYear(movieCsvSourceDTO.releaseYear());
        movie.setTitle(movieCsvSourceDTO.title());
        movie.setStudios(studios);
        movie.setWinner(movieCsvSourceDTO.winner());
        movie = movieService.save(movie);
        cacheMovie.add(movie);
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
                                    Producer producerCache = cacheProducer.stream().filter(c -> c.getName().equalsIgnoreCase(producerName)).findFirst().orElse(null);
                                    LinkedList<Movie> movies = moviesByProducer.stream()
                                            .map(m -> cacheMovie.stream()
                                                    .filter(c -> c.getTitle().equalsIgnoreCase(m.title()))
                                                    .findFirst()
                                                    .orElseGet(() -> {
                                                        Movie movie = findByTitle(m.title());
                                                        cacheMovie.add(movie);
                                                        return movie;
                                                    }))
                                            .collect(Collectors.toCollection(LinkedList::new));
                                    if (Objects.nonNull(producerCache)) {
                                        producerCache.getMovies().addAll(movies);
                                    } else {
                                        Producer producer = new Producer();
                                        producer.setId(null);
                                        producer.setName(producerName);
                                        producer.setMovies(movies);
                                        producers.add(producer);
                                        cacheProducer.add(producer);
                                    }
                                })
                );
        producers.forEach(producerUseCase::save);
    }

    public Movie findByTitle(String title) {
        return movieService.findByTitle(title);
    }

}

