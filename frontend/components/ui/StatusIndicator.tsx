import React from 'react';
import { cn } from '@/lib/utils';

export interface StatusIndicatorProps {
  status?: 'active' | 'live' | 'warning' | 'offline';
  label: string;
  className?: string;
}

export const StatusIndicator: React.FC<StatusIndicatorProps> = ({
  status = 'active',
  label,
  className,
}) => {
  const colors = {
    active: 'bg-[#00E599]',
    live: 'bg-[#00F0FF]',
    warning: 'bg-[#FFB800]',
    offline: 'bg-slate-500',
  };

  return (
    <div className={cn('inline-flex items-center gap-2 text-xs font-mono text-slate-300', className)}>
      <span className="relative flex h-2 w-2">
        {status === 'live' || status === 'active' ? (
          <span
            className={cn(
              'animate-ping absolute inline-flex h-full w-full rounded-full opacity-75',
              colors[status]
            )}
          ></span>
        ) : null}
        <span
          className={cn(
            'relative inline-flex rounded-full h-2 w-2',
            colors[status]
          )}
        ></span>
      </span>
      <span>{label}</span>
    </div>
  );
};
