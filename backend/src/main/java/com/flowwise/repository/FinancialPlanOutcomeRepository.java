package com.flowwise.repository;

import com.flowwise.entity.FinancialPlanOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialPlanOutcomeRepository extends JpaRepository<FinancialPlanOutcome, Long> {
    List<FinancialPlanOutcome> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialPlanOutcome> findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(Long merchantId, String horizon);
    Optional<FinancialPlanOutcome> findByMerchantIdAndPlanIdAndHorizon(Long merchantId, Long planId, String horizon);
}
