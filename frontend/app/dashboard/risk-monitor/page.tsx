'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantRiskMonitor,
  evaluateMerchantRisks,
  acknowledgeRiskAlert,
  resolveRiskAlert,
  BackendRiskMonitorSummaryDTO,
  BackendRiskAlertDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  ShieldAlert,
  AlertTriangle,
  TrendingUp,
  CheckCircle2,
  RefreshCw,
  Info,
  ShieldCheck,
  Check,
  ChevronDown,
  ChevronUp,
  HelpCircle,
  Activity,
  Layers,
} from 'lucide-react';

export default function RiskMonitorPage() {
  const [summary, setSummary] = useState<BackendRiskMonitorSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedSeverity, setSelectedSeverity] = useState<string>('ALL');
  const [expandedId, setExpandedId] = useState<number | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantRiskMonitor(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Risk Detection API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAcknowledge = async (alertId: number) => {
    try {
      await acknowledgeRiskAlert(1, alertId);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to acknowledge alert');
    }
  };

  const handleResolve = async (alertId: number) => {
    try {
      await resolveRiskAlert(1, alertId);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to resolve alert');
    }
  };

  const toggleExpand = (id: number) => {
    setExpandedId(expandedId === id ? null : id);
  };

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
              RISK ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Risk Detection API (`http://localhost:8080/api/v1/merchants/1/risk-monitor`).
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

  const filteredAlerts = summary?.alerts?.filter((a) => {
    if (selectedSeverity === 'ALL') return true;
    return a.severity === selectedSeverity;
  });

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Early Financial Risk Detection
            </h1>
            <Badge variant="demo">RISK MONITOR</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic early-warning risk monitoring across liquidity, cash-flow, receivables, payables, working capital, goals, and decision performance
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Risk Engine
        </Button>
      </div>

      {/* Main Composite Risk Health Scorecard */}
      {summary && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="grid grid-cols-1 sm:grid-cols-4 gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Composite Risk Health Score</span>
              <div className="flex items-baseline gap-2">
                <span className="text-3xl font-black text-[#00F0FF]">{summary.compositeRiskHealthScore}</span>
                <span className="text-xs text-slate-400">/100</span>
              </div>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Overall Risk Status</span>
              <Badge
                variant={summary.overallRiskLevel === 'LOW_RISK' ? 'emerald' : summary.overallRiskLevel === 'MODERATE_RISK' ? 'cyan' : 'amber'}
                className="text-xs font-bold gap-1 mt-1"
              >
                <ShieldCheck className="w-3.5 h-3.5" />
                {summary.overallRiskLevel}
              </Badge>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Open Risk Alerts</span>
              <span className="text-3xl font-bold text-white">{summary.openCount} <span className="text-xs text-slate-400 font-normal">Active Alerts</span></span>
            </div>

            <div className="space-y-1">
              <span className="text-[10px] text-slate-500 uppercase font-bold block">Critical / High Alerts</span>
              <div className="flex items-center gap-2 font-bold text-sm pt-1">
                <span className="text-rose-400">{summary.criticalCount} Critical</span>
                <span className="text-slate-500">|</span>
                <span className="text-amber-400">{summary.highCount} High</span>
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

      {/* Severity Filter Pills */}
      <div className="flex items-center gap-2 overflow-x-auto pb-2 font-mono text-xs">
        {['ALL', 'CRITICAL', 'HIGH', 'MEDIUM', 'LOW'].map((sev) => (
          <button
            key={sev}
            onClick={() => setSelectedSeverity(sev)}
            className={`px-3 py-1.5 border transition-colors ${
              selectedSeverity === sev
                ? 'bg-[#00F0FF]/10 border-[#00F0FF] text-[#00F0FF] font-bold'
                : 'bg-[#05080E] border-white/10 text-slate-400 hover:text-white'
            }`}
          >
            {sev} ALERTS
          </button>
        ))}
      </div>

      {/* Risk Alerts Grid */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <ShieldAlert className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Active Financial Risk Alerts</h2>
        </div>

        <div className="grid grid-cols-1 gap-4">
          {filteredAlerts?.map((alert) => {
            const isExpanded = expandedId === alert.id;

            return (
              <Card key={alert.id} className="p-6 space-y-4 border-white/10 bg-[#05080E]/40">
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2 flex-wrap">
                      <Badge
                        variant={alert.severity === 'CRITICAL' || alert.severity === 'HIGH' ? 'rose' : 'amber'}
                        className="text-[10px] font-bold"
                      >
                        {alert.severity}
                      </Badge>
                      <Badge variant="cyan" className="text-[10px]">{alert.riskType}</Badge>
                      <Badge variant="demo" className="text-[10px]">{alert.riskKey}</Badge>
                      <h3 className="text-base font-bold text-white uppercase">{alert.title}</h3>
                    </div>
                    <p className="text-xs text-slate-300 font-sans">{alert.description}</p>
                  </div>

                  <div className="flex items-center gap-2 shrink-0">
                    {alert.status === 'OPEN' && (
                      <>
                        <Button variant="outline" size="sm" onClick={() => handleAcknowledge(alert.id)} className="text-xs text-cyan-300 border-cyan-500/30">
                          Acknowledge
                        </Button>
                        <Button variant="emerald" size="sm" onClick={() => handleResolve(alert.id)} className="text-xs gap-1">
                          <Check className="w-3.5 h-3.5" />
                          Resolve
                        </Button>
                      </>
                    )}
                    {alert.status !== 'OPEN' && (
                      <Badge variant="emerald" className="text-[10px]">
                        {alert.status}
                      </Badge>
                    )}
                  </div>
                </div>

                {/* Metrics Row */}
                <div className="grid grid-cols-1 sm:grid-cols-4 gap-3 text-xs font-mono">
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">Baseline Value ({alert.detectionWindow}):</span>
                    <span className="text-slate-300 font-bold">₹{alert.baselineValue.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">Current Observed Value:</span>
                    <span className="text-white font-bold">₹{alert.currentValue.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">Deterioration Change %:</span>
                    <span className="text-rose-400 font-bold">+{alert.changePct}%</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">Risk Threshold Value:</span>
                    <span className="text-cyan-300 font-bold">₹{alert.thresholdValue.toLocaleString('en-IN')}</span>
                  </div>
                </div>

                {/* Expandable Evidence Drawer */}
                {isExpanded && (
                  <div className="p-4 bg-[#05080E] border border-[#00F0FF]/30 space-y-2 text-xs font-mono text-slate-300 animate-fadeIn">
                    <span className="text-[#00F0FF] font-bold block uppercase">Evidence Metrics & Detection Details</span>
                    <p className="font-sans text-slate-300">{alert.evidenceMetrics}</p>
                    <div className="flex items-center gap-4 text-[10px] text-slate-500 pt-1">
                      <span>Window: <strong>{alert.detectionWindow}</strong></span>
                      <span>Confidence: <strong>{alert.confidenceStatus}</strong></span>
                      <span>Evaluated: <strong>{new Date(alert.evaluatedAt).toLocaleTimeString()}</strong></span>
                    </div>
                  </div>
                )}

                <div className="flex justify-between items-center text-xs text-slate-400 pt-1">
                  <button
                    onClick={() => toggleExpand(alert.id)}
                    className="hover:text-[#00F0FF] underline"
                  >
                    {isExpanded ? 'Hide evidence metrics' : 'View evidence metrics'}
                  </button>
                  <span className="text-[10px] text-slate-500">Deterministic rule signal | ACTUAL</span>
                </div>
              </Card>
            );
          })}
        </div>
      </section>

      {/* Advisory Risk Mitigation Directives (Action Center Integration) */}
      <section className="space-y-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00F0FF]" />
          <h2 className="text-lg font-bold text-white uppercase tracking-tight">Recommended Risk Mitigation Actions</h2>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {summary?.recommendedRiskActions?.map((act) => (
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
          Advisory Governance & Non-Execution Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Early Risk Detection engine evaluates emerging financial trends across liquid reserves, payables pressure, and receivables collections. All flagged alerts and mitigation directives are strictly advisory; evaluating or resolving alerts does not move funds, modify bank accounts, or execute payments.
        </p>
      </section>
    </div>
  );
}
