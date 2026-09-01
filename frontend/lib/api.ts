export interface BackendMerchantDTO {
  id: number;
  businessName: string;
  displayName: string;
  businessType: string;
  industry: string;
  demoGstin: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendBusinessAccountDTO {
  id: number;
  merchantId: number;
  institutionName: string;
  accountType: string;
  maskedAccountRef: string;
  currentBalance: number;
  currency: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendMerchantDetailDTO {
  merchant: BackendMerchantDTO;
  accounts: BackendBusinessAccountDTO[];
  totalAvailableCash: number;
  connectedAccountsCount: number;
}

export interface BackendAccountDetailDTO {
  accountId: number;
  institutionName: string;
  accountType: string;
  maskedAccountRef: string;
  currentBalance: number;
  currency: string;
  status: string;
  cashContributionPct: number;
  totalCredits: number;
  totalDebits: number;
  netCashFlow: number;
  transactionCount: number;
}

export interface BackendMerchantWorkspaceDTO {
  merchantId: number;
  businessName: string;
  displayName: string;
  businessType: string;
  industry: string;
  demoGstin: string;
  totalAvailableCash: number;
  connectedAccountsCount: number;
  accounts: BackendAccountDetailDTO[];
  consolidatedNetCashFlow: number;
  consolidatedTransactionCount: number;
}

export interface BackendTransactionDTO {
  id: number;
  merchantId: number;
  businessAccountId: number;
  institutionName: string;
  transactionReference: string;
  transactionDate: string;
  description: string;
  amount: number;
  type: 'CREDIT' | 'DEBIT';
  category: string;
  subcategory?: string;
  counterparty: string;
  paymentMethod: string;
  status: 'SETTLED' | 'PENDING' | 'SCHEDULED';
  demoTag: string;
  sourceType?: string;
  sourceCaptureId?: number;
}

export interface BackendCategoryTotalDTO {
  category: string;
  type: 'CREDIT' | 'DEBIT';
  totalAmount: number;
  count: number;
}

export interface BackendTransactionSummaryDTO {
  totalCredits: number;
  totalDebits: number;
  netCashFlow: number;
  transactionCount: number;
  categoryTotals: BackendCategoryTotalDTO[];
}

export interface BackendMonthlyCashFlowDTO {
  month: string;
  inflow: number;
  outflow: number;
  netCashFlow: number;
}

export interface BackendCashFlowSummaryDTO {
  totalInflows: number;
  totalOutflows: number;
  netCashFlow: number;
  operatingInflows: number;
  operatingOutflows: number;
  averageMonthlyOutflow: number;
  burnRate: number;
  cashRunwayMonths: number;
  recurringExpensesEstimate: number;
  upcomingPayablePressure: number;
  liquidityStatus: 'OPTIMAL' | 'MODERATE' | 'CRITICAL';
}

export interface BackendHealthFactorDTO {
  factorName: string;
  score: number;
  maxScore: number;
  trend: 'IMPROVING' | 'STABLE' | 'DETERIORATING';
  explanation: string;
}

export interface BackendBusinessHealthDTO {
  overallScore: number;
  healthStatus: 'HEALTHY' | 'WATCH' | 'AT_RISK';
  factorScores: BackendHealthFactorDTO[];
  positiveSignals: string[];
  riskSignals: string[];
  summaryExplanation: string;
}

export interface BackendIntelligenceResponseDTO {
  question: string;
  answer: string;
  evidenceSummary: Record<string, any>;
  localAiActive: boolean;
  modelUsed: string;
  disclaimer: string;
}

export interface BackendCategoryMovementDTO {
  category: string;
  currentAmount: number;
  previousAmount: number;
  changeAmount: number;
  changePct: number;
  direction: 'INCREASED' | 'DECREASED' | 'STABLE';
}

export interface BackendTemporalSummaryDTO {
  currentMonth: string;
  previousMonth: string;
  currentInflow: number;
  previousInflow: number;
  inflowChangePct: number;
  inflowDirection: 'UP' | 'DOWN' | 'FLAT';
  currentOutflow: number;
  previousOutflow: number;
  outflowChangePct: number;
  outflowDirection: 'UP' | 'DOWN' | 'FLAT';
  currentNetCash: number;
  previousNetCash: number;
  netCashChangePct: number;
  netCashDirection: 'UP' | 'DOWN' | 'FLAT';
  categoryMovements: BackendCategoryMovementDTO[];
  anomalies: string[];
  insufficientHistory: boolean;
  historyMonthCount: number;
}

export interface BackendPeriodProjectionDTO {
  days: number;
  projectedInflow: number;
  projectedOutflow: number;
  projectedEndingCash: number;
  projectedRunwayMonths: number;
}

export interface BackendForecastSummaryDTO {
  currentAvailableCash: number;
  averageMonthlyInflow: number;
  averageMonthlyOutflow: number;
  projections: BackendPeriodProjectionDTO[];
  assumptions: string[];
  estimate: boolean;
}

export interface BackendScenarioRequestDTO {
  amount: number;
  category: string;
}

export interface BackendScenarioResultDTO {
  requestedAmount: number;
  category: string;
  baselineEndingCash: number;
  scenarioEndingCash: number;
  baselineRunwayMonths: number;
  scenarioRunwayMonths: number;
  cashImpact: number;
  runwayImpactMonths: number;
  riskStatus: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  assumptions: string[];
  estimate: boolean;
}

export interface BackendEvidenceItemDTO {
  metricName: string;
  value: any;
  unit: string;
  source: string;
  period: string;
  calculationType: 'ACTUAL' | 'ESTIMATE';
  assumptions: string;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED';
}

export interface BackendFinancialEvidenceSummaryDTO {
  question: string;
  intentCategory: string;
  evidenceItems: BackendEvidenceItemDTO[];
  assumptions: string[];
  overallStatus: string;
  conclusion: string;
}

export interface BackendEvaluationCaseResultDTO {
  caseId: string;
  question: string;
  category: string;
  responseText: string;
  grounded: boolean;
  numericalConsistent: boolean;
  relevant: boolean;
  evidenceCovered: boolean;
  fallbackUsed: boolean;
  latencyMs: number;
  score: number;
}

export interface BackendEvaluationSummaryDTO {
  runId: number;
  runTimestamp: string;
  benchmarkVersion: string;
  totalCases: number;
  overallScore: number;
  groundingScore: number;
  numericalConsistencyScore: number;
  relevanceScore: number;
  evidenceCoverageScore: number;
  unsupportedClaimsCount: number;
  fallbackRate: number;
  avgLatencyMs: number;
  caseResults: BackendEvaluationCaseResultDTO[];
}

export interface BackendDocumentCaptureRequestDTO {
  documentType: 'RECEIPT' | 'INVOICE' | 'EXPENSE';
  fileName?: string;
  fileData?: string;
  fileType?: string;
  fileSize?: number;
  amount?: number;
  vendorName?: string;
  category?: string;
}

export interface BackendDocumentCaptureResponseDTO {
  id: number;
  merchantId: number;
  documentType: 'RECEIPT' | 'INVOICE' | 'EXPENSE';
  fileName: string;
  fileType: string;
  fileSize: number;
  fileUrlOrData?: string;
  capturedAt: string;
  status: 'CAPTURED' | 'EXTRACTED' | 'CONFIRMED' | 'DISCARDED';
  extractedAmount: number;
  extractedVendor: string;
  extractedCategory: string;
  extractedDate: string;
  extractedTax: number;
  extractedReference: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendDocumentConfirmRequestDTO {
  amount?: number;
  vendorName?: string;
  category?: string;
  reference?: string;
}

export interface BackendDocumentIngestResponseDTO {
  captureId: number;
  transactionId: number;
  merchantId: number;
  amount: number;
  category: string;
  counterparty: string;
  transactionReference: string;
  sourceType: string;
  ingestionTimestamp: string;
  alreadyIngested: boolean;
}

export interface BackendFinancialActionDTO {
  id: number;
  merchantId: number;
  actionKey: string;
  title: string;
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  category: string;
  explanation: string;
  supportingEvidence: string;
  recommendedStep: string;
  status: 'OPEN' | 'DISMISSED' | 'RESOLVED';
  createdAt: string;
  updatedAt: string;
}

export interface BackendActionSummaryDTO {
  totalActions: number;
  highPriorityCount: number;
  mediumPriorityCount: number;
  lowPriorityCount: number;
  openCount: number;
  actions: BackendFinancialActionDTO[];
}

export interface BackendReceivableDTO {
  id: number;
  merchantId: number;
  counterparty: string;
  invoiceReference: string;
  invoiceAmount: number;
  amountReceived: number;
  outstandingAmount: number;
  invoiceDate: string;
  dueDate: string;
  status: 'CURRENT' | 'OVERDUE_1_30' | 'OVERDUE_31_60' | 'OVERDUE_60_PLUS' | 'PAID';
  daysOverdue: number;
}

export interface BackendReceivablesSummaryDTO {
  totalOutstanding: number;
  currentReceivables: number;
  overdue1To30Days: number;
  overdue31To60Days: number;
  overdue60PlusDays: number;
  totalOverdue: number;
  collectionRatePct: number;
  overdueRatioPct: number;
  largestOutstandingCounterparty: string;
  largestCounterpartyAmount: number;
  concentrationRatioPct: number;
  estimatedNearTermCollection: number;
  totalInvoicesCount: number;
  overdueInvoicesCount: number;
  receivables: BackendReceivableDTO[];
}

export interface BackendPayableDTO {
  id: number;
  merchantId: number;
  vendor: string;
  billReference: string;
  billAmount: number;
  amountPaid: number;
  outstandingAmount: number;
  billDate: string;
  dueDate: string;
  category: string;
  status: 'DUE_TODAY' | 'DUE_7_DAYS' | 'DUE_30_DAYS' | 'OVERDUE' | 'PAID';
  daysUntilDue: number;
}

export interface BackendPayablesSummaryDTO {
  totalOutstanding: number;
  dueToday: number;
  due7Days: number;
  due30Days: number;
  totalOverdue: number;
  totalPaid: number;
  paymentCoverageRatioPct: number;
  upcomingPayablePressure: number;
  largestVendorObligation: string;
  largestVendorAmount: number;
  totalBillsCount: number;
  overdueBillsCount: number;
  payables: BackendPayableDTO[];
}

export interface BackendWorkingCapitalSummaryDTO {
  netWorkingCapital: number;
  availableCash: number;
  receivablesOutstanding: number;
  payablesOutstanding: number;
  workingCapitalGap: number;
  currentCoverageRatio: number;
  nearTermCoverageRatio: number;
  cashConversionRiskStatus: 'OPTIMAL' | 'MODERATE' | 'HIGH_RISK';
  nearTermCollectionPotential: number;
  upcomingPayablePressure: number;
  topPressureDrivers: string[];
  summaryExplanation: string;
}

export interface BackendCommandCenterSnapshotDTO {
  overallFinancialStatus: 'HEALTHY' | 'WATCH' | 'AT_RISK';
  overallHealthScore: number;
  availableCash: number;
  netCashFlow: number;
  workingCapitalCoverage: number;
  receivablesPressure: number;
  payablesPressure: number;
  forecastRisk: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  top3Priorities: BackendFinancialActionDTO[];
  keyPositiveSignal: string;
  keyRiskSignal: string;
  whatChangedSummary: string;
  generatedAt: string;
}

export interface BackendReconciliationIssueDTO {
  id: string;
  transactionId?: number;
  issueType: 'DUPLICATE' | 'UNCATEGORIZED' | 'SUSPICIOUS_AMOUNT' | 'OFFICE_KIT_PENDING';
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  description: string;
  counterparty: string;
  amount: number;
  transactionDate: string;
  reconciliationStatus: 'UNREVIEWED' | 'RECONCILED' | 'IGNORED' | 'FLAGGED';
  evidenceDetails: string;
}

export interface BackendReconciliationSummaryDTO {
  totalTransactions: number;
  reconciledCount: number;
  unreviewedCount: number;
  ignoredCount: number;
  flaggedCount: number;
  duplicateIssuesCount: number;
  uncategorizedIssuesCount: number;
  suspiciousIssuesCount: number;
  officeKitPendingCount: number;
  reconciliationHealthPct: number;
  issues: BackendReconciliationIssueDTO[];
}

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  error?: string;
  status: number;
  timestamp: string;
}

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export async function fetchMerchants(): Promise<BackendMerchantDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchants from backend (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'API returned failure status');
  return json.data;
}

export async function fetchMerchantDetail(id: number | string): Promise<BackendMerchantDetailDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant details for ID ${id} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantDetailDTO> = await res.json();
  if (!json.success) throw new Error(json.error || `Merchant not found with ID ${id}`);
  return json.data;
}

export async function fetchMerchantAccounts(id: number | string): Promise<BackendBusinessAccountDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}/accounts`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant accounts for ID ${id} (HTTP ${res.status})`);
  const json: ApiResponse<BackendBusinessAccountDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve accounts');
  return json.data;
}

export async function fetchMerchantTransactions(
  merchantId: number | string,
  filters?: { type?: string; category?: string; search?: string }
): Promise<BackendTransactionDTO[]> {
  const params = new URLSearchParams();
  if (filters?.type) params.append('type', filters.type);
  if (filters?.category) params.append('category', filters.category);
  if (filters?.search) params.append('search', filters.search);

  const queryString = params.toString() ? `?${params.toString()}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/transactions${queryString}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch transactions for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve transactions');
  return json.data;
}

export async function fetchMerchantTransactionSummary(merchantId: number | string): Promise<BackendTransactionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/transactions/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch transaction summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve transaction summary');
  return json.data;
}

export async function fetchMerchantCashFlowSummary(merchantId: number | string): Promise<BackendCashFlowSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-flow`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch cash flow summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCashFlowSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve cash flow summary');
  return json.data;
}

export async function fetchMerchantMonthlyCashFlow(merchantId: number | string): Promise<BackendMonthlyCashFlowDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-flow/monthly`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch monthly cash flow for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMonthlyCashFlowDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve monthly cash flow');
  return json.data;
}

export async function fetchMerchantHealth(merchantId: number | string): Promise<BackendBusinessHealthDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/health`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch business health for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendBusinessHealthDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve business health');
  return json.data;
}

export async function askFlowwiseIntelligence(
  merchantId: number | string,
  question: string
): Promise<BackendIntelligenceResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/intelligence/query`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ question }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to process intelligence query for ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendIntelligenceResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to generate AI response');
  return json.data;
}

export async function fetchMerchantEvidence(
  merchantId: number | string,
  question: string
): Promise<BackendFinancialEvidenceSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/intelligence/evidence`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ question }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to fetch financial evidence for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialEvidenceSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve financial evidence');
  return json.data;
}

export async function fetchMerchantTemporalSummary(merchantId: number | string): Promise<BackendTemporalSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/temporal/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch temporal summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTemporalSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve temporal summary');
  return json.data;
}

export async function fetchMerchantCategoryMovements(merchantId: number | string): Promise<BackendCategoryMovementDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/temporal/categories`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch category movements for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCategoryMovementDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve category movements');
  return json.data;
}

export async function fetchMerchantForecast(merchantId: number | string): Promise<BackendForecastSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/forecast`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch forecast summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendForecastSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve forecast summary');
  return json.data;
}

export async function simulateMerchantScenario(
  merchantId: number | string,
  scenario: BackendScenarioRequestDTO
): Promise<BackendScenarioResultDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/forecast/scenario`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(scenario),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to simulate scenario for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendScenarioResultDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to simulate scenario');
  return json.data;
}

export async function runEvaluation(): Promise<BackendEvaluationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/evaluation/run`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to execute evaluation suite (HTTP ${res.status})`);
  const json: ApiResponse<BackendEvaluationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to run evaluation');
  return json.data;
}

export async function fetchEvaluationSummary(): Promise<BackendEvaluationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/evaluation/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch evaluation summary (HTTP ${res.status})`);
  const json: ApiResponse<BackendEvaluationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve evaluation summary');
  return json.data;
}

export async function createDocumentCapture(
  merchantId: number | string,
  request: BackendDocumentCaptureRequestDTO
): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/office-kit/captures`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to create document capture for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to create document capture');
  return json.data;
}

export async function fetchMerchantCaptures(merchantId: number | string): Promise<BackendDocumentCaptureResponseDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/office-kit/captures`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch document captures for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve document captures');
  return json.data;
}

export async function fetchCaptureDetail(captureId: number | string): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch capture detail for ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve capture detail');
  return json.data;
}

export async function confirmDocumentCapture(
  captureId: number | string,
  confirmData?: BackendDocumentConfirmRequestDTO
): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/confirm`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(confirmData || {}),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to confirm document capture ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to confirm capture');
  return json.data;
}

export async function discardDocumentCapture(captureId: number | string): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/discard`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to discard document capture ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to discard capture');
  return json.data;
}

export async function ingestDocumentCapture(captureId: number | string): Promise<BackendDocumentIngestResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/ingest`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to ingest document capture ID ${captureId} into financial ledger (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentIngestResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to ingest capture into ledger');
  return json.data;
}

export async function fetchMerchantActions(merchantId: number | string): Promise<BackendActionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/actions`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant actions for ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendActionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve merchant actions');
  return json.data;
}

export async function dismissAction(actionId: number | string): Promise<BackendFinancialActionDTO> {
  const res = await fetch(`${API_BASE_URL}/actions/${actionId}/dismiss`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to dismiss action ID ${actionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialActionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to dismiss action');
  return json.data;
}

export async function resolveAction(actionId: number | string): Promise<BackendFinancialActionDTO> {
  const res = await fetch(`${API_BASE_URL}/actions/${actionId}/resolve`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to resolve action ID ${actionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialActionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to resolve action');
  return json.data;
}

export async function fetchMerchantReceivables(merchantId: number | string): Promise<BackendReceivableDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/receivables`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch receivables for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReceivableDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve receivables');
  return json.data;
}

export async function fetchMerchantReceivablesSummary(merchantId: number | string): Promise<BackendReceivablesSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/receivables/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch receivables summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReceivablesSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve receivables summary');
  return json.data;
}

export async function fetchMerchantPayables(merchantId: number | string): Promise<BackendPayableDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/payables`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch payables for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPayableDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve payables');
  return json.data;
}

export async function fetchMerchantPayablesSummary(merchantId: number | string): Promise<BackendPayablesSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/payables/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch payables summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPayablesSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve payables summary');
  return json.data;
}

export async function fetchMerchantWorkingCapital(merchantId: number | string): Promise<BackendWorkingCapitalSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/working-capital`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch working capital for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendWorkingCapitalSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve working capital summary');
  return json.data;
}

export async function fetchMerchantCommandCenter(merchantId: number | string): Promise<BackendCommandCenterSnapshotDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/command-center`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch command center snapshot for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCommandCenterSnapshotDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve command center snapshot');
  return json.data;
}

export async function fetchMerchantWorkspace(merchantId: number | string): Promise<BackendMerchantWorkspaceDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/workspace`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch workspace for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantWorkspaceDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve merchant workspace');
  return json.data;
}

export async function fetchAccountSummary(
  merchantId: number | string,
  accountId: number | string
): Promise<BackendAccountDetailDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/accounts/${accountId}/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch summary for account ID ${accountId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendAccountDetailDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve account summary');
  return json.data;
}

export async function fetchMerchantReconciliation(merchantId: number | string): Promise<BackendReconciliationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/reconciliation`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch reconciliation summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReconciliationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve reconciliation summary');
  return json.data;
}

export async function fetchMerchantReconciliationIssues(merchantId: number | string): Promise<BackendReconciliationIssueDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/reconciliation/issues`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch reconciliation issues for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReconciliationIssueDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve reconciliation issues');
  return json.data;
}

export async function reconcileTransaction(transactionId: number | string, notes?: string): Promise<BackendTransactionDTO> {
  const res = await fetch(`${API_BASE_URL}/transactions/${transactionId}/reconcile`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to reconcile transaction ID ${transactionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to reconcile transaction');
  return json.data;
}

export async function ignoreTransaction(transactionId: number | string, notes?: string): Promise<BackendTransactionDTO> {
  const res = await fetch(`${API_BASE_URL}/transactions/${transactionId}/ignore`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to ignore transaction ID ${transactionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to ignore transaction');
  return json.data;
}
