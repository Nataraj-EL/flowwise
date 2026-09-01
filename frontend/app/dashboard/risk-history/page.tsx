'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantRiskHistory,
  evaluateMerchantRiskTrajectory,
  BackendRiskTrajectorySummaryDTO,
  BackendRiskTrajectoryDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Activity,
  AlertTriangle,
  TrendingUp,
  TrendingDown,
  CheckCircle2,
  RefreshCw,
  Info,
  ShieldCheck,
  Clock,
  Layers,
  HelpCircle,
} from 'lucide-react';

export default function RiskHistoryPage() {
  const [summary, setSummary] = useState<BackendRiskTrajectorySummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedDirection, setSelectedDirection] = useState<string>('ALL');

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantRiskHistory(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Risk Trajectory API');
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
              TRAJECTORY ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Risk Trajectory API (`http://localhost:8080/api/v1/merchants/1/risk-history`).
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

  const filteredTrajectories = summary?.trajectories?.filter((t) => {
    if (selectedDirection === 'ALL') return true;
    return t.trajectoryDirection === selectedDirection;
  });

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Risk Trajectory & Monitoring
            </h1>
            <Badge variant="demo">RISK TRAJECTORY</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic historical risk evolution tracking with 5% hysteresis boundary filter and severity transition velocity
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Trajectory Engine
        </Button>
      </div>

      {/* Main Composite Trajectory Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Composite Trajectory Status</span>
              <Badge
                variant={summary.compositeTrajectoryStatus === 'WORSENING' ? 'rose' : summary.compositeTrajectoryStatus === 'IMPROVING' ? 'emerald' : 'cyan'}
                className="text-xs font-bold gap-1 mt-1"
              >
                <Activity className="w-3.5 h-3.5" />
                {summary.compositeTrajectoryStatus}
              </Badge>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Tracked Risk Trajectories</span>
              <span className="text-3xl font-bold text-white">{summary.totalTrackedRisks} <span className="text-xs text-slate-400 font-normal">Active Trajectories</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Trajectory Breakdown</span>
              <div className="flex items-center gap-2 font-bold text-sm pt-1">
                <span className="text-rose-400">{summary.worseningCount} Worsening</span>
                <span className="text-slate-500">|</span>
                <span className="text-slate-300">{summary.stableCount} Stable</span>
                <span className="text-slate-500">|</span>
                <span className="text-emerald-400">{summary.improvingCount} Improving</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Avg Resolution Velocity</span>
              <div className="flex items-baseline gap-1 text-cyan-300 font-bold text-sm pt-1">
                <Clock className="w-3.5 h-3.5" />
                <span>{summary.avgResolutionTimeHours} hrs</span>
              </div>
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

      {/* Trajectory Filter Pills */}
      <div className="flex items-center gap-2 overflow-x-auto pb-2 font-mono text-xs">
        {['ALL', 'WORSENING', 'STABLE', 'IMPROVING', 'RESOLVED'].map((dir) => (
          <button
            key={dir}
            onClick={() => setSelectedDirection(dir)}
            className={`px-3 py-1.5 border transition-colors ${
              selectedDirection === dir
                ? 'bg-[#00F0FF]/10 border-[#00F0FF] text-[#00F0FF] font-bold'
                : 'bg-[#05080E] border-white/10 text-slate-400 hover:text-white'
            }`}
          >
            {dir} TRAJECTORIES
          </button>
        ))}
      </div>

      {/* Risk Trajectories Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Financial Risk Evolution Snapshots</h2>
        </div>

        <div className="grid grid-cols-1 gap-4">
          {filteredTrajectories?.map((traj) => (
            <Card key={traj.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                <div className="space-y-1">
                  <div className="flex items-center gap-2 flex-wrap">
                    <Badge
                      variant={traj.trajectoryDirection === 'WORSENING' ? 'rose' : traj.trajectoryDirection === 'IMPROVING' ? 'emerald' : 'cyan'}
                      className="text-[10px] font-bold"
                    >
                      {traj.trajectoryDirection}
                    </Badge>
                    <Badge variant="cyan" className="text-[10px]">{traj.riskType}</Badge>
                    <Badge variant="demo" className="text-[10px]">{traj.riskKey}</Badge>
                  </div>
                  <h3 className="text-base font-bold text-white uppercase pt-1">Severity Movement: {traj.severityTransition}</h3>
                </div>

                <div className="flex items-center gap-2 shrink-0">
                  <div className="p-2 bg-[#080E18] border border-[#00F0FF]/20 text-right">
                    <span className="text-[10px] text-slate-500 block">Escalation Velocity:</span>
                    <span className="text-amber-400 font-bold text-xs">{traj.escalationVelocity}x / period</span>
                  </div>
                </div>
              </div>

              {/* Snapshot Metrics Grid */}
              <div className="grid grid-cols-1 sm:grid-cols-4 gap-3 text-xs font-mono">
                <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                  <span className="text-[10px] text-slate-500 block">Baseline Value:</span>
                  <span className="text-slate-300 font-bold">₹{traj.baselineValue.toLocaleString('en-IN')}</span>
                </div>
                <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                  <span className="text-[10px] text-slate-500 block">Current Value:</span>
                  <span className="text-white font-bold">₹{traj.currentValue.toLocaleString('en-IN')}</span>
                </div>
                <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                  <span className="text-[10px] text-slate-500 block">Score Delta:</span>
                  <span className={traj.scoreDelta > 0 ? 'text-rose-400 font-bold' : 'text-emerald-400 font-bold'}>
                    {traj.scoreDelta > 0 ? '+' : ''}₹{traj.scoreDelta.toLocaleString('en-IN')}
                  </span>
                </div>
                <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                  <span className="text-[10px] text-slate-500 block">Observations / Recurrence:</span>
                  <span className="text-cyan-300 font-bold">{traj.observedSnapshotsCount} Snapshots | {traj.recurrenceCount} Recurrences</span>
                </div>
              </div>

              <div className="flex justify-between items-center text-[10px] text-slate-500 pt-1">
                <span>Evaluated: <strong>{new Date(traj.evaluatedAt).toLocaleTimeString()}</strong></span>
                <span>Deterministic hysteresis filter | ACTUAL vs ESTIMATE</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Risk Escalation Directives */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <AlertTriangle className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Advisory Escalation Mitigation Actions</h2>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {summary?.escalationActions?.map((act) => (
            <Card key={act.id} className="p-4 flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-white/10 bg-[#05080E]/40 text-xs font-mono">
              <div className="space-y-1">
                <div className="flex items-center gap-2">
                  <Badge variant={act.severity === 'HIGH' ? 'rose' : 'amber'} className="text-[9px]">
                    {act.severity}
                  </Badge>
                  <span className="text-white font-bold">{act.title}</span>
                </div>
                <p className="text-slate-400 font-sans text-[11px]">{act.explanation}</p>
              </div>

              <div className="p-2 bg-[#080E18] border border-[#00F0FF]/20 text-right shrink-0">
                <span className="text-[10px] text-slate-500 block">Recommended Step:</span>
                <span className="text-cyan-300 font-bold text-[11px]">{act.recommendedStep}</span>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Advisory Governance Disclaimer */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Governance & Hysteresis Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Risk Trajectory engine compares consecutive risk evaluation snapshots using an explicit 5.00% hysteresis filter. Historical evaluation snapshots are immutable and read-only; evaluating risk trajectories does not execute payments, modify accounts, or alter ledger records.
        </p>
      </section>
    </div>
  );
}
