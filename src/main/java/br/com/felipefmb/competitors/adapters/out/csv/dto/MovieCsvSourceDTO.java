package br.com.felipefmb.competitors.adapters.out.csv.dto;

import java.math.BigInteger;
import java.util.List;

public class MovieCsvSourceDTO {
    BigInteger id;
    Integer releaseYear;
    String title;
    List<String> studio;
    List<String> producer;
    boolean winner;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStudio() {
        return studio;
    }

    public void setStudio(List<String> studio) {
        this.studio = studio;
    }

    public List<String> getProducer() {
        return producer;
    }

    public void setProducer(List<String> producer) {
        this.producer = producer;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
