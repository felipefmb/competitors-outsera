package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.MovieRepository;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.ProducerRepository;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.StudioRepository;
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

    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;
    private final ProducerRepository producerRepository;

    public MovieUseCase(MovieRepository movieRepository, StudioRepository studioRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.studioRepository = studioRepository;
        this.producerRepository = producerRepository;
    }


    @Transactional
    public List<Movie> saveWinnersMovies(Collection<MovieCsvSourceDTO> moviesCsvDto) {
        Collection<MovieCsvSourceDTO> moviesCsvDtoWinners = moviesCsvDto.stream()
                .filter(MovieCsvSourceDTO::winner)
                .toList();
        List<Movie> movies = save(moviesCsvDtoWinners);
        return movies;
    }

    private List<Movie> save(Collection<MovieCsvSourceDTO> moviesCsvDtoWinners) {
        Log.info("moviesCsvDtoWinners", moviesCsvDtoWinners);
        List<ProducerEntity> cacheProducer = new ArrayList<>();
        List<StudioEntity> cacheStudio = new ArrayList<>();
        moviesCsvDtoWinners.forEach(csv -> {
            csv.producer().forEach(p -> {
                ProducerEntity producerEntity = cacheProducer.stream().filter(a -> a.getName().equalsIgnoreCase(p)).findFirst().orElse(null);
                if (Objects.isNull(producerEntity)) {
                    producerEntity = new ProducerEntity(null, p, null);
                }
                producerEntity = producerRepository.save(producerEntity);
                cacheProducer.add(producerEntity);
            });

            csv.studio().forEach(s -> {
                StudioEntity studioEntity = cacheStudio.stream().filter(a -> a.getName().equalsIgnoreCase(s)).findFirst().orElse(null);
                if (Objects.isNull(studioEntity)) {
                    studioEntity = new StudioEntity(null, s, null);
                }
                studioEntity = studioRepository.save(studioEntity);
                cacheStudio.add(studioEntity);
            });

            Set<StudioEntity> movieStudio = cacheStudio.stream().filter(i -> csv.studio().contains(i.getName())).collect(Collectors.toSet());
            Set<ProducerEntity> movieProducer = cacheProducer.stream().filter(i -> csv.producer().contains(i.getName())).collect(Collectors.toSet());
            var movieEntity = new MovieEntity(null, csv.releaseYear(), csv.title(), movieStudio, movieProducer, csv.winner());
            movieRepository.save(movieEntity);
        });
        return null;
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
