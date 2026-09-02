import React from 'react';
import { Eye, Brain, Compass } from 'lucide-react';

export const CapabilityPillars: React.FC = () => {
  const outcomes = [
    {
      step: '01',
      tag: 'SEE',
      icon: Eye,
      heading: 'Know your real cash position.',
      details: 'See all your bank accounts, customer payments, and supplier bills in one clear view.',
    },
    {
      step: '02',
      tag: 'UNDERSTAND',
      icon: Brain,
      heading: 'Know what is changing & why.',
      details: 'Catch unexpected bill increases, late customer payments, and cash trends early.',
    },
    {
      step: '03',
      tag: 'ACT',
      icon: Compass,
      heading: 'Know what to do next.',
      details: 'Get clear guidance on which bills to pay first so your business never runs out of cash.',
    },
  ];

  return (
    <section id="benefits" className="py-24 bg-[#0E1424] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16">
        <div className="text-center space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 uppercase bg-cyan-500/10 px-4 py-1.5 rounded-full border border-cyan-500/20">
            BUSINESS BENEFIT
          </span>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            Financial peace of mind in 3 simple steps.
          </h2>
          <p className="text-slate-300 text-base sm:text-lg font-normal">
            No accounting degree required. Flowwise simplifies cash flow management for everyone.
          </p>
        </div>

        {/* 3 Outcome Columns */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {outcomes.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#131A2E] border border-slate-800 p-8 sm:p-10 rounded-3xl space-y-6 hover:border-cyan-500/40 transition-all shadow-xl hover:-translate-y-1 group flex flex-col justify-between"
              >
                <div className="space-y-6">
                  <div className="flex items-center justify-between">
                    <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-cyan-500/15 to-emerald-500/15 border border-cyan-500/30 text-cyan-400 flex items-center justify-center group-hover:scale-110 transition-transform">
                      <Icon className="w-6 h-6" />
                    </div>
                    <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 bg-cyan-500/10 px-3.5 py-1 rounded-full border border-cyan-500/20">
                      STEP {item.step} • {item.tag}
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
