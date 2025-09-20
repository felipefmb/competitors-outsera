package br.com.felipefmb.competitors.domain.ports.in;

import java.util.List;

public interface CsvSource<T> {
    List<T> load(String csvFile);
}
