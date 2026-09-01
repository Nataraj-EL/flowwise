'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantPayablesSummary,
  BackendPayablesSummaryDTO,
  BackendPayableDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  CreditCard,
  AlertTriangle,
  CheckCircle2,
  Clock,
  PieChart,
  ShieldAlert,
  RefreshCw,
  Building2,
  Layers,
  Zap,
} from 'lucide-react';

export default function PayablesPage() {
  const [summary, setSummary] = useState<BackendPayablesSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');

  const loadPayables = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantPayablesSummary(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Payables Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPayables();
  }, []);

  const formatINR = (val: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(val || 0);
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
          {[1, 2, 3, 4, 5, 6].map((i) => (
            <div key={i} className="h-28 bg-[#0E1116] border border-white/10"></div>
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
              PAYABLES SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Payables API (`http://localhost:8080/api/v1/merchants/1/payables/summary`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadPayables} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const filteredPayables = (summary.payables || []).filter((p) => {
    if (filterStatus === 'ALL') return true;
    return p.status === filterStatus;
  });

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'DUE_TODAY':
        return <Badge variant="rose" className="text-[9px]">DUE TODAY</Badge>;
      case 'DUE_7_DAYS':
        return <Badge variant="amber" className="text-[9px]">DUE IN 7 DAYS</Badge>;
      case 'DUE_30_DAYS':
        return <Badge variant="cyan" className="text-[9px]">DUE IN 30 DAYS</Badge>;
      case 'OVERDUE':
        return <Badge variant="rose" className="text-[9px]">OVERDUE</Badge>;
      case 'PAID':
        return <Badge variant="emerald" className="text-[9px]">PAID</Badge>;
      default:
        return <Badge variant="neutral" className="text-[9px]">{status}</Badge>;
    }
  };

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Payables Intelligence
            </h1>
            <Badge variant="demo">VENDOR OBLIGATIONS & PAYMENT PRESSURE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic vendor bills tracking, due-date pressure analysis, and short-term cash flow impact
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs">
          <CreditCard className="w-4 h-4 text-[#00F0FF]" />
          {summary.totalBillsCount} VENDOR BILLS LEDGER
        </Badge>
      </div>

      {/* Summary Scorecards Grid */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CreditCard className="w-3.5 h-3.5 text-[#00F0FF]" />
            Total Outstanding
          </span>
          <div className="text-xl font-bold text-white">{formatINR(summary.totalOutstanding)}</div>
          <div className="text-[11px] text-slate-500">Unpaid vendor balances</div>
        </Card>

        <Card className="space-y-2 border-rose-500/30 bg-[#0C080A]">
          <span className="text-[10px] text-rose-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            Due Today
          </span>
          <div className="text-xl font-bold text-rose-400">{formatINR(summary.dueToday)}</div>
          <div className="text-[11px] text-slate-500">Immediate cash demand</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-amber-400" />
            Due in 7 Days
          </span>
          <div className="text-xl font-bold text-amber-400">{formatINR(summary.due7Days)}</div>
          <div className="text-[11px] text-slate-500">Short-term liquidity demand</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-[#00F0FF]" />
            Due in 30 Days
          </span>
          <div className="text-xl font-bold text-[#00F0FF]">{formatINR(summary.due30Days)}</div>
          <div className="text-[11px] text-slate-500">Monthly obligation schedule</div>
        </Card>

        <Card className="space-y-2 border-rose-500/40">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <AlertTriangle className="w-3.5 h-3.5 text-rose-400" />
            Total Overdue
          </span>
          <div className="text-xl font-bold text-rose-400">{formatINR(summary.totalOverdue)}</div>
          <div className="text-[11px] text-slate-500">{summary.overdueBillsCount} bills past due date</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Payment Coverage
          </span>
          <div className="text-xl font-bold text-[#00E599]">{summary.paymentCoverageRatioPct}%</div>
          <div className="text-[11px] text-slate-500">Paid vs Total Invoiced</div>
        </Card>
      </section>

      {/* Near-Term Payment Pressure Callout */}
      <Card variant="glow-cyan" className="p-6 space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
          <div className="flex items-center gap-2">
            <Zap className="w-5 h-5 text-rose-400" />
            <h3 className="text-base font-bold text-white uppercase">Near-Term Payment Pressure Analysis</h3>
          </div>
          <Badge
            variant={summary.upcomingPayablePressure > 50000 ? 'rose' : 'cyan'}
            className="text-[10px] uppercase"
          >
            {summary.upcomingPayablePressure > 50000 ? 'HIGH SHORT-TERM CASH DEMAND' : 'MODERATE PAYMENT PRESSURE'}
          </Badge>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Upcoming Payment Pressure (7-Day + Overdue):</span>
            <p className="text-sm font-bold text-rose-400">{formatINR(summary.upcomingPayablePressure)}</p>
          </div>

          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Largest Vendor Obligation:</span>
            <p className="text-sm font-bold text-white">{summary.largestVendorObligation}</p>
          </div>

          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Largest Obligation Amount:</span>
            <p className="text-sm font-bold text-[#00F0FF]">{formatINR(summary.largestVendorAmount)}</p>
          </div>
        </div>

        <div className="p-3 bg-[#05080E] border border-[#00F0FF]/30 text-xs font-sans text-slate-300">
          <span className="text-[#00F0FF] font-bold font-mono uppercase block mb-1">
            Liquidity Allocation Advisory:
          </span>
          Near-term short-term obligations stand at {formatINR(summary.upcomingPayablePressure)}. Reserving funds in your primary account prior to due dates will preserve supplier credit terms and prevent penalty charges.
        </div>
      </Card>

      {/* Vendor Bills Ledger Section */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Layers className="w-5 h-5 text-[#00F0FF]" />
            Vendor Bills Ledger
          </h2>

          {/* Filter Controls */}
          <div className="flex items-center gap-1.5 overflow-x-auto text-xs">
            {['ALL', 'OVERDUE', 'DUE_TODAY', 'DUE_7_DAYS', 'DUE_30_DAYS', 'PAID'].map((st) => (
              <button
                key={st}
                onClick={() => setFilterStatus(st)}
                className={`px-2.5 py-1 font-bold uppercase transition-colors shrink-0 ${
                  filterStatus === st
                    ? 'bg-[#00F0FF] text-black shadow-[0_0_10px_rgba(0,240,255,0.3)]'
                    : 'text-slate-400 hover:text-white bg-white/5 border border-white/10'
                }`}
              >
                {st.replace(/_/g, ' ')}
              </button>
            ))}
          </div>
        </div>

        {/* Ledger Table */}
        <Card className="p-0 overflow-x-auto border-white/10">
          <table className="w-full text-left text-xs font-mono">
            <tbody className="divide-y divide-white/5">
              <tr className="bg-[#050608] border-b border-white/10 text-slate-400 uppercase text-[10px]">
                <th className="py-3 px-4">Bill Ref</th>
                <th className="py-3 px-4">Vendor</th>
                <th className="py-3 px-4">Category</th>
                <th className="py-3 px-4">Bill Amount</th>
                <th className="py-3 px-4">Amount Paid</th>
                <th className="py-3 px-4">Outstanding</th>
                <th className="py-3 px-4">Due Date</th>
                <th className="py-3 px-4">Status</th>
              </tr>
              {filteredPayables.length === 0 ? (
                <tr>
                  <td colSpan={8} className="py-8 text-center text-slate-500">
                    No vendor bills found matching status filter &quot;{filterStatus}&quot;.
                  </td>
                </tr>
              ) : (
                filteredPayables.map((bill) => (
                  <tr key={bill.id} className="hover:bg-white/[0.02] transition-colors">
                    <td className="py-3 px-4 font-bold text-white">{bill.billReference}</td>
                    <td className="py-3 px-4 text-slate-200">{bill.vendor}</td>
                    <td className="py-3 px-4">
                      <Badge variant="cyan" className="text-[9px] uppercase">{bill.category}</Badge>
                    </td>
                    <td className="py-3 px-4 text-slate-300">{formatINR(bill.billAmount)}</td>
                    <td className="py-3 px-4 text-slate-400">{formatINR(bill.amountPaid)}</td>
                    <td className="py-3 px-4 font-bold text-rose-400">{formatINR(bill.outstandingAmount)}</td>
                    <td className="py-3 px-4 text-slate-400">
                      {bill.dueDate ? new Date(bill.dueDate).toLocaleDateString('en-IN') : 'N/A'}
                    </td>
                    <td className="py-3 px-4">{getStatusBadge(bill.status)}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </Card>
      </section>
    </div>
  );
}
