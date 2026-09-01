package com.flowwise.repository;

import com.flowwise.entity.FinancialStrategyLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialStrategyLearningRepository extends JpaRepository<FinancialStrategyLearning, Long> {
    List<FinancialStrategyLearning> findByMerchantIdOrderByEffectivenessScoreDesc(Long merchantId);
    Optional<FinancialStrategyLearning> findByMerchantIdAndStrategyKey(Long merchantId, String strategyKey);
    Optional<FinancialStrategyLearning> findByMerchantIdAndInterventionType(Long merchantId, String interventionType);
}
