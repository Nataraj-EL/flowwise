package com.flowwise.repository;

import com.flowwise.entity.FinancialDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionRepository extends JpaRepository<FinancialDecision, Long> {
    List<FinancialDecision> findByMerchantIdOrderByDecisionDateDescCreatedAtDesc(Long merchantId);
    List<FinancialDecision> findByMerchantIdAndDecisionStatusOrderByDecisionDateDesc(Long merchantId, String decisionStatus);
    Optional<FinancialDecision> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialDecision> findByMerchantIdAndActionIdAndDecisionStatusIn(Long merchantId, Long actionId, List<String> statuses);
    Optional<FinancialDecision> findByMerchantIdAndGoalIdAndDecisionStatusIn(Long merchantId, Long goalId, List<String> statuses);
}
