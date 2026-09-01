'use client';

import React, { useEffect, useState } from 'react';
import {
  Layers3,
  CheckCircle2,
  AlertTriangle,
  RefreshCw,
  Info,
  TrendingUp,
  ShieldCheck,
  Award,
  Zap,
  Target,
  FileText,
  Archive,
  PlayCircle,
  BrainCircuit,
} from 'lucide-react';
import {
  fetchFinancialDecisionPortfolioSummary,
  evaluateFinancialDecisionPortfolio,
  activateFinancialDecisionPortfolio,
  archiveFinancialDecisionPortfolio,
  BackendFinancialDecisionPortfolioSummaryDTO,
  BackendFinancialDecisionPortfolioDTO,
  BackendFinancialDecisionPortfolioItemDTO,
} from '@/lib/api';

export default function DecisionPortfolioPage() {
  const [merchantId] = useState<number>(1);
  const [horizon, setHorizon] = useState<string>('30D');
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [summary, setSummary] = useState<BackendFinancialDecisionPortfolioSummaryDTO | null>(null);
  const [actionNotice, setActionNotice] = useState<string | null>(null);

  const loadData = async (selectedHorizon: string) => {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchFinancialDecisionPortfolioSummary(merchantId, selectedHorizon);
      setSummary(data);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load decision portfolio summary');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData(horizon);
  }, [merchantId, horizon]);

  const handleHorizonChange = (h: string) => {
    setHorizon(h);
  };

  const handleEvaluatePortfolio = async () => {
    try {
      setEvaluating(true);
      setActionNotice(null);
      await evaluateFinancialDecisionPortfolio(merchantId, horizon);
      setActionNotice(`Re-evaluated decision portfolio for ${horizon} horizon.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to evaluate decision portfolio');
    } finally {
      setEvaluating(false);
    }
  };

  const handleActivate = async (portfolioId: number) => {
    try {
      setActionNotice(null);
      await activateFinancialDecisionPortfolio(merchantId, portfolioId);
      setActionNotice(`Activated decision portfolio #${portfolioId}.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to activate portfolio');
    }
  };

  const handleArchive = async (portfolioId: number) => {
    try {
      setActionNotice(null);
      await archiveFinancialDecisionPortfolio(merchantId, portfolioId);
      setActionNotice(`Archived decision portfolio #${portfolioId}.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to archive portfolio');
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-8 flex items-center justify-center">
        <div className="flex items-center gap-3 text-cyan-400">
          <RefreshCw className="w-6 h-6 animate-spin" />
          <span className="font-medium text-lg">Synthesizing Decision Portfolio & Optimization...</span>
        </div>
      </div>
    );
  }

  const activePortfolio: BackendFinancialDecisionPortfolioDTO | null = summary?.activePortfolio || null;
  const topItem: BackendFinancialDecisionPortfolioItemDTO | null = activePortfolio?.items && activePortfolio.items.length > 0 ? activePortfolio.items[0] : null;

  return (
    <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-6 md:p-8 space-y-8 font-sans">
      {/* Top Banner & Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-6">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-2.5 bg-cyan-500/10 border border-cyan-500/20 rounded-xl text-cyan-400">
              <Layers3 className="w-7 h-7" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-white">
                  Decision Portfolio & Optimization
                </h1>
                <span className="px-2.5 py-0.5 text-xs font-semibold bg-cyan-500/10 text-cyan-400 border border-cyan-500/30 rounded-full">
                  ADVISORY_PORTFOLIO
                </span>
              </div>
              <p className="text-slate-400 text-sm mt-1">
                Consolidates active decisions, plans, interventions, outcomes & learned strategy multipliers into a continuously ranked advisory portfolio.
              </p>
            </div>
          </div>
        </div>

        {/* Horizon Selector & Actions */}
        <div className="flex items-center gap-3">
          <div className="flex items-center gap-2 bg-[#121622] p-1.5 border border-slate-800 rounded-xl">
            {['7D', '30D', '60D', '90D'].map((h) => (
              <button
                key={h}
                onClick={() => handleHorizonChange(h)}
                className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                  horizon === h
                    ? 'bg-cyan-600 text-white shadow-md'
                    : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'
                }`}
              >
                {h}
              </button>
            ))}
          </div>

          <button
            onClick={handleEvaluatePortfolio}
            disabled={evaluating}
            className="flex items-center gap-2 bg-gradient-to-r from-cyan-600 to-emerald-600 hover:from-cyan-500 hover:to-emerald-500 text-white px-4 py-2 rounded-xl text-xs font-semibold shadow-lg shadow-cyan-900/30 transition-all"
          >
            <RefreshCw className={`w-4 h-4 ${evaluating ? 'animate-spin' : ''}`} />
            Synthesize Portfolio
          </button>
        </div>
      </div>

      {/* Governance Notice Banner */}
      <div className="bg-cyan-950/20 border border-cyan-500/30 rounded-xl p-4 flex items-start gap-3 text-cyan-300 text-sm">
        <Info className="w-5 h-5 text-cyan-400 shrink-0 mt-0.5" />
        <div>
          <span className="font-semibold text-cyan-300">ADVISORY ONLY — NO AUTOMATIC EXECUTION:</span>{' '}
          Portfolio rankings are strictly read-only recommendations (`ADVISORY_PORTFOLIO`). Flowwise never executes payments or modifies ledger/account state automatically.
        </div>
      </div>

      {actionNotice && (
        <div className="bg-emerald-950/30 border border-emerald-500/30 rounded-xl p-4 flex items-center justify-between text-emerald-300 text-sm">
          <div className="flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-emerald-400 shrink-0" />
            <span>{actionNotice}</span>
          </div>
          <button onClick={() => setActionNotice(null)} className="text-emerald-400 hover:text-emerald-200">
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

      {/* Focus Now Hero Recommendation Card */}
      {activePortfolio && topItem && (
        <div className="bg-gradient-to-br from-[#121622] via-[#0F1420] to-[#0A0D14] border border-cyan-500/30 rounded-2xl p-6 md:p-8 space-y-6 shadow-2xl relative overflow-hidden">
          <div className="absolute top-0 right-0 p-8 opacity-10 pointer-events-none">
            <Target className="w-48 h-48 text-cyan-400" />
          </div>

          <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-4">
            <div className="space-y-1">
              <div className="flex items-center gap-2">
                <span className="px-3 py-1 text-xs font-bold bg-cyan-500/20 text-cyan-300 border border-cyan-500/40 rounded-full flex items-center gap-1.5">
                  <Zap className="w-3.5 h-3.5" /> FOCUS NOW — RANK #1 PRIORITY
                </span>
                <span className="px-2.5 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded-md">
                  {activePortfolio.horizon} Horizon
                </span>
              </div>
              <h2 className="text-xl md:text-2xl font-extrabold text-white mt-2">
                {activePortfolio.primaryFocusArea}
              </h2>
            </div>

            <div className="flex items-center gap-3">
              <div className="text-right">
                <span className="text-xs text-slate-400 block">Overall Portfolio Score</span>
                <span className="text-3xl font-black text-cyan-400">{activePortfolio.overallPortfolioScore?.toFixed(2)} / 100</span>
              </div>

              {activePortfolio.status === 'ACTIVE' ? (
                <button
                  onClick={() => handleArchive(activePortfolio.id)}
                  className="p-2 bg-slate-800 hover:bg-slate-700 text-slate-300 rounded-xl text-xs flex items-center gap-1.5"
                  title="Archive Portfolio"
                >
                  <Archive className="w-4 h-4" /> Archive
                </button>
              ) : (
                <button
                  onClick={() => handleActivate(activePortfolio.id)}
                  className="p-2 bg-cyan-600 hover:bg-cyan-500 text-white rounded-xl text-xs font-medium flex items-center gap-1.5"
                  title="Activate Portfolio"
                >
                  <PlayCircle className="w-4 h-4" /> Activate
                </button>
              )}
            </div>
          </div>

          {/* 6-Factor Portfolio Breakdown */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Risk Protection (35%)</span>
              <span className="text-xl font-extrabold text-purple-400">{activePortfolio.riskScore?.toFixed(2)}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Financial Impact (25%)</span>
              <span className="text-xl font-extrabold text-emerald-400">{activePortfolio.impactScore?.toFixed(2)}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Urgency Score (15%)</span>
              <span className="text-xl font-extrabold text-amber-400">{activePortfolio.urgencyScore?.toFixed(2)}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Confidence Score (5%)</span>
              <span className="text-xl font-extrabold text-cyan-400">{activePortfolio.confidenceScore?.toFixed(2)}</span>
            </div>
          </div>
        </div>
      )}

      {/* Main Grid: Portfolio Items Timeline & Matrix */}
      <div className="space-y-6">
        <h3 className="text-lg font-semibold text-white flex items-center gap-2">
          <BrainCircuit className="w-5 h-5 text-cyan-400" />
          Ranked Decision Portfolio Items ({horizon})
        </h3>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {activePortfolio?.items?.map((item: BackendFinancialDecisionPortfolioItemDTO) => (
            <div
              key={item.id}
              className="bg-[#121622] border border-slate-800 rounded-xl p-6 hover:border-cyan-500/40 transition-all space-y-4 shadow-lg flex flex-col justify-between"
            >
              <div className="space-y-3">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <span className="w-6 h-6 rounded-full bg-cyan-500/20 border border-cyan-500/40 text-cyan-300 flex items-center justify-center text-xs font-bold font-mono">
                      #{item.rankOrder}
                    </span>
                    <span className="px-2.5 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded-md">
                      {item.decisionType}
                    </span>
                  </div>
                  <span className="text-lg font-black text-cyan-400">{item.priorityScore?.toFixed(2)} / 100</span>
                </div>

                <h4 className="font-bold text-white text-base">{item.title}</h4>
                <p className="text-xs text-slate-400 leading-relaxed">{item.description}</p>
              </div>

              <div className="space-y-3 border-t border-slate-800/80 pt-3">
                <div className="grid grid-cols-2 gap-2 text-[11px]">
                  <div className="bg-[#0A0D14] p-2.5 rounded-lg border border-slate-800/60">
                    <span className="text-slate-400 block font-medium">Expected Benefit</span>
                    <span className="text-emerald-400 font-semibold">{item.expectedBenefit}</span>
                  </div>
                  <div className="bg-[#0A0D14] p-2.5 rounded-lg border border-slate-800/60">
                    <span className="text-slate-400 block font-medium">Risk If Ignored</span>
                    <span className="text-rose-400 font-semibold">{item.riskIfIgnored}</span>
                  </div>
                </div>

                <div className="text-[10px] font-mono text-slate-400 bg-[#0A0D14] p-2.5 rounded-lg border border-slate-800/60 leading-relaxed">
                  {item.evidenceMetrics}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
