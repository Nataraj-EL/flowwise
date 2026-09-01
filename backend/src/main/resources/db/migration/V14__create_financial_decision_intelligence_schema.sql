-- V14: Financial Decision Intelligence Schema

CREATE TABLE IF NOT EXISTS financial_decision_analyses (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    analysis_key VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    recommended_option VARCHAR(64) NOT NULL,
    baseline_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    data_quality_status VARCHAR(32) NOT NULL DEFAULT 'SUFFICIENT',
    input_fingerprint VARCHAR(128),
    summary_explanation TEXT,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_decision_analyses_merchant ON financial_decision_analyses(merchant_id);
CREATE INDEX IF NOT EXISTS idx_decision_analyses_key ON financial_decision_analyses(analysis_key);

CREATE TABLE IF NOT EXISTS financial_decision_options (
    id BIGSERIAL PRIMARY KEY,
    analysis_id BIGINT NOT NULL REFERENCES financial_decision_analyses(id) ON DELETE CASCADE,
    option_key VARCHAR(64) NOT NULL, -- PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE
    title VARCHAR(255) NOT NULL,
    description TEXT,
    composite_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    liquidity_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    coverage_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    goal_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    projected_7d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_30d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    projected_90d_cash NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    risk_status VARCHAR(32) NOT NULL DEFAULT 'FEASIBLE',
    goal_impact_status VARCHAR(32) NOT NULL DEFAULT 'NEUTRAL',
    assumptions TEXT,
    evidence_metrics TEXT,
    rank_order INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_decision_options_analysis ON financial_decision_options(analysis_id);

-- Seed initial decision analysis for merchant ID 1
INSERT INTO financial_decision_analyses (merchant_id, analysis_key, title, recommended_option, baseline_score, data_quality_status, input_fingerprint, summary_explanation)
VALUES
(1, 'CURRENT_OPERATING_DECISION', 'Quarterly Liquidity Optimization Options', 'COLLECT_RECEIVABLES', 86.50, 'SUFFICIENT', 'fp_m1_2026q3_v1', 'Prioritizing distributor receivable collection provides optimal liquidity buffer without deferring critical payables or incurring vendor penalties.');

INSERT INTO financial_decision_options (analysis_id, option_key, title, description, composite_score, liquidity_score, coverage_score, goal_score, risk_score, urgency_score, projected_7d_cash, projected_30d_cash, projected_90d_cash, risk_status, goal_impact_status, assumptions, evidence_metrics, rank_order)
VALUES
(1, 'COLLECT_RECEIVABLES', 'Accelerate Distributor Receivable Collection', 'Execute targeted follow-ups on ₹165,000 overdue distributor receivables to boost immediate liquid reserves.', 86.50, 90.00, 85.00, 95.00, 80.00, 80.00, 580000.00, 695000.00, 920000.00, 'FEASIBLE', 'POSITIVE', 'Assumes 80% collection rate on 30-day overdue receivables.', 'Overdue Receivables: ₹165,000 | Net Cash Inflow: +₹132,000', 1),
(1, 'PAY_NOW', 'Settle Upcoming 7-Day Vendor Payables In Full', 'Pay ₹95,000 mandatory vendor bills immediately to capture prompt settlement terms.', 78.00, 70.00, 95.00, 75.00, 85.00, 70.00, 390000.00, 515000.00, 785000.00, 'FEASIBLE', 'NEUTRAL', 'Deducts ₹95,000 immediately from liquid reserves.', 'Payables Due: ₹95,000 | Remaining Cash: ₹390,000', 2),
(1, 'BUILD_RESERVE', 'Accumulate Emergency Working Capital Reserve', 'Ringfence 20% of net monthly cash flow (₹25,000/mo) into dedicated cash reserve account.', 72.50, 85.00, 70.00, 80.00, 75.00, 50.00, 510000.00, 585000.00, 815000.00, 'FEASIBLE', 'POSITIVE', 'Reserves ₹25,000 monthly cash flow.', 'Monthly Reserve: ₹25,000 | 90D Reserve Total: ₹75,000', 3),
(1, 'DEFER', 'Defer Non-Critical Payables By 14 Days', 'Request 14-day vendor extension on ₹45,000 non-essential inventory payables.', 58.00, 60.00, 50.00, 55.00, 45.00, 80.00, 530000.00, 565000.00, 740000.00, 'CAUTION', 'NEGATIVE', 'Preserves near-term cash but increases 30-day liability.', 'Deferred Bills: ₹45,000 | Vendor Terms Warning', 4);
