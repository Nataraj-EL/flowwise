'use client';

import React, { useEffect, useState } from 'react';
import {
  GitBranch,
  ShieldCheck,
  Zap,
  TrendingUp,
  AlertTriangle,
  CheckCircle2,
  XCircle,
  HelpCircle,
  RefreshCw,
  Info,
  ArrowRight,
  ChevronRight,
  Layers,
  Award,
  BookOpen,
} from 'lucide-react';
import {
  fetchFinancialDecisions,
  evaluateFinancialDecisions,
  acknowledgeFinancialDecision,
  completeFinancialDecision,
  dismissFinancialDecision,
  BackendFinancialDecisionSummaryDTO,
  BackendFinancialDecisionDTO,
  BackendFinancialDecisionOptionDTO,
} from '@/lib/api';

export default function FinancialDecisionsPage() {
  const [merchantId] = useState<number>(1);
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [summary, setSummary] = useState<BackendFinancialDecisionSummaryDTO | null>(null);
  const [selectedDecision, setSelectedDecision] = useState<BackendFinancialDecisionDTO | null>(null);
  const [actionNotice, setActionNotice] = useState<string | null>(null);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchFinancialDecisions(merchantId);
      setSummary(data);
      if (data.topRecommendation) {
        setSelectedDecision(data.topRecommendation);
      } else if (data.decisions && data.decisions.length > 0) {
        setSelectedDecision(data.decisions[0]);
      }
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load financial decision intelligence');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [merchantId]);

  const handleEvaluate = async () => {
    try {
      setEvaluating(true);
      setActionNotice(null);
      const updated = await evaluateFinancialDecisions(merchantId);
      setSummary(updated);
      if (updated.topRecommendation) {
        setSelectedDecision(updated.topRecommendation);
      }
      setActionNotice('Evaluated decision intelligence models and updated ranked recommendations.');
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to evaluate decisions');
    } finally {
      setEvaluating(false);
    }
  };

  const handleAcknowledge = async (id: number) => {
    try {
      setActionNotice(null);
      const updated = await acknowledgeFinancialDecision(merchantId, id);
      setSelectedDecision(updated);
      setActionNotice(`Decision #${id} acknowledged.`);
      loadData();
    } catch (err: any) {
      setError(err.message || 'Failed to acknowledge decision');
    }
  };

  const handleComplete = async (id: number) => {
    try {
      setActionNotice(null);
      const updated = await completeFinancialDecision(merchantId, id);
      setSelectedDecision(updated);
      setActionNotice(`Decision #${id} marked completed.`);
      loadData();
    } catch (err: any) {
      setError(err.message || 'Failed to complete decision');
    }
  };

  const handleDismiss = async (id: number) => {
    try {
      setActionNotice(null);
      const updated = await dismissFinancialDecision(merchantId, id);
      setSelectedDecision(updated);
      setActionNotice(`Decision #${id} dismissed.`);
      loadData();
    } catch (err: any) {
      setError(err.message || 'Failed to dismiss decision');
    }
  };

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'RECOMMENDED':
        return 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30';
      case 'ACKNOWLEDGED':
        return 'bg-blue-500/10 text-blue-400 border-blue-500/30';
      case 'COMPLETED':
        return 'bg-purple-500/10 text-purple-400 border-purple-500/30';
      case 'DISMISSED':
        return 'bg-slate-500/10 text-slate-400 border-slate-500/30';
      default:
        return 'bg-amber-500/10 text-amber-400 border-amber-500/30';
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-8 flex items-center justify-center">
        <div className="flex items-center gap-3 text-emerald-400">
          <RefreshCw className="w-6 h-6 animate-spin" />
          <span className="font-medium text-lg">Synthesizing Decision Intelligence...</span>
        </div>
      </div>
    );
  }

  const topRec = summary?.topRecommendation;

  return (
    <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-6 md:p-8 space-y-8 font-sans">
      {/* Top Banner & Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-6">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-2.5 bg-emerald-500/10 border border-emerald-500/20 rounded-xl text-emerald-400">
              <GitBranch className="w-7 h-7" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-white">
                  Decision Intelligence & Recommendation Selection
                </h1>
                <span className="px-2.5 py-0.5 text-xs font-semibold bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full">
                  ADVISORY_RECOMMENDATION
                </span>
              </div>
              <p className="text-slate-400 text-sm mt-1">
                Synthesizes risks, anomalies, correlations, interventions, outcomes, strategy learning & scenario simulations into ranked advisory decisions.
              </p>
            </div>
          </div>
        </div>

        <div className="flex items-center gap-3">
          <button
            onClick={handleEvaluate}
            disabled={evaluating}
            className="flex items-center gap-2 px-4 py-2.5 bg-emerald-600 hover:bg-emerald-500 text-white rounded-lg text-sm font-medium transition-colors shadow-lg shadow-emerald-950/40 disabled:opacity-50"
          >
            <RefreshCw className={`w-4 h-4 ${evaluating ? 'animate-spin' : ''}`} />
            Evaluate Options
          </button>
        </div>
      </div>

      {/* Advisory Notice Banner */}
      <div className="bg-emerald-950/20 border border-emerald-500/30 rounded-xl p-4 flex items-start gap-3 text-emerald-300 text-sm">
        <Info className="w-5 h-5 text-emerald-400 shrink-0 mt-0.5" />
        <div>
          <span className="font-semibold text-emerald-300">ADVISORY — NO AUTOMATIC EXECUTION:</span>{' '}
          All decision intelligence outputs are strictly read-only advisory recommendations. Flowwise never automatically executes payments, transfers, or account/ledger changes.
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

      {/* Hero Recommended Now Card */}
      {topRec && (
        <div className="bg-[#121622] border border-slate-800 rounded-2xl p-6 md:p-8 space-y-6 shadow-2xl relative overflow-hidden">
          <div className="absolute top-0 right-0 w-64 h-64 bg-emerald-500/5 rounded-full blur-3xl -mr-20 -mt-20 pointer-events-none" />

          <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-6">
            <div className="space-y-2">
              <div className="flex items-center gap-3">
                <span className="px-3 py-1 bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 rounded-full text-xs font-semibold tracking-wide flex items-center gap-1.5">
                  <Award className="w-3.5 h-3.5" /> RECOMMENDED NOW #1
                </span>
                <span className={`px-2.5 py-0.5 text-xs font-medium border rounded-full ${getStatusBadge(topRec.status)}`}>
                  {topRec.status}
                </span>
              </div>
              <h2 className="text-2xl md:text-3xl font-bold text-white tracking-tight">{topRec.title}</h2>
              <p className="text-slate-300 text-sm max-w-3xl leading-relaxed">{topRec.recommendation}</p>
            </div>

            <div className="flex flex-col items-center justify-center p-5 bg-[#0A0D14] border border-slate-800 rounded-xl text-center shrink-0 min-w-[180px]">
              <span className="text-xs font-medium text-slate-400 uppercase tracking-wider">Composite Score</span>
              <span className="text-4xl font-extrabold text-emerald-400 mt-1">{topRec.decisionScore?.toFixed(2)}</span>
              <span className="text-[11px] text-slate-500 mt-1">Bounded 0–100 Formula</span>
            </div>
          </div>

          {/* Decision Scorecard */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 pt-2">
            <div className="bg-[#0A0D14] border border-slate-800 p-4 rounded-xl">
              <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
                <span>Risk Protection</span>
                <ShieldCheck className="w-4 h-4 text-emerald-400" />
              </div>
              <div className="text-xl font-bold text-slate-100 mt-1">{topRec.riskScore?.toFixed(2)} / 100</div>
              <div className="text-[11px] text-slate-500 mt-1">30% Weight Priority</div>
            </div>

            <div className="bg-[#0A0D14] border border-slate-800 p-4 rounded-xl">
              <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
                <span>Financial Impact</span>
                <TrendingUp className="w-4 h-4 text-blue-400" />
              </div>
              <div className="text-xl font-bold text-slate-100 mt-1">{topRec.impactScore?.toFixed(2)} / 100</div>
              <div className="text-[11px] text-slate-500 mt-1">25% Weight Impact</div>
            </div>

            <div className="bg-[#0A0D14] border border-slate-800 p-4 rounded-xl">
              <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
                <span>Urgency</span>
                <Zap className="w-4 h-4 text-amber-400" />
              </div>
              <div className="text-xl font-bold text-slate-100 mt-1">{topRec.urgencyScore?.toFixed(2)} / 100</div>
              <div className="text-[11px] text-slate-500 mt-1">20% Weight Urgency</div>
            </div>

            <div className="bg-[#0A0D14] border border-slate-800 p-4 rounded-xl">
              <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
                <span>Confidence</span>
                <CheckCircle2 className="w-4 h-4 text-purple-400" />
              </div>
              <div className="text-xl font-bold text-slate-100 mt-1">{topRec.confidenceScore?.toFixed(2)} / 100</div>
              <div className="text-[11px] text-slate-500 mt-1">{topRec.confidenceStatus} Confidence</div>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex flex-wrap items-center gap-3 pt-2 border-t border-slate-800/80">
            {topRec.status === 'RECOMMENDED' && (
              <button
                onClick={() => handleAcknowledge(topRec.id)}
                className="px-4 py-2 bg-blue-600 hover:bg-blue-500 text-white rounded-lg text-sm font-medium transition-colors"
              >
                Acknowledge Recommendation
              </button>
            )}
            {topRec.status !== 'COMPLETED' && (
              <button
                onClick={() => handleComplete(topRec.id)}
                className="px-4 py-2 bg-purple-600 hover:bg-purple-500 text-white rounded-lg text-sm font-medium transition-colors"
              >
                Mark Completed
              </button>
            )}
            {topRec.status !== 'DISMISSED' && (
              <button
                onClick={() => handleDismiss(topRec.id)}
                className="px-4 py-2 bg-slate-800 hover:bg-slate-700 text-slate-300 rounded-lg text-sm font-medium transition-colors"
              >
                Dismiss
              </button>
            )}
          </div>
        </div>
      )}

      {/* Main Grid: Options Matrix & Tradeoffs */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Options Ranking Matrix (2 cols) */}
        <div className="lg:col-span-2 space-y-4">
          <h3 className="text-lg font-semibold text-white flex items-center gap-2">
            <Layers className="w-5 h-5 text-emerald-400" />
            Ranked Decision Options Matrix
          </h3>

          <div className="space-y-4">
            {topRec?.options?.map((opt: BackendFinancialDecisionOptionDTO, index: number) => (
              <div
                key={opt.id || index}
                className="bg-[#121622] border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition-all space-y-4"
              >
                <div className="flex items-center justify-between gap-4">
                  <div className="flex items-center gap-3">
                    <span className="w-7 h-7 rounded-lg bg-emerald-500/10 text-emerald-400 font-bold text-xs flex items-center justify-center border border-emerald-500/20">
                      #{opt.rankOrder || index + 1}
                    </span>
                    <div>
                      <h4 className="font-semibold text-slate-100 text-base">{opt.optionKey}</h4>
                      <span className="text-xs text-slate-400 font-mono">{opt.optionType}</span>
                    </div>
                  </div>
                  <div className="text-right">
                    <span className="text-lg font-bold text-emerald-400">{opt.optionScore?.toFixed(2)}</span>
                    <span className="text-xs text-slate-500 block">Option Score</span>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs">
                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="font-semibold text-emerald-400 block mb-1">Expected Benefit</span>
                    <span className="text-slate-300">{opt.expectedBenefit}</span>
                  </div>
                  <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    <span className="font-semibold text-rose-400 block mb-1">Risk If Ignored</span>
                    <span className="text-slate-300">{opt.riskIfIgnored}</span>
                  </div>
                </div>

                <div className="text-[11px] text-slate-400 font-mono bg-[#0A0D14] p-2.5 rounded-lg border border-slate-800/60">
                  {opt.evidenceMetrics}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Expected Benefit vs Risk-if-Ignored & Trade-offs (1 col) */}
        <div className="space-y-6">
          {/* Expected Benefit vs Risk-if-Ignored Card */}
          <div className="bg-[#121622] border border-slate-800 rounded-xl p-6 space-y-4">
            <h3 className="text-base font-semibold text-white flex items-center gap-2 border-b border-slate-800 pb-3">
              <ShieldCheck className="w-5 h-5 text-emerald-400" />
              Benefit vs Risk Analysis
            </h3>

            {topRec && (
              <div className="space-y-4 text-xs">
                <div>
                  <span className="text-slate-400 font-medium block mb-1">Top Recommendation Expected Benefit</span>
                  <p className="text-slate-200 leading-relaxed bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    {topRec.expectedBenefit}
                  </p>
                </div>

                <div>
                  <span className="text-slate-400 font-medium block mb-1">Risk If Ignored</span>
                  <p className="text-rose-300 leading-relaxed bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    {topRec.riskIfIgnored}
                  </p>
                </div>
              </div>
            )}
          </div>

          {/* Trade-offs Card */}
          <div className="bg-[#121622] border border-slate-800 rounded-xl p-6 space-y-4">
            <h3 className="text-base font-semibold text-white flex items-center gap-2 border-b border-slate-800 pb-3">
              <BookOpen className="w-5 h-5 text-purple-400" />
              Trade-offs & Assumptions
            </h3>

            {topRec && (
              <div className="space-y-4 text-xs">
                <div>
                  <span className="text-slate-400 font-medium block mb-1">Trade-offs Analysis</span>
                  <p className="text-slate-300 leading-relaxed bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80">
                    {topRec.tradeoffs}
                  </p>
                </div>

                <div>
                  <span className="text-slate-400 font-medium block mb-1">Assumptions & Evidence</span>
                  <p className="text-slate-300 leading-relaxed bg-[#0A0D14] p-3 rounded-lg border border-slate-800/80 font-mono text-[11px]">
                    {topRec.assumptions}
                  </p>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
