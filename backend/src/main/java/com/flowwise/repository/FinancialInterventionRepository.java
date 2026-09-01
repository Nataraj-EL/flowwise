package com.flowwise.repository;

import com.flowwise.entity.FinancialIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialInterventionRepository extends JpaRepository<FinancialIntervention, Long> {
    List<FinancialIntervention> findByMerchantIdOrderByPriorityScoreDesc(Long merchantId);
    Optional<FinancialIntervention> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<FinancialIntervention> findByMerchantIdAndInterventionKey(Long merchantId, String interventionKey);
}
