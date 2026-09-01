import React from 'react';
import { Server, Database, Code2 } from 'lucide-react';

export const TrustStrip: React.FC = () => {
  const stack = [
    { name: 'Spring Boot + Java 17', icon: Server },
    { name: 'PostgreSQL', icon: Database },
    { name: 'Next.js + TypeScript', icon: Code2 },
  ];

  return (
    <section className="py-12 bg-[#060709] border-t border-slate-800/80">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 space-y-6 text-center">
        <h3 className="text-sm font-mono uppercase tracking-widest text-slate-400 font-semibold">
          Built for serious financial workflows.
        </h3>

        {/* 3 Small Architecture Items */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-6 sm:gap-12 pt-1">
          {stack.map((item, i) => {
            const Icon = item.icon;
            return (
              <div key={i} className="flex items-center gap-2.5 text-xs font-mono text-slate-300">
                <Icon className="w-4 h-4 text-cyan-400" />
                <span>{item.name}</span>
              </div>
            );
          })}
        </div>

        {/* Short Line */}
        <p className="text-[11px] text-slate-500 font-mono pt-2">
          Synthetic demo environment. Financial data shown is illustrative only.
        </p>
      </div>
    </section>
  );
};
