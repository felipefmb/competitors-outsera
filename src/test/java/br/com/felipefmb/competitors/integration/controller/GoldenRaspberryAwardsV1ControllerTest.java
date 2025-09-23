package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.out.persistence.entity.ProducerEntity;
import br.com.felipefmb.competitors.adapters.out.persistence.repositories.ProducerRepository;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.integration.controller.mocks.MockFactory;
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
public class GoldenRaspberryAwardsV1ControllerTest {

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
    void testMustPerformTestWithoutResult() throws Exception {
        mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
    }

    @Test
    void mustPerformTestWithResult() throws Exception {
//        Producer producerDomainMock = MockFactory.createProducer();
        ProducerEntity producerEntity = MockFactory.createProducerEntity();
        Mockito.when(producerService.findAll()).thenReturn(List.of(producerEntity));

        mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(204));
//        var response = resultActions.andReturn().getResponse();
//        var content = mapper.readValue(response.getContentAsString(), Winners.class);
//        System.out.println(resultActions);
    }

}
