import React from 'react';
import { cn } from '@/lib/utils';

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost' | 'cyan' | 'emerald';
  size?: 'sm' | 'md' | 'lg';
  children: React.ReactNode;
}

export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  size = 'md',
  className,
  children,
  ...props
}) => {
  const baseStyles = 'inline-flex items-center justify-center font-medium tracking-wide transition-all duration-200 focus:outline-none disabled:opacity-50 disabled:cursor-not-allowed select-none active:scale-[0.98]';

  const variants = {
    primary: 'bg-white text-black hover:bg-slate-200 border border-white/20 font-semibold shadow-sm',
    secondary: 'bg-[#141820] text-slate-200 hover:bg-[#1C222E] border border-white/10 hover:border-white/20',
    outline: 'bg-transparent text-slate-200 hover:bg-white/5 border border-white/20 hover:border-white/40',
    ghost: 'bg-transparent text-slate-400 hover:text-white hover:bg-white/5',
    cyan: 'bg-[#00F0FF] text-black font-bold hover:bg-[#33F3FF] shadow-[0_0_15px_rgba(0,240,255,0.3)] border border-[#00F0FF]',
    emerald: 'bg-[#00E599] text-black font-bold hover:bg-[#33EBAD] shadow-[0_0_15px_rgba(0,229,153,0.3)] border border-[#00E599]',
  };

  const sizes = {
    sm: 'text-xs px-3 py-1.5 gap-1.5 uppercase font-mono tracking-wider',
    md: 'text-sm px-4 py-2.5 gap-2',
    lg: 'text-base px-6 py-3.5 gap-3 font-semibold',
  };

  return (
    <button
      className={cn(baseStyles, variants[variant], sizes[size], className)}
      {...props}
    >
      {children}
    </button>
  );
};
