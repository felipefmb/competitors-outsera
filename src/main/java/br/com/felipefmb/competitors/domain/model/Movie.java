package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public record Movie(
        BigInteger id,
        int releaseYear,
        String title,
        Set<Producer> producers,
        Set<Studio> studios,
        boolean winner
) implements Domain {
}
