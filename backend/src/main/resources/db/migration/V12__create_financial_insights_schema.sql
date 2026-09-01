-- V12: Financial Pattern & Insight Engine Schema

CREATE TABLE IF NOT EXISTS financial_insights (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    insight_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    severity VARCHAR(32) NOT NULL DEFAULT 'MEDIUM',
    description TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    detected_period VARCHAR(64) NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'NEW',
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    calculation_type VARCHAR(32) NOT NULL DEFAULT 'ACTUAL',
    assumptions TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_financial_insights_merchant ON financial_insights(merchant_id);
CREATE INDEX IF NOT EXISTS idx_financial_insights_status ON financial_insights(status);
CREATE INDEX IF NOT EXISTS idx_financial_insights_dedup ON financial_insights(merchant_id, insight_type, detected_period);

-- Seed initial demo pattern insights for merchant ID 1
INSERT INTO financial_insights (merchant_id, insight_type, title, severity, description, evidence_metrics, detected_period, status, confidence_status, calculation_type, assumptions)
VALUES
(1, 'RISING_PAYMENT_PRESSURE', 'High 7-Day Vendor Obligation Concentration', 'HIGH', 'Upcoming 7-day vendor obligations equal ₹112,000 against available cash of ₹485,000 (23.1% cash pressure).', '7-Day Obligations: ₹112,000 | Available Cash: ₹485,000 | Pressure Ratio: 23.1%', 'Current 7-Day Window', 'NEW', 'HIGH', 'ACTUAL', 'Calculated from active payables due within 7 days against total bank balances.'),
(1, 'RECURRING_EXPENSE_INCREASE', 'Logistics Outflow Spike Pattern', 'MEDIUM', 'Logistics subcategory expenses increased by 18.4% over prior month baseline.', 'Current Outflow: ₹68,000 | Prior Baseline: ₹57,400 | Increase: +18.4%', 'Prior 30 Days', 'NEW', 'HIGH', 'ACTUAL', 'Based on settled debit transaction category totals across past 60 days.'),
(1, 'RECEIVABLES_DETERIORATION', 'Overdue Receivable Accumulation', 'HIGH', 'Overdue receivables exceeded 18.5% of total outstanding invoices with ₹145,000 overdue >30 days.', 'Total Outstanding: ₹784,000 | Overdue Amount: ₹145,000 | Overdue Ratio: 18.5%', 'Current Quarter', 'ACKNOWLEDGED', 'HIGH', 'ACTUAL', 'Derived from distributor invoice aging schedule.');
