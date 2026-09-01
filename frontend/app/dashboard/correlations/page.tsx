'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantCorrelations,
  evaluateMerchantCorrelations,
  BackendCorrelationSummaryDTO,
  BackendSignalCorrelationDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Layers,
  AlertTriangle,
  TrendingUp,
  RefreshCw,
  Info,
  ShieldCheck,
  Activity,
  CheckCircle2,
  HelpCircle,
  Link,
  Target,
} from 'lucide-react';

export default function CorrelationsPage() {
  const [summary, setSummary] = useState<BackendCorrelationSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantCorrelations(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Signal Correlation API');
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
        <div className="h-44 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {[1, 2].map((i) => (
            <div key={i} className="h-64 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              CORRELATION ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Signal Correlation API (`http://localhost:8080/api/v1/merchants/1/correlations`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadData} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
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
              Signal Correlation & Root-Cause Engine
            </h1>
            <Badge variant="demo">ROOT CAUSE ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic cross-engine signal correlation linking financial symptoms to LIKELY_CONTRIBUTOR root causes
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Correlation Engine
        </Button>
      </div>

      {/* Main Correlation Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Total Tracked Correlations</span>
              <div className="flex items-baseline gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{summary.totalCorrelationsCount}</span>
                <span className="text-xs text-slate-400">Models</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">High Confidence Models</span>
              <span className="text-3xl font-bold text-emerald-400">{summary.highConfidenceCount} <span className="text-xs text-slate-400 font-normal">High Confidence</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Primary Root Cause Driver</span>
              <span className="text-xs font-bold text-amber-400 truncate block mt-1">{summary.topLikelyRootCause}</span>
            </div>
          </div>

          <div className="space-y-2 pt-1">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Engine Summary: </strong>
              {summary.summaryExplanation}
            </p>
          </div>
        </Card>
      )}

      {/* Correlations Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Layers className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Evaluated Root-Cause Correlation Models</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {summary?.correlations?.map((crl) => {
            let matchedSignals: any[] = [];
            try {
              matchedSignals = JSON.parse(crl.matchedSignalsJson || '[]');
            } catch (e) {}

            return (
              <Card key={crl.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2 flex-wrap">
                      <Badge variant="cyan" className="text-[10px]">{crl.correlationKey}</Badge>
                      <Badge variant="emerald" className="text-[10px]">{crl.confidenceStatus} CONFIDENCE</Badge>
                    </div>
                    <h3 className="text-lg font-bold text-white uppercase pt-1">Symptom Target: {crl.primaryTarget}</h3>
                    <p className="text-xs text-amber-400 font-bold font-mono">{crl.likelyRootCause}</p>
                  </div>

                  <div className="flex items-center gap-3 shrink-0">
                    <div className="p-3 bg-[#080E18] border border-[#00F0FF]/30 text-right">
                      <span className="text-[10px] text-slate-500 uppercase block font-bold">Correlation Score</span>
                      <span className="text-2xl font-black text-[#00F0FF]">{crl.correlationScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
                    </div>
                  </div>
                </div>

                {/* Matched Contributing Signals Table */}
                <div className="space-y-2">
                  <span className="text-xs font-bold text-white uppercase flex items-center gap-1">
                    <Link className="w-3.5 h-3.5 text-[#00F0FF]" />
                    Matched Contributing Engine Signals ({crl.contributingSignalsCount} Signals)
                  </span>

                  <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                    {matchedSignals.map((sig: any, idx: number) => (
                      <div key={idx} className="p-3 bg-[#05080E] border border-white/5 space-y-1 text-xs font-mono">
                        <span className="text-[10px] text-[#00F0FF] block font-bold">{sig.source}</span>
                        <span className="text-white font-bold block">{sig.signal}</span>
                        <span className="text-[10px] text-slate-500 block">Contribution Weight: {(sig.weight * 100).toFixed(0)}%</span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Details Footer */}
                <div className="p-3 bg-[#080E18] border border-white/5 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs font-mono">
                  <span className="text-slate-400"><strong className="text-slate-200">Ranking Formula:</strong> {crl.rankingFormula}</span>
                  <span className="text-[10px] text-slate-500">Evaluated: {new Date(crl.evaluatedAt).toLocaleTimeString()}</span>
                </div>
              </Card>
            );
          })}
        </div>
      </section>

      {/* Advisory Actions Section */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Recommended Root-Cause Mitigation Actions</h2>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {summary?.recommendedRootCauseActions?.map((act) => (
            <Card key={act.id} className="p-4 flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-white/10 bg-[#05080E]/40 text-xs font-mono">
              <div className="space-y-1">
                <div className="flex items-center gap-2">
                  <Badge variant={act.severity === 'HIGH' ? 'rose' : 'amber'} className="text-[9px]">
                    {act.severity}
                  </Badge>
                  <span className="text-white font-bold">{act.title}</span>
                </div>
                <p className="text-slate-400 font-sans text-[11px]">{act.explanation}</p>
              </div>

              <div className="p-2 bg-[#080E18] border border-[#00F0FF]/20 text-right shrink-0">
                <span className="text-[10px] text-slate-500 block">Recommended Step:</span>
                <span className="text-cyan-300 font-bold text-[11px]">{act.recommendedStep}</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Governance Disclaimer */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Governance & Non-Causation Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Signal Correlation engine evaluates mathematical alignment across financial engines. All identified root causes are explicitly labeled LIKELY_CONTRIBUTOR to distinguish correlation from causation; analyzing root causes does not move funds, modify bank accounts, or execute financial transactions.
        </p>
      </section>
    </div>
  );
}
