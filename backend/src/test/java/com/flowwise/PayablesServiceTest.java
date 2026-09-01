package com.flowwise;

import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.dto.PayableDTO;
import com.flowwise.dto.PayablesSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FlowwiseIntelligenceService;
import com.flowwise.service.PayablesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PayablesServiceTest {

    @Autowired
    private PayablesService payablesService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetPayables_Success() {
        List<PayableDTO> list = payablesService.getPayables(1L);

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0).getVendor());
        assertNotNull(list.get(0).getBillReference());
    }

    @Test
    void testGetPayablesSummary_Success() {
        PayablesSummaryDTO summary = payablesService.getPayablesSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getTotalOutstanding());
        assertTrue(summary.getTotalOutstanding().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(summary.getDueToday());
        assertNotNull(summary.getDue7Days());
        assertNotNull(summary.getDue30Days());
        assertNotNull(summary.getTotalOverdue());
        assertNotNull(summary.getPaymentCoverageRatioPct());
        assertNotNull(summary.getUpcomingPayablePressure());
        assertNotNull(summary.getLargestVendorObligation());

        // Verify pressure calculation: upcomingPayablePressure == dueToday + due7Days + totalOverdue
        BigDecimal expectedPressure = summary.getDueToday().add(summary.getDue7Days()).add(summary.getTotalOverdue());
        assertEquals(0, summary.getUpcomingPayablePressure().compareTo(expectedPressure));
    }

    @Test
    void testAiGrounding_PayablesQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "What bills do I owe this week?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Payable") || response.getAnswer().contains("Vendor") || response.getAnswer().contains("Due") || response.getAnswer().contains("Recommendation"));
    }

    @Test
    void testGetPayables_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            payablesService.getPayables(999L);
        });
    }
}
