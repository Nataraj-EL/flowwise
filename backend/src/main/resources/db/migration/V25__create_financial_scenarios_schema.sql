-- V25: Financial Scenario Simulation & What-If Planning Schema

ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS scenario_key VARCHAR(128) NOT NULL DEFAULT 'SCENARIO_DEFAULT';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS scenario_name VARCHAR(255) NOT NULL DEFAULT 'Financial Scenario';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS horizon VARCHAR(32) NOT NULL DEFAULT '30D';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS status VARCHAR(32) NOT NULL DEFAULT 'EVALUATED';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS baseline_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS projected_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS score_delta NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS projected_cash_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS projected_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS projected_goal_impact NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS evidence_metrics TEXT NOT NULL DEFAULT 'SIMULATED_ESTIMATE';
ALTER TABLE financial_scenarios ADD COLUMN IF NOT EXISTS evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE IF NOT EXISTS financial_scenario_items (
    id BIGSERIAL PRIMARY KEY,
    scenario_id BIGINT NOT NULL REFERENCES financial_scenarios(id) ON DELETE CASCADE,
    intervention_type VARCHAR(64) NOT NULL,
    intervention_id BIGINT,
    rank_order INT NOT NULL DEFAULT 1,
    projected_impact NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_risk_reduction NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    projected_goal_impact NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    evidence_metrics TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_scenarios_merchant_horizon ON financial_scenarios(merchant_id, horizon);
CREATE INDEX IF NOT EXISTS idx_scenario_items_scenario ON financial_scenario_items(scenario_id);

-- Seed initial simulation scenario for merchant ID 1
INSERT INTO financial_scenarios (
    merchant_id, scenario_type, name, scenario_key, scenario_name, horizon, status, baseline_score, projected_score,
    score_delta, projected_cash_impact, projected_risk_reduction, projected_goal_impact, confidence_status,
    assumptions, evidence_metrics
) VALUES (
    1, 'SIMULATION', 'Combined Receivables Acceleration & Inventory Expense Audit', 'SCENARIO_1_30D_REC_EXP', 'Combined Receivables Acceleration & Inventory Expense Audit', '30D', 'EVALUATED',
    78.45, 91.80, 13.35, 88240.00, 32.50, 42.00, 'HIGH',
    'Simulates combined execution of distributor collections (₹53,240) and vendor inventory expense audit (₹35,000). Projections are read-only advisory estimates.',
    'Baseline Score: 78.45/100 | Projected Score: 91.80/100 | Score Delta: +13.35 | Projected Cash Impact: ₹88,240.00 | SIMULATED_ESTIMATE'
);

INSERT INTO financial_scenario_items (
    scenario_id, intervention_type, intervention_id, rank_order, projected_impact,
    projected_risk_reduction, projected_goal_impact, evidence_metrics
) VALUES
(4, 'COLLECT_RECEIVABLES', 1, 1, 53240.00, 20.00, 25.00, 'Receivables Collection Simulation: Projected +₹53,240 cash inflow | SIMULATED_ESTIMATE'),
(4, 'REDUCE_EXPENSE', 2, 2, 35000.00, 12.50, 17.00, 'Expense Containment Simulation: Projected ₹35,000 cost savings | SIMULATED_ESTIMATE');
