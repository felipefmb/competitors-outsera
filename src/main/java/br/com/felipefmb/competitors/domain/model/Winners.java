package br.com.felipefmb.competitors.domain.model;

import java.util.List;

public record Winners(
        List<Interval> min,
        List<Interval> max
) {
}
