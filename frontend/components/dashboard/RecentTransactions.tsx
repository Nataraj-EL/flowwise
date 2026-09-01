'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { fetchMerchantTransactions, BackendTransactionDTO } from '@/lib/api';
import { DEMO_TRANSACTIONS } from '@/lib/mock-data';
import { formatINR } from '@/lib/utils';
import { ArrowUpRight, ArrowDownLeft, Receipt, CheckCircle, Clock, ExternalLink } from 'lucide-react';

export const RecentTransactions: React.FC = () => {
  const [transactions, setTransactions] = useState<BackendTransactionDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    async function loadData() {
      try {
        const data = await fetchMerchantTransactions(1);
        setTransactions(data.slice(0, 5)); // show top 5 on dashboard overview
      } catch (err) {
        // Fallback to static demo format if API server is not running on overview widget
        setTransactions(
          DEMO_TRANSACTIONS.map((t, i) => ({
            id: i + 1,
            merchantId: 1,
            businessAccountId: 1,
            institutionName: 'HDFC Bank',
            transactionReference: t.id,
            transactionDate: t.date,
            description: t.category,
            amount: t.amount,
            type: t.type,
            category: t.category.split(' ')[0].toUpperCase(),
            counterparty: t.counterparty,
            paymentMethod: 'UPI',
            status: t.status,
            demoTag: 'DEMO-DATA',
          }))
        );
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, []);

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
        <Link href="/dashboard/transactions" className="text-xs font-mono text-[#00F0FF] hover:underline flex items-center gap-1">
          <span>View All</span>
          <ExternalLink className="w-3.5 h-3.5" />
        </Link>
      </div>

      {loading ? (
        <div className="space-y-2 animate-pulse font-mono">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-12 bg-[#07080B] border border-white/5"></div>
          ))}
        </div>
      ) : (
        <div className="space-y-2 font-mono">
          {transactions.map((txn) => {
            const isCredit = txn.type === 'CREDIT';

            return (
              <div
                key={txn.transactionReference || txn.id}
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
                      <span className="truncate max-w-[160px] sm:max-w-[200px]">{txn.counterparty}</span>
                      <Badge variant="cyan" className="text-[9px]">
                        {txn.category}
                      </Badge>
                    </div>
                    <div className="text-[11px] text-slate-400 truncate max-w-[220px]">
                      {txn.description}
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
                    {isCredit ? `+${formatINR(txn.amount)}` : `-${formatINR(txn.amount)}`}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </Card>
  );
};
