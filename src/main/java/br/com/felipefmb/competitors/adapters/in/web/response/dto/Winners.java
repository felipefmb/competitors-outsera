package br.com.felipefmb.competitors.adapters.in.web.response.dto;

import br.com.felipefmb.competitors.adapters.in.web.response.Response;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Winners(

        @JsonProperty("min")
        List<WinnerInfo> winnerMinInterval,

        @JsonProperty("max")
        List<WinnerInfo> winnerMaxInterval
) implements Response {
}
