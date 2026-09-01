package com.flowwise.repository;

import com.flowwise.entity.FinancialPlanOptimizationFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialPlanOptimizationFactorRepository extends JpaRepository<FinancialPlanOptimizationFactor, Long> {
    List<FinancialPlanOptimizationFactor> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialPlanOptimizationFactor> findByMerchantIdAndPlanContext(Long merchantId, String planContext);
}
