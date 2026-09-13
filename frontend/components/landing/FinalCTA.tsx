import React from 'react';
import Link from 'next/link';
import { ArrowRight, Wallet } from 'lucide-react';

export const FinalCTA: React.FC = () => {
  return (
    <footer className="bg-[#070A12] border-t border-slate-800">
      {/* Final CTA Banner */}
      <div className="py-24 sm:py-32 bg-gradient-to-b from-[#0F172A] to-[#070A12] max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center space-y-8 relative overflow-hidden">
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[300px] bg-cyan-500/10 blur-[140px] rounded-full pointer-events-none"></div>

        <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight leading-tight max-w-3xl mx-auto relative z-10">
          Ready to simplify your business cash flow?
        </h2>

        <p className="text-slate-300 text-base sm:text-lg max-w-md mx-auto relative z-10 font-normal leading-relaxed">
          Explore the Flowwise interactive console with sample merchant data today.
        </p>

        <div className="pt-2 relative z-10 flex justify-center">
          <Link
            href="/dashboard"
            className="inline-flex items-center justify-center gap-3 px-8 sm:px-10 py-4 sm:py-5 bg-gradient-to-r from-cyan-500 to-emerald-400 hover:from-cyan-400 hover:to-emerald-300 text-slate-950 rounded-2xl text-sm sm:text-base font-bold transition-all shadow-2xl shadow-cyan-500/30 hover:shadow-cyan-500/50 hover:scale-[1.02] w-full sm:w-auto"
          >
            Try Demo Console Now
            <ArrowRight className="w-5 h-5 shrink-0" />
          </Link>
        </div>
      </div>

      {/* Footer Navigation */}
      <div className="border-t border-slate-800 py-10 max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col md:flex-row items-center justify-between gap-6 text-center md:text-left">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-xl bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center shrink-0">
            <Wallet className="w-4 h-4 text-cyan-400" />
          </div>
          <div>
            <div className="font-extrabold text-white text-base tracking-tight font-sans">
              FLOWWISE
            </div>
            <p className="text-xs text-slate-400">
              Simple cash flow intelligence for business owners.
            </p>
          </div>
        </div>

        {/* Links */}
        <div className="flex flex-wrap items-center justify-center gap-6 text-sm font-medium text-slate-300">
          <a href="#how-it-works" className="hover:text-white transition-colors">
            How It Works
          </a>
          <a href="#features" className="hover:text-white transition-colors">
            Features
          </a>
          <a href="#benefits" className="hover:text-white transition-colors">
            For Business Owners
          </a>
          <Link href="/dashboard" className="text-cyan-400 hover:text-cyan-300 font-bold transition-colors">
            Demo Console
          </Link>
        </div>

        {/* Copyright */}
        <div className="text-xs text-slate-400 font-mono">
          &copy; {new Date().getFullYear()} Flowwise Inc. All rights reserved.
        </div>
      </div>
    </footer>
  );
};
