import React from 'react';
import Link from 'next/link';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { ArrowRight, Sparkles, ShieldCheck, Activity } from 'lucide-react';

export const Hero: React.FC = () => {
  return (
    <section className="relative overflow-hidden pt-12 pb-20 md:pt-20 md:pb-32 cyber-grid">
      {/* Background Radial Glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[350px] bg-[#00F0FF]/10 blur-[120px] rounded-full pointer-events-none"></div>
      <div className="absolute bottom-10 right-10 w-[400px] h-[250px] bg-[#00E599]/10 blur-[100px] rounded-full pointer-events-none"></div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 space-y-8 text-center">
        {/* Top Tag */}
        <div className="inline-flex items-center gap-2 px-3 py-1 bg-[#0E1116] border border-[#00F0FF]/30 text-xs font-mono text-slate-300">
          <Sparkles className="w-3.5 h-3.5 text-[#00F0FF]" />
          <span>FINANCIAL INTELLIGENCE FOR MODERN MERCHANTS</span>
          <Badge variant="demo" className="ml-2">
            SYNTHETIC CONSOLE
          </Badge>
        </div>

        {/* Display Headline */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-black text-white uppercase tracking-tight font-mono leading-none max-w-5xl mx-auto">
          KNOW YOUR CASH.{' '}
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#00F0FF] via-white to-[#00E599]">
            GROW YOUR BUSINESS.
          </span>
        </h1>

        {/* Supporting Statement */}
        <p className="text-slate-300 text-base sm:text-xl max-w-3xl mx-auto font-normal leading-relaxed">
          Flowwise transforms fragmented merchant bank accounts, receivables, and vendor payables into a single real-time liquidity command center.
        </p>

        {/* CTAs */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-4">
          <Link href="/dashboard" className="w-full sm:w-auto">
            <Button variant="cyan" size="lg" className="w-full sm:w-auto gap-2">
              Launch Merchant Console (Demo)
              <ArrowRight className="w-5 h-5" />
            </Button>
          </Link>
          <a href="#intelligence" className="w-full sm:w-auto">
            <Button variant="outline" size="lg" className="w-full sm:w-auto">
              Explore Capabilities
            </Button>
          </a>
        </div>

        {/* Background Abstract Financial Data Grid Visualizer */}
        <div className="pt-12 max-w-5xl mx-auto">
          <div className="bg-[#0E1116] border border-white/15 p-2 sm:p-4 shadow-2xl relative">
            <div className="bg-[#060709] border border-white/10 p-4 sm:p-6 space-y-4 text-left font-mono">
              <div className="flex items-center justify-between text-xs text-slate-400 border-b border-white/10 pb-3">
                <div className="flex items-center gap-2">
                  <Activity className="w-4 h-4 text-[#00F0FF]" />
                  <span>SYNTHETIC LIQUIDITY GRAPH • APEX RETAIL SOLUTIONS [DEMO]</span>
                </div>
                <div className="flex items-center gap-3 text-[10px]">
                  <span className="text-[#00E599] font-bold">● HEALTH: 88/100</span>
                  <span className="text-[#00F0FF] font-bold">● RUNWAY: 4.8 MO</span>
                </div>
              </div>

              {/* Animated Waveform SVG Visualizer */}
              <div className="h-32 sm:h-44 w-full relative flex items-end">
                <svg className="w-full h-full" viewBox="0 0 800 200" preserveAspectRatio="none">
                  <defs>
                    <linearGradient id="heroGradient" x1="0" y1="0" x2="0" y2="1">
                      <stop offset="0%" stopColor="#00F0FF" stopOpacity="0.4" />
                      <stop offset="100%" stopColor="#00F0FF" stopOpacity="0" />
                    </linearGradient>
                  </defs>
                  <path
                    d="M 0 160 Q 100 140 200 120 T 400 90 T 600 60 T 800 30 L 800 200 L 0 200 Z"
                    fill="url(#heroGradient)"
                  />
                  <path
                    d="M 0 160 Q 100 140 200 120 T 400 90 T 600 60 T 800 30"
                    fill="none"
                    stroke="#00F0FF"
                    strokeWidth="3"
                  />
                </svg>

                <div className="absolute inset-0 grid grid-cols-6 border-t border-white/5 pointer-events-none">
                  {[1, 2, 3, 4, 5, 6].map((i) => (
                    <div key={i} className="border-r border-white/5 h-full"></div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
