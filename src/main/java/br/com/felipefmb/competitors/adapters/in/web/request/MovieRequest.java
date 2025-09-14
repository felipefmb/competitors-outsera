package br.com.felipefmb.competitors.adapters.in.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;

@Schema(description = "Payload para criação/atualização de filme")
public record MovieRequest(
        BigInteger id,
        @NotBlank String title,
        @Min(1888) int year,
        String studio,
        String producer,
        boolean winner
) {}
