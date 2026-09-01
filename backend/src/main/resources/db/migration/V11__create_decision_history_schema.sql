-- V11: Financial Decision History & Memory Schema

CREATE TABLE IF NOT EXISTS financial_decisions (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    action_id BIGINT REFERENCES financial_actions(id) ON DELETE SET NULL,
    goal_id BIGINT REFERENCES financial_goals(id) ON DELETE SET NULL,
    decision_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    recommendation TEXT,
    decision_status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    decision_notes VARCHAR(1000),
    decision_date DATE NOT NULL,
    outcome_status VARCHAR(32) NOT NULL DEFAULT 'UNKNOWN',
    outcome_notes VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_decisions_merchant ON financial_decisions(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_decisions_status ON financial_decisions(decision_status);

-- Seed initial demo financial decisions for merchant ID 1
INSERT INTO financial_decisions (merchant_id, action_id, goal_id, decision_type, title, recommendation, decision_status, decision_notes, decision_date, outcome_status, outcome_notes)
VALUES 
(1, NULL, 1, 'CASH_RESERVE', 'Accepted Emergency Cash Reserve Allocation', 'Allocate ₹15,000 monthly to cash safety reserve', 'COMPLETED', 'Approved monthly reserve transfer from operating revenue', '2026-08-01', 'POSITIVE', 'Cash runway extended by 1.2 months safely.'),
(1, NULL, NULL, 'VENDOR_PAYMENT', 'Vendor Payable Rescheduling Decision', 'Defer non-essential logistics vendor payment by 14 days', 'ACCEPTED', 'Agreed with vendor to extend payment term', '2026-08-15', 'UNKNOWN', NULL),
(1, NULL, NULL, 'RECEIVABLES_COLLECTION', 'Early Settlement Discount Offer', 'Offer 2% early payment discount to top counterparty', 'DECLINED', 'Declined offer due to thin profit margins', '2026-08-20', 'NEUTRAL', 'Counterparty settled on standard 30-day terms.');
