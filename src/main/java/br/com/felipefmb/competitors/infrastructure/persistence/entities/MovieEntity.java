package br.com.felipefmb.competitors.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "movie")
@SequenceGenerator(name = "movies_seq", sequenceName = "movies_seq", allocationSize = 1)
public class MovieEntity {

    @Id
    @Column(name = "id_movie")
    @GeneratedValue(generator = "movies_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "studios")
    private String studios;

    @Column(name = "producers")
    private String producers;

    @Column(name = "winner", nullable = false)
    private boolean winner;

    public MovieEntity() {
    }

    public MovieEntity(Integer year, String title, String studios, String producers, boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getStudios() {
        return studios;
    }

    public String getProducers() {
        return producers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

}
