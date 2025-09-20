package br.com.felipefmb.competitors.domain.model;

import java.util.List;

public record ProducersWinners(
        List<ProducerInfo> min,
        List<ProducerInfo> max
) {
}
