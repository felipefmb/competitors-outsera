package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "producer")
@SequenceGenerator(name = "producers_seq", sequenceName = "producers_seq", allocationSize = 1)
public class ProducerEntity implements IEntity {

    @Id
    @Column(name = "producer_id")
    @GeneratedValue(generator = "producers_seq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "producer_movie",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
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
