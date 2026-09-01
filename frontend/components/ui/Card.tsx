import React from 'react';
import { cn } from '@/lib/utils';

export interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: 'default' | 'glow-cyan' | 'glow-emerald' | 'inset';
  children: React.ReactNode;
}

export const Card: React.FC<CardProps> = ({
  variant = 'default',
  className,
  children,
  ...props
}) => {
  const baseStyles = 'bg-[#0E1116] border border-white/10 p-5 relative overflow-hidden transition-all duration-200';
  
  const variants = {
    default: 'hover:border-white/20',
    'glow-cyan': 'border-[#00F0FF]/40 shadow-[0_0_20px_-5px_rgba(0,240,255,0.2)]',
    'glow-emerald': 'border-[#00E599]/40 shadow-[0_0_20px_-5px_rgba(0,229,153,0.2)]',
    inset: 'bg-[#07080B] border-white/5',
  };

  return (
    <div className={cn(baseStyles, variants[variant], className)} {...props}>
      {children}
    </div>
  );
};
