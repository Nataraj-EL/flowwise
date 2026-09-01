import React from 'react';
import { Navbar } from '@/components/layout/Navbar';
import { Hero } from '@/components/landing/Hero';
import { IntelligenceGrid } from '@/components/landing/IntelligenceGrid';
import { DashboardPreview } from '@/components/landing/DashboardPreview';
import { CapabilityPillars } from '@/components/landing/CapabilityPillars';
import { TrustStrip } from '@/components/landing/TrustStrip';
import { FinalCTA } from '@/components/landing/FinalCTA';

export const metadata = {
  title: 'Flowwise | Financial Intelligence for Modern Merchants',
  description: 'Know your cash. Grow your business. Flowwise turns fragmented bank accounts, receivables, and payables into one intelligent financial command center.',
};

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-[#060709] text-white flex flex-col selection:bg-cyan-500 selection:text-black font-sans">
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
