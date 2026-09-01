package com.flowwise.repository;

import com.flowwise.entity.EvaluationRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRunRepository extends JpaRepository<EvaluationRun, Long> {
    Optional<EvaluationRun> findTopByOrderByRunTimestampDesc();
}
