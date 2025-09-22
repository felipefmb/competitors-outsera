package br.com.felipefmb.competitors.adapters.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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
    private List<MovieEntity> movies;

    public StudioEntity() {
    }

    public StudioEntity(BigInteger id, String name, List<MovieEntity> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

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

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public StudioEntity setMovies(List<MovieEntity> movies) {
        this.movies = movies;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StudioEntity that = (StudioEntity) object;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudioEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}
