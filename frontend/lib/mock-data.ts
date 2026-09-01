import { MerchantProfile, MetricData, CashFlowMonthData, TransactionItem, InvoiceItem, BusinessHealthMetric } from '@/types';
import { formatINR } from './utils';

export const DEMO_MERCHANTS: MerchantProfile[] = [
  {
    id: 'demo-merchant-01',
    name: 'Apex Retail Solutions [DEMO]',
    demoGstin: 'DEMO-27-GSTIN-0001',
    category: 'Omnichannel Apparel & Goods',
    isDemoEnvironment: true,
    activeAccountsCount: 3,
  },
  {
    id: 'demo-merchant-02',
    name: 'Zenith Electronics Hub [DEMO]',
    demoGstin: 'DEMO-29-GSTIN-0002',
    category: 'Consumer Electronics Wholesale',
    isDemoEnvironment: true,
    activeAccountsCount: 2,
  },
  {
    id: 'demo-merchant-03',
    name: 'Vanguard Organics [DEMO]',
    demoGstin: 'DEMO-19-GSTIN-0003',
    category: 'FMCG & Organics Supply',
    isDemoEnvironment: true,
    activeAccountsCount: 4,
  },
];

export const DEMO_METRICS: MetricData[] = [
  {
    title: 'Monthly Revenue',
    value: 842500,
    formattedValue: formatINR(842500),
    changeMoM: 14.2,
    trend: 'up',
    subtext: 'vs ₹7,37,740 previous month',
  },
  {
    title: 'Monthly Expenses',
    value: 518200,
    formattedValue: formatINR(518200),
    changeMoM: -3.8,
    trend: 'down',
    subtext: 'Reduced supplier overhead',
  },
  {
    title: 'Available Cash Balance',
    value: 324300,
    formattedValue: formatINR(324300),
    changeMoM: 18.6,
    trend: 'up',
    subtext: 'Across 3 connected demo accounts',
  },
  {
    title: 'Outstanding Receivables',
    value: 185000,
    formattedValue: formatINR(185000),
    changeMoM: -8.1,
    trend: 'down',
    subtext: '4 active demo invoices pending',
  },
  {
    title: 'Upcoming Payables',
    value: 92400,
    formattedValue: formatINR(92400),
    changeMoM: 2.4,
    trend: 'neutral',
    subtext: '2 vendor payables due in 7 days',
  },
  {
    title: 'Net Cash Flow (MoM)',
    value: 324300,
    formattedValue: formatINR(324300),
    changeMoM: 24.5,
    trend: 'up',
    subtext: 'Strong net positive position',
  },
];

export const DEMO_CASHFLOW_SERIES: CashFlowMonthData[] = [
  { month: 'Apr', inflow: 620000, outflow: 480000, netCash: 140000 },
  { month: 'May', inflow: 690000, outflow: 510000, netCash: 180000 },
  { month: 'Jun', inflow: 710000, outflow: 530000, netCash: 180000 },
  { month: 'Jul', inflow: 750000, outflow: 540000, netCash: 210000 },
  { month: 'Aug', inflow: 737740, outflow: 538600, netCash: 199140 },
  { month: 'Sep (Cur)', inflow: 842500, outflow: 518200, netCash: 324300 },
];

export const DEMO_HEALTH: BusinessHealthMetric = {
  overallScore: 88,
  liquidityScore: 92,
  solvencyScore: 84,
  runwayMonths: 4.8,
  burnRateMonthly: 518200,
  statusText: 'OPTIMAL',
};

export const DEMO_TRANSACTIONS: TransactionItem[] = [
  {
    id: 'TXN-DEMO-901',
    date: 'Today, 10:45 AM',
    counterparty: 'Metro Trade Distributors',
    category: 'Inventory Receivable Settlement',
    amount: 142000,
    formattedAmount: formatINR(142000),
    type: 'CREDIT',
    status: 'SETTLED',
    demoTag: 'DEMO-DATA',
  },
  {
    id: 'TXN-DEMO-902',
    date: 'Yesterday, 04:20 PM',
    counterparty: 'CyberSpace Logistics India',
    category: 'Freight & Courier Overhead',
    amount: 28400,
    formattedAmount: formatINR(28400),
    type: 'DEBIT',
    status: 'SETTLED',
    demoTag: 'DEMO-DATA',
  },
  {
    id: 'TXN-DEMO-903',
    date: '28 Aug, 02:15 PM',
    counterparty: 'Precision Packaging Pvt Ltd',
    category: 'Packaging Supply Order',
    amount: 64000,
    formattedAmount: formatINR(64000),
    type: 'DEBIT',
    status: 'PENDING',
    demoTag: 'DEMO-DATA',
  },
  {
    id: 'TXN-DEMO-904',
    date: '26 Aug, 11:30 AM',
    counterparty: 'Zenith Retail Outlets',
    category: 'Bulk Order Payment',
    amount: 215000,
    formattedAmount: formatINR(215000),
    type: 'CREDIT',
    status: 'SETTLED',
    demoTag: 'DEMO-DATA',
  },
];

export const DEMO_INVOICES: InvoiceItem[] = [
  {
    id: 'INV-DEMO-101',
    clientName: 'Kaveri Mart Outlets',
    amount: 85000,
    formattedAmount: formatINR(85000),
    dueDate: '04 Sep 2026',
    status: 'DUE_SOON',
  },
  {
    id: 'INV-DEMO-102',
    clientName: 'Deccan Retail Chains',
    amount: 60000,
    formattedAmount: formatINR(60000),
    dueDate: '12 Sep 2026',
    status: 'PENDING',
  },
  {
    id: 'INV-DEMO-103',
    clientName: 'Sri Balaji Wholesale',
    amount: 40000,
    formattedAmount: formatINR(40000),
    dueDate: '25 Aug 2026',
    status: 'OVERDUE',
    daysOverdue: 7,
  },
];
