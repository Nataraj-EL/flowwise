package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialInsightService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialInsightServiceTest {

    @Autowired
    private FinancialInsightService insightService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetMerchantInsights_DetectsPatternsAndDeduplicates() {
        List<FinancialInsightDTO> insights = insightService.getMerchantInsights(1L);
        assertNotNull(insights);
        assertFalse(insights.isEmpty());

        int initialSize = insights.size();

        // Re-call pattern detection (must be idempotent - no duplicate active insights)
        List<FinancialInsightDTO> reRun = insightService.getMerchantInsights(1L);
        assertEquals(initialSize, reRun.size());
    }

    @Test
    void testAcknowledgeInsight_Success() {
        List<FinancialInsightDTO> insights = insightService.getMerchantInsights(1L);
        FinancialInsightDTO newInsight = insights.stream()
                .filter(i -> "NEW".equalsIgnoreCase(i.getStatus()))
                .findFirst()
                .orElse(insights.get(0));

        if ("NEW".equalsIgnoreCase(newInsight.getStatus())) {
            FinancialInsightDTO updated = insightService.acknowledgeInsight(1L, newInsight.getId());
            assertEquals("ACKNOWLEDGED", updated.getStatus());
        }
    }

    @Test
    void testDismissInsight_Success() {
        List<FinancialInsightDTO> insights = insightService.getMerchantInsights(1L);
        FinancialInsightDTO target = insights.get(0);

        FinancialInsightDTO updated = insightService.dismissInsight(1L, target.getId());
        assertEquals("DISMISSED", updated.getStatus());

        // Attempting to dismiss already dismissed insight must throw exception
        assertThrows(IllegalStateException.class, () -> {
            insightService.dismissInsight(1L, target.getId());
        });
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            insightService.acknowledgeInsight(2L, 1L); // Merchant 2 accessing Merchant 1's insight
        });
    }

    @Test
    void testGetInsightSummary_Success() {
        InsightSummaryDTO summary = insightService.getInsightSummary(1L);
        assertNotNull(summary);
        assertTrue(summary.getTotalInsights() > 0);
        assertTrue(summary.isSufficientHistory());
        assertNotNull(summary.getPatternEngineStatus());
    }

    @Test
    void testAiGrounding_PatternQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "What patterns do you see?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Pattern") || response.getAnswer().contains("Insight") || response.getAnswer().contains("Analysis") || response.getAnswer().contains("Discovered"));
    }
}
