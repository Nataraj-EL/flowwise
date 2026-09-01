package com.flowwise.repository;

import com.flowwise.entity.EvaluationCaseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationCaseResultRepository extends JpaRepository<EvaluationCaseResult, Long> {
    List<EvaluationCaseResult> findByEvaluationRunId(Long runId);
}
