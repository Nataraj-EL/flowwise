package com.flowwise;

import com.flowwise.dto.EvidenceItemDTO;
import com.flowwise.dto.FinancialEvidenceSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.EvidenceBuilderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EvidenceBuilderServiceTest {

    @Autowired
    private EvidenceBuilderService evidenceBuilderService;

    @Test
    void testBuildEvidenceSummary_Affordability() {
        FinancialEvidenceSummaryDTO summary = evidenceBuilderService.buildEvidenceSummary(1L, "Can I afford ₹80,000 of inventory this week?");

        assertNotNull(summary);
        assertEquals("AFFORDABILITY", summary.getIntentCategory());
        assertNotNull(summary.getOverallStatus());
        assertNotNull(summary.getConclusion());
        assertFalse(summary.getEvidenceItems().isEmpty());
        assertFalse(summary.getAssumptions().isEmpty());

        // Check for ACTUAL vs ESTIMATE tags
        assertTrue(summary.getEvidenceItems().stream().anyMatch(i -> "ACTUAL".equals(i.getCalculationType())));
        assertTrue(summary.getEvidenceItems().stream().anyMatch(i -> "ESTIMATE".equals(i.getCalculationType())));
    }

    @Test
    void testBuildEvidenceSummary_Health() {
        FinancialEvidenceSummaryDTO summary = evidenceBuilderService.buildEvidenceSummary(1L, "Why is my health score 78?");

        assertNotNull(summary);
        assertEquals("HEALTH", summary.getIntentCategory());
        assertFalse(summary.getEvidenceItems().isEmpty());
        assertTrue(summary.getEvidenceItems().stream().anyMatch(i -> "Overall Health Score".equals(i.getMetricName())));
    }

    @Test
    void testBuildEvidenceSummary_Temporal() {
        FinancialEvidenceSummaryDTO summary = evidenceBuilderService.buildEvidenceSummary(1L, "What changed compared with last month?");

        assertNotNull(summary);
        assertEquals("TEMPORAL", summary.getIntentCategory());
        assertFalse(summary.getEvidenceItems().isEmpty());
    }

    @Test
    void testBuildEvidenceSummary_Forecast() {
        FinancialEvidenceSummaryDTO summary = evidenceBuilderService.buildEvidenceSummary(1L, "What is my 30-day cash projection?");

        assertNotNull(summary);
        assertEquals("FORECAST", summary.getIntentCategory());
        assertFalse(summary.getEvidenceItems().isEmpty());
        assertTrue(summary.getEvidenceItems().stream().anyMatch(i -> "30-Day Ending Cash".equals(i.getMetricName())));
    }

    @Test
    void testBuildEvidenceSummary_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            evidenceBuilderService.buildEvidenceSummary(999L, "How is my cash flow?");
        });
    }
}
