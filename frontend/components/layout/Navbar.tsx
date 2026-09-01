'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { Menu, X, ArrowRight } from 'lucide-react';

export const Navbar: React.FC = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-40 w-full bg-[#060709]/90 backdrop-blur-md border-b border-slate-800/80">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
        {/* Brand Wordmark */}
        <Link href="/" className="flex items-center gap-3 group">
          <div className="w-8 h-8 bg-cyan-500/10 border border-cyan-500/40 rounded-lg flex items-center justify-center font-mono font-black text-cyan-400 text-base tracking-tighter">
            FW
          </div>
          <span className="text-lg font-black tracking-widest text-white uppercase font-mono group-hover:text-cyan-400 transition-colors">
            FLOWWISE
          </span>
        </Link>

        {/* Desktop Navigation Links */}
        <nav className="hidden md:flex items-center gap-8 text-xs font-mono tracking-widest uppercase">
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

        {/* Right CTA */}
        <div className="hidden md:flex items-center gap-3">
          <Link
            href="/dashboard"
            className="px-4 py-2 bg-cyan-600 hover:bg-cyan-500 text-white rounded-lg text-xs font-mono font-semibold uppercase tracking-wider transition-all flex items-center gap-2 shadow-lg shadow-cyan-600/20"
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

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden bg-[#0A0D14] border-b border-slate-800 px-6 pt-4 pb-6 space-y-4 font-mono text-xs uppercase tracking-wider">
          <a
            href="#product"
            className="block text-slate-400 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            Product
          </a>
          <a
            href="#intelligence"
            className="block text-slate-400 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            Intelligence
          </a>
          <a
            href="#merchants"
            className="block text-slate-400 hover:text-white"
            onClick={() => setMobileMenuOpen(false)}
          >
            For Merchants
          </a>
          <div className="pt-4 border-t border-slate-800">
            <Link
              href="/dashboard"
              onClick={() => setMobileMenuOpen(false)}
              className="w-full py-2.5 bg-cyan-600 hover:bg-cyan-500 text-white rounded-lg text-xs font-mono font-semibold uppercase tracking-wider transition-all flex items-center justify-center gap-2"
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
