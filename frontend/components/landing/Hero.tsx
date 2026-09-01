import React from 'react';
import Link from 'next/link';
import { ArrowRight, Wallet, TrendingUp, TrendingDown, Receipt, ShieldCheck } from 'lucide-react';

export const Hero: React.FC = () => {
  return (
    <section className="relative overflow-hidden pt-16 pb-20 md:pt-24 md:pb-28">
      {/* Subtle Background Radial Glow */}
      <div className="absolute top-1/3 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[350px] bg-cyan-500/10 blur-[140px] rounded-full pointer-events-none"></div>

      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 space-y-10 text-center">
        {/* Subtle Demo Environment Indicator */}
        <div className="inline-flex items-center gap-2 px-3 py-1 bg-[#121622] border border-slate-800 rounded-full text-xs font-mono text-slate-400">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
          <span>DEMO ENVIRONMENT</span>
        </div>

        {/* Strong Headline */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold text-white tracking-tight leading-tight max-w-4xl mx-auto font-sans">
          KNOW YOUR CASH.<br />
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 via-white to-emerald-400">
            GROW YOUR BUSINESS.
          </span>
        </h1>

        {/* Short Supporting Copy */}
        <p className="text-slate-300 text-base sm:text-lg max-w-2xl mx-auto leading-relaxed">
          Flowwise turns fragmented bank accounts, receivables, and payables into one intelligent financial command center.
        </p>

        {/* Primary & Secondary CTAs */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-2">
          <Link
            href="/dashboard"
            className="w-full sm:w-auto px-6 py-3.5 bg-cyan-600 hover:bg-cyan-500 text-white rounded-xl text-sm font-semibold transition-all flex items-center justify-center gap-2 shadow-xl shadow-cyan-600/20"
          >
            Explore the Console
            <ArrowRight className="w-4 h-4" />
          </Link>
          <a
            href="#intelligence"
            className="w-full sm:w-auto px-6 py-3.5 bg-[#121622] hover:bg-slate-800 text-slate-300 hover:text-white border border-slate-800 rounded-xl text-sm font-semibold transition-all flex items-center justify-center"
          >
            See How It Works
          </a>
        </div>

        {/* Polished Financial Console Preview (5 Core Metrics Only) */}
        <div className="pt-8 max-w-4xl mx-auto">
          <div className="bg-[#121622] border border-slate-800 rounded-2xl p-6 shadow-2xl space-y-6 text-left">
            <div className="flex items-center justify-between border-b border-slate-800/80 pb-4">
              <div className="flex items-center gap-3">
                <div className="w-3 h-3 rounded-full bg-emerald-500/20 border border-emerald-500 flex items-center justify-center">
                  <div className="w-1.5 h-1.5 rounded-full bg-emerald-400"></div>
                </div>
                <span className="text-xs font-mono font-medium text-slate-300">
                  Apex Retail Solutions • Live Cash Snapshot
                </span>
              </div>
              <span className="text-xs font-mono text-emerald-400 bg-emerald-500/10 border border-emerald-500/20 px-2.5 py-0.5 rounded-full">
                Real-Time Sync
              </span>
            </div>

            {/* 5 Key Metric Cards */}
            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 gap-4">
              {/* Cash */}
              <div className="bg-[#0A0D14] border border-slate-800/80 p-4 rounded-xl space-y-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Cash Position</span>
                  <Wallet className="w-3.5 h-3.5 text-cyan-400" />
                </div>
                <div className="text-lg font-extrabold text-white">₹1,42,850</div>
                <div className="text-[10px] text-emerald-400 font-mono">+4.2% this week</div>
              </div>

              {/* Revenue */}
              <div className="bg-[#0A0D14] border border-slate-800/80 p-4 rounded-xl space-y-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Revenue (30D)</span>
                  <TrendingUp className="w-3.5 h-3.5 text-emerald-400" />
                </div>
                <div className="text-lg font-extrabold text-emerald-400">₹3,85,000</div>
                <div className="text-[10px] text-slate-400 font-mono">142 Invoices</div>
              </div>

              {/* Expenses */}
              <div className="bg-[#0A0D14] border border-slate-800/80 p-4 rounded-xl space-y-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Expenses (30D)</span>
                  <TrendingDown className="w-3.5 h-3.5 text-amber-400" />
                </div>
                <div className="text-lg font-extrabold text-slate-200">₹2,10,400</div>
                <div className="text-[10px] text-slate-400 font-mono">Operations & Vendors</div>
              </div>

              {/* Receivables */}
              <div className="bg-[#0A0D14] border border-slate-800/80 p-4 rounded-xl space-y-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Receivables</span>
                  <Receipt className="w-3.5 h-3.5 text-purple-400" />
                </div>
                <div className="text-lg font-extrabold text-purple-400">₹84,200</div>
                <div className="text-[10px] text-slate-400 font-mono">8 Outstanding</div>
              </div>

              {/* Runway / Health */}
              <div className="bg-[#0A0D14] border border-slate-800/80 p-4 rounded-xl space-y-1 col-span-2 sm:col-span-1">
                <div className="flex items-center justify-between text-slate-400 text-xs font-medium">
                  <span>Runway / Health</span>
                  <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" />
                </div>
                <div className="text-lg font-extrabold text-emerald-400">88 Days</div>
                <div className="text-[10px] text-emerald-400 font-mono">94.2 Health Score</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
