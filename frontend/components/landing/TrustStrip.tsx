import React from 'react';
import { Layers, ShieldCheck, Cpu, BarChart3 } from 'lucide-react';

export const TrustStrip: React.FC = () => {
  const capabilities = [
    {
      icon: Layers,
      title: 'Unified Multi-Bank Aggregation',
      description: 'Consolidate multiple merchant current & reserve accounts into one live view.',
    },
    {
      icon: ShieldCheck,
      title: 'Automated Reconciliation',
      description: 'Match bank settlements against GST invoices and vendor payables automatically.',
    },
    {
      icon: Cpu,
      title: 'Predictive Cash Velocity',
      description: 'Model future cash positions based on historical billing patterns and due dates.',
    },
    {
      icon: BarChart3,
      title: 'Real-Time Health Index',
      description: 'Instant liquidity, solvency, and burn rate diagnostics tuned for retail.',
    },
  ];

  return (
    <section className="bg-[#08090C] border-y border-white/10 py-12">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {capabilities.map((cap, idx) => {
            const Icon = cap.icon;
            return (
              <div
                key={idx}
                className="p-5 bg-[#0E1116] border border-white/5 space-y-2 hover:border-[#00F0FF]/40 transition-colors font-mono"
              >
                <div className="w-8 h-8 bg-[#00F0FF]/10 border border-[#00F0FF]/30 flex items-center justify-center text-[#00F0FF]">
                  <Icon className="w-4 h-4" />
                </div>
                <h4 className="text-sm font-bold text-white uppercase tracking-wider">
                  {cap.title}
                </h4>
                <p className="text-xs text-slate-400 font-sans leading-relaxed">
                  {cap.description}
                </p>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
