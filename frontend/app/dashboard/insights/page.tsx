'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantInsights,
  fetchMerchantInsightSummary,
  acknowledgeInsight,
  dismissInsight,
  BackendFinancialInsightDTO,
  BackendInsightSummaryDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Sparkles,
  AlertTriangle,
  CheckCircle2,
  TrendingUp,
  XCircle,
  Clock,
  Info,
  Check,
  X,
  RefreshCw,
  Layers,
  ChevronDown,
  ChevronUp,
  Database,
  HelpCircle,
} from 'lucide-react';

export default function InsightsPage() {
  const [insights, setInsights] = useState<BackendFinancialInsightDTO[]>([]);
  const [summary, setSummary] = useState<BackendInsightSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState<string>('ALL');
  const [expandedId, setExpandedId] = useState<number | null>(null);
  const [showBasis, setShowBasis] = useState<boolean>(false);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [insightsData, summaryData] = await Promise.all([
        fetchMerchantInsights(1),
        fetchMerchantInsightSummary(1),
      ]);
      setInsights(insightsData);
      setSummary(summaryData);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Pattern Insight API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAcknowledge = async (id: number) => {
    try {
      await acknowledgeInsight(1, id);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to acknowledge insight');
    }
  };

  const handleDismiss = async (id: number) => {
    try {
      await dismissInsight(1, id);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to dismiss insight');
    }
  };

  const toggleExpand = (id: number) => {
    setExpandedId(expandedId === id ? null : id);
  };

  const filteredInsights = insights.filter((inItem) => {
    if (filter === 'ALL') return true;
    if (filter === 'HIGH' || filter === 'MEDIUM' || filter === 'LOW') return inItem.severity === filter;
    return inItem.status === filter;
  });

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

  if (error) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              PATTERN INSIGHT SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Insight API (`http://localhost:8080/api/v1/merchants/1/insights`).
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
              Financial Pattern & Insight Engine
            </h1>
            <Badge variant="demo">ADVISORY PATTERN DETECTOR</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic pattern discovery cross-analyzing cash flow, receivables, payables, working capital, and ledger reconciliation
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Run Pattern Analysis
        </Button>
      </div>

      {/* Portfolio Overview Scorecards */}
      {summary && (
        <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <Card className="space-y-2">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <Sparkles className="w-3.5 h-3.5 text-[#00F0FF]" />
              Total Discovered Insights
            </span>
            <div className="text-2xl font-bold text-white">{summary.totalInsights}</div>
            <div className="text-[11px] text-slate-500">Active pattern memory</div>
          </Card>

          <Card className="space-y-2 border-rose-500/20">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <AlertTriangle className="w-3.5 h-3.5 text-rose-400" />
              High Severity Alerts
            </span>
            <div className="text-2xl font-bold text-rose-400">{summary.highSeverityCount}</div>
            <div className="text-[11px] text-slate-500">Actionable risk triggers</div>
          </Card>

          <Card className="space-y-2 border-amber-500/20">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <Clock className="w-3.5 h-3.5 text-amber-400" />
              Active New Insights
            </span>
            <div className="text-2xl font-bold text-amber-400">{summary.newCount}</div>
            <div className="text-[11px] text-slate-500">Unacknowledged pattern alerts</div>
          </Card>

          <Card className="space-y-2 border-emerald-500/20">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
              Pattern Engine Status
            </span>
            <div className="text-sm font-bold text-[#00E599] truncate">{summary.patternEngineStatus}</div>
            <div className="text-[11px] text-slate-500">{summary.sufficientHistory ? 'Sufficient history online' : 'Limited history baseline'}</div>
          </Card>
        </section>
      )}

      {/* Filter Tabs & Insights List */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div className="flex items-center gap-2 overflow-x-auto pb-1">
            {['ALL', 'HIGH', 'MEDIUM', 'LOW', 'NEW', 'ACKNOWLEDGED'].map((st) => (
              <button
                key={st}
                onClick={() => setFilter(st)}
                className={`px-3 py-1.5 text-xs font-mono border transition-colors whitespace-nowrap ${
                  filter === st
                    ? 'border-[#00F0FF] bg-[#00F0FF]/10 text-[#00F0FF] font-bold'
                    : 'border-white/10 bg-[#05080E] text-slate-400 hover:border-white/20'
                }`}
              >
                {st}
              </button>
            ))}
          </div>

          <button
            onClick={() => setShowBasis(!showBasis)}
            className="text-xs text-[#00F0FF] hover:underline flex items-center gap-1 shrink-0"
          >
            <Info className="w-3.5 h-3.5" />
            {showBasis ? 'Hide Rules' : 'View Pattern Rules'}
          </button>
        </div>

        {/* Pattern Detection Rules Drawer */}
        {showBasis && (
          <Card className="p-5 space-y-2 bg-[#05080E] border-[#00F0FF]/30 text-xs font-mono">
            <span className="text-[#00F0FF] font-bold block uppercase">Deterministic Pattern Engine Mechanics:</span>
            <ul className="list-disc list-inside text-slate-300 space-y-1 font-sans">
              <li><strong>RISING_PAYMENT_PRESSURE:</strong> Payables due within 7 days exceed 20% of available cash reserves.</li>
              <li><strong>RECEIVABLES_DETERIORATION:</strong> Overdue distributor invoices exceed 15% of total outstanding.</li>
              <li><strong>WORKING_CAPITAL_DETERIORATION:</strong> Working capital gap &gt; ₹0 or near-term coverage ratio &lt; 1.10.</li>
              <li><strong>REPEATED_RECONCILIATION_ISSUES:</strong> Unreviewed entries &gt; 3 or duplicate transaction alerts &gt; 1.</li>
              <li><strong>Deduplication &amp; Idempotency:</strong> Identical pattern types for the same detected period are deduplicated automatically.</li>
            </ul>
          </Card>
        )}

        {/* Insights Grid */}
        <div className="grid grid-cols-1 gap-4">
          {filteredInsights.length === 0 ? (
            <Card className="p-8 text-center text-slate-500 space-y-2">
              <Database className="w-8 h-8 text-slate-600 mx-auto" />
              <p className="text-sm font-bold text-slate-400 uppercase">NO PATTERN INSIGHTS DETECTED</p>
              <p className="text-xs text-slate-500 max-w-md mx-auto">
                No financial patterns matching filter "{filter}". Underlying Flowwise engines report stable baseline performance.
              </p>
            </Card>
          ) : (
            filteredInsights.map((inItem) => {
              const isExpanded = expandedId === inItem.id;
              return (
                <Card
                  key={inItem.id}
                  className={`p-6 space-y-4 transition-colors ${
                    inItem.severity === 'HIGH'
                      ? 'border-rose-500/30 bg-[#0C060A]/40'
                      : inItem.severity === 'MEDIUM'
                      ? 'border-amber-500/20 bg-[#0A0906]/40'
                      : 'border-white/10'
                  }`}
                >
                  {/* Card Header */}
                  <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-white/10 pb-3">
                    <div className="space-y-1">
                      <div className="flex items-center gap-2 flex-wrap">
                        <Badge
                          variant={
                            inItem.severity === 'HIGH'
                              ? 'rose'
                              : inItem.severity === 'MEDIUM'
                              ? 'amber'
                              : 'cyan'
                          }
                          className="text-[9px]"
                        >
                          {inItem.severity} SEVERITY
                        </Badge>

                        <Badge variant="demo" className="text-[9px]">
                          {inItem.insightType.replace(/_/g, ' ')}
                        </Badge>

                        <h3 className="text-base font-bold text-white uppercase">{inItem.title}</h3>
                      </div>

                      <div className="flex items-center gap-3 text-[11px] text-slate-400">
                        <span>Period: <strong className="text-white">{inItem.detectedPeriod}</strong></span>
                        <span>Type: <strong className="text-cyan-300">{inItem.calculationType}</strong></span>
                        <span>Confidence: <strong className="text-emerald-300">{inItem.confidenceStatus}</strong></span>
                      </div>
                    </div>

                    <div className="flex items-center gap-2 shrink-0">
                      <Badge
                        variant={
                          inItem.status === 'ACKNOWLEDGED'
                            ? 'emerald'
                            : inItem.status === 'DISMISSED'
                            ? 'demo'
                            : 'amber'
                        }
                        className="text-[9px] uppercase"
                      >
                        Status: {inItem.status}
                      </Badge>

                      <button
                        onClick={() => toggleExpand(inItem.id)}
                        className="p-1 text-slate-400 hover:text-white border border-white/10"
                      >
                        {isExpanded ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
                      </button>
                    </div>
                  </div>

                  {/* Main Description */}
                  <p className="text-xs text-slate-300 font-sans leading-relaxed">
                    {inItem.description}
                  </p>

                  {/* Metrics Box */}
                  <div className="p-3 bg-[#05080E] border border-white/5 font-mono text-xs text-[#00F0FF] space-y-1">
                    <span className="text-slate-500 uppercase text-[10px] block font-bold">Evidence Metrics</span>
                    <p className="text-white font-mono">{inItem.evidenceMetrics}</p>
                  </div>

                  {/* Expandable Evidence & Assumptions Drawer */}
                  {isExpanded && (
                    <div className="p-4 bg-[#05080E] border border-[#00F0FF]/30 space-y-2 text-xs font-mono text-slate-300 animate-fadeIn">
                      <span className="text-[#00F0FF] font-bold block uppercase">Why This Matters & Assumptions</span>
                      <p className="font-sans text-slate-300 leading-relaxed">
                        {inItem.assumptions || 'Calculated deterministically from underlying Spring Boot financial engines.'}
                      </p>
                    </div>
                  )}

                  {/* Card Action Controls */}
                  <div className="flex items-center justify-between pt-2 border-t border-white/5">
                    <button
                      onClick={() => toggleExpand(inItem.id)}
                      className="text-xs text-slate-400 hover:text-[#00F0FF] underline"
                    >
                      {isExpanded ? 'Hide evidence' : 'Why this matters'}
                    </button>

                    <div className="flex items-center gap-2">
                      {inItem.status === 'NEW' && (
                        <Button
                          variant="cyan"
                          size="sm"
                          onClick={() => handleAcknowledge(inItem.id)}
                          className="gap-1 text-xs"
                        >
                          <Check className="w-3.5 h-3.5" />
                          Acknowledge
                        </Button>
                      )}

                      {inItem.status !== 'DISMISSED' && (
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => handleDismiss(inItem.id)}
                          className="gap-1 text-xs text-slate-400 hover:text-white"
                        >
                          <X className="w-3.5 h-3.5" />
                          Dismiss
                        </Button>
                      )}
                    </div>
                  </div>
                </Card>
              );
            })
          )}
        </div>
      </section>

      {/* Advisory Governance Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Pattern Intelligence Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise pattern insights provide advisory risk intelligence to highlight emerging financial trends. Insight triggers do not execute payments or alter financial accounts automatically.
        </p>
      </section>
    </div>
  );
}
