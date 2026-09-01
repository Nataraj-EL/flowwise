-- V24: Financial Plan Outcome & Adaptive Optimization Schema

CREATE TABLE IF NOT EXISTS financial_plan_outcomes (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    plan_id BIGINT NOT NULL REFERENCES financial_plans(id) ON DELETE CASCADE,
    horizon VARCHAR(32) NOT NULL DEFAULT '30D', -- 7D, 30D, 60D, 90D
    outcome_status VARCHAR(32) NOT NULL DEFAULT 'SUCCESSFUL', -- SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA
    expected_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    score_variance_pct NUMERIC(8,2) NOT NULL DEFAULT 0.00,
    expected_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    actual_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    cash_variance_pct NUMERIC(8,2) NOT NULL DEFAULT 0.00,
    risk_reduction_expected NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_reduction_actual NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_progress_expected NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_progress_actual NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_plan_outcome_eval UNIQUE(merchant_id, plan_id, horizon)
);

CREATE TABLE IF NOT EXISTS financial_plan_optimization_factors (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    plan_context VARCHAR(128) NOT NULL, -- e.g., '30D', '7D', '90D', 'OVERDUE_COLLECTION'
    sample_count INT NOT NULL DEFAULT 0,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    optimization_multiplier NUMERIC(6,3) NOT NULL DEFAULT 1.000, -- Bounded 0.900 - 1.100
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_plan_opt_context UNIQUE(merchant_id, plan_context)
);

CREATE INDEX IF NOT EXISTS idx_plan_outcomes_merchant ON financial_plan_outcomes(merchant_id, horizon);
CREATE INDEX IF NOT EXISTS idx_plan_opt_merchant ON financial_plan_optimization_factors(merchant_id);

-- Seed initial outcome and optimization factor for merchant ID 1
INSERT INTO financial_plan_outcomes (
    merchant_id, plan_id, horizon, outcome_status, expected_score, actual_score, score_variance_pct,
    expected_cash_impact, actual_cash_impact, cash_variance_pct, risk_reduction_expected, risk_reduction_actual,
    goal_progress_expected, goal_progress_actual, effectiveness_score, confidence_status, evidence_metrics, assumptions
) VALUES (
    1, 1, '30D', 'SUCCESSFUL', 86.25, 89.50, 3.77,
    53240.00, 56000.00, 5.18, 25.00, 28.50,
    30.00, 35.00, 91.50, 'HIGH',
    'Expected Cash Impact: ₹53,240.00 | Actual Cash Impact: ₹56,000.00 | Cash Impact Variance: +5.18% | OBSERVED_PLAN_OUTCOME',
    'Evaluated over full 30D post-plan horizon using bank account credit entries and verified distributor collections.'
);

INSERT INTO financial_plan_optimization_factors (
    merchant_id, plan_context, sample_count, effectiveness_score, optimization_multiplier, confidence_status
) VALUES (
    1, '30D', 5, 91.50, 1.065, 'HIGH'
);
