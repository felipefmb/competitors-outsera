package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.ProducerInfo;
import br.com.felipefmb.competitors.domain.model.ProducersWinners;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProducersUseCase {

    private static final int MIN_INTERVALS = 2;

    public ProducersWinners getProducersWinners(List<Movie> movies) {
        List<ProducerInfo> infosProducers = getProducersInfo(movies);

        infosProducers.sort(Comparator.comparingInt(ProducerInfo::interval));

        List<Integer> minIntervals = infosProducers.stream().limit(MIN_INTERVALS).map(ProducerInfo::interval).toList();
        List<Integer> maxInterval = Stream.of(infosProducers.get(infosProducers.size() - 1)).map(ProducerInfo::interval).toList();

        List<ProducerInfo> min = infosProducers.stream().filter(l -> minIntervals.contains(l.interval())).toList();
        List<ProducerInfo> max = infosProducers.stream().filter(l -> maxInterval.contains(l.interval())).toList();
        return new ProducersWinners(min, max);
    }

    private List<ProducerInfo> getProducersInfo(List<Movie> movies) {
        List<ProducerInfo> producersInfos = new ArrayList<>();
        movies.stream()
                .collect(Collectors.groupingBy(Movie::producer))
                .entrySet()
                .stream()
                .filter(f -> Objects.nonNull(f.getValue()))
                .filter(f -> !f.getValue().isEmpty())
                .filter(f -> f.getValue().size() > 1)
                .forEach(entry -> {
                    Producer producer = entry.getKey();
                    List<Movie> moviesByProductor = entry.getValue();
                    moviesByProductor.sort(Comparator.comparingInt(Movie::year));
                    for (int i = 0; i < moviesByProductor.size() - 1; i++) {
                        Movie current = moviesByProductor.get(i);
                        Movie next = moviesByProductor.get(i + 1);
                        int interval = next.year() - current.year();
                        producersInfos.add(new ProducerInfo(producer, interval, current.year(), next.year()));
                    }
                });
        return producersInfos;
    }

}
