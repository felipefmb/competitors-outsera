package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import java.util.List;

public interface Mapper<D, E> {

    D toDomain(E entity);

    E toEntity(D domain);

    default List<D> toDomain(List<E> entities) {
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

}
