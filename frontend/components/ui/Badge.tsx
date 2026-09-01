import React from 'react';
import { cn } from '@/lib/utils';

export interface BadgeProps extends React.HTMLAttributes<HTMLSpanElement> {
  variant?: 'cyan' | 'emerald' | 'amber' | 'rose' | 'demo' | 'neutral';
  children: React.ReactNode;
}

export const Badge: React.FC<BadgeProps> = ({
  variant = 'neutral',
  className,
  children,
  ...props
}) => {
  const baseStyles = 'inline-flex items-center px-2 py-0.5 text-[10px] font-mono uppercase font-semibold tracking-widest border border-transparent';

  const variants = {
    cyan: 'bg-[#00F0FF]/10 text-[#00F0FF] border-[#00F0FF]/30',
    emerald: 'bg-[#00E599]/10 text-[#00E599] border-[#00E599]/30',
    amber: 'bg-[#FFB800]/10 text-[#FFB800] border-[#FFB800]/30',
    rose: 'bg-[#FF4757]/10 text-[#FF4757] border-[#FF4757]/30',
    demo: 'bg-amber-500/15 text-amber-300 border-amber-500/40 tracking-widest',
    neutral: 'bg-white/5 text-slate-400 border-white/10',
  };

  return (
    <span className={cn(baseStyles, variants[variant], className)} {...props}>
      {children}
    </span>
  );
};
