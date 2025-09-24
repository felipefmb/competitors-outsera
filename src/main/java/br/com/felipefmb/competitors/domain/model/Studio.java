package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;

public record Studio(
        BigInteger id,
        String name
) implements Domain {
}
