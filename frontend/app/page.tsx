import React from 'react';
import { Navbar } from '@/components/layout/Navbar';
import { Hero } from '@/components/landing/Hero';
import { IntelligenceGrid } from '@/components/landing/IntelligenceGrid';
import { DashboardPreview } from '@/components/landing/DashboardPreview';
import { CapabilityPillars } from '@/components/landing/CapabilityPillars';
import { TrustStrip } from '@/components/landing/TrustStrip';
import { FinalCTA } from '@/components/landing/FinalCTA';

export const metadata = {
  title: 'Flowwise | Simple Cash Flow Intelligence for Business Owners',
  description: 'Take the stress out of your business cash flow. Flowwise automatically tracks your cash, unpaid invoices, and upcoming bills in one clean dashboard.',
};

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-[#0B0F19] text-slate-100 flex flex-col selection:bg-cyan-500 selection:text-slate-950 font-sans">
      <Navbar />
      <main className="flex-1">
        <Hero />
        <IntelligenceGrid />
        <DashboardPreview />
        <CapabilityPillars />
        <TrustStrip />
      </main>
      <FinalCTA />
    </div>
  );
}
