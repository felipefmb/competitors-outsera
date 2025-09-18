package br.com.felipefmb.competitors.adapters.in.web.response;

import java.util.List;

public record WinnersResponse(
        List<IntervalResponse> min,
        List<IntervalResponse> max
) implements Response {
}
