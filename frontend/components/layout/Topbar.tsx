'use client';

import React, { useState } from 'react';
import { DEMO_MERCHANTS } from '@/lib/mock-data';
import { MerchantProfile } from '@/types';
import { Badge } from '@/components/ui/Badge';
import { Input } from '@/components/ui/Input';
import { StatusIndicator } from '@/components/ui/StatusIndicator';
import {
  Building2,
  ChevronDown,
  Search,
  Bell,
  Check,
  User,
  ShieldCheck,
} from 'lucide-react';

export const Topbar: React.FC = () => {
  const [selectedMerchant, setSelectedMerchant] = useState<MerchantProfile>(
    DEMO_MERCHANTS[0]
  );
  const [dropdownOpen, setDropdownOpen] = useState(false);

  return (
    <header className="h-20 bg-[#08090C] border-b border-white/10 px-4 sm:px-6 flex items-center justify-between gap-4 sticky top-0 z-20">
      {/* Left: Merchant Selector Dropdown */}
      <div className="relative">
        <button
          onClick={() => setDropdownOpen(!dropdownOpen)}
          className="flex items-center gap-3 bg-[#0E1116] border border-white/15 px-3.5 py-2 hover:border-[#00F0FF]/50 transition-colors text-left font-mono"
        >
          <div className="w-7 h-7 bg-[#00F0FF]/10 border border-[#00F0FF]/30 flex items-center justify-center text-[#00F0FF]">
            <Building2 className="w-4 h-4" />
          </div>
          <div className="flex flex-col">
            <span className="text-xs font-bold text-white tracking-wider truncate max-w-[160px] sm:max-w-[220px]">
              {selectedMerchant.name}
            </span>
            <span className="text-[10px] text-slate-400">
              GST: {selectedMerchant.demoGstin}
            </span>
          </div>
          <ChevronDown className="w-4 h-4 text-slate-400 ml-1" />
        </button>

        {/* Dropdown Menu */}
        {dropdownOpen && (
          <div className="absolute top-full left-0 mt-2 w-72 bg-[#0E1116] border border-white/20 shadow-2xl z-50 p-1 font-mono">
            <div className="px-3 py-2 text-[10px] text-slate-400 uppercase tracking-widest border-b border-white/10">
              Select Demo Merchant Profile
            </div>
            <div className="py-1">
              {DEMO_MERCHANTS.map((m) => (
                <button
                  key={m.id}
                  onClick={() => {
                    setSelectedMerchant(m);
                    setDropdownOpen(false);
                  }}
                  className="w-full text-left px-3 py-2.5 hover:bg-white/5 flex items-center justify-between text-xs transition-colors"
                >
                  <div>
                    <div className="font-bold text-white">{m.name}</div>
                    <div className="text-[10px] text-slate-400">{m.category}</div>
                  </div>
                  {selectedMerchant.id === m.id && (
                    <Check className="w-4 h-4 text-[#00F0FF]" />
                  )}
                </button>
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Center/Right Actions */}
      <div className="flex items-center gap-4">
        {/* Environment Tag */}
        <div className="hidden sm:flex items-center gap-2">
          <Badge variant="demo" className="gap-1.5 py-1">
            <ShieldCheck className="w-3 h-3 text-amber-400" />
            SYNTHETIC ENVIRONMENT
          </Badge>
          <StatusIndicator status="live" label="LIVE DEMO FEED" />
        </div>

        {/* Search Input */}
        <div className="hidden md:block w-48 lg:w-64">
          <Input
            placeholder="Search transactions, invoices..."
            icon={<Search className="w-4 h-4" />}
          />
        </div>

        {/* Notification Bell */}
        <button
          className="p-2 text-slate-400 hover:text-white bg-[#0E1116] border border-white/10 hover:border-white/20 transition-colors relative"
          aria-label="Notifications"
        >
          <Bell className="w-4 h-4" />
          <span className="absolute top-1 right-1 w-2 h-2 bg-[#00F0FF] rounded-full"></span>
        </button>

        {/* Merchant Avatar */}
        <div className="flex items-center gap-2 pl-2 border-l border-white/10 font-mono">
          <div className="w-8 h-8 bg-slate-800 border border-slate-600 flex items-center justify-center text-slate-300 font-bold text-xs">
            <User className="w-4 h-4" />
          </div>
          <div className="hidden xl:flex flex-col text-[11px]">
            <span className="text-white font-bold">Merchant Console</span>
            <span className="text-slate-500">Admin Privileges</span>
          </div>
        </div>
      </div>
    </header>
  );
};
