-- V23: Financial Plan Synthesis & Adaptive Planning Schema

CREATE TABLE IF NOT EXISTS financial_plans (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    plan_key VARCHAR(128) NOT NULL,
    horizon VARCHAR(32) NOT NULL DEFAULT '30D', -- 7D, 30D, 90D
    status VARCHAR(32) NOT NULL DEFAULT 'DRAFT', -- DRAFT, ACTIVE, COMPLETED, ARCHIVED
    overall_plan_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    primary_focus_area VARCHAR(255) NOT NULL,
    summary_explanation TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_plan_key UNIQUE(merchant_id, plan_key)
);

CREATE TABLE IF NOT EXISTS financial_plan_items (
    id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL REFERENCES financial_plans(id) ON DELETE CASCADE,
    item_key VARCHAR(128) NOT NULL,
    intervention_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_protection_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    financial_impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_alignment_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    historical_effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    expected_benefit VARCHAR(255) NOT NULL,
    risk_if_ignored VARCHAR(255) NOT NULL,
    horizon VARCHAR(32) NOT NULL DEFAULT '30D',
    rank_order INT NOT NULL DEFAULT 1,
    evidence_metrics TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_plans_merchant_horizon ON financial_plans(merchant_id, horizon);
CREATE INDEX IF NOT EXISTS idx_plan_items_plan ON financial_plan_items(plan_id);

-- Seed initial financial plan for merchant ID 1
INSERT INTO financial_plans (merchant_id, plan_key, horizon, status, overall_plan_score, primary_focus_area, summary_explanation, assumptions)
VALUES
(1, 'PLAN_1_30D_V1', '30D', 'ACTIVE', 86.25, 'Accelerate Overdue Receivables & Audit Expense Spikes', '30-Day Synthesis Financial Plan: Priority focused on recovering ₹53,240 distributor receivables and containing vendor inventory cost surge.', 'Plan items synthesize risks, anomalies, correlations, interventions, and learned strategy multipliers without automated fund execution.');

INSERT INTO financial_plan_items (plan_id, item_key, intervention_type, title, description, priority_score, risk_protection_score, financial_impact_score, urgency_score, goal_alignment_score, historical_effectiveness_score, confidence_status, expected_benefit, risk_if_ignored, horizon, rank_order, evidence_metrics)
VALUES
(1, 'ITEM_1_COLLECT', 'COLLECT_RECEIVABLES', 'Accelerate Distributor Overdue Collections', 'Prioritize collection of ₹53,240 overdue distributor receivables to safeguard 30-day liquid cash runway.', 93.04, 85.00, 90.00, 85.00, 70.00, 92.50, 'HIGH', 'Recover ₹53,240 working capital within 7 days', 'Liquidity shortfall risk during upcoming payable cycle', '30D', 1, 'Risk Protection: 85.00 | Impact: 90.00 | Urgency: 85.00 | Strategy Multiplier: 1.085x | ACTUAL'),
(1, 'ITEM_2_REDUCE', 'REDUCE_EXPENSE', 'Audit Supplier Inventory Expense Spike', 'Conduct immediate audit of +38.50% expense surge in vendor inventory payables.', 78.45, 75.00, 82.00, 75.00, 60.00, 75.00, 'HIGH', 'Prevent unnecessary ₹35,000 cash burn expansion', 'Unchecked operating cash bleed reducing cash reserves', '30D', 2, 'Risk Protection: 75.00 | Impact: 82.00 | Urgency: 75.00 | Strategy Multiplier: 1.000x | ACTUAL');
