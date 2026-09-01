package com.flowwise;

import com.flowwise.controller.FinancialExecutionScheduleController;
import com.flowwise.dto.FinancialExecutionScheduleDTO;
import com.flowwise.dto.FinancialExecutionScheduleSummaryDTO;
import com.flowwise.service.FinancialExecutionSchedulingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinancialExecutionScheduleController.class)
public class FinancialExecutionScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialExecutionSchedulingService schedulingService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/execution-schedules - Returns 200 OK")
    void testGetScheduleSummary_Success() throws Exception {
        FinancialExecutionScheduleDTO activeSchedule = new FinancialExecutionScheduleDTO(
                1L, 1L, "SCHED_KEY", "30D", "ACTIVE", new BigDecimal("93.80"),
                new BigDecimal("85.00"), new BigDecimal("91.50"), new BigDecimal("94.20"),
                new BigDecimal("92.00"), 2, 2, 0, "Primary Focus", "Expected Benefit",
                "Risk If Deferred", "Evidence", "Assumptions", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        FinancialExecutionScheduleSummaryDTO summary = new FinancialExecutionScheduleSummaryDTO(
                1L, 1, activeSchedule, Collections.singletonList(activeSchedule),
                "Summary Explanation", "Advisory Notice"
        );

        given(schedulingService.getScheduleSummary(eq(1L), eq("30D"))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/execution-schedules?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.activeSchedule.overallScheduleScore").value(93.80));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/execution-schedules/evaluate - Returns 200 OK")
    void testEvaluateSchedule_Success() throws Exception {
        FinancialExecutionScheduleDTO schedule = new FinancialExecutionScheduleDTO(
                1L, 1L, "SCHED_KEY", "30D", "ACTIVE", new BigDecimal("93.80"),
                new BigDecimal("85.00"), new BigDecimal("91.50"), new BigDecimal("94.20"),
                new BigDecimal("92.00"), 2, 2, 0, "Primary Focus", "Expected Benefit",
                "Risk If Deferred", "Evidence", "Assumptions", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(schedulingService.evaluateSchedule(eq(1L), eq("30D"))).willReturn(schedule);

        mockMvc.perform(post("/api/v1/merchants/1/execution-schedules/evaluate?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }
}
