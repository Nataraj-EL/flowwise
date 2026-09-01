import React from 'react';
import Link from 'next/link';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { ArrowRight, ShieldCheck, Database, Code } from 'lucide-react';

export const FinalCTA: React.FC = () => {
  return (
    <footer className="bg-[#040507] text-slate-400 font-mono">
      {/* Final Call To Action Banner */}
      <div className="border-b border-white/10 py-20 relative overflow-hidden cyber-grid">
        <div className="max-w-5xl mx-auto px-4 text-center space-y-6 relative z-10">
          <Badge variant="cyan" className="py-1">
            DEMO READY CONSOLE
          </Badge>

          <h2 className="text-3xl sm:text-5xl font-black text-white uppercase tracking-tight">
            TAKE COMMAND OF YOUR CASH FLOW TODAY.
          </h2>

          <p className="text-slate-300 font-sans text-base sm:text-lg max-w-2xl mx-auto">
            Experience Flowwise merchant financial intelligence in action with pre-configured synthetic merchant profiles.
          </p>

          <div className="pt-4 flex justify-center">
            <Link href="/dashboard">
              <Button variant="cyan" size="lg" className="gap-2">
                Launch Merchant Console
                <ArrowRight className="w-5 h-5" />
              </Button>
            </Link>
          </div>
        </div>
      </div>

      {/* Footer Navigation & Product Metadata */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12 grid grid-cols-1 md:grid-cols-4 gap-8 text-xs border-b border-white/10">
        {/* Col 1: Brand & Positioning */}
        <div className="space-y-3 md:col-span-1">
          <div className="flex items-center gap-2">
            <div className="w-6 h-6 bg-black border border-[#00F0FF] flex items-center justify-center font-bold text-[#00F0FF] text-xs">
              FW
            </div>
            <span className="font-bold text-white tracking-widest text-sm uppercase">FLOWWISE</span>
          </div>
          <p className="text-slate-400 font-sans text-xs leading-relaxed">
            Financial intelligence for modern merchants. Know your cash. Grow your business.
          </p>
          <div className="pt-1">
            <Badge variant="demo">DEMO ENVIRONMENT</Badge>
          </div>
        </div>

        {/* Col 2: Platform Links */}
        <div className="space-y-2">
          <div className="font-bold text-white uppercase tracking-wider mb-2">Platform Console</div>
          <ul className="space-y-1.5 text-slate-400">
            <li><Link href="/dashboard" className="hover:text-[#00F0FF]">Dashboard Overview</Link></li>
            <li><span className="text-slate-600">Cash Flow (Sprint 2)</span></li>
            <li><span className="text-slate-600">Transactions (Sprint 2)</span></li>
            <li><span className="text-slate-600">Health Index (Sprint 2)</span></li>
          </ul>
        </div>

        {/* Col 3: Technology Stack */}
        <div className="space-y-2">
          <div className="font-bold text-white uppercase tracking-wider mb-2">Architecture</div>
          <ul className="space-y-1.5 text-slate-400">
            <li className="flex items-center gap-1.5"><Code className="w-3.5 h-3.5 text-[#00F0FF]" /> Next.js 15 App Router</li>
            <li className="flex items-center gap-1.5"><Code className="w-3.5 h-3.5 text-[#00E599]" /> Tailwind CSS v4</li>
            <li className="flex items-center gap-1.5"><Database className="w-3.5 h-3.5 text-amber-400" /> Spring Boot Java 17 Backend</li>
            <li className="flex items-center gap-1.5"><Database className="w-3.5 h-3.5 text-cyan-400" /> PostgreSQL Ready Schema</li>
          </ul>
        </div>

        {/* Col 4: Safety & Disclaimer */}
        <div className="space-y-2">
          <div className="font-bold text-white uppercase tracking-wider mb-2">Synthetic Data Notice</div>
          <p className="text-slate-400 font-sans text-xs leading-relaxed">
            All merchant profiles, transaction records, and financial numbers presented in Sprint 1 are synthetic demo data created solely for UI visualization.
          </p>
        </div>
      </div>

      {/* Bottom Copyright */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6 flex flex-col sm:flex-row items-center justify-between text-[11px] text-slate-400 gap-4">
        <div>© 2026 Flowwise Platform Inc. All rights reserved.</div>
        <div className="flex items-center gap-2">
          <ShieldCheck className="w-4 h-4 text-[#00E599]" />
          <span>Independent Commercial Product Architecture</span>
        </div>
      </div>
    </footer>
  );
};
