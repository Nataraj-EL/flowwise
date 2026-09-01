import React from 'react';
import { Navbar } from '@/components/layout/Navbar';
import { Hero } from '@/components/landing/Hero';
import { TrustStrip } from '@/components/landing/TrustStrip';
import { IntelligenceGrid } from '@/components/landing/IntelligenceGrid';
import { DashboardPreview } from '@/components/landing/DashboardPreview';
import { CapabilityPillars } from '@/components/landing/CapabilityPillars';
import { FinalCTA } from '@/components/landing/FinalCTA';

export const metadata = {
  title: 'Flowwise | Financial Intelligence for Modern Merchants',
  description: 'Know your cash. Grow your business. Unified liquidity command center, automated reconciliation, and business health scoring for growing merchants.',
};

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-[#060709] text-white flex flex-col selection:bg-[#00F0FF] selection:text-black">
      <Navbar />
      <main className="flex-1">
        <Hero />
        <TrustStrip />
        <IntelligenceGrid />
        <DashboardPreview />
        <CapabilityPillars />
      </main>
      <FinalCTA />
    </div>
  );
}
