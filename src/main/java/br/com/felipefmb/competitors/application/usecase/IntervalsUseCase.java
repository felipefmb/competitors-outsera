package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Interval;
import br.com.felipefmb.competitors.domain.model.Movie;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class IntervalsUseCase {

    public List<Interval> getIntervals(List<Movie> movies) {
        List<Interval> intervals = new ArrayList<>();
        movies.stream()
                .collect(Collectors.groupingBy(Movie::producer))
                .entrySet()
                .stream()
                .filter(f -> Objects.nonNull(f.getValue()))
                .filter(f -> !f.getValue().isEmpty())
                .filter(f -> f.getValue().size() > 1)
                .forEach(entry -> {
                    String producer = entry.getKey();
                    List<Movie> moviesByProductor = entry.getValue();
                    moviesByProductor.sort(Comparator.comparingInt(Movie::year));
                    for (int i = 0; i < moviesByProductor.size() - 1; i++) {
                        Movie current = moviesByProductor.get(i);
                        Movie next = moviesByProductor.get(i + 1);
                        int interval = next.year() - current.year();
                        intervals.add(new Interval(producer, interval, current.year(), next.year()));
                    }
                });

        return intervals;
    }

}
