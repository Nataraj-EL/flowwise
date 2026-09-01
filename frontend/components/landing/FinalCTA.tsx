import React from 'react';
import Link from 'next/link';
import { ArrowRight } from 'lucide-react';

export const FinalCTA: React.FC = () => {
  return (
    <footer className="bg-[#07090E] border-t border-slate-800/60">
      {/* Final CTA Section */}
      <div className="py-28 max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center space-y-8">
        <h2 className="text-3xl sm:text-5xl font-extrabold text-white tracking-tight">
          Take control of your cash flow.
        </h2>

        <p className="text-slate-400 text-base max-w-md mx-auto">
          Explore the Flowwise financial intelligence console.
        </p>

        <div className="pt-2">
          <Link
            href="/dashboard"
            className="inline-flex items-center gap-2 px-8 py-4 bg-cyan-500 hover:bg-cyan-400 text-slate-950 rounded-xl text-sm font-semibold transition-all shadow-xl shadow-cyan-500/20"
          >
            Enter Demo Console
            <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      </div>

      {/* Minimal Footer */}
      <div className="border-t border-slate-800/60 py-10 max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col md:flex-row items-center justify-between gap-6">
        <div className="space-y-1 text-center md:text-left">
          <div className="font-bold text-white text-base tracking-tight">
            FLOWWISE
          </div>
          <p className="text-xs text-slate-400">
            Financial intelligence for modern merchants.
          </p>
        </div>

        {/* Navigation Links */}
        <div className="flex items-center gap-6 text-xs font-medium text-slate-400">
          <a href="#product" className="hover:text-white transition-colors">
            Product
          </a>
          <a href="#intelligence" className="hover:text-white transition-colors">
            Intelligence
          </a>
          <Link href="/dashboard" className="hover:text-cyan-400 transition-colors font-semibold">
            Demo
          </Link>
        </div>

        {/* Small Copyright Line */}
        <div className="text-xs text-slate-400">
          &copy; {new Date().getFullYear()} Flowwise Inc. All rights reserved.
        </div>
      </div>
    </footer>
  );
};
