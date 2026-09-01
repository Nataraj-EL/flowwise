'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { Menu, X, ArrowRight } from 'lucide-react';

export const Navbar: React.FC = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full bg-[#07090E]/80 backdrop-blur-xl border-b border-slate-800/60">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
        {/* Brand Wordmark */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-8 h-8 rounded-xl bg-gradient-to-tr from-cyan-500 to-emerald-400 p-[1px] shadow-lg shadow-cyan-500/20 group-hover:scale-105 transition-transform">
            <div className="w-full h-full bg-[#07090E] rounded-[11px] flex items-center justify-center font-mono font-extrabold text-cyan-400 text-xs tracking-tighter">
              FW
            </div>
          </div>
          <span className="text-lg font-bold tracking-tight text-white font-sans group-hover:text-cyan-400 transition-colors">
            FLOWWISE
          </span>
        </Link>

        {/* Desktop Navigation Links */}
        <nav className="hidden md:flex items-center gap-8 text-sm font-medium">
          <a href="#product" className="text-slate-400 hover:text-white transition-colors">
            Product
          </a>
          <a href="#intelligence" className="text-slate-400 hover:text-white transition-colors">
            Intelligence
          </a>
          <a href="#merchants" className="text-slate-400 hover:text-white transition-colors">
            For Merchants
          </a>
        </nav>

        {/* Right CTA Button */}
        <div className="hidden md:flex items-center gap-3">
          <Link
            href="/dashboard"
            className="px-4 py-2 bg-white hover:bg-slate-100 text-slate-950 font-semibold rounded-full text-xs transition-all flex items-center gap-1.5 shadow-md shadow-white/10"
          >
            View Demo
            <ArrowRight className="w-3.5 h-3.5" />
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
        <div className="md:hidden bg-[#0D111A] border-b border-slate-800 px-6 pt-4 pb-6 space-y-4 text-sm font-medium">
          <a
            href="#product"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            Product
          </a>
          <a
            href="#intelligence"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            Intelligence
          </a>
          <a
            href="#merchants"
            className="block text-slate-300 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            For Merchants
          </a>
          <div className="pt-4 border-t border-slate-800">
            <Link
              href="/dashboard"
              onClick={() => setMobileMenuOpen(false)}
              className="w-full py-2.5 bg-white hover:bg-slate-100 text-slate-950 font-semibold rounded-xl text-xs transition-all flex items-center justify-center gap-2 shadow-md"
            >
              View Demo
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      )}
    </header>
  );
};
