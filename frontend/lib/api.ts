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
