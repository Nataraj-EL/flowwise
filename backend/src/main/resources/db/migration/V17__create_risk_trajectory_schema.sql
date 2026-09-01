-- V17: Financial Risk Trajectory & Monitoring Schema

CREATE TABLE IF NOT EXISTS risk_trajectory_snapshots (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    risk_key VARCHAR(64) NOT NULL,
    risk_type VARCHAR(64) NOT NULL,
    trajectory_direction VARCHAR(32) NOT NULL DEFAULT 'STABLE', -- IMPROVING, STABLE, WORSENING, RESOLVED, INSUFFICIENT_DATA
    severity_transition VARCHAR(64) NOT NULL DEFAULT 'UNCHANGED', -- UNCHANGED, ESCALATED_MEDIUM_TO_HIGH, DE_ESCALATED_HIGH_TO_MEDIUM, etc.
    escalation_velocity NUMERIC(7,2) NOT NULL DEFAULT 0.00,
    observed_snapshots_count INT NOT NULL DEFAULT 1,
    baseline_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    current_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    score_delta NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    resolution_time_hours NUMERIC(7,2) NOT NULL DEFAULT 0.00,
    recurrence_count INT NOT NULL DEFAULT 0,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_risk_trajectory_merchant ON risk_trajectory_snapshots(merchant_id);
CREATE INDEX IF NOT EXISTS idx_risk_trajectory_key ON risk_trajectory_snapshots(risk_key);
CREATE INDEX IF NOT EXISTS idx_risk_trajectory_direction ON risk_trajectory_snapshots(trajectory_direction);

-- Seed initial trajectory snapshot data for merchant ID 1
INSERT INTO risk_trajectory_snapshots (merchant_id, risk_key, risk_type, trajectory_direction, severity_transition, escalation_velocity, observed_snapshots_count, baseline_value, current_value, score_delta, resolution_time_hours, recurrence_count)
VALUES
(1, 'RSK_M1_RECEIVABLES_OVERDUE_30D', 'RECEIVABLES', 'WORSENING', 'ESCALATED_MEDIUM_TO_HIGH', 1.35, 3, 124528.00, 165000.00, 40472.00, 0.00, 1),
(1, 'RSK_M1_PAYABLES_PRESSURE_7D', 'PAYABLES', 'STABLE', 'UNCHANGED', 0.00, 2, 75000.00, 95000.00, 20000.00, 0.00, 0);
