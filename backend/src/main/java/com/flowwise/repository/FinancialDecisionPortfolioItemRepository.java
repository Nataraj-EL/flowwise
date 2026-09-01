package com.flowwise.repository;

import com.flowwise.entity.FinancialDecisionPortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialDecisionPortfolioItemRepository extends JpaRepository<FinancialDecisionPortfolioItem, Long> {
    List<FinancialDecisionPortfolioItem> findByPortfolioIdOrderByRankOrderAsc(Long portfolioId);
}
