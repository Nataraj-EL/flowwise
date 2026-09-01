import React from 'react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { DEMO_HEALTH } from '@/lib/mock-data';
import { formatINR } from '@/lib/utils';
import { Activity, ShieldCheck, Zap, AlertTriangle } from 'lucide-react';

export const BusinessHealthCard: React.FC = () => {
  const { overallScore, liquidityScore, solvencyScore, runwayMonths, burnRateMonthly, statusText } = DEMO_HEALTH;

  // SVG Radial calculation
  const circumference = 2 * Math.PI * 40;
  const strokeDashoffset = circumference - (overallScore / 100) * circumference;

  return (
    <Card variant="glow-emerald" className="flex flex-col justify-between space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <Activity className="w-5 h-5 text-[#00E599]" />
          <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
            Business Health Index
          </h3>
        </div>
        <Badge variant="emerald" className="gap-1">
          <ShieldCheck className="w-3.5 h-3.5" />
          {statusText} STATUS
        </Badge>
      </div>

      {/* Main Gauge & Primary Readout */}
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
              stroke="#00E599"
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

        {/* Breakdown Stats */}
        <div className="space-y-4 font-mono">
          <div>
            <div className="flex justify-between text-xs text-slate-300 mb-1">
              <span className="flex items-center gap-1.5">
                <Zap className="w-3.5 h-3.5 text-[#00F0FF]" />
                Liquidity Velocity
              </span>
              <span className="font-bold text-[#00F0FF]">{liquidityScore}%</span>
            </div>
            <div className="h-1.5 w-full bg-white/5 border border-white/10">
              <div
                className="h-full bg-[#00F0FF] shadow-[0_0_8px_#00F0FF]"
                style={{ width: `${liquidityScore}%` }}
              ></div>
            </div>
          </div>

          <div>
            <div className="flex justify-between text-xs text-slate-300 mb-1">
              <span className="flex items-center gap-1.5">
                <ShieldCheck className="w-3.5 h-3.5 text-[#00E599]" />
                Solvency Cushion
              </span>
              <span className="font-bold text-[#00E599]">{solvencyScore}%</span>
            </div>
            <div className="h-1.5 w-full bg-white/5 border border-white/10">
              <div
                className="h-full bg-[#00E599] shadow-[0_0_8px_#00E599]"
                style={{ width: `${solvencyScore}%` }}
              ></div>
            </div>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
            <div className="flex justify-between text-xs text-slate-400">
              <span>Projected Cash Runway</span>
              <span className="text-white font-bold">{runwayMonths} Months</span>
            </div>
            <div className="flex justify-between text-[11px] text-slate-500">
              <span>Monthly Expense Burn</span>
              <span>{formatINR(burnRateMonthly)}</span>
            </div>
          </div>
        </div>
      </div>

      {/* Health Insight Footer Note */}
      <div className="text-xs text-slate-400 font-mono flex items-center gap-2 border-t border-white/10 pt-3">
        <AlertTriangle className="w-4 h-4 text-amber-400 shrink-0" />
        <span>
          Liquidity ratio indicates healthy operational runway. Payables due in 7 days comfortably covered by current reserves.
        </span>
      </div>
    </Card>
  );
};
