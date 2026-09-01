'use client';

import React, { useState } from 'react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { DEMO_CASHFLOW_SERIES } from '@/lib/mock-data';
import { formatINR } from '@/lib/utils';
import { BarChart3, ArrowUpRight, ArrowDownRight } from 'lucide-react';

export const CashFlowChart: React.FC = () => {
  const [hoveredIdx, setHoveredIdx] = useState<number | null>(null);

  const maxVal = 900000; // max scale factor for SVG bar height

  return (
    <Card className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <BarChart3 className="w-5 h-5 text-[#00F0FF]" />
          <div>
            <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
              Cash Flow Trend Analysis
            </h3>
            <p className="text-[11px] text-slate-400 font-mono">
              6-Month Rolling Inflows vs Outflows (Synthetic Data)
            </p>
          </div>
        </div>

        <div className="flex items-center gap-4 text-xs font-mono">
          <div className="flex items-center gap-1.5">
            <span className="w-3 h-3 bg-[#00F0FF] inline-block"></span>
            <span className="text-slate-300">Inflow</span>
          </div>
          <div className="flex items-center gap-1.5">
            <span className="w-3 h-3 bg-[#00E599] inline-block"></span>
            <span className="text-slate-300">Outflow</span>
          </div>
        </div>
      </div>

      {/* SVG Custom Responsive Chart */}
      <div className="relative h-64 w-full bg-[#07080B] border border-white/5 p-4 flex flex-col justify-between">
        {/* Grid Lines */}
        <div className="absolute inset-0 flex flex-col justify-between p-4 pointer-events-none opacity-20">
          <div className="border-b border-white/40 w-full"></div>
          <div className="border-b border-white/20 w-full"></div>
          <div className="border-b border-white/20 w-full"></div>
          <div className="border-b border-white/40 w-full"></div>
        </div>

        {/* Bars Container */}
        <div className="relative flex-1 flex items-end justify-between gap-2 sm:gap-6 pt-6 z-10">
          {DEMO_CASHFLOW_SERIES.map((item, idx) => {
            const inflowHeightPct = (item.inflow / maxVal) * 100;
            const outflowHeightPct = (item.outflow / maxVal) * 100;
            const isHovered = hoveredIdx === idx;

            return (
              <div
                key={item.month}
                onMouseEnter={() => setHoveredIdx(idx)}
                onMouseLeave={() => setHoveredIdx(null)}
                className="flex-1 flex flex-col items-center h-full justify-end group cursor-pointer"
              >
                {/* Tooltip on Hover */}
                {isHovered && (
                  <div className="absolute top-2 bg-[#0E1116] border border-white/20 p-2 text-[11px] font-mono shadow-2xl z-20 space-y-1">
                    <div className="font-bold text-white uppercase border-b border-white/10 pb-1">
                      {item.month} Summary
                    </div>
                    <div className="text-[#00F0FF] flex justify-between gap-4">
                      <span>Inflow:</span>
                      <span>{formatINR(item.inflow)}</span>
                    </div>
                    <div className="text-[#00E599] flex justify-between gap-4">
                      <span>Outflow:</span>
                      <span>{formatINR(item.outflow)}</span>
                    </div>
                    <div className="text-white font-bold border-t border-white/10 pt-1 flex justify-between gap-4">
                      <span>Net Cash:</span>
                      <span>{formatINR(item.netCash)}</span>
                    </div>
                  </div>
                )}

                {/* Bars Pair */}
                <div className="flex items-end gap-1 w-full max-w-[48px] h-full justify-center">
                  {/* Inflow Bar */}
                  <div
                    className="w-full bg-[#00F0FF]/80 group-hover:bg-[#00F0FF] transition-all duration-300 relative"
                    style={{ height: `${inflowHeightPct}%` }}
                  >
                    <div className="absolute top-0 left-0 right-0 h-1 bg-[#00F0FF] shadow-[0_0_8px_#00F0FF]"></div>
                  </div>

                  {/* Outflow Bar */}
                  <div
                    className="w-full bg-[#00E599]/80 group-hover:bg-[#00E599] transition-all duration-300 relative"
                    style={{ height: `${outflowHeightPct}%` }}
                  >
                    <div className="absolute top-0 left-0 right-0 h-1 bg-[#00E599] shadow-[0_0_8px_#00E599]"></div>
                  </div>
                </div>

                {/* Month Label */}
                <span className="text-[11px] font-mono text-slate-400 mt-2 font-bold uppercase group-hover:text-white">
                  {item.month}
                </span>
              </div>
            );
          })}
        </div>
      </div>

      {/* Summary Footer */}
      <div className="grid grid-cols-2 sm:grid-cols-3 gap-4 pt-2 font-mono text-xs">
        <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
          <span className="text-slate-400">Sep Inflow</span>
          <div className="text-base font-bold text-[#00F0FF] flex items-center gap-1">
            <ArrowUpRight className="w-4 h-4 text-[#00F0FF]" />
            {formatINR(842500)}
          </div>
        </div>

        <div className="p-3 bg-[#07080B] border border-white/5 space-y-1">
          <span className="text-slate-400">Sep Outflow</span>
          <div className="text-base font-bold text-[#00E599] flex items-center gap-1">
            <ArrowDownRight className="w-4 h-4 text-[#00E599]" />
            {formatINR(518200)}
          </div>
        </div>

        <div className="col-span-2 sm:col-span-1 p-3 bg-[#07080B] border border-white/5 space-y-1">
          <span className="text-slate-400">Net Surplus</span>
          <div className="text-base font-bold text-white">
            {formatINR(324300)}
          </div>
        </div>
      </div>
    </Card>
  );
};
