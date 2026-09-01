'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantDecisionIntelligence,
  fetchLatestDecisionAnalysis,
  BackendDecisionAnalysisDTO,
  BackendDecisionOptionDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Scale,
  Sparkles,
  TrendingUp,
  AlertTriangle,
  CheckCircle2,
  RefreshCw,
  Info,
  ChevronDown,
  ChevronUp,
  Award,
  HelpCircle,
  Clock,
  Layers,
  ArrowRight,
} from 'lucide-react';

export default function DecisionIntelligencePage() {
  const [analysis, setAnalysis] = useState<BackendDecisionAnalysisDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [expandedId, setExpandedId] = useState<number | null>(null);
  const [showWeightModel, setShowWeightModel] = useState<boolean>(false);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantDecisionIntelligence(1);
      setAnalysis(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Decision Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

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
            <div key={i} className="h-72 bg-[#0E1116] border border-white/10"></div>
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
              DECISION ENGINE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Decision Intelligence API (`http://localhost:8080/api/v1/merchants/1/decision-intelligence`).
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

  const topOption = analysis?.options?.find(o => o.optionKey === analysis.recommendedOption) || analysis?.options?.[0];

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Financial Decision Intelligence
            </h1>
            <Badge variant="demo">DECISION RANKING ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic 5-factor decision optimization cross-evaluating liquidity, coverage, goal impact, risk, and urgency
          </p>
        </div>

        <Button variant="outline" onClick={loadData} className="gap-2 shrink-0 border-[#00F0FF]/40 text-[#00F0FF]">
          <RefreshCw className="w-4 h-4" />
          Re-evaluate Analysis
        </Button>
      </div>

      {/* Top Recommended Choice Highlight Banner */}
      {analysis && topOption && (
        <Card variant="glow-cyan" className="p-6 space-y-4 border-[#00F0FF]/40 bg-[#080E18]/60">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-4">
            <div className="space-y-1">
              <div className="flex items-center gap-2 flex-wrap">
                <Badge variant="emerald" className="text-[10px] gap-1">
                  <Award className="w-3 h-3" />
                  #1 TOP RECOMMENDATION
                </Badge>
                <Badge variant="demo" className="text-[10px]">{topOption.optionKey}</Badge>
                <h2 className="text-xl font-bold text-white uppercase">{topOption.title}</h2>
              </div>
              <p className="text-xs text-slate-300 font-sans">{topOption.description}</p>
            </div>

            <div className="p-3 bg-[#05080E] border border-[#00F0FF]/30 text-right shrink-0">
              <span className="text-[10px] text-slate-500 uppercase block font-bold">Composite Score</span>
              <span className="text-2xl font-bold text-[#00F0FF]">{topOption.compositeScore}<span className="text-xs text-slate-400">/100</span></span>
            </div>
          </div>

          {/* Explanation & Metadata */}
          <div className="space-y-2">
            <p className="text-xs font-sans text-slate-200 leading-relaxed bg-[#05080E] p-3 border border-white/5">
              <strong className="text-[#00F0FF] font-mono">Engine Summary: </strong>
              {analysis.summaryExplanation}
            </p>

            <div className="flex items-center justify-between text-[11px] text-slate-400 font-mono pt-1">
              <span>Data Quality: <strong className="text-emerald-400">{analysis.dataQualityStatus}</strong></span>
              <span>Fingerprint: <strong className="text-slate-300">{analysis.inputFingerprint || 'fp_m1_active'}</strong></span>
              <span>Evaluated: <strong className="text-slate-300">{new Date(analysis.evaluatedAt).toLocaleTimeString()}</strong></span>
            </div>
          </div>
        </Card>
      )}

      {/* 5-Factor Scoring Model Toggle & Explanation */}
      <section className="space-y-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Scale className="w-5 h-5 text-[#00F0FF]" />
            <h2 className="text-lg font-bold text-white uppercase tracking-tight">Ranked Decision Alternatives</h2>
          </div>

          <button
            onClick={() => setShowWeightModel(!showWeightModel)}
            className="text-xs text-[#00F0FF] hover:underline flex items-center gap-1 shrink-0"
          >
            <Info className="w-3.5 h-3.5" />
            {showWeightModel ? 'Hide Weight Matrix' : 'View Scoring Weight Matrix'}
          </button>
        </div>

        {showWeightModel && (
          <Card className="p-5 space-y-3 bg-[#05080E] border-[#00F0FF]/30 text-xs font-mono">
            <span className="text-[#00F0FF] font-bold block uppercase">Deterministic 5-Factor Score Weight Matrix (Total: 100%):</span>
            <div className="grid grid-cols-1 sm:grid-cols-5 gap-3 text-center">
              <div className="p-2 bg-[#0A0E1A] border border-white/10">
                <span className="text-slate-400 block text-[10px]">Liquidity Impact</span>
                <span className="text-white font-bold text-sm">25% Weight</span>
              </div>
              <div className="p-2 bg-[#0A0E1A] border border-white/10">
                <span className="text-slate-400 block text-[10px]">Obligation Coverage</span>
                <span className="text-white font-bold text-sm">20% Weight</span>
              </div>
              <div className="p-2 bg-[#0A0E1A] border border-white/10">
                <span className="text-slate-400 block text-[10px]">Goal Progress</span>
                <span className="text-white font-bold text-sm">25% Weight</span>
              </div>
              <div className="p-2 bg-[#0A0E1A] border border-white/10">
                <span className="text-slate-400 block text-[10px]">Operational Risk</span>
                <span className="text-white font-bold text-sm">15% Weight</span>
              </div>
              <div className="p-2 bg-[#0A0E1A] border border-white/10">
                <span className="text-slate-400 block text-[10px]">Urgency / Penalty</span>
                <span className="text-white font-bold text-sm">15% Weight</span>
              </div>
            </div>
            <p className="text-[11px] text-slate-400 font-sans pt-1">
              Tie-breaker hierarchy: Risk Reduction &rarr; Liquidity Preservation &rarr; Goal Impact &rarr; Urgency &rarr; Option Key Order.
            </p>
          </Card>
        )}

        {/* Ranked Options Grid */}
        <div className="grid grid-cols-1 gap-6">
          {analysis?.options?.map((opt) => {
            const isExpanded = expandedId === (opt.id || opt.rankOrder);
            const isRecommended = opt.rankOrder === 1;

            return (
              <Card
                key={opt.optionKey}
                className={`p-6 space-y-5 transition-colors ${
                  isRecommended
                    ? 'border-[#00F0FF]/40 bg-[#080E18]/40'
                    : 'border-white/10 bg-[#05080E]/40'
                }`}
              >
                {/* Card Top Row */}
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-white/10 pb-4">
                  <div className="space-y-1">
                    <div className="flex items-center gap-2 flex-wrap">
                      <Badge
                        variant={isRecommended ? 'emerald' : 'demo'}
                        className="text-[10px] font-bold uppercase"
                      >
                        #{opt.rankOrder} {isRecommended ? 'RECOMMENDED' : 'OPTION'}
                      </Badge>
                      <Badge variant="cyan" className="text-[10px]">{opt.optionKey}</Badge>
                      <h3 className="text-base font-bold text-white uppercase">{opt.title}</h3>
                    </div>
                    <p className="text-xs text-slate-300 font-sans">{opt.description}</p>
                  </div>

                  <div className="flex items-center gap-3 shrink-0">
                    <div className="text-right">
                      <span className="text-[10px] text-slate-500 uppercase block font-bold">Composite Score</span>
                      <span className="text-xl font-bold text-[#00F0FF]">{opt.compositeScore}<span className="text-xs text-slate-400">/100</span></span>
                    </div>

                    <button
                      onClick={() => toggleExpand(opt.id || opt.rankOrder)}
                      className="p-1.5 text-slate-400 hover:text-white border border-white/10"
                    >
                      {isExpanded ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
                    </button>
                  </div>
                </div>

                {/* 5-Factor Component Score Bars */}
                <div className="space-y-2">
                  <span className="text-[10px] text-slate-500 uppercase font-bold block">5-Factor Component Score Breakdown</span>
                  <div className="grid grid-cols-1 sm:grid-cols-5 gap-3 text-xs font-mono">
                    <div className="p-2 bg-[#05080E] border border-white/5 space-y-1">
                      <div className="flex justify-between text-[10px]">
                        <span className="text-slate-400">Liquidity (25%):</span>
                        <span className="text-cyan-300 font-bold">{opt.liquidityScore}</span>
                      </div>
                      <div className="w-full h-1.5 bg-white/10 overflow-hidden">
                        <div className="h-full bg-[#00F0FF]" style={{ width: `${opt.liquidityScore}%` }}></div>
                      </div>
                    </div>

                    <div className="p-2 bg-[#05080E] border border-white/5 space-y-1">
                      <div className="flex justify-between text-[10px]">
                        <span className="text-slate-400">Coverage (20%):</span>
                        <span className="text-cyan-300 font-bold">{opt.coverageScore}</span>
                      </div>
                      <div className="w-full h-1.5 bg-white/10 overflow-hidden">
                        <div className="h-full bg-cyan-400" style={{ width: `${opt.coverageScore}%` }}></div>
                      </div>
                    </div>

                    <div className="p-2 bg-[#05080E] border border-white/5 space-y-1">
                      <div className="flex justify-between text-[10px]">
                        <span className="text-slate-400">Goal (25%):</span>
                        <span className="text-[#00E599] font-bold">{opt.goalScore}</span>
                      </div>
                      <div className="w-full h-1.5 bg-white/10 overflow-hidden">
                        <div className="h-full bg-[#00E599]" style={{ width: `${opt.goalScore}%` }}></div>
                      </div>
                    </div>

                    <div className="p-2 bg-[#05080E] border border-white/5 space-y-1">
                      <div className="flex justify-between text-[10px]">
                        <span className="text-slate-400">Risk (15%):</span>
                        <span className="text-amber-400 font-bold">{opt.riskScore}</span>
                      </div>
                      <div className="w-full h-1.5 bg-white/10 overflow-hidden">
                        <div className="h-full bg-amber-400" style={{ width: `${opt.riskScore}%` }}></div>
                      </div>
                    </div>

                    <div className="p-2 bg-[#05080E] border border-white/5 space-y-1">
                      <div className="flex justify-between text-[10px]">
                        <span className="text-slate-400">Urgency (15%):</span>
                        <span className="text-indigo-400 font-bold">{opt.urgencyScore}</span>
                      </div>
                      <div className="w-full h-1.5 bg-white/10 overflow-hidden">
                        <div className="h-full bg-indigo-400" style={{ width: `${opt.urgencyScore}%` }}></div>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Projected Cash Horizons & Status Row */}
                <div className="grid grid-cols-1 sm:grid-cols-4 gap-3 text-xs font-mono pt-2 border-t border-white/5">
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">7-Day Projected Cash:</span>
                    <span className="text-white font-bold">₹{opt.projected7dCash.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">30-Day Projected Cash:</span>
                    <span className="text-white font-bold">₹{opt.projected30dCash.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 space-y-0.5">
                    <span className="text-[10px] text-slate-500 block">90-Day Projected Cash:</span>
                    <span className="text-cyan-300 font-bold">₹{opt.projected90dCash.toLocaleString('en-IN')}</span>
                  </div>
                  <div className="p-2.5 bg-[#05080E] border border-white/5 flex flex-col justify-between">
                    <span className="text-[10px] text-slate-500 block">Goal Impact & Risk:</span>
                    <div className="flex items-center gap-1.5 pt-0.5">
                      <Badge
                        variant={opt.goalImpactStatus === 'POSITIVE' ? 'emerald' : opt.goalImpactStatus === 'NEGATIVE' ? 'rose' : 'cyan'}
                        className="text-[9px]"
                      >
                        Goal: {opt.goalImpactStatus}
                      </Badge>
                      <Badge
                        variant={opt.riskStatus === 'FEASIBLE' ? 'emerald' : 'amber'}
                        className="text-[9px]"
                      >
                        {opt.riskStatus}
                      </Badge>
                    </div>
                  </div>
                </div>

                {/* Expandable Assumptions & Evidence Metrics */}
                {isExpanded && (
                  <div className="p-4 bg-[#05080E] border border-[#00F0FF]/30 space-y-3 text-xs font-mono text-slate-300 animate-fadeIn">
                    <span className="text-[#00F0FF] font-bold block uppercase">Counterfactual Assumptions & Evidence Metrics</span>
                    <div className="space-y-1 font-sans">
                      <p><strong>Assumptions: </strong>{opt.assumptions}</p>
                      <p><strong>Evidence Metrics: </strong>{opt.evidenceMetrics}</p>
                    </div>
                  </div>
                )}

                {/* Bottom Drawer Toggle */}
                <div className="flex justify-between items-center text-xs text-slate-400">
                  <button
                    onClick={() => toggleExpand(opt.id || opt.rankOrder)}
                    className="hover:text-[#00F0FF] underline"
                  >
                    {isExpanded ? 'Hide evidence' : 'View assumptions & metrics'}
                  </button>
                  <span className="text-[10px] text-slate-500">Counterfactual projection | EST</span>
                </div>
              </Card>
            );
          })}
        </div>
      </section>

      {/* Advisory Governance Disclaimer Notice */}
      <section className="p-4 bg-[#05080E] border border-white/10 text-xs font-mono space-y-1">
        <div className="flex items-center gap-2 text-amber-400 font-bold uppercase">
          <HelpCircle className="w-4 h-4" />
          Advisory Governance & Non-Execution Directive
        </div>
        <p className="text-slate-400 font-sans leading-relaxed">
          Flowwise decision intelligence evaluates financial choices using a deterministic counterfactual scoring matrix. Recommendations are advisory and read-only; evaluating or viewing options does not move funds, authorize payments, or alter ledger accounts.
        </p>
      </section>
    </div>
  );
}
