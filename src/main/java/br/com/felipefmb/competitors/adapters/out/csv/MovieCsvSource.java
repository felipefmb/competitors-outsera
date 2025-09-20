package br.com.felipefmb.competitors.adapters.out.csv;

import br.com.felipefmb.competitors.domain.exceptions.FileReaderException;
import br.com.felipefmb.competitors.domain.model.Movie;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieCsvSource extends ClassPathCsvSource<Movie> {
    @Override
    Movie generateObject(CSVRecord csvRecord) {
        Integer year = parseInt(get(csvRecord, "year"));
        String title = get(csvRecord, "title");
        String studio = get(csvRecord, "studios");
        String producer = get(csvRecord, "producers");
        boolean winner = parseWinner(get(csvRecord, "winner"));
        if (title == null || title.isBlank() || year == null) return null;
        return new Movie(null, year, title, studio, producer, winner);
    }

    private static String get(CSVRecord csvRecord, String header) {
        if (csvRecord.isMapped(header)) return csvRecord.get(header);
        return csvRecord.getParser().getHeaderNames().stream()
                .filter(headerValue -> headerValue != null && headerValue.equalsIgnoreCase(header))
                .findFirst()
                .map(csvRecord::get)
                .orElse(null);
    }

    private static Integer parseInt(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            throw new FileReaderException(e.getMessage(), e.getCause());
        }
    }

    private static boolean parseWinner(String value) {
        return Objects.nonNull(value) && value.trim().toLowerCase().contains("y");
    }
}
