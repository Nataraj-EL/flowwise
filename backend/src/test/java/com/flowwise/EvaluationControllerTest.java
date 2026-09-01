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
class EvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRunEvaluation_Success() throws Exception {
        mockMvc.perform(post("/api/v1/evaluation/run")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalCases").value(15))
                .andExpect(jsonPath("$.data.overallScore").exists())
                .andExpect(jsonPath("$.data.groundingScore").exists())
                .andExpect(jsonPath("$.data.caseResults[0].caseId").value("TC-01"));
    }

    @Test
    void testGetEvaluationSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/evaluation/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalCases").value(15));
    }
}
