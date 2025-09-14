package br.com.felipefmb.competitors.core.domain.models;

public record Movie(
        int year,
        String title,
        String studios,
        String producers,
        boolean winner
) {
}
