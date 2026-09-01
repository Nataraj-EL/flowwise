-- Sprint 10: AI Evaluation & Observability Schema Migration

CREATE TABLE evaluation_runs (
    id BIGSERIAL PRIMARY KEY,
    run_timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_cases INT NOT NULL,
    overall_score NUMERIC(5, 2) NOT NULL,
    grounding_score NUMERIC(5, 2) NOT NULL,
    numerical_consistency_score NUMERIC(5, 2) NOT NULL,
    relevance_score NUMERIC(5, 2) NOT NULL,
    evidence_coverage_score NUMERIC(5, 2) NOT NULL,
    unsupported_claims_count INT NOT NULL,
    fallback_rate NUMERIC(5, 2) NOT NULL,
    avg_latency_ms NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evaluation_case_results (
    id BIGSERIAL PRIMARY KEY,
    run_id BIGINT NOT NULL REFERENCES evaluation_runs(id) ON DELETE CASCADE,
    case_id VARCHAR(50) NOT NULL,
    question TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    ground_truth_expected TEXT,
    response_text TEXT,
    grounded BOOLEAN NOT NULL,
    numerical_consistent BOOLEAN NOT NULL,
    relevant BOOLEAN NOT NULL,
    evidence_covered BOOLEAN NOT NULL,
    fallback_used BOOLEAN NOT NULL,
    latency_ms BIGINT NOT NULL,
    score NUMERIC(5, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ai_request_logs (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    merchant_id BIGINT NOT NULL,
    query TEXT NOT NULL,
    model VARCHAR(100) NOT NULL,
    latency_ms BIGINT NOT NULL,
    ollama_available BOOLEAN NOT NULL,
    fallback_used BOOLEAN NOT NULL,
    evidence_count INT NOT NULL,
    evaluation_status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_eval_runs_timestamp ON evaluation_runs(run_timestamp);
CREATE INDEX idx_eval_case_results_run_id ON evaluation_case_results(run_id);
CREATE INDEX idx_ai_logs_merchant_id ON ai_request_logs(merchant_id);
