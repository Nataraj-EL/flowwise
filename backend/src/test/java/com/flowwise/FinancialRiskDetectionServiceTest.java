package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialRiskDetectionService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialRiskDetectionServiceTest {

    @Autowired
    private FinancialRiskDetectionService riskService;

    @Autowired
    private FlowwiseIntelligenceService aiService;

    @Test
    void testEvaluateMerchantRisks_ReturnsSummaryAndAlerts() {
        RiskMonitorSummaryDTO summary = riskService.evaluateMerchantRisks(1L);
        assertNotNull(summary);
        assertNotNull(summary.getCompositeRiskHealthScore());
        assertTrue(summary.getCompositeRiskHealthScore().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(summary.getCompositeRiskHealthScore().compareTo(new BigDecimal("100")) <= 0);
        assertNotNull(summary.getAlerts());
        assertTrue(summary.getAlerts().size() >= 2);
    }

    @Test
    void testRiskAlertLifecycle_AcknowledgeAndResolve() {
        RiskMonitorSummaryDTO summary = riskService.evaluateMerchantRisks(1L);
        Long alertId = summary.getAlerts().get(0).getId();

        RiskAlertDTO acked = riskService.acknowledgeRiskAlert(1L, alertId);
        assertEquals("ACKNOWLEDGED", acked.getStatus());

        RiskAlertDTO resolved = riskService.resolveRiskAlert(1L, alertId);
        assertEquals("RESOLVED", resolved.getStatus());
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            riskService.getMerchantRiskMonitor(999L);
        });
    }

    @Test
    void testAiGrounding_RiskQueries() {
        IntelligenceResponseDTO response = aiService.processMerchantQuery(1L, "Are there emerging financial risks? Risk monitor");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Risk") || response.getAnswer().contains("Analysis") || response.getAnswer().contains("Health"));
    }
}
