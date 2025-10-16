package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.math.BigInteger;
import java.util.Set;

@Entity(name = "movie")
@Table(name = "movie")
@SequenceGenerator(name = "movies_seq", sequenceName = "movies_seq", allocationSize = 1)
public class MovieEntity implements IEntity {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(generator = "movies_seq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_studio",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private Set<StudioEntity> movieStudios;

    @ManyToMany(mappedBy = "movies")
    private Set<ProducerEntity> movieProducers;

    @Column(name = "winner", nullable = false)
    private boolean winner;

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

    public Set<StudioEntity> getMovieStudios() {
        return movieStudios;
    }

    public void setMovieStudios(Set<StudioEntity> movieStudios) {
        this.movieStudios = movieStudios;
    }

    public Set<ProducerEntity> getMovieProducers() {
        return movieProducers;
    }

    public void setMovieProducers(Set<ProducerEntity> movieProducers) {
        this.movieProducers = movieProducers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
