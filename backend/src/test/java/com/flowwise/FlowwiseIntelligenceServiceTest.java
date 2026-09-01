package com.flowwise;

import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FlowwiseIntelligenceServiceTest {

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testProcessMerchantQuery_AffordabilityQuestion() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "Can I afford ₹80,000 of inventory this week?");

        assertNotNull(response);
        assertEquals("Can I afford ₹80,000 of inventory this week?", response.getQuestion());
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("80,000") || response.getAnswer().contains("inventory") || response.getAnswer().contains("Apex"));

        assertNotNull(response.getEvidenceSummary());
        assertTrue(response.getEvidenceSummary().containsKey("availableCash"));
        assertTrue(response.getEvidenceSummary().containsKey("netCashFlow"));
        assertNotNull(response.getDisclaimer());
    }

    @Test
    void testProcessMerchantQuery_CashFlowQuestion() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "How is my cash flow?");

        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().toLowerCase().contains("cash flow") || response.getAnswer().toLowerCase().contains("surplus") || response.getAnswer().toLowerCase().contains("runway"));
    }

    @Test
    void testProcessMerchantQuery_HealthQuestion() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "Why is my business health score low?");

        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().toLowerCase().contains("health") || response.getAnswer().toLowerCase().contains("score") || response.getAnswer().toLowerCase().contains("burn"));
    }

    @Test
    void testProcessMerchantQuery_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            intelligenceService.processMerchantQuery(999L, "Can I afford inventory?");
        });
    }
}
