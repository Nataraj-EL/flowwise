package com.flowwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowwise.dto.IntelligenceQueryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntelligenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testQueryIntelligence_Success() throws Exception {
        IntelligenceQueryDTO query = new IntelligenceQueryDTO("Can I afford ₹80,000 of inventory this week?");

        mockMvc.perform(post("/api/v1/merchants/1/intelligence/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.question").value("Can I afford ₹80,000 of inventory this week?"))
                .andExpect(jsonPath("$.data.answer").exists())
                .andExpect(jsonPath("$.data.evidenceSummary.availableCash").exists())
                .andExpect(jsonPath("$.data.modelUsed").exists())
                .andExpect(jsonPath("$.data.disclaimer").exists());
    }

    @Test
    void testQueryIntelligence_NotFound() throws Exception {
        IntelligenceQueryDTO query = new IntelligenceQueryDTO("How is my cash flow?");

        mockMvc.perform(post("/api/v1/merchants/999/intelligence/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
