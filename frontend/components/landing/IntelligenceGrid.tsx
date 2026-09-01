import React from 'react';
import { TrendingUp, RefreshCw, LineChart, ShieldCheck } from 'lucide-react';

export const IntelligenceGrid: React.FC = () => {
  const capabilities = [
    {
      icon: TrendingUp,
      title: 'Cash Flow',
      description: 'Understand where money is coming from and where it is going.',
    },
    {
      icon: RefreshCw,
      title: 'Reconciliation',
      description: 'Match settlements, invoices, and payables automatically.',
    },
    {
      icon: LineChart,
      title: 'Forecasting',
      description: 'See how upcoming payments and collections affect future liquidity.',
    },
    {
      icon: ShieldCheck,
      title: 'Financial Health',
      description: 'Monitor liquidity, runway, risk, and cash position from one place.',
    },
  ];

  return (
    <section id="intelligence" className="py-20 bg-[#0A0D14] border-y border-slate-800/80">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 space-y-12">
        <div className="text-center space-y-3">
          <h2 className="text-2xl sm:text-4xl font-extrabold text-white tracking-tight">
            Financial clarity without the spreadsheet.
          </h2>
          <p className="text-slate-400 text-sm sm:text-base max-w-xl mx-auto">
            Essential intelligence tools designed to give merchants complete confidence over cash flow.
          </p>
        </div>

        {/* 2x2 Clean Grid Layout with Generous Spacing */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {capabilities.map((item, index) => {
            const Icon = item.icon;
            return (
              <div
                key={index}
                className="bg-[#121622] border border-slate-800 p-8 rounded-2xl space-y-4 hover:border-slate-700 transition-all shadow-lg"
              >
                <div className="w-12 h-12 rounded-xl bg-cyan-500/10 border border-cyan-500/20 text-cyan-400 flex items-center justify-center">
                  <Icon className="w-6 h-6" />
                </div>

                <div className="space-y-2">
                  <h3 className="text-lg font-bold text-white">{item.title}</h3>
                  <p className="text-slate-400 text-sm leading-relaxed">{item.description}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
};
