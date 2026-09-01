import React from 'react';
import { MetricCard } from '@/components/ui/MetricCard';
import { BusinessHealthCard } from '@/components/dashboard/BusinessHealthCard';
import { AskFlowwisePanel } from '@/components/dashboard/AskFlowwisePanel';
import { CashFlowChart } from '@/components/dashboard/CashFlowChart';
import { RecentTransactions } from '@/components/dashboard/RecentTransactions';
import { DEMO_METRICS } from '@/lib/mock-data';
import { Badge } from '@/components/ui/Badge';
import { Activity, ShieldCheck, Sparkles } from 'lucide-react';

export default function DashboardOverviewPage() {
  return (
    <div className="space-y-8">
      {/* Page Title & Environment Notice */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase font-mono tracking-tight">
              Console Overview
            </h1>
            <Badge variant="demo" className="hidden sm:inline-flex">
              DEMO DATA
            </Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Real-time financial status for <span className="text-white font-bold">Apex Retail Solutions [DEMO]</span>
          </p>
        </div>

        <div className="flex items-center gap-3 text-xs font-mono">
          <div className="p-2.5 bg-[#0E1116] border border-white/10 flex items-center gap-2 text-slate-300">
            <ShieldCheck className="w-4 h-4 text-[#00E599]" />
            <span>3 Bank Accounts Linked</span>
          </div>
        </div>
      </div>

      {/* 6 Primary Financial Metric Cards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {DEMO_METRICS.map((metric, index) => (
          <MetricCard
            key={index}
            title={metric.title}
            formattedValue={metric.formattedValue}
            changeMoM={metric.changeMoM}
            trend={metric.trend}
            subtext={metric.subtext}
            highlightColor={
              metric.title.includes('Cash')
                ? 'cyan'
                : metric.title.includes('Revenue')
                ? 'emerald'
                : 'neutral'
            }
          />
        ))}
      </section>

      {/* Core Intelligence Grid: Business Health & Ask Flowwise Panel */}
      <section className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <BusinessHealthCard />
        <AskFlowwisePanel />
      </section>

      {/* Analytics & Transactions Feed */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <CashFlowChart />
        </div>
        <div className="lg:col-span-1">
          <RecentTransactions />
        </div>
      </section>
    </div>
  );
}
