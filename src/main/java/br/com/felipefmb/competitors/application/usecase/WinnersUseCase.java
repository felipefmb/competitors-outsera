package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.domain.model.Interval;
import br.com.felipefmb.competitors.adapters.in.web.response.WinnersResponse;
import br.com.felipefmb.competitors.domain.model.Winners;
import br.com.felipefmb.competitors.domain.ports.out.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class WinnersUseCase {

    public WinnersUseCase() {
    }

    public Winners getWinners(List<Interval> listResponsesIntervals) {
        int qtdeRecords = listResponsesIntervals.size();
        listResponsesIntervals.sort(Comparator.comparingInt(Interval::interval));
        List<Integer> minIntervals = listResponsesIntervals.subList(0, Math.min(qtdeRecords, 2)).stream().map(Interval::interval).toList();
        List<Integer> maxInterval = Stream.of(listResponsesIntervals.get(listResponsesIntervals.size() - 1)).map(Interval::interval).toList();
        List<Interval> min = listResponsesIntervals.stream().filter(l -> minIntervals.contains(l.interval())).toList();
        List<Interval> max = listResponsesIntervals.stream().filter(l -> maxInterval.contains(l.interval())).toList();
        return new Winners(min, max);
    }
}
