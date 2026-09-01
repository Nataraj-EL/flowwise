'use client';

import React from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { Badge } from '@/components/ui/Badge';
import {
  LayoutDashboard,
  TrendingUp,
  LineChart,
  BarChart3,
  FileText,
  CreditCard,
  Briefcase,
  Building2,
  CheckSquare,
  History,
  Scale,
  ShieldAlert,
  Sparkles,
  Target,
  BrainCircuit,
  ClipboardCheck,
  ChartNoAxesCombined,
  ChartNoAxesColumnIncreasing,
  GitCompareArrows,
  GitBranch,
  TestTube,
  Wallet,
  Zap,
  Activity,
  Layers,
  ListChecks,
  Camera,
  ArrowRightLeft,
  Package,
  Settings,
  HelpCircle,
  ChevronRight,
  Database,
} from 'lucide-react';
import { cn } from '@/lib/utils';

export const Sidebar: React.FC = () => {
  const pathname = usePathname();

  const navItems = [
    { name: 'Command Center', href: '/dashboard/command-center', icon: LayoutDashboard },
    { name: 'Workspace', href: '/dashboard/workspace', icon: Building2 },
    { name: 'Overview', href: '/dashboard', icon: LayoutDashboard },
    { name: 'Transactions', href: '/dashboard/transactions', icon: ArrowRightLeft, disabled: false },
    { name: 'Cash Flow', href: '/dashboard/cashflow', icon: TrendingUp, disabled: false },
    { name: 'Health & Risk', href: '/dashboard/health', icon: ShieldAlert, disabled: false },
    { name: 'Forecast & Scenarios', href: '/dashboard/forecast', icon: LineChart, disabled: false },
    { name: 'Receivables', href: '/dashboard/receivables', icon: FileText, disabled: false },
    { name: 'Payables', href: '/dashboard/payables', icon: CreditCard, disabled: false },
    { name: 'Working Capital', href: '/dashboard/working-capital', icon: Briefcase, disabled: false },
    { name: 'Cash Management', href: '/dashboard/cash-management', icon: Wallet, disabled: false },
    { name: 'Financial Goals', href: '/dashboard/goals', icon: Target, disabled: false },
    { name: 'Decision History', href: '/dashboard/decisions', icon: History, disabled: false },
    { name: 'Decision Intelligence', href: '/dashboard/decision-intelligence', icon: Scale, disabled: false },
    { name: 'Decision Performance', href: '/dashboard/decision-performance', icon: BarChart3, disabled: false },
    { name: 'Risk Monitor', href: '/dashboard/risk-monitor', icon: ShieldAlert, disabled: false },
    { name: 'Risk History', href: '/dashboard/risk-history', icon: Activity, disabled: false },
    { name: 'Financial Anomalies', href: '/dashboard/anomalies', icon: Zap, disabled: false },
    { name: 'Signal Correlation', href: '/dashboard/correlations', icon: Layers, disabled: false },
    { name: 'Interventions', href: '/dashboard/interventions', icon: ListChecks, disabled: false },
    { name: 'Intervention Outcomes', href: '/dashboard/intervention-outcomes', icon: Target, disabled: false },
    { name: 'Strategy Learning', href: '/dashboard/strategy-learning', icon: BrainCircuit, disabled: false },
    { name: 'Financial Plan', href: '/dashboard/financial-plan', icon: ClipboardCheck, disabled: false },
    { name: 'Plan Optimization', href: '/dashboard/plan-optimization', icon: Zap, disabled: false },
    { name: 'Financial Scenarios', href: '/dashboard/financial-scenarios', icon: GitCompareArrows, disabled: false },
    { name: 'Financial Decisions', href: '/dashboard/financial-decisions', icon: GitBranch, disabled: false },
    { name: 'Decision Outcomes', href: '/dashboard/financial-decision-outcomes', icon: ChartNoAxesColumnIncreasing, disabled: false },
    { name: 'AI Evidence Grounding', href: '/dashboard/evidence-grounding', icon: BrainCircuit, disabled: false },
    { name: 'Plan Outcomes', href: '/dashboard/financial-plan-outcomes', icon: ChartNoAxesCombined, disabled: false },
    { name: 'Scenarios & Forecast', href: '/dashboard/scenarios', icon: LineChart, disabled: false },
    { name: 'Reconciliation', href: '/dashboard/reconciliation', icon: CheckSquare, disabled: false },
    { name: 'Action Center', href: '/dashboard/actions', icon: Zap, disabled: false },
    { name: 'AI Evaluation', href: '/dashboard/evaluation', icon: Activity, disabled: false },
    { name: 'Office Kit', href: '/dashboard/office-kit', icon: Camera, disabled: false },
    { name: 'Inventory', href: '/dashboard/inventory', icon: Package, disabled: true, tag: 'SOON' },
    { name: 'Settings', href: '/dashboard/settings', icon: Settings, disabled: true, tag: 'SOON' },
  ];

  return (
    <aside className="hidden lg:flex flex-col w-64 bg-[#08090C] border-r border-white/10 min-h-screen sticky top-0 z-30 select-none">
      {/* Brand Top Header */}
      <div className="h-20 border-b border-white/10 px-6 flex items-center justify-between">
        <Link href="/" className="flex items-center gap-3">
          <div className="w-8 h-8 bg-black border border-[#00F0FF] flex items-center justify-center font-mono font-black text-[#00F0FF] text-lg">
            FW
          </div>
          <span className="text-lg font-black tracking-widest text-white font-mono uppercase">
            FLOWWISE
          </span>
        </Link>
        <Badge variant="demo">DEMO</Badge>
      </div>

      {/* Navigation List */}
      <div className="flex-1 px-4 py-6 space-y-6">
        <div>
          <div className="px-3 text-[10px] font-mono uppercase tracking-widest text-slate-500 mb-3 font-semibold">
            Merchant Intelligence
          </div>
          <nav className="space-y-1 font-mono text-xs uppercase tracking-wider">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = pathname === item.href;

              return (
                <div key={item.name}>
                  {item.disabled ? (
                    <div
                      className={cn(
                        'flex items-center justify-between px-3 py-2.5 text-slate-500 border border-transparent cursor-not-allowed opacity-60'
                      )}
                    >
                      <div className="flex items-center gap-3">
                        <Icon className="w-4 h-4 text-slate-600" />
                        <span>{item.name}</span>
                      </div>
                      <span className="text-[9px] font-mono text-slate-600 bg-white/5 px-1.5 py-0.5 border border-white/5">
                        {item.tag}
                      </span>
                    </div>
                  ) : (
                    <Link
                      href={item.href}
                      className={cn(
                        'flex items-center justify-between px-3 py-2.5 border transition-all duration-150',
                        isActive
                          ? 'bg-[#12161F] text-[#00F0FF] border-[#00F0FF]/40 font-bold shadow-[0_0_15px_rgba(0,240,255,0.15)]'
                          : 'text-slate-300 hover:text-white hover:bg-white/5 border-transparent'
                      )}
                    >
                      <div className="flex items-center gap-3">
                        <Icon className={cn('w-4 h-4', isActive ? 'text-[#00F0FF]' : 'text-slate-400')} />
                        <span>{item.name}</span>
                      </div>
                      <ChevronRight className={cn('w-3.5 h-3.5', isActive ? 'text-[#00F0FF]' : 'text-slate-600')} />
                    </Link>
                  )}
                </div>
              );
            })}
          </nav>
        </div>

        {/* Database & Arch Status */}
        <div className="pt-4 border-t border-white/10 space-y-3">
          <div className="px-3 text-[10px] font-mono uppercase tracking-widest text-slate-500 font-semibold">
            System Status
          </div>
          <div className="bg-[#0D1016] border border-white/5 p-3 space-y-2">
            <div className="flex items-center justify-between text-[11px] font-mono">
              <span className="text-slate-400 flex items-center gap-1.5">
                <Database className="w-3.5 h-3.5 text-[#00E599]" />
                PostgreSQL Core
              </span>
              <span className="text-[#00E599]">READY</span>
            </div>
            <div className="text-[10px] font-mono text-slate-500">
              Spring Boot + JPA Schema Stubs Active
            </div>
          </div>
        </div>
      </div>

      {/* Footer Info */}
      <div className="p-4 border-t border-white/10 bg-[#060709]">
        <div className="flex items-center justify-between text-[10px] font-mono text-slate-400">
          <span>Flowwise Console v0.1</span>
          <span className="text-amber-400 font-bold">SYNTHETIC</span>
        </div>
      </div>
    </aside>
  );
};
