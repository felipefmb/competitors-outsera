package br.com.felipefmb.competitors.adapters.in.web.response;

import br.com.felipefmb.competitors.domain.model.Interval;

import java.util.List;

public record WinnersResponse(
        List<Interval> min,
        List<Interval> max
) implements Response {
}
