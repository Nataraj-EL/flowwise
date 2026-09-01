-- V13: Financial Scenario & Forecast Intelligence Schema

CREATE TABLE IF NOT EXISTS financial_scenarios (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    scenario_type VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    revenue_modifier_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    expense_modifier_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    receivable_collection_pct NUMERIC(5,2) NOT NULL DEFAULT 100.00,
    payable_acceleration_pct NUMERIC(5,2) NOT NULL DEFAULT 100.00,
    projected_7d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_30d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_60d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_90d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    runway_months NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_status VARCHAR(32) NOT NULL DEFAULT 'FEASIBLE',
    goal_achievable BOOLEAN NOT NULL DEFAULT TRUE,
    assumptions TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_scenarios_merchant ON financial_scenarios(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_scenarios_type ON financial_scenarios(scenario_type);

-- Seed initial demo scenario evaluations for merchant ID 1
INSERT INTO financial_scenarios (merchant_id, scenario_type, name, description, revenue_modifier_pct, expense_modifier_pct, receivable_collection_pct, payable_acceleration_pct, projected_7d_cash, projected_30d_cash, projected_60d_cash, projected_90d_cash, runway_months, risk_status, goal_achievable, assumptions)
VALUES
(1, 'BASELINE', 'Baseline Current Operations', 'Projects cash flow using current average inflows and historical due-date collections/payables.', 0.00, 0.00, 100.00, 100.00, 520000.00, 610000.00, 740000.00, 880000.00, 12.50, 'FEASIBLE', TRUE, 'Historical inflow: ₹185,000/mo, Outflow: ₹136,000/mo. Current liquid reserves: ₹485,000.'),
(1, 'CAUTIOUS', 'Cautious Market Growth', 'Models a 10% dip in monthly inflows with a 5% increase in operational expenses and 80% receivables collection rate.', -10.00, 5.00, 80.00, 100.00, 495000.00, 540000.00, 615000.00, 690000.00, 8.20, 'FEASIBLE', TRUE, 'Inflow modifier: -10%, Outflow modifier: +5%, Collection rate: 80%.'),
(1, 'STRESS', 'Stress Liquidity Contraction', 'Simulates a 25% revenue drop, 15% surge in expenses, and 50% delay in receivable collections.', -25.00, 15.00, 50.00, 100.00, 440000.00, 410000.00, 360000.00, 310000.00, 4.10, 'CAUTION', FALSE, 'Inflow modifier: -25%, Outflow modifier: +15%, Collection rate: 50%. Goal target at risk.');
