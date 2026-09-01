package com.flowwise;

import com.flowwise.dto.TransactionDTO;
import com.flowwise.dto.TransactionSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.TransactionService;
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
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testGetMerchantTransactions_Success() {
        List<TransactionDTO> transactions = transactionService.getMerchantTransactions(1L, null, null, null);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertTrue(transactions.size() >= 12);
    }

    @Test
    void testGetMerchantTransactions_FilterByType() {
        List<TransactionDTO> creditTxns = transactionService.getMerchantTransactions(1L, "CREDIT", null, null);
        assertNotNull(creditTxns);
        assertTrue(creditTxns.stream().allMatch(t -> "CREDIT".equalsIgnoreCase(t.getType())));
    }

    @Test
    void testGetTransactionSummary_Success() {
        TransactionSummaryDTO summary = transactionService.getTransactionSummary(1L);

        assertNotNull(summary);
        assertTrue(summary.getTransactionCount() >= 12);
        assertNotNull(summary.getTotalCredits());
        assertNotNull(summary.getTotalDebits());
        assertNotNull(summary.getNetCashFlow());
        assertFalse(summary.getCategoryTotals().isEmpty());

        // Net Cash Flow = Total Credits - Total Debits
        assertEquals(0, summary.getTotalCredits().subtract(summary.getTotalDebits()).compareTo(summary.getNetCashFlow()));
    }

    @Test
    void testGetTransactionSummary_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.getTransactionSummary(999L);
        });
    }
}
