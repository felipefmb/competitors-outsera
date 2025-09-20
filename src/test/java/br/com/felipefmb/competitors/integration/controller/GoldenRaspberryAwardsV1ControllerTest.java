package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.in.web.response.WinnersResponse;
import br.com.felipefmb.competitors.application.usecase.MovieUseCase;
import br.com.felipefmb.competitors.application.usecase.WinnersUseCase;
import br.com.felipefmb.competitors.domain.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false) // desabilita filtros de segurança para focar no HTTP/Controller
@ActiveProfiles("test")
public class GoldenRaspberryAwardsV1ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    MovieUseCase movieUseCase;

    @MockitoBean
    WinnersUseCase winnersUseCase;

    @BeforeEach
    public void setup() {
    }

    @Test
    void deveRealizarTeste() throws Exception {

        Mockito.when(movieUseCase.getMovies()).thenReturn(List.of(
                new Movie(BigInteger.ONE, 2024, "Teste titulo", "Teste studio", "Teste producer", true),
                new Movie(BigInteger.TWO, 2025, "Teste titulo 2", "Teste studio", "Teste producer", true)
        ));
        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/intervals"))
                .andExpect(status().isOk());
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), WinnersResponse.class);
        System.out.println(resultActions);
    }

}
