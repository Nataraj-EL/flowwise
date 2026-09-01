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

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  error?: string;
  status: number;
  timestamp: string;
}

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export async function fetchMerchants(): Promise<BackendMerchantDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants`, {
    cache: 'no-store',
  });
  
  if (!res.ok) {
    throw new Error(`Failed to fetch merchants from backend (HTTP ${res.status})`);
  }
  
  const json: ApiResponse<BackendMerchantDTO[]> = await res.json();
  if (!json.success) {
    throw new Error(json.error || 'API returned failure status');
  }
  return json.data;
}

export async function fetchMerchantDetail(id: number | string): Promise<BackendMerchantDetailDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}`, {
    cache: 'no-store',
  });

  if (!res.ok) {
    throw new Error(`Failed to fetch merchant details for ID ${id} (HTTP ${res.status})`);
  }

  const json: ApiResponse<BackendMerchantDetailDTO> = await res.json();
  if (!json.success) {
    throw new Error(json.error || `Merchant not found with ID ${id}`);
  }
  return json.data;
}

export async function fetchMerchantAccounts(id: number | string): Promise<BackendBusinessAccountDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}/accounts`, {
    cache: 'no-store',
  });

  if (!res.ok) {
    throw new Error(`Failed to fetch merchant accounts for ID ${id} (HTTP ${res.status})`);
  }

  const json: ApiResponse<BackendBusinessAccountDTO[]> = await res.json();
  if (!json.success) {
    throw new Error(json.error || 'Failed to retrieve accounts');
  }
  return json.data;
}
