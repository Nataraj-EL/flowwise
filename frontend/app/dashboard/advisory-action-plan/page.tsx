'use client';

import React, { useEffect, useState } from 'react';
import {
  ListOrdered,
  CheckCircle2,
  AlertTriangle,
  RefreshCw,
  Info,
  TrendingUp,
  ShieldCheck,
  Award,
  Zap,
  Target,
  PlayCircle,
  Archive,
  ArrowRight,
  Clock,
  BrainCircuit,
  Lock,
} from 'lucide-react';
import {
  fetchAdvisoryActionPlanSummary,
  evaluateAdvisoryActionPlan,
  activateAdvisoryActionPlan,
  archiveAdvisoryActionPlan,
  BackendAdvisoryActionPlanSummaryDTO,
  BackendAdvisoryActionPlanDTO,
  BackendAdvisoryActionPlanStepDTO,
} from '@/lib/api';

export default function AdvisoryActionPlanPage() {
  const [merchantId] = useState<number>(1);
  const [horizon, setHorizon] = useState<string>('30D');
  const [loading, setLoading] = useState<boolean>(true);
  const [evaluating, setEvaluating] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [summary, setSummary] = useState<BackendAdvisoryActionPlanSummaryDTO | null>(null);
  const [actionNotice, setActionNotice] = useState<string | null>(null);

  const loadData = async (selectedHorizon: string) => {
    try {
      setLoading(true);
      setError(null);
      const data = await fetchAdvisoryActionPlanSummary(merchantId, selectedHorizon);
      setSummary(data);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to load advisory action plan summary');
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

  const handleEvaluatePlan = async () => {
    try {
      setEvaluating(true);
      setActionNotice(null);
      await evaluateAdvisoryActionPlan(merchantId, horizon);
      setActionNotice(`Re-evaluated advisory action plan for ${horizon} horizon.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to evaluate advisory action plan');
    } finally {
      setEvaluating(false);
    }
  };

  const handleActivate = async (planId: number) => {
    try {
      setActionNotice(null);
      await activateAdvisoryActionPlan(merchantId, planId);
      setActionNotice(`Activated advisory action plan #${planId}.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to activate action plan');
    }
  };

  const handleArchive = async (planId: number) => {
    try {
      setActionNotice(null);
      await archiveAdvisoryActionPlan(merchantId, planId);
      setActionNotice(`Archived advisory action plan #${planId}.`);
      loadData(horizon);
    } catch (err: any) {
      console.error(err);
      setError(err.message || 'Failed to archive action plan');
    }
  };

  const getReadinessBadge = (status: string) => {
    switch (status) {
      case 'READY':
        return 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30';
      case 'BLOCKED':
        return 'bg-rose-500/10 text-rose-400 border-rose-500/30';
      case 'REVIEW_REQUIRED':
        return 'bg-amber-500/10 text-amber-400 border-amber-500/30';
      default:
        return 'bg-slate-500/10 text-slate-400 border-slate-500/30';
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-8 flex items-center justify-center">
        <div className="flex items-center gap-3 text-emerald-400">
          <RefreshCw className="w-6 h-6 animate-spin" />
          <span className="font-medium text-lg">Sequencing Advisory Action Steps & Readiness...</span>
        </div>
      </div>
    );
  }

  const activePlan: BackendAdvisoryActionPlanDTO | null = summary?.activePlan || null;
  const nextStep: BackendAdvisoryActionPlanStepDTO | null = activePlan?.steps && activePlan.steps.length > 0 ? activePlan.steps[0] : null;

  return (
    <div className="min-h-screen bg-[#0A0D14] text-slate-100 p-6 md:p-8 space-y-8 font-sans">
      {/* Top Banner & Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-6">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-2.5 bg-emerald-500/10 border border-emerald-500/20 rounded-xl text-emerald-400">
              <ListOrdered className="w-7 h-7" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-2xl md:text-3xl font-bold tracking-tight text-white">
                  Advisory Action Sequencing & Execution Readiness
                </h1>
                <span className="px-2.5 py-0.5 text-xs font-semibold bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full">
                  ADVISORY_ACTION_PLAN
                </span>
              </div>
              <p className="text-slate-400 text-sm mt-1">
                Converts decision portfolio items into ordered, dependency-aware action steps to guide merchant execution.
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
                    ? 'bg-emerald-600 text-white shadow-md'
                    : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'
                }`}
              >
                {h}
              </button>
            ))}
          </div>

          <button
            onClick={handleEvaluatePlan}
            disabled={evaluating}
            className="flex items-center gap-2 bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-500 hover:to-teal-500 text-white px-4 py-2 rounded-xl text-xs font-semibold shadow-lg shadow-emerald-900/30 transition-all"
          >
            <RefreshCw className={`w-4 h-4 ${evaluating ? 'animate-spin' : ''}`} />
            Sequence Actions
          </button>
        </div>
      </div>

      {/* Governance Notice Banner */}
      <div className="bg-emerald-950/20 border border-emerald-500/30 rounded-xl p-4 flex items-start gap-3 text-emerald-300 text-sm">
        <Info className="w-5 h-5 text-emerald-400 shrink-0 mt-0.5" />
        <div>
          <span className="font-semibold text-emerald-300">ADVISORY ONLY — NO AUTOMATIC EXECUTION:</span>{' '}
          Action plan steps are strictly read-only recommendations (`ADVISORY_ACTION_PLAN`). Flowwise never executes payments, transfers, or account state changes automatically.
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

      {/* Next Best Action Hero Card */}
      {activePlan && nextStep && (
        <div className="bg-gradient-to-br from-[#121622] via-[#0F1420] to-[#0A0D14] border border-emerald-500/30 rounded-2xl p-6 md:p-8 space-y-6 shadow-2xl relative overflow-hidden">
          <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-4">
            <div className="space-y-1">
              <div className="flex items-center gap-2">
                <span className="px-3 py-1 text-xs font-bold bg-emerald-500/20 text-emerald-300 border border-emerald-500/40 rounded-full flex items-center gap-1.5">
                  <ArrowRight className="w-3.5 h-3.5 text-emerald-400" /> NEXT BEST ACTION — STEP #1
                </span>
                <span className="px-2.5 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded-md">
                  {activePlan.horizon} Horizon
                </span>
              </div>
              <h2 className="text-xl md:text-2xl font-extrabold text-white mt-2">
                {activePlan.primaryNextAction}
              </h2>
            </div>

            <div className="flex items-center gap-3">
              <div className="text-right">
                <span className="text-xs text-slate-400 block">Overall Readiness Score</span>
                <span className="text-3xl font-black text-emerald-400">{activePlan.overallReadinessScore?.toFixed(2)} / 100</span>
              </div>

              {activePlan.status === 'ACTIVE' ? (
                <button
                  onClick={() => handleArchive(activePlan.id)}
                  className="p-2 bg-slate-800 hover:bg-slate-700 text-slate-300 rounded-xl text-xs flex items-center gap-1.5"
                  title="Archive Plan"
                >
                  <Archive className="w-4 h-4" /> Archive
                </button>
              ) : (
                <button
                  onClick={() => handleActivate(activePlan.id)}
                  className="p-2 bg-emerald-600 hover:bg-emerald-500 text-white rounded-xl text-xs font-medium flex items-center gap-1.5"
                  title="Activate Plan"
                >
                  <PlayCircle className="w-4 h-4" /> Activate
                </button>
              )}
            </div>
          </div>

          {/* Action Step Readiness Summary Scorecard */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Total Steps</span>
              <span className="text-2xl font-extrabold text-white">{activePlan.totalStepsCount}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Ready Steps</span>
              <span className="text-2xl font-extrabold text-emerald-400">{activePlan.readyStepsCount}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Blocked Steps</span>
              <span className="text-2xl font-extrabold text-rose-400">{activePlan.blockedStepsCount}</span>
            </div>
            <div className="bg-[#0A0D14]/80 border border-slate-800 p-4 rounded-xl">
              <span className="text-xs text-slate-400 block mb-1">Expected Benefit</span>
              <span className="text-xs font-bold text-emerald-300">{activePlan.expectedBenefit}</span>
            </div>
          </div>
        </div>
      )}

      {/* Ordered Action Steps Timeline */}
      <div className="space-y-6">
        <h3 className="text-lg font-semibold text-white flex items-center gap-2">
          <Clock className="w-5 h-5 text-emerald-400" />
          Ordered Action Timeline ({horizon})
        </h3>

        <div className="space-y-4">
          {activePlan?.steps?.map((step: BackendAdvisoryActionPlanStepDTO) => (
            <div
              key={step.id}
              className="bg-[#121622] border border-slate-800 rounded-xl p-6 hover:border-emerald-500/40 transition-all space-y-4 shadow-lg"
            >
              <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800/80 pb-3">
                <div className="flex items-center gap-3">
                  <span className="w-8 h-8 rounded-full bg-emerald-500/20 border border-emerald-500/40 text-emerald-300 flex items-center justify-center font-bold text-sm font-mono shrink-0">
                    #{step.stepNumber}
                  </span>
                  <div>
                    <div className="flex items-center gap-2">
                      <span className={`px-2.5 py-0.5 text-xs font-semibold border rounded-full ${getReadinessBadge(step.readinessStatus)}`}>
                        {step.readinessStatus}
                      </span>
                      <span className="px-2 py-0.5 text-xs font-mono bg-slate-800 text-slate-300 rounded-md">
                        {step.actionType}
                      </span>
                    </div>
                    <h4 className="font-bold text-white text-base mt-1">{step.title}</h4>
                  </div>
                </div>

                <div className="text-right">
                  <span className="text-xs text-slate-400 block">Step Score</span>
                  <span className="text-xl font-black text-emerald-400">{step.stepScore?.toFixed(2)} / 100</span>
                </div>
              </div>

              <p className="text-xs text-slate-400 leading-relaxed">{step.description}</p>

              {/* Step Prerequisites & Expected Outcome */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs">
                <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/60">
                  <span className="text-slate-400 block font-medium mb-0.5">Prerequisites</span>
                  <span className="text-slate-200">{step.prerequisites}</span>
                </div>
                <div className="bg-[#0A0D14] p-3 rounded-lg border border-slate-800/60">
                  <span className="text-slate-400 block font-medium mb-0.5">Expected Outcome</span>
                  <span className="text-emerald-300 font-semibold">{step.expectedOutcome}</span>
                </div>
              </div>

              <div className="text-[10px] font-mono text-slate-400 bg-[#0A0D14] p-2.5 rounded-lg border border-slate-800/60 leading-relaxed">
                {step.evidenceMetrics}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
