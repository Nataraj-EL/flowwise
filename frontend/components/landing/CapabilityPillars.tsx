import React from 'react';
import { Eye, Brain, Compass } from 'lucide-react';

export const CapabilityPillars: React.FC = () => {
  const outcomes = [
    {
      step: '01',
      tag: 'SEE',
      icon: Eye,
      heading: 'Know your real cash position.',
      details: 'Unify bank accounts, receivables, and vendor payables into a single clear view.',
    },
    {
      step: '02',
      tag: 'UNDERSTAND',
      icon: Brain,
      heading: 'Know what is changing and why.',
      details: 'Detect unusual expense spikes, revenue anomalies, and cash flow trends automatically.',
    },
    {
      step: '03',
      tag: 'PLAN',
      icon: Compass,
      heading: 'Know what to do before cash becomes a problem.',
      details: 'Receive dependency-aware action sequences and forecasting guidance to protect liquidity.',
    },
  ];

  return (
    <section id="merchants" className="py-28 bg-[#0E1424] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16">
        <div className="text-center space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 uppercase bg-cyan-500/10 px-4 py-1.5 rounded-full border border-cyan-500/20">
            MERCHANT VALUE
          </span>
          <h2 className="text-4xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            Designed for merchant outcomes.
          </h2>
          <p className="text-slate-300 text-lg font-normal">
            Three simple steps to financial clarity and peace of mind.
          </p>
        </div>

        {/* 3 Outcome Columns: SEE / UNDERSTAND / PLAN */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {outcomes.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#131A2E] border border-slate-800 p-10 rounded-3xl space-y-6 hover:border-cyan-500/40 transition-all shadow-xl hover:-translate-y-1 group flex flex-col justify-between"
              >
                <div className="space-y-6">
                  <div className="flex items-center justify-between">
                    <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-cyan-500/15 to-emerald-500/15 border border-cyan-500/30 text-cyan-400 flex items-center justify-center group-hover:scale-110 transition-transform">
                      <Icon className="w-6 h-6" />
                    </div>
                    <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 bg-cyan-500/10 px-3.5 py-1 rounded-full border border-cyan-500/20">
                      {item.step} • {item.tag}
                    </span>
                  </div>

                  <h3 className="text-2xl font-bold text-white leading-snug tracking-tight">{item.heading}</h3>
                  <p className="text-slate-300 text-base leading-relaxed">{item.details}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
