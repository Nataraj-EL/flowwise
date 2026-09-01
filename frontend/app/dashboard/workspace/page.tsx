'use client';

import React, { useEffect, useState } from 'react';
import {
  fetchMerchantWorkspace,
  BackendMerchantWorkspaceDTO,
} from '@/lib/api';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import Link from 'next/link';
import {
  Building2,
  AlertTriangle,
  CheckCircle2,
  PieChart,
  RefreshCw,
  TrendingUp,
  ArrowRight,
  Zap,
  CreditCard,
  FileText,
  Briefcase,
  LayoutDashboard,
  Layers,
  Landmark,
} from 'lucide-react';

export default function WorkspacePage() {
  const [workspace, setWorkspace] = useState<BackendMerchantWorkspaceDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const loadWorkspace = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantWorkspace(1);
      setWorkspace(data);
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Merchant Workspace API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadWorkspace();
  }, []);

  const formatINR = (val: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(val || 0);
  };

  if (loading) {
    return (
      <div className="space-y-8 font-mono animate-pulse">
        <div className="h-20 bg-[#0E1116] border border-white/10 w-full"></div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-28 bg-[#0E1116] border border-white/10"></div>
          ))}
        </div>
        <div className="h-64 bg-[#0E1116] border border-white/10"></div>
      </div>
    );
  }

  if (error || !workspace) {
    return (
      <div className="py-12 max-w-3xl mx-auto space-y-6 text-center font-mono">
        <Card variant="glow-cyan" className="p-8 space-y-6">
          <div className="w-12 h-12 bg-rose-500/10 border border-rose-500/40 text-rose-400 mx-auto flex items-center justify-center">
            <AlertTriangle className="w-6 h-6" />
          </div>

          <div className="space-y-2">
            <h2 className="text-xl sm:text-2xl font-bold text-white uppercase tracking-tight">
              MERCHANT WORKSPACE UNREACHABLE
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 font-sans leading-relaxed">
              Could not establish connection to Spring Boot Workspace API (`http://localhost:8080/api/v1/merchants/1/workspace`).
            </p>
          </div>

          <div className="p-3 bg-[#07080B] border border-white/10 text-xs text-rose-300 font-mono text-left truncate">
            <span className="text-slate-500">Error:</span> {error}
          </div>

          <Button variant="cyan" size="lg" onClick={loadWorkspace} className="gap-2 mx-auto">
            <RefreshCw className="w-4 h-4" />
            Retry Connection
          </Button>
        </Card>
      </div>
    );
  }

  return (
    <div className="space-y-8 font-mono">
      {/* Merchant Identity Banner */}
      <div className="p-6 bg-[#080B10] border border-[#00F0FF]/30 space-y-4">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div className="space-y-1">
            <div className="flex items-center gap-2">
              <Building2 className="w-6 h-6 text-[#00F0FF]" />
              <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
                {workspace.businessName}
              </h1>
              <Badge variant="demo">MULTI-ACCOUNT WORKSPACE</Badge>
            </div>
            <p className="text-xs sm:text-sm text-slate-400 font-mono">
              {workspace.businessType} • {workspace.industry} • GSTIN: {workspace.demoGstin}
            </p>
          </div>

          <Badge variant="cyan" className="py-2.5 px-4 font-mono text-xs uppercase shrink-0 gap-1.5">
            <Landmark className="w-4 h-4 text-[#00F0FF]" />
            {workspace.connectedAccountsCount} CONNECTED BUSINESS ACCOUNTS
          </Badge>
        </div>
      </div>

      {/* Consolidated Financial Scorecards */}
      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card className="space-y-2 border-[#00F0FF]/30">
          <span className="text-[10px] text-[#00F0FF] uppercase font-bold flex items-center gap-1.5">
            <CheckCircle2 className="w-3.5 h-3.5 text-[#00E599]" />
            Total Available Cash
          </span>
          <div className="text-2xl font-bold text-white">{formatINR(workspace.totalAvailableCash)}</div>
          <div className="text-[11px] text-slate-500">Across all connected bank accounts</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <TrendingUp className="w-3.5 h-3.5 text-[#00F0FF]" />
            Consolidated Net Cash Flow
          </span>
          <div className={`text-2xl font-bold ${workspace.consolidatedNetCashFlow >= 0 ? 'text-[#00E599]' : 'text-rose-400'}`}>
            {formatINR(workspace.consolidatedNetCashFlow)}
          </div>
          <div className="text-[11px] text-slate-500">Current period net movement</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Landmark className="w-3.5 h-3.5 text-[#00F0FF]" />
            Connected Accounts
          </span>
          <div className="text-2xl font-bold text-white">{workspace.connectedAccountsCount}</div>
          <div className="text-[11px] text-slate-500">Active banking feeds</div>
        </Card>

        <Card className="space-y-2">
          <span className="text-[10px] text-slate-400 uppercase font-bold flex items-center gap-1.5">
            <Layers className="w-3.5 h-3.5 text-[#00F0FF]" />
            Consolidated Transactions
          </span>
          <div className="text-2xl font-bold text-[#00F0FF]">{workspace.consolidatedTransactionCount}</div>
          <div className="text-[11px] text-slate-500">Processed ledger items</div>
        </Card>
      </section>

      {/* Connected Business Accounts Breakdown */}
      <section className="space-y-4">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Landmark className="w-5 h-5 text-[#00F0FF]" />
            Connected Bank & Business Accounts
          </h2>
          <Badge variant="neutral" className="text-[10px]">REAL-TIME ACCOUNT FEEDS</Badge>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {workspace.accounts.map((acc) => (
            <Card key={acc.accountId} className="p-6 space-y-4 border-white/10 hover:border-[#00F0FF]/40 transition-colors">
              <div className="flex items-center justify-between border-b border-white/10 pb-3">
                <div>
                  <h3 className="text-base font-bold text-white uppercase">{acc.institutionName}</h3>
                  <p className="text-xs text-slate-400 font-mono">{acc.maskedAccountRef}</p>
                </div>
                <div className="flex items-center gap-2">
                  <Badge variant="cyan" className="text-[9px] uppercase">{acc.accountType}</Badge>
                  <Badge variant="emerald" className="text-[9px] uppercase">{acc.status}</Badge>
                </div>
              </div>

              <div className="space-y-2">
                <div className="flex justify-between items-baseline">
                  <span className="text-xs text-slate-400">Current Balance:</span>
                  <span className="text-xl font-bold text-[#00F0FF]">{formatINR(acc.currentBalance)}</span>
                </div>

                {/* Cash Contribution Progress Bar */}
                <div className="space-y-1">
                  <div className="flex justify-between text-[10px] text-slate-400">
                    <span>Cash Contribution Share:</span>
                    <span className="font-bold text-white">{acc.cashContributionPct}%</span>
                  </div>
                  <div className="w-full h-2 bg-white/5 overflow-hidden border border-white/10">
                    <div
                      className="h-full bg-gradient-to-r from-[#00F0FF] to-[#00E599]"
                      style={{ width: `${Math.min(acc.cashContributionPct, 100)}%` }}
                    ></div>
                  </div>
                </div>
              </div>

              {/* Account Activity Metrics */}
              <div className="grid grid-cols-3 gap-2 pt-2 border-t border-white/10 text-xs">
                <div className="space-y-0.5">
                  <span className="text-[10px] text-slate-500 uppercase block">Total Credits</span>
                  <span className="font-bold text-[#00E599]">{formatINR(acc.totalCredits)}</span>
                </div>
                <div className="space-y-0.5">
                  <span className="text-[10px] text-slate-500 uppercase block">Total Debits</span>
                  <span className="font-bold text-rose-400">{formatINR(acc.totalDebits)}</span>
                </div>
                <div className="space-y-0.5 text-right">
                  <span className="text-[10px] text-slate-500 uppercase block">Transactions</span>
                  <span className="font-bold text-white">{acc.transactionCount}</span>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </section>

      {/* Quick Navigation into Flowwise Intelligence Modules */}
      <section className="space-y-4">
        <div className="border-b border-white/10 pb-3">
          <h2 className="text-lg font-bold text-white uppercase tracking-tight flex items-center gap-2">
            <Zap className="w-5 h-5 text-[#00F0FF]" />
            Quick Workspace Navigation
          </h2>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-4">
          <Link href="/dashboard/command-center">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <LayoutDashboard className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Command Center</div>
              <div className="text-[10px] text-slate-500">Executive Briefing</div>
            </Card>
          </Link>

          <Link href="/dashboard/transactions">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <Layers className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Transactions</div>
              <div className="text-[10px] text-slate-500">Ledger Ingestion</div>
            </Card>
          </Link>

          <Link href="/dashboard/cashflow">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <TrendingUp className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Cash Flow</div>
              <div className="text-[10px] text-slate-500">Burn & Runway</div>
            </Card>
          </Link>

          <Link href="/dashboard/receivables">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <FileText className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Receivables</div>
              <div className="text-[10px] text-slate-500">Customer Invoices</div>
            </Card>
          </Link>

          <Link href="/dashboard/payables">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <CreditCard className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Payables</div>
              <div className="text-[10px] text-slate-500">Vendor Obligations</div>
            </Card>
          </Link>

          <Link href="/dashboard/working-capital">
            <Card className="p-4 space-y-2 text-center hover:border-[#00F0FF] transition-all cursor-pointer group">
              <Briefcase className="w-6 h-6 text-[#00F0FF] mx-auto group-hover:scale-110 transition-transform" />
              <div className="text-xs font-bold text-white uppercase">Working Capital</div>
              <div className="text-[10px] text-slate-500">Liquidity Ratios</div>
            </Card>
          </Link>
        </div>
      </section>
    </div>
  );
}
