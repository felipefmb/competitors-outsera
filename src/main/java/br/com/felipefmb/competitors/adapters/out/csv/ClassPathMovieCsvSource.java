package br.com.felipefmb.competitors.adapters.out.csv;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.in.MovieCsvSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClassPathMovieCsvSource implements MovieCsvSource {

    private final Resource csvResource;

    public ClassPathMovieCsvSource(
            @Value("classpath:application/movielist.csv") Resource csvResource) {
        this.csvResource = csvResource;
    }


    @Override
    public List<Movie> loadMovies() {
        if (!csvResource.exists()) return List.of();

        List<Movie> list = new ArrayList<>();
        try (var in = csvResource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             var parser = CSVParser.parse(reader,
                     CSVFormat.DEFAULT
                             .builder()
                             .setDelimiter(";")
                             .setHeader()
                             .setSkipHeaderRecord(true)
                             .setIgnoreEmptyLines(true)
                             .setTrim(true)
                             .build()
             )) {

            for (CSVRecord record : parser) {
                Integer year = parseInt(get(record, "year"));
                String title = get(record, "title");
                String studio = get(record, "studios");
                String producer = get(record, "producers");
                boolean winner = parseWinner(get(record, "winner"));

                if (title == null || title.isBlank() || year == null) continue;

                list.add(new Movie(null, year, title, studio, producer, winner));
            }
        } catch (IOException e) {
            // logar aqui (adapter) — não no use case
            // logger.warn("Erro lendo CSV", e);
            return List.of();
        }
        return list;
    }

    private static String get(CSVRecord record, String header) {
        if (record.isMapped(header)) return record.get(header);
        for (String h : record.getParser().getHeaderNames()) {
            if (h != null && h.equalsIgnoreCase(header)) return record.get(h);
        }
        return null;
    }

    private static Integer parseInt(String v) {
        if (v == null || v.isBlank()) return null;
        try {
            return Integer.valueOf(v.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean parseWinner(String v) {
        return v != null && v.trim().equalsIgnoreCase("yes");
    }
}
