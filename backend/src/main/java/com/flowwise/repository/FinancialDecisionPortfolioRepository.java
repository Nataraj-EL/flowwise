package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionPortfolioRepository extends JpaRepository<FinancialDecisionPortfolio, Long> {
    List<FinancialDecisionPortfolio> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialDecisionPortfolio> findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(Long merchantId, String horizon);
    Optional<FinancialDecisionPortfolio> findByMerchantIdAndHorizonAndStatus(Long merchantId, String horizon, String status);
    Optional<FinancialDecisionPortfolio> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialDecisionPortfolio> findByMerchantIdAndHorizonAndPortfolioKey(Long merchantId, String horizon, String portfolioKey);
}
