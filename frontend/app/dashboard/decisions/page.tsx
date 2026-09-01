'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantDecisions,
  fetchMerchantDecisionSummary,
  createMerchantDecision,
  acceptDecision,
  declineDecision,
  completeDecision,
  recordDecisionOutcome,
  BackendFinancialDecisionDTO,
  BackendDecisionSummaryDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  History,
  AlertTriangle,
  CheckCircle2,
  TrendingUp,
  XCircle,
  Clock,
  Plus,
  Info,
  Check,
  X,
  RefreshCw,
  FileText,
  Target,
  Zap,
} from 'lucide-react';

export default function DecisionsPage() {
  const [decisions, setDecisions] = useState<BackendFinancialDecisionDTO[]>([]);
  const [summary, setSummary] = useState<BackendDecisionSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState<string>('ALL');

  // Modals State
  const [showCreateModal, setShowCreateModal] = useState<boolean>(false);
  const [showOutcomeModal, setShowOutcomeModal] = useState<boolean>(false);
  const [selectedDecisionId, setSelectedDecisionId] = useState<number | null>(null);
  const [showBasis, setShowBasis] = useState<boolean>(false);

  // Form State for Create
  const [decisionType, setDecisionType] = useState<string>('CASH_MANAGEMENT');
  const [title, setTitle] = useState<string>('');
  const [recommendation, setRecommendation] = useState<string>('');
  const [decisionNotes, setDecisionNotes] = useState<string>('');
  const [creating, setCreating] = useState<boolean>(false);

  // Form State for Outcome
  const [outcomeStatus, setOutcomeStatus] = useState<string>('POSITIVE');
  const [outcomeNotes, setOutcomeNotes] = useState<string>('');
  const [submittingOutcome, setSubmittingOutcome] = useState<boolean>(false);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [decisionsData, summaryData] = await Promise.all([
        fetchMerchantDecisions(1),
        fetchMerchantDecisionSummary(1),
      ]);
      setDecisions(decisionsData);
      setSummary(summaryData);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Financial Decision API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleCreateDecision = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim()) return;
    setCreating(true);
    try {
      await createMerchantDecision(1, {
        decisionType,
        title,
        recommendation,
        decisionNotes,
      });
      setShowCreateModal(false);
      setTitle('');
      setRecommendation('');
      setDecisionNotes('');
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to create decision');
    } finally {
      setCreating(false);
    }
  };

  const handleAccept = async (id: number) => {
    try {
      await acceptDecision(1, id, 'Accepted by merchant');
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to accept decision');
    }
  };

  const handleDecline = async (id: number) => {
    try {
      await declineDecision(1, id, 'Declined by merchant');
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to decline decision');
    }
  };

  const handleComplete = async (id: number) => {
    try {
      await completeDecision(1, id, 'Completed by merchant');
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to complete decision');
    }
  };

  const handleOpenOutcomeModal = (id: number) => {
    setSelectedDecisionId(id);
    setOutcomeStatus('POSITIVE');
    setOutcomeNotes('');
    setShowOutcomeModal(true);
  };

  const handleSaveOutcome = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedDecisionId) return;
    setSubmittingOutcome(true);
    try {
      await recordDecisionOutcome(1, selectedDecisionId, {
        outcomeStatus: outcomeStatus as any,
        outcomeNotes,
      });
      setShowOutcomeModal(false);
      loadData();
    } catch (err: any) {
      alert(err.message || 'Failed to record decision outcome');
    } finally {
      setSubmittingOutcome(false);
    }
  };

  const filteredDecisions = decisions.filter((d) => {
    if (filter === 'ALL') return true;
    return d.decisionStatus === filter;
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
              DECISION HISTORY SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Decision API (`http://localhost:8080/api/v1/merchants/1/decisions`).
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
              Financial Decision History & Memory
            </h1>
            <Badge variant="demo">ADVISORY DECISION MEMORY</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Record merchant decisions, track execution outcomes, and measure historical decision performance
          </p>
        </div>

        <Button variant="cyan" onClick={() => setShowCreateModal(true)} className="gap-2 shrink-0">
          <Plus className="w-4 h-4" />
          Record New Decision
        </Button>
      </div>

      {/* Decision Summary Scorecards */}
      {summary && (
        <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <Card className="space-y-2">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <History className="w-3.5 h-3.5 text-[#00F0FF]" />
              Total Decisions
            </span>
            <div className="text-2xl font-bold text-white">{summary.totalDecisions}</div>
            <div className="text-[11px] text-slate-500">Recorded history log</div>
          </Card>

          <Card className="space-y-2 border-emerald-500/20">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
              Accepted / Completed
            </span>
            <div className="text-2xl font-bold text-[#00E599]">{summary.acceptedCount + summary.completedCount}</div>
            <div className="text-[11px] text-slate-500">{summary.completedCount} marked completed</div>
          </Card>

          <Card className="space-y-2 border-slate-500/20">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <XCircle className="w-3.5 h-3.5 text-slate-400" />
              Declined
            </span>
            <div className="text-2xl font-bold text-slate-300">{summary.declinedCount}</div>
            <div className="text-[11px] text-slate-500">Recommendations declined</div>
          </Card>

          <Card className="space-y-2 border-cyan-500/30">
            <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
              <TrendingUp className="w-3.5 h-3.5 text-[#00F0FF]" />
              Positive Outcome Rate
            </span>
            <div className="text-2xl font-bold text-[#00F0FF]">{summary.successRatePct}%</div>
            <div className="text-[11px] text-slate-500">{summary.positiveOutcomeCount} positive outcomes</div>
          </Card>
        </section>
      )}

      {/* Filter Tabs & History List */}
      <section className="space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          {/* Status Filter Buttons */}
          <div className="flex items-center gap-2 overflow-x-auto pb-1">
            {['ALL', 'PENDING', 'ACCEPTED', 'COMPLETED', 'DECLINED'].map((st) => (
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
            {showBasis ? 'Hide Rules' : 'View Decision Rules'}
          </button>
        </div>

        {/* Rules Drawer */}
        {showBasis && (
          <Card className="p-5 space-y-2 bg-[#05080E] border-[#00F0FF]/30 text-xs font-mono">
            <span className="text-[#00F0FF] font-bold block uppercase">Decision Memory Mechanics:</span>
            <ul className="list-disc list-inside text-slate-300 space-y-1 font-sans">
              <li><strong>Strict Lifecycle:</strong> PENDING → ACCEPTED → COMPLETED, or PENDING → DECLINED. Terminal states protected.</li>
              <li><strong>Outcome Rule:</strong> Outcomes (POSITIVE, NEGATIVE, NEUTRAL) can ONLY be recorded on COMPLETED decisions.</li>
              <li><strong>Context Linking:</strong> Optionally link decisions directly to Action Center alerts or Financial Goals.</li>
              <li><strong>Audit Traceability:</strong> All metrics calculated deterministically from stored merchant decision history.</li>
            </ul>
          </Card>
        )}

        {/* Decisions Timeline Grid */}
        <div className="grid grid-cols-1 gap-4">
          {filteredDecisions.length === 0 ? (
            <Card className="p-8 text-center text-slate-500">
              No decisions found matching filter "{filter}".
            </Card>
          ) : (
            filteredDecisions.map((d) => (
              <Card
                key={d.id}
                className="p-6 space-y-4 border-white/10 hover:border-[#00F0FF]/30 transition-colors"
              >
                {/* Header Row */}
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-white/10 pb-3">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2 flex-wrap">
                      <Badge variant="cyan" className="text-[9px]">
                        {d.decisionType.replace(/_/g, ' ')}
                      </Badge>
                      <h3 className="text-base font-bold text-white uppercase">{d.title}</h3>
                    </div>

                    <div className="flex items-center gap-3 text-[11px] text-slate-400">
                      <span className="flex items-center gap-1">
                        <Clock className="w-3.5 h-3.5 text-slate-400" />
                        Decision Date: {d.decisionDate}
                      </span>
                      {d.actionTitle && (
                        <span className="text-amber-300 flex items-center gap-1">
                          <Zap className="w-3 h-3" />
                          Action: {d.actionTitle}
                        </span>
                      )}
                      {d.goalName && (
                        <span className="text-cyan-300 flex items-center gap-1">
                          <Target className="w-3 h-3" />
                          Goal: {d.goalName}
                        </span>
                      )}
                    </div>
                  </div>

                  <div className="flex items-center gap-2 shrink-0">
                    <Badge
                      variant={
                        d.decisionStatus === 'COMPLETED'
                          ? 'emerald'
                          : d.decisionStatus === 'ACCEPTED'
                          ? 'cyan'
                          : d.decisionStatus === 'PENDING'
                          ? 'amber'
                          : 'demo'
                      }
                      className="text-[9px] uppercase"
                    >
                      Status: {d.decisionStatus}
                    </Badge>

                    <Badge
                      variant={
                        d.outcomeStatus === 'POSITIVE'
                          ? 'emerald'
                          : d.outcomeStatus === 'NEGATIVE'
                          ? 'rose'
                          : 'demo'
                      }
                      className="text-[9px] uppercase"
                    >
                      Outcome: {d.outcomeStatus}
                    </Badge>
                  </div>
                </div>

                {/* Recommendation Box */}
                {d.recommendation && (
                  <div className="p-3 bg-[#05080E] border border-white/5 space-y-1 text-xs font-mono">
                    <span className="text-slate-500 uppercase text-[10px] font-bold">Recommendation Context</span>
                    <p className="text-slate-300 font-sans">{d.recommendation}</p>
                  </div>
                )}

                {/* Decision Notes */}
                {d.decisionNotes && (
                  <div className="text-xs text-slate-400 font-mono space-y-1">
                    <span className="text-slate-500 block uppercase text-[10px]">Merchant Decision Notes:</span>
                    <p className="text-white font-sans">{d.decisionNotes}</p>
                  </div>
                )}

                {/* Outcome Notes */}
                {d.outcomeNotes && (
                  <div className="p-3 bg-emerald-500/5 border border-emerald-500/20 space-y-1 text-xs font-mono">
                    <span className="text-[#00E599] font-bold uppercase text-[10px]">Recorded Execution Outcome</span>
                    <p className="text-slate-200 font-sans">{d.outcomeNotes}</p>
                  </div>
                )}

                {/* Contextual Action Buttons */}
                <div className="flex items-center justify-end gap-2 pt-2 border-t border-white/5">
                  {d.decisionStatus === 'PENDING' && (
                    <>
                      <Button
                        variant="outline"
                        size="sm"
                        onClick={() => handleDecline(d.id)}
                        className="gap-1 text-xs text-slate-400 hover:text-white"
                      >
                        <X className="w-3.5 h-3.5" />
                        Decline
                      </Button>
                      <Button
                        variant="cyan"
                        size="sm"
                        onClick={() => handleAccept(d.id)}
                        className="gap-1 text-xs"
                      >
                        <Check className="w-3.5 h-3.5" />
                        Accept Decision
                      </Button>
                    </>
                  )}

                  {d.decisionStatus === 'ACCEPTED' && (
                    <Button
                      variant="cyan"
                      size="sm"
                      onClick={() => handleComplete(d.id)}
                      className="gap-1 text-xs"
                    >
                      <CheckCircle2 className="w-3.5 h-3.5" />
                      Mark Completed
                    </Button>
                  )}

                  {d.decisionStatus === 'COMPLETED' && (
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleOpenOutcomeModal(d.id)}
                      className="gap-1 text-xs text-[#00F0FF] border-[#00F0FF]/40 hover:bg-[#00F0FF]/10"
                    >
                      <FileText className="w-3.5 h-3.5" />
                      {d.outcomeStatus === 'UNKNOWN' ? 'Record Outcome' : 'Update Outcome'}
                    </Button>
                  )}
                </div>
              </Card>
            ))
          )}
        </div>
      </section>

      {/* Create Decision Modal */}
      {showCreateModal && (
        <div className="fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <Card className="w-full max-w-lg p-6 space-y-5 border-[#00F0FF]/40 bg-[#0A0D12]">
            <div className="flex items-center justify-between border-b border-white/10 pb-4">
              <h3 className="text-lg font-bold text-white uppercase flex items-center gap-2">
                <History className="w-5 h-5 text-[#00F0FF]" />
                Record New Financial Decision
              </h3>
              <button onClick={() => setShowCreateModal(false)} className="text-slate-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleCreateDecision} className="space-y-4 text-xs font-mono">
              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Decision Title</label>
                <input
                  type="text"
                  required
                  placeholder="e.g. Approved Q4 Inventory Financing"
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                />
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Decision Category Type</label>
                <select
                  value={decisionType}
                  onChange={(e) => setDecisionType(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                >
                  <option value="CASH_MANAGEMENT">CASH_MANAGEMENT</option>
                  <option value="VENDOR_PAYMENT">VENDOR_PAYMENT</option>
                  <option value="RECEIVABLES_COLLECTION">RECEIVABLES_COLLECTION</option>
                  <option value="EXPENSE_REDUCTION">EXPENSE_REDUCTION</option>
                  <option value="GOAL_ALIGNMENT">GOAL_ALIGNMENT</option>
                </select>
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Recommendation Details</label>
                <textarea
                  rows={2}
                  placeholder="Details of the financial recommendation received..."
                  value={recommendation}
                  onChange={(e) => setRecommendation(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none font-sans"
                />
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Merchant Rationale / Notes</label>
                <textarea
                  rows={2}
                  placeholder="Why this decision was taken..."
                  value={decisionNotes}
                  onChange={(e) => setDecisionNotes(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none font-sans"
                />
              </div>

              <div className="flex items-center justify-end gap-3 pt-3 border-t border-white/10">
                <Button variant="outline" type="button" onClick={() => setShowCreateModal(false)}>
                  Cancel
                </Button>
                <Button variant="cyan" type="submit" disabled={creating} className="gap-2">
                  <Check className="w-4 h-4" />
                  {creating ? 'Saving...' : 'Record Decision'}
                </Button>
              </div>
            </form>
          </Card>
        </div>
      )}

      {/* Record Outcome Modal */}
      {showOutcomeModal && (
        <div className="fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <Card className="w-full max-w-md p-6 space-y-5 border-[#00F0FF]/40 bg-[#0A0D12]">
            <div className="flex items-center justify-between border-b border-white/10 pb-4">
              <h3 className="text-base font-bold text-white uppercase flex items-center gap-2">
                <FileText className="w-4 h-4 text-[#00F0FF]" />
                Record Execution Outcome
              </h3>
              <button onClick={() => setShowOutcomeModal(false)} className="text-slate-400 hover:text-white">
                <X className="w-4 h-4" />
              </button>
            </div>

            <form onSubmit={handleSaveOutcome} className="space-y-4 text-xs font-mono">
              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Outcome Result Status</label>
                <select
                  value={outcomeStatus}
                  onChange={(e) => setOutcomeStatus(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                >
                  <option value="POSITIVE">POSITIVE (Achieved desired result)</option>
                  <option value="NEUTRAL">NEUTRAL (Standard outcome)</option>
                  <option value="NEGATIVE">NEGATIVE (Sub-optimal outcome)</option>
                </select>
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Outcome Notes & Impact</label>
                <textarea
                  rows={3}
                  required
                  placeholder="Record actual cash flow impact or operational notes..."
                  value={outcomeNotes}
                  onChange={(e) => setOutcomeNotes(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none font-sans"
                />
              </div>

              <div className="flex items-center justify-end gap-3 pt-3 border-t border-white/10">
                <Button variant="outline" type="button" onClick={() => setShowOutcomeModal(false)}>
                  Cancel
                </Button>
                <Button variant="cyan" type="submit" disabled={submittingOutcome} className="gap-2">
                  <Check className="w-4 h-4" />
                  {submittingOutcome ? 'Saving...' : 'Save Outcome'}
                </Button>
              </div>
            </form>
          </Card>
        </div>
      )}

      {/* Advisory Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <Info className="w-4 h-4" />
          Advisory Decision Memory Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise Decision History serves as an advisory decision log to document strategy and track long-term execution memory. Decision status records do not execute payments or alter bank accounts automatically.
        </p>
      </section>
    </div>
  );
}
