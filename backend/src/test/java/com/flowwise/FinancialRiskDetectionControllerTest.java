package com.flowwise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FinancialRiskDetectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetRiskMonitor_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/risk-monitor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.compositeRiskHealthScore").exists())
                .andExpect(jsonPath("$.data.alerts").isArray());
    }

    @Test
    void testEvaluateRisks_Success() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/1/risk-monitor/evaluate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.overallRiskLevel").exists());
    }

    @Test
    void testCrossMerchantAccess_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/risk-monitor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
