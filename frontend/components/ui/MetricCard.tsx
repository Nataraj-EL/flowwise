import React from 'react';
import { Card } from './Card';
import { Badge } from './Badge';
import { TrendingUp, TrendingDown, Minus } from 'lucide-react';
import { cn } from '@/lib/utils';

export interface MetricCardProps {
  title: string;
  formattedValue: string;
  changeMoM?: number;
  trend?: 'up' | 'down' | 'neutral';
  subtext?: string;
  highlightColor?: 'cyan' | 'emerald' | 'amber' | 'neutral';
  className?: string;
}

export const MetricCard: React.FC<MetricCardProps> = ({
  title,
  formattedValue,
  changeMoM,
  trend = 'neutral',
  subtext,
  highlightColor = 'neutral',
  className,
}) => {
  const isPositive = trend === 'up';
  const isNegative = trend === 'down';

  return (
    <Card className={cn('flex flex-col justify-between h-full group', className)}>
      <div className="flex items-start justify-between gap-2 mb-2">
        <span className="text-xs uppercase font-mono tracking-wider text-slate-400 font-medium">
          {title}
        </span>
        {changeMoM !== undefined && (
          <Badge
            variant={
              isPositive ? 'emerald' : isNegative ? 'rose' : 'neutral'
            }
            className="flex items-center gap-1"
          >
            {isPositive ? (
              <TrendingUp className="w-3 h-3" />
            ) : isNegative ? (
              <TrendingDown className="w-3 h-3" />
            ) : (
              <Minus className="w-3 h-3" />
            )}
            {changeMoM > 0 ? `+${changeMoM}%` : `${changeMoM}%`}
          </Badge>
        )}
      </div>

      <div className="my-2">
        <div
          className={cn(
            'text-2xl sm:text-3xl font-mono font-bold tracking-tight text-white group-hover:text-[#00F0FF] transition-colors',
            highlightColor === 'cyan' && 'text-[#00F0FF]',
            highlightColor === 'emerald' && 'text-[#00E599]'
          )}
        >
          {formattedValue}
        </div>
      </div>

      {subtext && (
        <div className="text-[11px] text-slate-400 font-mono flex items-center gap-1.5 mt-1 border-t border-white/5 pt-2">
          <span className="w-1 h-1 bg-white/30 rounded-full inline-block"></span>
          {subtext}
        </div>
      )}
    </Card>
  );
};
