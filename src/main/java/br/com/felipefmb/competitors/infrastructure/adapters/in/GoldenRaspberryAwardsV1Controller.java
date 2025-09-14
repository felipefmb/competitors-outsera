package br.com.felipefmb.competitors.infrastructure.adapters.in;

import br.com.felipefmb.competitors.application.Log;
import br.com.felipefmb.competitors.application.service.LoadMoviesFromCSVService;
import br.com.felipefmb.competitors.core.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.core.domain.models.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/golden-raspberry-awards")
public class GoldenRaspberryAwardsV1Controller {

    private final LoadMoviesFromCSVService loadMoviesFromCSVService;

    public GoldenRaspberryAwardsV1Controller(LoadMoviesFromCSVService loadMoviesFromCSVService) {
        this.loadMoviesFromCSVService = loadMoviesFromCSVService;
    }

    @GetMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> movies() {
        try {
            List<Movie> movies = loadMoviesFromCSVService.read();
            return ResponseEntity.ok().body(movies);
        } catch (IOException e) {
            String message = MessageFormat.format("Error on load data from CSV File {0}", "moviesList.csv");
            Log.error(message, e.fillInStackTrace());
            throw new GoldenRaspberryAwardsException(message);
        }

    }

}
