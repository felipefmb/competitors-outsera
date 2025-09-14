package br.com.felipefmb.competitors.adapters.in.web;

import br.com.felipefmb.competitors.adapters.in.web.mapper.DataMapper;
import br.com.felipefmb.competitors.adapters.in.web.mapper.MovieWebMapper;
import br.com.felipefmb.competitors.adapters.in.web.request.MovieRequest;
import br.com.felipefmb.competitors.adapters.in.web.response.DataResponse;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.service.LoadMoviesFromCSVService;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Movies", description = "Operações com filmes")
@RestController
@RequestMapping("/v1/golden-raspberry-awards")
public class GoldenRaspberryAwardsV1Controller {

    private final LoadMoviesFromCSVService loadMoviesFromCSVService;

    public GoldenRaspberryAwardsV1Controller(LoadMoviesFromCSVService loadMoviesFromCSVService) {
        this.loadMoviesFromCSVService = loadMoviesFromCSVService;
    }

    @Operation(summary = "Lista filmes", description = "Retorna uma página de filmes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> movies() {
        try {
            var movies = loadMoviesFromCSVService.read();
            var movieResponse = MovieWebMapper.toResponse(movies);
            var dataResponse = DataMapper.toData(movieResponse);
            return ResponseEntity.ok().body(dataResponse);
        } catch (IOException e) {
            String message = MessageFormat.format("Error on load data from CSV File {0}", "moviesList.csv");
            Log.error(message, e.fillInStackTrace());
            var dataResponse = DataMapper.toData(message);
            return ResponseEntity.badRequest().body(dataResponse);
        }
    }

    @PostMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> saveMovies(@RequestBody MovieRequest movieRequest) {
        try {
            var toDoman = MovieWebMapper.toDomain(movieRequest);

            return ResponseEntity.ok().body(movies);
        } catch (IOException e) {
            String message = MessageFormat.format("Error on load data from CSV File {0}", "moviesList.csv");
            Log.error(message, e.fillInStackTrace());
            throw new GoldenRaspberryAwardsException(message);
        }

    }

}
