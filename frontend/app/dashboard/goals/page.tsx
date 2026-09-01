'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantGoals,
  createMerchantGoal,
  evaluateGoal,
  archiveGoal,
  BackendFinancialGoalDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Target,
  AlertTriangle,
  CheckCircle2,
  TrendingUp,
  ShieldAlert,
  RefreshCw,
  Clock,
  Plus,
  Info,
  Archive,
  Layers,
  HelpCircle,
  X,
  Check,
} from 'lucide-react';

export default function GoalsPage() {
  const [goals, setGoals] = useState<BackendFinancialGoalDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [showBasis, setShowBasis] = useState<boolean>(false);

  // Form State
  const [goalType, setGoalType] = useState<string>('CASH_RESERVE');
  const [name, setName] = useState<string>('');
  const [targetAmount, setTargetAmount] = useState<string>('500000');
  const [targetDate, setTargetDate] = useState<string>('2026-12-31');
  const [creating, setCreating] = useState<boolean>(false);

  const loadGoals = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantGoals(1);
      setGoals(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Financial Goal API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadGoals();
  }, []);

  const handleCreateGoal = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!name.trim()) return;
    setCreating(true);
    try {
      await createMerchantGoal(1, {
        goalType: goalType as any,
        name,
        targetAmount: parseFloat(targetAmount) || 0,
        targetDate,
      });
      setShowModal(false);
      setName('');
      loadGoals();
    } catch (err: any) {
      alert(err.message || 'Failed to create goal');
    } finally {
      setCreating(false);
    }
  };

  const handleEvaluate = async (goalId: number) => {
    try {
      await evaluateGoal(1, goalId);
      loadGoals();
    } catch (err: any) {
      alert(err.message || 'Failed to evaluate goal');
    }
  };

  const handleArchive = async (goalId: number) => {
    try {
      await archiveGoal(1, goalId);
      loadGoals();
    } catch (err: any) {
      alert(err.message || 'Failed to archive goal');
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
              FINANCIAL GOALS SERVICE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Financial Goal API (`http://localhost:8080/api/v1/merchants/1/goals`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadGoals} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  const activeGoals = goals.filter((g) => g.riskStatus === 'ON_TRACK');
  const atRiskGoals = goals.filter((g) => g.riskStatus === 'AT_RISK');
  const achievedGoals = goals.filter((g) => g.riskStatus === 'ACHIEVED');

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Financial Goals & Decision Tracking
            </h1>
            <Badge variant="demo">ADVISORY GOAL EVALUATOR</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Define measurable financial targets and track dynamic progress using underlying Flowwise engines
          </p>
        </div>

        <Button variant="cyan" onClick={() => setShowModal(true)} className="gap-2 shrink-0">
          <Plus className="w-4 h-4" />
          Create Financial Goal
        </Button>
      </div>

      {/* Portfolio Overview Scorecards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Target className="w-3.5 h-3.5 text-[#00F0FF]" />
            Total Goals Configured
          </span>
          <div className="text-2xl font-bold text-white">{goals.length}</div>
          <div className="text-[11px] text-slate-500">Portfolio items</div>
        </Card>

        <Card className="space-y-2 border-emerald-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            On-Track / Active
          </span>
          <div className="text-2xl font-bold text-[#00E599]">{activeGoals.length}</div>
          <div className="text-[11px] text-slate-500">Meeting target pace</div>
        </Card>

        <Card className="space-y-2 border-rose-500/20">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <ShieldAlert className="w-3.5 h-3.5 text-rose-400" />
            At-Risk Goals
          </span>
          <div className="text-2xl font-bold text-rose-400">{atRiskGoals.length}</div>
          <div className="text-[11px] text-slate-500">Lagging pace requirement</div>
        </Card>

        <Card className="space-y-2 border-cyan-500/30">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <TrendingUp className="w-3.5 h-3.5 text-[#00F0FF]" />
            Achieved Goals
          </span>
          <div className="text-2xl font-bold text-[#00F0FF]">{achievedGoals.length}</div>
          <div className="text-[11px] text-slate-500">Successfully completed</div>
        </Card>
      </section>

      {/* Goal Cards Grid */}
      <section className="space-y-4">
        <div className="flex items-center justify-between">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Layers className="w-5 h-5 text-[#00F0FF]" />
            Active Financial Goals Portfolio
          </h2>
          <button
            onClick={() => setShowBasis(!showBasis)}
            className="text-xs text-[#00F0FF] hover:underline flex items-center gap-1"
          >
            <Info className="w-3.5 h-3.5" />
            {showBasis ? 'Hide Rules' : 'View Goal Rules'}
          </button>
        </div>

        {/* Calculation Rules Drawer */}
        {showBasis && (
          <Card className="p-5 space-y-2 bg-[#05080E] border-[#00F0FF]/30 text-xs font-mono">
            <span className="text-[#00F0FF] font-bold block uppercase">Goal Evaluation Mechanics:</span>
            <ul className="list-disc list-inside text-slate-300 space-y-1 font-sans">
              <li><strong>Accumulation Goals:</strong> (CASH_RESERVE, WORKING_CAPITAL, RECEIVABLES_COLLECTION) evaluate liquid cash/working capital growth against target amount.</li>
              <li><strong>Reduction Goals:</strong> (DEBT_REDUCTION, EXPENSE_REDUCTION) evaluate liability or expense reduction down from initial baseline amount.</li>
              <li><strong>Pace Requirement:</strong> Derived as Remaining Target / Months Remaining until deadline.</li>
              <li><strong>Engine Grounding:</strong> Values derived live from Spring Boot financial engines (Cash Flow, Receivables, Payables, Working Capital).</li>
            </ul>
          </Card>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {goals.length === 0 ? (
            <Card className="p-8 text-center text-slate-500 col-span-2">
              No financial goals configured yet. Click "Create Financial Goal" to start tracking.
            </Card>
          ) : (
            goals.map((goal) => (
              <Card
                key={goal.id}
                className="p-6 space-y-4 border-white/10 hover:border-[#00F0FF]/40 transition-colors"
              >
                {/* Card Header */}
                <div className="flex items-start justify-between gap-2 border-b border-white/10 pb-3">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2">
                      <Badge
                        variant={goal.goalCategoryType === 'REDUCTION' ? 'amber' : 'cyan'}
                        className="text-[9px]"
                      >
                        {goal.goalCategoryType}
                      </Badge>
                      <h3 className="text-base font-bold text-white uppercase">{goal.name}</h3>
                    </div>
                    <span className="text-[11px] text-slate-400 uppercase font-mono">{goal.goalType.replace(/_/g, ' ')}</span>
                  </div>

                  <Badge
                    variant={
                      goal.riskStatus === 'ACHIEVED'
                        ? 'cyan'
                        : goal.riskStatus === 'AT_RISK'
                        ? 'rose'
                        : goal.riskStatus === 'EXPIRED'
                        ? 'demo'
                        : 'emerald'
                    }
                    className="text-[9px] uppercase"
                  >
                    {goal.riskStatus.replace(/_/g, ' ')}
                  </Badge>
                </div>

                {/* Progress Bar */}
                <div className="space-y-1.5">
                  <div className="flex items-center justify-between text-xs font-mono">
                    <span className="text-slate-400">Progress</span>
                    <span className="text-[#00F0FF] font-bold">{goal.progressPct}%</span>
                  </div>
                  <div className="w-full h-2 bg-white/5 border border-white/10 overflow-hidden">
                    <div
                      className={`h-full transition-all duration-300 ${
                        goal.riskStatus === 'ACHIEVED'
                          ? 'bg-[#00F0FF]'
                          : goal.riskStatus === 'AT_RISK'
                          ? 'bg-rose-400'
                          : 'bg-[#00E599]'
                      }`}
                      style={{ width: `${Math.min(100, goal.progressPct)}%` }}
                    ></div>
                  </div>
                </div>

                {/* Amount Metrics */}
                <div className="grid grid-cols-2 gap-2 text-xs font-mono p-3 bg-[#05080E] border border-white/5">
                  <div>
                    <span className="text-slate-500 block text-[10px] uppercase">Current Level</span>
                    <span className="text-white font-bold">{formatINR(goal.currentAmount)}</span>
                  </div>
                  <div className="text-right">
                    <span className="text-slate-500 block text-[10px] uppercase">Target Goal</span>
                    <span className="text-[#00F0FF] font-bold">{formatINR(goal.targetAmount)}</span>
                  </div>
                </div>

                {/* Pace & Deadline Info */}
                <div className="space-y-1 text-xs font-mono text-slate-300">
                  <div className="flex items-center justify-between">
                    <span className="text-slate-400 flex items-center gap-1">
                      <Clock className="w-3.5 h-3.5 text-slate-400" />
                      Target Date:
                    </span>
                    <span className="text-white">{goal.targetDate} ({goal.daysRemaining < 0 ? 'Deadline passed' : `${goal.daysRemaining} days left`})</span>
                  </div>

                  <div className="flex items-center justify-between">
                    <span className="text-slate-400 flex items-center gap-1">
                      <TrendingUp className="w-3.5 h-3.5 text-[#00E599]" />
                      Required Pace:
                    </span>
                    <span className="text-[#00E599] font-bold">{formatINR(goal.requiredMonthlyPace)} / month</span>
                  </div>
                </div>

                {/* Explanation */}
                <p className="text-xs text-slate-400 font-sans leading-relaxed pt-2 border-t border-white/5">
                  {goal.statusExplanation}
                </p>

                {/* Card Actions */}
                <div className="flex items-center justify-end gap-2 pt-2">
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleArchive(goal.id)}
                    className="gap-1 text-xs text-slate-400 hover:text-white"
                  >
                    <Archive className="w-3.5 h-3.5" />
                    Archive
                  </Button>
                  <Button
                    variant="cyan"
                    size="sm"
                    onClick={() => handleEvaluate(goal.id)}
                    className="gap-1 text-xs"
                  >
                    <RefreshCw className="w-3.5 h-3.5" />
                    Evaluate Live
                  </Button>
                </div>
              </Card>
            ))
          )}
        </div>
      </section>

      {/* Create Goal Modal Form */}
      {showModal && (
        <div className="fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <Card className="w-full max-w-lg p-6 space-y-5 border-[#00F0FF]/40 bg-[#0A0D12]">
            <div className="flex items-center justify-between border-b border-white/10 pb-4">
              <h3 className="text-lg font-bold text-white uppercase flex items-center gap-2">
                <Target className="w-5 h-5 text-[#00F0FF]" />
                Create New Financial Goal
              </h3>
              <button onClick={() => setShowModal(false)} className="text-slate-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleCreateGoal} className="space-y-4 text-xs font-mono">
              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Goal Name</label>
                <input
                  type="text"
                  required
                  placeholder="e.g. Q4 Cash Reserve Fund"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                />
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Goal Type</label>
                <select
                  value={goalType}
                  onChange={(e) => setGoalType(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                >
                  <option value="CASH_RESERVE">CASH_RESERVE (Accumulation)</option>
                  <option value="WORKING_CAPITAL">WORKING_CAPITAL (Accumulation)</option>
                  <option value="RECEIVABLES_COLLECTION">RECEIVABLES_COLLECTION (Accumulation)</option>
                  <option value="DEBT_REDUCTION">DEBT_REDUCTION (Reduction)</option>
                  <option value="EXPENSE_REDUCTION">EXPENSE_REDUCTION (Reduction)</option>
                </select>
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Target Amount (₹)</label>
                <input
                  type="number"
                  required
                  min="1"
                  value={targetAmount}
                  onChange={(e) => setTargetAmount(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                />
              </div>

              <div className="space-y-1">
                <label className="text-slate-400 uppercase font-bold">Target Deadline</label>
                <input
                  type="date"
                  required
                  value={targetDate}
                  onChange={(e) => setTargetDate(e.target.value)}
                  className="w-full p-2.5 bg-[#05080E] border border-white/10 text-white focus:border-[#00F0FF] outline-none"
                />
              </div>

              <div className="flex items-center justify-end gap-3 pt-3 border-t border-white/10">
                <Button variant="outline" type="button" onClick={() => setShowModal(false)}>
                  Cancel
                </Button>
                <Button variant="cyan" type="submit" disabled={creating} className="gap-2">
                  <Check className="w-4 h-4" />
                  {creating ? 'Saving...' : 'Save Financial Goal'}
                </Button>
              </div>
            </form>
          </Card>
        </div>
      )}

      {/* Advisory Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Goal Tracking Notice
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise goal evaluations provide decision support to assist merchant financial planning. Goal metrics are derived live from connected bank feeds and financial ledgers. Flowwise will never execute payments or alter financial accounts automatically.
        </p>
      </section>
    </div>
  );
}
