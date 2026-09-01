'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantWorkingCapital,
  BackendWorkingCapitalSummaryDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Briefcase,
  AlertTriangle,
  CheckCircle2,
  PieChart,
  ShieldAlert,
  RefreshCw,
  TrendingUp,
  ArrowRight,
  Zap,
  Layers,
} from 'lucide-react';

export default function WorkingCapitalPage() {
  const [summary, setSummary] = useState<BackendWorkingCapitalSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadWorkingCapital = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantWorkingCapital(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Working Capital Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadWorkingCapital();
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
              WORKING CAPITAL SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Working Capital API (`http://localhost:8080/api/v1/merchants/1/working-capital`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadWorkingCapital} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const isHighRisk = summary.cashConversionRiskStatus === 'HIGH_RISK';
  const isModerate = summary.cashConversionRiskStatus === 'MODERATE';

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Working Capital Intelligence
            </h1>
            <Badge variant="demo">BALANCES & LIQUIDITY COVERAGE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Derived net working capital, cash vs. obligations coverage, gap breakdown, and top pressure drivers
          </p>
        </div>

        <Badge
          variant={isHighRisk ? 'rose' : isModerate ? 'amber' : 'emerald'}
          className="py-2 px-3 gap-1.5 font-mono text-xs uppercase"
        >
          <Briefcase className="w-4 h-4" />
          RISK STATUS: {summary.cashConversionRiskStatus.replace(/_/g, ' ')}
        </Badge>
      </div>

      {/* KPI Scorecards Grid */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
        <Card className="space-y-2 border-[#00F0FF]/30">
          <span className="text-[10px] text-[#00F0FF] uppercase font-bold flex items-center gap-1.5">
            <Briefcase className="w-3.5 h-3.5 text-[#00F0FF]" />
            Net Working Capital
          </span>
          <div className="text-xl font-bold text-white">{formatINR(summary.netWorkingCapital)}</div>
          <div className="text-[11px] text-slate-500">Cash + Receivables - Payables</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Available Cash
          </span>
          <div className="text-xl font-bold text-[#00E599]">{formatINR(summary.availableCash)}</div>
          <div className="text-[11px] text-slate-500">Primary bank balances</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <TrendingUp className="w-3.5 h-3.5 text-[#00F0FF]" />
            Receivables Owed
          </span>
          <div className="text-xl font-bold text-white">{formatINR(summary.receivablesOutstanding)}</div>
          <div className="text-[11px] text-slate-500">Uncollected B2B invoices</div>
        </Card>

        <Card className="space-y-2 border-rose-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            Payables Owed
          </span>
          <div className="text-xl font-bold text-rose-400">{formatINR(summary.payablesOutstanding)}</div>
          <div className="text-[11px] text-slate-500">Unpaid vendor bills</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <PieChart className="w-3.5 h-3.5 text-[#00F0FF]" />
            Current Coverage
          </span>
          <div className="text-xl font-bold text-[#00F0FF]">{summary.currentCoverageRatio}x</div>
          <div className="text-[11px] text-slate-500">(Cash + Rec) / Payables</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Zap className="w-3.5 h-3.5 text-amber-400" />
            Near-Term Coverage
          </span>
          <div className="text-xl font-bold text-amber-400">{summary.nearTermCoverageRatio}x</div>
          <div className="text-[11px] text-slate-500">Liquid / 7-Day Pressure</div>
        </Card>
      </section>

      {/* Cash vs Obligations View */}
      <section className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card variant="glow-cyan" className="p-6 space-y-4">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <h3 className="text-base font-bold text-white uppercase flex items-center gap-2">
              <CheckCircle2 className="w-5 h-5 text-[#00E599]" />
              Liquid Assets & Collection Potential
            </h3>
            <Badge variant="emerald" className="text-[9px]">ACTUAL + ESTIMATE</Badge>
          </div>

          <div className="space-y-3 text-xs">
            <div className="flex justify-between py-1.5 border-b border-white/5">
              <span className="text-slate-400">Available Liquid Cash:</span>
              <span className="font-bold text-white">{formatINR(summary.availableCash)}</span>
            </div>

            <div className="flex justify-between py-1.5 border-b border-white/5">
              <span className="text-slate-400">30-Day Receivables Collection Potential:</span>
              <span className="font-bold text-[#00E599]">{formatINR(summary.nearTermCollectionPotential)}</span>
            </div>

            <div className="flex justify-between pt-2 text-sm font-bold border-t border-white/10">
              <span className="text-white">Total Near-Term Liquidity Buffer:</span>
              <span className="text-[#00F0FF]">{formatINR(summary.availableCash + summary.nearTermCollectionPotential)}</span>
            </div>
          </div>
        </Card>

        <Card variant="glow-cyan" className="p-6 space-y-4 border-rose-500/30">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <h3 className="text-base font-bold text-white uppercase flex items-center gap-2">
              <ShieldAlert className="w-5 h-5 text-rose-400" />
              Short-Term Payment Obligations
            </h3>
            <Badge variant="rose" className="text-[9px]">ACTUAL DEMAND</Badge>
          </div>

          <div className="space-y-3 text-xs">
            <div className="flex justify-between py-1.5 border-b border-white/5">
              <span className="text-slate-400">Total Outstanding Payables:</span>
              <span className="font-bold text-slate-200">{formatINR(summary.payablesOutstanding)}</span>
            </div>

            <div className="flex justify-between py-1.5 border-b border-white/5">
              <span className="text-slate-400">Upcoming Payment Pressure (7-Day + Overdue):</span>
              <span className="font-bold text-rose-400">{formatINR(summary.upcomingPayablePressure)}</span>
            </div>

            <div className="flex justify-between pt-2 text-sm font-bold border-t border-white/10">
              <span className="text-white">Working Capital Gap / Deficit:</span>
              <span className="text-amber-400">{formatINR(summary.workingCapitalGap)}</span>
            </div>
          </div>
        </Card>
      </section>

      {/* Top Working Capital Pressure Drivers Callout */}
      <Card variant="glow-cyan" className="p-6 space-y-4">
        <div className="flex items-center gap-2 border-b border-white/10 pb-3">
          <Zap className="w-5 h-5 text-[#00F0FF]" />
          <h3 className="text-base font-bold text-white uppercase">Top Working Capital Pressure Drivers</h3>
        </div>

        <div className="space-y-2 text-xs">
          {summary.topPressureDrivers.map((driver, idx) => (
            <div key={idx} className="p-3 bg-[#05080E] border border-[#00F0FF]/30 flex items-center gap-3">
              <ArrowRight className="w-4 h-4 text-[#00F0FF] shrink-0" />
              <span className="text-slate-200 font-mono leading-relaxed">{driver}</span>
            </div>
          ))}
        </div>

        <div className="p-3 bg-[#070B0E] border border-white/10 text-xs font-sans text-slate-300">
          <span className="text-[#00F0FF] font-bold font-mono uppercase block mb-1">
            Working Capital Summary Explanation:
          </span>
          {summary.summaryExplanation}
        </div>
      </Card>
    </div>
  );
}
