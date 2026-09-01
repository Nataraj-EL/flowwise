'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantReceivablesSummary,
  BackendReceivablesSummaryDTO,
  BackendReceivableDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  FileText,
  AlertTriangle,
  CheckCircle2,
  Clock,
  PieChart,
  ShieldAlert,
  RefreshCw,
  ArrowUpRight,
  UserCheck,
  Building2,
  Layers,
} from 'lucide-react';

export default function ReceivablesPage() {
  const [summary, setSummary] = useState<BackendReceivablesSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');

  const loadReceivables = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantReceivablesSummary(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Receivables Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadReceivables();
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
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
          {[1, 2, 3, 4, 5].map((i) => (
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
              RECEIVABLES SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Receivables API (`http://localhost:8080/api/v1/merchants/1/receivables/summary`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadReceivables} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const filteredReceivables = (summary.receivables || []).filter((r) => {
    if (filterStatus === 'ALL') return true;
    return r.status === filterStatus;
  });

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'CURRENT':
        return <Badge variant="cyan" className="text-[9px]">CURRENT</Badge>;
      case 'OVERDUE_1_30':
        return <Badge variant="amber" className="text-[9px]">1-30 DAYS OVERDUE</Badge>;
      case 'OVERDUE_31_60':
        return <Badge variant="amber" className="text-[9px]">31-60 DAYS OVERDUE</Badge>;
      case 'OVERDUE_60_PLUS':
        return <Badge variant="rose" className="text-[9px]">60+ DAYS OVERDUE</Badge>;
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
              Receivables Intelligence
            </h1>
            <Badge variant="demo">AGING & CONCENTRATION ANALYSIS</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic B2B invoice aging analysis, debtor concentration, and near-term collection potential
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs">
          <FileText className="w-4 h-4 text-[#00F0FF]" />
          {summary.totalInvoicesCount} B2B INVOICES LEDGER
        </Badge>
      </div>

      {/* Summary Cards Grid */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <FileText className="w-3.5 h-3.5 text-[#00F0FF]" />
            Total Outstanding
          </span>
          <div className="text-2xl font-bold text-white">{formatINR(summary.totalOutstanding)}</div>
          <div className="text-[11px] text-slate-500">Uncollected B2B balances</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Current (Not Due)
          </span>
          <div className="text-2xl font-bold text-[#00E599]">{formatINR(summary.currentReceivables)}</div>
          <div className="text-[11px] text-slate-500">Within payment terms</div>
        </Card>

        <Card className="space-y-2 border-rose-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            Total Overdue
          </span>
          <div className="text-2xl font-bold text-rose-400">{formatINR(summary.totalOverdue)}</div>
          <div className="text-[11px] text-slate-500">{summary.overdueInvoicesCount} invoices past due</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-[#00F0FF]" />
            Collection Rate
          </span>
          <div className="text-2xl font-bold text-[#00F0FF]">{summary.collectionRatePct}%</div>
          <div className="text-[11px] text-slate-500">Received vs Invoiced</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <PieChart className="w-3.5 h-3.5 text-amber-400" />
            Concentration Ratio
          </span>
          <div className="text-2xl font-bold text-amber-400">{summary.concentrationRatioPct}%</div>
          <div className="text-[11px] text-slate-500 truncate">{summary.largestOutstandingCounterparty}</div>
        </Card>
      </section>

      {/* Aging Buckets Breakdown Grid */}
      <section className="space-y-4">
        <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
          <Clock className="w-5 h-5 text-[#00F0FF]" />
          Invoice Aging Breakdown
        </h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <Card className="space-y-2 border-[#00E599]/30 bg-[#070B09]">
            <span className="text-[10px] text-[#00E599] font-bold uppercase">Current (Not Due)</span>
            <div className="text-xl font-bold text-white">{formatINR(summary.currentReceivables)}</div>
            <p className="text-[11px] text-slate-400 font-sans">Payment expected within active credit terms</p>
          </Card>

          <Card className="space-y-2 border-amber-500/30 bg-[#0C0A07]">
            <span className="text-[10px] text-amber-400 font-bold uppercase">1 – 30 Days Overdue</span>
            <div className="text-xl font-bold text-amber-400">{formatINR(summary.overdue1To30Days)}</div>
            <p className="text-[11px] text-slate-400 font-sans">Minor delay; candidates for near-term follow-up</p>
          </Card>

          <Card className="space-y-2 border-amber-600/30 bg-[#0E0906]">
            <span className="text-[10px] text-amber-500 font-bold uppercase">31 – 60 Days Overdue</span>
            <div className="text-xl font-bold text-amber-500">{formatINR(summary.overdue31To60Days)}</div>
            <p className="text-[11px] text-slate-400 font-sans">Moderate delay; requires payment schedule review</p>
          </Card>

          <Card className="space-y-2 border-rose-500/40 bg-[#0C080A]">
            <span className="text-[10px] text-rose-400 font-bold uppercase">60+ Days Overdue</span>
            <div className="text-xl font-bold text-rose-400">{formatINR(summary.overdue60PlusDays)}</div>
            <p className="text-[11px] text-slate-400 font-sans">Severe aging; high collection risk threshold</p>
          </Card>
        </div>
      </section>

      {/* Counterparty Concentration Risk Callout */}
      <Card variant="glow-cyan" className="p-6 space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
          <div className="flex items-center gap-2">
            <Building2 className="w-5 h-5 text-[#00F0FF]" />
            <h3 className="text-base font-bold text-white uppercase">Debtor Counterparty Concentration</h3>
          </div>
          <Badge
            variant={summary.concentrationRatioPct > 40 ? 'amber' : 'emerald'}
            className="text-[10px] uppercase"
          >
            {summary.concentrationRatioPct > 40 ? 'HIGH CONCENTRATION RISK (>40%)' : 'BALANCED DEBTOR DISTRIBUTION'}
          </Badge>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Largest Outstanding Debtor:</span>
            <p className="text-sm font-bold text-white">{summary.largestOutstandingCounterparty}</p>
          </div>

          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Outstanding Amount Owed:</span>
            <p className="text-sm font-bold text-[#00F0FF]">{formatINR(summary.largestCounterpartyAmount)}</p>
          </div>

          <div className="space-y-1">
            <span className="text-slate-400 font-mono">Estimated Near-Term Collection:</span>
            <p className="text-sm font-bold text-[#00E599]">{formatINR(summary.estimatedNearTermCollection)}</p>
          </div>
        </div>

        <div className="p-3 bg-[#05080E] border border-[#00F0FF]/30 text-xs font-sans text-slate-300">
          <span className="text-[#00F0FF] font-bold font-mono uppercase block mb-1">
            Liquidity Impact Grounding:
          </span>
          Collecting {formatINR(summary.estimatedNearTermCollection)} from current and 1-30 day overdue invoices will directly increase primary liquid reserves and expand available cash runway.
        </div>
      </Card>

      {/* Invoice Ledger Section */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Layers className="w-5 h-5 text-[#00F0FF]" />
            B2B Invoice Ledger
          </h2>

          {/* Filter Controls */}
          <div className="flex items-center gap-1.5 overflow-x-auto text-xs">
            {['ALL', 'CURRENT', 'OVERDUE_1_30', 'OVERDUE_31_60', 'OVERDUE_60_PLUS', 'PAID'].map((st) => (
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
            <thead className="bg-[#050608] border-b border-white/10 text-slate-400 uppercase text-[10px]">
              <tr>
                <th className="py-3 px-4">Invoice Ref</th>
                <th className="py-3 px-4">Counterparty</th>
                <th className="py-3 px-4">Invoice Amount</th>
                <th className="py-3 px-4">Received</th>
                <th className="py-3 px-4">Outstanding</th>
                <th className="py-3 px-4">Due Date</th>
                <th className="py-3 px-4">Aging Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-white/5">
              {filteredReceivables.length === 0 ? (
                <tr>
                  <td colSpan={7} className="py-8 text-center text-slate-500">
                    No invoice records found matching status filter &quot;{filterStatus}&quot;.
                  </td>
                </tr>
              ) : (
                filteredReceivables.map((inv) => (
                  <tr key={inv.id} className="hover:bg-white/[0.02] transition-colors">
                    <td className="py-3 px-4 font-bold text-white">{inv.invoiceReference}</td>
                    <td className="py-3 px-4 text-slate-200">{inv.counterparty}</td>
                    <td className="py-3 px-4 text-slate-300">{formatINR(inv.invoiceAmount)}</td>
                    <td className="py-3 px-4 text-slate-400">{formatINR(inv.amountReceived)}</td>
                    <td className="py-3 px-4 font-bold text-[#00F0FF]">{formatINR(inv.outstandingAmount)}</td>
                    <td className="py-3 px-4 text-slate-400">
                      {inv.dueDate ? new Date(inv.dueDate).toLocaleDateString('en-IN') : 'N/A'}
                    </td>
                    <td className="py-3 px-4">{getStatusBadge(inv.status)}</td>
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
