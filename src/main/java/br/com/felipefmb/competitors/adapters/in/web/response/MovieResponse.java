package br.com.felipefmb.competitors.adapters.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

@Schema(description = "Resposta de filme")
public record MovieResponse(
        BigInteger id,
        String title,
        int year,
        String studio,
        String producer,
        boolean winner
) {}
