package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.in.web.response.dto.WinnerInfo;
import br.com.felipefmb.competitors.adapters.in.web.response.dto.Winners;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GoldenRaspberryAwardsV1ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testContentFile() throws Exception {
        var resultActions = mockMvc.perform(get("/v1/golden-raspberry-awards/producers/winners"))
                .andExpect(status().is(200));
        var response = resultActions.andReturn().getResponse();
        var content = mapper.readValue(response.getContentAsString(), Winners.class);
        Assertions.assertEquals(1, content.min().size());
        Assertions.assertEquals(1, content.max().size());
        WinnerInfo minWinnerInfo = content.min().get(0);
        WinnerInfo maxWinnerInfo = content.max().get(0);
        Assertions.assertEquals(1, minWinnerInfo.interval());
        Assertions.assertEquals(13, maxWinnerInfo.interval());
    }

}
