'use client';

import React, { useEffect, useState } from 'react';
import {
  ChartNoAxesColumnIncreasing,
  CheckCircle2,
  AlertTriangle,
  XCircle,
  HelpCircle,
  RefreshCw,
  Info,
  TrendingUp,
  ShieldCheck,
  Award,
  Layers,
  BrainCircuit,
  Calendar,
} from 'lucide-react';
import {
  fetchFinancialDecisionOutcomeSummary,
  evaluateDecisionOutcome,
  BackendFinancialDecisionOutcomeSummaryDTO,
  BackendFinancialDecisionOutcomeDTO,
  BackendDecisionLearningDTO,
} from '@/lib/api';

export default function FinancialDecisionOutcomesPage() {
  const [merchantId] = useState<number>(1);
  const [window, setWindow] = useState<string>('30D');
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [summary, setSummary] = useState<BackendFinancialDecisionOutcomeSummaryDTO | null>(null);
  const [actionNotice, setActionNotice] = useState<string | null>(null);

  const loadData = async (selectedWindow: string) => {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchFinancialDecisionOutcomeSummary(merchantId, selectedWindow);
      setSummary(data);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load decision outcome summary');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData(window);
  }, [merchantId, window]);

  const handleWindowChange = (w: string) => {
    setWindow(w);
  };

  const handleEvaluate = async (decisionId: number) => {
    try {
      setEvaluating(true);
      setActionNotice(null);
      await evaluateDecisionOutcome(merchantId, decisionId, window);
      setActionNotice(`Evaluated financial decision outcome for decision #${decisionId} (${window} window).`);
      loadData(window);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to evaluate decision outcome');
    } finally {
      setEvaluating(false);
    }
  };

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'SUCCESSFUL':
        return 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30';
      case 'PARTIAL':
        return 'bg-amber-500/10 text-amber-400 border-amber-500/30';
      case 'INEFFECTIVE':
        return 'bg-rose-500/10 text-rose-400 border-rose-500/30';
      default:
        return 'bg-slate-500/10 text-slate-400 border-slate-500/30';
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-8 flex items-center justify-center">
        <div className="flex items-center gap-3 text-emerald-400">
          <RefreshCw className="w-6 h-6 animate-spin" />
          <span className="font-medium text-lg">Measuring Decision Outcomes & Learning...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-6 md:p-8 space-y-8 font-sans">
      {/* Top Banner & Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-6">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-2.5 bg-emerald-500/10 border border-emerald-500/20 rounded-xl text-emerald-400">
              <ChartNoAxesColumnIncreasing className="w-7 h-7" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-white">
                  Decision Outcomes & Effectiveness Learning
                </h1>
                <span className="px-2.5 py-0.5 text-xs font-semibold bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full">
                  OBSERVED_DECISION_OUTCOME
                </span>
              </div>
              <p className="text-slate-400 text-sm mt-1">
                Measures actual vs expected score, cash recovery & risk reduction for completed decisions to generate bounded learning multipliers.
              </p>
            </div>
          </div>
        </div>

        {/* Evaluation Window Selector */}
        <div className="flex items-center gap-2 bg-[#121622] p-1.5 border border-slate-800 rounded-xl">
          {['7D', '30D', '60D', '90D'].map((w) => (
            <button
              key={w}
              onClick={() => handleWindowChange(w)}
              className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                window === w
                  ? 'bg-emerald-600 text-white shadow-md'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'
              }`}
            >
              {w}
            </button>
          ))}
        </div>
      </div>

      {/* Governance Notice Banner */}
      <div className="bg-emerald-950/20 border border-emerald-500/30 rounded-xl p-4 flex items-start gap-3 text-emerald-300 text-sm">
        <Info className="w-5 h-5 text-emerald-400 shrink-0 mt-0.5" />
        <div>
          <span className="font-semibold text-emerald-300">OBSERVED OUTCOME — NOT SIMULATED:</span>{' '}
          Outcome measurements are strictly read-only, immutable historical records (`OBSERVED_DECISION_OUTCOME`). Simulated what-if estimates are never treated as actual outcomes.
        </div>
      </div>

      {actionNotice && (
        <div className="bg-blue-950/30 border border-blue-500/30 rounded-xl p-4 flex items-center justify-between text-blue-300 text-sm">
          <div className="flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-blue-400 shrink-0" />
            <span>{actionNotice}</span>
          </div>
          <button onClick={() => setActionNotice(null)} className="text-blue-400 hover:text-blue-200">
            Dismiss
          </button>
        </div>
      )}

      {error && (
        <div className="bg-rose-950/30 border border-rose-500/30 rounded-xl p-4 flex items-center gap-3 text-rose-300 text-sm">
          <AlertTriangle className="w-5 h-5 text-rose-400 shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {/* Effectiveness Scorecard */}
      <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl col-span-2 md:col-span-1 flex flex-col justify-between">
          <div className="text-xs font-medium text-slate-400">Avg Effectiveness</div>
          <div className="text-3xl font-extrabold text-emerald-400 my-1">
            {summary?.averageEffectivenessScore?.toFixed(2) || '0.00'} / 100
          </div>
          <div className="text-[11px] text-slate-500">Across {summary?.totalEvaluatedOutcomesCount || 0} Outcomes ({window})</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Successful</span>
            <CheckCircle2 className="w-4 h-4 text-emerald-400" />
          </div>
          <div className="text-2xl font-bold text-emerald-400 mt-2">{summary?.successfulCount || 0}</div>
          <div className="text-[11px] text-slate-500">Score &ge; 80.00</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Partial</span>
            <AlertTriangle className="w-4 h-4 text-amber-400" />
          </div>
          <div className="text-2xl font-bold text-amber-400 mt-2">{summary?.partialCount || 0}</div>
          <div className="text-[11px] text-slate-500">Score 50.00–79.99</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Ineffective</span>
            <XCircle className="w-4 h-4 text-rose-400" />
          </div>
          <div className="text-2xl font-bold text-rose-400 mt-2">{summary?.ineffectiveCount || 0}</div>
          <div className="text-[11px] text-slate-500">Score &lt; 50.00</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Insufficient</span>
            <HelpCircle className="w-4 h-4 text-slate-500" />
          </div>
          <div className="text-2xl font-bold text-slate-400 mt-2">{summary?.insufficientDataCount || 0}</div>
          <div className="text-[11px] text-slate-500">Pending Window</div>
        </div>
      </div>

      {/* Main Grid: Outcomes Timeline & Learning Multiplier Matrix */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Evaluated Decision Outcomes Timeline (2 cols) */}
        <div className="lg:col-span-2 space-y-4">
          <h3 className="text-lg font-semibold text-white flex items-center gap-2">
            <Layers className="w-5 h-5 text-emerald-400" />
            Measured Decision Outcomes Timeline ({window})
          </h3>

          <div className="space-y-4">
            {summary?.outcomes?.map((o: BackendFinancialDecisionOutcomeDTO) => (
              <div
                key={o.id}
                className="bg-[#121622] border border-slate-800 rounded-xl p-6 hover:border-slate-700 transition-all space-y-4 shadow-lg"
              >
                <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-4">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2">
                      <span className={`px-2.5 py-0.5 text-xs font-semibold border rounded-full ${getStatusBadge(o.outcomeStatus)}`}>
                        {o.outcomeStatus}
                      </span>
                      <span className="px-2 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded-md">
                        {o.evaluationWindow} Window
                      </span>
                    </div>
                    <h4 className="font-bold text-white text-base">Decision #{o.decisionId} Evaluation</h4>
                  </div>

                  <div className="flex items-center gap-4 text-right">
                    <div>
                      <span className="text-xs text-slate-400 block">Effectiveness Score</span>
                      <span className="text-xl font-extrabold text-emerald-400">{o.effectivenessScore?.toFixed(2)}</span>
                    </div>
                    <button
                      onClick={() => handleEvaluate(o.decisionId)}
                      disabled={evaluating}
                      className="p-2 bg-slate-800 hover:bg-slate-700 text-slate-200 rounded-lg text-xs font-medium transition-colors"
                      title="Re-evaluate Outcome"
                    >
                      <RefreshCw className={`w-3.5 h-3.5 ${evaluating ? 'animate-spin' : ''}`} />
                    </button>
                  </div>
                </div>

                {/* Expected vs Actual Scorecard */}
                <div className="grid grid-cols-2 md:grid-cols-4 gap-3 text-xs">
                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="text-slate-400 block mb-1 font-medium">Composite Score</span>
                    <div className="font-bold text-slate-100">
                      Exp: {o.expectedScore?.toFixed(2)} | <span className="text-emerald-400">Act: {o.actualScore?.toFixed(2)}</span>
                    </div>
                    <span className="text-[10px] text-emerald-400 block mt-0.5">+{o.scoreVariancePct?.toFixed(2)}% Var</span>
                  </div>

                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="text-slate-400 block mb-1 font-medium">Cash Impact</span>
                    <div className="font-bold text-slate-100">
                      Exp: ₹{o.expectedCashImpact?.toLocaleString()} | <span className="text-emerald-400">Act: ₹{o.actualCashImpact?.toLocaleString()}</span>
                    </div>
                    <span className="text-[10px] text-emerald-400 block mt-0.5">+{o.cashVariancePct?.toFixed(2)}% Var</span>
                  </div>

                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="text-slate-400 block mb-1 font-medium">Risk Reduction</span>
                    <div className="font-bold text-slate-100">
                      Exp: {o.expectedRiskReduction?.toFixed(2)} | <span className="text-purple-400">Act: {o.actualRiskReduction?.toFixed(2)}</span>
                    </div>
                  </div>

                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="text-slate-400 block mb-1 font-medium">Goal Impact</span>
                    <div className="font-bold text-slate-100">
                      Exp: {o.expectedGoalImpact?.toFixed(2)} | <span className="text-blue-400">Act: {o.actualGoalImpact?.toFixed(2)}</span>
                    </div>
                  </div>
                </div>

                <div className="text-[11px] text-slate-300 font-mono bg-[#0A0D14] p-3 rounded-lg border border-slate-800/60 leading-relaxed">
                  {o.evidenceMetrics}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Learning Multiplier Matrix (1 col) */}
        <div className="space-y-6">
          <div className="bg-[#121622] border border-slate-800 rounded-xl p-6 space-y-4 shadow-xl">
            <h3 className="text-base font-semibold text-white flex items-center gap-2 border-b border-slate-800 pb-3">
              <BrainCircuit className="w-5 h-5 text-emerald-400" />
              Decision Learning Multipliers
            </h3>
            <p className="text-xs text-slate-400 leading-relaxed">
              Learned from historical observed decision outcomes. Applied to future decision composite scores strictly bounded to <span className="text-emerald-400 font-mono">[0.900, 1.100]</span>.
            </p>

            <div className="space-y-3 pt-2">
              {summary?.learnings?.map((l: BackendDecisionLearningDTO) => (
                <div key={l.id} className="bg-[#0A0D14] p-4 rounded-xl border border-slate-800 space-y-2">
                  <div className="flex items-center justify-between">
                    <span className="font-semibold text-slate-200 text-xs">{l.decisionType}</span>
                    <span className="text-sm font-extrabold text-emerald-400 font-mono">{l.learningMultiplier?.toFixed(3)}x</span>
                  </div>

                  <div className="flex items-center justify-between text-[11px] text-slate-400">
                    <span>Samples: <strong className="text-slate-200">{l.sampleCount}</strong></span>
                    <span>Confidence: <strong className="text-purple-400">{l.confidenceStatus}</strong></span>
                  </div>

                  <div className="text-[10px] text-slate-500 font-mono border-t border-slate-800/60 pt-1.5 mt-1">
                    {l.evidenceMetrics}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
