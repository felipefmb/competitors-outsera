package br.com.felipefmb.competitors.adapters.out.persistence.repositories;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.ports.out.StudioRepositoryPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class StudioRepository implements StudioRepositoryPort {

    private final StudioRepositoryJpa studioRepositoryJpa;

    public StudioRepository(StudioRepositoryJpa studioRepositoryJpa) {
        this.studioRepositoryJpa = studioRepositoryJpa;
    }

    @Override
    public List<StudioEntity> saveAll(List<StudioEntity> entities) {
        return studioRepositoryJpa.findAll();
    }

    @Override
    @Transactional
    public StudioEntity save(StudioEntity entity) {
        return studioRepositoryJpa.save(entity);
    }

    @Override
    public StudioEntity findByName(String name) {
        return studioRepositoryJpa.findByName(name);
    }

    @Override
    public Optional<StudioEntity> findById(BigInteger id) {
        return studioRepositoryJpa.findById(id);
    }
}
