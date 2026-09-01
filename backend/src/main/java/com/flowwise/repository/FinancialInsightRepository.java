package com.flowwise.repository;

import com.flowwise.entity.FinancialInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialInsightRepository extends JpaRepository<FinancialInsight, Long> {
    List<FinancialInsight> findByMerchantIdOrderByCreatedAtDesc(Long merchantId);
    List<FinancialInsight> findByMerchantIdAndStatusOrderByCreatedAtDesc(Long merchantId, String status);
    Optional<FinancialInsight> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialInsight> findByMerchantIdAndInsightTypeAndDetectedPeriodAndStatusIn(
            Long merchantId, String insightType, String detectedPeriod, List<String> statuses);
}
