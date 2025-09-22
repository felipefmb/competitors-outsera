package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;

public record Producer(
        BigInteger id,
        String name
) implements Domain {
}
