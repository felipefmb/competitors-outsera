package br.com.felipefmb.competitors.adapters.in.web.response.dto;

public record Interval(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {
}
