-- Sprint 12: Verified Financial Ingestion & Provenance Schema Migration

ALTER TABLE transactions ADD COLUMN source_type VARCHAR(50) DEFAULT 'BANK_FEED';
ALTER TABLE transactions ADD COLUMN source_capture_id BIGINT;
ALTER TABLE transactions ADD COLUMN ingestion_timestamp TIMESTAMP WITH TIME ZONE;

ALTER TABLE document_captures ADD COLUMN ingested BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE document_captures ADD COLUMN ingested_transaction_id BIGINT;
ALTER TABLE document_captures ADD COLUMN ingested_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE transactions ADD CONSTRAINT fk_tx_source_capture FOREIGN KEY (source_capture_id) REFERENCES document_captures(id) ON DELETE SET NULL;
ALTER TABLE document_captures ADD CONSTRAINT fk_doc_ingested_tx FOREIGN KEY (ingested_transaction_id) REFERENCES transactions(id) ON DELETE SET NULL;

CREATE INDEX idx_transactions_source_capture_id ON transactions(source_capture_id);
CREATE INDEX idx_doc_captures_ingested ON document_captures(ingested);
