package br.com.felipefmb.competitors.adapters.in.web;

import br.com.felipefmb.competitors.adapters.in.web.mapper.DataMapper;
import br.com.felipefmb.competitors.adapters.in.web.mapper.MovieWebMapper;
import br.com.felipefmb.competitors.adapters.in.web.request.MovieRequest;
import br.com.felipefmb.competitors.adapters.in.web.response.DataResponse;
import br.com.felipefmb.competitors.adapters.in.web.response.IntervalResponse;
import br.com.felipefmb.competitors.adapters.in.web.response.Response;
import br.com.felipefmb.competitors.adapters.in.web.response.WinnersResponse;
import br.com.felipefmb.competitors.application.usecase.MovieUseCase;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.application.usecase.LoadMoviesFromCSVUseCase;
import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.model.Movie;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Movies", description = "Operações com filmes")
@RestController
@RequestMapping("/v1/golden-raspberry-awards")
public class GoldenRaspberryAwardsV1Controller {

    private final LoadMoviesFromCSVUseCase loadMoviesFromCSVUseCase;

    private final MovieUseCase movieUseCase;

    public GoldenRaspberryAwardsV1Controller(LoadMoviesFromCSVUseCase loadMoviesFromCSVUseCase, MovieUseCase movieUseCase) {
        this.loadMoviesFromCSVUseCase = loadMoviesFromCSVUseCase;
        this.movieUseCase = movieUseCase;
    }

    @Operation(summary = "Lista filmes", description = "Retorna uma página de filmes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping(value = "/producers/intervals", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> producersIntervals() {
        Log.info("Starting query of all films");
        try {
            Log.info("Querying all records");
            var movies = movieUseCase.findWinners();
            Log.info("Consultation completed");

            if (Objects.isNull(movies) || movies.isEmpty()) {
                Log.info("No data");
                return ResponseEntity.noContent().build();
            }
            List<IntervalResponse> listResponseInterval = new ArrayList<>();
            var listResponsesIntervals = new ArrayList<>(movies.stream()
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
                            listResponseInterval.add(new IntervalResponse(producer, interval, moviePrevious.year(), movieNext.year()));
                            if (nextPosition >= moviesByProductor.size()) break;
                        }
                        return listResponseInterval;
                    })
                    .flatMap(Collection::stream)
                    .distinct()
                    .toList());

            listResponsesIntervals.sort(Comparator.comparingInt(IntervalResponse::interval));
            List<IntervalResponse> min = listResponsesIntervals.subList(0, 1);
            List<IntervalResponse> max = List.of(listResponsesIntervals.get(listResponsesIntervals.size() - 1));
            var response = new WinnersResponse(min, max);
            Log.info("Returning results");
            return ResponseEntity.ok().body(response);
        } catch (GoldenRaspberryAwardsException e) {
            String message = MessageFormat.format("Error on load data from CSV File {0}", "moviesList.csv");
            Log.error(message, e.fillInStackTrace());
            var dataResponse = DataMapper.toData(message);
            return ResponseEntity.badRequest().body(dataResponse);
        }
    }

}
