package br.com.felipefmb.competitors.adapters.in.web;

import br.com.felipefmb.competitors.adapters.in.web.mapper.DataMapper;
import br.com.felipefmb.competitors.adapters.in.web.mapper.WinnerMapper;
import br.com.felipefmb.competitors.adapters.in.web.response.Response;
import br.com.felipefmb.competitors.application.usecase.IntervalsUseCase;
import br.com.felipefmb.competitors.application.usecase.MovieUseCase;
import br.com.felipefmb.competitors.application.usecase.WinnersUseCase;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import br.com.felipefmb.competitors.domain.model.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Movies", description = "Operações com filmes")
@RestController
@RequestMapping("/v1/golden-raspberry-awards")
public class GoldenRaspberryAwardsV1Controller {

    private final MovieUseCase movieUseCase;

    private final IntervalsUseCase intervalsUseCase;

    private final WinnersUseCase winnersUseCase;


    public GoldenRaspberryAwardsV1Controller(MovieUseCase movieUseCase, IntervalsUseCase intervalsUseCase, WinnersUseCase winnersUseCase) {
        this.movieUseCase = movieUseCase;
        this.intervalsUseCase = intervalsUseCase;
        this.winnersUseCase = winnersUseCase;
    }

    @Operation(summary = "Lista filmes", description = "Retorna uma página de filmes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)))
    })
    @GetMapping(value = "/producers/winners", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> producersIntervals() {
        try {
            var movies = movieUseCase.getMovies();
            var intervals = intervalsUseCase.getIntervals(movies);
            var winners = winnersUseCase.getWinners(intervals);
            return ResponseEntity.ok().body(WinnerMapper.toData(winners));
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (GoldenRaspberryAwardsException e) {
            Log.error(e.getMessage(), e.fillInStackTrace());
            var dataResponse = DataMapper.toData(e.getMessage());
            return ResponseEntity.badRequest().body(dataResponse);
        }
    }

}
