import React from 'react';
import Link from 'next/link';
import { ArrowRight, LayoutDashboard, ShieldCheck, Zap, LineChart } from 'lucide-react';

export const DashboardPreview: React.FC = () => {
  return (
    <section id="product" className="py-28 bg-[#0B0F19] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16 text-center">
        <div className="space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-emerald-400 uppercase bg-emerald-500/10 px-4 py-1.5 rounded-full border border-emerald-500/20">
            PRODUCT PREVIEW
          </span>
          <h2 className="text-4xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            Your financial command center.
          </h2>
          <p className="text-slate-300 text-lg font-normal">
            One workspace for the financial signals that matter most.
          </p>
        </div>

        {/* High-Impact Console Window Graphic */}
        <div className="bg-[#12192B] border border-slate-700/80 rounded-3xl p-8 sm:p-10 shadow-[0_30px_90px_-20px_rgba(0,0,0,0.8)] space-y-8 text-left relative overflow-hidden backdrop-blur-2xl">
          {/* Top Bar */}
          <div className="flex items-center justify-between border-b border-slate-800 pb-5">
            <div className="flex items-center gap-3">
              <div className="p-2.5 bg-cyan-500/10 border border-cyan-500/20 rounded-xl text-cyan-400">
                <LayoutDashboard className="w-6 h-6" />
              </div>
              <div>
                <h4 className="text-base font-extrabold text-white tracking-tight">FLOWWISE CONSOLE</h4>
                <span className="text-xs text-slate-400 font-mono">Merchant Account ID #1 • Apex Retail Solutions</span>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <span className="px-4 py-1.5 bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full text-xs font-mono font-semibold">
                ● Console Active
              </span>
            </div>
          </div>

          {/* Cards Grid */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-emerald-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Liquidity Buffer</span>
                <ShieldCheck className="w-5 h-5 text-emerald-400" />
              </div>
              <div className="text-3xl font-black text-emerald-400 tracking-tight">₹1,42,850</div>
              <p className="text-xs text-slate-400 leading-relaxed">14 days runway protection buffer verified across linked merchant accounts.</p>
            </div>

            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-purple-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Working Capital Score</span>
                <Zap className="w-5 h-5 text-purple-400" />
              </div>
              <div className="text-3xl font-black text-purple-400 tracking-tight">93.8 / 100</div>
              <p className="text-xs text-slate-400 leading-relaxed">Optimal payable vs receivable alignment with automated settlement matching.</p>
            </div>

            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-cyan-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Forecasted Runway</span>
                <LineChart className="w-5 h-5 text-cyan-400" />
              </div>
              <div className="text-3xl font-black text-cyan-400 tracking-tight">4.8 Months</div>
              <p className="text-xs text-slate-400 leading-relaxed">Zero cash deficit projected across 30D, 60D, and 90D advisory horizons.</p>
            </div>
          </div>

          {/* Console Link */}
          <div className="pt-4 flex items-center justify-between border-t border-slate-800 text-sm">
            <span className="text-slate-400 font-mono text-xs">Interactive Financial Intelligence Console</span>
            <Link
              href="/dashboard"
              className="px-5 py-2.5 bg-cyan-500/10 hover:bg-cyan-500/20 text-cyan-400 border border-cyan-500/30 rounded-xl font-bold text-xs uppercase tracking-wider flex items-center gap-2 transition-all"
            >
              Open Interactive Console
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
