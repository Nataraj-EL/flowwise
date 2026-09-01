-- Sprint 13: Financial Action Center Schema Migration

CREATE TABLE financial_actions (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    action_key VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    severity VARCHAR(20) NOT NULL, -- HIGH, MEDIUM, LOW
    category VARCHAR(50) NOT NULL, -- PAYABLE_PRESSURE, RUNWAY_RISK, EXPENSE_SPIKE, RECEIVABLES_CONCENTRATION, OPPORTUNITY, HEALTH_MONITOR
    explanation TEXT NOT NULL,
    supporting_evidence TEXT,
    recommended_step TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN', -- OPEN, DISMISSED, RESOLVED
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_merchant_action_key UNIQUE (merchant_id, action_key)
);

CREATE INDEX idx_financial_actions_merchant ON financial_actions(merchant_id);
CREATE INDEX idx_financial_actions_status ON financial_actions(status);
CREATE INDEX idx_financial_actions_severity ON financial_actions(severity);
