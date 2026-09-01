export interface MerchantProfile {
  id: string;
  name: string;
  demoGstin: string;
  category: string;
  isDemoEnvironment: boolean;
  activeAccountsCount: number;
}

export interface MetricData {
  title: string;
  value: number;
  formattedValue: string;
  changeMoM: number;
  trend: 'up' | 'down' | 'neutral';
  subtext: string;
}

export interface CashFlowMonthData {
  month: string;
  inflow: number;
  outflow: number;
  netCash: number;
}

export interface TransactionItem {
  id: string;
  date: string;
  counterparty: string;
  category: string;
  amount: number;
  formattedAmount: string;
  type: 'CREDIT' | 'DEBIT';
  status: 'SETTLED' | 'PENDING' | 'SCHEDULED';
  demoTag: string;
}

export interface InvoiceItem {
  id: string;
  clientName: string;
  amount: number;
  formattedAmount: string;
  dueDate: string;
  status: 'OVERDUE' | 'DUE_SOON' | 'PENDING';
  daysOverdue?: number;
}

export interface BusinessHealthMetric {
  overallScore: number; // 0 - 100
  liquidityScore: number;
  solvencyScore: number;
  runwayMonths: number;
  burnRateMonthly: number;
  statusText: 'OPTIMAL' | 'MODERATE' | 'CRITICAL';
}
