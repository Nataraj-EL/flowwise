import React from 'react';
import Link from 'next/link';
import { SectionHeading } from '@/components/ui/SectionHeading';
import { Button } from '@/components/ui/Button';
import { MetricCard } from '@/components/ui/MetricCard';
import { Badge } from '@/components/ui/Badge';
import { DEMO_METRICS } from '@/lib/mock-data';
import { ArrowRight, LayoutDashboard, ShieldCheck } from 'lucide-react';

export const DashboardPreview: React.FC = () => {
  return (
    <section className="py-20 bg-[#08090C] border-b border-white/10 cyber-grid">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <SectionHeading
          tag="FINANCIAL CONSOLE PREVIEW"
          title="HIGH-DENSITY MERCHANT INTERFACE"
          subtitle="Built like a high-performance trading platform. Essential financial figures are upfront, bold, and readable at a glance."
          align="center"
        />

        {/* Mock Console Container */}
        <div className="bg-[#0E1116] border border-white/20 shadow-2xl p-4 sm:p-6 space-y-6">
          <div className="flex items-center justify-between border-b border-white/10 pb-4 font-mono text-xs">
            <div className="flex items-center gap-3">
              <div className="w-3 h-3 bg-rose-500 rounded-full"></div>
              <div className="w-3 h-3 bg-amber-500 rounded-full"></div>
              <div className="w-3 h-3 bg-[#00E599] rounded-full"></div>
              <span className="text-white font-bold ml-2 hidden sm:inline">
                APEX RETAIL SOLUTIONS [DEMO CONSOLE]
              </span>
            </div>
            <div className="flex items-center gap-2">
              <Badge variant="demo">SYNTHETIC ENVIRONMENT</Badge>
              <span className="text-[#00F0FF] font-bold">LIVE FEED</span>
            </div>
          </div>

          {/* 6 Metric Cards Preview */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {DEMO_METRICS.map((m, i) => (
              <MetricCard
                key={i}
                title={m.title}
                formattedValue={m.formattedValue}
                changeMoM={m.changeMoM}
                trend={m.trend}
                subtext={m.subtext}
              />
            ))}
          </div>

          {/* Console CTA */}
          <div className="pt-4 text-center border-t border-white/10">
            <Link href="/dashboard">
              <Button variant="cyan" size="lg" className="gap-2">
                Open Full Interactive Console
                <ArrowRight className="w-4 h-4" />
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
