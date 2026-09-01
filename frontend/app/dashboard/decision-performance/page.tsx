'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantDecisionCalibration,
  fetchLatestDecisionPerformance,
  BackendDecisionCalibrationDTO,
  BackendOptionPerformanceDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  BarChart3,
  Award,
  TrendingUp,
  AlertTriangle,
  CheckCircle2,
  RefreshCw,
  Info,
  ShieldCheck,
  Target,
  Layers,
  HelpCircle,
} from 'lucide-react';

export default function DecisionPerformancePage() {
  const [calibration, setCalibration] = useState<BackendDecisionCalibrationDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantDecisionCalibration(1);
      setCalibration(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Decision Calibration API');
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
          {[1, 2, 3, 4].map((i) => (
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
              CALIBRATION ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Decision Calibration API (`http://localhost:8080/api/v1/merchants/1/decision-calibration`).
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
              Decision Performance & Calibration
            </h1>
            <Badge variant="demo">CALIBRATION ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Empirical recommendation fidelity tracking and bounded scoring factor calibration across completed decision outcomes
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Calibration
        </Button>
      </div>

      {/* Main Performance Scorecard Banner */}
      {calibration && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Overall Recommendation Success Rate</span>
              <span className="text-3xl font-black text-[#00F0FF]">{calibration.overallSuccessRatePct}%</span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Total Evaluated Outcomes</span>
              <span className="text-3xl font-bold text-white">{calibration.totalEvaluatedDecisions} <span className="text-xs text-slate-400 font-normal">Decisions</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Confidence Rating</span>
              <Badge
                variant={calibration.confidenceLevel === 'HIGH' ? 'emerald' : calibration.confidenceLevel === 'MODERATE' ? 'cyan' : 'amber'}
                className="text-xs font-bold gap-1 mt-1"
              >
                <ShieldCheck className="w-3.5 h-3.5" />
                {calibration.confidenceLevel}
              </Badge>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Data Completeness</span>
              <span className="text-3xl font-bold text-emerald-400">{calibration.dataCompletenessPct}%</span>
            </div>
          </div>

          <div className="space-y-2 pt-1">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Calibration Summary: </strong>
              {calibration.summaryInsight}
            </p>
          </div>
        </Card>
      )}

      {/* Option Performance & Multiplier Matrix */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <BarChart3 className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Option-Wise Performance Calibration Factor Matrix</h2>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {calibration?.optionPerformances?.map((opt) => (
            <Card key={opt.optionKey} className="p-5 space-y-4 border-white/10 bg-[#05080E]/50">
              <div className="flex items-center justify-between border-b border-white/10 pb-3">
                <Badge variant="cyan" className="text-[10px] font-bold">{opt.optionKey}</Badge>
                <Badge
                  variant={opt.accuracyStatus === 'ACCURATE' ? 'emerald' : opt.accuracyStatus === 'UNCALIBRATED' ? 'demo' : 'amber'}
                  className="text-[9px]"
                >
                  {opt.accuracyStatus}
                </Badge>
              </div>

              <div className="space-y-2 text-xs font-mono">
                <div className="flex justify-between">
                  <span className="text-slate-400">Sample Size:</span>
                  <span className="text-white font-bold">{opt.totalSampleCount}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-400">Positive Outcomes:</span>
                  <span className="text-[#00E599] font-bold">{opt.positiveOutcomeCount}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-400">Negative Outcomes:</span>
                  <span className="text-rose-400 font-bold">{opt.negativeOutcomeCount}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-400">Success Rate:</span>
                  <span className="text-[#00F0FF] font-bold">{opt.successRatePct}%</span>
                </div>
                <div className="flex justify-between pt-2 border-t border-white/10">
                  <span className="text-slate-400">Calibrated Multiplier:</span>
                  <span className="text-emerald-400 font-bold text-sm">{opt.calibrationMultiplier}x</span>
                </div>
              </div>

              <div className="text-[10px] text-slate-500 font-sans">
                {opt.totalSampleCount < 3
                  ? 'Requires min 3 samples before adjusting multiplier'
                  : 'Multiplier bounded between 0.80x and 1.20x'}
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Recent Evaluated Decisions List */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Layers className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Evaluated Merchant Decisions Log</h2>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {calibration?.recentDecisions?.map((dec) => (
            <Card key={dec.id} className="p-4 flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-white/10 bg-[#05080E]/40 text-xs font-mono">
              <div className="space-y-1">
                <div className="flex items-center gap-2">
                  <span className="text-white font-bold">{dec.title}</span>
                  <Badge variant="demo" className="text-[9px]">{dec.decisionType}</Badge>
                </div>
                <p className="text-slate-400 font-sans text-[11px]">Recommendation: {dec.recommendation || 'N/A'}</p>
              </div>

              <div className="flex items-center gap-3 shrink-0">
                <Badge variant={dec.decisionStatus === 'COMPLETED' ? 'emerald' : 'cyan'} className="text-[10px]">
                  {dec.decisionStatus}
                </Badge>
                <Badge variant={dec.outcomeStatus === 'POSITIVE' ? 'emerald' : dec.outcomeStatus === 'NEGATIVE' ? 'rose' : 'demo'} className="text-[10px]">
                  Outcome: {dec.outcomeStatus}
                </Badge>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Governance & Immutable History
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise recommendation calibration measures empirical fidelity across completed merchant decisions. Calibration multipliers tune future decision scoring models without rewriting past recommendations, decision records, or ledger accounts.
        </p>
      </section>
    </div>
  );
}
