package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.Interval;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class WinnerUserCase {

    private final ProducerUseCase producerUseCase;

    public WinnerUserCase(ProducerUseCase producerUseCase) {
        this.producerUseCase = producerUseCase;
    }

    @Transactional(readOnly = true)
    public Winners findWinners() {
        List<Producer> producers = producerUseCase.findProducersWithMultipleMovies();
        if (producers.isEmpty()) {
            throw new NotFoundException("No records found");
        }
        return getWinners(producers);
    }

    private Winners getWinners(List<Producer> producers) {
        List<Interval> intervals = getIntervals(producers);
        intervals.sort(Comparator.comparingInt(Interval::interval));
        List<Interval> min = intervals.stream()
                .filter(i -> i.interval() == intervals.get(0).interval())
                .toList();
        List<Interval> max = intervals.stream()
                .filter(i -> i.interval() == intervals.get(intervals.size() - 1).interval())
                .toList();
        return new Winners(min, max);
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

}
