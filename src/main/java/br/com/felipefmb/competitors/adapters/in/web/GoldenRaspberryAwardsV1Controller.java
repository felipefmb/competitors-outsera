package br.com.felipefmb.competitors.adapters.in.web;

import br.com.felipefmb.competitors.adapters.in.web.mapper.DataMapper;
import br.com.felipefmb.competitors.adapters.in.web.response.DataResponse;
import br.com.felipefmb.competitors.adapters.in.web.response.Response;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.application.usecase.WinnerUserCase;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import br.com.felipefmb.competitors.domain.model.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Golden Raspberry Awards", description = "Serviço que retorna as informações do prêmio Golden Raspberry Awards concedido as piores produções cinematográficas")
@RestController
@RequestMapping("/v1/golden-raspberry-awards")
public class GoldenRaspberryAwardsV1Controller {

    private final WinnerUserCase winnerUserCase;

    public GoldenRaspberryAwardsV1Controller(WinnerUserCase winnerUserCase) {
        this.winnerUserCase = winnerUserCase;
    }

    @Operation(
            summary = "Consulta dos vencedores com menor e maior intervalo de recebimento do prêmio",
            description = "Retorna os vencedores com menor e maior intervalo de recebimento do prêmio")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Winners.class)))
    @ApiResponse(responseCode = "204", description = "Not Found", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataResponse.class)))
    @GetMapping(value = "/producers/winners", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> producersIntervals() {
        try {
            return ResponseEntity.ok().body(winnerUserCase.findWinners());
        } catch (NotFoundException e) {
            return ResponseEntity.status(204).build();
        } catch (GoldenRaspberryAwardsException e) {
            Log.error(e.getMessage(), e.fillInStackTrace());
            var dataResponse = DataMapper.toData(e.getMessage());
            return ResponseEntity.badRequest().body(dataResponse);
        }
    }

}
