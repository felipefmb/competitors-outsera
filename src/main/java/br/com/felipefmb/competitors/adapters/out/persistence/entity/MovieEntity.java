package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "movie")
@SequenceGenerator(name = "movies_seq", sequenceName = "movies_seq", allocationSize = 1)
public class MovieEntity {

    @Id
    @Column(name = "id_movie")
    @GeneratedValue(generator = "movies_seq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "studio")
    private String studio;

    @Column(name = "producer")
    private String producer;

    @Column(name = "winner", nullable = false)
    private boolean winner;

    public MovieEntity() {
    }

    public MovieEntity(Integer year, String title, String studio, String producer, boolean winner) {
        this.year = year;
        this.title = title;
        this.studio = studio;
        this.producer = producer;
        this.winner = winner;
    }

    public BigInteger getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getProducer() {
        return producer;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

}
