import React from 'react';
import Link from 'next/link';
import { ArrowRight, LayoutDashboard, ShieldCheck, Zap, LineChart } from 'lucide-react';

export const DashboardPreview: React.FC = () => {
  return (
    <section id="product" className="py-24 bg-[#0B0F19] border-t border-slate-800/60">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 space-y-10 text-center">
        <div className="space-y-3">
          <h2 className="text-3xl sm:text-4xl font-extrabold text-white tracking-tight">
            Your financial command center.
          </h2>
          <p className="text-slate-400 text-base max-w-md mx-auto">
            One workspace for the financial signals that matter most.
          </p>
        </div>

        {/* Dashboard Console Mock Graphic */}
        <div className="bg-[#101625] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-[0_20px_50px_-10px_rgba(0,0,0,0.5)] space-y-6 text-left relative overflow-hidden">
          {/* Top Console Navigation Bar */}
          <div className="flex items-center justify-between border-b border-slate-800/80 pb-4">
            <div className="flex items-center gap-3">
              <div className="p-2 bg-cyan-500/10 border border-cyan-500/20 rounded-lg text-cyan-400">
                <LayoutDashboard className="w-5 h-5" />
              </div>
              <div>
                <h4 className="text-xs font-mono font-bold text-white tracking-wide uppercase">FLOWWISE CONSOLE</h4>
                <span className="text-[11px] text-slate-400">Merchant Account ID #1</span>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <span className="px-3 py-1 bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full text-xs font-mono">
                ● Console Active
              </span>
            </div>
          </div>

          {/* Console Cards Preview Grid */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="bg-[#07090E] border border-slate-800/80 p-5 rounded-xl space-y-2">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Liquidity Buffer</span>
                <ShieldCheck className="w-4 h-4 text-emerald-400" />
              </div>
              <div className="text-2xl font-bold text-emerald-400 tracking-tight">₹1,42,850</div>
              <p className="text-xs text-slate-400">14 days runway protection buffer verified.</p>
            </div>

            <div className="bg-[#07090E] border border-slate-800/80 p-5 rounded-xl space-y-2">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Working Capital Score</span>
                <Zap className="w-4 h-4 text-purple-400" />
              </div>
              <div className="text-2xl font-bold text-purple-400 tracking-tight">93.8 / 100</div>
              <p className="text-xs text-slate-400">Optimal payable vs receivable alignment.</p>
            </div>

            <div className="bg-[#07090E] border border-slate-800/80 p-5 rounded-xl space-y-2">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Forecasted Runway</span>
                <LineChart className="w-4 h-4 text-cyan-400" />
              </div>
              <div className="text-2xl font-bold text-cyan-400 tracking-tight">4.8 Months</div>
              <p className="text-xs text-slate-400">Zero deficit projected over 90D horizon.</p>
            </div>
          </div>

          {/* CTA Link inside visual */}
          <div className="pt-3 flex items-center justify-between border-t border-slate-800/80 text-xs">
            <span className="text-slate-400">Financial Intelligence Console</span>
            <Link href="/dashboard" className="text-cyan-400 hover:text-cyan-300 font-semibold flex items-center gap-1.5 transition-colors">
              Open Dashboard
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
