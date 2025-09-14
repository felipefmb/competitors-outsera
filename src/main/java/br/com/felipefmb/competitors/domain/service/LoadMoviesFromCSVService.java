package br.com.felipefmb.competitors.domain.service;

import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.model.Movie;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LoadMoviesFromCSVService {

    private final Resource csvResource;

    public LoadMoviesFromCSVService(@Value("classpath:application/movielist.csv") Resource csvResource) {
        this.csvResource = csvResource;
    }

    public List<Movie> read() throws IOException {
        if (!csvResource.exists()) {
            Log.warn("CSV resource not found: " + csvResource);
            return List.of();
        }

        List<Movie> movieList = new ArrayList<>();


        try (
                var inputStream = csvResource.getInputStream();
                var bufferedRead = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                var parser = CSVParser.parse(
                        bufferedRead,
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

                if (Objects.isNull(title) || title.isBlank() || year == null) {
                    Log.warn(MessageFormat.format("Skipping row (missing required fields). record={0}", record.getRecordNumber()));
                    continue;
                }

                movieList.add(new Movie(null, year, title, studio, producer, winner));
            }
        }


        return movieList;
    }

    private static String get(CSVRecord record, String headerParameter) {
        try {
            if (record.isMapped(headerParameter)) return record.get(headerParameter);
            for (String header : record.getParser().getHeaderNames()) {
                if (Objects.nonNull(header) && header.equalsIgnoreCase(headerParameter)) {
                    return record.get(header);
                }
            }
            return null;
        } catch (IllegalArgumentException e) {
            Log.error("Error reading header", e.fillInStackTrace());
            return null;
        }
    }

    private static Integer parseInt(String value) {
        if (Objects.isNull(value) || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            Log.error("Error converting a String value to a number", e.fillInStackTrace());
            return null;
        }
    }

    private static boolean parseWinner(String value) {
        return Objects.nonNull(value) && value.trim().equalsIgnoreCase("yes");
    }

}
