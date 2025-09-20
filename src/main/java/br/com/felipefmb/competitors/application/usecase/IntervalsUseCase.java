package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Interval;
import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class IntervalsUseCase {

    public List<Interval> getIntervals(List<Movie> movies) {
        List<Interval> listResponseInterval = new ArrayList<>();
        new ArrayList<>(movies.stream()
                .collect(Collectors.groupingBy(Movie::producer))
                .entrySet()
                .stream()
                .filter(f -> Objects.nonNull(f.getValue()))
                .filter(f -> !f.getValue().isEmpty())
                .filter(f -> f.getValue().size() > 1)
                .map(m -> {
                    String producer = m.getKey();
                    List<Movie> moviesByProductor = m.getValue();
                    moviesByProductor.sort(Comparator.comparingInt(Movie::year));
                    for (var i = 0; i <= moviesByProductor.size(); i++) {
                        int nextPosition = i + 1;
                        if (nextPosition >= moviesByProductor.size()) break;
                        Movie moviePrevious = moviesByProductor.get(i);
                        Movie movieNext = moviesByProductor.get(nextPosition);
                        int interval = movieNext.year() - moviePrevious.year();
                        listResponseInterval.add(new Interval(producer, interval, moviePrevious.year(), movieNext.year()));
                        if (nextPosition >= moviesByProductor.size()) break;
                    }
                    return listResponseInterval;
                })
                .flatMap(Collection::stream)
                .distinct()
                .toList());

        return listResponseInterval;
    }

}
