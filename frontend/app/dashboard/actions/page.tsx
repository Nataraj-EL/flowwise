'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantActions,
  dismissAction,
  resolveAction,
  BackendActionSummaryDTO,
  BackendFinancialActionDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Zap,
  AlertTriangle,
  ShieldAlert,
  CheckCircle2,
  XCircle,
  FileCheck,
  RefreshCw,
  Layers,
  ArrowRight,
  TrendingUp,
  Award,
  Sparkles,
} from 'lucide-react';

export default function ActionsPage() {
  const [summary, setSummary] = useState<BackendActionSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [filterSeverity, setFilterSeverity] = useState<string>('ALL');

  const loadActions = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantActions(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Financial Action API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadActions();
  }, []);

  const handleDismiss = async (actionId: number) => {
    try {
      const updated = await dismissAction(actionId);
      setSummary((prev) => {
        if (!prev) return null;
        const updatedList = prev.actions.map((a) => (a.id === actionId ? updated : a));
        const openCount = updatedList.filter((a) => a.status === 'OPEN').length;
        return { ...prev, openCount, actions: updatedList };
      });
    } catch (err: any) {
      setError(err.message || 'Failed to dismiss action');
    }
  };

  const handleResolve = async (actionId: number) => {
    try {
      const updated = await resolveAction(actionId);
      setSummary((prev) => {
        if (!prev) return null;
        const updatedList = prev.actions.map((a) => (a.id === actionId ? updated : a));
        const openCount = updatedList.filter((a) => a.status === 'OPEN').length;
        return { ...prev, openCount, actions: updatedList };
      });
    } catch (err: any) {
      setError(err.message || 'Failed to resolve action');
    }
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-28 bg-[#0E1116] border border-white/10"></div>
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
              ACTION CENTER SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Financial Action Service (`http://localhost:8080/api/v1/merchants/1/actions`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadActions} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const filteredActions = summary.actions.filter((a) => {
    if (filterSeverity === 'ALL') return true;
    if (filterSeverity === 'OPEN') return a.status === 'OPEN';
    return a.severity === filterSeverity;
  });

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Financial Action Center
            </h1>
            <Badge variant="demo">PRIORITIZED RECOMMENDATIONS</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Evidence-backed financial recommendations derived from Cash Flow, Health, Temporal & Forecast engines
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs">
          <Zap className="w-4 h-4 text-[#00F0FF]" />
          {summary.openCount} OPEN ADVISORY ACTIONS
        </Badge>
      </div>

      {/* Priority Summary Cards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card className="space-y-2 border-rose-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            Critical High Priority
          </span>
          <div className="text-3xl font-bold text-rose-400">{summary.highPriorityCount}</div>
          <div className="text-[11px] text-slate-500">Short-term payables & runway alerts</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <AlertTriangle className="w-3.5 h-3.5 text-amber-400" />
            Medium Priority Warnings
          </span>
          <div className="text-3xl font-bold text-amber-400">{summary.mediumPriorityCount}</div>
          <div className="text-[11px] text-slate-500">Expense spikes & category shifts</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Sparkles className="w-3.5 h-3.5 text-[#00E599]" />
            Low Priority Opportunities
          </span>
          <div className="text-3xl font-bold text-[#00E599]">{summary.lowPriorityCount}</div>
          <div className="text-[11px] text-slate-500">Working capital optimization</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Layers className="w-3.5 h-3.5 text-[#00F0FF]" />
            Total Open Actions
          </span>
          <div className="text-3xl font-bold text-white">{summary.openCount} / {summary.totalActions}</div>
          <div className="text-[11px] text-slate-500">Advisory recommendations</div>
        </Card>
      </section>

      {/* Filter Tabs */}
      <div className="flex items-center gap-2 border-b border-white/10 pb-3 overflow-x-auto text-xs">
        {['ALL', 'OPEN', 'HIGH', 'MEDIUM', 'LOW'].map((tab) => (
          <button
            key={tab}
            onClick={() => setFilterSeverity(tab)}
            className={`px-3 py-1.5 font-bold uppercase transition-colors shrink-0 ${
              filterSeverity === tab
                ? 'bg-[#00F0FF] text-black shadow-[0_0_10px_rgba(0,240,255,0.3)]'
                : 'text-slate-400 hover:text-white bg-white/5 border border-white/10'
            }`}
          >
            {tab === 'ALL' ? 'All Actions' : tab === 'OPEN' ? 'Open Only' : `${tab} Priority`}
          </button>
        ))}
      </div>

      {/* Action Cards List */}
      {filteredActions.length === 0 ? (
        <Card variant="glow-emerald" className="p-8 text-center space-y-4">
          <div className="w-12 h-12 bg-emerald-500/10 border border-emerald-500/40 text-[#00E599] mx-auto flex items-center justify-center">
            <CheckCircle2 className="w-6 h-6" />
          </div>
          <div className="space-y-1">
            <h3 className="text-lg font-bold text-white uppercase">All Priority Actions Addressed</h3>
            <p className="text-xs text-slate-300 font-sans max-w-md mx-auto">
              Your merchant financial signals are currently healthy with zero unresolved open risks in this category.
            </p>
          </div>
        </Card>
      ) : (
        <div className="space-y-4">
          {filteredActions.map((action) => {
            const isHigh = action.severity === 'HIGH';
            const isMed = action.severity === 'MEDIUM';

            return (
              <Card
                key={action.id}
                className={`space-y-4 border transition-all ${
                  action.status !== 'OPEN'
                    ? 'opacity-60 bg-[#060709] border-white/10'
                    : isHigh
                    ? 'border-rose-500/40 bg-[#0C080A]'
                    : isMed
                    ? 'border-amber-500/30 bg-[#0C0A07]'
                    : 'border-emerald-500/30 bg-[#070B09]'
                }`}
              >
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-white/10 pb-3">
                  <div className="flex items-center gap-2">
                    <Badge variant={isHigh ? 'rose' : isMed ? 'amber' : 'emerald'} className="text-[10px] py-1 px-2.5">
                      {action.severity} PRIORITY
                    </Badge>
                    <Badge variant="cyan" className="text-[9px]">
                      {action.category}
                    </Badge>
                  </div>

                  <div className="flex items-center gap-2">
                    <span className="text-[10px] text-slate-500 font-mono">Status:</span>
                    <Badge
                      variant={
                        action.status === 'RESOLVED'
                          ? 'emerald'
                          : action.status === 'DISMISSED'
                          ? 'neutral'
                          : 'cyan'
                      }
                      className="text-[9px] uppercase"
                    >
                      {action.status}
                    </Badge>
                  </div>
                </div>

                <div className="space-y-2">
                  <h3 className="text-base sm:text-lg font-bold text-white tracking-tight">
                    {action.title}
                  </h3>
                  <p className="text-xs text-slate-300 leading-relaxed font-sans">
                    {action.explanation}
                  </p>
                </div>

                {/* Supporting Financial Evidence */}
                {action.supportingEvidence && (
                  <div className="p-3 bg-[#050608] border border-white/10 text-xs font-mono space-y-1">
                    <span className="text-[10px] text-slate-500 uppercase font-bold block">
                      Supporting Financial Evidence
                    </span>
                    <p className="text-[#00F0FF] text-[11px]">{action.supportingEvidence}</p>
                  </div>
                )}

                {/* Recommended Next Step Box */}
                <div className="p-3.5 bg-[#080E14] border border-[#00F0FF]/30 space-y-1.5">
                  <div className="flex items-center gap-1.5 text-xs font-bold text-white uppercase">
                    <ArrowRight className="w-4 h-4 text-[#00F0FF]" />
                    <span>Recommended Next Step</span>
                  </div>
                  <p className="text-xs text-slate-200 font-sans pl-5 leading-relaxed">
                    {action.recommendedStep}
                  </p>
                </div>

                {/* Controls: Resolve & Dismiss */}
                {action.status === 'OPEN' && (
                  <div className="flex items-center justify-end gap-3 pt-2 border-t border-white/10">
                    <Button
                      variant="emerald"
                      size="sm"
                      onClick={() => handleResolve(action.id)}
                      className="gap-1.5"
                    >
                      <CheckCircle2 className="w-3.5 h-3.5" />
                      <span>Mark Resolved</span>
                    </Button>

                    <Button
                      variant="secondary"
                      size="sm"
                      onClick={() => handleDismiss(action.id)}
                      className="gap-1.5 text-slate-400 hover:text-white"
                    >
                      <XCircle className="w-3.5 h-3.5" />
                      <span>Dismiss</span>
                    </Button>
                  </div>
                )}
              </Card>
            );
          })}
        </div>
      )}
    </div>
  );
}
