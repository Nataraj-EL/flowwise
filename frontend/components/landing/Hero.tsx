import React from 'react';
import Link from 'next/link';
import { ArrowRight, Wallet, ArrowDownLeft, ArrowUpRight, ShieldCheck, Sparkles, CheckCircle2 } from 'lucide-react';

export const Hero: React.FC = () => {
  return (
    <section className="relative overflow-hidden pt-12 pb-20 md:pt-20 md:pb-28 bg-[#0B0F19]">
      {/* Background Radial Glows */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[450px] bg-cyan-500/10 blur-[160px] rounded-full pointer-events-none"></div>
      <div className="absolute bottom-10 right-10 w-[500px] h-[300px] bg-emerald-500/10 blur-[140px] rounded-full pointer-events-none"></div>

      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        {/* Hero Top Content Block */}
        <div className="flex flex-col items-center text-center space-y-6 max-w-4xl mx-auto">
          {/* Badge */}
          <div className="inline-flex items-center gap-2 px-4 py-1.5 bg-[#141B2D] border border-cyan-500/30 rounded-full text-xs font-semibold text-slate-300 shadow-md">
            <Sparkles className="w-3.5 h-3.5 text-cyan-400 shrink-0" />
            <span>Simple Financial Command Center</span>
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse ml-1 shrink-0"></span>
          </div>

          {/* Headline */}
          <h1 className="text-3xl sm:text-5xl lg:text-6xl font-extrabold text-white tracking-tight leading-[1.2] max-w-4xl">
            Take the stress out of your cash flow.<br />
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 via-emerald-300 to-teal-200">
              Understand your numbers in seconds.
            </span>
          </h1>

          {/* Subtitle */}
          <p className="text-slate-300 text-base sm:text-lg max-w-2xl font-normal leading-relaxed">
            Flowwise automatically tracks your cash, unpaid invoices, and upcoming bills in one clean dashboard. No spreadsheets required.
          </p>

          {/* Trust Checkmarks */}
          <div className="flex flex-wrap items-center justify-center gap-4 sm:gap-6 text-xs sm:text-sm text-slate-300 font-medium pt-1">
            <div className="flex items-center gap-2">
              <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
              <span>No accounting background needed</span>
            </div>
            <div className="flex items-center gap-2">
              <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
              <span>Instant live setup</span>
            </div>
            <div className="flex items-center gap-2">
              <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
              <span>100% Secure & Private</span>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-4 w-full sm:w-auto">
            <Link
              href="/dashboard"
              className="w-full sm:w-auto px-7 py-3.5 bg-gradient-to-r from-cyan-500 to-emerald-400 hover:from-cyan-400 hover:to-emerald-300 text-slate-950 font-bold rounded-2xl text-sm sm:text-base transition-all flex items-center justify-center gap-2.5 shadow-xl shadow-cyan-500/25 hover:shadow-cyan-500/40 hover:scale-[1.02]"
            >
              Try Free Interactive Demo
              <ArrowRight className="w-4 h-4 sm:w-5 sm:h-5 shrink-0" />
            </Link>
            <a
              href="#how-it-works"
              className="w-full sm:w-auto px-7 py-3.5 bg-[#141B2D] hover:bg-slate-800 text-slate-200 hover:text-white border border-slate-700/80 rounded-2xl text-sm sm:text-base font-semibold transition-all flex items-center justify-center"
            >
              See How It Works
            </a>
          </div>
        </div>

        {/* Polished Preview Window Card Container (Separated with clean top margin) */}
        <div className="mt-12 sm:mt-16 max-w-5xl mx-auto">
          <div className="bg-[#12192B] border border-slate-700/80 rounded-3xl p-5 sm:p-8 shadow-[0_30px_90px_-20px_rgba(0,0,0,0.8)] space-y-6 text-left backdrop-blur-2xl">
            {/* Window Top Bar */}
            <div className="flex flex-wrap items-center justify-between border-b border-slate-800 pb-4 gap-3">
              <div className="flex items-center gap-3">
                <div className="flex items-center gap-1.5 shrink-0">
                  <div className="w-3 h-3 rounded-full bg-rose-500"></div>
                  <div className="w-3 h-3 rounded-full bg-amber-500"></div>
                  <div className="w-3 h-3 rounded-full bg-emerald-500"></div>
                </div>
                <div className="h-4 w-[1px] bg-slate-800 hidden sm:block"></div>
                <span className="text-xs sm:text-sm font-bold text-white font-sans">
                  Apex Retail Store • Live Business Health
                </span>
              </div>

              <div className="flex items-center gap-2">
                <span className="inline-flex items-center gap-2 text-xs font-semibold text-emerald-400 bg-emerald-500/10 border border-emerald-500/30 px-3 py-1 rounded-full">
                  <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping shrink-0"></span>
                  Financially Safe & Healthy
                </span>
              </div>
            </div>

            {/* 4 Clean Metric Cards */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
              {/* Total Cash */}
              <div className="bg-[#0F172A] border border-slate-800 p-5 rounded-2xl space-y-3 hover:border-cyan-500/40 transition-colors flex flex-col justify-between">
                <div className="flex items-center justify-between text-slate-400 text-xs font-bold uppercase tracking-wider gap-2">
                  <span>Total Cash Available</span>
                  <div className="p-2 rounded-xl bg-cyan-500/10 border border-cyan-500/20 text-cyan-400 shrink-0">
                    <Wallet className="w-4 h-4" />
                  </div>
                </div>
                <div>
                  <div className="text-2xl font-bold text-white tracking-tight">₹1,42,850</div>
                  <div className="text-xs text-emerald-400 font-medium mt-1">Ready in bank accounts</div>
                </div>
              </div>

              {/* Money Coming In */}
              <div className="bg-[#0F172A] border border-slate-800 p-5 rounded-2xl space-y-3 hover:border-emerald-500/40 transition-colors flex flex-col justify-between">
                <div className="flex items-center justify-between text-slate-400 text-xs font-bold uppercase tracking-wider gap-2">
                  <span>Money Coming In</span>
                  <div className="p-2 rounded-xl bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 shrink-0">
                    <ArrowDownLeft className="w-4 h-4" />
                  </div>
                </div>
                <div>
                  <div className="text-2xl font-bold text-emerald-400 tracking-tight">₹3,85,000</div>
                  <div className="text-xs text-slate-400 font-medium mt-1">142 Sales collected</div>
                </div>
              </div>

              {/* Money Going Out */}
              <div className="bg-[#0F172A] border border-slate-800 p-5 rounded-2xl space-y-3 hover:border-amber-500/40 transition-colors flex flex-col justify-between">
                <div className="flex items-center justify-between text-slate-400 text-xs font-bold uppercase tracking-wider gap-2">
                  <span>Money Going Out</span>
                  <div className="p-2 rounded-xl bg-amber-500/10 border border-amber-500/20 text-amber-400 shrink-0">
                    <ArrowUpRight className="w-4 h-4" />
                  </div>
                </div>
                <div>
                  <div className="text-2xl font-bold text-slate-200 tracking-tight">₹2,10,400</div>
                  <div className="text-xs text-slate-400 font-medium mt-1">Rent, Salaries & Vendors</div>
                </div>
              </div>

              {/* Financial Safety */}
              <div className="bg-[#0F172A] border border-slate-800 p-5 rounded-2xl space-y-3 hover:border-emerald-500/40 transition-colors flex flex-col justify-between">
                <div className="flex items-center justify-between text-slate-400 text-xs font-bold uppercase tracking-wider gap-2">
                  <span>Cash Safety Days</span>
                  <div className="p-2 rounded-xl bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 shrink-0">
                    <ShieldCheck className="w-4 h-4" />
                  </div>
                </div>
                <div>
                  <div className="text-2xl font-bold text-emerald-400 tracking-tight">88 Days</div>
                  <div className="text-xs text-emerald-400 font-medium mt-1">3 Months of safe runway</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
