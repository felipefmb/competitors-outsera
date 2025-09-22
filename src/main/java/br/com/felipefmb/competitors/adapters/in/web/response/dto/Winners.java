package br.com.felipefmb.competitors.adapters.in.web.response.dto;

import br.com.felipefmb.competitors.adapters.in.web.response.Response;

import java.util.List;

public record Winners(
        List<Interval> min,
        List<Interval> max
) implements Response {
}
