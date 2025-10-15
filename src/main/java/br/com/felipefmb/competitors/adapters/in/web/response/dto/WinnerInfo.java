package br.com.felipefmb.competitors.adapters.in.web.response.dto;

public record WinnerInfo(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {
}
