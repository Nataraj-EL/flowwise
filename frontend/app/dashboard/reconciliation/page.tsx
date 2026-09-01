'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantReconciliation,
  reconcileTransaction,
  ignoreTransaction,
  BackendReconciliationSummaryDTO,
  BackendReconciliationIssueDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  CheckSquare,
  AlertTriangle,
  CheckCircle2,
  PieChart,
  ShieldAlert,
  RefreshCw,
  Clock,
  Layers,
  Check,
  XCircle,
  FileSearch,
} from 'lucide-react';

export default function ReconciliationPage() {
  const [summary, setSummary] = useState<BackendReconciliationSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [severityFilter, setSeverityFilter] = useState<string>('ALL');
  const [typeFilter, setTypeFilter] = useState<string>('ALL');
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  const loadReconciliation = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantReconciliation(1);
      setSummary(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Reconciliation Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadReconciliation();
  }, []);

  const handleReconcile = async (txId?: number) => {
    if (!txId) return;
    try {
      await reconcileTransaction(txId, 'Verified via Reconciliation Console');
      setActionMessage(`Transaction #${txId} marked as RECONCILED.`);
      setTimeout(() => setActionMessage(null), 4000);
      loadReconciliation();
    } catch (err: any) {
      alert(err.message || 'Failed to reconcile transaction');
    }
  };

  const handleIgnore = async (txId?: number) => {
    if (!txId) return;
    try {
      await ignoreTransaction(txId, 'Ignored via Reconciliation Console');
      setActionMessage(`Transaction #${txId} marked as IGNORED.`);
      setTimeout(() => setActionMessage(null), 4000);
      loadReconciliation();
    } catch (err: any) {
      alert(err.message || 'Failed to ignore transaction');
    }
  };

  const formatINR = (val: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(val || 0);
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-16 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
          {[1, 2, 3, 4, 5, 6].map((i) => (
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
              RECONCILIATION SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Reconciliation API (`http://localhost:8080/api/v1/merchants/1/reconciliation`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadReconciliation} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const filteredIssues = (summary.issues || []).filter((issue) => {
    if (severityFilter !== 'ALL' && issue.severity !== severityFilter) return false;
    if (typeFilter !== 'ALL' && issue.issueType !== typeFilter) return false;
    return true;
  });

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Reconciliation & Audit Console
            </h1>
            <Badge variant="demo">LEDGER AUDIT & ANOMALY DETECTION</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic duplicate transaction detection, unreviewed queue review, and one-click reconciliation actions
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs uppercase">
          <CheckSquare className="w-4 h-4 text-[#00F0FF]" />
          HEALTH SCORE: {summary.reconciliationHealthPct}%
        </Badge>
      </div>

      {/* Toast Notification */}
      {actionMessage && (
        <div className="p-3 bg-[#00E599]/10 border border-[#00E599]/40 text-[#00E599] text-xs font-mono flex items-center gap-2">
          <CheckCircle2 className="w-4 h-4 shrink-0" />
          <span>{actionMessage}</span>
        </div>
      )}

      {/* Summary Scorecards Grid */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-6 gap-4">
        <Card className="space-y-2 border-emerald-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Health Score
          </span>
          <div className="text-xl font-bold text-[#00E599]">{summary.reconciliationHealthPct}%</div>
          <div className="text-[11px] text-slate-500">Reconciled / Total</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Check className="w-3.5 h-3.5 text-[#00F0FF]" />
            Reconciled
          </span>
          <div className="text-xl font-bold text-white">{summary.reconciledCount}</div>
          <div className="text-[11px] text-slate-500">Verified items</div>
        </Card>

        <Card className="space-y-2 border-amber-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Clock className="w-3.5 h-3.5 text-amber-400" />
            Unreviewed
          </span>
          <div className="text-xl font-bold text-amber-400">{summary.unreviewedCount}</div>
          <div className="text-[11px] text-slate-500">Awaiting merchant review</div>
        </Card>

        <Card className="space-y-2 border-rose-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <FileSearch className="w-3.5 h-3.5 text-rose-400" />
            Duplicates
          </span>
          <div className="text-xl font-bold text-rose-400">{summary.duplicateIssuesCount}</div>
          <div className="text-[11px] text-slate-500">Matching amount/vendor</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            High-Value Outflows
          </span>
          <div className="text-xl font-bold text-rose-400">{summary.suspiciousIssuesCount}</div>
          <div className="text-[11px] text-slate-500">Debits &gt; ₹1,00,000</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Layers className="w-3.5 h-3.5 text-[#00F0FF]" />
            Office Kit Pending
          </span>
          <div className="text-xl font-bold text-[#00F0FF]">{summary.officeKitPendingCount}</div>
          <div className="text-[11px] text-slate-500">Captures awaiting review</div>
        </Card>
      </section>

      {/* Filter Controls & Issues Ledger */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <FileSearch className="w-5 h-5 text-[#00F0FF]" />
            Flagged Reconciliation Issues Queue
          </h2>

          <div className="flex flex-wrap items-center gap-2 text-xs">
            <div className="flex items-center gap-1 border border-white/10 bg-white/5 p-1">
              <span className="text-[10px] text-slate-500 uppercase px-1">Severity:</span>
              {['ALL', 'HIGH', 'MEDIUM', 'LOW'].map((sev) => (
                <button
                  key={sev}
                  onClick={() => setSeverityFilter(sev)}
                  className={`px-2 py-0.5 text-[10px] font-bold uppercase transition-colors ${
                    severityFilter === sev
                      ? 'bg-[#00F0FF] text-black'
                      : 'text-slate-400 hover:text-white'
                  }`}
                >
                  {sev}
                </button>
              ))}
            </div>

            <div className="flex items-center gap-1 border border-white/10 bg-white/5 p-1">
              <span className="text-[10px] text-slate-500 uppercase px-1">Type:</span>
              {['ALL', 'DUPLICATE', 'UNCATEGORIZED', 'SUSPICIOUS_AMOUNT', 'OFFICE_KIT_PENDING'].map((tp) => (
                <button
                  key={tp}
                  onClick={() => setTypeFilter(tp)}
                  className={`px-2 py-0.5 text-[10px] font-bold uppercase transition-colors ${
                    typeFilter === tp
                      ? 'bg-[#00F0FF] text-black'
                      : 'text-slate-400 hover:text-white'
                  }`}
                >
                  {tp.replace(/_/g, ' ')}
                </button>
              ))}
            </div>
          </div>
        </div>

        {/* Flagged Issues List */}
        <div className="space-y-3">
          {filteredIssues.length === 0 ? (
            <Card className="py-8 text-center text-slate-500">
              No flagged reconciliation issues found matching current filters.
            </Card>
          ) : (
            filteredIssues.map((issue) => (
              <Card key={issue.id} className="p-5 space-y-3 border-white/10 hover:border-[#00F0FF]/40 transition-colors">
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-white/10 pb-3">
                  <div className="flex items-center gap-2">
                    <Badge variant={issue.severity === 'HIGH' ? 'rose' : 'amber'} className="text-[9px]">
                      {issue.severity}
                    </Badge>
                    <Badge variant="cyan" className="text-[9px] uppercase">
                      {issue.issueType.replace(/_/g, ' ')}
                    </Badge>
                    <span className="text-xs font-bold text-white">{issue.counterparty}</span>
                  </div>

                  <div className="flex items-center gap-3">
                    <span className="text-sm font-bold text-[#00F0FF]">{formatINR(issue.amount)}</span>
                    <span className="text-xs text-slate-400">{issue.transactionDate}</span>
                  </div>
                </div>

                <p className="text-xs text-slate-200 font-sans leading-relaxed">{issue.description}</p>

                <div className="p-3 bg-[#05080E] border border-white/10 text-xs font-mono text-slate-400">
                  <span className="text-[#00F0FF] font-bold block mb-1">Evidence Details:</span>
                  {issue.evidenceDetails}
                </div>

                {/* Actions */}
                {issue.transactionId && (
                  <div className="flex items-center justify-end gap-2 pt-2 border-t border-white/5">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleIgnore(issue.transactionId)}
                      className="gap-1 text-xs text-slate-400 hover:text-white"
                    >
                      <XCircle className="w-3.5 h-3.5" />
                      Ignore
                    </Button>
                    <Button
                      variant="cyan"
                      size="sm"
                      onClick={() => handleReconcile(issue.transactionId)}
                      className="gap-1 text-xs"
                    >
                      <Check className="w-3.5 h-3.5" />
                      Reconcile
                    </Button>
                  </div>
                )}
              </Card>
            ))
          )}
        </div>
      </section>
    </div>
  );
}
