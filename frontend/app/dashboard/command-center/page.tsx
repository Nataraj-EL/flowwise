'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantCommandCenter,
  BackendCommandCenterSnapshotDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  LayoutDashboard,
  AlertTriangle,
  CheckCircle2,
  PieChart,
  ShieldAlert,
  RefreshCw,
  TrendingUp,
  ArrowRight,
  Zap,
  Clock,
  Briefcase,
  Activity,
} from 'lucide-react';

export default function CommandCenterPage() {
  const [snapshot, setSnapshot] = useState<BackendCommandCenterSnapshotDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadCommandCenter = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantCommandCenter(1);
      setSnapshot(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Command Center API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCommandCenter();
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
        <div className="h-20 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
          {[1, 2, 3, 4, 5, 6].map((i) => (
            <div key={i} className="h-28 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !snapshot) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              COMMAND CENTER SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Command Center API (`http://localhost:8080/api/v1/merchants/1/command-center`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadCommandCenter} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const isHealthy = snapshot.overallFinancialStatus === 'HEALTHY';
  const isWatch = snapshot.overallFinancialStatus === 'WATCH';

  return (
    <div className="space-y-8 font-mono">
      {/* Executive Header Banner */}
      <div className="p-6 bg-[#080B10] border border-[#00F0FF]/30 space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div className="space-y-1">
            <div className="flex items-center gap-2">
              <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
                Financial Command Center
              </h1>
              <Badge variant="demo">EXECUTIVE BRIEFING SNAPSHOT</Badge>
            </div>
            <p className="text-xs sm:text-sm text-slate-400 font-mono">
              Unified financial intelligence synthesis across Cash Flow, Health, Working Capital, Receivables, Payables & Forecast
            </p>
          </div>

          <div className="flex items-center gap-3 shrink-0">
            <div className="text-right font-mono">
              <div className="text-xs text-slate-400 uppercase">Health Score</div>
              <div className="text-2xl font-bold text-[#00F0FF]">{snapshot.overallHealthScore}/100</div>
            </div>
            <Badge
              variant={isHealthy ? 'emerald' : isWatch ? 'amber' : 'rose'}
              className="py-2.5 px-4 font-mono text-xs uppercase text-center"
            >
              STATUS: {snapshot.overallFinancialStatus.replace(/_/g, ' ')}
            </Badge>
          </div>
        </div>

        <div className="flex items-center gap-2 text-[10px] text-slate-500 font-mono border-t border-white/10 pt-3">
          <Clock className="w-3.5 h-3.5 text-[#00F0FF]" />
          <span>Briefing generated at {new Date(snapshot.generatedAt).toLocaleString()}</span>
        </div>
      </div>

      {/* Core KPIs Grid */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Available Cash
          </span>
          <div className="text-xl font-bold text-[#00E599]">{formatINR(snapshot.availableCash)}</div>
          <div className="text-[11px] text-slate-500">Liquid reserves</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <TrendingUp className="w-3.5 h-3.5 text-[#00F0FF]" />
            Net Cash Flow
          </span>
          <div className={`text-xl font-bold ${snapshot.netCashFlow >= 0 ? 'text-[#00E599]' : 'text-rose-400'}`}>
            {formatINR(snapshot.netCashFlow)}
          </div>
          <div className="text-[11px] text-slate-500">Current period net</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Briefcase className="w-3.5 h-3.5 text-[#00F0FF]" />
            Working Capital
          </span>
          <div className="text-xl font-bold text-white">{snapshot.workingCapitalCoverage}x</div>
          <div className="text-[11px] text-slate-500">Liquidity coverage ratio</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-amber-400" />
            Overdue Receivables
          </span>
          <div className="text-xl font-bold text-amber-400">{formatINR(snapshot.receivablesPressure)}</div>
          <div className="text-[11px] text-slate-500">Uncollected overdue</div>
        </Card>

        <Card className="space-y-2 border-rose-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            Payable Pressure
          </span>
          <div className="text-xl font-bold text-rose-400">{formatINR(snapshot.payablesPressure)}</div>
          <div className="text-[11px] text-slate-500">Near-term payment demand</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Activity className="w-3.5 h-3.5 text-[#00F0FF]" />
            Forecast Outlook
          </span>
          <div className="text-xl font-bold text-[#00F0FF]">{snapshot.forecastRisk.replace(/_/g, ' ')}</div>
          <div className="text-[11px] text-slate-500">30-90 Day Runway Risk</div>
        </Card>
      </section>

      {/* Top 3 Priorities Section */}
      <section className="space-y-4">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Zap className="w-5 h-5 text-rose-400" />
            Top Executive Priorities & Recommended Actions
          </h2>
          <Badge variant="rose" className="text-[10px]">PRIORITIZED ACTION PLAN</Badge>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {snapshot.top3Priorities.length === 0 ? (
            <Card className="col-span-3 py-8 text-center text-slate-500">
              No open high-priority recommendations detected. Operational health is optimal.
            </Card>
          ) : (
            snapshot.top3Priorities.map((act, idx) => (
              <Card key={act.id} className="space-y-3 p-5 border-white/10 hover:border-[#00F0FF]/40 transition-colors">
                <div className="flex items-center justify-between">
                  <span className="text-[10px] font-bold text-[#00F0FF] uppercase">Priority #{idx + 1}</span>
                  <Badge variant={act.severity === 'HIGH' ? 'rose' : 'amber'} className="text-[9px]">
                    {act.severity}
                  </Badge>
                </div>

                <h3 className="text-sm font-bold text-white leading-snug">{act.title}</h3>
                <p className="text-xs text-slate-300 font-sans leading-relaxed">{act.explanation}</p>

                <div className="pt-2 border-t border-white/10 text-[11px] space-y-1">
                  <span className="text-[#00F0FF] font-bold uppercase block text-[9px]">Recommended Step:</span>
                  <p className="text-slate-300 font-sans">{act.recommendedStep}</p>
                </div>
              </Card>
            ))
          )}
        </div>
      </section>

      {/* Executive Briefing Signals Box */}
      <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card variant="glow-cyan" className="p-6 space-y-3 border-emerald-500/30">
          <div className="flex items-center gap-2 text-emerald-400 font-bold uppercase text-xs">
            <CheckCircle2 className="w-4 h-4" />
            Key Positive Signal
          </div>
          <p className="text-xs text-slate-200 font-mono leading-relaxed">{snapshot.keyPositiveSignal}</p>
        </Card>

        <Card variant="glow-cyan" className="p-6 space-y-3 border-rose-500/30">
          <div className="flex items-center gap-2 text-rose-400 font-bold uppercase text-xs">
            <ShieldAlert className="w-4 h-4" />
            Key Risk Signal
          </div>
          <p className="text-xs text-slate-200 font-mono leading-relaxed">{snapshot.keyRiskSignal}</p>
        </Card>

        <Card variant="glow-cyan" className="p-6 space-y-3 border-[#00F0FF]/30">
          <div className="flex items-center gap-2 text-[#00F0FF] font-bold uppercase text-xs">
            <Activity className="w-4 h-4" />
            What Changed Summary
          </div>
          <p className="text-xs text-slate-200 font-mono leading-relaxed">{snapshot.whatChangedSummary}</p>
        </Card>
      </section>
    </div>
  );
}
