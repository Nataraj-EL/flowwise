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
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetBusinessHealth_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.overallScore").exists())
                .andExpect(jsonPath("$.data.healthStatus").exists())
                .andExpect(jsonPath("$.data.factorScores[0].factorName").exists())
                .andExpect(jsonPath("$.data.summaryExplanation").exists());
    }

    @Test
    void testGetBusinessHealth_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
