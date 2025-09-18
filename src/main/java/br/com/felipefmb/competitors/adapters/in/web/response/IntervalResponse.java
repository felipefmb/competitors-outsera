package br.com.felipefmb.competitors.adapters.in.web.response;

public record IntervalResponse(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) implements Response {
}
