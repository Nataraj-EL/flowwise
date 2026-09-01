import React from 'react';
import { Eye, Brain, Compass } from 'lucide-react';

export const CapabilityPillars: React.FC = () => {
  const outcomes = [
    {
      step: 'SEE',
      icon: Eye,
      heading: 'Know your real cash position.',
      details: 'Unify bank accounts, receivables, and vendor payables into a single clear view.',
    },
    {
      step: 'UNDERSTAND',
      icon: Brain,
      heading: 'Know what is changing and why.',
      details: 'Detect unusual expense spikes, revenue anomalies, and cash flow trends automatically.',
    },
    {
      step: 'PLAN',
      icon: Compass,
      heading: 'Know what to do before cash becomes a problem.',
      details: 'Receive dependency-aware action sequences and forecasting guidance to protect liquidity.',
    },
  ];

  return (
    <section id="merchants" className="py-20 bg-[#0A0D14] border-t border-slate-800/80">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <div className="text-center space-y-3">
          <h2 className="text-2xl sm:text-4xl font-extrabold text-white tracking-tight">
            Designed for merchant outcomes.
          </h2>
          <p className="text-slate-400 text-sm sm:text-base max-w-lg mx-auto">
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
                className="bg-[#121622] border border-slate-800 p-8 rounded-2xl space-y-4 hover:border-slate-700 transition-all shadow-lg flex flex-col justify-between"
              >
                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 bg-cyan-500/10 px-3 py-1 rounded-full border border-cyan-500/20">
                      {item.step}
                    </span>
                    <Icon className="w-5 h-5 text-slate-400" />
                  </div>

                  <h3 className="text-xl font-bold text-white leading-snug">{item.heading}</h3>
                  <p className="text-slate-400 text-xs sm:text-sm leading-relaxed">{item.details}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
