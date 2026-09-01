import React from 'react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { DEMO_TRANSACTIONS } from '@/lib/mock-data';
import { ArrowUpRight, ArrowDownLeft, Receipt, CheckCircle, Clock } from 'lucide-react';

export const RecentTransactions: React.FC = () => {
  return (
    <Card className="space-y-4">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <Receipt className="w-5 h-5 text-[#00F0FF]" />
          <div>
            <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
              Recent Transaction Log
            </h3>
            <p className="text-[11px] text-slate-400 font-mono">
              Live Synthetic Activity Feed
            </p>
          </div>
        </div>
        <Badge variant="demo">DEMO LEDGER</Badge>
      </div>

      {/* Transaction Table / List */}
      <div className="space-y-2 font-mono">
        {DEMO_TRANSACTIONS.map((txn) => {
          const isCredit = txn.type === 'CREDIT';

          return (
            <div
              key={txn.id}
              className="p-3 bg-[#07080B] hover:bg-white/5 border border-white/5 hover:border-white/15 transition-colors flex flex-col sm:flex-row sm:items-center justify-between gap-3 text-xs"
            >
              <div className="flex items-center gap-3">
                <div
                  className={`w-8 h-8 flex items-center justify-center shrink-0 border ${
                    isCredit
                      ? 'bg-[#00F0FF]/10 text-[#00F0FF] border-[#00F0FF]/30'
                      : 'bg-rose-500/10 text-rose-400 border-rose-500/30'
                  }`}
                >
                  {isCredit ? (
                    <ArrowDownLeft className="w-4 h-4" />
                  ) : (
                    <ArrowUpRight className="w-4 h-4" />
                  )}
                </div>

                <div>
                  <div className="font-bold text-white flex items-center gap-2">
                    <span>{txn.counterparty}</span>
                    <Badge variant="neutral" className="text-[9px]">
                      {txn.demoTag}
                    </Badge>
                  </div>
                  <div className="text-[11px] text-slate-400">
                    {txn.category} • {txn.date}
                  </div>
                </div>
              </div>

              <div className="flex items-center justify-between sm:justify-end gap-4 border-t sm:border-0 border-white/5 pt-2 sm:pt-0">
                <div className="flex items-center gap-1.5">
                  {txn.status === 'SETTLED' ? (
                    <span className="text-[#00E599] flex items-center gap-1 text-[10px]">
                      <CheckCircle className="w-3 h-3" /> SETTLED
                    </span>
                  ) : (
                    <span className="text-amber-400 flex items-center gap-1 text-[10px]">
                      <Clock className="w-3 h-3" /> PENDING
                    </span>
                  )}
                </div>

                <div
                  className={`text-sm font-bold tracking-wider ${
                    isCredit ? 'text-[#00F0FF]' : 'text-rose-400'
                  }`}
                >
                  {isCredit ? `+${txn.formattedAmount}` : `-${txn.formattedAmount}`}
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </Card>
  );
};
