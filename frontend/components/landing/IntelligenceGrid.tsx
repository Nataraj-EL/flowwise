import React from 'react';
import { TrendingUp, RefreshCw, LineChart, ShieldCheck } from 'lucide-react';

export const IntelligenceGrid: React.FC = () => {
  const capabilities = [
    {
      icon: TrendingUp,
      title: 'Cash Flow',
      description: 'Understand where money is coming from and where it is going.',
      badge: 'REAL-TIME TRACKING',
    },
    {
      icon: RefreshCw,
      title: 'Reconciliation',
      description: 'Match settlements, invoices, and payables automatically.',
      badge: 'AUTOMATED MATCHING',
    },
    {
      icon: LineChart,
      title: 'Forecasting',
      description: 'See how upcoming payments and collections affect future liquidity.',
      badge: 'PREDICTIVE RUNWAY',
    },
    {
      icon: ShieldCheck,
      title: 'Financial Health',
      description: 'Monitor liquidity, runway, risk, and cash position from one place.',
      badge: 'CONTINUOUS SCORING',
    },
  ];

  return (
    <section id="intelligence" className="py-28 bg-[#0E1424] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16">
        <div className="text-center space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 uppercase bg-cyan-500/10 px-4 py-1.5 rounded-full border border-cyan-500/20">
            INTELLIGENCE MODULES
          </span>
          <h2 className="text-4xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            Financial clarity without the spreadsheet.
          </h2>
          <p className="text-slate-300 text-lg font-normal">
            Essential intelligence tools designed to give merchants complete confidence over cash flow.
          </p>
        </div>

        {/* 2x2 Spaced Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 lg:gap-10">
          {capabilities.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#131A2E] border border-slate-800 p-10 rounded-3xl space-y-6 hover:border-cyan-500/40 transition-all shadow-xl hover:-translate-y-1 group"
              >
                <div className="flex items-center justify-between">
                  <div className="w-14 h-14 rounded-2xl bg-gradient-to-br from-cyan-500/15 via-teal-500/10 to-emerald-500/15 border border-cyan-500/30 text-cyan-400 flex items-center justify-center group-hover:scale-110 transition-transform">
                    <Icon className="w-7 h-7" />
                  </div>
                  <span className="text-[11px] font-mono font-bold tracking-wider text-slate-400 bg-[#0B0F19] px-3 py-1 rounded-full border border-slate-800">
                    {item.badge}
                  </span>
                </div>

                <div className="space-y-2">
                  <h3 className="text-2xl font-bold text-white tracking-tight">{item.title}</h3>
                  <p className="text-slate-300 text-base leading-relaxed">{item.description}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
