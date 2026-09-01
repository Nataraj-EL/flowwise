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
class FinancialInsightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMerchantInsights_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/insights")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetInsightSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/insights/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalInsights").exists())
                .andExpect(jsonPath("$.data.patternEngineStatus").exists());
    }

    @Test
    void testAcknowledgeInsight_Success() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/1/insights/1/acknowledge")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACKNOWLEDGED"));
    }

    @Test
    void testDismissInsight_Success() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/1/insights/2/dismiss")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("DISMISSED"));
    }

    @Test
    void testCrossMerchantInsightAccess_NotFound() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/2/insights/1/acknowledge")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
