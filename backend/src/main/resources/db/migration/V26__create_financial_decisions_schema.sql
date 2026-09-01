-- V26: Financial Decision Intelligence & Recommendation Selection Schema

ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS decision_key VARCHAR(128) NOT NULL DEFAULT 'DECISION_DEFAULT';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS status VARCHAR(32) NOT NULL DEFAULT 'RECOMMENDED';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS decision_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS risk_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS confidence_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS expected_benefit TEXT NOT NULL DEFAULT 'Recommended decision expected benefit';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS risk_if_ignored TEXT NOT NULL DEFAULT 'Risk if ignored';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS selected_scenario_id BIGINT;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS selected_plan_id BIGINT;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS selected_intervention_id BIGINT;
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS evidence_metrics TEXT NOT NULL DEFAULT 'ADVISORY_RECOMMENDATION';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS assumptions TEXT NOT NULL DEFAULT 'Advisory decision assumptions';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS tradeoffs TEXT NOT NULL DEFAULT 'Trade-off analysis';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH';
ALTER TABLE financial_decisions ADD COLUMN IF NOT EXISTS evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS decision_id BIGINT REFERENCES financial_decisions(id) ON DELETE CASCADE;
ALTER TABLE financial_decision_options ALTER COLUMN analysis_id DROP NOT NULL;
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS option_type VARCHAR(64) NOT NULL DEFAULT 'CUSTOM';
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS source_id BIGINT;
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS option_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00;
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH';
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS expected_benefit TEXT NOT NULL DEFAULT 'Expected benefit';
ALTER TABLE financial_decision_options ADD COLUMN IF NOT EXISTS risk_if_ignored TEXT NOT NULL DEFAULT 'Risk if ignored';

CREATE INDEX IF NOT EXISTS idx_decisions_merchant ON financial_decisions(merchant_id);
CREATE INDEX IF NOT EXISTS idx_decision_options_decision ON financial_decision_options(decision_id);

-- Seed initial demo recommendation decision for merchant ID 1
INSERT INTO financial_decisions (
    merchant_id, decision_type, title, recommendation, decision_key, status, decision_score,
    risk_score, impact_score, urgency_score, confidence_score, expected_benefit, risk_if_ignored,
    selected_scenario_id, selected_plan_id, selected_intervention_id, evidence_metrics, assumptions,
    tradeoffs, confidence_status, decision_date
) VALUES (
    1, 'INTERVENTION_EXECUTION', 'Accelerate High-Yield Distributor Receivables Recovery',
    'Execute structured receivable collection protocol for ₹53,240 overdue distributor invoices within 7 days.',
    'DECISION_1_REC_ACC', 'RECOMMENDED', 92.45, 88.50, 95.00, 90.00, 89.00,
    'Immediate ₹53,240 liquidity injection; extends cash runway by 1.8 months without incurring debt.',
    'Liquidity deficit risk within 30 days if overdue distributor balance defaults.',
    1, 1, 1,
    'Risk Protection: 88.50/100 | Financial Impact: 95.00/100 | Urgency: 90.00/100 | Strategy Multiplier: 1.085x | ADVISORY_RECOMMENDATION',
    'Assumes distributor acknowledges valid invoice terms and settles via direct bank transfer.',
    'Focuses immediate collection effort on distributor invoices; defers non-critical marketing expenditure.',
    'HIGH', '2026-09-01'
);

INSERT INTO financial_decision_options (
    decision_id, option_key, option_type, source_id, option_score, risk_score, impact_score,
    urgency_score, confidence_status, expected_benefit, risk_if_ignored, rank_order, evidence_metrics,
    title
) VALUES
(4, 'OPT_1_REC_ACCEL', 'COLLECT_RECEIVABLES', 1, 92.45, 88.50, 95.00, 90.00, 'HIGH',
 'Immediate ₹53,240 cash recovery and runway extension.', 'Risk of working capital shortfall within 30 days.', 1,
 'Rank #1 Option | Composite Score: 92.45/100 | ADVISORY_RECOMMENDATION', 'Accelerate Distributor Receivable Collection'),
(4, 'OPT_2_EXP_CONTAIN', 'REDUCE_EXPENSE', 2, 84.10, 80.00, 85.00, 82.00, 'HIGH',
 'Container audit cost savings of ₹35,000.', 'Uncontained operational expense creep.', 2,
 'Rank #2 Option | Composite Score: 84.10/100 | ADVISORY_RECOMMENDATION', 'Container Expense Audit');
