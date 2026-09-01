package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialDecisionOptionRepository extends JpaRepository<FinancialDecisionOption, Long> {
    List<FinancialDecisionOption> findByDecisionIdOrderByRankOrderAsc(Long decisionId);
}
