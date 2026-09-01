'use client';

import React, { useEffect, useState } from 'react';
import {
  CalendarClock,
  CheckCircle2,
  AlertTriangle,
  Clock,
  RefreshCw,
  Info,
  Layers,
  ArrowRight,
  ShieldCheck,
  Zap,
  Lock,
  ChevronRight,
  ShieldAlert,
} from 'lucide-react';
import {
  fetchFinancialExecutionScheduleSummary,
  evaluateFinancialExecutionSchedule,
  BackendFinancialExecutionScheduleSummaryDTO,
  BackendFinancialExecutionScheduleDTO,
  BackendFinancialExecutionScheduleItemDTO,
} from '@/lib/api';

export default function ExecutionSchedulePage() {
  const [merchantId] = useState<number>(1);
  const [horizon, setHorizon] = useState<string>('30D');
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [summary, setSummary] = useState<BackendFinancialExecutionScheduleSummaryDTO | null>(null);
  const [actionNotice, setActionNotice] = useState<string | null>(null);

  const loadData = async (selectedHorizon: string) => {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchFinancialExecutionScheduleSummary(merchantId, selectedHorizon);
      setSummary(data);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load financial execution schedule');
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

  const handleEvaluate = async () => {
    try {
      setEvaluating(true);
      setActionNotice(null);
      await evaluateFinancialExecutionSchedule(merchantId, horizon);
      setActionNotice(`Evaluated new advisory execution schedule for ${horizon} horizon.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to evaluate execution schedule');
    } finally {
      setEvaluating(false);
    }
  };

  const activeSchedule = summary?.activeSchedule;

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-8 flex items-center justify-center">
        <div className="flex items-center gap-3 text-cyan-400">
          <RefreshCw className="w-6 h-6 animate-spin" />
          <span className="font-medium text-lg">Evaluating Execution Capacity & Adaptive Schedule...</span>
        </div>
      </div>
    );
  }

  const scheduledItems = activeSchedule?.items?.filter((i) => i.readinessStatus === 'SCHEDULED') || [];
  const deferredItems = activeSchedule?.items?.filter((i) => i.readinessStatus === 'DEFERRED') || [];
  const primaryItem = scheduledItems[0];

  return (
    <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-6 md:p-8 space-y-8 font-sans">
      {/* Top Banner & Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-6">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-2.5 bg-cyan-500/10 border border-cyan-500/20 rounded-xl text-cyan-400">
              <CalendarClock className="w-7 h-7" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-white">
                  Execution Schedule & Capacity
                </h1>
                <span className="px-2.5 py-0.5 text-xs font-semibold bg-cyan-500/10 text-cyan-400 border border-cyan-500/30 rounded-full">
                  ADVISORY_EXECUTION_SCHEDULE
                </span>
              </div>
              <p className="text-slate-400 text-sm mt-1">
                Dependency-aware, capacity-constrained execution sequence answering: What to execute this week, in what order, and what to defer.
              </p>
            </div>
          </div>
        </div>

        {/* Horizon Selector & Re-Evaluate Button */}
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
            onClick={handleEvaluate}
            disabled={evaluating}
            className="flex items-center gap-2 px-4 py-2 bg-cyan-600 hover:bg-cyan-500 text-white rounded-xl text-xs font-semibold shadow-lg shadow-cyan-600/20 transition-all"
          >
            <RefreshCw className={`w-3.5 h-3.5 ${evaluating ? 'animate-spin' : ''}`} />
            Evaluate Schedule
          </button>
        </div>
      </div>

      {/* Governance Notice Banner */}
      <div className="bg-amber-950/20 border border-amber-500/30 rounded-xl p-4 flex items-start gap-3 text-amber-300 text-sm">
        <Info className="w-5 h-5 text-amber-400 shrink-0 mt-0.5" />
        <div>
          <span className="font-semibold text-amber-300">ADVISORY — NO AUTOMATIC EXECUTION:</span>{' '}
          All schedules are purely advisory (`ADVISORY_EXECUTION_SCHEDULE`). Flowwise never executes payments, transfers, or account state changes. Safety-critical risk protection (Risk &ge; 85.00) always overrides effort and capacity limits.
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

      {/* Hero "Execute Next" Card */}
      {primaryItem && (
        <div className="bg-gradient-to-r from-cyan-950/40 via-[#121622] to-[#121622] border border-cyan-500/30 rounded-2xl p-6 shadow-2xl relative overflow-hidden">
          <div className="absolute top-0 right-0 p-8 opacity-10 text-cyan-400 pointer-events-none">
            <CalendarClock className="w-48 h-48" />
          </div>

          <div className="space-y-4 relative z-10">
            <div className="flex items-center gap-3">
              <span className="px-3 py-1 text-xs font-bold bg-cyan-500/20 text-cyan-300 border border-cyan-500/40 rounded-full flex items-center gap-1.5">
                <Zap className="w-3.5 h-3.5" />
                EXECUTE NEXT #{primaryItem.sequenceOrder} ({primaryItem.scheduledPeriod})
              </span>
              <span className="text-xs text-slate-400 font-mono">
                Capacity Cost: {primaryItem.capacityCost?.toFixed(1)} hrs
              </span>
            </div>

            <h2 className="text-xl md:text-2xl font-extrabold text-white tracking-tight">
              {primaryItem.title}
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs pt-2">
              <div className="bg-[#0A0D14]/80 border border-slate-800 p-3.5 rounded-xl">
                <span className="text-slate-400 block font-medium mb-1">Expected Outcome</span>
                <span className="text-emerald-400 font-semibold">{primaryItem.expectedOutcome}</span>
              </div>

              <div className="bg-[#0A0D14]/80 border border-slate-800 p-3.5 rounded-xl">
                <span className="text-slate-400 block font-medium mb-1">Schedule Step Score</span>
                <span className="text-cyan-400 font-bold">{primaryItem.priorityScore?.toFixed(2)} / 100</span>
              </div>

              <div className="bg-[#0A0D14]/80 border border-slate-800 p-3.5 rounded-xl">
                <span className="text-slate-400 block font-medium mb-1">Deferral Risk</span>
                <span className="text-rose-400 font-semibold">{primaryItem.deferralRisk}</span>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Schedule Scorecard */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Capacity Score</span>
            <Clock className="w-4 h-4 text-cyan-400" />
          </div>
          <div className="text-2xl font-extrabold text-cyan-400 mt-2">
            {activeSchedule?.capacityScore?.toFixed(1) || '0.0'}%
          </div>
          <div className="text-[11px] text-slate-500 mt-1">
            {activeSchedule?.scheduledActions || 0} of {activeSchedule?.totalActions || 0} Actions Scheduled
          </div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Risk Protection</span>
            <ShieldCheck className="w-4 h-4 text-emerald-400" />
          </div>
          <div className="text-2xl font-extrabold text-emerald-400 mt-2">
            {activeSchedule?.riskScore?.toFixed(1) || '0.0'} / 100
          </div>
          <div className="text-[11px] text-slate-500 mt-1">Safety-Critical Priority Override</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Impact Score</span>
            <Zap className="w-4 h-4 text-purple-400" />
          </div>
          <div className="text-2xl font-extrabold text-purple-400 mt-2">
            {activeSchedule?.impactScore?.toFixed(1) || '0.0'} / 100
          </div>
          <div className="text-[11px] text-slate-500 mt-1">Expected Financial Benefit</div>
        </div>

        <div className="bg-[#121622] border border-slate-800 p-5 rounded-2xl">
          <div className="text-xs font-medium text-slate-400 flex items-center justify-between">
            <span>Urgency Score</span>
            <AlertTriangle className="w-4 h-4 text-amber-400" />
          </div>
          <div className="text-2xl font-extrabold text-amber-400 mt-2">
            {activeSchedule?.urgencyScore?.toFixed(1) || '0.0'} / 100
          </div>
          <div className="text-[11px] text-slate-500 mt-1">Time Sensitivity</div>
        </div>
      </div>

      {/* Main Sections: Scheduled vs Deferred */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Scheduled Actions Section */}
        <div className="space-y-4">
          <h3 className="text-lg font-semibold text-white flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-emerald-400" />
            Scheduled Execution Sequence ({scheduledItems.length})
          </h3>

          <div className="space-y-4">
            {scheduledItems.map((item: BackendFinancialExecutionScheduleItemDTO) => (
              <div
                key={item.id}
                className="bg-[#121622] border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition-all space-y-3 shadow-lg"
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <span className="w-6 h-6 rounded-full bg-cyan-500/20 text-cyan-400 text-xs font-bold flex items-center justify-center">
                      #{item.sequenceOrder}
                    </span>
                    <span className="px-2 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded">
                      {item.scheduledPeriod}
                    </span>
                    <span className="px-2 py-0.5 text-xs font-semibold bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full">
                      SCHEDULED
                    </span>
                  </div>

                  <span className="text-xs text-slate-400 font-mono">
                    Score: <strong className="text-cyan-400">{item.priorityScore?.toFixed(2)}</strong>
                  </span>
                </div>

                <h4 className="font-bold text-white text-base">{item.title}</h4>

                <div className="text-xs text-slate-300 bg-[#0A0D14] p-3 rounded-lg border border-slate-800/60 space-y-1">
                  <div>
                    <span className="text-slate-400">Expected Outcome:</span>{' '}
                    <span className="text-emerald-400 font-medium">{item.expectedOutcome}</span>
                  </div>
                  <div className="text-[11px] text-slate-500 font-mono">
                    {item.evidenceMetrics}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Deferred Actions Section */}
        <div className="space-y-4">
          <h3 className="text-lg font-semibold text-white flex items-center gap-2">
            <Clock className="w-5 h-5 text-amber-400" />
            Safely Deferred Actions ({deferredItems.length})
          </h3>

          <div className="space-y-4">
            {deferredItems.length === 0 ? (
              <div className="bg-[#121622] border border-slate-800 rounded-xl p-8 text-center text-slate-400 text-sm">
                No actions currently deferred. All eligible actions scheduled within capacity budget.
              </div>
            ) : (
              deferredItems.map((item: BackendFinancialExecutionScheduleItemDTO) => (
                <div
                  key={item.id}
                  className="bg-[#121622] border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition-all space-y-3 opacity-80"
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-2">
                      <span className="px-2 py-0.5 text-xs font-mono bg-slate-800 text-slate-400 rounded">
                        {item.scheduledPeriod}
                      </span>
                      <span className="px-2 py-0.5 text-xs font-semibold bg-amber-500/10 text-amber-400 border border-amber-500/30 rounded-full">
                        DEFERRED
                      </span>
                    </div>

                    <span className="text-xs text-slate-400 font-mono">
                      Deferral Risk Score: <strong className="text-amber-400">{item.deferralScore?.toFixed(2)}</strong>
                    </span>
                  </div>

                  <h4 className="font-bold text-slate-300 text-base">{item.title}</h4>

                  <div className="text-xs text-slate-400 bg-[#0A0D14] p-3 rounded-lg border border-slate-800/60 space-y-1">
                    <div>
                      <span className="text-slate-500">Deferral Explanation:</span>{' '}
                      <span className="text-amber-300">{item.deferralRisk}</span>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
