'use client';

import React, { useEffect, useState } from 'react';
import {
  GitCompareArrows,
  TrendingUp,
  ShieldCheck,
  Layers,
  AlertTriangle,
  CheckCircle2,
  Info,
  Clock,
  BrainCircuit,
  Sparkles,
  ArrowUpRight,
  BarChart3,
  Archive,
  Target,
  RefreshCw,
  Sliders,
} from 'lucide-react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  fetchMerchantScenarioSummary,
  evaluateScenario,
  archiveScenario,
  BackendFinancialScenarioSummaryDTO,
  BackendFinancialScenarioDTO,
} from '@/lib/api';

export default function FinancialScenariosPage() {
  const merchantId = 1;
  const [horizon, setHorizon] = useState<string>('30D');
  const [summary, setSummary] = useState<BackendFinancialScenarioSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [archivingId, setArchivingId] = useState<number | null>(null);
  const [selectedScenario, setSelectedScenario] = useState<BackendFinancialScenarioDTO | null>(null);
  const [error, setError] = useState<string | null>(null);

  const loadData = async (activeHorizon: string) => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantScenarioSummary(merchantId, activeHorizon);
      setSummary(data);
      if (data.topRankedScenario) {
        setSelectedScenario(data.topRankedScenario);
      }
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load scenario simulation data');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData(horizon);
  }, [horizon]);

  const handleEvaluate = async () => {
    setEvaluating(true);
    try {
      const data = await evaluateScenario(merchantId, horizon, 'Combined Receivables Acceleration & Inventory Expense Audit');
      setSummary(data);
      if (data.topRankedScenario) {
        setSelectedScenario(data.topRankedScenario);
      }
    } catch (err: any) {
      console.error(err);
      alert('Failed to evaluate simulation scenario: ' + err.message);
    } finally {
      setEvaluating(false);
    }
  };

  const handleArchive = async (scenarioId: number) => {
    setArchivingId(scenarioId);
    try {
      await archiveScenario(merchantId, scenarioId);
      await loadData(horizon);
    } catch (err: any) {
      console.error(err);
      alert('Failed to archive scenario: ' + err.message);
    } finally {
      setArchivingId(null);
    }
  };

  return (
    <div className="space-y-6 pb-12">
      {/* Header Banner */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 bg-slate-900/90 border border-slate-800 p-6 rounded-2xl backdrop-blur-xl">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <Badge variant="cyan" className="text-xs uppercase tracking-wider">
              SIMULATION ENGINE
            </Badge>
            <Badge variant="emerald" className="text-xs uppercase tracking-wider">
              SPRINT 36 WHAT-IF PLANNING
            </Badge>
          </div>
          <h1 className="text-2xl font-black text-white tracking-tight flex items-center gap-2 pt-1">
            <GitCompareArrows className="w-6 h-6 text-cyan-400" />
            Financial Scenario Simulation Console
          </h1>
          <p className="text-sm text-slate-400">
            Evaluate alternative intervention combinations deterministically before execution — compare baseline vs projected impact across 7D, 30D, 60D, and 90D horizons.
          </p>
        </div>

        <div className="flex items-center gap-3">
          {/* Horizon Selector */}
          <div className="flex items-center bg-slate-950 p-1 rounded-xl border border-slate-800">
            {['7D', '30D', '60D', '90D'].map((h) => (
              <button
                key={h}
                onClick={() => setHorizon(h)}
                className={`px-3 py-1.5 text-xs font-bold rounded-lg transition-all ${
                  horizon === h
                    ? 'bg-cyan-500 text-slate-950 shadow-md shadow-cyan-500/20'
                    : 'text-slate-400 hover:text-white'
                }`}
              >
                {h}
              </button>
            ))}
          </div>

          <Button
            onClick={handleEvaluate}
            disabled={evaluating}
            className="bg-cyan-500 hover:bg-cyan-400 text-slate-950 font-bold px-4 py-2 text-xs rounded-xl shadow-lg shadow-cyan-500/20 flex items-center gap-2"
          >
            <RefreshCw className={`w-3.5 h-3.5 ${evaluating ? 'animate-spin' : ''}`} />
            {evaluating ? 'Simulating...' : 'Run Simulation'}
          </Button>
        </div>
      </div>

      {/* Prominent Governance Banner */}
      <div className="bg-amber-500/10 border border-amber-500/30 p-4 rounded-xl flex items-start gap-3">
        <AlertTriangle className="w-5 h-5 text-amber-400 shrink-0 mt-0.5" />
        <div className="space-y-1">
          <h4 className="text-xs font-bold text-amber-300 uppercase tracking-wider">
            SIMULATED / ESTIMATED — ADVISORY GOVERNANCE NOTICE
          </h4>
          <p className="text-xs text-slate-300 leading-relaxed">
            All scenario projections are deterministic advisory estimates labeled <code className="bg-slate-950 px-1 py-0.5 rounded text-amber-300 font-mono">SIMULATED_ESTIMATE</code>. Flowwise never automatically executes payments or alters ledger state. Simulated outcomes are strictly isolated and never feed into historical outcome or strategy learning engines.
          </p>
        </div>
      </div>

      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-32 bg-slate-900 border border-slate-800 animate-pulse rounded-2xl" />
          ))}
        </div>
      ) : error ? (
        <div className="bg-rose-500/10 border border-rose-500/30 p-6 rounded-2xl text-center space-y-2">
          <AlertTriangle className="w-8 h-8 text-rose-400 mx-auto" />
          <h3 className="text-base font-bold text-rose-200">Failed to Load Scenario Simulation</h3>
          <p className="text-xs text-slate-400">{error}</p>
          <Button onClick={() => loadData(horizon)} variant="outline" className="mt-2 text-xs">
            Retry Loading
          </Button>
        </div>
      ) : summary ? (
        <>
          {/* Top-Ranked Scenario Banner & Metrics */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <Card className="bg-slate-900/80 border-slate-800/80 backdrop-blur-md space-y-3">
              <div className="flex items-center justify-between text-xs font-medium text-slate-400">
                <span>BASELINE SCORE</span>
                <BarChart3 className="w-4 h-4 text-slate-500" />
              </div>
              <div className="text-2xl font-black text-white">
                {summary.baselineScore?.toFixed(2)}
                <span className="text-xs font-normal text-slate-500 ml-1">/100</span>
              </div>
              <p className="text-[11px] text-slate-400">Current baseline health prior to intervention simulation</p>
            </Card>

            <Card className="bg-slate-900/80 border-slate-800/80 backdrop-blur-md border-l-4 border-l-cyan-500 space-y-3">
              <div className="flex items-center justify-between text-xs font-medium text-slate-400">
                <span>TOP PROJECTED SCORE</span>
                <TrendingUp className="w-4 h-4 text-cyan-400" />
              </div>
              <div className="text-2xl font-black text-cyan-400">
                {summary.topProjectedScore?.toFixed(2)}
                <span className="text-xs font-normal text-slate-500 ml-1">/100</span>
              </div>
              <div className="flex items-center gap-2">
                <Badge variant="cyan" className="text-[10px]">
                  +{summary.topRankedScenario?.scoreDelta?.toFixed(2)} SCORE DELTA
                </Badge>
                <span className="text-[10px] text-slate-400">SIMULATED</span>
              </div>
            </Card>

            <Card className="bg-slate-900/80 border-slate-800/80 backdrop-blur-md space-y-3">
              <div className="flex items-center justify-between text-xs font-medium text-slate-400">
                <span>PROJECTED CASH IMPACT</span>
                <Sparkles className="w-4 h-4 text-emerald-400" />
              </div>
              <div className="text-2xl font-black text-emerald-400">
                +₹{summary.topRankedScenario?.projectedCashImpact?.toLocaleString('en-IN')}
              </div>
              <p className="text-[11px] text-slate-400">Net projected liquidity enhancement across {horizon}</p>
            </Card>

            <Card className="bg-slate-900/80 border-slate-800/80 backdrop-blur-md space-y-3">
              <div className="flex items-center justify-between text-xs font-medium text-slate-400">
                <span>RISK REDUCTION</span>
                <ShieldCheck className="w-4 h-4 text-indigo-400" />
              </div>
              <div className="text-2xl font-black text-indigo-400">
                -{summary.topRankedScenario?.projectedRiskReduction?.toFixed(1)}%
              </div>
              <div className="flex items-center gap-2">
                <Badge variant="neutral" className="text-[10px] text-slate-300">
                  CONFIDENCE: {summary.topRankedScenario?.confidenceStatus}
                </Badge>
              </div>
            </Card>
          </div>

          {/* Main Scenario Grid & Composition */}
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Scenarios List */}
            <div className="lg:col-span-2 space-y-4">
              <div className="flex items-center justify-between">
                <h3 className="text-base font-bold text-white uppercase tracking-wider flex items-center gap-2">
                  <Layers className="w-4 h-4 text-cyan-400" />
                  Evaluated What-If Scenarios ({summary.scenarios?.length || 0})
                </h3>
                <span className="text-xs text-slate-400">Active Horizon: {horizon}</span>
              </div>

              {summary.scenarios?.map((s) => {
                const isSelected = selectedScenario?.id === s.id;
                return (
                  <div
                    key={s.id}
                    onClick={() => setSelectedScenario(s)}
                    className={`bg-slate-900/90 border p-5 rounded-2xl transition-all cursor-pointer space-y-4 ${
                      isSelected
                        ? 'border-cyan-500/80 shadow-lg shadow-cyan-500/10 ring-1 ring-cyan-500/50'
                        : 'border-slate-800/80 hover:border-slate-700'
                    }`}
                  >
                    <div className="flex items-start justify-between gap-4">
                      <div className="space-y-1">
                        <div className="flex items-center gap-2">
                          <Badge variant="cyan" className="text-[10px] uppercase">
                            {s.status}
                          </Badge>
                          <Badge variant="demo" className="text-[10px]">
                            {s.horizon} HORIZON
                          </Badge>
                          <span className="text-[11px] font-mono text-slate-400">#{s.scenarioKey}</span>
                        </div>
                        <h4 className="text-base font-bold text-white pt-1">{s.scenarioName}</h4>
                      </div>

                      <div className="text-right">
                        <div className="text-xl font-black text-cyan-400">{s.projectedScore?.toFixed(2)}</div>
                        <div className="text-xs font-semibold text-emerald-400">+{s.scoreDelta?.toFixed(2)} Score Delta</div>
                      </div>
                    </div>

                    {/* Progress Comparison Bar */}
                    <div className="space-y-1.5">
                      <div className="flex justify-between text-[11px] text-slate-400 font-medium">
                        <span>Baseline: {s.baselineScore?.toFixed(2)}</span>
                        <span>Projected: {s.projectedScore?.toFixed(2)}</span>
                      </div>
                      <div className="w-full h-2 bg-slate-950 rounded-full overflow-hidden flex">
                        <div
                          className="h-full bg-slate-600 rounded-l-full"
                          style={{ width: `${s.baselineScore}%` }}
                        />
                        <div
                          className="h-full bg-cyan-400 rounded-r-full"
                          style={{ width: `${Math.max(0, (s.projectedScore || 0) - (s.baselineScore || 0))}%` }}
                        />
                      </div>
                    </div>

                    {/* Impact Summary Metrics */}
                    <div className="grid grid-cols-3 gap-2 bg-slate-950/60 p-3 rounded-xl border border-slate-800/60 text-center">
                      <div>
                        <div className="text-[10px] text-slate-500 font-medium">CASH IMPACT</div>
                        <div className="text-xs font-bold text-emerald-400">+₹{s.projectedCashImpact?.toLocaleString('en-IN')}</div>
                      </div>
                      <div>
                        <div className="text-[10px] text-slate-500 font-medium">RISK REDUCTION</div>
                        <div className="text-xs font-bold text-indigo-400">-{s.projectedRiskReduction?.toFixed(1)}%</div>
                      </div>
                      <div>
                        <div className="text-[10px] text-slate-500 font-medium">GOAL IMPACT</div>
                        <div className="text-xs font-bold text-cyan-400">+{s.projectedGoalImpact?.toFixed(1)}%</div>
                      </div>
                    </div>

                    {/* Footer Actions */}
                    <div className="flex items-center justify-between pt-1">
                      <div className="flex items-center gap-1.5 text-slate-400 text-xs">
                        <Info className="w-3.5 h-3.5 text-cyan-400" />
                        <span className="truncate max-w-[320px]">{s.assumptions}</span>
                      </div>

                      {s.status !== 'ARCHIVED' && (
                        <Button
                          onClick={(e) => {
                            e.stopPropagation();
                            handleArchive(s.id);
                          }}
                          disabled={archivingId === s.id}
                          variant="ghost"
                          className="h-7 px-2 text-[11px] text-slate-400 hover:text-rose-400 hover:bg-rose-500/10"
                        >
                          <Archive className="w-3 h-3 mr-1" />
                          {archivingId === s.id ? 'Archiving...' : 'Archive'}
                        </Button>
                      )}
                    </div>
                  </div>
                );
              })}
            </div>

            {/* Selected Scenario Composition & Evidence Drawer */}
            <div className="space-y-4">
              <h3 className="text-base font-bold text-white uppercase tracking-wider flex items-center gap-2">
                <Sliders className="w-4 h-4 text-cyan-400" />
                Intervention Composition
              </h3>

              {selectedScenario ? (
                <div className="bg-slate-900/90 border border-slate-800 p-5 rounded-2xl space-y-4">
                  <div className="space-y-1">
                    <Badge variant="cyan" className="text-[10px]">
                      SELECTED SCENARIO
                    </Badge>
                    <h4 className="text-base font-bold text-white">{selectedScenario.scenarioName}</h4>
                    <p className="text-xs text-slate-400">{selectedScenario.assumptions}</p>
                  </div>

                  <div className="space-y-3 pt-2">
                    <h5 className="text-xs font-bold text-slate-300 uppercase tracking-wider">
                      Simulated Interventions ({selectedScenario.items?.length || 0})
                    </h5>

                    {selectedScenario.items?.map((item) => (
                      <div
                        key={item.id}
                        className="bg-slate-950 border border-slate-800 p-3.5 rounded-xl space-y-2"
                      >
                        <div className="flex items-center justify-between text-xs">
                          <Badge variant="neutral" className="text-[10px] font-mono">
                            RANK #{item.rankOrder}
                          </Badge>
                          <span className="font-bold text-cyan-400 text-[11px]">{item.interventionType}</span>
                        </div>

                        <div className="grid grid-cols-2 gap-2 text-[11px] pt-1">
                          <div>
                            <span className="text-slate-500 block">Projected Cash</span>
                            <span className="font-bold text-emerald-400">+₹{item.projectedImpact?.toLocaleString('en-IN')}</span>
                          </div>
                          <div>
                            <span className="text-slate-500 block">Risk Reduction</span>
                            <span className="font-bold text-indigo-400">-{item.projectedRiskReduction?.toFixed(1)}%</span>
                          </div>
                        </div>

                        <p className="text-[10px] text-slate-400 pt-1 border-t border-slate-900">
                          {item.evidenceMetrics}
                        </p>
                      </div>
                    ))}
                  </div>

                  {/* Evidence Metrics Footer */}
                  <div className="bg-slate-950/80 p-3 rounded-xl border border-slate-800 space-y-1">
                    <div className="text-[10px] font-bold text-slate-400 uppercase tracking-wider flex items-center gap-1">
                      <BrainCircuit className="w-3 h-3 text-cyan-400" />
                      SIMULATION EVIDENCE METRICS
                    </div>
                    <p className="text-[11px] font-mono text-slate-300 leading-relaxed">
                      {selectedScenario.evidenceMetrics}
                    </p>
                  </div>
                </div>
              ) : (
                <div className="bg-slate-900/60 border border-slate-800 p-8 rounded-2xl text-center space-y-2">
                  <Info className="w-6 h-6 text-slate-500 mx-auto" />
                  <p className="text-xs text-slate-400">Select a scenario to view its intervention composition</p>
                </div>
              )}
            </div>
          </div>
        </>
      ) : null}
    </div>
  );
}
