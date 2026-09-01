import React from 'react';
import { SectionHeading } from '@/components/ui/SectionHeading';
import { Card } from '@/components/ui/Card';
import { Zap, ShieldCheck, PieChart, Landmark, Lock, Sparkles } from 'lucide-react';

export const CapabilityPillars: React.FC = () => {
  const pillars = [
    {
      icon: Zap,
      title: 'Instant Cash Visibility',
      description: 'See total available cash across all business accounts in real time without manual spreadsheet exports.',
    },
    {
      icon: ShieldCheck,
      title: 'Zero Ledger Drift',
      description: 'Automatic matching ensures your bank balances, GST records, and internal accounts remain strictly synchronized.',
    },
    {
      icon: PieChart,
      title: 'Predictive Working Capital',
      description: 'Know how inventory orders or vendor payments will affect your cash balance 30 to 90 days in advance.',
    },
    {
      icon: Landmark,
      title: 'Multi-Entity Banking Support',
      description: 'Manage multiple retail outlets, franchises, or subsidiaries from a single unified merchant workspace.',
    },
    {
      icon: Lock,
      title: 'PostgreSQL Architecture',
      description: 'Built on Spring Boot JPA and PostgreSQL schema design for enterprise data security and audit compliance.',
    },
    {
      icon: Sparkles,
      title: 'Ask Flowwise Engine (Sprint 2)',
      description: 'Query business metrics in plain English. Future sprints unlock automated AI reasoning over financial data.',
    },
  ];

  return (
    <section id="merchants" className="py-20 bg-[#060709] border-b border-white/10">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <SectionHeading
          tag="CORE CAPABILITIES"
          title="YOUR BUSINESS, UNDERSTOOD."
          subtitle="Designed specifically for merchants who need clarity over complex, fast-moving financial cash flows."
          align="center"
        />

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {pillars.map((p, idx) => {
            const Icon = p.icon;
            return (
              <Card key={idx} className="space-y-3 font-mono hover:border-[#00F0FF]/40 transition-colors">
                <div className="w-9 h-9 bg-[#00F0FF]/10 border border-[#00F0FF]/30 flex items-center justify-center text-[#00F0FF]">
                  <Icon className="w-5 h-5" />
                </div>
                <h3 className="text-lg font-bold text-white uppercase tracking-wider">
                  {p.title}
                </h3>
                <p className="text-xs text-slate-300 font-sans leading-relaxed">
                  {p.description}
                </p>
              </Card>
            );
          })}
        </div>
      </div>
    </section>
  );
};
