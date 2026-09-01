-- V22: Financial Strategy Learning Schema

CREATE TABLE IF NOT EXISTS financial_strategy_learnings (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    strategy_key VARCHAR(128) NOT NULL,
    intervention_type VARCHAR(64) NOT NULL,
    context_type VARCHAR(64) NOT NULL DEFAULT 'GENERAL_RECEIVABLES',
    sample_count INT NOT NULL DEFAULT 1,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    learning_multiplier NUMERIC(4,3) NOT NULL DEFAULT 1.000, -- 0.900 - 1.100
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH', -- HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_strategy_learning UNIQUE(merchant_id, strategy_key)
);

CREATE INDEX IF NOT EXISTS idx_strategy_learnings_merchant ON financial_strategy_learnings(merchant_id);
CREATE INDEX IF NOT EXISTS idx_strategy_learnings_type ON financial_strategy_learnings(intervention_type);

-- Seed initial strategy learning record for merchant ID 1
INSERT INTO financial_strategy_learnings (merchant_id, strategy_key, intervention_type, context_type, sample_count, effectiveness_score, learning_multiplier, confidence_status, evidence_metrics, assumptions)
VALUES
(1, 'COLLECT_RECEIVABLES:DISTRIBUTOR_OVERDUE', 'COLLECT_RECEIVABLES', 'DISTRIBUTOR_OVERDUE', 5, 92.50, 1.085, 'HIGH', '5 Completed Outcomes Evaluated | Average Effectiveness: 92.50/100 | Actual Cash Recovered: ₹266,200 | OBSERVED_OUTCOME', 'Strategy learning derives performance multipliers strictly from historical post-completion outcomes without altering past interventions.');
