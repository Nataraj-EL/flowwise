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
class TemporalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTemporalSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/temporal/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.currentMonth").exists())
                .andExpect(jsonPath("$.data.inflowChangePct").exists())
                .andExpect(jsonPath("$.data.outflowChangePct").exists())
                .andExpect(jsonPath("$.data.anomalies").exists());
    }

    @Test
    void testGetCategoryMovements_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/temporal/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].category").exists());
    }

    @Test
    void testGetTemporalSummary_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/temporal/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
