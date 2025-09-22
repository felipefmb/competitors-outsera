package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.Interval;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
public class WinnerUserCase {

    private final ProducerUseCase producerUseCase;

    public WinnerUserCase(ProducerUseCase producerUseCase) {
        this.producerUseCase = producerUseCase;
    }

    @Transactional
    public Winners findWinners() {
        List<Producer> producers = producerUseCase.findAll();
        List<Interval> intervals = getIntervals(producers);
        return getWinners(intervals);
    }

    private List<Interval> getIntervals(List<Producer> producers) {
        List<Interval> intervals = new ArrayList<>();
        producers.forEach(producer -> {
            ArrayList<Movie> moviesByProducer = new ArrayList<>(producer.movies());
            moviesByProducer.sort(Comparator.comparing(Movie::releaseYear));
            for (int i = 0; i < moviesByProducer.size() - 1; i++) {
                Movie current = moviesByProducer.get(i);
                Movie next = moviesByProducer.get(i + 1);
                int interval = next.releaseYear() - current.releaseYear();
                intervals.add(new Interval(producer.name(), interval, current.releaseYear(), next.releaseYear()));
            }
        });
        return intervals;
    }

    private Winners getWinners(List<Interval> intervals) {
        intervals.sort(Comparator.comparingInt(Interval::interval));

        List<Integer> minIntervals = intervals.stream().limit(2).map(Interval::interval).toList();
        List<Integer> maxInterval = Stream.of(intervals.get(intervals.size() - 1)).map(Interval::interval).toList();

        List<Interval> min = intervals.stream().filter(l -> minIntervals.contains(l.interval())).toList();
        List<Interval> max = intervals.stream().filter(l -> maxInterval.contains(l.interval())).toList();
        return new Winners(min, max);
    }
}
