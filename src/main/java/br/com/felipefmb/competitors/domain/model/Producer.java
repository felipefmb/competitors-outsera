package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;

public record Producer(
        BigInteger id,
        String name,
        List<Movie> movies
) implements Domain {
}
