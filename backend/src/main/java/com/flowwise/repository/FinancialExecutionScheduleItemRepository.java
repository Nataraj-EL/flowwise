package com.flowwise.repository;

import com.flowwise.entity.FinancialExecutionScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialExecutionScheduleItemRepository extends JpaRepository<FinancialExecutionScheduleItem, Long> {
    List<FinancialExecutionScheduleItem> findByScheduleIdOrderBySequenceOrderAsc(Long scheduleId);
    List<FinancialExecutionScheduleItem> findByScheduleIdAndReadinessStatusOrderBySequenceOrderAsc(Long scheduleId, String readinessStatus);
}
