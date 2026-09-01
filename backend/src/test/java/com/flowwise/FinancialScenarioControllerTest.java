package com.flowwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowwise.dto.ScenarioSimulationRequestDTO;
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
class FinancialScenarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetMerchantScenarios_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/scenarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetScenarioComparison_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/scenarios/comparison")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.baselineScenario").exists())
                .andExpect(jsonPath("$.data.cautiousScenario").exists())
                .andExpect(jsonPath("$.data.stressScenario").exists());
    }

    @Test
    void testSimulateScenario_Success() throws Exception {
        ScenarioSimulationRequestDTO request = new ScenarioSimulationRequestDTO(
                "CUSTOM",
                "API Simulation Test",
                new BigDecimal("-10.00"),
                new BigDecimal("5.00"),
                new BigDecimal("80.00"),
                new BigDecimal("100.00"),
                false
        );

        mockMvc.perform(post("/api/v1/merchants/1/scenarios/simulate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.scenarioType").value("CUSTOM"))
                .andExpect(jsonPath("$.data.riskStatus").exists());
    }

    @Test
    void testCrossMerchantScenarioAccess_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/scenarios/comparison")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
