'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantScenarioComparison,
  simulateScenario,
  BackendScenarioComparisonDTO,
  BackendFinancialScenarioDTO,
  BackendScenarioSimulationRequestDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  LineChart,
  TrendingUp,
  AlertTriangle,
  CheckCircle2,
  RefreshCw,
  Sliders,
  Info,
  ChevronDown,
  ChevronUp,
  ShieldAlert,
  HelpCircle,
  Save,
} from 'lucide-react';

export default function ScenariosPage() {
  const [comparison, setComparison] = useState<BackendScenarioComparisonDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Custom Simulator State
  const [customName, setCustomName] = useState<string>('Custom Growth Model');
  const [revMod, setRevMod] = useState<number>(-15);
  const [expMod, setExpMod] = useState<number>(10);
  const [recPct, setRecPct] = useState<number>(75);
  const [payPct, setPayPct] = useState<number>(100);
  const [saveScenario, setSaveScenario] = useState<boolean>(false);
  const [simulating, setSimulating] = useState<boolean>(false);
  const [customResult, setCustomResult] = useState<BackendFinancialScenarioDTO | null>(null);
  const [showAssumptions, setShowAssumptions] = useState<boolean>(false);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantScenarioComparison(1);
      setComparison(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Scenario Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSimulateCustom = async (e: React.FormEvent) => {
    e.preventDefault();
    setSimulating(true);
    try {
      const req: BackendScenarioSimulationRequestDTO = {
        scenarioType: 'CUSTOM',
        name: customName,
        revenueModifierPct: revMod,
        expenseModifierPct: expMod,
        receivableCollectionPct: recPct,
        payableAccelerationPct: payPct,
        saveScenario,
      };

      const res = await simulateScenario(1, req);
      setCustomResult(res);
      if (saveScenario) {
        loadData();
      }
    } catch (err: any) {
      alert(err.message || 'Failed to execute custom scenario simulation');
    } finally {
      setSimulating(false);
    }
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {[1, 2, 3].map((i) => (
            <div key={i} className="h-72 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
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
              SCENARIO INTELLIGENCE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Scenario API (`http://localhost:8080/api/v1/merchants/1/scenarios/comparison`).
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
              Scenario & Forecast Intelligence
            </h1>
            <Badge variant="demo">DETERMINISTIC SIMULATOR</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic 7, 30, 60, and 90-day multi-horizon liquidity projections under Baseline, Cautious, and Stress market models
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Refresh Models
        </Button>
      </div>

      {/* Primary Risk Alert Banner */}
      {comparison && (
        <Card className="p-4 bg-[#0A0906] border-amber-500/40 text-amber-300 space-y-1">
          <div className="flex items-center gap-2 font-bold text-xs uppercase">
            <ShieldAlert className="w-4 h-4 text-amber-400" />
            Primary Liquidity Warning & Strategy Advice
          </div>
          <p className="text-xs font-sans text-slate-300">{comparison.primaryRiskAlert}</p>
          <p className="text-[11px] font-mono text-slate-400 pt-1 border-t border-white/5">{comparison.summaryAdvice}</p>
        </Card>
      )}

      {/* Pre-Built Multi-Scenario Comparison Cards */}
      {comparison && (
        <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[
            { dto: comparison.baselineScenario, color: 'cyan', title: 'BASELINE' },
            { dto: comparison.cautiousScenario, color: 'amber', title: 'CAUTIOUS' },
            { dto: comparison.stressScenario, color: 'rose', title: 'STRESS' },
          ].map(({ dto, color, title }) => (
            <Card
              key={title}
              className={`p-6 space-y-5 border-t-4 ${
                color === 'cyan'
                  ? 'border-t-[#00F0FF] border-white/10'
                  : color === 'amber'
                  ? 'border-t-amber-400 border-amber-500/20'
                  : 'border-t-rose-400 border-rose-500/20'
              }`}
            >
              {/* Card Header */}
              <div className="flex items-center justify-between border-b border-white/10 pb-3">
                <div className="space-y-1">
                  <div className="flex items-center gap-2">
                    <span className="text-sm font-bold text-white uppercase">{title}</span>
                    <Badge variant={color === 'cyan' ? 'cyan' : color === 'amber' ? 'amber' : 'rose'} className="text-[9px]">
                      {dto.scenarioType}
                    </Badge>
                  </div>
                  <span className="text-[11px] text-slate-400 block">{dto.name}</span>
                </div>

                <Badge
                  variant={
                    dto.riskStatus === 'FEASIBLE'
                      ? 'emerald'
                      : dto.riskStatus === 'CAUTION'
                      ? 'amber'
                      : 'rose'
                  }
                  className="text-[9px] uppercase"
                >
                  {dto.riskStatus}
                </Badge>
              </div>

              {/* Modifiers Box */}
              <div className="p-3 bg-[#05080E] border border-white/5 text-[11px] font-mono space-y-1 text-slate-300">
                <div className="flex justify-between">
                  <span className="text-slate-500">Revenue Mod:</span>
                  <span className={(dto.revenueModifierPct ?? 0) < 0 ? 'text-rose-400' : 'text-emerald-400'}>{dto.revenueModifierPct ?? 0}%</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-500">Expense Mod:</span>
                  <span className={(dto.expenseModifierPct ?? 0) > 0 ? 'text-amber-400' : 'text-slate-300'}>+{(dto.expenseModifierPct ?? 0)}%</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-500">Rec Collection:</span>
                  <span className="text-cyan-400">{dto.receivableCollectionPct ?? 100}%</span>
                </div>
              </div>

              {/* Projections Horizons Grid */}
              <div className="space-y-2">
                <span className="text-[10px] text-slate-500 uppercase font-bold block">Projected Cash Positions</span>
                <div className="grid grid-cols-2 gap-2 text-xs font-mono">
                  <div className="p-2 bg-[#05080E] border border-white/5">
                    <span className="text-[10px] text-slate-500 block">7-Day:</span>
                    <span className="text-white font-bold">₹{(dto.projected7dCash ?? 0).toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2 bg-[#05080E] border border-white/5">
                    <span className="text-[10px] text-slate-500 block">30-Day:</span>
                    <span className="text-white font-bold">₹{(dto.projected30dCash ?? 0).toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2 bg-[#05080E] border border-white/5">
                    <span className="text-[10px] text-slate-500 block">60-Day:</span>
                    <span className="text-cyan-300 font-bold">₹{(dto.projected60dCash ?? 0).toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2 bg-[#05080E] border border-white/5">
                    <span className="text-[10px] text-slate-500 block">90-Day:</span>
                    <span className="text-cyan-300 font-bold">₹{(dto.projected90dCash ?? 0).toLocaleString('en-IN')}</span>
                  </div>
                </div>
              </div>

              {/* Runway & Goal Impact */}
              <div className="pt-2 border-t border-white/10 space-y-1.5 text-xs font-mono">
                <div className="flex justify-between items-center">
                  <span className="text-slate-400">Cash Runway:</span>
                  <span className="text-white font-bold">{dto.runwayMonths} Months</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-slate-400">Goal Target:</span>
                  <span className={dto.goalAchievable ? 'text-[#00E599] font-bold' : 'text-rose-400 font-bold'}>
                    {dto.goalAchievable ? 'ACHIEVABLE' : 'AT RISK'}
                  </span>
                </div>
              </div>
            </Card>
          ))}
        </section>
      )}

      {/* Interactive Custom Scenario Simulator */}
      <section className="space-y-4">
        <div className="flex items-center gap-2 border-b border-white/10 pb-3">
          <Sliders className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Interactive Custom Scenario Sandbox</h2>
        </div>

        <Card className="p-6 bg-[#05080E] border-[#00F0FF]/30 space-y-6">
          <form onSubmit={handleSimulateCustom} className="space-y-6">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div className="space-y-1">
                <label className="text-xs text-slate-400 font-mono">Scenario Name</label>
                <input
                  type="text"
                  value={customName}
                  onChange={(e) => setCustomName(e.target.value)}
                  className="w-full bg-[#0E1116] border border-white/10 px-3 py-2 text-xs text-white font-mono focus:border-[#00F0FF] outline-none"
                  placeholder="e.g. Q3 Expansion Model"
                />
              </div>

              <div className="flex items-center gap-2 pt-6">
                <label className="text-xs text-slate-300 font-mono flex items-center gap-2 cursor-pointer">
                  <input
                    type="checkbox"
                    checked={saveScenario}
                    onChange={(e) => setSaveScenario(e.target.checked)}
                    className="accent-[#00F0FF]"
                  />
                  Save scenario definition to history
                </label>
              </div>
            </div>

            {/* Slider Controls Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              <div className="space-y-2">
                <div className="flex justify-between text-xs font-mono">
                  <span className="text-slate-400">Revenue Mod %:</span>
                  <span className={revMod < 0 ? 'text-rose-400 font-bold' : 'text-emerald-400 font-bold'}>{revMod}%</span>
                </div>
                <input
                  type="range"
                  min="-50"
                  max="50"
                  value={revMod}
                  onChange={(e) => setRevMod(Number(e.target.value))}
                  className="w-full accent-[#00F0FF]"
                />
              </div>

              <div className="space-y-2">
                <div className="flex justify-between text-xs font-mono">
                  <span className="text-slate-400">Expense Mod %:</span>
                  <span className={expMod > 0 ? 'text-amber-400 font-bold' : 'text-slate-300 font-bold'}>+{expMod}%</span>
                </div>
                <input
                  type="range"
                  min="-50"
                  max="50"
                  value={expMod}
                  onChange={(e) => setExpMod(Number(e.target.value))}
                  className="w-full accent-[#00F0FF]"
                />
              </div>

              <div className="space-y-2">
                <div className="flex justify-between text-xs font-mono">
                  <span className="text-slate-400">Receivable Collection %:</span>
                  <span className="text-cyan-400 font-bold">{recPct}%</span>
                </div>
                <input
                  type="range"
                  min="0"
                  max="100"
                  value={recPct}
                  onChange={(e) => setRecPct(Number(e.target.value))}
                  className="w-full accent-[#00F0FF]"
                />
              </div>

              <div className="space-y-2">
                <div className="flex justify-between text-xs font-mono">
                  <span className="text-slate-400">Payable Acceleration %:</span>
                  <span className="text-amber-400 font-bold">{payPct}%</span>
                </div>
                <input
                  type="range"
                  min="0"
                  max="100"
                  value={payPct}
                  onChange={(e) => setPayPct(Number(e.target.value))}
                  className="w-full accent-[#00F0FF]"
                />
              </div>
            </div>

            <Button type="submit" variant="cyan" size="lg" disabled={simulating} className="gap-2 w-full sm:w-auto">
              <LineChart className="w-4 h-4" />
              {simulating ? 'Simulating Projections...' : 'Run Custom Simulation'}
            </Button>
          </form>

          {/* Custom Simulation Result Box */}
          {customResult && (
            <div className="p-5 bg-[#080C14] border border-[#00F0FF]/40 space-y-4 animate-fadeIn">
              <div className="flex items-center justify-between border-b border-white/10 pb-3">
                <div className="space-y-1">
                  <span className="text-xs text-slate-400 uppercase">Simulation Output Result</span>
                  <h4 className="text-base font-bold text-white">{customResult.name}</h4>
                </div>
                <Badge variant={customResult.riskStatus === 'FEASIBLE' ? 'emerald' : 'amber'} className="text-[10px]">
                  Risk: {customResult.riskStatus}
                </Badge>
              </div>

              <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-slate-500 block text-[10px]">7-Day Cash:</span>
                  <span className="text-white font-bold text-sm">₹{(customResult.projected7dCash ?? 0).toLocaleString('en-IN')}</span>
                </div>
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-slate-500 block text-[10px]">30-Day Cash:</span>
                  <span className="text-white font-bold text-sm">₹{(customResult.projected30dCash ?? 0).toLocaleString('en-IN')}</span>
                </div>
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-slate-500 block text-[10px]">60-Day Cash:</span>
                  <span className="text-cyan-300 font-bold text-sm">₹{(customResult.projected60dCash ?? 0).toLocaleString('en-IN')}</span>
                </div>
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-slate-500 block text-[10px]">90-Day Cash:</span>
                  <span className="text-cyan-300 font-bold text-sm">₹{(customResult.projected90dCash ?? 0).toLocaleString('en-IN')}</span>
                </div>
              </div>

              <p className="text-xs text-slate-300 font-sans border-t border-white/5 pt-2">
                {customResult.assumptions}
              </p>
            </div>
          )}
        </Card>
      </section>

      {/* Advisory Governance Disclaimer Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Forecast Governance & Non-Execution Directive
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise scenario projections are deterministic estimates based on historical ledger data and selected modifiers. Projections are read-only and advisory; they never automatically authorize payments, execute transfers, or modify financial ledger balances.
        </p>
      </section>
    </div>
  );
}
