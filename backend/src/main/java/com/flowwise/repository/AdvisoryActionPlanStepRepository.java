package com.flowwise.repository;

import com.flowwise.entity.AdvisoryActionPlanStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisoryActionPlanStepRepository extends JpaRepository<AdvisoryActionPlanStep, Long> {
    List<AdvisoryActionPlanStep> findByPlanIdOrderByStepNumberAsc(Long planId);
}
