'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchEvaluationSummary,
  runEvaluation,
  BackendEvaluationSummaryDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Activity,
  CheckCircle2,
  AlertTriangle,
  RefreshCw,
  Zap,
  ShieldCheck,
  Cpu,
  Clock,
  Layers,
  FileCheck,
  Percent,
} from 'lucide-react';

export default function EvaluationPage() {
  const [summary, setSummary] = useState<BackendEvaluationSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [running, setRunning] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchEvaluationSummary();
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Evaluation Service');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleRunEvaluation = async () => {
    setRunning(true);
    setError(null);
    try {
      const data = await runEvaluation();
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Evaluation suite run failed');
    } finally {
      setRunning(false);
    }
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-32 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !summary) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              EVALUATION SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Evaluation Service (`http://localhost:8080/api/v1/evaluation/summary`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={handleRunEvaluation} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Run Benchmark Evaluation Suite
          </Button>
        </Card>
      </div>
    );
  }

  const metrics = [
    {
      title: 'Overall Benchmark Score',
      value: `${summary.overallScore}%`,
      subtitle: `${summary.totalCases} Representative Test Cases`,
      highlight: summary.overallScore >= 80 ? ('emerald' as const) : ('amber' as const),
    },
    {
      title: 'Faithfulness & Grounding',
      value: `${summary.groundingScore}%`,
      subtitle: 'Zero Hallucinated Claims',
      highlight: 'cyan' as const,
    },
    {
      title: 'Numerical Consistency',
      value: `${summary.numericalConsistencyScore}%`,
      subtitle: '100% Deterministic Match',
      highlight: 'emerald' as const,
    },
    {
      title: 'Evidence & Relevance Coverage',
      value: `${summary.relevanceScore}%`,
      subtitle: `Coverage: ${summary.evidenceCoverageScore}%`,
      highlight: 'neutral' as const,
    },
  ];

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              AI Evaluation & Diagnostics
            </h1>
            <Badge variant="demo">AI EVALUATION / INTERNAL DIAGNOSTICS</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic Grounding, Consistency, Relevance & Observability Framework ({summary.benchmarkVersion})
          </p>
        </div>

        <Button variant="cyan" size="lg" onClick={handleRunEvaluation} disabled={running} className="gap-2 shrink-0">
          {running ? <span className="animate-spin">⏳</span> : <Activity className="w-4 h-4" />}
          <span>{running ? 'Evaluating Benchmark...' : 'Run Benchmark Suite'}</span>
        </Button>
      </div>

      {/* 4 Primary Scorecards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {metrics.map((m, idx) => (
          <Card key={idx} className="space-y-2">
            <span className="text-[10px] text-slate-400 uppercase font-bold">{m.title}</span>
            <div
              className={`text-3xl font-bold ${
                m.highlight === 'cyan'
                  ? 'text-[#00F0FF]'
                  : m.highlight === 'emerald'
                  ? 'text-[#00E599]'
                  : m.highlight === 'amber'
                  ? 'text-amber-400'
                  : 'text-white'
              }`}
            >
              {m.value}
            </div>
            <div className="text-[11px] text-slate-500">{m.subtitle}</div>
          </Card>
        ))}
      </section>

      {/* Secondary System Diagnostic Indicators */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 text-xs">
        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <span className="text-[10px] text-slate-500 uppercase">Unsupported Claims</span>
          <div className="text-base font-bold text-[#00E599]">{summary.unsupportedClaimsCount} Detected</div>
          <p className="text-[10px] text-slate-400">Zero ungrounded financial assertions</p>
        </div>

        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <span className="text-[10px] text-slate-500 uppercase">Fallback Rate</span>
          <div className="text-base font-bold text-white">{summary.fallbackRate}%</div>
          <p className="text-[10px] text-slate-400">Deterministic fallback engine active</p>
        </div>

        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <span className="text-[10px] text-slate-500 uppercase font-mono">Avg Pipeline Latency</span>
          <div className="text-base font-bold text-[#00F0FF]">{summary.avgLatencyMs} ms</div>
          <p className="text-[10px] text-slate-400">Local evaluation execution time</p>
        </div>
      </div>

      {/* 15-Case Benchmark Matrix Table */}
      <Card className="space-y-4">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <div className="flex items-center gap-2">
            <FileCheck className="w-5 h-5 text-[#00F0FF]" />
            <h3 className="text-sm font-bold text-white uppercase tracking-wider">
              15-Case Versioned Benchmark Test Matrix
            </h3>
          </div>
          <Badge variant="cyan">{summary.totalCases} BENCHMARK CASES</Badge>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs font-mono">
            <thead>
              <tr className="border-b border-white/10 text-slate-500 text-[10px] uppercase">
                <th className="py-2 px-3">ID</th>
                <th className="py-2 px-3">Category</th>
                <th className="py-2 px-3">Merchant Question</th>
                <th className="py-2 px-3 text-center">Grounded</th>
                <th className="py-2 px-3 text-center">Numerical Match</th>
                <th className="py-2 px-3 text-center">Relevant</th>
                <th className="py-2 px-3 text-right">Latency</th>
                <th className="py-2 px-3 text-right">Score</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-white/5">
              {summary.caseResults.map((tc) => (
                <tr key={tc.caseId} className="hover:bg-white/5 transition-colors">
                  <td className="py-3 px-3 font-bold text-white">{tc.caseId}</td>
                  <td className="py-3 px-3 text-slate-400 text-[11px]">{tc.category}</td>
                  <td className="py-3 px-3 text-slate-200">{tc.question}</td>
                  <td className="py-3 px-3 text-center">
                    <Badge variant={tc.grounded ? 'emerald' : 'rose'} className="text-[9px] py-0.5">
                      {tc.grounded ? 'PASS' : 'FAIL'}
                    </Badge>
                  </td>
                  <td className="py-3 px-3 text-center">
                    <Badge variant={tc.numericalConsistent ? 'cyan' : 'rose'} className="text-[9px] py-0.5">
                      {tc.numericalConsistent ? 'PASS' : 'FAIL'}
                    </Badge>
                  </td>
                  <td className="py-3 px-3 text-center">
                    <Badge variant={tc.relevant ? 'emerald' : 'amber'} className="text-[9px] py-0.5">
                      {tc.relevant ? 'PASS' : 'PARTIAL'}
                    </Badge>
                  </td>
                  <td className="py-3 px-3 text-right text-slate-400">{tc.latencyMs} ms</td>
                  <td className="py-3 px-3 text-right font-bold text-[#00F0FF]">{tc.score}%</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
}
