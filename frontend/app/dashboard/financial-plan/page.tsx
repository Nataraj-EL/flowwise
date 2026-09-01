'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantFinancialPlans,
  evaluateFinancialPlan,
  activateFinancialPlan,
  archiveFinancialPlan,
  BackendFinancialPlanSummaryDTO,
  BackendFinancialPlanDTO,
  BackendFinancialPlanItemDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  ClipboardCheck,
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
  Archive,
} from 'lucide-react';

export default function FinancialPlanPage() {
  const [summary, setSummary] = useState<BackendFinancialPlanSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedHorizon, setSelectedHorizon] = useState<string>('30D');

  const loadData = async (horizon: string = selectedHorizon) => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantFinancialPlans(1, horizon);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Financial Plan API');
    } finally {
      setLoading(false);
    }
  };

  const handleEvaluate = async () => {
    setLoading(true);
    try {
      const data = await evaluateFinancialPlan(1, selectedHorizon);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to evaluate financial plan engine');
    } finally {
      setLoading(false);
    }
  };

  const handleActivate = async (planId: number) => {
    try {
      await activateFinancialPlan(1, planId);
      await loadData(selectedHorizon);
    } catch (err: any) {
      alert('Activation failed: ' + err.message);
    }
  };

  const handleArchive = async (planId: number) => {
    try {
      await archiveFinancialPlan(1, planId);
      await loadData(selectedHorizon);
    } catch (err: any) {
      alert('Archival failed: ' + err.message);
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
              FINANCIAL PLAN ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Financial Plan API (`http://localhost:8080/api/v1/merchants/1/financial-plans`).
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

  const activePlan = summary?.activePlan;

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Financial Plan Synthesis
            </h1>
            <Badge variant="demo">SYNTHESIS CONSOLE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic plan synthesis combining risks, anomalies, correlations, interventions, outcomes, and strategy multipliers into a ranked 6-factor advisory plan
          </p>
        </div>

        <Button variant="outline" onClick={handleEvaluate} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Plan Synthesis
        </Button>
      </div>

      {/* Main Plan Scorecard */}
      {summary && activePlan && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Active Plan Horizon</span>
              <div className="flex items-center gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{activePlan.horizon}</span>
                <Badge variant={activePlan.status === 'ACTIVE' ? 'emerald' : 'amber'} className="text-[10px] font-bold">
                  {activePlan.status}
                </Badge>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Overall Plan Score</span>
              <span className="text-3xl font-bold text-[#00F0FF]">{activePlan.overallPlanScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Primary Focus Area</span>
              <span className="text-sm font-bold text-emerald-400 block pt-1">{activePlan.primaryFocusArea}</span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Synthesized Directives</span>
              <span className="text-3xl font-bold text-white">{activePlan.items?.length || 0} <span className="text-xs text-slate-400 font-normal">Items</span></span>
            </div>
          </div>

          <div className="space-y-2 pt-1">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Plan Summary: </strong>
              {activePlan.summaryExplanation}
            </p>
          </div>

          {/* Plan Controls */}
          <div className="flex items-center gap-2 pt-2">
            {activePlan.status !== 'ACTIVE' && (
              <Button size="sm" variant="cyan" onClick={() => handleActivate(activePlan.id)} className="gap-2">
                <CheckCircle2 className="w-3.5 h-3.5" /> Activate Plan
              </Button>
            )}
            {activePlan.status !== 'ARCHIVED' && (
              <Button size="sm" variant="outline" onClick={() => handleArchive(activePlan.id)} className="gap-2 border-white/20 text-slate-300">
                <Archive className="w-3.5 h-3.5" /> Archive Plan
              </Button>
            )}
          </div>
        </Card>
      )}

      {/* Planning Horizon Selector */}
      <div className="flex items-center gap-2 font-mono text-xs">
        <span className="text-slate-400 font-bold uppercase text-[10px]">Planning Horizon:</span>
        {['30D', '7D', '90D'].map((h) => (
          <button
            key={h}
            onClick={() => handleHorizonChange(h)}
            className={`px-3 py-1.5 border transition-colors ${
              selectedHorizon === h
                ? 'bg-[#00F0FF]/10 border-[#00F0FF] text-[#00F0FF] font-bold'
                : 'bg-[#05080E] border-white/10 text-slate-400 hover:text-white'
            }`}
          >
            {h} HORIZON
          </button>
        ))}
      </div>

      {/* Plan Directives Timeline */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <ClipboardCheck className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Ranked Advisory Plan Directives</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {activePlan?.items?.map((item) => (
            <Card key={item.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge variant="cyan" className="text-[10px] font-bold">#{item.rankOrder} DIRECTIVE</Badge>
                    <Badge variant="demo" className="text-[10px]">{item.interventionType}</Badge>
                    <Badge variant="emerald" className="text-[10px]">{item.horizon} HORIZON</Badge>
                  </div>
                  <h3 className="text-base font-bold text-white uppercase pt-1">{item.title}</h3>
                  <p className="text-xs text-slate-300 font-sans">{item.description}</p>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  <div className="p-3 bg-[#080E18] border border-[#00F0FF]/30 text-right">
                    <span className="text-[10px] text-slate-500 uppercase block font-bold">Priority Score</span>
                    <span className="text-2xl font-black text-[#00F0FF]">{item.priorityScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
                  </div>
                </div>
              </div>

              {/* 6-Factor Score Breakdown */}
              <div className="grid grid-cols-2 sm:grid-cols-6 gap-2 text-xs font-mono">
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Risk Prot (30%)</span>
                  <span className="text-sm font-bold text-emerald-400">{item.riskProtectionScore}</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Impact (25%)</span>
                  <span className="text-sm font-bold text-cyan-300">{item.financialImpactScore}</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Urgency (20%)</span>
                  <span className="text-sm font-bold text-amber-400">{item.urgencyScore}</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Goal Align (10%)</span>
                  <span className="text-sm font-bold text-slate-300">{item.goalAlignmentScore}</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Hist Eff (10%)</span>
                  <span className="text-sm font-bold text-slate-300">{item.historicalEffectivenessScore}</span>
                </div>
                <div className="p-2 bg-[#05080E] border border-white/5 text-center">
                  <span className="text-[9px] text-slate-500 uppercase block font-bold">Confidence (5%)</span>
                  <span className="text-sm font-bold text-slate-400">{item.confidenceStatus}</span>
                </div>
              </div>

              {/* Benefit vs Risk Details */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Expected Financial Benefit</span>
                  <p className="text-emerald-400 font-bold">{item.expectedBenefit}</p>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Risk If Ignored</span>
                  <p className="text-rose-400 font-bold">{item.riskIfIgnored}</p>
                </div>
              </div>

              {/* Evidence Footer */}
              <div className="p-3 bg-[#080E18] border border-white/5 flex items-center justify-between text-xs font-mono">
                <span className="text-slate-300"><strong className="text-slate-500">Evidence:</strong> {item.evidenceMetrics}</span>
                <span className="text-[10px] text-slate-500">Rank #{item.rankOrder} | 6-FACTOR SYNTHESIS</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Governance Disclaimer */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Financial Plan Governance Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Financial Plan Synthesis Engine converts current risks, anomalies, correlations, interventions, outcomes, and learned strategy multipliers into a 6-factor ranked advisory plan. Plans are strictly read-only and advisory; Flowwise never executes payments, modifies bank accounts, or alters ledger state automatically.
        </p>
      </section>
    </div>
  );
}
