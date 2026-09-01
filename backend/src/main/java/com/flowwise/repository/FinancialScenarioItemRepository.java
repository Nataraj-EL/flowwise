package com.flowwise.repository;

import com.flowwise.entity.FinancialScenarioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialScenarioItemRepository extends JpaRepository<FinancialScenarioItem, Long> {
    List<FinancialScenarioItem> findByScenarioIdOrderByRankOrderAsc(Long scenarioId);
}
