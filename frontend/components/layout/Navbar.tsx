'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { Modal } from '@/components/ui/Modal';
import { Menu, X, ArrowRight, ShieldCheck } from 'lucide-react';

export const Navbar: React.FC = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [signInOpen, setSignInOpen] = useState(false);

  return (
    <header className="sticky top-0 z-40 w-full bg-[#060709]/90 backdrop-blur-md border-b border-white/10">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
        {/* Brand Wordmark */}
        <Link href="/" className="flex items-center gap-3 group">
          <div className="w-9 h-9 bg-black border border-[#00F0FF] flex items-center justify-center font-mono font-black text-[#00F0FF] text-xl tracking-tighter shadow-[0_0_12px_rgba(0,240,255,0.4)] group-hover:scale-105 transition-transform">
            FW
          </div>
          <div className="flex flex-col">
            <span className="text-xl font-black tracking-widest text-white uppercase group-hover:text-[#00F0FF] transition-colors font-mono">
              FLOWWISE
            </span>
            <span className="text-[9px] font-mono tracking-widest text-slate-400 uppercase -mt-1">
              FINANCIAL INTELLIGENCE
            </span>
          </div>
        </Link>

        {/* Desktop Navigation Links */}
        <nav className="hidden md:flex items-center gap-8 text-sm font-mono tracking-wider uppercase">
          <a href="#intelligence" className="text-slate-300 hover:text-[#00F0FF] transition-colors">
            Product
          </a>
          <a href="#capabilities" className="text-slate-300 hover:text-[#00F0FF] transition-colors">
            Intelligence
          </a>
          <a href="#merchants" className="text-slate-300 hover:text-[#00F0FF] transition-colors">
            For Merchants
          </a>
          <Badge variant="demo" className="py-1">
            DEMO MODE
          </Badge>
        </nav>

        {/* Action Buttons */}
        <div className="hidden md:flex items-center gap-3">
          <Button variant="ghost" size="sm" onClick={() => setSignInOpen(true)}>
            Sign In
          </Button>
          <Link href="/dashboard">
            <Button variant="cyan" size="sm" className="gap-2">
              Launch Console
              <ArrowRight className="w-4 h-4" />
            </Button>
          </Link>
        </div>

        {/* Mobile Hamburger Button */}
        <button
          className="md:hidden p-2 text-slate-300 hover:text-white"
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          aria-label="Toggle Navigation Menu"
        >
          {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
        </button>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden bg-[#0E1116] border-b border-white/10 px-4 pt-4 pb-6 space-y-4 font-mono text-sm uppercase">
          <a
            href="#intelligence"
            className="block text-slate-300 hover:text-[#00F0FF]"
            onClick={() => setMobileMenuOpen(false)}
          >
            Product
          </a>
          <a
            href="#capabilities"
            className="block text-slate-300 hover:text-[#00F0FF]"
            onClick={() => setMobileMenuOpen(false)}
          >
            Intelligence
          </a>
          <a
            href="#merchants"
            className="block text-slate-300 hover:text-[#00F0FF]"
            onClick={() => setMobileMenuOpen(false)}
          >
            For Merchants
          </a>
          <div className="pt-4 border-t border-white/10 flex flex-col gap-3">
            <Button
              variant="outline"
              size="md"
              className="w-full justify-center"
              onClick={() => {
                setMobileMenuOpen(false);
                setSignInOpen(true);
              }}
            >
              Sign In
            </Button>
            <Link href="/dashboard" onClick={() => setMobileMenuOpen(false)}>
              <Button variant="cyan" size="md" className="w-full justify-center gap-2">
                Launch Console
                <ArrowRight className="w-4 h-4" />
              </Button>
            </Link>
          </div>
        </div>
      )}

      {/* Sign In Demo Modal */}
      <Modal
        isOpen={signInOpen}
        onClose={() => setSignInOpen(false)}
        title="Merchant Authentication Demo"
      >
        <div className="space-y-4">
          <div className="p-3 bg-amber-500/10 border border-amber-500/30 text-amber-300 text-xs font-mono flex items-start gap-2">
            <ShieldCheck className="w-4 h-4 mt-0.5 shrink-0" />
            <span>
              Authentication is simulated for Sprint 1. Click below to enter the live merchant console with pre-loaded demo data.
            </span>
          </div>

          <div className="space-y-2">
            <label className="text-xs font-mono text-slate-400 uppercase">Demo Merchant Identifier</label>
            <input
              type="text"
              readOnly
              value="demo-merchant@flowwise.internal"
              className="w-full bg-black border border-white/10 px-3 py-2 text-sm font-mono text-slate-300"
            />
          </div>

          <Link href="/dashboard" className="block pt-2">
            <Button variant="cyan" size="md" className="w-full justify-center">
              Continue to Console (Demo)
            </Button>
          </Link>
        </div>
      </Modal>
    </header>
  );
};
