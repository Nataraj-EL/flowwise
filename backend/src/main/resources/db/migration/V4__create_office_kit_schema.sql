-- Sprint 11: Office Kit Phone Capture Schema Migration

CREATE TABLE document_captures (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    document_type VARCHAR(50) NOT NULL,
    file_name VARCHAR(255),
    file_type VARCHAR(50),
    file_size BIGINT,
    file_url_or_data TEXT,
    captured_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    extracted_amount NUMERIC(15, 2),
    extracted_vendor VARCHAR(255),
    extracted_category VARCHAR(100),
    extracted_date TIMESTAMP WITH TIME ZONE,
    extracted_tax NUMERIC(15, 2),
    extracted_reference VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_doc_captures_merchant_id ON document_captures(merchant_id);
CREATE INDEX idx_doc_captures_status ON document_captures(status);
