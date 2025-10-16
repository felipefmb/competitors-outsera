package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.WinnerInfo;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
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
        Integer minInterval = 0;
        Integer maxInterval = 0;
        LinkedList<WinnerInfo> winnersMinInterval = new LinkedList<>();
        LinkedList<WinnerInfo> winnersMaxInterval = new LinkedList<>();
        for (var producer : producers) {
            ArrayList<Movie> moviesByProducer = new ArrayList<>(producer.getMovies());
            moviesByProducer.sort(Comparator.comparing(Movie::getReleaseYear));
            for (int i = 0; i < moviesByProducer.size() - 1; i++) {
                Movie current = moviesByProducer.get(i);
                Movie next = moviesByProducer.get(i + 1);
                int interval = next.getReleaseYear() - current.getReleaseYear();
                WinnerInfo winnerInfo = new WinnerInfo(producer.getName(), interval, current.getReleaseYear(), next.getReleaseYear());
                minInterval = generateMinInterval(winnersMinInterval, winnerInfo, minInterval, interval);
                maxInterval = generateMaxInterval(winnersMaxInterval, winnerInfo, maxInterval, interval);
            }
        }
        return new Winners(winnersMinInterval, winnersMaxInterval);
    }

    private Integer generateMaxInterval(LinkedList<WinnerInfo> winnersMaxInterval, WinnerInfo winnerInfo, Integer maxInterval, Integer interval) {
        if (interval >= maxInterval) {
            if (interval > maxInterval) {
                winnersMaxInterval.clear();
            }
            winnersMaxInterval.add(winnerInfo);
            maxInterval = interval;
        }
        return maxInterval;
    }

    private Integer generateMinInterval(LinkedList<WinnerInfo> winnerInfos, WinnerInfo winnerInfo, Integer minInterval, Integer interval) {
        if (interval <= minInterval || minInterval == 0) {
            if (interval < minInterval) {
                winnerInfos.clear();
            }
            winnerInfos.add(winnerInfo);
            minInterval = interval;
        }
        return minInterval;
    }

}
