package com.flowwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowwise.dto.ScenarioRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetForecastSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/forecast")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.currentAvailableCash").exists())
                .andExpect(jsonPath("$.data.projections[0].days").value(30))
                .andExpect(jsonPath("$.data.projections[1].days").value(60))
                .andExpect(jsonPath("$.data.projections[2].days").value(90))
                .andExpect(jsonPath("$.data.estimate").value(true));
    }

    @Test
    void testSimulateScenario_Success() throws Exception {
        ScenarioRequestDTO request = new ScenarioRequestDTO(new BigDecimal("80000"), "INVENTORY");

        mockMvc.perform(post("/api/v1/merchants/1/forecast/scenario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.requestedAmount").value(80000))
                .andExpect(jsonPath("$.data.category").value("INVENTORY"))
                .andExpect(jsonPath("$.data.riskStatus").exists())
                .andExpect(jsonPath("$.data.estimate").value(true));
    }

    @Test
    void testGetForecastSummary_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/forecast")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
