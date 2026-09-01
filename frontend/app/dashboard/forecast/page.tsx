'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantForecast,
  simulateMerchantScenario,
  BackendForecastSummaryDTO,
  BackendScenarioResultDTO,
} from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { AskFlowwisePanel } from '@/components/dashboard/AskFlowwisePanel';
import {
  TrendingUp,
  LineChart,
  Calculator,
  ShieldCheck,
  AlertTriangle,
  RefreshCw,
  Zap,
  Clock,
  ArrowRight,
  Info,
  Layers,
  ArrowDownRight,
  CheckCircle2,
} from 'lucide-react';

export default function ForecastPage() {
  const [forecast, setForecast] = useState<BackendForecastSummaryDTO | null>(null);
  const [scenarioResult, setScenarioResult] = useState<BackendScenarioResultDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [simulating, setSimulating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  // Scenario Input State
  const [scenarioAmount, setScenarioAmount] = useState<string>('80000');
  const [scenarioCategory, setScenarioCategory] = useState<string>('INVENTORY');

  const categories = ['INVENTORY', 'PAYROLL', 'RENT', 'OPERATIONS'];

  const loadForecastData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [forecastData, defaultScenario] = await Promise.all([
        fetchMerchantForecast(1),
        simulateMerchantScenario(1, { amount: 80000, category: 'INVENTORY' }),
      ]);
      setForecast(forecastData);
      setScenarioResult(defaultScenario);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Forecast API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadForecastData();
  }, []);

  const handleSimulate = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    const amt = parseFloat(scenarioAmount);
    if (isNaN(amt) || amt <= 0) return;

    setSimulating(true);
    try {
      const res = await simulateMerchantScenario(1, { amount: amt, category: scenarioCategory });
      setScenarioResult(res);
    } catch (err: any) {
      setError(err.message || 'Scenario simulation failed');
    } finally {
      setSimulating(false);
    }
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
          {[1, 2, 3].map((i) => (
            <div key={i} className="h-36 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !forecast) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              FORECAST ENGINE API UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Forecasting Engine (`http://localhost:8080/api/v1/merchants/1/forecast`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadForecastData} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry API Connection
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
              Forecast & Scenario Console
            </h1>
            <Badge variant="demo">ESTIMATE PROJECTIONS</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            30/60/90-Day Cash Projections & What-If Simulations for <span className="text-white font-bold">Apex Retail Solutions [DEMO]</span>
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs">
          <Calculator className="w-4 h-4 text-[#00F0FF]" />
          SPRING BOOT FORECASTING SERVICE
        </Badge>
      </div>

      {/* 30/60/90-Day Projections Baseline Cards */}
      <section className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        {forecast.projections.map((p) => (
          <Card key={p.days} className="space-y-3">
            <div className="flex items-center justify-between border-b border-white/10 pb-2">
              <span className="text-xs font-bold text-white uppercase">
                {p.days}-Day Projected Horizon
              </span>
              <Badge variant="demo">ESTIMATE</Badge>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-400 uppercase">Projected Ending Cash</span>
              <div className="text-2xl font-bold text-[#00F0FF]">
                {formatINR(p.projectedEndingCash)}
              </div>
            </div>

            <div className="grid grid-cols-2 gap-2 text-[11px] pt-1 border-t border-white/5">
              <div>
                <span className="text-slate-500">Inflow ({p.days}d):</span>
                <div className="text-white font-semibold">+{formatINR(p.projectedInflow)}</div>
              </div>
              <div>
                <span className="text-slate-500">Outflow ({p.days}d):</span>
                <div className="text-slate-300 font-semibold">-{formatINR(p.projectedOutflow)}</div>
              </div>
            </div>

            <div className="p-2 bg-[#07080B] border border-white/10 flex justify-between items-center text-xs">
              <span className="text-slate-400">Projected Runway</span>
              <span className="text-[#00E599] font-bold">{p.projectedRunwayMonths} Months</span>
            </div>
          </Card>
        ))}
      </section>

      {/* Interactive Scenario Sandbox Section */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left: Input Sandbox Form */}
        <Card variant="glow-cyan" className="space-y-5">
          <div className="flex items-center gap-2 border-b border-white/10 pb-3">
            <Calculator className="w-5 h-5 text-[#00F0FF]" />
            <h3 className="text-base font-bold text-white uppercase tracking-wider">
              What-If Scenario Sandbox
            </h3>
          </div>

          <form onSubmit={handleSimulate} className="space-y-4 text-xs">
            <div className="space-y-1.5">
              <label className="text-slate-300 font-bold uppercase">
                Hypothetical One-Time Expense (₹)
              </label>
              <Input
                type="number"
                value={scenarioAmount}
                onChange={(e) => setScenarioAmount(e.target.value)}
                placeholder="e.g. 80000"
                className="text-sm font-bold text-[#00F0FF]"
              />
            </div>

            <div className="space-y-1.5">
              <label className="text-slate-300 font-bold uppercase">Expense Category</label>
              <select
                value={scenarioCategory}
                onChange={(e) => setScenarioCategory(e.target.value)}
                className="w-full bg-[#07080B] border border-white/15 text-slate-200 text-xs font-mono px-3 py-2 focus:outline-none focus:border-[#00F0FF]"
              >
                {categories.map((c) => (
                  <option key={c} value={c}>
                    {c}
                  </option>
                ))}
              </select>
            </div>

            <Button variant="cyan" size="lg" type="submit" disabled={simulating} className="w-full gap-2">
              {simulating ? <span className="animate-spin text-xs">⏳</span> : <Zap className="w-4 h-4" />}
              <span>Simulate Scenario Impact</span>
            </Button>
          </form>

          {/* Quick Preset Buttons */}
          <div className="space-y-2 pt-2 border-t border-white/10 text-xs">
            <span className="text-[10px] text-slate-500 uppercase font-semibold">Preset Scenarios</span>
            <div className="flex flex-wrap gap-2">
              {[
                { label: '₹80,000 Inventory', amt: '80000', cat: 'INVENTORY' },
                { label: '₹1,50,000 Equipment', amt: '150000', cat: 'OPERATIONS' },
                { label: '₹2,00,000 Expansion', amt: '200000', cat: 'INVENTORY' },
              ].map((preset) => (
                <button
                  key={preset.label}
                  onClick={() => {
                    setScenarioAmount(preset.amt);
                    setScenarioCategory(preset.cat);
                    simulateMerchantScenario(1, { amount: parseFloat(preset.amt), category: preset.cat }).then(setScenarioResult);
                  }}
                  className="text-[11px] bg-[#07080B] hover:bg-white/5 border border-white/10 px-2.5 py-1 text-slate-300 hover:text-white"
                >
                  {preset.label}
                </button>
              ))}
            </div>
          </div>
        </Card>

        {/* Right: Scenario Impact Output Card */}
        {scenarioResult && (
          <Card className="lg:col-span-2 space-y-6">
            <div className="flex items-center justify-between border-b border-white/10 pb-4">
              <div className="flex items-center gap-2">
                <LineChart className="w-5 h-5 text-[#00E599]" />
                <div>
                  <h3 className="text-base font-bold text-white uppercase tracking-wider">
                    Baseline vs Scenario Simulation Impact
                  </h3>
                  <p className="text-[11px] text-slate-400">
                    Modelling ₹{scenarioResult.requestedAmount.toLocaleString('en-IN')} outlay for {scenarioResult.category}
                  </p>
                </div>
              </div>

              <Badge
                variant={
                  scenarioResult.riskStatus === 'FEASIBLE'
                    ? 'emerald'
                    : scenarioResult.riskStatus === 'CAUTION'
                    ? 'amber'
                    : 'rose'
                }
                className="py-1.5 px-3 gap-1"
              >
                <ShieldCheck className="w-3.5 h-3.5" />
                {scenarioResult.riskStatus}
              </Badge>
            </div>

            {/* Impact Metric Comparison Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-xs">
              {/* Cash Balance Impact */}
              <div className="p-4 bg-[#07080B] border border-white/10 space-y-2">
                <span className="text-[10px] text-slate-400 uppercase font-bold">Liquid Cash Impact</span>
                <div className="flex items-center justify-between font-bold">
                  <div className="space-y-0.5">
                    <div className="text-slate-500 text-[10px]">Baseline Cash</div>
                    <div className="text-white text-base">{formatINR(scenarioResult.baselineEndingCash)}</div>
                  </div>
                  <ArrowRight className="w-4 h-4 text-slate-500 shrink-0" />
                  <div className="space-y-0.5 text-right">
                    <div className="text-slate-500 text-[10px]">Scenario Cash</div>
                    <div className="text-[#00F0FF] text-base">{formatINR(scenarioResult.scenarioEndingCash)}</div>
                  </div>
                </div>
                <div className="text-[11px] text-rose-400 font-bold border-t border-white/5 pt-1 text-right">
                  Net Impact: {formatINR(scenarioResult.cashImpact)}
                </div>
              </div>

              {/* Runway Impact */}
              <div className="p-4 bg-[#07080B] border border-white/10 space-y-2">
                <span className="text-[10px] text-slate-400 uppercase font-bold">Cash Runway Impact</span>
                <div className="flex items-center justify-between font-bold">
                  <div className="space-y-0.5">
                    <div className="text-slate-500 text-[10px]">Baseline Runway</div>
                    <div className="text-white text-base">{scenarioResult.baselineRunwayMonths} Mo</div>
                  </div>
                  <ArrowRight className="w-4 h-4 text-slate-500 shrink-0" />
                  <div className="space-y-0.5 text-right">
                    <div className="text-slate-500 text-[10px]">Scenario Runway</div>
                    <div className="text-[#00E599] text-base">{scenarioResult.scenarioRunwayMonths} Mo</div>
                  </div>
                </div>
                <div className="text-[11px] text-amber-400 font-bold border-t border-white/5 pt-1 text-right">
                  Runway Delta: {scenarioResult.runwayImpactMonths} Months
                </div>
              </div>
            </div>

            {/* Assumptions List */}
            <div className="space-y-2 pt-2 border-t border-white/10 text-xs">
              <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1">
                <Info className="w-3.5 h-3.5 text-[#00F0FF]" /> Simulation Assumptions & Bounds
              </span>
              <div className="space-y-1 text-[11px] text-slate-300">
                {scenarioResult.assumptions.map((asm, idx) => (
                  <div key={idx} className="flex items-start gap-2">
                    <CheckCircle2 className="w-3.5 h-3.5 text-[#00F0FF] shrink-0 mt-0.5" />
                    <span>{asm}</span>
                  </div>
                ))}
              </div>
            </div>
          </Card>
        )}
      </section>

      {/* Ask Flowwise AI Panel */}
      <AskFlowwisePanel />
    </div>
  );
}
