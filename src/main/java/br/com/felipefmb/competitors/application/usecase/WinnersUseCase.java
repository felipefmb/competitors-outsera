package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Interval;
import br.com.felipefmb.competitors.domain.model.Winners;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class WinnersUseCase {

    private static final int MIN_INTERVALS = 2;

    public Winners getWinners(List<Interval> intervals) {
        intervals.sort(Comparator.comparingInt(Interval::interval));

        List<Integer> minIntervals = intervals.stream().limit(MIN_INTERVALS).map(Interval::interval).toList();
        List<Integer> maxInterval = Stream.of(intervals.get(intervals.size() - 1)).map(Interval::interval).toList();

        List<Interval> min = intervals.stream().filter(l -> minIntervals.contains(l.interval())).toList();
        List<Interval> max = intervals.stream().filter(l -> maxInterval.contains(l.interval())).toList();
        return new Winners(min, max);
    }
}
