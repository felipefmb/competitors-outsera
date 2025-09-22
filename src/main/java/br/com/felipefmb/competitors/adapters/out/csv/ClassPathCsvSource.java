package br.com.felipefmb.competitors.adapters.out.csv;

import br.com.felipefmb.competitors.domain.exceptions.FileReaderException;
import br.com.felipefmb.competitors.domain.ports.in.CsvSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class ClassPathCsvSource<T> implements CsvSource<T> {

    @Override
    public Collection<T> load(String csvFile) {
        Resource csvResource = new ClassPathResource(String.format("application/%s.csv", csvFile));
        if (!csvResource.exists()) return List.of();

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
            return generateObjects(parser);
        } catch (IOException e) {
            throw new FileReaderException(e.getMessage(), e.getCause());
        }
    }

    protected abstract List<T> generateObjects(CSVParser parser);

    protected static String get(CSVRecord csvRecord, String header) {
        if (csvRecord.isMapped(header)) return csvRecord.get(header);
        return csvRecord.getParser().getHeaderNames().stream()
                .filter(headerValue -> headerValue != null && headerValue.equalsIgnoreCase(header))
                .findFirst()
                .map(csvRecord::get)
                .orElse(null);
    }

    protected static Integer parseInt(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            throw new FileReaderException(e.getMessage(), e.getCause());
        }
    }

    protected static boolean parseWinner(String value) {
        return Objects.nonNull(value) && value.trim().toLowerCase().contains("y");
    }

}
