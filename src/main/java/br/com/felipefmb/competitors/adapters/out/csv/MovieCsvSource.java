package br.com.felipefmb.competitors.adapters.out.csv;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.domain.exceptions.MovieException;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class MovieCsvSource extends ClassPathCsvSource<MovieCsvSourceDTO> {

    @Override
    protected List<MovieCsvSourceDTO> generateObjects(CSVParser parser) {
        var movies = getMovies(parser);
        if (Objects.isNull(movies) || movies.isEmpty()) throw new MovieException("Invalid CSV file");
        return movies;
    }

    private List<MovieCsvSourceDTO> getMovies(CSVParser parser) {
        return parser.stream()
                .map(csvRecord -> {
                    Integer releaseYear = parseInt(get(csvRecord, "year"));
                    String title = get(csvRecord, "title");
                    String producersNames = get(csvRecord, "producers");
                    String studiosNames = get(csvRecord, "studios");
                    boolean winner = parseWinner(get(csvRecord, "winner"));
                    if (releaseYear == null || isBlank(title) || isBlank(producersNames)) return null;
                    List<String> producers = Arrays.stream(producersNames.split(",| and ")).map(String::trim).toList();
                    List<String> studio = isBlank(studiosNames) ? List.of() :
                            Arrays.stream(studiosNames.split(",| and ")).map(String::trim).toList();
                    return new MovieCsvSourceDTO(
                            null, releaseYear, title, studio, producers, winner
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean isBlank(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

}