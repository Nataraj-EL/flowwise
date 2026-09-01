import React from 'react';
import Link from 'next/link';
import { ArrowRight, Activity } from 'lucide-react';

export const FinalCTA: React.FC = () => {
  return (
    <footer className="bg-[#070A12] border-t border-slate-800/80">
      {/* Final CTA Banner */}
      <div className="py-32 bg-gradient-to-b from-[#0E1424] to-[#070A12] max-w-7xl mx-auto px-6 lg:px-8 text-center space-y-10 relative overflow-hidden">
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[300px] bg-cyan-500/10 blur-[140px] rounded-full pointer-events-none"></div>

        <h2 className="text-4xl sm:text-6xl font-black text-white tracking-tight leading-tight max-w-4xl mx-auto relative z-10">
          Take control of your cash flow.
        </h2>

        <p className="text-slate-300 text-lg sm:text-xl max-w-lg mx-auto relative z-10 font-normal">
          Explore the Flowwise financial intelligence console.
        </p>

        <div className="pt-2 relative z-10">
          <Link
            href="/dashboard"
            className="inline-flex items-center gap-3 px-10 py-5 bg-gradient-to-r from-cyan-500 to-emerald-400 hover:from-cyan-400 hover:to-emerald-300 text-slate-950 rounded-2xl text-base font-bold transition-all shadow-2xl shadow-cyan-500/30 hover:shadow-cyan-500/50 hover:scale-[1.03]"
          >
            Enter Demo Console
            <ArrowRight className="w-5 h-5" />
          </Link>
        </div>
      </div>

      {/* Minimal Footer */}
      <div className="border-t border-slate-800/80 py-12 max-w-6xl mx-auto px-6 lg:px-8 flex flex-col md:flex-row items-center justify-between gap-6">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-lg bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center">
            <Activity className="w-4 h-4 text-cyan-400" />
          </div>
          <div>
            <div className="font-extrabold text-white text-base tracking-tight font-sans">
              FLOWWISE
            </div>
            <p className="text-xs text-slate-400">
              Financial intelligence for modern merchants.
            </p>
          </div>
        </div>

        {/* Links */}
        <div className="flex items-center gap-8 text-sm font-medium text-slate-300">
          <a href="#product" className="hover:text-white transition-colors">
            Product
          </a>
          <a href="#intelligence" className="hover:text-white transition-colors">
            Intelligence
          </a>
          <Link href="/dashboard" className="text-cyan-400 hover:text-cyan-300 font-bold transition-colors">
            Demo Console
          </Link>
        </div>

        {/* Small Copyright Line */}
        <div className="text-xs text-slate-400 font-mono">
          &copy; {new Date().getFullYear()} Flowwise Inc. All rights reserved.
        </div>
      </div>
    </footer>
  );
};
