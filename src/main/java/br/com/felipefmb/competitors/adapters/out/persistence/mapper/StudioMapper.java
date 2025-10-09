package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.model.Studio;

public class StudioMapper implements Mapper<Studio, StudioEntity> {

    public Studio toDomain(StudioEntity entity) {
        return new Studio(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public StudioEntity toEntity(Studio domain) {
        StudioEntity entity = new StudioEntity();
        entity.setId(domain.id());
        entity.setName(domain.name());
        return entity;
    }

}
