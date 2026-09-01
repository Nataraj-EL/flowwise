import React from 'react';
import { X } from 'lucide-react';
import { Button } from './Button';
import { cn } from '@/lib/utils';

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
}

export const Modal: React.FC<ModalProps> = ({
  isOpen,
  onClose,
  title,
  children,
}) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-md animate-in fade-in duration-200">
      <div className="bg-[#0E1116] border border-white/20 w-full max-w-lg p-6 relative shadow-2xl space-y-4">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <h3 className="text-lg font-bold text-white font-mono uppercase tracking-wider">
            {title}
          </h3>
          <Button variant="ghost" size="sm" onClick={onClose} aria-label="Close modal">
            <X className="w-4 h-4" />
          </Button>
        </div>

        <div>{children}</div>
      </div>
    </div>
  );
};
