-- V30: Advisory Action Outcome & Adaptive Learning Schema

CREATE TABLE IF NOT EXISTS advisory_action_outcomes (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    plan_id BIGINT NOT NULL REFERENCES advisory_action_plans(id) ON DELETE CASCADE,
    step_id BIGINT NOT NULL REFERENCES advisory_action_plan_steps(id) ON DELETE CASCADE,
    evaluation_window VARCHAR(16) NOT NULL DEFAULT '30D',
    outcome_status VARCHAR(32) NOT NULL DEFAULT 'SUCCESSFUL',
    expected_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    score_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_outcome TEXT NOT NULL,
    actual_outcome TEXT NOT NULL,
    risk_reduction_expected NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_reduction_actual NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    financial_impact_expected NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    financial_impact_actual NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_advisory_action_outcome_merchant_step_window UNIQUE (merchant_id, step_id, evaluation_window)
);

CREATE TABLE IF NOT EXISTS advisory_action_learning (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    action_type VARCHAR(64) NOT NULL,
    context_type VARCHAR(64) NOT NULL,
    sample_count INT NOT NULL DEFAULT 1,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    learning_multiplier NUMERIC(5,3) NOT NULL DEFAULT 1.000,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    evidence_metrics TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_advisory_action_learning_type_context UNIQUE (merchant_id, action_type, context_type)
);

CREATE INDEX IF NOT EXISTS idx_advisory_action_outcomes_merchant ON advisory_action_outcomes(merchant_id);
CREATE INDEX IF NOT EXISTS idx_advisory_action_learning_merchant ON advisory_action_learning(merchant_id);

-- Seed demo outcome and learning records for merchant ID 1
INSERT INTO advisory_action_outcomes (
    merchant_id, plan_id, step_id, evaluation_window, outcome_status, expected_score,
    actual_score, score_variance_pct, expected_outcome, actual_outcome, risk_reduction_expected,
    risk_reduction_actual, financial_impact_expected, financial_impact_actual, effectiveness_score,
    confidence_status, evidence_metrics, assumptions
) VALUES (
    1, 1, 1, '30D', 'SUCCESSFUL', 94.20, 95.85, 1.75,
    '₹53,240 cash recovery within 7 days', '₹54,150 verified distributor payment via bank ingestion',
    88.50, 90.00, 53240.00, 54150.00, 93.60, 'HIGH',
    'Actual Cash Recovery: ₹54,150 vs ₹53,240 expected (+1.71%) | Risk Reduction: 90.00/100 | OBSERVED_ACTION_OUTCOME',
    'Bank feed ingestion verified transaction settlement within 30D window.'
);

INSERT INTO advisory_action_learning (
    merchant_id, action_type, context_type, sample_count, effectiveness_score,
    learning_multiplier, confidence_status, evidence_metrics
) VALUES (
    1, 'COLLECT_RECEIVABLES', '30D', 5, 93.60, 1.085, 'HIGH',
    'Sample Count: 5 | Avg Effectiveness: 93.60/100 | Bounded Multiplier: 1.085x | OBSERVED_ACTION_OUTCOME'
);
