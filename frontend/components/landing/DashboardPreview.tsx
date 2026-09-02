import React from 'react';
import Link from 'next/link';
import { ArrowRight, LayoutDashboard, ShieldCheck, Zap, LineChart, CheckCircle } from 'lucide-react';

export const DashboardPreview: React.FC = () => {
  return (
    <section id="features" className="py-24 bg-[#0B0F19] border-t border-slate-800/80">
      <div className="max-w-6xl mx-auto px-6 lg:px-8 space-y-16 text-center">
        <div className="space-y-4 max-w-3xl mx-auto">
          <span className="text-xs font-mono font-bold tracking-widest text-emerald-400 uppercase bg-emerald-500/10 px-4 py-1.5 rounded-full border border-emerald-500/20">
            SIMPLE WORKSPACE
          </span>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight leading-tight">
            Your complete financial command center.
          </h2>
          <p className="text-slate-300 text-base sm:text-lg font-normal">
            One clean workspace for the financial numbers that matter most to your business.
          </p>
        </div>

        {/* Console Preview Card */}
        <div className="bg-[#12192B] border border-slate-700/80 rounded-3xl p-6 sm:p-10 shadow-[0_30px_90px_-20px_rgba(0,0,0,0.8)] space-y-8 text-left relative overflow-hidden backdrop-blur-2xl">
          {/* Header Bar */}
          <div className="flex flex-col sm:flex-row sm:items-center justify-between border-b border-slate-800 pb-5 gap-3">
            <div className="flex items-center gap-3">
              <div className="p-2.5 bg-cyan-500/10 border border-cyan-500/20 rounded-xl text-cyan-400">
                <LayoutDashboard className="w-6 h-6" />
              </div>
              <div>
                <h4 className="text-base font-extrabold text-white tracking-tight">FLOWWISE COMMAND CENTER</h4>
                <span className="text-xs text-slate-400">Apex Retail Store • Simple Overview</span>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <span className="px-4 py-1.5 bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 rounded-full text-xs font-semibold flex items-center gap-2">
                <CheckCircle className="w-3.5 h-3.5 text-emerald-400" />
                All Bills Covered
              </span>
            </div>
          </div>

          {/* 3 Simple Non-Technical Cards */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-emerald-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Cash Reserve Protection</span>
                <ShieldCheck className="w-5 h-5 text-emerald-400" />
              </div>
              <div className="text-3xl font-black text-emerald-400 tracking-tight">₹1,42,850</div>
              <p className="text-xs text-slate-300 leading-relaxed">
                You have enough cash reserved to cover all operating costs for the next 14 days.
              </p>
            </div>

            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-purple-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Business Safety Rating</span>
                <Zap className="w-5 h-5 text-purple-400" />
              </div>
              <div className="text-3xl font-black text-purple-400 tracking-tight">93.8 / 100</div>
              <p className="text-xs text-slate-300 leading-relaxed">
                Great job! Your incoming customer collections easily match your upcoming bills.
              </p>
            </div>

            <div className="bg-[#0B0F19] border border-slate-800 p-6 rounded-2xl space-y-3 hover:border-cyan-500/40 transition-colors">
              <div className="flex items-center justify-between text-xs text-slate-400 font-semibold uppercase tracking-wider">
                <span>Financial Safety Buffer</span>
                <LineChart className="w-5 h-5 text-cyan-400" />
              </div>
              <div className="text-3xl font-black text-cyan-400 tracking-tight">4.8 Months</div>
              <p className="text-xs text-slate-300 leading-relaxed">
                Zero money shortage projected over the next 90 days. Your business is in safe hands.
              </p>
            </div>
          </div>

          {/* Interactive Console Link */}
          <div className="pt-4 flex flex-col sm:flex-row items-center justify-between border-t border-slate-800 text-sm gap-4">
            <span className="text-slate-400 text-xs">
              Click below to test out the interactive demo console loaded with sample business data.
            </span>
            <Link
              href="/dashboard"
              className="px-5 py-2.5 bg-gradient-to-r from-cyan-500 to-emerald-400 text-slate-950 font-bold text-xs uppercase tracking-wider rounded-xl flex items-center gap-2 transition-all hover:scale-[1.02] shadow-md"
            >
              Open Interactive Demo
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
