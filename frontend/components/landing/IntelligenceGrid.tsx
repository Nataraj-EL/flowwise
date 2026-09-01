'use client';

import React, { useState } from 'react';
import { SectionHeading } from '@/components/ui/SectionHeading';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { TrendingUp, Activity, ArrowRightLeft, Sparkles, CheckCircle2 } from 'lucide-react';
import { cn } from '@/lib/utils';

export const IntelligenceGrid: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'cashflow' | 'health' | 'txns' | 'forecast'>('cashflow');

  const tabs = [
    { id: 'cashflow', label: 'Cash Flow', icon: TrendingUp },
    { id: 'health', label: 'Business Health', icon: Activity },
    { id: 'txns', label: 'Transactions', icon: ArrowRightLeft },
    { id: 'forecast', label: 'Forecasting', icon: Sparkles },
  ];

  const content = {
    cashflow: {
      title: 'Real-Time Cash Inflow & Outflow Visibility',
      description:
        'Track exact daily, weekly, and monthly cash movements. Flowwise categorizes settlements, vendor dues, and operating expenses automatically.',
      highlights: [
        'Multi-account current balance consolidation',
        'Automatic recurring expense detection',
        'Net cash flow trend analysis',
      ],
      tag: 'CASH FLOW ENGINE',
    },
    health: {
      title: 'Algorithmic Business Health Scoring',
      description:
        'Know your exact financial health (0–100) combining liquidity ratios, working capital cycles, and cash runway projections.',
      highlights: [
        'Instant liquidity velocity gauge',
        'Automated runway burn alert system',
        'Solvency risk mitigation indicators',
      ],
      tag: 'HEALTH INDEX ENGINE',
    },
    txns: {
      title: 'Unified Merchant Transaction Feed',
      description:
        'Eliminate manual ledger entry. View all bank deposits, UPI settlements, and vendor payables in one high-density feed.',
      highlights: [
        'GSTIN tagged invoice reconciliation',
        'Pending vs Settled payment tracking',
        'Searchable transaction audit trail',
      ],
      tag: 'TRANSACTION ENGINE',
    },
    forecast: {
      title: 'Predictive Working Capital Forecasting',
      description:
        'Evaluate working capital needs before making major inventory or expansion commitments. Test "What If" financial decisions.',
      highlights: [
        '30-day cash position forward projection',
        'Inventory affordability assessment',
        'Late-payment risk impact modeling',
      ],
      tag: 'FORECASTING ENGINE',
    },
  };

  const activeContent = content[activeTab];

  return (
    <section id="intelligence" className="py-20 bg-[#060709] border-b border-white/10">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <SectionHeading
          tag="PRODUCT INTELLIGENCE"
          title="ENGINEERED FOR MERCHANT COMMAND"
          subtitle="Four core intelligence pillars designed to give business owners total financial control."
          align="center"
        />

        {/* Tab Navigation */}
        <div className="flex flex-wrap items-center justify-center gap-2 font-mono">
          {tabs.map((t) => {
            const Icon = t.icon;
            const isActive = activeTab === t.id;
            return (
              <button
                key={t.id}
                onClick={() => setActiveTab(t.id as any)}
                className={cn(
                  'flex items-center gap-2 px-5 py-3 text-xs uppercase font-bold tracking-wider border transition-all duration-200',
                  isActive
                    ? 'bg-[#0E1116] text-[#00F0FF] border-[#00F0FF] shadow-[0_0_15px_rgba(0,240,255,0.2)]'
                    : 'bg-transparent text-slate-400 border-white/10 hover:text-white hover:border-white/30'
                )}
              >
                <Icon className="w-4 h-4" />
                <span>{t.label}</span>
              </button>
            );
          })}
        </div>

        {/* Active Tab Panel */}
        <Card variant="glow-cyan" className="p-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 items-center">
            <div className="space-y-6">
              <Badge variant="cyan">{activeContent.tag}</Badge>
              <h3 className="text-2xl sm:text-3xl font-black text-white uppercase font-mono tracking-tight">
                {activeContent.title}
              </h3>
              <p className="text-slate-300 font-sans text-base leading-relaxed">
                {activeContent.description}
              </p>
              <div className="space-y-3 font-mono text-xs">
                {activeContent.highlights.map((h, i) => (
                  <div key={i} className="flex items-center gap-3 text-slate-200">
                    <CheckCircle2 className="w-4 h-4 text-[#00E599] shrink-0" />
                    <span>{h}</span>
                  </div>
                ))}
              </div>
            </div>

            {/* Visual Graphic Stub */}
            <div className="bg-[#07080B] border border-white/10 p-6 space-y-4 font-mono text-xs text-slate-400">
              <div className="flex items-center justify-between border-b border-white/10 pb-3">
                <span className="text-[#00F0FF] font-bold">SYSTEM MODULE OUTPUT</span>
                <span className="text-[10px] text-slate-500">LIVE DEMO INTERFACE</span>
              </div>
              <div className="space-y-2">
                <div className="flex justify-between p-2 bg-white/5">
                  <span>Engine State:</span>
                  <span className="text-[#00E599] font-bold">OPERATIONAL</span>
                </div>
                <div className="flex justify-between p-2 bg-white/5">
                  <span>Reconciliation Mode:</span>
                  <span className="text-white">AUTOMATED</span>
                </div>
                <div className="flex justify-between p-2 bg-white/5">
                  <span>Latency Target:</span>
                  <span className="text-white">&lt; 100ms</span>
                </div>
              </div>
            </div>
          </div>
        </Card>
      </div>
    </section>
  );
};
