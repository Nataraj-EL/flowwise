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
class FinancialDecisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMerchantDecisions_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decisions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetDecisionSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decisions/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalDecisions").exists())
                .andExpect(jsonPath("$.data.successRatePct").exists());
    }

    @Test
    void testCreateDecision_Success() throws Exception {
        String body = """
                {
                    "decisionType": "CASH_MANAGEMENT",
                    "title": "Test Cash Buffer Decision",
                    "recommendation": "Maintain minimum 10% burn rate buffer",
                    "decisionNotes": "Approved by founder"
                }
                """;

        mockMvc.perform(post("/api/v1/merchants/1/decisions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Cash Buffer Decision"));
    }

    @Test
    void testAcceptAndCompleteDecision_Success() throws Exception {
        String createBody = """
                {
                    "decisionType": "GOAL_ALIGNMENT",
                    "title": "Test Reserve Decision",
                    "recommendation": "Set aside reserve"
                }
                """;

        String response = mockMvc.perform(post("/api/v1/merchants/1/decisions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Parse created decision ID from JSON string
        int idStart = response.indexOf("\"id\":") + 5;
        int idEnd = response.indexOf(",", idStart);
        String createdId = response.substring(idStart, idEnd).trim();

        mockMvc.perform(post("/api/v1/merchants/1/decisions/" + createdId + "/accept")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\": \"Accepted in test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.decisionStatus").value("ACCEPTED"));

        mockMvc.perform(post("/api/v1/merchants/1/decisions/" + createdId + "/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\": \"Completed in test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.decisionStatus").value("COMPLETED"));
    }

    @Test
    void testCrossMerchantDecisionAccess_NotFound() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/2/decisions/1/accept")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
