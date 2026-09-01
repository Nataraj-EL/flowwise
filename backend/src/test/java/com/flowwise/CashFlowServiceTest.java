package com.flowwise;

import com.flowwise.dto.CashFlowSummaryDTO;
import com.flowwise.dto.MonthlyCashFlowDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.CashFlowService;
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
class CashFlowServiceTest {

    @Autowired
    private CashFlowService cashFlowService;

    @Test
    void testGetCashFlowSummary_Success() {
        CashFlowSummaryDTO summary = cashFlowService.getCashFlowSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getTotalInflows());
        assertNotNull(summary.getTotalOutflows());
        assertNotNull(summary.getNetCashFlow());
        assertNotNull(summary.getBurnRate());
        assertNotNull(summary.getCashRunwayMonths());
        assertNotNull(summary.getLiquidityStatus());

        // Net Cash Flow = Inflows - Outflows
        assertEquals(0, summary.getTotalInflows().subtract(summary.getTotalOutflows()).compareTo(summary.getNetCashFlow()));
        
        // Assert liquidity status (0.8 months runway -> CRITICAL)
        assertNotNull(summary.getLiquidityStatus());
        assertEquals("CRITICAL", summary.getLiquidityStatus());
        assertTrue(summary.getCashRunwayMonths().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testGetMonthlyCashFlows_Success() {
        List<MonthlyCashFlowDTO> monthly = cashFlowService.getMonthlyCashFlows(1L);

        assertNotNull(monthly);
        assertFalse(monthly.isEmpty());
        assertTrue(monthly.stream().anyMatch(m -> m.getInflow().compareTo(BigDecimal.ZERO) > 0));
    }

    @Test
    void testGetCashFlowSummary_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            cashFlowService.getCashFlowSummary(999L);
        });
    }
}
