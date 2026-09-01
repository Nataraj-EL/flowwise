-- V15: Decision Outcome & Recommendation Calibration Schema

CREATE TABLE IF NOT EXISTS decision_calibration_records (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    calibration_key VARCHAR(64) NOT NULL,
    total_evaluated_decisions INT NOT NULL DEFAULT 0,
    successful_decisions INT NOT NULL DEFAULT 0,
    overall_success_rate_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_level VARCHAR(32) NOT NULL DEFAULT 'MODERATE', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    data_completeness_pct NUMERIC(5,2) NOT NULL DEFAULT 100.00,
    summary_insight TEXT,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_decision_calibration_merchant ON decision_calibration_records(merchant_id);
CREATE INDEX IF NOT EXISTS idx_decision_calibration_key ON decision_calibration_records(calibration_key);

CREATE TABLE IF NOT EXISTS option_calibration_factors (
    id BIGSERIAL PRIMARY KEY,
    calibration_record_id BIGINT NOT NULL REFERENCES decision_calibration_records(id) ON DELETE CASCADE,
    option_key VARCHAR(64) NOT NULL, -- PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE
    total_sample_count INT NOT NULL DEFAULT 0,
    positive_outcome_count INT NOT NULL DEFAULT 0,
    negative_outcome_count INT NOT NULL DEFAULT 0,
    success_rate_pct NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    calibration_multiplier NUMERIC(4,2) NOT NULL DEFAULT 1.00, -- Bounded 0.80 to 1.20
    avg_cash_impact_variance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    accuracy_status VARCHAR(32) NOT NULL DEFAULT 'ACCURATE', -- ACCURATE, OVERESTIMATED, UNDERESTIMATED, UNCALIBRATED
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_option_factors_record ON option_calibration_factors(calibration_record_id);

-- Seed initial calibration performance data for merchant ID 1
INSERT INTO decision_calibration_records (merchant_id, calibration_key, total_evaluated_decisions, successful_decisions, overall_success_rate_pct, confidence_level, data_completeness_pct, summary_insight)
VALUES
(1, 'CURRENT_CALIBRATION_BASELINE', 4, 3, 75.00, 'MODERATE', 100.00, 'Historical recommendations demonstrate 75% positive outcome fidelity across 4 evaluated decision cycles.');

INSERT INTO option_calibration_factors (calibration_record_id, option_key, total_sample_count, positive_outcome_count, negative_outcome_count, success_rate_pct, calibration_multiplier, avg_cash_impact_variance, accuracy_status)
VALUES
(1, 'COLLECT_RECEIVABLES', 3, 3, 0, 100.00, 1.08, 12500.00, 'ACCURATE'),
(1, 'PAY_NOW', 1, 1, 0, 100.00, 1.00, 0.00, 'UNCALIBRATED'),
(1, 'BUILD_RESERVE', 0, 0, 0, 0.00, 1.00, 0.00, 'UNCALIBRATED'),
(1, 'DEFER', 0, 0, 0, 0.00, 1.00, 0.00, 'UNCALIBRATED'),
(1, 'REDUCE_EXPENSE', 0, 0, 0, 0.00, 1.00, 0.00, 'UNCALIBRATED');
