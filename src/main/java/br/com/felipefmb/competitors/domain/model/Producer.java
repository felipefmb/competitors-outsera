package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.Set;

public record Producer(
        BigInteger id,
        String name,
        Set<Movie> movies
) implements Domain {
}
