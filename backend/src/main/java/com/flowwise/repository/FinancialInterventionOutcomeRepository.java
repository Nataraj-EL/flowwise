package com.flowwise.repository;

import com.flowwise.entity.FinancialInterventionOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialInterventionOutcomeRepository extends JpaRepository<FinancialInterventionOutcome, Long> {
    List<FinancialInterventionOutcome> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialInterventionOutcome> findByMerchantIdAndEvaluationWindow(Long merchantId, String evaluationWindow);
    Optional<FinancialInterventionOutcome> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialInterventionOutcome> findByMerchantIdAndInterventionIdAndEvaluationWindow(Long merchantId, Long interventionId, String evaluationWindow);
}
