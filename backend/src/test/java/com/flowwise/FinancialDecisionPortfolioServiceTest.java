package com.flowwise;

import com.flowwise.dto.FinancialDecisionPortfolioDTO;
import com.flowwise.dto.FinancialDecisionPortfolioSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialDecisionPortfolioService;
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
public class FinancialDecisionPortfolioServiceTest {

    @Autowired
    private FinancialDecisionPortfolioService portfolioService;

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
    @DisplayName("Evaluate Portfolio - Returns Valid Portfolio & Deterministic Ranking")
    void testEvaluatePortfolio_Success() {
        FinancialDecisionPortfolioDTO portfolio = portfolioService.evaluatePortfolio(testMerchant.getId(), "30D");

        assertNotNull(portfolio);
        assertEquals("30D", portfolio.getHorizon());
        assertEquals("ACTIVE", portfolio.getStatus());
        assertTrue(portfolio.getOverallPortfolioScore().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(portfolio.getItems());
        assertFalse(portfolio.getItems().isEmpty());

        // Item 1 should have highest priority score
        assertEquals(1, portfolio.getItems().get(0).getRankOrder());
        assertTrue(portfolio.getItems().get(0).getPriorityScore().compareTo(new BigDecimal("75.00")) > 0);
    }

    @Test
    @DisplayName("Get Portfolio Summary - Valid Summary & Merchant Isolation")
    void testGetPortfolioSummary_Success() {
        FinancialDecisionPortfolioSummaryDTO summary = portfolioService.getPortfolioSummary(testMerchant.getId(), "30D");

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalPortfoliosCount() > 0);
        assertNotNull(summary.getActivePortfolio());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Invalid Merchant")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.getPortfolioSummary(99999L, "30D");
        });
    }
}
