package com.flowwise;

import com.flowwise.dto.ActionSummaryDTO;
import com.flowwise.service.FinancialActionService;
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
class ActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FinancialActionService actionService;

    @Test
    void testGetMerchantActions_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/actions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalActions").exists())
                .andExpect(jsonPath("$.data.actions").isArray());
    }

    @Test
    void testDismissAction_Success() throws Exception {
        ActionSummaryDTO summary = actionService.getMerchantActions(1L);
        Long actionId = summary.getActions().get(0).getId();

        mockMvc.perform(post("/api/v1/actions/" + actionId + "/dismiss")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("DISMISSED"));
    }

    @Test
    void testResolveAction_Success() throws Exception {
        ActionSummaryDTO summary = actionService.getMerchantActions(1L);
        Long actionId = summary.getActions().get(0).getId();

        mockMvc.perform(post("/api/v1/actions/" + actionId + "/resolve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("RESOLVED"));
    }
}
