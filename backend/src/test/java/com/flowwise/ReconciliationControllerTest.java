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
class ReconciliationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetReconciliationSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/reconciliation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.reconciliationHealthPct").exists())
                .andExpect(jsonPath("$.data.issues").isArray());
    }

    @Test
    void testGetReconciliationIssues_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/reconciliation/issues")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testReconcileTransaction_Success() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/1/reconcile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\": \"Reconciled by test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testIgnoreTransaction_Success() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/2/ignore")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\": \"Ignored by test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
