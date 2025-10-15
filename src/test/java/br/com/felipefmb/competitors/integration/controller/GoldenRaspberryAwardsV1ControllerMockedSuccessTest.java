package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.WinnerInfo;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.domain.model.Producer;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.felipefmb.competitors.integration.controller.mocks.MockFactory.Numbers.*;
import static java.math.BigInteger.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GoldenRaspberryAwardsV1ControllerMockedSuccessTest {

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
        Producer producer = MockFactory.createProducer(valueOf(ONE));
        Movie movieEntityOne = MockFactory.createMovie(valueOf(ONE), 2020);
        Movie movieEntityTwo = MockFactory.createMovie(valueOf(TWO), 2021);

        producer.getMovies().clear();
        producer.getMovies().addAll(Set.of(movieEntityOne, movieEntityTwo));

        Mockito.when(producerService.findProducersWithMultipleMovies(Mockito.any())).thenReturn(List.of(producer));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());
        WinnerInfo minWinnerInfo = content.min().get(ZERO);
        WinnerInfo maxWinnerInfo = content.max().get(ZERO);
        Assertions.assertEquals(minWinnerInfo.interval(), ONE.intValue());
        Assertions.assertEquals(minWinnerInfo.producer(), producer.getName());
        Assertions.assertEquals(minWinnerInfo.previousWin(), maxWinnerInfo.previousWin());
        Assertions.assertEquals(minWinnerInfo.followingWin(), maxWinnerInfo.followingWin());
        Assertions.assertEquals(maxWinnerInfo.producer(), producer.getName());
        Assertions.assertEquals(maxWinnerInfo.interval(), ONE.intValue());
    }

    @Test
    void testWithDifferentIntervalsMaxMinAndSameProducer() throws Exception {
        Producer producer = MockFactory.createProducer(valueOf(ONE));
        Movie movieOne = MockFactory.createMovie(valueOf(ONE), 2010);
        Movie movieTwo = MockFactory.createMovie(valueOf(TWO), 2011);

        Movie movieThree = MockFactory.createMovie(valueOf(THREE), 2013);
        Movie movieFour = MockFactory.createMovie(valueOf(FOUR), 2015);

        Movie movieFive = MockFactory.createMovie(valueOf(FIVE), 2016);
        Movie movieSix = MockFactory.createMovie(valueOf(SIX), 2019);

        producer.getMovies().clear();
        producer.getMovies().addAll(Set.of(
                movieOne,
                movieTwo,
                movieThree,
                movieFour,
                movieFive,
                movieSix
        ));

        Mockito.when(producerService.findProducersWithMultipleMovies(Mockito.any())).thenReturn(List.of(producer));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());

        WinnerInfo minWinnerInfo = content.min().get(ZERO);
        WinnerInfo maxWinnerInfo = content.max().get(ZERO);
        Assertions.assertEquals(minWinnerInfo.interval(), ONE.intValue());
        Assertions.assertEquals(minWinnerInfo.producer(), producer.getName());
        Assertions.assertEquals(minWinnerInfo.previousWin(), movieOne.getReleaseYear());
        Assertions.assertEquals(minWinnerInfo.followingWin(), movieTwo.getReleaseYear());
        Assertions.assertEquals(maxWinnerInfo.producer(), producer.getName());
        Assertions.assertEquals(maxWinnerInfo.interval(), THREE.intValue());
        Assertions.assertEquals(maxWinnerInfo.previousWin(), movieFive.getReleaseYear());
        Assertions.assertEquals(maxWinnerInfo.followingWin(), movieSix.getReleaseYear());
    }

    @Test
    void testWithDifferentIntervalsMaxMinAndDifferentProducers() throws Exception {
        Producer producer = MockFactory.createProducer(valueOf(ONE));
        Producer producerTwo = MockFactory.createProducer(valueOf(TWO));
        Producer producerTree = MockFactory.createProducer(valueOf(THREE));

        Movie movieOne = MockFactory.createMovie(valueOf(ONE), 2010);
        Movie movieTwo = MockFactory.createMovie(valueOf(TWO), 2011);

        Movie movieThree = MockFactory.createMovie(valueOf(THREE), 2013);
        Movie movieFour = MockFactory.createMovie(valueOf(FOUR), 2015);

        Movie movieFive = MockFactory.createMovie(valueOf(FIVE), 2016);
        Movie movieSix = MockFactory.createMovie(valueOf(SIX), 2019);

        producer.getMovies().addAll(Set.of(
                movieOne,
                movieTwo
        ));

        producerTwo.getMovies().clear();
        producerTwo.getMovies().addAll(Set.of(
                movieThree,
                movieFour
        ));

        producerTree.getMovies().clear();
        producerTree.getMovies().addAll(Set.of(
                movieFive,
                movieSix
        ));

        Mockito.when(producerService.findProducersWithMultipleMovies(Mockito.any())).thenReturn(List.of(producer, producerTwo, producerTree));

        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(content.min());
        Assertions.assertNotNull(content.max());
        Assertions.assertFalse(content.max().isEmpty());
        Assertions.assertFalse(content.min().isEmpty());

        WinnerInfo minWinnerInfo = content.min().get(ZERO);
        WinnerInfo maxWinnerInfo = content.max().get(ZERO);
        Assertions.assertEquals(minWinnerInfo.interval(), ONE.intValue());
        Assertions.assertEquals(minWinnerInfo.producer(), producer.getName());
        Assertions.assertEquals(minWinnerInfo.previousWin(), movieOne.getReleaseYear());
        Assertions.assertEquals(minWinnerInfo.followingWin(), movieTwo.getReleaseYear());
        Assertions.assertEquals(maxWinnerInfo.producer(), producerTree.getName());
        Assertions.assertEquals(maxWinnerInfo.interval(), THREE.intValue());
        Assertions.assertEquals(maxWinnerInfo.previousWin(), movieFive.getReleaseYear());
        Assertions.assertEquals(maxWinnerInfo.followingWin(), movieSix.getReleaseYear());
    }

}
