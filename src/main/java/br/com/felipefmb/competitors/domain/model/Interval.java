package br.com.felipefmb.competitors.domain.model;

import br.com.felipefmb.competitors.adapters.in.web.response.Response;

public record Interval(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) implements Response {
}
