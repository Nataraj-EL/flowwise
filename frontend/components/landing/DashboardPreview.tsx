import React from 'react';
import Link from 'next/link';
import { ArrowRight, LayoutDashboard, ShieldCheck, Zap, LineChart } from 'lucide-react';

export const DashboardPreview: React.FC = () => {
  return (
    <section id="product" className="py-20 bg-[#060709]">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 space-y-10 text-center">
        <div className="space-y-3">
          <h2 className="text-2xl sm:text-4xl font-extrabold text-white tracking-tight">
            Your financial command center.
          </h2>
          <p className="text-slate-400 text-sm sm:text-base max-w-lg mx-auto">
            One workspace for the financial signals that matter most.
          </p>
        </div>

        {/* Dashboard Console Graphic Visual */}
        <div className="bg-[#121622] border border-slate-800 rounded-2xl p-6 md:p-8 shadow-2xl space-y-6 text-left relative overflow-hidden">
          {/* Top Console Navigation Bar Mock */}
          <div className="flex items-center justify-between border-b border-slate-800/80 pb-4">
            <div className="flex items-center gap-3">
              <div className="p-2 bg-cyan-500/10 border border-cyan-500/20 rounded-lg text-cyan-400">
                <LayoutDashboard className="w-5 h-5" />
              </div>
              <div>
                <h4 className="text-sm font-bold text-white font-mono">FLOWWISE CONSOLE</h4>
                <span className="text-[11px] text-slate-400">Merchant Account ID #1 • Demo Environment</span>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <span className="px-3 py-1 bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full text-xs font-mono">
                ● System Active
              </span>
            </div>
          </div>

          {/* Console Cards Preview Grid */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-[#0A0D14] border border-slate-800 p-5 rounded-xl space-y-3">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Liquidity Buffer</span>
                <ShieldCheck className="w-4 h-4 text-emerald-400" />
              </div>
              <div className="text-2xl font-extrabold text-emerald-400">₹1,42,850</div>
              <p className="text-xs text-slate-400">14 days runway protection buffer verified.</p>
            </div>

            <div className="bg-[#0A0D14] border border-slate-800 p-5 rounded-xl space-y-3">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Working Capital Score</span>
                <Zap className="w-4 h-4 text-purple-400" />
              </div>
              <div className="text-2xl font-extrabold text-purple-400">93.8 / 100</div>
              <p className="text-xs text-slate-400">Optimal payable vs receivable alignment.</p>
            </div>

            <div className="bg-[#0A0D14] border border-slate-800 p-5 rounded-xl space-y-3">
              <div className="flex items-center justify-between text-xs text-slate-400 font-medium">
                <span>Forecasted Runway</span>
                <LineChart className="w-4 h-4 text-cyan-400" />
              </div>
              <div className="text-2xl font-extrabold text-cyan-400">4.8 Months</div>
              <p className="text-xs text-slate-400">Zero deficit projected over 90D horizon.</p>
            </div>
          </div>

          {/* CTA Link inside visual */}
          <div className="pt-4 flex items-center justify-between border-t border-slate-800/80 text-xs">
            <span className="text-slate-400 font-mono">Interactive Financial Intelligence Console</span>
            <Link href="/dashboard" className="text-cyan-400 hover:text-cyan-300 font-semibold flex items-center gap-1.5">
              Open Dashboard
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
