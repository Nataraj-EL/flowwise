package com.flowwise.repository;

import com.flowwise.entity.FinancialScenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialScenarioRepository extends JpaRepository<FinancialScenario, Long> {
    List<FinancialScenario> findByMerchantIdOrderByCreatedAtDesc(Long merchantId);
    List<FinancialScenario> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    List<FinancialScenario> findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(Long merchantId, String horizon);
    Optional<FinancialScenario> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialScenario> findByMerchantIdAndScenarioType(Long merchantId, String scenarioType);
    Optional<FinancialScenario> findByMerchantIdAndScenarioKey(Long merchantId, String scenarioKey);
}
