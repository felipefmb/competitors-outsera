package br.com.felipefmb.competitors.integration.controller.mocks;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Studio;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class MockFactory {

    private static final Integer LIMIT_RANDON_ID = 10;
    public static final String NAME_PRODUCER = "PRODUCER_%s";
    public static final String TITLE_MOVIE = "MOVIE_%s";
    public static final String NAME_STUDIO = "STUDIO_%s";
    public static final Boolean WINNER = Boolean.TRUE;
    public static final Boolean LOOSER = Boolean.FALSE;


    public static Producer createProducer() {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new Producer(id, String.format(NAME_PRODUCER, id), Set.of());
    }

    public static Movie createMovie(int releaseYear) {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new Movie(id, releaseYear, String.format(TITLE_MOVIE, id), Set.of(createStudio()), WINNER);
    }

    public static Studio createStudio() {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new Studio(id, String.format(NAME_STUDIO, id));
    }

    public static ProducerEntity createProducerEntity() {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new ProducerEntity(id, String.format(NAME_PRODUCER, id), Set.of());
    }

    public static MovieEntity createMovieEntity(Integer releaseYear) {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new MovieEntity(id, releaseYear, String.format(TITLE_MOVIE, id), Set.of(createStudioEntity()), Set.of(createProducerEntity()), WINNER);
    }

    public static StudioEntity createStudioEntity() {
        BigInteger id = BigInteger.valueOf(new Random().nextInt(LIMIT_RANDON_ID));
        return new StudioEntity(id, String.format(NAME_STUDIO, id), new ArrayList<>());
    }
}
