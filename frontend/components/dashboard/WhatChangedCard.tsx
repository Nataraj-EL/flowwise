'use client';

import React, { useEffect, useState } from 'react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { fetchMerchantTemporalSummary, BackendTemporalSummaryDTO } from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { ArrowUpRight, ArrowDownRight, Minus, Calendar, ShieldAlert, Zap, TrendingUp, CheckCircle2 } from 'lucide-react';

export const WhatChangedCard: React.FC = () => {
  const [temporal, setTemporal] = useState<BackendTemporalSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    async function loadData() {
      try {
        const data = await fetchMerchantTemporalSummary(1);
        setTemporal(data);
      } catch (err) {
        // Fallback gracefully if API is offline
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, []);

  if (loading) {
    return (
      <Card className="h-44 animate-pulse font-mono space-y-3">
        <div className="h-6 bg-white/5 w-1/3"></div>
        <div className="h-20 bg-white/5 w-full"></div>
      </Card>
    );
  }

  if (!temporal || temporal.insufficientHistory) {
    return (
      <Card className="font-mono space-y-2 p-4">
        <div className="flex items-center gap-2 text-xs font-bold text-white uppercase">
          <Calendar className="w-4 h-4 text-[#00F0FF]" />
          <span>What Changed? (Period-over-Period)</span>
        </div>
        <p className="text-xs text-slate-400">
          Single-month transaction ledger active. Accumulate 2+ months of transaction history to unlock period-over-period financial change analytics.
        </p>
      </Card>
    );
  }

  return (
    <Card variant="glow-cyan" className="space-y-4 font-mono">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-3">
        <div className="flex items-center gap-2">
          <TrendingUp className="w-5 h-5 text-[#00F0FF]" />
          <div>
            <h3 className="text-sm font-bold text-white uppercase tracking-wider">
              What Changed? ({temporal.currentMonth} vs {temporal.previousMonth})
            </h3>
            <p className="text-[10px] text-slate-400">
              Spring Boot Period Comparison Engine
            </p>
          </div>
        </div>
        <Badge variant="demo">TEMPORAL ENGINE</Badge>
      </div>

      {/* 3 Metric Change Pills */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
        {/* Inflow Shift */}
        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <div className="flex justify-between text-slate-400 text-[11px]">
            <span>Inflow Movement</span>
            <span className={temporal.inflowDirection === 'UP' ? 'text-[#00F0FF] font-bold' : 'text-slate-400'}>
              {temporal.inflowChangePct >= 0 ? `+${temporal.inflowChangePct}%` : `${temporal.inflowChangePct}%`}
            </span>
          </div>
          <div className="text-sm font-bold text-white flex items-center justify-between">
            <span>{formatINR(temporal.currentInflow)}</span>
            {temporal.inflowDirection === 'UP' ? (
              <ArrowUpRight className="w-4 h-4 text-[#00F0FF]" />
            ) : (
              <ArrowDownRight className="w-4 h-4 text-slate-400" />
            )}
          </div>
        </div>

        {/* Outflow Shift */}
        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <div className="flex justify-between text-slate-400 text-[11px]">
            <span>Outflow Movement</span>
            <span className={temporal.outflowDirection === 'UP' ? 'text-rose-400 font-bold' : 'text-[#00E599] font-bold'}>
              {temporal.outflowChangePct >= 0 ? `+${temporal.outflowChangePct}%` : `${temporal.outflowChangePct}%`}
            </span>
          </div>
          <div className="text-sm font-bold text-white flex items-center justify-between">
            <span>{formatINR(temporal.currentOutflow)}</span>
            {temporal.outflowDirection === 'UP' ? (
              <ArrowUpRight className="w-4 h-4 text-rose-400" />
            ) : (
              <ArrowDownRight className="w-4 h-4 text-[#00E599]" />
            )}
          </div>
        </div>

        {/* Net Cash Shift */}
        <div className="p-3 bg-[#07080B] border border-white/10 space-y-1">
          <div className="flex justify-between text-slate-400 text-[11px]">
            <span>Net Cash Position</span>
            <span className={temporal.netCashDirection === 'UP' ? 'text-[#00E599] font-bold' : 'text-rose-400 font-bold'}>
              {temporal.netCashChangePct >= 0 ? `+${temporal.netCashChangePct}%` : `${temporal.netCashChangePct}%`}
            </span>
          </div>
          <div className="text-sm font-bold text-white flex items-center justify-between">
            <span>{formatINR(temporal.currentNetCash)}</span>
            {temporal.netCashDirection === 'UP' ? (
              <ArrowUpRight className="w-4 h-4 text-[#00E599]" />
            ) : (
              <ArrowDownRight className="w-4 h-4 text-rose-400" />
            )}
          </div>
        </div>
      </div>

      {/* Category Movements & Anomalies */}
      {temporal.categoryMovements && temporal.categoryMovements.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs pt-1">
          {/* Top Category Shifts */}
          <div className="p-3 bg-[#07080B] border border-white/10 space-y-2">
            <span className="text-[10px] uppercase text-slate-400 font-bold flex items-center gap-1">
              <Zap className="w-3.5 h-3.5 text-[#00F0FF]" /> Top Category Shifts
            </span>
            <div className="space-y-1.5 text-[11px]">
              {temporal.categoryMovements.slice(0, 2).map((m) => (
                <div key={m.category} className="flex items-center justify-between text-slate-300">
                  <span className="font-semibold text-white">{m.category}</span>
                  <span className={m.direction === 'INCREASED' ? 'text-rose-400 font-bold' : 'text-[#00E599] font-bold'}>
                    {m.direction === 'INCREASED' ? '+' : '-'}{formatINR(m.changeAmount)} ({m.changePct}%)
                  </span>
                </div>
              ))}
            </div>
          </div>

          {/* Anomaly Indicator */}
          <div className="p-3 bg-[#07080B] border border-white/10 space-y-2">
            <span className="text-[10px] uppercase text-slate-400 font-bold flex items-center gap-1">
              <ShieldAlert className="w-3.5 h-3.5 text-amber-400" /> Temporal Anomaly Diagnostics
            </span>
            <p className="text-[11px] text-slate-300 leading-relaxed">
              {temporal.anomalies[0]}
            </p>
          </div>
        </div>
      )}
    </Card>
  );
};
