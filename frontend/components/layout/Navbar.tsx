'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { Menu, X, ArrowRight, Wallet } from 'lucide-react';

export const Navbar: React.FC = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full bg-[#0B0F19]/90 backdrop-blur-md border-b border-slate-800/80">
      <div className="max-w-7xl mx-auto px-6 lg:px-8 h-20 flex items-center justify-between">
        {/* Friendly Brand Wordmark */}
        <Link href="/" className="flex items-center gap-3 group">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-cyan-500 via-teal-400 to-emerald-400 p-[1px] shadow-lg shadow-cyan-500/20 group-hover:scale-105 transition-transform">
            <div className="w-full h-full bg-[#0B0F19] rounded-[11px] flex items-center justify-center">
              <Wallet className="w-5 h-5 text-cyan-400" />
            </div>
          </div>
          <div className="flex flex-col">
            <span className="text-xl font-extrabold tracking-tight text-white font-sans group-hover:text-cyan-400 transition-colors">
              FLOWWISE
            </span>
            <span className="text-[10px] font-medium text-slate-400 tracking-wide -mt-1">
              Simple Cash Flow for Business
            </span>
          </div>
        </Link>

        {/* Non-Technical Navigation Links */}
        <nav className="hidden md:flex items-center gap-10 text-sm font-medium text-slate-300">
          <a href="#how-it-works" className="hover:text-white transition-colors">
            How It Works
          </a>
          <a href="#features" className="hover:text-white transition-colors">
            Features
          </a>
          <a href="#benefits" className="hover:text-white transition-colors">
            For Business Owners
          </a>
        </nav>

        {/* Friendly Right CTA Button */}
        <div className="hidden md:flex items-center gap-4">
          <Link
            href="/dashboard"
            className="px-5 py-2.5 bg-gradient-to-r from-cyan-500 via-teal-400 to-emerald-400 hover:opacity-95 text-slate-950 font-bold rounded-xl text-xs tracking-wider uppercase transition-all flex items-center gap-2 shadow-lg shadow-cyan-500/20 hover:scale-[1.02]"
          >
            Try Free Demo
            <ArrowRight className="w-4 h-4" />
          </Link>
        </div>

        {/* Mobile Hamburger Button */}
        <button
          className="md:hidden p-2 text-slate-400 hover:text-white"
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          aria-label="Toggle Navigation Menu"
        >
          {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
        </button>
      </div>

      {/* Mobile Menu Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden bg-[#0F1422] border-b border-slate-800 px-6 pt-4 pb-6 space-y-4 text-sm font-medium">
          <a
            href="#how-it-works"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            How It Works
          </a>
          <a
            href="#features"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            Features
          </a>
          <a
            href="#benefits"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            For Business Owners
          </a>
          <div className="pt-4 border-t border-slate-800">
            <Link
              href="/dashboard"
              onClick={() => setMobileMenuOpen(false)}
              className="w-full py-3 bg-gradient-to-r from-cyan-500 to-emerald-400 text-slate-950 font-bold rounded-xl text-xs tracking-wider uppercase transition-all flex items-center justify-center gap-2 shadow-md"
            >
              Try Free Demo
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      )}
    </header>
  );
};
