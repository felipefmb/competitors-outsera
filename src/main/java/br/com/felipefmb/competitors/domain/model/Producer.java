package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;

public class Producer implements IDomain {
    private BigInteger id;
    private String name;
    private List<Movie> movies;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
