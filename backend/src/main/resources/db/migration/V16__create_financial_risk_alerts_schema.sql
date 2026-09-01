-- V16: Early Financial Risk Detection Schema

CREATE TABLE IF NOT EXISTS financial_risk_alerts (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    risk_key VARCHAR(64) NOT NULL, -- e.g. RSK_M1_RECEIVABLES_OVERDUE_30D
    risk_type VARCHAR(64) NOT NULL, -- LIQUIDITY, CASHFLOW, RECEIVABLES, PAYABLES, WORKING_CAPITAL, GOAL, DECISION_PERFORMANCE
    severity VARCHAR(32) NOT NULL, -- LOW, MEDIUM, HIGH, CRITICAL
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    baseline_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    current_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    change_pct NUMERIC(7,2) NOT NULL DEFAULT 0.00,
    threshold_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    detection_window VARCHAR(64) NOT NULL DEFAULT '30D',
    status VARCHAR(32) NOT NULL DEFAULT 'OPEN', -- OPEN, ACKNOWLEDGED, RESOLVED
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    evidence_metrics TEXT,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_risk_alerts_merchant ON financial_risk_alerts(merchant_id);
CREATE INDEX IF NOT EXISTS idx_risk_alerts_key ON financial_risk_alerts(risk_key);
CREATE INDEX IF NOT EXISTS idx_risk_alerts_status ON financial_risk_alerts(status);

-- Seed initial risk alerts for merchant ID 1
INSERT INTO financial_risk_alerts (merchant_id, risk_key, risk_type, severity, title, description, baseline_value, current_value, change_pct, threshold_value, detection_window, status, confidence_status, evidence_metrics)
VALUES
(1, 'RSK_M1_RECEIVABLES_OVERDUE_30D', 'RECEIVABLES', 'HIGH', 'Distributor Invoice Collection Deterioration', 'Overdue receivables increased by 32.50% over the last 30 days to ₹165,000.', 124528.00, 165000.00, 32.50, 150000.00, '30D', 'OPEN', 'HIGH', 'Baseline Overdue: ₹124,528 | Current Overdue: ₹165,000 | Threshold: ₹150,000'),
(1, 'RSK_M1_PAYABLES_PRESSURE_7D', 'PAYABLES', 'MEDIUM', '7-Day Vendor Obligation Accumulation', 'Upcoming 7-day vendor obligations stand at ₹95,000 representing 19.59% of available liquid cash.', 75000.00, 95000.00, 26.67, 90000.00, '7D', 'OPEN', 'HIGH', 'Baseline Payables: ₹75,000 | Current Payables: ₹95,000 | Available Cash: ₹485,000');
