package com.flowwise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FinancialDecisionIntelligenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDecisionIntelligence_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decision-intelligence")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.recommendedOption").exists())
                .andExpect(jsonPath("$.data.options").isArray());
    }

    @Test
    void testGetLatestAnalysis_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decision-intelligence/analysis")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.analysisKey").value("CURRENT_OPERATING_DECISION"));
    }

    @Test
    void testCrossMerchantAccess_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/decision-intelligence")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
