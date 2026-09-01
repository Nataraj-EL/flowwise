package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialDecisionLearningRepository extends JpaRepository<FinancialDecisionLearning, Long> {
    List<FinancialDecisionLearning> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialDecisionLearning> findByMerchantIdAndDecisionTypeAndContextType(Long merchantId, String decisionType, String contextType);
    Optional<FinancialDecisionLearning> findByMerchantIdAndDecisionType(Long merchantId, String decisionType);
}
