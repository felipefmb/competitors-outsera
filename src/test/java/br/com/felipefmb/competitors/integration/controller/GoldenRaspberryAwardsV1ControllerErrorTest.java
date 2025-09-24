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
class GoldenRaspberryAwardsV1ControllerErrorTest {

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
    void testWithoutResult() throws Exception {
        mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
    }

    @Test
    void testWithOneProducerWithOneMovie() throws Exception {
        ProducerEntity producerEntity = MockFactory.createProducerEntity(valueOf(ONE));
        MovieEntity movieEntityOne = MockFactory.createMovieEntity(valueOf(ONE), 2020);
        producerEntity.setMovies(Set.of(movieEntityOne));
        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntity));
        mockMvc
                .perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
    }

    @Test
    void testWithTwoProducersWithOneEachMovie() throws Exception {
        ProducerEntity producerEntityOne = MockFactory.createProducerEntity(valueOf(ONE));
        ProducerEntity producerEntityTwo = MockFactory.createProducerEntity(valueOf(TWO));

        MovieEntity movieEntityOne = MockFactory.createMovieEntity(valueOf(ONE), 2020);
        MovieEntity movieEntityTwo = MockFactory.createMovieEntity(valueOf(TWO), 2020);

        producerEntityOne.setMovies(Set.of(movieEntityOne));
        producerEntityTwo.setMovies(Set.of(movieEntityTwo));

        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntityOne, producerEntityTwo));
        mockMvc
                .perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
    }

}
