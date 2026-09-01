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
    <section id="merchants" className="py-24 bg-[#07090E] border-t border-slate-800/60">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <div className="text-center space-y-3">
          <h2 className="text-3xl sm:text-4xl font-extrabold text-white tracking-tight">
            Designed for merchant outcomes.
          </h2>
          <p className="text-slate-400 text-base max-w-md mx-auto">
            Three simple steps to financial clarity and peace of mind.
          </p>
        </div>

        {/* 3 Outcome Columns: SEE / UNDERSTAND / PLAN */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 lg:gap-8">
          {outcomes.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#101625] border border-slate-800/80 p-8 rounded-2xl space-y-4 hover:border-slate-700/80 transition-all shadow-lg flex flex-col justify-between group"
              >
                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 bg-cyan-500/10 px-3 py-1 rounded-full border border-cyan-500/20">
                      {item.tag}
                    </span>
                    <span className="text-xs font-mono font-semibold text-slate-500">{item.step}</span>
                  </div>

                  <h3 className="text-lg font-bold text-white leading-snug tracking-tight">{item.heading}</h3>
                  <p className="text-slate-400 text-sm leading-relaxed">{item.details}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
