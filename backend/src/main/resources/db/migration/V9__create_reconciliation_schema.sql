-- V9: Transaction Reconciliation & Review Schema

ALTER TABLE transactions ADD COLUMN IF NOT EXISTS reconciliation_status VARCHAR(32) DEFAULT 'UNREVIEWED';
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS reconciliation_notes VARCHAR(500);
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS reconciled_at TIMESTAMP WITH TIME ZONE;

-- Seed initial reconciliation flags for demo transaction dataset
UPDATE transactions
SET reconciliation_status = 'RECONCILED',
    reconciled_at = CURRENT_TIMESTAMP
WHERE id IN (1, 2, 3, 4, 5);

UPDATE transactions
SET reconciliation_status = 'UNREVIEWED'
WHERE reconciliation_status IS NULL;
