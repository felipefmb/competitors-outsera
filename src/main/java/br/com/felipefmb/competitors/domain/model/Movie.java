package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;
import java.util.List;

public class Movie implements Domain {
    private BigInteger id;
    private int releaseYear;
    private String title;
    private List<Studio> studios;
    private boolean winner;

    public BigInteger getId() {
        return id;
    }

    public Movie setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Movie setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public Movie setStudios(List<Studio> studios) {
        this.studios = studios;
        return this;
    }

    public boolean isWinner() {
        return winner;
    }

    public Movie setWinner(boolean winner) {
        this.winner = winner;
        return this;
    }
}
