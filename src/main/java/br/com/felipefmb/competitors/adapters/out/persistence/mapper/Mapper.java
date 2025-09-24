package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Mapper<D, E> {

    D toDomain(E entity);

    E toEntity(D domain);

    default List<D> toDomains(List<E> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        return entities.stream().map(this::toDomain).toList();
    }

    default List<E> toEntities(List<D> domains) {
        if (domains == null || domains.isEmpty()) {
            return List.of();
        }
        return domains.stream().map(this::toEntity).toList();
    }

    default Set<D> toDomains(Set<E> entities) {
        if (entities == null || entities.isEmpty()) {
            return Set.of();
        }
        return entities.stream().map(this::toDomain).collect(Collectors.toSet());
    }

    default Set<E> toEntities(Set<D> domains) {
        if (domains == null || domains.isEmpty()) {
            return Set.of();
        }
        return domains.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
