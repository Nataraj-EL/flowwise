package com.flowwise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public Map<String, Object> getHealthStatus() {
        return Map.of(
            "status", "UP",
            "service", "Flowwise Engine Backend",
            "environment", "DEMO",
            "timestamp", System.currentTimeMillis()
        );
    }
}
