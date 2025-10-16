package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity(name = "studio")
@Table(name = "studio")
@SequenceGenerator(name = "studios_seq", sequenceName = "studios_seq", allocationSize = 1)
public class StudioEntity implements IEntity {

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

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
    }
}
