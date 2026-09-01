'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantPlanOutcomeSummary,
  fetchMerchantPlanOptimizationFactors,
  evaluatePlanOutcome,
  BackendFinancialPlanOutcomeSummaryDTO,
  BackendPlanOptimizationDTO,
  BackendFinancialPlanOutcomeDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  ChartNoAxesCombined,
  AlertTriangle,
  TrendingUp,
  RefreshCw,
  Info,
  ShieldCheck,
  Activity,
  CheckCircle2,
  HelpCircle,
  BarChart3,
  Layers,
  ArrowRight,
  Zap,
} from 'lucide-react';

export default function FinancialPlanOutcomePage() {
  const [summary, setSummary] = useState<BackendFinancialPlanOutcomeSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedHorizon, setSelectedHorizon] = useState<string>('30D');

  const loadData = async (horizon: string = selectedHorizon) => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantPlanOutcomeSummary(1, horizon);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Financial Plan Outcome API');
    } finally {
      setLoading(false);
    }
  };

  const handleEvaluateOutcome = async (planId: number) => {
    setLoading(true);
    try {
      await evaluatePlanOutcome(1, planId, selectedHorizon);
      await loadData(selectedHorizon);
    } catch (err: any) {
      alert('Outcome evaluation failed: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleHorizonChange = (h: string) => {
    setSelectedHorizon(h);
    loadData(h);
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
              PLAN OUTCOME ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Financial Plan Outcome API (`http://localhost:8080/api/v1/merchants/1/financial-plan-outcomes`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={() => loadData()} className="gap-2 mx-auto">
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
              Financial Plan Outcome & Optimization
            </h1>
            <Badge variant="demo">OUTCOME ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Measures expected vs observed plan outcomes across 7D/30D/60D/90D and calibrates bounded optimization multipliers (0.900–1.100x) for future plan synthesis
          </p>
        </div>

        <Button variant="outline" onClick={() => loadData()} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Refresh Outcome Metrics
        </Button>
      </div>

      {/* Main Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Avg Plan Effectiveness</span>
              <span className="text-3xl font-black text-[#00F0FF]">{summary.averageEffectivenessScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Evaluated Plan Outcomes</span>
              <span className="text-3xl font-bold text-white">{summary.totalEvaluatedOutcomesCount} <span className="text-xs text-slate-400 font-normal">Plans</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Outcome Breakdown</span>
              <div className="flex items-center gap-1.5 pt-1">
                <Badge variant="emerald" className="text-[10px]">{summary.successfulCount} Successful</Badge>
                <Badge variant="amber" className="text-[10px]">{summary.partialCount} Partial</Badge>
                <Badge variant="rose" className="text-[10px]">{summary.ineffectiveCount} Ineffective</Badge>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Evaluation Window</span>
              <span className="text-sm font-bold text-emerald-400 block pt-1">{selectedHorizon} Post-Plan Horizon</span>
            </div>
          </div>

          <div className="space-y-2 pt-1">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Outcome Summary: </strong>
              {summary.summaryExplanation}
            </p>
          </div>
        </Card>
      )}

      {/* Horizon Window Selector */}
      <div className="flex items-center gap-2 font-mono text-xs">
        <span className="text-slate-400 font-bold uppercase text-[10px]">Evaluation Window:</span>
        {['30D', '7D', '60D', '90D'].map((h) => (
          <button
            key={h}
            onClick={() => handleHorizonChange(h)}
            className={`px-3 py-1.5 border transition-colors ${
              selectedHorizon === h
                ? 'bg-[#00F0FF]/10 border-[#00F0FF] text-[#00F0FF] font-bold'
                : 'bg-[#05080E] border-white/10 text-slate-400 hover:text-white'
            }`}
          >
            {h} WINDOW
          </button>
        ))}
      </div>

      {/* Observed Outcomes Timeline Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <ChartNoAxesCombined className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Observed Plan Outcomes (Expected vs Actual)</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {summary?.outcomes?.map((o) => (
            <Card key={o.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge variant={o.outcomeStatus === 'SUCCESSFUL' ? 'emerald' : o.outcomeStatus === 'PARTIAL' ? 'amber' : 'rose'} className="text-[10px] font-bold">
                      {o.outcomeStatus}
                    </Badge>
                    <Badge variant="demo" className="text-[10px]">PLAN #{o.planId}</Badge>
                    <Badge variant="cyan" className="text-[10px]">{o.horizon} HORIZON</Badge>
                    <Badge variant="neutral" className="text-[10px] text-slate-400">OBSERVED_PLAN_OUTCOME</Badge>
                  </div>
                  <h3 className="text-base font-bold text-white uppercase pt-1">30-Day Synthesis Financial Plan Outcome</h3>
                </div>

                <div className="p-3 bg-[#080E18] border border-[#00F0FF]/30 text-right">
                  <span className="text-[10px] text-slate-500 uppercase block font-bold">Effectiveness Score</span>
                  <span className="text-2xl font-black text-[#00F0FF]">{o.effectivenessScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
                </div>
              </div>

              {/* Expected vs Actual Metric Cards */}
              <div className="grid grid-cols-1 sm:grid-cols-4 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Plan Score Impact</span>
                  <div className="flex items-center justify-between pt-1">
                    <span className="text-slate-400">Exp: {o.expectedScore}</span>
                    <span className="text-emerald-400 font-bold">Act: {o.actualScore}</span>
                  </div>
                  <span className="text-[10px] text-cyan-300 block text-right font-bold">Var: +{o.scoreVariancePct}%</span>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Cash Impact</span>
                  <div className="flex items-center justify-between pt-1">
                    <span className="text-slate-400">Exp: ₹{o.expectedCashImpact}</span>
                    <span className="text-emerald-400 font-bold">Act: ₹{o.actualCashImpact}</span>
                  </div>
                  <span className="text-[10px] text-cyan-300 block text-right font-bold">Var: +{o.cashVariancePct}%</span>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Risk Reduction</span>
                  <div className="flex items-center justify-between pt-1">
                    <span className="text-slate-400">Exp: -{o.riskReductionExpected}%</span>
                    <span className="text-emerald-400 font-bold">Act: -{o.riskReductionActual}%</span>
                  </div>
                  <span className="text-[10px] text-emerald-400 block text-right font-bold">Exceeded Target</span>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Goal Progress</span>
                  <div className="flex items-center justify-between pt-1">
                    <span className="text-slate-400">Exp: +{o.goalProgressExpected}%</span>
                    <span className="text-emerald-400 font-bold">Act: +{o.goalProgressActual}%</span>
                  </div>
                  <span className="text-[10px] text-emerald-400 block text-right font-bold">Pace Met</span>
                </div>
              </div>

              {/* Evidence Metrics Footer */}
              <div className="p-3 bg-[#080E18] border border-white/5 flex items-center justify-between text-xs font-mono">
                <span className="text-slate-300"><strong className="text-slate-500">Evidence:</strong> {o.evidenceMetrics}</span>
                <span className="text-[10px] text-slate-500">{o.evaluatedAt}</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Adaptive Optimization Multiplier Matrix */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Zap className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Adaptive Optimization Multiplier Matrix</h2>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
          {summary?.optimizationFactors?.map((f) => (
            <Card key={f.id} className="p-5 space-y-3 border-white/10 bg-[#05080E]/60">
              <div className="flex items-center justify-between border-b border-white/10 pb-2">
                <span className="text-xs font-bold text-white uppercase">{f.planContext} PLAN CONTEXT</span>
                <Badge variant="emerald" className="text-[10px] font-bold">{f.confidenceStatus}</Badge>
              </div>

              <div className="flex items-center justify-between pt-1">
                <span className="text-xs text-slate-400 font-mono">Learned Multiplier</span>
                <span className="text-2xl font-black text-[#00F0FF]">{f.optimizationMultiplier}x</span>
              </div>

              <div className="grid grid-cols-2 gap-2 text-[10px] font-mono text-slate-300 pt-1">
                <div>Sample Count: <strong className="text-white">{f.sampleCount}</strong></div>
                <div>Avg Score: <strong className="text-emerald-400">{f.effectivenessScore}/100</strong></div>
              </div>

              <p className="text-[10px] text-slate-400 font-sans leading-relaxed pt-1">
                Learned multiplier adjusts future plan synthesis ranking without rewriting historical outcome scores. Bounded strictly to 0.900–1.100x.
              </p>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Governance Disclaimer */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Outcome Governance Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Financial Plan Outcome Engine compares expected vs observed metrics over completed post-plan horizons. All evaluations are strictly read-only and labeled OBSERVED_PLAN_OUTCOME; Flowwise never executes payments, modifies accounts, or alters historical ledger state automatically.
        </p>
      </section>
    </div>
  );
}
