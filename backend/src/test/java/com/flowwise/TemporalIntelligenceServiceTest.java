package com.flowwise;

import com.flowwise.dto.CategoryMovementDTO;
import com.flowwise.dto.TemporalSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.TemporalIntelligenceService;
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
class TemporalIntelligenceServiceTest {

    @Autowired
    private TemporalIntelligenceService temporalService;

    @Test
    void testGetTemporalSummary_Success() {
        TemporalSummaryDTO summary = temporalService.getTemporalSummary(1L);

        assertNotNull(summary);
        assertNotNull(summary.getCurrentMonth());
        assertNotNull(summary.getPreviousMonth());
        assertNotNull(summary.getInflowChangePct());
        assertNotNull(summary.getOutflowChangePct());
        assertNotNull(summary.getNetCashChangePct());
        assertNotNull(summary.getInflowDirection());
        assertNotNull(summary.getOutflowDirection());

        assertNotNull(summary.getCategoryMovements());
        assertNotNull(summary.getAnomalies());
        assertFalse(summary.isInsufficientHistory());
    }

    @Test
    void testCalculateCategoryMovements_Success() {
        List<CategoryMovementDTO> movements = temporalService.calculateCategoryMovements(1L, "Sep", "Aug");

        assertNotNull(movements);
        assertFalse(movements.isEmpty());
        assertTrue(movements.stream().anyMatch(m -> m.getCategory() != null));
    }

    @Test
    void testGetTemporalSummary_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            temporalService.getTemporalSummary(999L);
        });
    }
}
