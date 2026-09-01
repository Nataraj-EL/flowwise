'use client';

import React, { useState } from 'react';
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
  Wallet,
  Zap,
  Activity,
  Camera,
  ArrowRightLeft,
  Package,
  Settings,
  Menu,
  X,
} from 'lucide-react';
import { cn } from '@/lib/utils';

export const MobileNav: React.FC = () => {
  const [open, setOpen] = useState(false);
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
    { name: 'Pattern Insights', href: '/dashboard/insights', icon: Sparkles, disabled: false },
    { name: 'Scenarios & Forecast', href: '/dashboard/scenarios', icon: LineChart, disabled: false },
    { name: 'Reconciliation', href: '/dashboard/reconciliation', icon: CheckSquare, disabled: false },
    { name: 'Action Center', href: '/dashboard/actions', icon: Zap, disabled: false },
    { name: 'AI Evaluation', href: '/dashboard/evaluation', icon: Activity, disabled: false },
    { name: 'Office Kit', href: '/dashboard/office-kit', icon: Camera, disabled: false },
    { name: 'Inventory', href: '/dashboard/inventory', icon: Package, disabled: true },
    { name: 'Settings', href: '/dashboard/settings', icon: Settings, disabled: true },
  ];

  return (
    <div className="lg:hidden bg-[#08090C] border-b border-white/10 px-4 py-3 flex items-center justify-between font-mono">
      <div className="flex items-center gap-2">
        <button
          onClick={() => setOpen(!open)}
          className="p-2 text-slate-300 hover:text-white bg-[#0E1116] border border-white/10"
          aria-label="Toggle Menu"
        >
          {open ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
        </button>
        <Link href="/" className="flex items-center gap-2">
          <div className="w-7 h-7 bg-black border border-[#00F0FF] flex items-center justify-center font-mono font-bold text-[#00F0FF] text-sm">
            FW
          </div>
          <span className="font-bold text-white tracking-widest text-sm">FLOWWISE</span>
        </Link>
      </div>

      <Badge variant="demo">DEMO</Badge>

      {/* Drawer Overlay */}
      {open && (
        <div className="fixed inset-0 top-14 z-40 bg-[#060709]/95 backdrop-blur-lg p-6 space-y-6">
          <div className="space-y-2 border-b border-white/10 pb-4">
            <div className="text-[10px] uppercase text-slate-500 tracking-widest">Active Demo Merchant</div>
            <div className="text-sm font-bold text-white flex items-center gap-2">
              <Building2 className="w-4 h-4 text-[#00F0FF]" />
              Apex Retail Solutions [DEMO]
            </div>
          </div>

          <nav className="space-y-2 text-sm uppercase">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = pathname === item.href;

              return (
                <div key={item.name}>
                  {item.disabled ? (
                    <div className="flex items-center gap-3 p-3 text-slate-600 border border-transparent opacity-50 cursor-not-allowed">
                      <Icon className="w-4 h-4" />
                      <span>{item.name} (SOON)</span>
                    </div>
                  ) : (
                    <Link
                      href={item.href}
                      onClick={() => setOpen(false)}
                      className={cn(
                        'flex items-center gap-3 p-3 border font-bold',
                        isActive
                          ? 'bg-[#12161F] text-[#00F0FF] border-[#00F0FF]/40'
                          : 'text-slate-300 border-white/5'
                      )}
                    >
                      <Icon className="w-4 h-4" />
                      <span>{item.name}</span>
                    </Link>
                  )}
                </div>
              );
            })}
          </nav>
        </div>
      )}
    </div>
  );
};
