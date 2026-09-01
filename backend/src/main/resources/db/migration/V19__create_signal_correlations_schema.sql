-- V19: Financial Signal Correlation & Root-Cause Schema

CREATE TABLE IF NOT EXISTS signal_correlations (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    correlation_key VARCHAR(64) NOT NULL,
    primary_target VARCHAR(128) NOT NULL,
    likely_root_cause VARCHAR(128) NOT NULL,
    correlation_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    contributing_signals_count INT NOT NULL DEFAULT 1,
    matched_signals_json TEXT NOT NULL,
    ranking_formula VARCHAR(255) NOT NULL DEFAULT 'Weighted Contribution Score (0-100)',
    detection_window VARCHAR(64) NOT NULL DEFAULT '30-Day Window',
    evidence_metrics TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_signal_correlations_merchant ON signal_correlations(merchant_id);
CREATE INDEX IF NOT EXISTS idx_signal_correlations_key ON signal_correlations(correlation_key);
CREATE INDEX IF NOT EXISTS idx_signal_correlations_score ON signal_correlations(correlation_score);

-- Seed initial correlation records for merchant ID 1
INSERT INTO signal_correlations (merchant_id, correlation_key, primary_target, likely_root_cause, correlation_score, confidence_status, contributing_signals_count, matched_signals_json, ranking_formula, detection_window, evidence_metrics)
VALUES
(1, 'CRL_M1_RECEIVABLE_DETERIORATION', 'Distributor Collection Delay', 'LIKELY_CONTRIBUTOR: Delayed Wholesaler Settlements & Extended Invoice Payment Cycles', 84.50, 'HIGH', 3, '[{"source":"Receivables Engine","signal":"Overdue Invoice Ratio +18.5%","weight":0.40},{"source":"Cash Management Engine","signal":"Expected 30D Collection Drop ₹53,240","weight":0.35},{"source":"Anomaly Detection","signal":"Receivable Drop Anomaly (-24.20%)","weight":0.25}]', 'Weighted Contribution Score: 0.40*Rec + 0.35*Cash + 0.25*Anom', '30-Day Window', 'Target: Distributor Collection Delay | Score: 84.50/100 | Confidence: HIGH | Primary Driver: Overdue Invoice Ratio (+18.5%) | LIKELY_CONTRIBUTOR | ACTUAL'),
(1, 'CRL_M1_PAYABLE_PRESSURE_SURGE', 'Payable Obligation Spike', 'LIKELY_CONTRIBUTOR: Supplier Inventory Replenishment Acceleration', 76.20, 'HIGH', 2, '[{"source":"Payables Engine","signal":"Near-Term Payable Pressure ₹95,000","weight":0.60},{"source":"Anomaly Detection","signal":"Expense Spike Anomaly (+38.50%)","weight":0.40}]', 'Weighted Contribution Score: 0.60*Pay + 0.40*Anom', '30-Day Window', 'Target: Payable Obligation Spike | Score: 76.20/100 | Confidence: HIGH | Primary Driver: Near-Term Payable Pressure (₹95,000) | LIKELY_CONTRIBUTOR | ACTUAL');
