package br.com.felipefmb.competitors.adapters.out.csv.dto;

import br.com.felipefmb.competitors.domain.model.Domain;

import java.math.BigInteger;
import java.util.List;

public record MovieCsvSourceDTO(
        BigInteger id,
        Integer releaseYear,
        String title,
        List<String> studio,
        List<String> producer,
        boolean winner
) implements Domain {
}
