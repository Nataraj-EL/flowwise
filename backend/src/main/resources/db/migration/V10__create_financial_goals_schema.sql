-- V10: Financial Goals & Decision Tracking Schema

CREATE TABLE IF NOT EXISTS financial_goals (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    goal_type VARCHAR(64) NOT NULL,
    name VARCHAR(255) NOT NULL,
    target_amount NUMERIC(19, 2) NOT NULL,
    initial_baseline_amount NUMERIC(19, 2) DEFAULT 0.00,
    target_date DATE NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_goals_merchant ON financial_goals(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_goals_status ON financial_goals(status);

-- Seed initial demo financial goals for merchant ID 1
INSERT INTO financial_goals (merchant_id, goal_type, name, target_amount, initial_baseline_amount, target_date, status)
VALUES 
(1, 'CASH_RESERVE', 'Emergency Cash Reserve Buffer', 500000.00, 150000.00, '2026-12-31', 'ACTIVE'),
(1, 'WORKING_CAPITAL', 'Q3 Working Capital Expansion', 350000.00, 100000.00, '2026-10-31', 'ACTIVE'),
(1, 'DEBT_REDUCTION', 'Vendor Payable Liability Reduction', 100000.00, 450000.00, '2026-09-30', 'ACTIVE'),
(1, 'RECEIVABLES_COLLECTION', 'Overdue Invoice Collection Drive', 250000.00, 50000.00, '2026-08-31', 'EXPIRED');
