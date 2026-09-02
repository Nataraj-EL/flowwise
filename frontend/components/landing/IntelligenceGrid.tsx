import React from 'react';
import { Wallet, RefreshCw, LineChart, ShieldCheck } from 'lucide-react';

export const IntelligenceGrid: React.FC = () => {
  const capabilities = [
    {
      icon: Wallet,
      title: 'Track Cash Instantly',
      description: 'See your real cash balance in real time across all bank accounts without logging into multiple apps.',
      badge: 'EASY CASH VIEW',
    },
    {
      icon: RefreshCw,
      title: 'Automatic Bill & Sales Matching',
      description: 'Never worry about missing a payment or invoice. Flowwise matches settlements and expenses automatically.',
      badge: 'ZERO MANUAL MATH',
    },
    {
      icon: LineChart,
      title: 'Predict Future Cash Flow',
      description: 'See how upcoming customer collections and bill payments will affect your cash balance weeks in advance.',
      badge: 'FUTURE PLANNING',
    },
    {
      icon: ShieldCheck,
      title: 'Simple Business Health Score',
      description: 'Get an easy-to-understand score (e.g. 94/100) that confirms your business is safe from cash shortages.',
      badge: 'PEACE OF MIND',
    },
  ];

  return (
    <section id="how-it-works" className="py-24 bg-[#0E1424] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16">
        <div className="text-center space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-cyan-400 uppercase bg-cyan-500/10 px-4 py-1.5 rounded-full border border-cyan-500/20">
            HOW FLOWWISE HELPS YOU
          </span>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            4 simple ways Flowwise helps your business grow.
          </h2>
          <p className="text-slate-300 text-base sm:text-lg font-normal">
            Designed for store owners, merchants, and founders who want financial clarity without complicated accounting software.
          </p>
        </div>

        {/* 2x2 Spaced Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 lg:gap-10">
          {capabilities.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#131A2E] border border-slate-800 p-8 sm:p-10 rounded-3xl space-y-6 hover:border-cyan-500/40 transition-all shadow-xl hover:-translate-y-1 group"
              >
                <div className="flex items-center justify-between">
                  <div className="w-14 h-14 rounded-2xl bg-gradient-to-br from-cyan-500/15 via-teal-500/10 to-emerald-500/15 border border-cyan-500/30 text-cyan-400 flex items-center justify-center group-hover:scale-110 transition-transform">
                    <Icon className="w-7 h-7" />
                  </div>
                  <span className="text-[11px] font-mono font-bold tracking-wider text-slate-400 bg-[#0B0F19] px-3.5 py-1 rounded-full border border-slate-800">
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
