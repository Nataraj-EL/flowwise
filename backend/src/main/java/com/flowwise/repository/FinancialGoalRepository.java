package com.flowwise.repository;

import com.flowwise.entity.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {
    List<FinancialGoal> findByMerchantIdOrderByTargetDateAsc(Long merchantId);
    List<FinancialGoal> findByMerchantIdAndStatusOrderByTargetDateAsc(Long merchantId, String status);
    Optional<FinancialGoal> findByIdAndMerchantId(Long id, Long merchantId);
}
