-- V29: Financial Advisory Action Sequencing & Execution Readiness Schema

CREATE TABLE IF NOT EXISTS advisory_action_plans (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    plan_key VARCHAR(128) NOT NULL,
    horizon VARCHAR(16) NOT NULL DEFAULT '30D',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    overall_readiness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    total_steps_count INT NOT NULL DEFAULT 0,
    ready_steps_count INT NOT NULL DEFAULT 0,
    blocked_steps_count INT NOT NULL DEFAULT 0,
    primary_next_action VARCHAR(255) NOT NULL,
    expected_benefit TEXT NOT NULL,
    risk_if_delayed TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_advisory_action_plan_merchant_horizon UNIQUE (merchant_id, horizon, plan_key)
);

CREATE TABLE IF NOT EXISTS advisory_action_plan_steps (
    id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL REFERENCES advisory_action_plans(id) ON DELETE CASCADE,
    step_key VARCHAR(128) NOT NULL,
    step_number INT NOT NULL DEFAULT 1,
    action_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    readiness_status VARCHAR(32) NOT NULL DEFAULT 'READY',
    step_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    priority_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_protection_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    dependency_readiness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    effort_level VARCHAR(32) NOT NULL DEFAULT 'LOW',
    prerequisites TEXT NOT NULL,
    expected_outcome TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_advisory_action_plans_merchant ON advisory_action_plans(merchant_id);
CREATE INDEX IF NOT EXISTS idx_advisory_action_plan_steps_plan ON advisory_action_plan_steps(plan_id);

-- Seed demo advisory action plan for merchant ID 1
INSERT INTO advisory_action_plans (
    merchant_id, plan_key, horizon, status, overall_readiness_score, total_steps_count,
    ready_steps_count, blocked_steps_count, primary_next_action, expected_benefit,
    risk_if_delayed, evidence_metrics, assumptions
) VALUES (
    1, 'PLAN_30D_ACTION_1', '30D', 'ACTIVE', 93.50, 2, 2, 0,
    'Dispatch Invoices & Initiate Distributor Payment Verification',
    'Immediate ₹53,240 distributor cash recovery + ₹35,000 logistics cost reduction.',
    'Liquidity deficit within 30 days if overdue distributor balance defaults.',
    'Ready Steps: 2/2 | Readiness Score: 93.50/100 | ADVISORY_ACTION_PLAN',
    'Sequenced from Sprint 39 Decision Portfolio. Excludes SIMULATED_ESTIMATE from actual outcomes.'
);

INSERT INTO advisory_action_plan_steps (
    plan_id, step_key, step_number, action_type, title, description, readiness_status,
    step_score, priority_score, risk_protection_score, urgency_score, dependency_readiness_score,
    confidence_status, effort_level, prerequisites, expected_outcome, evidence_metrics
) VALUES
(1, 'STEP_1_COLLECT', 1, 'COLLECT_RECEIVABLES', 'Dispatch Invoices & Initiate Distributor Follow-Up',
 'Verify GSTIN invoice records and issue structured collection request for ₹53,240.', 'READY',
 94.20, 92.45, 88.50, 90.00, 100.00, 'HIGH', 'LOW', 'None - Ready for immediate dispatch',
 '₹53,240 cash recovery within 7 days', 'Step #1 Priority | Score: 94.20/100 | Status: READY | ADVISORY_ACTION_PLAN'),
(1, 'STEP_2_AUDIT', 2, 'AUDIT_EXPENSES', 'Verify Container Rates & Audit Logistics Vendor Bill',
 'Audit logistics vendor bill against contracted container rates to contain ₹35,000 cost surge.', 'READY',
 86.40, 84.10, 80.00, 82.00, 90.00, 'HIGH', 'MEDIUM', 'Logistics invoice receipt verified',
 '₹35,000 monthly cost containment', 'Step #2 Priority | Score: 86.40/100 | Status: READY | ADVISORY_ACTION_PLAN');
