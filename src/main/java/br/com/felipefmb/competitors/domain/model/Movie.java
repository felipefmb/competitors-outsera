package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;

public record Movie(
        BigInteger id,
        int releaseYear,
        String title,
        List<Producer> producers,
        List<Studio> studios,
        boolean winner
) implements Domain {
}
