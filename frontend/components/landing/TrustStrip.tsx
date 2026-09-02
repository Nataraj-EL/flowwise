import React from 'react';
import { Lock, Zap, Smartphone } from 'lucide-react';

export const TrustStrip: React.FC = () => {
  const pillars = [
    { name: 'Bank-Grade Security & Privacy', icon: Lock },
    { name: 'Instant 2-Minute Setup', icon: Zap },
    { name: 'Works on Phone, Tablet & PC', icon: Smartphone },
  ];

  return (
    <section className="py-20 bg-[#0B0F19] border-t border-slate-800/80">
      <div className="max-w-5xl mx-auto px-6 lg:px-8 space-y-8 text-center">
        <h3 className="text-xs font-mono uppercase tracking-widest text-slate-400 font-bold">
          Trusted, secure, and ready for your business.
        </h3>

        {/* 3 User-Friendly Pillars */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-6 sm:gap-8 pt-2">
          {pillars.map((item, i) => {
            const Icon = item.icon;
            return (
              <div
                key={i}
                className="flex items-center gap-3 text-sm font-semibold text-slate-200 bg-[#12192B] px-6 py-4 rounded-2xl border border-slate-800 shadow-md hover:border-slate-700 transition-colors"
              >
                <Icon className="w-5 h-5 text-cyan-400" />
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
