package com.flowwise.repository;

import com.flowwise.entity.FinancialDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionRepository extends JpaRepository<FinancialDecision, Long> {
    List<FinancialDecision> findByMerchantIdOrderByCreatedAtDesc(Long merchantId);
    List<FinancialDecision> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialDecision> findByMerchantIdOrderByDecisionDateDescCreatedAtDesc(Long merchantId);
    List<FinancialDecision> findByMerchantIdAndDecisionStatus(Long merchantId, String decisionStatus);
    List<FinancialDecision> findByMerchantIdAndActionIdAndDecisionStatusIn(Long merchantId, Long actionId, List<String> statuses);
    List<FinancialDecision> findByMerchantIdAndGoalIdAndDecisionStatusIn(Long merchantId, Long goalId, List<String> statuses);
    List<FinancialDecision> findByMerchantIdAndStatusOrderByEvaluatedAtDesc(Long merchantId, String status);
    Optional<FinancialDecision> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialDecision> findByMerchantIdAndDecisionKey(Long merchantId, String decisionKey);
}
