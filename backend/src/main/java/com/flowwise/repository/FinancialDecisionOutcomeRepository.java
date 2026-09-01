package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionOutcomeRepository extends JpaRepository<FinancialDecisionOutcome, Long> {
    List<FinancialDecisionOutcome> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialDecisionOutcome> findByMerchantIdAndEvaluationWindowOrderByEvaluatedAtDesc(Long merchantId, String evaluationWindow);
    Optional<FinancialDecisionOutcome> findByMerchantIdAndDecisionIdAndEvaluationWindow(Long merchantId, Long decisionId, String evaluationWindow);
    List<FinancialDecisionOutcome> findByMerchantIdAndDecision_DecisionType(Long merchantId, String decisionType);
}
