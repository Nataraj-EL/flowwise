'use client';

import React, { useState } from 'react';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { askFlowwiseIntelligence, BackendIntelligenceResponseDTO } from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { Bot, Sparkles, Send, ShieldCheck, Layers, Lock, Cpu, CheckCircle } from 'lucide-react';

export const AskFlowwisePanel: React.FC = () => {
  const [question, setQuestion] = useState<string>('Can I afford ₹80,000 of inventory this week?');
  const [response, setResponse] = useState<BackendIntelligenceResponseDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const presetQuestions = [
    'What changed compared with last month?',
    'Why did my cash drop this month?',
    'Which expense increased the most?',
    'Is my cash flow getting better or worse?',
    'Can I afford ₹80,000 of inventory this week?',
  ];

  const handleAsk = async (qText?: string) => {
    const queryToAsk = qText || question;
    if (!queryToAsk.trim()) return;

    setLoading(true);
    setError(null);
    try {
      const data = await askFlowwiseIntelligence(1, queryToAsk.trim());
      setResponse(data);
    } catch (err: any) {
      setError(err.message || 'Failed to communicate with Flowwise Intelligence API');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card variant="glow-cyan" className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div className="flex items-center gap-2">
          <div className="w-7 h-7 bg-black border border-[#00F0FF] flex items-center justify-center">
            <Sparkles className="w-4 h-4 text-[#00F0FF]" />
          </div>
          <div>
            <h3 className="text-base font-bold text-white font-mono uppercase tracking-wider">
              Ask Flowwise AI
            </h3>
            <p className="text-[11px] text-slate-400 font-mono">
              Grounded Local Financial Intelligence
            </p>
          </div>
        </div>

        <div className="flex items-center gap-2">
          <Badge variant={response?.localAiActive ? 'emerald' : 'cyan'} className="gap-1.5 font-mono text-[10px]">
            <Cpu className="w-3.5 h-3.5" />
            {response?.localAiActive ? 'OLLAMA GEMMA 3 4B' : 'LOCAL AI (GEMMA 3 4B)'}
          </Badge>
        </div>
      </div>

      {/* Preset Questions Chips */}
      <div className="space-y-2 font-mono">
        <span className="text-[10px] uppercase text-slate-500 tracking-wider font-semibold">
          Preset Merchant Intelligence Prompts
        </span>
        <div className="flex flex-wrap gap-2">
          {presetQuestions.map((q) => (
            <button
              key={q}
              onClick={() => {
                setQuestion(q);
                handleAsk(q);
              }}
              className="text-xs bg-[#07080B] hover:bg-white/5 border border-white/10 hover:border-[#00F0FF]/40 text-slate-300 hover:text-white px-3 py-1.5 transition-colors text-left"
            >
              {q}
            </button>
          ))}
        </div>
      </div>

      {/* Input Form */}
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleAsk();
        }}
        className="flex gap-2 font-mono"
      >
        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask a question about your business financial position..."
          className="flex-1 bg-[#07080B] border border-white/15 px-4 py-2.5 text-xs text-white placeholder:text-slate-500 focus:outline-none focus:border-[#00F0FF]"
        />
        <Button variant="cyan" size="md" type="submit" disabled={loading} className="gap-2 shrink-0">
          {loading ? (
            <span className="animate-spin text-xs">⏳</span>
          ) : (
            <Send className="w-3.5 h-3.5" />
          )}
          <span>Analyze</span>
        </Button>
      </form>

      {/* Processing Loading Indicator */}
      {loading && (
        <div className="p-6 bg-[#07080B] border border-white/10 space-y-3 font-mono animate-pulse">
          <div className="flex items-center gap-2 text-xs text-[#00F0FF]">
            <Sparkles className="w-4 h-4 animate-spin" />
            <span>Assembling Structured Evidence & Grounding Response via Gemma 3 4B...</span>
          </div>
          <div className="h-4 bg-white/5 w-3/4"></div>
          <div className="h-4 bg-white/5 w-1/2"></div>
        </div>
      )}

      {/* Error Banner */}
      {error && (
        <div className="p-4 bg-rose-500/10 border border-rose-500/30 text-rose-300 font-mono text-xs space-y-1">
          <span className="font-bold">INTELLIGENCE ENGINE NOTICE:</span>
          <p>{error}</p>
        </div>
      )}

      {/* Grounded Response Output */}
      {response && !loading && (
        <div className="space-y-4 font-mono">
          <div className="p-4 bg-[#07080B] border border-[#00F0FF]/30 space-y-3">
            <div className="flex items-center justify-between border-b border-white/10 pb-2 text-xs">
              <span className="text-[#00F0FF] font-bold flex items-center gap-1.5">
                <Bot className="w-4 h-4" /> Grounded Financial Answer
              </span>
              <span className="text-[10px] text-slate-500">{response.modelUsed}</span>
            </div>

            <p className="text-xs sm:text-sm text-slate-200 leading-relaxed">
              {response.answer}
            </p>

            {/* Evidence Summary Pills */}
            {response.evidenceSummary && (
              <div className="pt-2 border-t border-white/10 space-y-2">
                <span className="text-[10px] uppercase text-slate-500 font-bold">
                  Retrieved Financial Evidence Context
                </span>
                <div className="flex flex-wrap gap-2 text-[11px]">
                  {response.evidenceSummary.availableCash && (
                    <div className="px-2.5 py-1 bg-white/5 border border-white/10 text-slate-300">
                      Available Cash: <span className="text-white font-bold">{formatINR(response.evidenceSummary.availableCash)}</span>
                    </div>
                  )}
                  {response.evidenceSummary.netCashFlow && (
                    <div className="px-2.5 py-1 bg-white/5 border border-white/10 text-slate-300">
                      Net Surplus: <span className="text-[#00E599] font-bold">{formatINR(response.evidenceSummary.netCashFlow)}</span>
                    </div>
                  )}
                  {response.evidenceSummary.monthlyBurnRate && (
                    <div className="px-2.5 py-1 bg-white/5 border border-white/10 text-slate-300">
                      Monthly Burn: <span className="text-rose-400 font-bold">{formatINR(response.evidenceSummary.monthlyBurnRate)}</span>
                    </div>
                  )}
                  {response.evidenceSummary.healthScore !== undefined && (
                    <div className="px-2.5 py-1 bg-white/5 border border-white/10 text-slate-300">
                      Health Rating: <span className="text-[#00F0FF] font-bold">{response.evidenceSummary.healthScore}/100</span>
                    </div>
                  )}
                </div>
              </div>
            )}
          </div>

          {/* AI Safety Disclaimer */}
          <div className="text-[10px] text-slate-500 flex items-center gap-1.5">
            <Lock className="w-3 h-3 text-amber-400 shrink-0" />
            <span>{response.disclaimer}</span>
          </div>
        </div>
      )}
    </Card>
  );
};
