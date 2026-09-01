import React from 'react';
import { Server, Database, Code2 } from 'lucide-react';

export const TrustStrip: React.FC = () => {
  const stack = [
    { name: 'Spring Boot + Java 17', icon: Server },
    { name: 'PostgreSQL', icon: Database },
    { name: 'Next.js + TypeScript', icon: Code2 },
  ];

  return (
    <section className="py-20 bg-[#0B0F19] border-t border-slate-800/80">
      <div className="max-w-5xl mx-auto px-6 lg:px-8 space-y-8 text-center">
        <h3 className="text-xs font-mono uppercase tracking-widest text-slate-400 font-bold">
          Built for serious financial workflows.
        </h3>

        {/* 3 Architecture Badges */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-6 sm:gap-8 pt-2">
          {stack.map((item, i) => {
            const Icon = item.icon;
            return (
              <div
                key={i}
                className="flex items-center gap-3 text-sm font-semibold text-slate-200 bg-[#12192B] px-6 py-3.5 rounded-2xl border border-slate-800 shadow-md hover:border-slate-700 transition-colors"
              >
                <Icon className="w-5 h-5 text-cyan-400" />
                <span>{item.name}</span>
              </div>
            );
          })}
        </div>

        <p className="text-xs text-slate-400 font-mono pt-2">
          Synthetic demo environment. Financial data shown is illustrative only.
        </p>
      </div>
    </section>
  );
};
