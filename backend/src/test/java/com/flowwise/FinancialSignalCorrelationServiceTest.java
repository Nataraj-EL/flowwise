package com.flowwise;

import com.flowwise.dto.CorrelationSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialSignalCorrelationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FinancialSignalCorrelationServiceTest {

    @Autowired
    private FinancialSignalCorrelationService correlationService;

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
    @DisplayName("Evaluate Signal Correlations - Weighted Contribution Score & LIKELY_CONTRIBUTOR Labeling")
    void testEvaluateSignalCorrelations() {
        CorrelationSummaryDTO summary = correlationService.evaluateSignalCorrelations(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalCorrelationsCount() > 0);
        assertNotNull(summary.getCorrelations());
        assertFalse(summary.getCorrelations().isEmpty());
        assertTrue(summary.getTopLikelyRootCause().contains("LIKELY_CONTRIBUTOR"));
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            correlationService.getMerchantCorrelationSummary(99999L);
        });
    }
}
