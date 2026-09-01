package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialDecisionService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialDecisionServiceTest {

    @Autowired
    private FinancialDecisionService decisionService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testDecisionLifecycle_AcceptCompleteOutcome() {
        CreateDecisionRequestDTO req = new CreateDecisionRequestDTO(
                null, null, "CASH_MANAGEMENT",
                "Vendor Payment Deferral",
                "Defer non-critical inventory payment",
                "Approved by CFO",
                LocalDate.now()
        );

        FinancialDecisionDTO created = decisionService.createDecision(1L, req);
        assertEquals("PENDING", created.getDecisionStatus());

        // Accept Decision
        FinancialDecisionDTO accepted = decisionService.acceptDecision(1L, created.getId(), "Accepted by merchant");
        assertEquals("ACCEPTED", accepted.getDecisionStatus());

        // Complete Decision
        FinancialDecisionDTO completed = decisionService.completeDecision(1L, created.getId(), "Payment deferred successfully");
        assertEquals("COMPLETED", completed.getDecisionStatus());

        // Record Outcome (allowed ONLY on COMPLETED)
        DecisionOutcomeDTO outcome = new DecisionOutcomeDTO("POSITIVE", "Saved ₹40,000 cash buffer for payroll.");
        FinancialDecisionDTO withOutcome = decisionService.recordOutcome(1L, created.getId(), outcome);
        assertEquals("POSITIVE", withOutcome.getOutcomeStatus());
    }

    @Test
    void testInvalidLifecycleTransition_ThrowsException() {
        CreateDecisionRequestDTO req = new CreateDecisionRequestDTO(
                null, null, "EXPENSE_REDUCTION",
                "Software Subscription Cancellation",
                "Cancel unused SaaS licenses",
                "Notes",
                LocalDate.now()
        );

        FinancialDecisionDTO created = decisionService.createDecision(1L, req);

        // Decline Decision
        decisionService.declineDecision(1L, created.getId(), "Declined");

        // Attempt invalid transition: DECLINED -> COMPLETED
        assertThrows(IllegalStateException.class, () -> {
            decisionService.completeDecision(1L, created.getId(), "Complete");
        });
    }

    @Test
    void testOutcomeBeforeCompletion_ThrowsException() {
        CreateDecisionRequestDTO req = new CreateDecisionRequestDTO(
                null, null, "DEBT_REDUCTION",
                "Early Payment Discount",
                "Accept 2% discount",
                "Notes",
                LocalDate.now()
        );

        FinancialDecisionDTO created = decisionService.createDecision(1L, req);

        // Outcome on PENDING decision must be rejected
        assertThrows(IllegalStateException.class, () -> {
            decisionService.recordOutcome(1L, created.getId(), new DecisionOutcomeDTO("POSITIVE", "Notes"));
        });
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            decisionService.acceptDecision(2L, 1L, "Notes"); // Merchant 2 accessing Merchant 1's decision
        });
    }

    @Test
    void testGetDecisionSummary_Success() {
        DecisionSummaryDTO summary = decisionService.getDecisionSummary(1L);
        assertNotNull(summary);
        assertTrue(summary.getTotalDecisions() > 0);
        assertNotNull(summary.getSuccessRatePct());
    }

    @Test
    void testAiGrounding_DecisionQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "What decisions have I made?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Decision") || response.getAnswer().contains("Accepted") || response.getAnswer().contains("Performance") || response.getAnswer().contains("Summary"));
    }
}
