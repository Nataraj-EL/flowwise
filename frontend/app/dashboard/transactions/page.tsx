'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantTransactions,
  fetchMerchantTransactionSummary,
  BackendTransactionDTO,
  BackendTransactionSummaryDTO,
} from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import {
  Receipt,
  ArrowDownLeft,
  ArrowUpRight,
  Filter,
  Search,
  CheckCircle,
  Clock,
  AlertTriangle,
  RefreshCw,
  Layers,
  PieChart,
  Calendar,
  CreditCard,
} from 'lucide-react';

export default function TransactionsPage() {
  const [transactions, setTransactions] = useState<BackendTransactionDTO[]>([]);
  const [summary, setSummary] = useState<BackendTransactionSummaryDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Filter States
  const [typeFilter, setTypeFilter] = useState<string>('ALL');
  const [categoryFilter, setCategoryFilter] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState<string>('');

  const categoriesList = [
    'ALL',
    'SALES',
    'INVENTORY',
    'OPERATIONS',
    'PAYROLL',
    'TAX',
    'UTILITIES',
    'RENT',
    'TRANSFER',
    'REFUND',
  ];

  const loadData = async () => {
    setLoading(true);
    setError(null);
    try {
      const filters: any = {};
      if (typeFilter !== 'ALL') filters.type = typeFilter;
      if (categoryFilter !== 'ALL') filters.category = categoryFilter;
      if (searchQuery.trim()) filters.search = searchQuery.trim();

      const [txnsData, summaryData] = await Promise.all([
        fetchMerchantTransactions(1, filters),
        fetchMerchantTransactionSummary(1),
      ]);

      setTransactions(txnsData);
      setSummary(summaryData);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Transaction API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [typeFilter, categoryFilter]);

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    loadData();
  };

  return (
    <div className="space-y-8 font-mono">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Transaction Intelligence Ledger
            </h1>
            <Badge variant="demo">SPRING BOOT ENGINE</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Deterministic Classification & Merchant Ledger for <span className="text-white font-bold">Apex Retail Solutions [DEMO]</span>
          </p>
        </div>
      </div>

      {/* Error State */}
      {error && (
        <Card variant="glow-cyan" className="p-6 text-center space-y-4">
          <div className="w-10 h-10 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-5 h-5" />
          </div>
          <div className="space-y-1">
            <h3 className="text-lg font-bold text-white uppercase">API CONNECTION FAILED</h3>
            <p className="text-xs text-slate-300 font-sans">{error}</p>
          </div>
          <Button variant="cyan" size="md" onClick={loadData} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry API Connection
          </Button>
        </Card>
      )}

      {/* 4 Summary Cards (Calculated by Spring Boot Backend!) */}
      {summary && (
        <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <Card className="space-y-2">
            <span className="text-xs uppercase text-slate-400 font-medium">Total Credits (Inflow)</span>
            <div className="text-2xl sm:text-3xl font-bold text-[#00F0FF]">
              +{formatINR(summary.totalCredits)}
            </div>
            <span className="text-[10px] text-slate-500">Calculated by Backend Service</span>
          </Card>

          <Card className="space-y-2">
            <span className="text-xs uppercase text-slate-400 font-medium">Total Debits (Outflow)</span>
            <div className="text-2xl sm:text-3xl font-bold text-rose-400">
              -{formatINR(summary.totalDebits)}
            </div>
            <span className="text-[10px] text-slate-500">Calculated by Backend Service</span>
          </Card>

          <Card className="space-y-2">
            <span className="text-xs uppercase text-slate-400 font-medium">Net Cash Surplus</span>
            <div className="text-2xl sm:text-3xl font-bold text-[#00E599]">
              {formatINR(summary.netCashFlow)}
            </div>
            <span className="text-[10px] text-slate-500">Ledger Net Position</span>
          </Card>

          <Card className="space-y-2">
            <span className="text-xs uppercase text-slate-400 font-medium">Ledger Record Count</span>
            <div className="text-2xl sm:text-3xl font-bold text-white">
              {summary.transactionCount} TXNS
            </div>
            <span className="text-[10px] text-slate-500">Flyway Database Schema</span>
          </Card>
        </section>
      )}

      {/* Category Totals Breakdown Pills */}
      {summary && summary.categoryTotals && (
        <Card className="space-y-3">
          <div className="flex items-center gap-2 text-xs font-bold text-slate-300 uppercase border-b border-white/10 pb-2">
            <PieChart className="w-4 h-4 text-[#00F0FF]" />
            <span>Backend Category Breakdown</span>
          </div>
          <div className="flex flex-wrap gap-2">
            {summary.categoryTotals.map((cat) => (
              <div
                key={cat.category}
                className="p-2.5 bg-[#07080B] border border-white/10 flex items-center gap-3 text-xs"
              >
                <Badge variant={cat.type === 'CREDIT' ? 'cyan' : 'rose'}>
                  {cat.category}
                </Badge>
                <span className="text-white font-bold">{formatINR(cat.totalAmount)}</span>
                <span className="text-[10px] text-slate-500">({cat.count} txns)</span>
              </div>
            ))}
          </div>
        </Card>
      )}

      {/* Interactive Filter Controls */}
      <Card className="space-y-4">
        <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
          {/* Type Tabs */}
          <div className="flex items-center gap-1 bg-[#07080B] p-1 border border-white/10 text-xs">
            {['ALL', 'CREDIT', 'DEBIT'].map((type) => (
              <button
                key={type}
                onClick={() => setTypeFilter(type)}
                className={`px-3 py-1.5 uppercase font-bold transition-colors ${
                  typeFilter === type
                    ? 'bg-[#12161F] text-[#00F0FF] border border-[#00F0FF]/40'
                    : 'text-slate-400 hover:text-white'
                }`}
              >
                {type}
              </button>
            ))}
          </div>

          {/* Category Dropdown & Search Bar */}
          <form onSubmit={handleSearchSubmit} className="flex flex-col sm:flex-row items-center gap-3">
            <div className="flex items-center gap-2 w-full sm:w-auto">
              <Filter className="w-4 h-4 text-slate-400 shrink-0" />
              <select
                value={categoryFilter}
                onChange={(e) => setCategoryFilter(e.target.value)}
                className="bg-[#07080B] border border-white/15 text-slate-200 text-xs font-mono px-3 py-2 focus:outline-none focus:border-[#00F0FF] w-full sm:w-auto"
              >
                {categoriesList.map((cat) => (
                  <option key={cat} value={cat}>
                    Category: {cat}
                  </option>
                ))}
              </select>
            </div>

            <div className="flex items-center gap-2 w-full sm:w-auto">
              <Input
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search counterparty or reference..."
                icon={<Search className="w-4 h-4" />}
                className="text-xs"
              />
              <Button variant="secondary" size="sm" type="submit">
                Search
              </Button>
            </div>
          </form>
        </div>
      </Card>

      {/* High-Density Transaction Table */}
      <Card className="p-0 overflow-hidden">
        <div className="p-4 bg-[#0E1116] border-b border-white/10 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Receipt className="w-4 h-4 text-[#00F0FF]" />
            <h3 className="text-sm font-bold text-white uppercase tracking-wider">
              Ledger Transactions ({transactions.length})
            </h3>
          </div>
          <Badge variant="demo">SYNTHETIC LEDGER</Badge>
        </div>

        {loading ? (
          <div className="p-8 text-center space-y-2 animate-pulse">
            <div className="h-10 bg-white/5 w-full"></div>
            <div className="h-10 bg-white/5 w-full"></div>
            <div className="h-10 bg-white/5 w-full"></div>
          </div>
        ) : transactions.length === 0 ? (
          <div className="p-12 text-center text-slate-500 space-y-2">
            <p className="text-sm font-bold">No transactions match your active filters.</p>
            <p className="text-xs">Try resetting search query or category filters.</p>
            <Button
              variant="outline"
              size="sm"
              onClick={() => {
                setTypeFilter('ALL');
                setCategoryFilter('ALL');
                setSearchQuery('');
              }}
              className="mt-2"
            >
              Reset Filters
            </Button>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs border-collapse">
              <thead>
                <tr className="bg-[#07080B] text-slate-400 border-b border-white/10 uppercase font-mono">
                  <th className="p-3.5">Ref ID / Date</th>
                  <th className="p-3.5">Counterparty & Description</th>
                  <th className="p-3.5">Category</th>
                  <th className="p-3.5">Payment Method</th>
                  <th className="p-3.5">Status</th>
                  <th className="p-3.5 text-right">Amount</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-white/5">
                {transactions.map((txn) => {
                  const isCredit = txn.type === 'CREDIT';

                  return (
                    <tr key={txn.id} className="hover:bg-white/5 transition-colors">
                      <td className="p-3.5 space-y-1">
                        <div className="font-bold text-white">{txn.transactionReference}</div>
                        <div className="text-[10px] text-slate-500 flex items-center gap-1">
                          <Calendar className="w-3 h-3" />
                          {new Date(txn.transactionDate).toLocaleDateString('en-IN', {
                            day: '2-digit',
                            month: 'short',
                            year: 'numeric',
                          })}
                        </div>
                      </td>

                      <td className="p-3.5 space-y-1">
                        <div className="font-bold text-white">{txn.counterparty}</div>
                        <div className="text-[11px] text-slate-400 max-w-xs truncate">
                          {txn.description}
                        </div>
                      </td>

                      <td className="p-3.5">
                        <Badge variant="cyan">{txn.category}</Badge>
                      </td>

                      <td className="p-3.5">
                        <span className="inline-flex items-center gap-1 text-[11px] text-slate-300 bg-white/5 px-2 py-1 border border-white/10">
                          <CreditCard className="w-3 h-3 text-[#00F0FF]" />
                          {txn.paymentMethod}
                        </span>
                      </td>

                      <td className="p-3.5">
                        {txn.status === 'SETTLED' ? (
                          <span className="text-[#00E599] flex items-center gap-1 text-[11px]">
                            <CheckCircle className="w-3.5 h-3.5" /> SETTLED
                          </span>
                        ) : (
                          <span className="text-amber-400 flex items-center gap-1 text-[11px]">
                            <Clock className="w-3.5 h-3.5" /> PENDING
                          </span>
                        )}
                      </td>

                      <td className="p-3.5 text-right font-bold text-sm">
                        <span className={isCredit ? 'text-[#00F0FF]' : 'text-rose-400'}>
                          {isCredit ? `+${formatINR(txn.amount)}` : `-${formatINR(txn.amount)}`}
                        </span>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
}
