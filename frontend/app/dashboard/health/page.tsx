'use client';

import React, { useEffect, useState } from 'react';
import { fetchMerchantHealth, BackendBusinessHealthDTO } from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import {
  Activity,
  ShieldCheck,
  CheckCircle2,
  AlertTriangle,
  RefreshCw,
  TrendingUp,
  TrendingDown,
  Minus,
  Info,
  Layers,
  Zap,
  Lock,
} from 'lucide-react';

export default function HealthPage() {
  const [healthData, setHealthData] = useState<BackendBusinessHealthDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantHealth(1);
      setHealthData(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Business Health API');
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
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="h-64 bg-[#0E1116] border border-white/10"></div>
          <div className="lg:col-span-2 h-64 bg-[#0E1116] border border-white/10"></div>
        </div>
      </div>
    );
  }

  if (error || !healthData) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              HEALTH ENGINE API UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Health Engine (`http://localhost:8080/api/v1/merchants/1/health`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadData} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry API Connection
          </Button>
        </Card>
      </div>
    );
  }

  const { overallScore, healthStatus, factorScores, positiveSignals, riskSignals, summaryExplanation } = healthData;

  // SVG Radial Gauge
  const circumference = 2 * Math.PI * 52;
  const strokeDashoffset = circumference - (overallScore / 100) * circumference;

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Business Health Console
            </h1>
            <Badge variant="demo">DETERMINISTIC ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Operational Diagnostics & Risk Engine for <span className="text-white font-bold">Apex Retail Solutions [DEMO]</span>
          </p>
        </div>

        <Badge
          variant={healthStatus === 'HEALTHY' ? 'emerald' : healthStatus === 'WATCH' ? 'amber' : 'rose'}
          className="py-2 px-4 text-xs font-bold gap-2"
        >
          <ShieldCheck className="w-4 h-4" />
          {healthStatus} STATUS
        </Badge>
      </div>

      {/* Main Score Overview & Radial Gauge */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <Card variant={healthStatus === 'HEALTHY' ? 'glow-emerald' : 'glow-cyan'} className="flex flex-col items-center justify-center p-8 space-y-4 text-center">
          <div className="relative flex items-center justify-center">
            <svg className="w-44 h-44 transform -rotate-90">
              <circle
                cx="88"
                cy="88"
                r="52"
                stroke="rgba(255, 255, 255, 0.08)"
                strokeWidth="12"
                fill="transparent"
              />
              <circle
                cx="88"
                cy="88"
                r="52"
                stroke={healthStatus === 'HEALTHY' ? '#00E599' : healthStatus === 'WATCH' ? '#FFB800' : '#FF4757'}
                strokeWidth="12"
                fill="transparent"
                strokeDasharray={circumference}
                strokeDashoffset={strokeDashoffset}
                strokeLinecap="square"
                className="transition-all duration-1000 ease-out"
              />
            </svg>
            <div className="absolute flex flex-col items-center justify-center text-center">
              <span className="text-4xl font-black text-white">
                {overallScore}
              </span>
              <span className="text-[10px] text-slate-400 uppercase font-semibold">
                SCORE / 100
              </span>
            </div>
          </div>

          <div className="space-y-1">
            <h3 className="text-lg font-bold text-white uppercase">{healthStatus} RATING</h3>
            <p className="text-xs text-slate-400 max-w-xs">{summaryExplanation}</p>
          </div>
        </Card>

        {/* Positive & Risk Signals Cards */}
        <div className="lg:col-span-2 space-y-4">
          <Card className="space-y-4">
            <div className="flex items-center gap-2 border-b border-white/10 pb-3">
              <CheckCircle2 className="w-4 h-4 text-[#00E599]" />
              <h3 className="text-sm font-bold text-white uppercase tracking-wider">
                Positive Operational Signals ({positiveSignals.length})
              </h3>
            </div>
            <div className="space-y-2 text-xs">
              {positiveSignals.map((signal, idx) => (
                <div key={idx} className="p-3 bg-[#00E599]/5 border border-[#00E599]/20 flex items-start gap-2.5">
                  <CheckCircle2 className="w-4 h-4 text-[#00E599] shrink-0 mt-0.5" />
                  <span className="text-slate-200">{signal}</span>
                </div>
              ))}
            </div>
          </Card>

          {riskSignals.length > 0 && (
            <Card className="space-y-4">
              <div className="flex items-center gap-2 border-b border-white/10 pb-3">
                <AlertTriangle className="w-4 h-4 text-amber-400" />
                <h3 className="text-sm font-bold text-white uppercase tracking-wider">
                  Risk & Attention Factors ({riskSignals.length})
                </h3>
              </div>
              <div className="space-y-2 text-xs">
                {riskSignals.map((signal, idx) => (
                  <div key={idx} className="p-3 bg-amber-500/5 border border-amber-500/20 flex items-start gap-2.5">
                    <AlertTriangle className="w-4 h-4 text-amber-400 shrink-0 mt-0.5" />
                    <span className="text-slate-200">{signal}</span>
                  </div>
                ))}
              </div>
            </Card>
          )}
        </div>
      </section>

      {/* 5 Factor Breakdown Grid */}
      <Card className="space-y-6">
        <div className="flex items-center justify-between border-b border-white/10 pb-4">
          <div className="flex items-center gap-2">
            <Layers className="w-5 h-5 text-[#00F0FF]" />
            <div>
              <h3 className="text-base font-bold text-white uppercase tracking-wider">
                5-Factor Health Pillar Breakdown
              </h3>
              <p className="text-[11px] text-slate-400">
                Deterministic weighting engine computed by Spring Boot service layer
              </p>
            </div>
          </div>
          <Badge variant="cyan">DECIMAL ENGINE</Badge>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-xs">
          {factorScores.map((factor) => {
            const pct = Math.round((factor.score / factor.maxScore) * 100);

            return (
              <div key={factor.factorName} className="p-4 bg-[#07080B] border border-white/10 space-y-3">
                <div className="flex items-center justify-between">
                  <span className="font-bold text-white text-sm">{factor.factorName}</span>
                  <div className="flex items-center gap-2">
                    <Badge
                      variant={
                        factor.trend === 'IMPROVING'
                          ? 'emerald'
                          : factor.trend === 'DETERIORATING'
                          ? 'rose'
                          : 'cyan'
                      }
                      className="text-[10px] gap-1"
                    >
                      {factor.trend === 'IMPROVING' ? (
                        <TrendingUp className="w-3 h-3" />
                      ) : factor.trend === 'DETERIORATING' ? (
                        <TrendingDown className="w-3 h-3" />
                      ) : (
                        <Minus className="w-3 h-3" />
                      )}
                      {factor.trend}
                    </Badge>
                    <span className="font-bold text-[#00F0FF]">
                      {factor.score}/{factor.maxScore}
                    </span>
                  </div>
                </div>

                <div className="h-2 w-full bg-white/5 border border-white/10">
                  <div
                    className={`h-full ${
                      pct >= 80 ? 'bg-[#00E599]' : pct >= 50 ? 'bg-[#00F0FF]' : 'bg-rose-400'
                    }`}
                    style={{ width: `${pct}%` }}
                  ></div>
                </div>

                <p className="text-slate-400 text-[11px] leading-relaxed">{factor.explanation}</p>
              </div>
            );
          })}
        </div>
      </Card>

      {/* Financial Disclaimer Banner */}
      <Card className="bg-[#07080B] border border-white/10 p-4 flex items-start gap-3 text-xs text-slate-400">
        <Lock className="w-5 h-5 text-amber-400 shrink-0 mt-0.5" />
        <div className="space-y-1">
          <span className="font-bold text-white uppercase">Operational Diagnostic Disclaimer</span>
          <p className="text-[11px] leading-relaxed">
            Flowwise Business Health Scores are deterministic operational intelligence indicators computed strictly by Spring Boot `BusinessHealthService` using merchant transaction ledgers. They are NOT credit scores and are NOT intended for credit or underwriting decisions.
          </p>
        </div>
      </Card>
    </div>
  );
}
