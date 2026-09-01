import React from 'react';
import Link from 'next/link';
import { ArrowRight, Wallet, TrendingUp, TrendingDown, Receipt, ShieldCheck, Sparkles, Activity } from 'lucide-react';

export const Hero: React.FC = () => {
  return (
    <section className="relative overflow-hidden pt-20 pb-28 md:pt-28 md:pb-36 bg-[#0B0F19]">
      {/* Background Ambient Radial Glows */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[450px] bg-cyan-500/15 blur-[160px] rounded-full pointer-events-none"></div>
      <div className="absolute bottom-10 right-10 w-[500px] h-[300px] bg-emerald-500/10 blur-[140px] rounded-full pointer-events-none"></div>

      <div className="max-w-6xl mx-auto px-6 lg:px-8 relative z-10 space-y-10 text-center">
        {/* Demo Environment Badge */}
        <div className="inline-flex items-center gap-2.5 px-4 py-1.5 bg-[#141B2D]/90 border border-cyan-500/30 rounded-full text-xs font-semibold text-slate-300 shadow-md">
          <Sparkles className="w-3.5 h-3.5 text-cyan-400" />
          <span>DEMO ENVIRONMENT</span>
          <span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse ml-1"></span>
        </div>

        {/* Hero Title */}
        <h1 className="text-5xl sm:text-7xl lg:text-8xl font-black text-white tracking-tight leading-[1.1] max-w-5xl mx-auto">
          Know your cash.<br />
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 via-emerald-300 to-teal-200">
            Grow your business.
          </span>
        </h1>

        {/* Supporting Copy */}
        <p className="text-slate-300 text-lg sm:text-xl max-w-3xl mx-auto font-normal leading-relaxed">
          Flowwise turns fragmented bank accounts, receivables, and payables into one intelligent financial command center.
        </p>

        {/* CTAs */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-5 pt-4">
          <Link
            href="/dashboard"
            className="w-full sm:w-auto px-8 py-4 bg-gradient-to-r from-cyan-500 to-emerald-400 hover:from-cyan-400 hover:to-emerald-300 text-slate-950 font-bold rounded-2xl text-base transition-all flex items-center justify-center gap-3 shadow-xl shadow-cyan-500/25 hover:shadow-cyan-500/40 hover:scale-[1.02]"
          >
            Explore the Console
            <ArrowRight className="w-5 h-5" />
          </Link>
          <a
            href="#intelligence"
            className="w-full sm:w-auto px-8 py-4 bg-[#141B2D] hover:bg-slate-800 text-slate-200 hover:text-white border border-slate-700/80 rounded-2xl text-base font-semibold transition-all flex items-center justify-center"
          >
            See How It Works
          </a>
        </div>

        {/* Polished Financial Console Preview Window */}
        <div className="pt-12 max-w-6xl mx-auto">
          <div className="bg-[#12192B] border border-slate-700/80 rounded-3xl p-6 sm:p-10 shadow-[0_30px_90px_-20px_rgba(0,0,0,0.8)] space-y-8 text-left relative overflow-hidden backdrop-blur-2xl">
            {/* Window Top Bar */}
            <div className="flex flex-col sm:flex-row sm:items-center justify-between border-b border-slate-800 pb-5 gap-4">
              <div className="flex items-center gap-4">
                <div className="flex items-center gap-2">
                  <div className="w-3 h-3 rounded-full bg-rose-500"></div>
                  <div className="w-3 h-3 rounded-full bg-amber-500"></div>
                  <div className="w-3 h-3 rounded-full bg-emerald-500"></div>
                </div>
                <div className="h-4 w-[1px] bg-slate-800 hidden sm:block"></div>
                <div className="flex items-center gap-2">
                  <Activity className="w-4 h-4 text-cyan-400" />
                  <span className="text-sm font-semibold text-slate-200 font-sans">
                    Apex Retail Solutions
                  </span>
                  <span className="text-xs text-slate-400 font-mono">• Real-Time Cash Command</span>
                </div>
              </div>

              <div className="flex items-center gap-3">
                <span className="inline-flex items-center gap-2 text-xs font-mono font-medium text-emerald-400 bg-emerald-500/10 border border-emerald-500/30 px-3.5 py-1 rounded-full">
                  <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span>
                  Live Command Sync
                </span>
              </div>
            </div>

            {/* 5 Key Metric Cards Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
              {/* Cash Position */}
              <div className="bg-[#0B0F19] border border-slate-800 p-5 rounded-2xl space-y-2 hover:border-cyan-500/40 transition-colors">
                <div className="flex items-center justify-between text-slate-400 text-xs font-semibold uppercase tracking-wider">
                  <span>Cash Position</span>
                  <div className="p-1.5 rounded-lg bg-cyan-500/10 border border-cyan-500/20 text-cyan-400">
                    <Wallet className="w-4 h-4" />
                  </div>
                </div>
                <div className="text-2xl font-black text-white tracking-tight">₹1,42,850</div>
                <div className="text-xs text-emerald-400 font-medium flex items-center gap-1">
                  <span>↑ +4.2%</span>
                  <span className="text-slate-400 font-normal">vs last week</span>
                </div>
              </div>

              {/* Revenue */}
              <div className="bg-[#0B0F19] border border-slate-800 p-5 rounded-2xl space-y-2 hover:border-emerald-500/40 transition-colors">
                <div className="flex items-center justify-between text-slate-400 text-xs font-semibold uppercase tracking-wider">
                  <span>Revenue (30D)</span>
                  <div className="p-1.5 rounded-lg bg-emerald-500/10 border border-emerald-500/20 text-emerald-400">
                    <TrendingUp className="w-4 h-4" />
                  </div>
                </div>
                <div className="text-2xl font-black text-emerald-400 tracking-tight">₹3,85,000</div>
                <div className="text-xs text-slate-400 font-medium">142 Settled Invoices</div>
              </div>

              {/* Expenses */}
              <div className="bg-[#0B0F19] border border-slate-800 p-5 rounded-2xl space-y-2 hover:border-amber-500/40 transition-colors">
                <div className="flex items-center justify-between text-slate-400 text-xs font-semibold uppercase tracking-wider">
                  <span>Expenses (30D)</span>
                  <div className="p-1.5 rounded-lg bg-amber-500/10 border border-amber-500/20 text-amber-400">
                    <TrendingDown className="w-4 h-4" />
                  </div>
                </div>
                <div className="text-2xl font-black text-slate-200 tracking-tight">₹2,10,400</div>
                <div className="text-xs text-slate-400 font-medium">Operations & Payables</div>
              </div>

              {/* Receivables */}
              <div className="bg-[#0B0F19] border border-slate-800 p-5 rounded-2xl space-y-2 hover:border-purple-500/40 transition-colors">
                <div className="flex items-center justify-between text-slate-400 text-xs font-semibold uppercase tracking-wider">
                  <span>Receivables</span>
                  <div className="p-1.5 rounded-lg bg-purple-500/10 border border-purple-500/20 text-purple-400">
                    <Receipt className="w-4 h-4" />
                  </div>
                </div>
                <div className="text-2xl font-black text-purple-400 tracking-tight">₹84,200</div>
                <div className="text-xs text-slate-400 font-medium">8 Open Invoices</div>
              </div>

              {/* Runway / Health */}
              <div className="bg-[#0B0F19] border border-slate-800 p-5 rounded-2xl space-y-2 hover:border-emerald-500/40 transition-colors col-span-1 sm:col-span-2 lg:col-span-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-semibold uppercase tracking-wider">
                  <span>Runway / Health</span>
                  <div className="p-1.5 rounded-lg bg-emerald-500/10 border border-emerald-500/20 text-emerald-400">
                    <ShieldCheck className="w-4 h-4" />
                  </div>
                </div>
                <div className="text-2xl font-black text-emerald-400 tracking-tight">88 Days</div>
                <div className="text-xs text-emerald-400 font-medium">94.2 Score • Protected</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
