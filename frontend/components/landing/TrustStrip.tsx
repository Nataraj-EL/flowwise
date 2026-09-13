import React from 'react';
import { Lock, Zap, Smartphone } from 'lucide-react';

export const TrustStrip: React.FC = () => {
  const pillars = [
    { name: 'Bank-Grade Security & Privacy', icon: Lock },
    { name: 'Instant 2-Minute Setup', icon: Zap },
    { name: 'Works on Phone, Tablet & PC', icon: Smartphone },
  ];

  return (
    <section className="py-16 md:py-20 bg-[#0B0F19] border-t border-slate-800">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 space-y-8 text-center">
        <h3 className="text-xs font-mono uppercase tracking-widest text-slate-400 font-bold">
          Trusted, secure, and ready for your business.
        </h3>

        {/* 3 Pillars */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 sm:gap-6 pt-2">
          {pillars.map((item, i) => {
            const Icon = item.icon;
            return (
              <div
                key={i}
                className="flex items-center gap-3 text-xs sm:text-sm font-semibold text-slate-200 bg-[#12192B] px-5 py-3.5 rounded-2xl border border-slate-800 shadow-md hover:border-slate-700 transition-colors w-full sm:w-auto justify-center"
              >
                <Icon className="w-4 h-4 sm:w-5 sm:h-5 text-cyan-400 shrink-0" />
                <span>{item.name}</span>
              </div>
            );
          })}
        </div>

        <p className="text-xs text-slate-400 font-mono pt-2">
          Interactive demo environment. Loaded with sample merchant data for easy exploration.
        </p>
      </div>
    </section>
  );
};
