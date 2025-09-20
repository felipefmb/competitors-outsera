package br.com.felipefmb.competitors.adapters.out.csv;

import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.ports.in.CsvSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ClassPathCsvSource<T> implements CsvSource<T> {

    @Override
    public List<T> load(String csvFile) {
        Resource csvResource = new ClassPathResource(String.format("application/%s.csv", csvFile));
        if (!csvResource.exists()) return List.of();

        List<T> list = new ArrayList<>();
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

            for (CSVRecord csvRecord : parser) {
                T object = generateObject(csvRecord);
                if (Objects.isNull(object)) continue;
                list.add(object);
            }
        } catch (IOException e) {
            // logar aqui (adapter) — não no use case
            // logger.warn("Erro lendo CSV", e);
            return List.of();
        }
        return list;
    }

    abstract T generateObject(CSVRecord csvRecord);

}
