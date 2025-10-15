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
        List<WinnerInfo> winnerInfos = getWinnerInfos(producers);
        winnerInfos.sort(Comparator.comparingInt(WinnerInfo::interval));
        List<WinnerInfo> min = winnerInfos.stream()
                .filter(i -> i.interval() == winnerInfos.get(0).interval())
                .toList();
        List<WinnerInfo> max = winnerInfos.stream()
                .filter(i -> i.interval() == winnerInfos.get(winnerInfos.size() - 1).interval())
                .toList();
        return new Winners(min, max);
    }

    private List<WinnerInfo> getWinnerInfos(List<Producer> producers) {
        List<WinnerInfo> winnerInfos = new ArrayList<>();
        producers.forEach(producer -> {
            ArrayList<Movie> moviesByProducer = new ArrayList<>(producer.getMovies());
            moviesByProducer.sort(Comparator.comparing(Movie::getReleaseYear));
            for (int i = 0; i < moviesByProducer.size() - 1; i++) {
                Movie current = moviesByProducer.get(i);
                Movie next = moviesByProducer.get(i + 1);
                int interval = next.getReleaseYear() - current.getReleaseYear();
                winnerInfos.add(new WinnerInfo(producer.getName(), interval, current.getReleaseYear(), next.getReleaseYear()));
            }
        });
        return winnerInfos;
    }

}
