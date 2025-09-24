package br.com.felipefmb.competitors.domain.ports.in;

import java.util.Collection;

public interface CsvSource<T> {
    Collection<T> load(String csvFile);
}
