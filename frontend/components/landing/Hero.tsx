import React from 'react';
import Link from 'next/link';
import { ArrowRight, Wallet, TrendingUp, TrendingDown, Receipt, ShieldCheck } from 'lucide-react';

export const Hero: React.FC = () => {
  return (
    <section className="relative overflow-hidden pt-16 pb-24 md:pt-24 md:pb-32">
      {/* Ambient Gradient Radial Glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[700px] h-[400px] bg-cyan-500/10 blur-[150px] rounded-full pointer-events-none"></div>

      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 space-y-8 text-center">
        {/* Subtle Demo Environment Badge */}
        <div className="inline-flex items-center gap-2 px-3 py-1 bg-[#101625] border border-slate-800 rounded-full text-xs font-medium text-slate-400">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
          <span>DEMO ENVIRONMENT</span>
        </div>

        {/* Strong Headline (Title Case for Modern Aesthetics) */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold text-white tracking-tight leading-[1.15] max-w-4xl mx-auto">
          Know your cash.<br />
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 via-emerald-300 to-teal-200">
            Grow your business.
          </span>
        </h1>

        {/* Short Supporting Copy */}
        <p className="text-slate-400 text-base sm:text-lg max-w-2xl mx-auto font-normal leading-relaxed">
          Flowwise turns fragmented bank accounts, receivables, and payables into one intelligent financial command center.
        </p>

        {/* Primary & Secondary CTAs */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-2">
          <Link
            href="/dashboard"
            className="w-full sm:w-auto px-6 py-3.5 bg-cyan-500 hover:bg-cyan-400 text-slate-950 font-semibold rounded-xl text-sm transition-all flex items-center justify-center gap-2 shadow-lg shadow-cyan-500/20"
          >
            Explore the Console
            <ArrowRight className="w-4 h-4" />
          </Link>
          <a
            href="#intelligence"
            className="w-full sm:w-auto px-6 py-3.5 bg-[#101625] hover:bg-slate-800/80 text-slate-300 hover:text-white border border-slate-800 rounded-xl text-sm font-medium transition-all flex items-center justify-center"
          >
            See How It Works
          </a>
        </div>

        {/* Polished Financial Console Preview (5 Core Metrics Only) */}
        <div className="pt-10 max-w-4xl mx-auto">
          <div className="bg-[#101625] border border-slate-800/80 rounded-2xl p-6 sm:p-8 shadow-[0_25px_60px_-15px_rgba(0,0,0,0.6)] space-y-6 text-left relative">
            {/* Console Header Bar */}
            <div className="flex items-center justify-between border-b border-slate-800/80 pb-4">
              <div className="flex items-center gap-3">
                <div className="flex items-center gap-1.5">
                  <div className="w-2.5 h-2.5 rounded-full bg-rose-500/80"></div>
                  <div className="w-2.5 h-2.5 rounded-full bg-amber-500/80"></div>
                  <div className="w-2.5 h-2.5 rounded-full bg-emerald-500/80"></div>
                </div>
                <span className="text-xs font-mono font-medium text-slate-400 border-l border-slate-800 pl-3">
                  Apex Retail Solutions • Real-Time Sync
                </span>
              </div>
              <span className="text-[11px] font-mono text-emerald-400 bg-emerald-500/10 border border-emerald-500/20 px-2.5 py-0.5 rounded-full">
                ● Live Command Center
              </span>
            </div>

            {/* 5 Core Metrics Grid */}
            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 gap-3.5">
              {/* Cash */}
              <div className="bg-[#07090E] border border-slate-800/80 p-4 rounded-xl space-y-1.5">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Cash</span>
                  <Wallet className="w-3.5 h-3.5 text-cyan-400" />
                </div>
                <div className="text-lg font-bold text-white tracking-tight">₹1,42,850</div>
                <div className="text-[10px] text-emerald-400 font-mono">+4.2% this week</div>
              </div>

              {/* Revenue */}
              <div className="bg-[#07090E] border border-slate-800/80 p-4 rounded-xl space-y-1.5">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Revenue</span>
                  <TrendingUp className="w-3.5 h-3.5 text-emerald-400" />
                </div>
                <div className="text-lg font-bold text-emerald-400 tracking-tight">₹3,85,000</div>
                <div className="text-[10px] text-slate-400 font-mono">142 Invoices</div>
              </div>

              {/* Expenses */}
              <div className="bg-[#07090E] border border-slate-800/80 p-4 rounded-xl space-y-1.5">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Expenses</span>
                  <TrendingDown className="w-3.5 h-3.5 text-amber-400" />
                </div>
                <div className="text-lg font-bold text-slate-200 tracking-tight">₹2,10,400</div>
                <div className="text-[10px] text-slate-400 font-mono">Operations & Vendors</div>
              </div>

              {/* Receivables */}
              <div className="bg-[#07090E] border border-slate-800/80 p-4 rounded-xl space-y-1.5">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Receivables</span>
                  <Receipt className="w-3.5 h-3.5 text-purple-400" />
                </div>
                <div className="text-lg font-bold text-purple-400 tracking-tight">₹84,200</div>
                <div className="text-[10px] text-slate-400 font-mono">8 Outstanding</div>
              </div>

              {/* Runway / Health */}
              <div className="bg-[#07090E] border border-slate-800/80 p-4 rounded-xl space-y-1.5 col-span-2 sm:col-span-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Runway / Health</span>
                  <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" />
                </div>
                <div className="text-lg font-bold text-emerald-400 tracking-tight">88 Days</div>
                <div className="text-[10px] text-emerald-400 font-mono">94.2 Score</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
