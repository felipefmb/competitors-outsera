package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;

public class Producer implements Domain {
    private BigInteger id;
    private String name;
    private List<Movie> movies;

    public BigInteger getId() {
        return id;
    }

    public Producer setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Producer setName(String name) {
        this.name = name;
        return this;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Producer setMovies(List<Movie> movies) {
        this.movies = movies;
        return this;
    }
}
