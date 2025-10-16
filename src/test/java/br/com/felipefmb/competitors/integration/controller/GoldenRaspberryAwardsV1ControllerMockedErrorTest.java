package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GoldenRaspberryAwardsV1ControllerMockedErrorTest {

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
    void testWithoutValidProducers() throws Exception {
        Mockito.when(producerService.findProducersWithMultipleMovies(Mockito.any())).thenReturn(List.of());
        mockMvc
                .perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
    }

}
