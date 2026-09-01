'use client';

import React, { useState } from 'react';
import { Card } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { Sparkles, Send, Bot, Lock, ArrowRight } from 'lucide-react';

export const AskFlowwisePanel: React.FC = () => {
  const [prompt, setPrompt] = useState('Can I afford ₹80,000 of inventory this week?');
  const [hasQueried, setHasQueried] = useState(false);

  const examplePrompts = [
    'Can I afford ₹80,000 of inventory this week?',
    'What is my projected end-of-month cash position?',
    'Which pending invoices are at risk of late payment?',
  ];

  return (
    <Card variant="glow-cyan" className="space-y-5">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <div className="w-7 h-7 bg-[#00F0FF]/10 border border-[#00F0FF]/30 flex items-center justify-center text-[#00F0FF]">
            <Sparkles className="w-4 h-4" />
          </div>
          <div>
            <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
              Ask Flowwise Intelligence
            </h3>
            <p className="text-[11px] text-slate-400 font-mono">
              Natural language financial command preview
            </p>
          </div>
        </div>
        <Badge variant="cyan" className="gap-1">
          <Lock className="w-3 h-3" />
          SPRINT 2 PREVIEW
        </Badge>
      </div>

      {/* Quick Prompts Selector */}
      <div className="space-y-2">
        <div className="text-[11px] font-mono text-slate-400 uppercase tracking-widest font-semibold">
          Sample Financial Queries:
        </div>
        <div className="flex flex-wrap gap-2">
          {examplePrompts.map((q, idx) => (
            <button
              key={idx}
              onClick={() => {
                setPrompt(q);
                setHasQueried(true);
              }}
              className="text-xs font-mono bg-[#07080B] hover:bg-white/5 text-slate-300 hover:text-[#00F0FF] border border-white/10 px-3 py-1.5 transition-colors text-left flex items-center gap-1.5"
            >
              <ArrowRight className="w-3 h-3 text-[#00F0FF]" />
              {q}
            </button>
          ))}
        </div>
      </div>

      {/* Input Field */}
      <div className="flex items-center gap-2">
        <input
          type="text"
          value={prompt}
          onChange={(e) => setPrompt(e.target.value)}
          placeholder="Ask Flowwise about cash flow, runway, inventory..."
          className="flex-1 bg-[#07080B] border border-white/15 px-4 py-3 text-sm font-mono text-white placeholder:text-slate-500 focus:outline-none focus:border-[#00F0FF]"
        />
        <Button
          variant="cyan"
          size="md"
          onClick={() => setHasQueried(true)}
          className="gap-2 shrink-0"
        >
          <span>Query</span>
          <Send className="w-4 h-4" />
        </Button>
      </div>

      {/* Disabled Demo Response Area */}
      <div className="bg-[#07080B] border border-white/10 p-4 space-y-3 relative overflow-hidden">
        <div className="flex items-center justify-between text-xs font-mono text-slate-400 border-b border-white/5 pb-2">
          <span className="flex items-center gap-2 text-[#00F0FF]">
            <Bot className="w-4 h-4" />
            AI Financial Assistant Response Area
          </span>
          <span className="text-[10px] text-amber-400 bg-amber-500/10 px-2 py-0.5 border border-amber-500/30">
            OFFLINE — SPRINT 2 INTEGRATION
          </span>
        </div>

        {hasQueried ? (
          <div className="space-y-3 font-mono text-xs text-slate-300">
            <div className="p-2.5 bg-white/5 border border-white/5 text-slate-400">
              <span className="text-slate-500">Query:</span> "{prompt}"
            </div>
            <div className="p-3 bg-[#0E1116] border border-[#00F0FF]/30 space-y-2">
              <div className="flex items-center justify-between text-[#00F0FF] font-bold">
                <span>SIMULATED RESPONSE PREVIEW:</span>
                <span className="text-[10px] text-slate-400">Confidence 96%</span>
              </div>
              <p className="text-slate-300 leading-relaxed">
                "Yes, based on current cash reserves of <strong className="text-white">₹3,24,300</strong> and upcoming payables of <strong className="text-white">₹92,400</strong>, purchasing <strong className="text-white">₹80,000</strong> of inventory leaves a healthy liquid buffer of <strong className="text-[#00E599]">₹1,51,900</strong> for operational contingencies."
              </p>
              <div className="text-[10px] text-slate-500 border-t border-white/5 pt-2">
                * Real-time automated ledger synthesis and AI reasoning engine will be unlocked in Sprint 2.
              </div>
            </div>
          </div>
        ) : (
          <div className="py-4 text-center text-xs font-mono text-slate-500 space-y-1">
            <p>Select or type a financial question above to preview the Ask Flowwise interface.</p>
            <p className="text-[10px] text-slate-600">No live API or LLM connection required for Sprint 1.</p>
          </div>
        )}
      </div>
    </Card>
  );
};
