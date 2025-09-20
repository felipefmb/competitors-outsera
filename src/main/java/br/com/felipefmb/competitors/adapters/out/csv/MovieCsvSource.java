package br.com.felipefmb.competitors.adapters.out.csv;

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
        for (String headerValue : csvRecord.getParser().getHeaderNames()) {
            if (headerValue != null && headerValue.equalsIgnoreCase(header)) return csvRecord.get(headerValue);
        }
        return null;
    }

    private static Integer parseInt(String value) {
        if (Objects.isNull(value) || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean parseWinner(String value) {
        return value != null && value.trim().equalsIgnoreCase("yes");
    }
}
