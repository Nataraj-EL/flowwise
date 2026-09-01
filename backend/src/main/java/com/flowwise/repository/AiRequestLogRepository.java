package com.flowwise.repository;

import com.flowwise.entity.AiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiRequestLogRepository extends JpaRepository<AiRequestLog, Long> {
    List<AiRequestLog> findTop20ByOrderByTimestampDesc();
}
