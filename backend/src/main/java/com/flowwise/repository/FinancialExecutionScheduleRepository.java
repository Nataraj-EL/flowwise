package com.flowwise.repository;

import com.flowwise.entity.FinancialExecutionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialExecutionScheduleRepository extends JpaRepository<FinancialExecutionSchedule, Long> {
    List<FinancialExecutionSchedule> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<FinancialExecutionSchedule> findByMerchantIdAndHorizonAndStatus(Long merchantId, String horizon, String status);
    Optional<FinancialExecutionSchedule> findByMerchantIdAndHorizonAndScheduleKey(Long merchantId, String horizon, String scheduleKey);
    Optional<FinancialExecutionSchedule> findByIdAndMerchantId(Long id, Long merchantId);
}
