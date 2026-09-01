'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantInterventions,
  evaluateMerchantInterventions,
  acknowledgeIntervention,
  completeIntervention,
  dismissIntervention,
  BackendInterventionSummaryDTO,
  BackendFinancialInterventionDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  ListChecks,
  AlertTriangle,
  TrendingUp,
  RefreshCw,
  Info,
  ShieldCheck,
  Activity,
  CheckCircle2,
  HelpCircle,
  Check,
  X,
  Target,
  Clock,
  Zap,
} from 'lucide-react';

export default function InterventionsPage() {
  const [summary, setSummary] = useState<BackendInterventionSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantInterventions(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Intervention Prioritization API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAcknowledge = async (id: number) => {
    try {
      await acknowledgeIntervention(1, id);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to acknowledge intervention');
    }
  };

  const handleComplete = async (id: number) => {
    try {
      await completeIntervention(1, id);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to complete intervention');
    }
  };

  const handleDismiss = async (id: number) => {
    try {
      await dismissIntervention(1, id);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to dismiss intervention');
    }
  };

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
              INTERVENTION ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Financial Intervention API (`http://localhost:8080/api/v1/merchants/1/interventions`).
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
              Financial Intervention Prioritization
            </h1>
            <Badge variant="demo">INTERVENTION ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic 5-factor weighted priority ranking answering "What should I address first, and why?"
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Interventions
        </Button>
      </div>

      {/* Main Intervention Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Total Tracked Interventions</span>
              <div className="flex items-baseline gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{summary.totalInterventionsCount}</span>
                <span className="text-xs text-slate-400">Total</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Open Active Interventions</span>
              <span className="text-3xl font-bold text-white">{summary.openCount} <span className="text-xs text-slate-400 font-normal">Active Alerts</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">High Priority (&ge;75/100)</span>
              <span className="text-3xl font-bold text-rose-400">{summary.highPriorityCount} <span className="text-xs text-slate-400 font-normal">Urgent Focus</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Top Focus Area</span>
              <span className="text-xs font-bold text-amber-400 truncate block mt-1">{summary.topFocusArea}</span>
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

      {/* Interventions Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <ListChecks className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Ranked Advisory Financial Interventions</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {summary?.interventions?.map((itv) => (
            <Card key={itv.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge variant={itv.priorityScore >= 80 ? 'rose' : 'amber'} className="text-[10px] font-bold">
                      PRIORITY SCORE {itv.priorityScore}/100
                    </Badge>
                    <Badge variant="cyan" className="text-[10px]">{itv.interventionType}</Badge>
                    <Badge variant="demo" className="text-[10px]">{itv.interventionKey}</Badge>
                  </div>
                  <h3 className="text-lg font-bold text-white uppercase pt-1">{itv.title}</h3>
                  <p className="text-xs text-slate-300 font-sans">{itv.description}</p>
                </div>

                <div className="flex items-center gap-2 shrink-0">
                  {itv.status === 'OPEN' || itv.status === 'ACKNOWLEDGED' ? (
                    <>
                      <Button variant="outline" size="sm" onClick={() => handleAcknowledge(itv.id)} className="text-xs text-cyan-300 border-cyan-500/30">
                        Acknowledge
                      </Button>
                      <Button variant="emerald" size="sm" onClick={() => handleComplete(itv.id)} className="text-xs gap-1">
                        <Check className="w-3.5 h-3.5" />
                        Complete
                      </Button>
                      <Button variant="outline" size="sm" onClick={() => handleDismiss(itv.id)} className="text-xs text-slate-400 border-white/10">
                        Dismiss
                      </Button>
                    </>
                  ) : (
                    <Badge variant={itv.status === 'COMPLETED' ? 'emerald' : 'neutral'} className="text-[10px]">
                      {itv.status}
                    </Badge>
                  )}
                </div>
              </div>

              {/* 5-Factor Score Breakdown */}
              <div className="grid grid-cols-1 sm:grid-cols-5 gap-2 text-xs font-mono">
                <div className="p-2 bg-[#05080E] border border-white/5 space-y-0.5 text-center">
                  <span className="text-[9px] text-slate-500 block">Financial Impact (35%)</span>
                  <span className="text-cyan-300 font-bold">{itv.impactScore}/100</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 space-y-0.5 text-center">
                  <span className="text-[9px] text-slate-500 block">Urgency (25%)</span>
                  <span className="text-amber-400 font-bold">{itv.urgencyScore}/100</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 space-y-0.5 text-center">
                  <span className="text-[9px] text-slate-500 block">Risk Reduction (20%)</span>
                  <span className="text-rose-400 font-bold">80.00/100</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 space-y-0.5 text-center">
                  <span className="text-[9px] text-slate-500 block">Goal Impact (10%)</span>
                  <span className="text-emerald-400 font-bold">70.00/100</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 space-y-0.5 text-center">
                  <span className="text-[9px] text-slate-500 block">Confidence (10%)</span>
                  <span className="text-slate-300 font-bold">{itv.confidenceStatus}</span>
                </div>
              </div>

              {/* Benefit vs Risk-If-Ignored */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#080E18] border border-emerald-500/20 space-y-1">
                  <span className="text-[10px] text-emerald-400 uppercase font-bold block">Expected Benefit:</span>
                  <p className="text-slate-200 font-sans text-xs">{itv.expectedBenefit}</p>
                </div>
                <div className="p-3 bg-[#080E18] border border-rose-500/20 space-y-1">
                  <span className="text-[10px] text-rose-400 uppercase font-bold block">Risk If Ignored:</span>
                  <p className="text-slate-200 font-sans text-xs">{itv.riskIfIgnored}</p>
                </div>
              </div>

              {/* Details Footer */}
              <div className="p-3 bg-[#080E18] border border-white/5 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs font-mono">
                <span className="text-slate-300"><strong className="text-slate-500">Evidence:</strong> {itv.evidenceMetrics}</span>
                <span className="text-[10px] text-slate-500">Effort: <strong>{itv.effortLevel}</strong> | Evaluated: {new Date(itv.evaluatedAt).toLocaleTimeString()}</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Actions Section */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Recommended Intervention Directives</h2>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {summary?.recommendedInterventionActions?.map((act) => (
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
          Advisory Governance & Non-Execution Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Financial Intervention engine synthesizes cross-engine data to recommend prioritized business steps. All recommendations are strictly advisory; acknowledging or completing an intervention does not move funds, modify bank accounts, or execute financial transactions automatically.
        </p>
      </section>
    </div>
  );
}
