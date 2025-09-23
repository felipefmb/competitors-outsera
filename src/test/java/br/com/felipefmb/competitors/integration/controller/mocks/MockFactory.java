package br.com.felipefmb.competitors.integration.controller.mocks;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.StudioEntity;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Studio;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public class MockFactory {

    public static class Numbers {
        public static final Integer ZERO = 0;
        public static final Integer ONE = 1;
        public static final Integer TWO = 2;
        public static final Integer THREE = 3;
        public static final Integer FOUR = 4;
        public static final Integer FIVE = 5;
        public static final Integer SIX = 6;
        public static final Integer SEVEN = 7;
        public static final Integer EIGHT = 8;
        public static final Integer NINE = 9;
    }

    private static final Integer LIMIT_RANDON_ID = 10;
    public static final String NAME_PRODUCER = "PRODUCER_%s";
    public static final String TITLE_MOVIE = "MOVIE_%s";
    public static final String NAME_STUDIO = "STUDIO_%s";
    public static final Boolean WINNER = Boolean.TRUE;
    public static final Boolean LOOSER = Boolean.FALSE;

    public static Producer createProducer(BigInteger id) {
        return new Producer(id, String.format(NAME_PRODUCER, id), Set.of());
    }

    public static Movie createMovie(BigInteger id, int releaseYear) {
        return new Movie(id, releaseYear, String.format(TITLE_MOVIE, id), Set.of(), WINNER);
    }

    public static Studio createStudio(BigInteger id) {
        return new Studio(id, String.format(NAME_STUDIO, id));
    }

    public static ProducerEntity createProducerEntity(BigInteger id) {
        return new ProducerEntity(id, String.format(NAME_PRODUCER, id), Set.of());
    }

    public static MovieEntity createMovieEntity(BigInteger id, Integer releaseYear) {
        return new MovieEntity(id, releaseYear, String.format(TITLE_MOVIE, id), Set.of(), Set.of(), WINNER);
    }

    public static StudioEntity createStudioEntity(BigInteger id) {
        return new StudioEntity(id, String.format(NAME_STUDIO, id), List.of());
    }
}
