package com.flowwise.repository;

import com.flowwise.entity.FinancialPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialPlanRepository extends JpaRepository<FinancialPlan, Long> {
    List<FinancialPlan> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialPlan> findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(Long merchantId, String horizon);
    Optional<FinancialPlan> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialPlan> findByMerchantIdAndPlanKey(Long merchantId, String planKey);
    Optional<FinancialPlan> findByMerchantIdAndHorizonAndStatus(Long merchantId, String horizon, String status);
}
