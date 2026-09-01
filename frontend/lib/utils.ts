import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

/**
 * Formats monetary numbers according to authentic Indian Rupee formatting standards.
 * Example: 342800 -> "₹3,42,800"
 */
export function formatINR(amount: number): string {
  const isNegative = amount < 0;
  const absAmount = Math.abs(amount);
  
  const formatter = new Intl.NumberFormat('en-IN', {
    maximumFractionDigits: 0,
  });

  const formatted = formatter.format(absAmount);
  return `${isNegative ? '-' : ''}₹${formatted}`;
}
