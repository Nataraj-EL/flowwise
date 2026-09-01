'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { fetchMerchantHealth, BackendBusinessHealthDTO } from '@/lib/api';
import { DEMO_HEALTH } from '@/lib/mock-data';
import { Activity, ShieldCheck, Zap, AlertTriangle, ExternalLink, CheckCircle2 } from 'lucide-react';

export const BusinessHealthCard: React.FC = () => {
  const [healthData, setHealthData] = useState<BackendBusinessHealthDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    async function loadData() {
      try {
        const data = await fetchMerchantHealth(1);
        setHealthData(data);
      } catch (err) {
        // Fallback to static demo baseline if API is offline
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, []);

  const overallScore = healthData ? healthData.overallScore : DEMO_HEALTH.overallScore;
  const statusText = healthData ? healthData.healthStatus : DEMO_HEALTH.statusText;
  const factors = healthData ? healthData.factorScores : [];

  // SVG Radial calculation
  const circumference = 2 * Math.PI * 40;
  const strokeDashoffset = circumference - (overallScore / 100) * circumference;

  return (
    <Card variant={statusText === 'HEALTHY' ? 'glow-emerald' : 'glow-cyan'} className="flex flex-col justify-between space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00E599]" />
          <div>
            <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
              Business Health Index
            </h3>
            <p className="text-[11px] text-slate-400 font-mono">
              Spring Boot Deterministic Diagnostics Engine
            </p>
          </div>
        </div>
        <div className="flex items-center gap-2">
          <Badge variant={statusText === 'HEALTHY' ? 'emerald' : statusText === 'WATCH' ? 'amber' : 'rose'} className="gap-1">
            <ShieldCheck className="w-3.5 h-3.5" />
            {statusText}
          </Badge>
          <Link href="/dashboard/health" className="text-xs font-mono text-[#00F0FF] hover:underline flex items-center gap-1">
            <span>Console</span>
            <ExternalLink className="w-3.5 h-3.5" />
          </Link>
        </div>
      </div>

      {loading ? (
        <div className="h-48 bg-[#07080B] animate-pulse"></div>
      ) : (
        /* Main Gauge & Primary Readout */
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 items-center">
          {/* SVG Radial Gauge */}
          <div className="flex items-center justify-center p-4 bg-[#07080B] border border-white/5 relative">
            <svg className="w-36 h-36 transform -rotate-90">
              <circle
                cx="72"
                cy="72"
                r="40"
                stroke="rgba(255, 255, 255, 0.08)"
                strokeWidth="10"
                fill="transparent"
              />
              <circle
                cx="72"
                cy="72"
                r="40"
                stroke={statusText === 'HEALTHY' ? '#00E599' : statusText === 'WATCH' ? '#FFB800' : '#FF4757'}
                strokeWidth="10"
                fill="transparent"
                strokeDasharray={circumference}
                strokeDashoffset={strokeDashoffset}
                strokeLinecap="square"
                className="transition-all duration-1000 ease-out"
              />
            </svg>
            <div className="absolute flex flex-col items-center justify-center text-center font-mono">
              <span className="text-3xl font-black text-white text-glow-emerald">
                {overallScore}
              </span>
              <span className="text-[10px] text-slate-400 uppercase font-semibold">
                OUT OF 100
              </span>
            </div>
          </div>

          {/* Factor Scores Breakdown */}
          <div className="space-y-3 font-mono">
            {factors.length > 0 ? (
              factors.slice(0, 3).map((f) => {
                const pct = Math.round((f.score / f.maxScore) * 100);
                return (
                  <div key={f.factorName}>
                    <div className="flex justify-between text-xs text-slate-300 mb-1">
                      <span>{f.factorName}</span>
                      <span className="font-bold text-[#00F0FF]">
                        {f.score}/{f.maxScore}
                      </span>
                    </div>
                    <div className="h-1.5 w-full bg-white/5 border border-white/10">
                      <div
                        className="h-full bg-[#00F0FF]"
                        style={{ width: `${pct}%` }}
                      ></div>
                    </div>
                  </div>
                );
              })
            ) : (
              <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-slate-400">
                Deterministic 5-factor scoring model initialized.
              </div>
            )}
          </div>
        </div>
      )}

      {/* Positive & Risk Signals */}
      {healthData && (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs font-mono border-t border-white/10 pt-4">
          {healthData.positiveSignals.length > 0 && (
            <div className="p-2.5 bg-[#00E599]/5 border border-[#00E599]/20 space-y-1">
              <span className="text-[#00E599] font-bold flex items-center gap-1 text-[11px]">
                <CheckCircle2 className="w-3.5 h-3.5" /> Positive Signal
              </span>
              <p className="text-[11px] text-slate-300">{healthData.positiveSignals[0]}</p>
            </div>
          )}
          {healthData.riskSignals.length > 0 && (
            <div className="p-2.5 bg-rose-500/5 border border-rose-500/20 space-y-1">
              <span className="text-rose-400 font-bold flex items-center gap-1 text-[11px]">
                <AlertTriangle className="w-3.5 h-3.5" /> Risk Factor
              </span>
              <p className="text-[11px] text-slate-300">{healthData.riskSignals[0]}</p>
            </div>
          )}
        </div>
      )}
    </Card>
  );
};
