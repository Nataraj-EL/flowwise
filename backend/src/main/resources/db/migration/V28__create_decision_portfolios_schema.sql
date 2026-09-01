-- V28: Financial Decision Portfolio & Continuous Advisory Optimization Schema

CREATE TABLE IF NOT EXISTS financial_decision_portfolios (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    portfolio_key VARCHAR(128) NOT NULL,
    horizon VARCHAR(16) NOT NULL DEFAULT '30D',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    overall_portfolio_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    primary_focus_area VARCHAR(255) NOT NULL,
    expected_benefit TEXT NOT NULL,
    risk_if_ignored TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_decision_portfolio_merchant_horizon UNIQUE (merchant_id, horizon, portfolio_key)
);

CREATE TABLE IF NOT EXISTS financial_decision_portfolio_items (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL REFERENCES financial_decision_portfolios(id) ON DELETE CASCADE,
    item_key VARCHAR(128) NOT NULL,
    decision_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_protection_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    financial_impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    historical_effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_alignment_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    expected_benefit TEXT NOT NULL,
    risk_if_ignored TEXT NOT NULL,
    rank_order INT NOT NULL DEFAULT 1,
    evidence_metrics TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_decision_portfolios_merchant ON financial_decision_portfolios(merchant_id);
CREATE INDEX IF NOT EXISTS idx_decision_portfolio_items_portfolio ON financial_decision_portfolio_items(portfolio_id);

-- Seed demo portfolio data for merchant ID 1
INSERT INTO financial_decision_portfolios (
    merchant_id, portfolio_key, horizon, status, overall_portfolio_score, risk_score,
    impact_score, urgency_score, confidence_score, primary_focus_area, expected_benefit,
    risk_if_ignored, evidence_metrics, assumptions
) VALUES (
    1, 'PORTFOLIO_30D_REC_1', '30D', 'ACTIVE', 91.85, 88.50, 95.00, 90.00, 89.00,
    'Accelerate Overdue Receivables Recovery & Audit Logistics Expense Creep',
    'Immediate ₹53,240 distributor cash recovery + ₹35,000 container cost reduction.',
    'Liquidity shortfall within 30 days if overdue distributor balance defaults.',
    'Risk Protection: 88.50/100 | Financial Impact: 95.00/100 | Strategy Multiplier: 1.085x | ADVISORY_PORTFOLIO',
    'Portfolio items synthesized across risks, anomalies, correlations, interventions, outcomes & learned multipliers.'
);

INSERT INTO financial_decision_portfolio_items (
    portfolio_id, item_key, decision_type, title, description, priority_score,
    risk_protection_score, financial_impact_score, urgency_score, historical_effectiveness_score,
    goal_alignment_score, confidence_status, expected_benefit, risk_if_ignored, rank_order, evidence_metrics
) VALUES
(1, 'ITEM_1_COLLECT', 'COLLECT_RECEIVABLES', 'Accelerate Overdue Distributor Receivables',
 'Execute structured collection follow-ups for ₹53,240 overdue invoices.', 92.45, 88.50, 95.00, 90.00, 92.80, 95.00,
 'HIGH', 'Immediate ₹53,240 cash inflow within 7 days.', 'Working capital deficit in 30 days.', 1,
 'Rank #1 Priority | Composite Score: 92.45/100 | ADVISORY_PORTFOLIO'),
(1, 'ITEM_2_EXPENSE', 'REDUCE_EXPENSE', 'Audit Logistics Vendor Inventory Surge',
 'Contain logistics vendor surge of ₹35,000 via container rate verification.', 84.10, 80.00, 85.00, 82.00, 86.50, 88.00,
 'HIGH', '₹35,000 monthly cost containment.', 'Operational margin reduction.', 2,
 'Rank #2 Priority | Composite Score: 84.10/100 | ADVISORY_PORTFOLIO');
