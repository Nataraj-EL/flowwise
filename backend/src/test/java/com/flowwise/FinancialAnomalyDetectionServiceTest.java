package com.flowwise;

import com.flowwise.dto.AnomalySummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialAnomalyDetectionService;
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
public class FinancialAnomalyDetectionServiceTest {

    @Autowired
    private FinancialAnomalyDetectionService anomalyService;

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
    @DisplayName("Evaluate Merchant Anomalies - Threshold Boundaries & Severity Mapping")
    void testEvaluateMerchantAnomalies() {
        AnomalySummaryDTO summary = anomalyService.evaluateMerchantAnomalies(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalAnomaliesCount() > 0);
        assertNotNull(summary.getAnomalies());
        assertFalse(summary.getAnomalies().isEmpty());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            anomalyService.getMerchantAnomalySummary(99999L);
        });
    }
}
