package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
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
        Log.info("moviesCsvDtoWinners", moviesCsvDtoWinners);
        Set<Producer> cacheProducer = new HashSet<>();
        Set<Studio> cacheStudio = new HashSet<>();
        moviesCsvDtoWinners.forEach(csv -> {
            generateProducers(csv, cacheProducer);
            generateStudios(csv, cacheStudio);
            generateMovie(csv, cacheProducer, cacheStudio);
        });

        List<MovieEntity> moviesEntities = movieService.findAll();
        Log.info("movies", moviesEntities);

    }

    private void generateMovie(MovieCsvSourceDTO movieCsvSourceDTO, Set<Producer> cacheProducer, Set<Studio> cacheStudio) {
        Set<Studio> movieStudio = cacheStudio.stream()
                .filter(i -> movieCsvSourceDTO.studio().contains(i.name()))
                .collect(Collectors.toSet());

        Set<Producer> movieProducer = cacheProducer.stream()
                .filter(i -> movieCsvSourceDTO.producer().contains(i.name()))
                .collect(Collectors.toSet());

        var movie = new Movie(null, movieCsvSourceDTO.releaseYear(), movieCsvSourceDTO.title(), movieProducer, movieStudio, movieCsvSourceDTO.winner());
        movieService.save(movie);
    }

    private void generateStudios(MovieCsvSourceDTO movieCsvSourceDTO, Set<Studio> cacheStudio) {
        movieCsvSourceDTO.studio().forEach(studioName -> {
            Studio studio = cacheStudio.stream().filter(a -> a.name().equalsIgnoreCase(studioName)).findFirst().orElse(null);
            if (Objects.isNull(studio)) {
                studio = new Studio(null, studioName);
            }
            studio = studioUseCase.save(studio);
            cacheStudio.add(studio);
        });
    }

    private void generateProducers(MovieCsvSourceDTO movieCsvSourceDTO, Set<Producer> cacheProducer) {
        movieCsvSourceDTO.producer().forEach(producerName -> {
            Producer producer = cacheProducer.stream().filter(a -> a.name().equalsIgnoreCase(producerName)).findFirst().orElse(null);
            if (Objects.isNull(producer)) {
                producer = new Producer(null, producerName);
            }
            producer = producerUseCase.save(producer);
            cacheProducer.add(producer);
        });
    }

}
