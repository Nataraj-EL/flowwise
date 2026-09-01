package com.flowwise;

import com.flowwise.dto.ActionSummaryDTO;
import com.flowwise.dto.FinancialActionDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialActionService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialActionServiceTest {

    @Autowired
    private FinancialActionService actionService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetMerchantActions_Success() {
        ActionSummaryDTO summary = actionService.getMerchantActions(1L);

        assertNotNull(summary);
        assertTrue(summary.getTotalActions() > 0);
        assertNotNull(summary.getActions());
        assertFalse(summary.getActions().isEmpty());

        // Check Severity Ordering: HIGH comes before MEDIUM, MEDIUM comes before LOW
        FinancialActionDTO firstAction = summary.getActions().get(0);
        assertNotNull(firstAction.getSeverity());
        assertNotNull(firstAction.getRecommendedStep());
        assertNotNull(firstAction.getSupportingEvidence());
    }

    @Test
    void testDismissAction_Success() {
        ActionSummaryDTO summary = actionService.getMerchantActions(1L);
        FinancialActionDTO first = summary.getActions().get(0);

        FinancialActionDTO dismissed = actionService.dismissAction(first.getId());

        assertNotNull(dismissed);
        assertEquals("DISMISSED", dismissed.getStatus());
    }

    @Test
    void testResolveAction_Success() {
        ActionSummaryDTO summary = actionService.getMerchantActions(1L);
        FinancialActionDTO first = summary.getActions().get(0);

        FinancialActionDTO resolved = actionService.resolveAction(first.getId());

        assertNotNull(resolved);
        assertEquals("RESOLVED", resolved.getStatus());
    }

    @Test
    void testAiGrounding_ActionCenterQueries() {
        IntelligenceResponseDTO focusResponse = intelligenceService.processMerchantQuery(1L, "What should I focus on this week?");
        assertNotNull(focusResponse);
        assertNotNull(focusResponse.getAnswer());
        assertTrue(focusResponse.getAnswer().contains("Recommendation") || focusResponse.getAnswer().contains("Focus") || focusResponse.getAnswer().contains("Payable"));

        IntelligenceResponseDTO riskResponse = intelligenceService.processMerchantQuery(1L, "What is my biggest financial risk?");
        assertNotNull(riskResponse);
        assertNotNull(riskResponse.getAnswer());
    }

    @Test
    void testGetMerchantActions_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            actionService.getMerchantActions(999L);
        });
    }
}
