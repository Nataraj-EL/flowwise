'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantCashManagement,
  fetchMerchantPaymentPlan,
  BackendCashManagementSummaryDTO,
  BackendPaymentPlanDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Wallet,
  AlertTriangle,
  CheckCircle2,
  TrendingUp,
  ShieldAlert,
  RefreshCw,
  Clock,
  ArrowRight,
  Info,
  Calendar,
  Layers,
  HelpCircle,
} from 'lucide-react';

export default function CashManagementPage() {
  const [summary, setSummary] = useState<BackendCashManagementSummaryDTO | null>(null);
  const [plan, setPlan] = useState<BackendPaymentPlanDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showBasis, setShowBasis] = useState<boolean>(false);

  const loadCashManagement = async () => {
    setLoading(true);
    setError(null);
    try {
      const [sumData, planData] = await Promise.all([
        fetchMerchantCashManagement(1),
        fetchMerchantPaymentPlan(1),
      ]);
      setSummary(sumData);
      setPlan(planData);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Cash Management API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCashManagement();
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
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-28 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !summary || !plan) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              CASH MANAGEMENT SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Cash Management API (`http://localhost:8080/api/v1/merchants/1/cash-management`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadCashManagement} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const isSafe = summary.paymentRiskStatus === 'SAFE';
  const isCaution = summary.paymentRiskStatus === 'CAUTION';

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Cash Management & Payment Planning
            </h1>
            <Badge variant="demo">ADVISORY LIQUIDITY GUIDE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic cash projections, safe payment limits, and prioritized vendor obligation scheduling
          </p>
        </div>

        <Badge
          variant={isSafe ? 'cyan' : isCaution ? 'amber' : 'rose'}
          className="py-2 px-3 gap-1.5 font-mono text-xs uppercase"
        >
          <Wallet className="w-4 h-4" />
          RISK STATUS: {summary.paymentRiskStatus}
        </Badge>
      </div>

      {/* Risk Banner */}
      <Card
        variant={isSafe ? 'glow-cyan' : 'glow-emerald'}
        className={`p-5 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 ${
          isCaution ? 'border-amber-500/40' : !isSafe ? 'border-rose-500/40' : ''
        }`}
      >
        <div className="flex items-center gap-3">
          <div
            className={`w-10 h-10 border flex items-center justify-center ${
              isSafe
                ? 'bg-[#00E599]/10 border-[#00E599]/40 text-[#00E599]'
                : isCaution
                ? 'bg-amber-500/10 border-amber-500/40 text-amber-400'
                : 'bg-rose-500/10 border-rose-500/40 text-rose-400'
            }`}
          >
            {isSafe ? <CheckCircle2 className="w-5 h-5" /> : <ShieldAlert className="w-5 h-5" />}
          </div>
          <div>
            <h3 className="text-sm font-bold text-white uppercase">{summary.paymentRiskStatus} Liquidity Status</h3>
            <p className="text-xs text-slate-300 font-sans">{plan.executionAdvice}</p>
          </div>
        </div>

        <button
          onClick={() => setShowBasis(!showBasis)}
          className="text-xs text-[#00F0FF] hover:underline flex items-center gap-1 shrink-0"
        >
          <Info className="w-3.5 h-3.5" />
          {showBasis ? 'Hide Calculation Basis' : 'View Calculation Basis'}
        </button>
      </Card>

      {/* Calculation Basis & Assumptions Drawer */}
      {showBasis && (
        <Card className="p-5 space-y-3 bg-[#05080E] border-[#00F0FF]/30 text-xs font-mono">
          <div className="flex items-center gap-2 text-[#00F0FF] font-bold uppercase">
            <Info className="w-4 h-4" />
            Deterministic Calculation Basis
          </div>
          <p className="text-slate-300 font-sans leading-relaxed">{summary.calculationBasis}</p>
          <div className="space-y-1">
            <span className="text-slate-400 font-bold block">Assumptions & Rules:</span>
            <ul className="list-disc list-inside text-slate-400 space-y-0.5 font-sans">
              {summary.assumptions.map((asm, idx) => (
                <li key={idx}>{asm}</li>
              ))}
            </ul>
          </div>
        </Card>
      )}

      {/* KPI Cards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Wallet className="w-3.5 h-3.5 text-[#00F0FF]" />
            Current Available Cash
          </span>
          <div className="text-2xl font-bold text-white">{formatINR(summary.currentAvailableCash)}</div>
          <div className="text-[11px] text-slate-500">Connected bank balances</div>
        </Card>

        <Card className="space-y-2 border-rose-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-rose-400" />
            7-Day Obligations
          </span>
          <div className="text-2xl font-bold text-rose-400">{formatINR(summary.upcoming7DayObligations)}</div>
          <div className="text-[11px] text-slate-500">Bills due within 7 days</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <TrendingUp className="w-3.5 h-3.5 text-[#00E599]" />
            7-Day Expected Collections
          </span>
          <div className="text-2xl font-bold text-[#00E599]">{formatINR(summary.expected7DayCollections)}</div>
          <div className="text-[11px] text-slate-500">Estimated receivables inflow</div>
        </Card>

        <Card className="space-y-2 border-cyan-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00F0FF]" />
            Safe Payment Capacity
          </span>
          <div className="text-2xl font-bold text-[#00F0FF]">{formatINR(summary.safePaymentCapacity)}</div>
          <div className="text-[11px] text-slate-500 font-bold uppercase text-amber-400">Advisory Limit</div>
        </Card>
      </section>

      {/* 7 & 30-Day Projections */}
      <section className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <Card className="p-6 space-y-3 border-white/10">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <span className="text-xs font-bold text-slate-400 uppercase flex items-center gap-2">
              <Calendar className="w-4 h-4 text-[#00F0FF]" />
              7-Day Projected Cash Position
            </span>
            <span className={`text-lg font-bold ${summary.projected7DayCashPosition >= 0 ? 'text-[#00E599]' : 'text-rose-400'}`}>
              {formatINR(summary.projected7DayCashPosition)}
            </span>
          </div>
          <p className="text-xs text-slate-300 font-sans leading-relaxed">
            Available Cash + 7D Collections (₹{summary.expected7DayCollections}) - 7D Obligations (₹{summary.upcoming7DayObligations}).
          </p>
        </Card>

        <Card className="p-6 space-y-3 border-white/10">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <span className="text-xs font-bold text-slate-400 uppercase flex items-center gap-2">
              <Calendar className="w-4 h-4 text-[#00F0FF]" />
              30-Day Projected Cash Position
            </span>
            <span className={`text-lg font-bold ${summary.projected30DayCashPosition >= 0 ? 'text-[#00E599]' : 'text-rose-400'}`}>
              {formatINR(summary.projected30DayCashPosition)}
            </span>
          </div>
          <p className="text-xs text-slate-300 font-sans leading-relaxed">
            Available Cash + 30D Collections (₹{summary.expected30DayCollections}) - 30D Obligations (₹{summary.upcoming30DayObligations}).
          </p>
        </Card>
      </section>

      {/* Prioritized Payment Plan Table */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Layers className="w-5 h-5 text-[#00F0FF]" />
            Prioritized Vendor Payment Schedule
          </h2>
          <span className="text-xs text-slate-400">
            Recommended Total: <span className="text-[#00E599] font-bold">{formatINR(plan.recommendedPaymentTotal)}</span> | Deferred: <span className="text-amber-400 font-bold">{formatINR(plan.deferredPaymentTotal)}</span>
          </span>
        </div>

        <Card className="p-0 overflow-hidden border-white/10">
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse text-xs">
              <thead>
                <tr className="bg-[#05080E] border-b border-white/10 text-slate-400 uppercase text-[10px]">
                  <th className="p-3">Priority</th>
                  <th className="p-3">Vendor</th>
                  <th className="p-3">Category</th>
                  <th className="p-3">Due Date</th>
                  <th className="p-3 text-right">Outstanding Amount</th>
                  <th className="p-3">Advisory Status</th>
                  <th className="p-3">Rationale</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-white/5">
                {plan.prioritizedPayments.length === 0 && plan.deferredPayments.length === 0 ? (
                  <tr>
                    <td colSpan={7} className="p-6 text-center text-slate-500">
                      No pending vendor payables found in ledger.
                    </td>
                  </tr>
                ) : (
                  [...plan.prioritizedPayments, ...plan.deferredPayments].map((item, idx) => (
                    <tr key={idx} className="hover:bg-white/5 transition-colors">
                      <td className="p-3">
                        <Badge
                          variant={
                            item.priority === 'P1_CRITICAL'
                              ? 'rose'
                              : item.priority === 'P2_HIGH'
                              ? 'amber'
                              : 'cyan'
                          }
                          className="text-[9px]"
                        >
                          {item.priority.replace(/_/g, ' ')}
                        </Badge>
                      </td>
                      <td className="p-3 font-bold text-white">{item.vendor}</td>
                      <td className="p-3 text-slate-400">{item.category}</td>
                      <td className="p-3 text-slate-300">
                        {item.dueDate} ({item.daysUntilDue < 0 ? `${Math.abs(item.daysUntilDue)}d overdue` : `${item.daysUntilDue}d left`})
                      </td>
                      <td className="p-3 text-right font-bold text-[#00F0FF]">{formatINR(item.outstandingAmount)}</td>
                      <td className="p-3">
                        <Badge
                          variant={item.advisoryStatus === 'RECOMMENDED' ? 'emerald' : 'amber'}
                          className="text-[9px]"
                        >
                          {item.advisoryStatus}
                        </Badge>
                      </td>
                      <td className="p-3 text-slate-400 font-sans max-w-xs truncate">{item.priorityReason}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </Card>
      </section>

      {/* Advisory Disclaimer Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Liquidity Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          {summary.advisoryNotice} Flowwise provides calculated recommendations to support merchant financial decision-making. No transactions are executed automatically.
        </p>
      </section>
    </div>
  );
}
