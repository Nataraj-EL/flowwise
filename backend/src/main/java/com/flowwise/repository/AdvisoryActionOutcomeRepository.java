package com.flowwise.repository;

import com.flowwise.entity.AdvisoryActionOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvisoryActionOutcomeRepository extends JpaRepository<AdvisoryActionOutcome, Long> {
    List<AdvisoryActionOutcome> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<AdvisoryActionOutcome> findByMerchantIdAndEvaluationWindowOrderByEvaluatedAtDesc(Long merchantId, String evaluationWindow);
    Optional<AdvisoryActionOutcome> findByMerchantIdAndStepIdAndEvaluationWindow(Long merchantId, Long stepId, String evaluationWindow);
    List<AdvisoryActionOutcome> findByMerchantIdAndStep_ActionType(Long merchantId, String actionType);
}
