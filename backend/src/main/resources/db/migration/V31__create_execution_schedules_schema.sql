-- V31: Financial Execution Capacity & Adaptive Scheduling Schema

CREATE TABLE IF NOT EXISTS financial_execution_schedules (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    schedule_key VARCHAR(64) NOT NULL,
    horizon VARCHAR(16) NOT NULL DEFAULT '30D',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    overall_schedule_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    capacity_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    impact_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    total_actions INT NOT NULL DEFAULT 0,
    scheduled_actions INT NOT NULL DEFAULT 0,
    deferred_actions INT NOT NULL DEFAULT 0,
    primary_focus TEXT NOT NULL,
    expected_benefit TEXT NOT NULL,
    risk_if_deferred TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    assumptions TEXT NOT NULL,
    evaluated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_financial_execution_schedule_merchant_horizon_key UNIQUE (merchant_id, horizon, schedule_key)
);

CREATE TABLE IF NOT EXISTS financial_execution_schedule_items (
    id BIGSERIAL PRIMARY KEY,
    schedule_id BIGINT NOT NULL REFERENCES financial_execution_schedules(id) ON DELETE CASCADE,
    action_plan_id BIGINT NOT NULL REFERENCES advisory_action_plans(id) ON DELETE CASCADE,
    step_id BIGINT NOT NULL REFERENCES advisory_action_plan_steps(id) ON DELETE CASCADE,
    action_type VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    scheduled_period VARCHAR(32) NOT NULL DEFAULT 'WEEK_1',
    sequence_order INT NOT NULL DEFAULT 1,
    readiness_status VARCHAR(32) NOT NULL DEFAULT 'SCHEDULED',
    priority_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    risk_protection_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    urgency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    dependency_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    effectiveness_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    capacity_cost NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    deferral_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    confidence_status VARCHAR(32) NOT NULL DEFAULT 'HIGH',
    expected_outcome TEXT NOT NULL,
    deferral_risk TEXT NOT NULL,
    evidence_metrics TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_execution_schedules_merchant ON financial_execution_schedules(merchant_id);
CREATE INDEX IF NOT EXISTS idx_execution_schedule_items_schedule ON financial_execution_schedule_items(schedule_id);

-- Seed demo execution schedule for merchant ID 1
INSERT INTO financial_execution_schedules (
    merchant_id, schedule_key, horizon, status, overall_schedule_score, capacity_score,
    risk_score, impact_score, urgency_score, total_actions, scheduled_actions, deferred_actions,
    primary_focus, expected_benefit, risk_if_deferred, evidence_metrics, assumptions
) VALUES (
    1, 'SCHED_DEMO_30D', '30D', 'ACTIVE', 93.80, 85.00,
    91.50, 94.20, 92.00, 2, 2, 0,
    'Dispatch Invoices & Initiate Distributor Payment Verification',
    '₹53,240 working capital recovery & 14-day runaway protection',
    'Distributor payment delay past 7 days increases cash deficit risk by 28.50%',
    'Scheduled: 2/2 actions (100.00% capacity utilization) | Score: 93.80/100 | ADVISORY_EXECUTION_SCHEDULE',
    'Capacity limit: 40 hrs/week. All prerequisites verified READY.'
);

INSERT INTO financial_execution_schedule_items (
    schedule_id, action_plan_id, step_id, action_type, title, scheduled_period,
    sequence_order, readiness_status, priority_score, risk_protection_score, urgency_score,
    dependency_score, effectiveness_score, capacity_cost, deferral_score, confidence_status,
    expected_outcome, deferral_risk, evidence_metrics
) VALUES (
    1, 1, 1, 'COLLECT_RECEIVABLES', 'Dispatch Invoices & Initiate Distributor Payment Verification', 'WEEK_1',
    1, 'SCHEDULED', 94.20, 90.00, 92.00, 100.00, 93.60, 12.50, 15.20, 'HIGH',
    '₹53,240 working capital recovery within 7 days',
    'Delaying payment verification risks ₹53,240 default',
    'Sequence #1 | Score: 94.20/100 | 1.085x Learning Multiplier | ADVISORY_EXECUTION_SCHEDULE'
), (
    1, 1, 2, 'STAGGER_PAYABLES', 'Request Vendor Payment Staggering for Q3 Inventory', 'WEEK_2',
    2, 'SCHEDULED', 88.50, 85.00, 80.00, 100.00, 89.20, 8.00, 22.40, 'HIGH',
    'Preserve ₹35,000 cash buffer across 14-day window',
    'Immediate payout causes brief ₹12,000 overdraft',
    'Sequence #2 | Score: 88.50/100 | 1.050x Learning Multiplier | ADVISORY_EXECUTION_SCHEDULE'
);
