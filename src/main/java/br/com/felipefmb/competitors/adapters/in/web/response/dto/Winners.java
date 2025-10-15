package br.com.felipefmb.competitors.adapters.in.web.response.dto;

import br.com.felipefmb.competitors.adapters.in.web.response.Response;

import java.util.List;

public record Winners(
        List<WinnerInfo> min,
        List<WinnerInfo> max
) implements Response {
}
