-- V21: Financial Intervention Outcome & Effectiveness Schema

CREATE TABLE IF NOT EXISTS financial_intervention_outcomes (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    intervention_id BIGINT NOT NULL REFERENCES financial_interventions(id) ON DELETE CASCADE,
    intervention_type VARCHAR(64) NOT NULL,
    outcome_status VARCHAR(32) NOT NULL DEFAULT 'SUCCESSFUL', -- SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA
    evaluation_window VARCHAR(32) NOT NULL DEFAULT '30D', -- 7D, 30D, 60D, 90D
    expected_benefit VARCHAR(255) NOT NULL,
    actual_benefit VARCHAR(255) NOT NULL,
    benefit_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    actual_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    cash_impact_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expected_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    actual_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_impact_variance_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_intervention_window UNIQUE(intervention_id, evaluation_window)
);

CREATE INDEX IF NOT EXISTS idx_intervention_outcomes_merchant ON financial_intervention_outcomes(merchant_id);
CREATE INDEX IF NOT EXISTS idx_intervention_outcomes_status ON financial_intervention_outcomes(outcome_status);

-- Seed initial outcome record for merchant ID 1 (linking to intervention 1)
INSERT INTO financial_intervention_outcomes (merchant_id, intervention_id, intervention_type, outcome_status, evaluation_window, expected_benefit, actual_benefit, benefit_variance_pct, expected_cash_impact, actual_cash_impact, cash_impact_variance_pct, expected_risk_reduction, actual_risk_reduction, goal_impact_variance_pct, effectiveness_score, confidence_status, evidence_metrics, assumptions)
VALUES
(1, 1, 'COLLECT_RECEIVABLES', 'SUCCESSFUL', '30D', 'Recover ₹53,240 working capital within 7 days', 'OBSERVED_OUTCOME: Recovered ₹53,240 overdue distributor receivables', 100.00, 53240.00, 53240.00, 0.00, 80.00, 85.00, 15.00, 92.50, 'HIGH', 'Expected: ₹53,240 | Actual: ₹53,240 | Variance: 0.00% | Cash Runway Extended: +0.65 Months | ACTUAL', 'Outcome measured over 30-day post-completion evaluation window.');
