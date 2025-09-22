package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "movie")
@Table(name = "movie")
@SequenceGenerator(name = "movies_seq", sequenceName = "movies_seq", allocationSize = 1)
public class MovieEntity {

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<ProducerEntity> movieProducers;

    @Column(name = "winner", nullable = false)
    private boolean winner;

    public MovieEntity() {
    }

    public MovieEntity(BigInteger id, Integer releaseYear, String title, Set<StudioEntity> movieStudios, Set<ProducerEntity> movieProducers, boolean winner) {
        this.id = id;
        this.releaseYear = releaseYear;
        this.title = title;
        this.movieStudios = movieStudios;
        this.movieProducers = movieProducers;
        this.winner = winner;
    }

    public BigInteger getId() {
        return id;
    }

    public MovieEntity setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public MovieEntity setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<StudioEntity> getMovieStudios() {
        return movieStudios;
    }

    public MovieEntity setMovieStudios(Set<StudioEntity> movieStudios) {
        this.movieStudios = movieStudios;
        return this;
    }

    public Set<ProducerEntity> getMovieProducers() {
        return movieProducers;
    }

    public MovieEntity setMovieProducers(Set<ProducerEntity> movieProducers) {
        this.movieProducers = movieProducers;
        return this;
    }

    public boolean isWinner() {
        return winner;
    }

    public MovieEntity setWinner(boolean winner) {
        this.winner = winner;
        return this;
    }

    public void addMovieStudio(StudioEntity studio) {
        this.movieStudios.add(studio);
    }

    public void addMovieProducer(ProducerEntity producer) {
        this.movieProducers.add(producer);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MovieEntity that = (MovieEntity) object;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", releaseYear=" + releaseYear +
                ", title='" + title + '\'' +
                ", movieStudios=" + movieStudios +
                ", movieProducers=" + movieProducers +
                ", winner=" + winner +
                '}';
    }
}
