package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;

public record Movie(
        BigInteger id,
        int year,
        String title,
        String studio,
        String producer,
        boolean winner
) {
}
