-- V20: Financial Intervention Prioritization & Action Planning Schema

CREATE TABLE IF NOT EXISTS financial_interventions (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    intervention_key VARCHAR(64) NOT NULL,
    intervention_type VARCHAR(64) NOT NULL, -- COLLECT_RECEIVABLES, REDUCE_EXPENSE, MANAGE_PAYABLES, BUILD_CASH_RESERVE, PROTECT_GOAL, MITIGATE_RISK, INVESTIGATE_ANOMALY
    title VARCHAR(128) NOT NULL,
    description TEXT NOT NULL,
    priority_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    expected_benefit VARCHAR(255) NOT NULL,
    risk_if_ignored VARCHAR(255) NOT NULL,
    effort_level VARCHAR(32) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH
    linked_risk_id BIGINT,
    linked_anomaly_id BIGINT,
    linked_correlation_id BIGINT,
    linked_goal_id BIGINT,
    status VARCHAR(32) NOT NULL DEFAULT 'OPEN', -- OPEN, ACKNOWLEDGED, COMPLETED, DISMISSED
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_interventions_merchant ON financial_interventions(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_interventions_key ON financial_interventions(intervention_key);
CREATE INDEX IF NOT EXISTS idx_financial_interventions_score ON financial_interventions(priority_score);

-- Seed initial intervention records for merchant ID 1
INSERT INTO financial_interventions (merchant_id, intervention_key, intervention_type, title, description, priority_score, urgency_score, impact_score, confidence_status, expected_benefit, risk_if_ignored, effort_level, status, evidence_metrics, assumptions)
VALUES
(1, 'INT_M1_COLLECT_OVERDUE', 'COLLECT_RECEIVABLES', 'Accelerate Distributor Overdue Collections', 'Prioritize collection of ₹53,240 overdue distributor receivables to safeguard 30-day liquid cash runway.', 88.40, 85.00, 90.00, 'HIGH', 'Recover ₹53,240 working capital within 7 days', 'Liquidity shortfall risk during upcoming payable cycle', 'LOW', 'OPEN', 'Overdue Ratio: +18.50% | Outstanding: ₹53,240 | Target Runway Impact: +0.6 Months | ACTUAL', 'Assumes distributor payment terms can be accelerated via automated reminder notices.'),
(1, 'INT_M1_REDUCE_EXPENSE_SURGE', 'REDUCE_EXPENSE', 'Audit Supplier Inventory Expense Spike', 'Conduct immediate audit of +38.50% expense surge in vendor inventory payables.', 79.20, 75.00, 82.00, 'HIGH', 'Prevent unnecessary ₹35,000 cash burn expansion', 'Unchecked operating cash bleed reducing cash reserves', 'MEDIUM', 'OPEN', 'Expense Spike Anomaly: +38.50% | Deviation: ₹35,000 | Baseline: ₹90,800 | ACTUAL', 'Assumes vendor invoice prices can be negotiated down to baseline levels.');
