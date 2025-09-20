package br.com.felipefmb.competitors.domain.model;

public record ProducerInfo(
        Producer producer,
        int interval,
        int previousWin,
        int followingWin
) {
}
