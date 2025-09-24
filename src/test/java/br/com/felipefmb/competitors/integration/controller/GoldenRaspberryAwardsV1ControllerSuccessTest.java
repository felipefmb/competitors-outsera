package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.Interval;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.MovieEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.integration.controller.mocks.MockFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static br.com.felipefmb.competitors.integration.controller.mocks.MockFactory.Numbers.*;
import static java.math.BigInteger.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GoldenRaspberryAwardsV1ControllerSuccessTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    ProducerService producerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithOneProducerWithTwoMovies() throws Exception {
        ProducerEntity producerEntity = MockFactory.createProducerEntity(valueOf(ONE));
        MovieEntity movieEntityOne = MockFactory.createMovieEntity(valueOf(ONE), 2020);
        MovieEntity movieEntityTwo = MockFactory.createMovieEntity(valueOf(TWO), 2021);

        producerEntity.setMovies(Set.of(movieEntityOne, movieEntityTwo));

        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntity));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());
        Interval minInterval = content.min().get(ZERO);
        Interval maxInterval = content.max().get(ZERO);
        Assertions.assertEquals(minInterval.interval(), ONE.intValue());
        Assertions.assertEquals(minInterval.producer(), producerEntity.getName());
        Assertions.assertEquals(minInterval.previousWin(), maxInterval.previousWin());
        Assertions.assertEquals(minInterval.followingWin(), maxInterval.followingWin());
        Assertions.assertEquals(maxInterval.producer(), producerEntity.getName());
        Assertions.assertEquals(maxInterval.interval(), ONE.intValue());
    }

    @Test
    void testWithDifferentIntervalsMaxMinAndSameProducer() throws Exception {
        ProducerEntity producerEntity = MockFactory.createProducerEntity(valueOf(ONE));
        MovieEntity movieEntityOne = MockFactory.createMovieEntity(valueOf(ONE), 2010);
        MovieEntity movieEntityTwo = MockFactory.createMovieEntity(valueOf(TWO), 2011);

        MovieEntity movieEntityThree = MockFactory.createMovieEntity(valueOf(THREE), 2013);
        MovieEntity movieEntityFour = MockFactory.createMovieEntity(valueOf(FOUR), 2015);

        MovieEntity movieEntityFive = MockFactory.createMovieEntity(valueOf(FIVE), 2016);
        MovieEntity movieEntitySix = MockFactory.createMovieEntity(valueOf(SIX), 2019);

        producerEntity.setMovies(Set.of(
                movieEntityOne,
                movieEntityTwo,
                movieEntityThree,
                movieEntityFour,
                movieEntityFive,
                movieEntitySix
        ));

        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntity));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());

        Interval minInterval = content.min().get(ZERO);
        Interval maxInterval = content.max().get(ZERO);
        Assertions.assertEquals(minInterval.interval(), ONE.intValue());
        Assertions.assertEquals(minInterval.producer(), producerEntity.getName());
        Assertions.assertEquals(minInterval.previousWin(), movieEntityOne.getReleaseYear());
        Assertions.assertEquals(minInterval.followingWin(), movieEntityTwo.getReleaseYear());
        Assertions.assertEquals(maxInterval.producer(), producerEntity.getName());
        Assertions.assertEquals(maxInterval.interval(), THREE.intValue());
        Assertions.assertEquals(maxInterval.previousWin(), movieEntityFive.getReleaseYear());
        Assertions.assertEquals(maxInterval.followingWin(), movieEntitySix.getReleaseYear());
    }

    @Test
    void testWithDifferentIntervalsMaxMinAndDifferentProducers() throws Exception {
        ProducerEntity producerEntity = MockFactory.createProducerEntity(valueOf(ONE));
        ProducerEntity producerEntityTwo = MockFactory.createProducerEntity(valueOf(TWO));
        ProducerEntity producerEntityTree = MockFactory.createProducerEntity(valueOf(THREE));

        MovieEntity movieEntityOne = MockFactory.createMovieEntity(valueOf(ONE), 2010);
        MovieEntity movieEntityTwo = MockFactory.createMovieEntity(valueOf(TWO), 2011);

        MovieEntity movieEntityThree = MockFactory.createMovieEntity(valueOf(THREE), 2013);
        MovieEntity movieEntityFour = MockFactory.createMovieEntity(valueOf(FOUR), 2015);

        MovieEntity movieEntityFive = MockFactory.createMovieEntity(valueOf(FIVE), 2016);
        MovieEntity movieEntitySix = MockFactory.createMovieEntity(valueOf(SIX), 2019);

        producerEntity.setMovies(Set.of(
                movieEntityOne,
                movieEntityTwo
        ));
        producerEntityTwo.setMovies(Set.of(
                movieEntityThree,
                movieEntityFour
        ));
        producerEntityTree.setMovies(Set.of(
                movieEntityFive,
                movieEntitySix
        ));

        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntity, producerEntityTwo, producerEntityTree));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());

        Interval minInterval = content.min().get(ZERO);
        Interval maxInterval = content.max().get(ZERO);
        Assertions.assertEquals(minInterval.interval(), ONE.intValue());
        Assertions.assertEquals(minInterval.producer(), producerEntity.getName());
        Assertions.assertEquals(minInterval.previousWin(), movieEntityOne.getReleaseYear());
        Assertions.assertEquals(minInterval.followingWin(), movieEntityTwo.getReleaseYear());
        Assertions.assertEquals(maxInterval.producer(), producerEntityTree.getName());
        Assertions.assertEquals(maxInterval.interval(), THREE.intValue());
        Assertions.assertEquals(maxInterval.previousWin(), movieEntityFive.getReleaseYear());
        Assertions.assertEquals(maxInterval.followingWin(), movieEntitySix.getReleaseYear());
    }

}
