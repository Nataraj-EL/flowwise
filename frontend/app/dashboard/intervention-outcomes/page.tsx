'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantInterventionOutcomes,
  evaluateInterventionOutcome,
  BackendInterventionEffectivenessSummaryDTO,
  BackendInterventionOutcomeDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Target,
  AlertTriangle,
  TrendingUp,
  RefreshCw,
  Info,
  ShieldCheck,
  Activity,
  CheckCircle2,
  HelpCircle,
  BarChart3,
} from 'lucide-react';

export default function InterventionOutcomesPage() {
  const [summary, setSummary] = useState<BackendInterventionEffectivenessSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedWindow, setSelectedWindow] = useState<string>('30D');

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantInterventionOutcomes(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Outcome & Effectiveness API');
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
              OUTCOME ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Intervention Outcome API (`http://localhost:8080/api/v1/merchants/1/intervention-outcomes`).
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
              Intervention Outcome & Effectiveness
            </h1>
            <Badge variant="demo">EFFECTIVENESS CONSOLE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic post-completion outcome measurement comparing EXPECTED vs ACTUAL financial impacts
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Effectiveness Engine
        </Button>
      </div>

      {/* Main Outcome Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Total Evaluated Outcomes</span>
              <div className="flex items-baseline gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{summary.totalEvaluatedOutcomesCount}</span>
                <span className="text-xs text-slate-400">Outcomes</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Outcome Classification</span>
              <div className="flex items-center gap-2 font-bold text-sm pt-1">
                <span className="text-emerald-400">{summary.successfulCount} Successful</span>
                <span className="text-slate-500">|</span>
                <span className="text-amber-400">{summary.partialCount} Partial</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Ineffective / Insufficient Data</span>
              <div className="flex items-center gap-2 font-bold text-sm pt-1">
                <span className="text-rose-400">{summary.ineffectiveCount} Ineffective</span>
                <span className="text-slate-500">|</span>
                <span className="text-slate-400">{summary.insufficientDataCount} Insufficient</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Average Effectiveness Score</span>
              <span className="text-3xl font-bold text-[#00F0FF]">{summary.averageEffectivenessScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
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

      {/* Evaluation Window Selector */}
      <div className="flex items-center gap-2 font-mono text-xs">
        <span className="text-slate-400 font-bold uppercase text-[10px]">Evaluation Window:</span>
        {['30D', '7D', '60D', '90D'].map((win) => (
          <button
            key={win}
            onClick={() => setSelectedWindow(win)}
            className={`px-3 py-1.5 border transition-colors ${
              selectedWindow === win
                ? 'bg-[#00F0FF]/10 border-[#00F0FF] text-[#00F0FF] font-bold'
                : 'bg-[#05080E] border-white/10 text-slate-400 hover:text-white'
            }`}
          >
            {win} POST-COMPLETION
          </button>
        ))}
      </div>

      {/* Outcomes Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Target className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Evaluated Intervention Outcomes</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {summary?.outcomes?.map((otc) => (
            <Card key={otc.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge
                      variant={otc.outcomeStatus === 'SUCCESSFUL' ? 'emerald' : otc.outcomeStatus === 'PARTIAL' ? 'amber' : 'rose'}
                      className="text-[10px] font-bold"
                    >
                      {otc.outcomeStatus}
                    </Badge>
                    <Badge variant="cyan" className="text-[10px]">{otc.interventionType}</Badge>
                    <Badge variant="demo" className="text-[10px]">{otc.evaluationWindow} WINDOW</Badge>
                  </div>
                  <h3 className="text-base font-bold text-white uppercase pt-1">Interment Target #{otc.interventionId}</h3>
                  <p className="text-xs text-emerald-400 font-bold font-mono">{otc.actualBenefit}</p>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  <div className="p-3 bg-[#080E18] border border-[#00F0FF]/30 text-right">
                    <span className="text-[10px] text-slate-500 uppercase block font-bold">Effectiveness Score</span>
                    <span className="text-2xl font-black text-[#00F0FF]">{otc.effectivenessScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
                  </div>
                </div>
              </div>

              {/* Expected vs Actual Comparison Matrix */}
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Expected vs Actual Cash Impact</span>
                  <div className="flex justify-between text-slate-300">
                    <span>Expected: ₹{otc.expectedCashImpact.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="flex justify-between text-white font-bold">
                    <span>Actual: ₹{otc.actualCashImpact.toLocaleString('en-IN')}</span>
                    <span className="text-emerald-400">Var: {otc.cashImpactVariancePct}%</span>
                  </div>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Expected vs Actual Risk Reduction</span>
                  <div className="flex justify-between text-slate-300">
                    <span>Expected: {otc.expectedRiskReduction}</span>
                  </div>
                  <div className="flex justify-between text-white font-bold">
                    <span>Actual: {otc.actualRiskReduction}</span>
                    <span className="text-emerald-400">+5.00 pts</span>
                  </div>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Goal Progress Variance</span>
                  <div className="flex justify-between text-slate-300">
                    <span>Goal Pace Impact:</span>
                  </div>
                  <div className="flex justify-between text-white font-bold">
                    <span>Target Progress:</span>
                    <span className="text-cyan-300">+{otc.goalImpactVariancePct}% Pace</span>
                  </div>
                </div>
              </div>

              {/* Details Footer */}
              <div className="p-3 bg-[#080E18] border border-white/5 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs font-mono">
                <span className="text-slate-300"><strong className="text-slate-500">Evidence:</strong> {otc.evidenceMetrics}</span>
                <span className="text-[10px] text-slate-500">Evaluated: {new Date(otc.evaluatedAt).toLocaleTimeString()} | OBSERVED_OUTCOME</span>
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
          Flowwise Intervention Outcome engine measures observed post-completion financial metrics against expectations. All results are strictly labeled OBSERVED_OUTCOME to distinguish observed financial trends from absolute causality; evaluating outcomes does not move funds, modify bank accounts, or execute financial transactions automatically.
        </p>
      </section>
    </div>
  );
}
