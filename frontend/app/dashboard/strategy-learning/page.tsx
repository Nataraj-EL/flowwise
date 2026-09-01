'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantStrategyLearning,
  evaluateStrategyLearning,
  BackendStrategyLearningSummaryDTO,
  BackendStrategyLearningDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  BrainCircuit,
  AlertTriangle,
  TrendingUp,
  RefreshCw,
  Info,
  ShieldCheck,
  Activity,
  CheckCircle2,
  HelpCircle,
  BarChart3,
  Sparkles,
} from 'lucide-react';

export default function StrategyLearningPage() {
  const [summary, setSummary] = useState<BackendStrategyLearningSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantStrategyLearning(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Strategy Learning API');
    } finally {
      setLoading(false);
    }
  };

  const handleEvaluate = async () => {
    setLoading(true);
    try {
      const data = await evaluateStrategyLearning(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to evaluate strategy learning engine');
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
              STRATEGY LEARNING ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Strategy Learning API (`http://localhost:8080/api/v1/merchants/1/strategy-learning`).
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
              Strategy Learning & Optimization
            </h1>
            <Badge variant="demo">OPTIMIZATION CONSOLE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic learning engine identifying top-performing intervention strategies from historical observed outcomes
          </p>
        </div>

        <Button variant="outline" onClick={handleEvaluate} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Recalibrate Strategy Multipliers
        </Button>
      </div>

      {/* Main Strategy Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Evaluated Strategy Contexts</span>
              <div className="flex items-baseline gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{summary.totalEvaluatedStrategiesCount}</span>
                <span className="text-xs text-slate-400">Contexts</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Top Performing Strategy</span>
              <span className="text-xl font-bold text-emerald-400 block pt-1">{summary.topPerformingInterventionType}</span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">High Confidence Strategies</span>
              <div className="flex items-center gap-2 font-bold text-sm pt-1">
                <span className="text-emerald-400">{summary.highConfidenceCount} High Confidence</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Average Learning Multiplier</span>
              <span className="text-3xl font-bold text-[#00F0FF]">{summary.averageLearningMultiplier}x <span className="text-xs text-slate-400 font-normal">(0.90-1.10x)</span></span>
            </div>
          </div>

          <div className="space-y-2 pt-1">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Engine Summary: </strong>
              {summary.summaryExplanation}
            </p>
          </div>
        </Card>
      )}

      {/* Context -> Intervention Performance Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <BrainCircuit className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Context → Strategy Performance Matrix</h2>
        </div>

        <div className="grid grid-cols-1 gap-6">
          {summary?.learnings?.map((lrn) => (
            <Card key={lrn.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge variant="cyan" className="text-[10px]">{lrn.interventionType}</Badge>
                    <Badge variant="demo" className="text-[10px]">{lrn.contextType}</Badge>
                    <Badge
                      variant={lrn.confidenceStatus === 'HIGH' ? 'emerald' : lrn.confidenceStatus === 'MODERATE' ? 'amber' : 'rose'}
                      className="text-[10px] font-bold"
                    >
                      {lrn.confidenceStatus} CONFIDENCE ({lrn.sampleCount} SAMPLES)
                    </Badge>
                  </div>
                  <h3 className="text-base font-bold text-white uppercase pt-1">{lrn.strategyKey}</h3>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  <div className="p-3 bg-[#080E18] border border-[#00F0FF]/30 text-right">
                    <span className="text-[10px] text-slate-500 uppercase block font-bold">Learning Multiplier</span>
                    <span className="text-2xl font-black text-emerald-400">{lrn.learningMultiplier}x</span>
                  </div>
                  <div className="p-3 bg-[#080E18] border border-white/10 text-right">
                    <span className="text-[10px] text-slate-500 uppercase block font-bold">Effectiveness Score</span>
                    <span className="text-2xl font-bold text-[#00F0FF]">{lrn.effectivenessScore} <span className="text-xs text-slate-400 font-normal">/100</span></span>
                  </div>
                </div>
              </div>

              {/* Details & Assumptions */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs font-mono">
                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Evidence Metrics</span>
                  <p className="text-slate-300">{lrn.evidenceMetrics}</p>
                </div>

                <div className="p-3 bg-[#05080E] border border-white/5 space-y-1">
                  <span className="text-[10px] text-slate-500 block uppercase font-bold">Optimization Assumptions</span>
                  <p className="text-slate-300">{lrn.assumptions}</p>
                </div>
              </div>

              {/* Footer */}
              <div className="p-3 bg-[#080E18] border border-white/5 flex items-center justify-between text-xs font-mono">
                <span className="text-emerald-400 font-bold">Learned from historical observed outcomes (Future Recommendation Calibration Only)</span>
                <span className="text-[10px] text-slate-500">Evaluated: {new Date(lrn.evaluatedAt).toLocaleTimeString()}</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Governance Disclaimer */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Governance & Future Calibration Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Strategy Learning engine calculates bounded strategy performance multipliers (0.90-1.10) to calibrate future intervention recommendations. Multipliers affect future recommendations only; historical interventions, decisions, and scores remain strictly immutable and read-only.
        </p>
      </section>
    </div>
  );
}
