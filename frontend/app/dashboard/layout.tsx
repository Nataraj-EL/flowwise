import React from 'react';
import { Sidebar } from '@/components/layout/Sidebar';
import { Topbar } from '@/components/layout/Topbar';
import { MobileNav } from '@/components/layout/MobileNav';

export const metadata = {
  title: 'Merchant Intelligence Console | Flowwise',
  description: 'Real-time financial command center for modern merchants.',
};

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen bg-[#060709] text-white flex selection:bg-[#00F0FF] selection:text-black">
      {/* Desktop Sidebar */}
      <Sidebar />

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col min-w-0">
        {/* Mobile Navigation Header */}
        <MobileNav />

        {/* Top Header Bar */}
        <Topbar />

        {/* Dynamic Page Viewport */}
        <main className="flex-1 p-4 sm:p-6 lg:p-8 space-y-8 max-w-7xl w-full mx-auto">
          {children}
        </main>
      </div>
    </div>
  );
}
