'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantCashFlowSummary,
  fetchMerchantMonthlyCashFlow,
  BackendCashFlowSummaryDTO,
  BackendMonthlyCashFlowDTO,
} from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { CashFlowChart } from '@/components/dashboard/CashFlowChart';
import {
  TrendingUp,
  ArrowUpRight,
  ArrowDownRight,
  ShieldCheck,
  AlertTriangle,
  RefreshCw,
  Zap,
  Clock,
  Repeat,
  Activity,
  Layers,
} from 'lucide-react';

export default function CashFlowPage() {
  const [summary, setSummary] = useState<BackendCashFlowSummaryDTO | null>(null);
  const [monthly, setMonthly] = useState<BackendMonthlyCashFlowDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [summaryData, monthlyData] = await Promise.all([
        fetchMerchantCashFlowSummary(1),
        fetchMerchantMonthlyCashFlow(1),
      ]);
      setSummary(summaryData);
      setMonthly(monthlyData);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Cash Flow API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-32 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !summary) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              CASH FLOW API UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Cash Flow Intelligence Engine (`http://localhost:8080/api/v1/merchants/1/cash-flow`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadData} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry API Connection
          </Button>
        </Card>
      </div>
    );
  }

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Cash Flow Intelligence Console
            </h1>
            <Badge variant="demo">SPRING BOOT ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic Liquidity Engine for <span className="text-white font-bold">Apex Retail Solutions [DEMO]</span>
          </p>
        </div>

        <div className="flex items-center gap-3 text-xs font-mono">
          <Badge
            variant={
              summary.liquidityStatus === 'OPTIMAL'
                ? 'emerald'
                : summary.liquidityStatus === 'MODERATE'
                ? 'amber'
                : 'rose'
            }
            className="py-1.5 px-3 gap-1.5"
          >
            <ShieldCheck className="w-4 h-4" />
            {summary.liquidityStatus} LIQUIDITY STATUS
          </Badge>
        </div>
      </div>

      {/* 4 Summary Cards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card className="space-y-2">
          <span className="text-xs uppercase text-slate-400 font-medium">Total Cash Inflows</span>
          <div className="text-2xl sm:text-3xl font-bold text-[#00F0FF]">
            +{formatINR(summary.totalInflows)}
          </div>
          <span className="text-[10px] text-slate-500">Historical Inflow Ledger</span>
        </Card>

        <Card className="space-y-2">
          <span className="text-xs uppercase text-slate-400 font-medium">Total Cash Outflows</span>
          <div className="text-2xl sm:text-3xl font-bold text-rose-400">
            -{formatINR(summary.totalOutflows)}
          </div>
          <span className="text-[10px] text-slate-500">Historical Outflow Ledger</span>
        </Card>

        <Card className="space-y-2">
          <span className="text-xs uppercase text-slate-400 font-medium">Net Cash Surplus</span>
          <div className="text-2xl sm:text-3xl font-bold text-[#00E599]">
            {formatINR(summary.netCashFlow)}
          </div>
          <span className="text-[10px] text-slate-500">Net Surplus Position</span>
        </Card>

        <Card className="space-y-2">
          <span className="text-xs uppercase text-slate-400 font-medium">Calculated Cash Runway</span>
          <div className="text-2xl sm:text-3xl font-bold text-white">
            {summary.cashRunwayMonths} Months
          </div>
          <span className="text-[10px] text-slate-500">Based on Burn Rate</span>
        </Card>
      </section>

      {/* Detailed Cash Flow Intelligence Grid */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Main Chart */}
        <div className="lg:col-span-2">
          <CashFlowChart />
        </div>

        {/* Operating & Liquidity Breakdown */}
        <div className="space-y-4">
          <Card className="space-y-4">
            <div className="flex items-center justify-between border-b border-white/10 pb-3">
              <div className="flex items-center gap-2 text-xs font-bold text-white uppercase">
                <Zap className="w-4 h-4 text-[#00F0FF]" />
                <span>Operating Cash Dynamics</span>
              </div>
              <Badge variant="cyan">DECIMAL ENGINE</Badge>
            </div>

            <div className="space-y-3 text-xs">
              <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
                <div className="flex justify-between text-slate-400">
                  <span>Operating Inflows (Sales)</span>
                  <span className="text-[#00F0FF] font-bold">+{formatINR(summary.operatingInflows)}</span>
                </div>
                <p className="text-[10px] text-slate-500">Core business sales collections</p>
              </div>

              <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
                <div className="flex justify-between text-slate-400">
                  <span>Operating Outflows</span>
                  <span className="text-rose-400 font-bold">-{formatINR(summary.operatingOutflows)}</span>
                </div>
                <p className="text-[10px] text-slate-500">Rent, inventory, payroll, utilities</p>
              </div>

              <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
                <div className="flex justify-between text-slate-400">
                  <span>Average Monthly Burn Rate</span>
                  <span className="text-white font-bold">{formatINR(summary.burnRate)}</span>
                </div>
                <p className="text-[10px] text-slate-500">Average monthly cash outflow</p>
              </div>

              <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
                <div className="flex justify-between text-slate-400 flex items-center gap-1">
                  <Repeat className="w-3.5 h-3.5 text-amber-400" />
                  <span>Recurring Expense Estimate</span>
                  <span className="text-amber-400 font-bold">{formatINR(summary.recurringExpensesEstimate)}</span>
                </div>
                <p className="text-[10px] text-slate-500">Estimated fixed rent, payroll, utilities</p>
              </div>

              <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
                <div className="flex justify-between text-slate-400 flex items-center gap-1">
                  <Clock className="w-3.5 h-3.5 text-rose-400" />
                  <span>Upcoming Payable Pressure</span>
                  <span className="text-rose-400 font-bold">{formatINR(summary.upcomingPayablePressure)}</span>
                </div>
                <p className="text-[10px] text-slate-500">Pending invoices & vendor dues</p>
              </div>
            </div>
          </Card>
        </div>
      </section>
    </div>
  );
}
