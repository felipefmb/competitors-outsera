package br.com.felipefmb.competitors.adapters.out.mapper;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.domain.model.Movie;

import java.util.List;

public class MovieDtoMapper {

    public static Movie dtoToDomain(List<MovieCsvSourceDTO> dtos) {
        System.out.println(dtos);
        return null;
    }

}
