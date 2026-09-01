package com.flowwise;

import com.flowwise.dto.FinancialExecutionScheduleDTO;
import com.flowwise.dto.FinancialExecutionScheduleSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialExecutionSchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class FinancialExecutionSchedulingServiceTest {

    @Autowired
    private FinancialExecutionSchedulingService schedulingService;

    @Autowired
    private MerchantRepository merchantRepository;

    private Merchant testMerchant;

    @BeforeEach
    void setUp() {
        testMerchant = merchantRepository.findById(1L).orElseGet(() -> {
            Merchant m = new Merchant();
            m.setBusinessName("Test Enterprise");
            m.setDisplayName("Test Enterprise");
            m.setBusinessType("RETAIL");
            m.setIndustry("TECHNOLOGY");
            m.setDemoGstin("27AAAAA0000A1Z5");
            return merchantRepository.save(m);
        });
    }

    @Test
    @DisplayName("Evaluate Execution Schedule - Deterministic 6-Factor Scoring & Safety-Critical Protection")
    void testEvaluateSchedule_Success() {
        FinancialExecutionScheduleDTO schedule = schedulingService.evaluateSchedule(testMerchant.getId(), "30D");

        assertNotNull(schedule);
        assertEquals("30D", schedule.getHorizon());
        assertEquals("ACTIVE", schedule.getStatus());
        assertTrue(schedule.getOverallScheduleScore().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(schedule.getScheduledActions() > 0);
        assertNotNull(schedule.getItems());
        assertFalse(schedule.getItems().isEmpty());

        // Verify Safety-Critical Risk Override (RiskProtectionScore >= 85.00 is SCHEDULED)
        schedule.getItems().forEach(item -> {
            if (item.getRiskProtectionScore().compareTo(new BigDecimal("85.00")) >= 0) {
                assertEquals("SCHEDULED", item.getReadinessStatus());
            }
        });
    }

    @Test
    @DisplayName("Get Execution Schedule Summary - Returns Active Schedule")
    void testGetScheduleSummary_Success() {
        FinancialExecutionScheduleSummaryDTO summary = schedulingService.getScheduleSummary(testMerchant.getId(), "30D");

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertNotNull(summary.getActiveSchedule());
        assertTrue(summary.getActiveSchedule().getScheduledActions() > 0);
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Invalid Merchant")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            schedulingService.getScheduleSummary(99999L, "30D");
        });
    }
}
