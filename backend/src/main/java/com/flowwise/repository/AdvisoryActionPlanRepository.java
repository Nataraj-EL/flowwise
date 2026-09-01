package com.flowwise.repository;

import com.flowwise.entity.AdvisoryActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvisoryActionPlanRepository extends JpaRepository<AdvisoryActionPlan, Long> {
    List<AdvisoryActionPlan> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<AdvisoryActionPlan> findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(Long merchantId, String horizon);
    Optional<AdvisoryActionPlan> findByMerchantIdAndHorizonAndStatus(Long merchantId, String horizon, String status);
    Optional<AdvisoryActionPlan> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<AdvisoryActionPlan> findByMerchantIdAndHorizonAndPlanKey(Long merchantId, String horizon, String planKey);
}
