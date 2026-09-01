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
class FinancialGoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMerchantGoals_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/goals")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testCreateGoal_Success() throws Exception {
        String body = """
                {
                    "goalType": "CASH_RESERVE",
                    "name": "Test Emergency Fund",
                    "targetAmount": 500000.00,
                    "targetDate": "2026-12-31"
                }
                """;

        mockMvc.perform(post("/api/v1/merchants/1/goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Test Emergency Fund"));
    }

    @Test
    void testEvaluateGoal_Success() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/1/goals/1/evaluate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testArchiveGoal_Success() throws Exception {
        mockMvc.perform(post("/api/v1/merchants/1/goals/1/archive")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.riskStatus").value("ARCHIVED"));
    }

    @Test
    void testCrossMerchantGoalAccess_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/2/goals/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
