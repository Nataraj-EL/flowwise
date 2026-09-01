package com.flowwise;

import com.flowwise.dto.CashManagementSummaryDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.dto.PaymentPlanDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.CashManagementService;
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
class CashManagementServiceTest {

    @Autowired
    private CashManagementService cashManagementService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetCashManagementSummary_Success() {
        CashManagementSummaryDTO summary = cashManagementService.getCashManagementSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getCurrentAvailableCash());
        assertNotNull(summary.getUpcoming7DayObligations());
        assertNotNull(summary.getExpected7DayCollections());
        assertNotNull(summary.getProjected7DayCashPosition());
        assertNotNull(summary.getSafePaymentCapacity());
        assertNotNull(summary.getPaymentRiskStatus());
        assertNotNull(summary.getCalculationBasis());
        assertNotNull(summary.getAdvisoryNotice());

        // Verify projection formula: Projected = Available + Expected Collections - Obligations
        BigDecimal expectedProjected = summary.getCurrentAvailableCash()
                .add(summary.getExpected7DayCollections())
                .subtract(summary.getUpcoming7DayObligations());

        assertEquals(expectedProjected, summary.getProjected7DayCashPosition());

        // Verify advisory capacity is non-negative
        assertTrue(summary.getSafePaymentCapacity().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(summary.getAdvisoryNotice().contains("Advisory"));
    }

    @Test
    void testGetPaymentPlan_Success() {
        PaymentPlanDTO plan = cashManagementService.getPaymentPlan(1L);

        assertNotNull(plan);
        assertNotNull(plan.getPrioritizedPayments());
        assertNotNull(plan.getExecutionAdvice());
        assertTrue(plan.getSafePaymentCapacity().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testAiGrounding_CashManagementQueries() {
        IntelligenceResponseDTO response1 = intelligenceService.processMerchantQuery(1L, "Can I pay my bills this week?");
        assertNotNull(response1);
        assertNotNull(response1.getAnswer());
        assertTrue(response1.getAnswer().contains("Cash") || response1.getAnswer().contains("Payment") || response1.getAnswer().contains("Capacity") || response1.getAnswer().contains("Status"));

        IntelligenceResponseDTO response2 = intelligenceService.processMerchantQuery(1L, "What should I pay first?");
        assertNotNull(response2);
        assertNotNull(response2.getAnswer());

        IntelligenceResponseDTO response3 = intelligenceService.processMerchantQuery(1L, "How much can I safely spend?");
        assertNotNull(response3);
        assertNotNull(response3.getAnswer());
    }

    @Test
    void testGetCashManagement_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            cashManagementService.getCashManagementSummary(999L);
        });
    }
}
