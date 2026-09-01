package com.flowwise;

import com.flowwise.dto.ReceivableDTO;
import com.flowwise.dto.ReceivablesSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.ReceivablesService;
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
class ReceivablesServiceTest {

    @Autowired
    private ReceivablesService receivablesService;

    @Test
    void testGetReceivables_Success() {
        List<ReceivableDTO> list = receivablesService.getReceivables(1L);

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0).getCounterparty());
        assertNotNull(list.get(0).getInvoiceReference());
    }

    @Test
    void testGetReceivablesSummary_Success() {
        ReceivablesSummaryDTO summary = receivablesService.getReceivablesSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getTotalOutstanding());
        assertTrue(summary.getTotalOutstanding().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(summary.getCurrentReceivables());
        assertNotNull(summary.getOverdue1To30Days());
        assertNotNull(summary.getOverdue31To60Days());
        assertNotNull(summary.getOverdue60PlusDays());
        assertNotNull(summary.getTotalOverdue());
        assertNotNull(summary.getConcentrationRatioPct());
        assertNotNull(summary.getLargestOutstandingCounterparty());

        // Verify mathematical consistency: totalOutstanding == current + totalOverdue
        BigDecimal sumBuckets = summary.getCurrentReceivables().add(summary.getTotalOverdue());
        assertEquals(0, summary.getTotalOutstanding().compareTo(sumBuckets));
    }

    @Test
    void testGetReceivables_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            receivablesService.getReceivables(999L);
        });
    }
}
