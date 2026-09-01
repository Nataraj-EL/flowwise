-- V18: Financial Anomaly Detection & Monitoring Schema

CREATE TABLE IF NOT EXISTS financial_anomalies (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    anomaly_key VARCHAR(64) NOT NULL,
    anomaly_type VARCHAR(64) NOT NULL,
    severity VARCHAR(32) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, CRITICAL
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    baseline_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    observed_value NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    deviation_pct NUMERIC(7,2) NOT NULL DEFAULT 0.00,
    threshold_pct NUMERIC(7,2) NOT NULL DEFAULT 20.00,
    detection_window VARCHAR(64) NOT NULL DEFAULT '30-Day Window',
    sample_size INT NOT NULL DEFAULT 3,
    status VARCHAR(32) NOT NULL DEFAULT 'OPEN', -- OPEN, ACKNOWLEDGED, RESOLVED
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    evidence_metrics TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_anomalies_merchant ON financial_anomalies(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_anomalies_key ON financial_anomalies(anomaly_key);
CREATE INDEX IF NOT EXISTS idx_financial_anomalies_severity ON financial_anomalies(severity);
CREATE INDEX IF NOT EXISTS idx_financial_anomalies_status ON financial_anomalies(status);

-- Seed initial financial anomaly data for merchant ID 1
INSERT INTO financial_anomalies (merchant_id, anomaly_key, anomaly_type, severity, title, description, baseline_value, observed_value, deviation_pct, threshold_pct, detection_window, sample_size, status, confidence_status, evidence_metrics)
VALUES
(1, 'ANM_M1_EXPENSE_SPIKE_30D', 'EXPENSE_SPIKE', 'HIGH', 'Unusual Logistics Expense Surge', 'Observed monthly logistics expenses exceeded historical baseline by +38.50%.', 85000.00, 117725.00, 38.50, 35.00, '30-Day Window', 6, 'OPEN', 'HIGH', 'Baseline: ₹85,000.00 | Observed: ₹117,725.00 | Deviation: +38.50% | Threshold: +35.00% | Mean: ₹85,000.00 | StdDev: ₹4,250.00 | Window: 30-Day Window | Confidence: HIGH | ACTUAL'),
(1, 'ANM_M1_RECEIVABLE_DROP_30D', 'RECEIVABLE_DROP', 'MEDIUM', 'Distributor Collection Pace Slowdown', 'Observed near-term 30-day collection velocity fell by -24.20% below moving average.', 220000.00, 166760.00, -24.20, -20.00, '30-Day Window', 4, 'OPEN', 'HIGH', 'Baseline: ₹220,000.00 | Observed: ₹166,760.00 | Deviation: -24.20% | Threshold: -20.00% | Mean: ₹220,000.00 | StdDev: ₹11,000.00 | Window: 30-Day Window | Confidence: HIGH | ACTUAL');
