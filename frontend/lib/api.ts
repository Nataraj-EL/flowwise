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
