package com.flowwise;

import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.dto.WorkingCapitalSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FlowwiseIntelligenceService;
import com.flowwise.service.WorkingCapitalService;
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
class WorkingCapitalServiceTest {

    @Autowired
    private WorkingCapitalService workingCapitalService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetWorkingCapitalSummary_Success() {
        WorkingCapitalSummaryDTO summary = workingCapitalService.getWorkingCapitalSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getNetWorkingCapital());
        assertNotNull(summary.getAvailableCash());
        assertNotNull(summary.getReceivablesOutstanding());
        assertNotNull(summary.getPayablesOutstanding());
        assertNotNull(summary.getCurrentCoverageRatio());
        assertNotNull(summary.getNearTermCoverageRatio());
        assertNotNull(summary.getCashConversionRiskStatus());
        assertNotNull(summary.getTopPressureDrivers());
        assertFalse(summary.getTopPressureDrivers().isEmpty());

        // Verify Net Working Capital Math: Net = AvailableCash + Receivables - Payables
        BigDecimal expectedNet = summary.getAvailableCash()
                .add(summary.getReceivablesOutstanding())
                .subtract(summary.getPayablesOutstanding());
        assertEquals(0, summary.getNetWorkingCapital().compareTo(expectedNet));
    }

    @Test
    void testAiGrounding_WorkingCapitalQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "Can I cover my obligations this month?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Working Capital") || response.getAnswer().contains("Net") || response.getAnswer().contains("Coverage") || response.getAnswer().contains("Recommendation"));
    }

    @Test
    void testGetWorkingCapital_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            workingCapitalService.getWorkingCapitalSummary(999L);
        });
    }
}
