-- V27: Financial Decision Outcome & Learning Schema

CREATE TABLE IF NOT EXISTS financial_decision_outcomes (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    decision_id BIGINT NOT NULL REFERENCES financial_decisions(id) ON DELETE CASCADE,
    outcome_status VARCHAR(32) NOT NULL DEFAULT 'SUCCESSFUL',
    evaluation_window VARCHAR(16) NOT NULL DEFAULT '30D',
    expected_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    score_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    actual_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    cash_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_goal_impact NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_goal_impact NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_decision_outcome_merchant_decision_window UNIQUE (merchant_id, decision_id, evaluation_window)
);

CREATE TABLE IF NOT EXISTS financial_decision_learning (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    decision_type VARCHAR(64) NOT NULL,
    context_type VARCHAR(64) NOT NULL,
    sample_count INT NOT NULL DEFAULT 1,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    learning_multiplier NUMERIC(5,3) NOT NULL DEFAULT 1.000,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    evidence_metrics TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_decision_learning_type_context UNIQUE (merchant_id, decision_type, context_type)
);

CREATE INDEX IF NOT EXISTS idx_decision_outcomes_merchant ON financial_decision_outcomes(merchant_id);
CREATE INDEX IF NOT EXISTS idx_decision_outcomes_decision ON financial_decision_outcomes(decision_id);
CREATE INDEX IF NOT EXISTS idx_decision_learning_merchant ON financial_decision_learning(merchant_id);

-- Seed demo outcome and learning records for merchant ID 1
INSERT INTO financial_decision_outcomes (
    merchant_id, decision_id, outcome_status, evaluation_window, expected_score, actual_score,
    score_variance_pct, expected_cash_impact, actual_cash_impact, cash_variance_pct,
    expected_risk_reduction, actual_risk_reduction, expected_goal_impact, actual_goal_impact,
    effectiveness_score, confidence_status, evidence_metrics, assumptions
) VALUES (
    1, 4, 'SUCCESSFUL', '30D', 92.45, 94.10, 1.78, 53240.00, 54150.00, 1.71,
    88.50, 90.00, 95.00, 96.00, 92.80, 'HIGH',
    'Observed Score: 94.10/100 | Actual Cash Recovery: ₹54,150.00 (+1.71%) | Risk Reduction: 90.00/100 | OBSERVED_DECISION_OUTCOME',
    'Assumes full distributor settlement verified via bank account transaction ingestion.'
);

INSERT INTO financial_decision_learning (
    merchant_id, decision_type, context_type, sample_count, effectiveness_score,
    learning_multiplier, confidence_status, evidence_metrics
) VALUES (
    1, 'INTERVENTION_EXECUTION', '30D', 5, 92.80, 1.085, 'HIGH',
    'Sample Count: 5 | Avg Effectiveness: 92.80/100 | Bounded Multiplier: 1.085x | OBSERVED_DECISION_OUTCOME'
);
