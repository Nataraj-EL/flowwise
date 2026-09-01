package com.flowwise.repository;

import com.flowwise.entity.FinancialRiskAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialRiskAlertRepository extends JpaRepository<FinancialRiskAlert, Long> {
    List<FinancialRiskAlert> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialRiskAlert> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialRiskAlert> findByMerchantIdAndRiskKey(Long merchantId, String riskKey);
}
