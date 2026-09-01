package com.flowwise.repository;

import com.flowwise.entity.FinancialAnomaly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialAnomalyRepository extends JpaRepository<FinancialAnomaly, Long> {
    List<FinancialAnomaly> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialAnomaly> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialAnomaly> findByMerchantIdAndAnomalyKey(Long merchantId, String anomalyKey);
}
