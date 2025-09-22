package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.mapper.ProducerMapper;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.MovieRepository;
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

    @Transactional(Transactional.TxType.MANDATORY)
    private void save(Collection<MovieCsvSourceDTO> moviesCsvDtoWinners) {
        Log.info("moviesCsvDtoWinners", moviesCsvDtoWinners);
        Set<Producer> cacheProducer = new HashSet<>();
        Set<Studio> cacheStudio = new HashSet<>();
        moviesCsvDtoWinners.forEach(csv -> {
            generateProducers(csv, cacheProducer);
            generateStudios(csv, cacheStudio);
            generateMovie(csv, cacheProducer, cacheStudio);
        });
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
//        var movieEntity = new MovieEntity(null, movieCsvSourceDTO.releaseYear(), movieCsvSourceDTO.title(), movieStudioEntities, movieProducerEntities, movieCsvSourceDTO.winner());
//        movieEntity.getMovieStudios().stream().forEach(s -> studioUseCase.findById(s.getId())).collect(Collectors.toSet());
//        movieEntity.getMovieProducers().forEach(s -> producerUseCase.findById(s.getId()));
//        movieRepository.save(movieEntity);
    }

    private void generateStudios(MovieCsvSourceDTO movieCsvSourceDTO, Set<Studio> cacheStudio) {
        movieCsvSourceDTO.studio().forEach(studioName -> {
            Studio studio = cacheStudio.stream().filter(a -> a.name().equalsIgnoreCase(studioName)).findFirst().orElse(null);
            if (Objects.isNull(studio)) {
                studio = new Studio(null, studioName, null);
            }
            studio = studioUseCase.save(studio);
            cacheStudio.add(studio);
        });
    }

    private void generateProducers(MovieCsvSourceDTO movieCsvSourceDTO, Set<Producer> cacheProducer) {
        movieCsvSourceDTO.producer().forEach(p -> {
            Producer producer = cacheProducer.stream().filter(a -> a.name().equalsIgnoreCase(p)).findFirst().orElse(null);
            if (Objects.isNull(producer)) {
                producer = new Producer(null, p, null);
            }
            producer = producerUseCase.save(producer);
            cacheProducer.add(producer);
        });
    }

//    private List<Movie> save(Collection<MovieCsvSourceDTO> moviesCsvDtoWinners) {
//        Log.info("moviesCsvDtoWinners", moviesCsvDtoWinners);
//        moviesCsvDtoWinners.forEach(csv -> {
//            StudioEntity studioEntity = new StudioEntity();
//            studioEntity.setName(csv.studio().get(0));
//            List<StudioEntity> movieStudio = List.of(studioEntity);
//
//            ProducerEntity producerEntity = new ProducerEntity();
//            producerEntity.setName(csv.producer().get(0));
//            List<ProducerEntity> movieProducer = List.of(producerEntity);
//            var movieEntity = new MovieEntity(null, csv.releaseYear(), csv.title(), movieStudio, movieProducer, csv.winner());
//            movieService.save(movieEntity);
//        });
//
//
//        return null;
//    }

//    private List<Producer> generateProducers(MovieCsvSourceDTO movieCsvDTO, List<Producer> producersCache) {
//        var producerCsvList = movieCsvDTO.producer();
//        return producerCsvList.stream().map(producerName -> {
//            Producer producerCache = producersCache.stream()
//                    .filter(p -> p.name().equalsIgnoreCase(producerName))
//                    .findFirst().orElse(null);
//
//            if (Objects.isNull(producerCache)) {
//                producerCache = producerUseCase.save(new Producer(null, producerName, null));
//                producersCache.add(producerCache);
//            }
//            return producerCache;
//        }).toList();
//    }

//    private List<Studio> generateStudios(MovieCsvSourceDTO movieCsvDTO, List<Studio> studiosCache) {
//        var studioCsvList = movieCsvDTO.studio();
//        return studioCsvList.stream().map(studioName -> {
//            Studio studioCache = studiosCache.stream()
//                    .filter(p -> p.name().equalsIgnoreCase(studioName))
//                    .findFirst().orElse(null);
//
//            if (Objects.isNull(studioCache)) {
//                studioCache = studioUseCase.save(new Studio(null, studioName));
//                studiosCache.add(studioCache);
//            }
//            return studioCache;
//        }).toList();
//    }
}
