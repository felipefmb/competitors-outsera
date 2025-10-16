package br.com.felipefmb.competitors.adapters.out.persistence.mapper;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.model.Studio;

public class StudioMapper implements Mapper<Studio, StudioEntity> {

    public Studio toDomain(StudioEntity entity) {
        Studio domain = new Studio();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        return domain;
    }

    @Override
    public StudioEntity toEntity(Studio domain) {
        StudioEntity entity = new StudioEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }

}
