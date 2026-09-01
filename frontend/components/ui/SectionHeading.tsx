import React from 'react';
import { cn } from '@/lib/utils';

export interface SectionHeadingProps {
  tag?: string;
  title: string;
  subtitle?: string;
  align?: 'left' | 'center';
  className?: string;
}

export const SectionHeading: React.FC<SectionHeadingProps> = ({
  tag,
  title,
  subtitle,
  align = 'left',
  className,
}) => {
  return (
    <div
      className={cn(
        'space-y-2 mb-8',
        align === 'center' && 'text-center max-w-3xl mx-auto',
        className
      )}
    >
      {tag && (
        <div
          className={cn(
            'inline-flex items-center gap-2 text-[11px] font-mono font-semibold uppercase tracking-widest text-[#00F0FF]',
            align === 'center' && 'justify-center'
          )}
        >
          <span className="w-2 h-2 bg-[#00F0FF] rounded-none inline-block"></span>
          {tag}
        </div>
      )}
      <h2 className="text-2xl sm:text-4xl font-extrabold text-white tracking-tight uppercase">
        {title}
      </h2>
      {subtitle && (
        <p className="text-slate-400 text-sm sm:text-base max-w-2xl font-normal leading-relaxed">
          {subtitle}
        </p>
      )}
    </div>
  );
};
