package com.flowwise;

import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.dto.ReconciliationSummaryDTO;
import com.flowwise.dto.TransactionDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FlowwiseIntelligenceService;
import com.flowwise.service.ReconciliationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReconciliationServiceTest {

    @Autowired
    private ReconciliationService reconciliationService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetReconciliationSummary_Success() {
        ReconciliationSummaryDTO summary = reconciliationService.getReconciliationSummary(1L);

        assertNotNull(summary);
        assertTrue(summary.getTotalTransactions() > 0);
        assertNotNull(summary.getReconciliationHealthPct());
        assertNotNull(summary.getIssues());
    }

    @Test
    void testReconcileAndIgnoreTransaction_Success() {
        TransactionDTO reconciled = reconciliationService.reconcileTransaction(1L, "Verified by audit team");
        assertNotNull(reconciled);

        TransactionDTO ignored = reconciliationService.ignoreTransaction(2L, "Demo transaction ignored");
        assertNotNull(ignored);
    }

    @Test
    void testAiGrounding_ReconciliationQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "Are there duplicate transactions awaiting review?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Reconciliation") || response.getAnswer().contains("Health") || response.getAnswer().contains("Unreviewed") || response.getAnswer().contains("Status"));
    }

    @Test
    void testGetReconciliation_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            reconciliationService.getReconciliationSummary(999L);
        });
    }

    @Test
    void testReconcile_TransactionNotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            reconciliationService.reconcileTransaction(999L, "Notes");
        });
    }
}
