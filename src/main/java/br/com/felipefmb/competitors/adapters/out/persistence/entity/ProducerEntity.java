package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "producer")
@SequenceGenerator(name = "producers_seq", sequenceName = "producers_seq", allocationSize = 1)
public class ProducerEntity {

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

    public ProducerEntity() {
    }

    public ProducerEntity(BigInteger id) {
        this.id = id;
    }

    public ProducerEntity(BigInteger id, String name, Set<MovieEntity> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public BigInteger getId() {
        return id;
    }

    public ProducerEntity setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProducerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public ProducerEntity setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProducerEntity that = (ProducerEntity) object;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProducerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}
