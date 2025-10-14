package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity(name = "studio")
@Table(name = "studio")
@SequenceGenerator(name = "studios_seq", sequenceName = "studios_seq", allocationSize = 1)
public class StudioEntity {

    @Id
    @Column(name = "studio_id")
    @GeneratedValue(generator = "studios_seq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @ManyToMany(mappedBy = "movieStudios")
    private Set<MovieEntity> movies;

    public BigInteger getId() {
        return id;
    }

    public StudioEntity setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudioEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public StudioEntity setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
        return this;
    }
}
