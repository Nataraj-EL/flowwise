package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionAnalysisRepository extends JpaRepository<FinancialDecisionAnalysis, Long> {
    List<FinancialDecisionAnalysis> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialDecisionAnalysis> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialDecisionAnalysis> findByMerchantIdAndAnalysisKey(Long merchantId, String analysisKey);
}
