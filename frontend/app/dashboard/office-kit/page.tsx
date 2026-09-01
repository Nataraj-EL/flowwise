'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import {
  createDocumentCapture,
  fetchMerchantCaptures,
  confirmDocumentCapture,
  discardDocumentCapture,
  ingestDocumentCapture,
  BackendDocumentCaptureResponseDTO,
  BackendDocumentIngestResponseDTO,
} from '@/lib/api';
import { formatINR } from '@/lib/utils';
import { Card } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import {
  Camera,
  Upload,
  CheckCircle2,
  XCircle,
  FileText,
  Scan,
  Smartphone,
  RefreshCw,
  Zap,
  Building2,
  Calendar,
  Layers,
  ArrowRight,
  ShieldCheck,
  Database,
  ExternalLink,
} from 'lucide-react';

export default function OfficeKitPage() {
  const [captures, setCaptures] = useState<BackendDocumentCaptureResponseDTO[]>([]);
  const [activeCapture, setActiveCapture] = useState<BackendDocumentCaptureResponseDTO | null>(null);
  const [ingestResult, setIngestResult] = useState<BackendDocumentIngestResponseDTO | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [uploading, setUploading] = useState<boolean>(false);
  const [ingesting, setIngesting] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  // Document Form State
  const [docType, setDocType] = useState<'RECEIPT' | 'INVOICE' | 'EXPENSE'>('RECEIPT');
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);

  // Review Form Fields
  const [amount, setAmount] = useState<string>('');
  const [vendor, setVendor] = useState<string>('');
  const [category, setCategory] = useState<string>('OPERATIONS');
  const [reference, setReference] = useState<string>('');

  const loadCaptures = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchMerchantCaptures(1);
      setCaptures(data);
      if (data.length > 0 && !activeCapture) {
        setActiveCapture(data[0]);
        populateReviewForm(data[0]);
      }
    } catch (err: any) {
      setError(err.message || 'Failed to connect to Spring Boot Office Kit API');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCaptures();
  }, []);

  const populateReviewForm = (capture: BackendDocumentCaptureResponseDTO) => {
    setAmount(capture.extractedAmount ? String(capture.extractedAmount) : '');
    setVendor(capture.extractedVendor || '');
    setCategory(capture.extractedCategory || 'OPERATIONS');
    setReference(capture.extractedReference || '');
    setIngestResult(null);
  };

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setUploading(true);
    setError(null);

    // Read preview URL
    const reader = new FileReader();
    reader.onload = async () => {
      const base64 = reader.result as string;
      setPreviewUrl(base64);

      try {
        const response = await createDocumentCapture(1, {
          documentType: docType,
          fileName: file.name,
          fileType: file.type,
          fileSize: file.size,
          fileData: base64,
        });

        setActiveCapture(response);
        populateReviewForm(response);
        setCaptures((prev) => [response, ...prev]);
      } catch (err: any) {
        setError(err.message || 'Failed to upload document capture');
      } finally {
        setUploading(false);
      }
    };
    reader.readAsDataURL(file);
  };

  const handleConfirm = async () => {
    if (!activeCapture) return;
    setUploading(true);
    try {
      const updated = await confirmDocumentCapture(activeCapture.id, {
        amount: amount ? parseFloat(amount) : activeCapture.extractedAmount,
        vendorName: vendor || activeCapture.extractedVendor,
        category: category || activeCapture.extractedCategory,
        reference: reference || activeCapture.extractedReference,
      });
      setActiveCapture(updated);
      setCaptures((prev) => prev.map((c) => (c.id === updated.id ? updated : c)));
    } catch (err: any) {
      setError(err.message || 'Failed to confirm document capture');
    } finally {
      setUploading(false);
    }
  };

  const handleDiscard = async () => {
    if (!activeCapture) return;
    setUploading(true);
    try {
      const updated = await discardDocumentCapture(activeCapture.id);
      setActiveCapture(updated);
      setCaptures((prev) => prev.map((c) => (c.id === updated.id ? updated : c)));
    } catch (err: any) {
      setError(err.message || 'Failed to discard document capture');
    } finally {
      setUploading(false);
    }
  };

  const handleIngest = async () => {
    if (!activeCapture) return;
    setIngesting(true);
    setError(null);
    try {
      const res = await ingestDocumentCapture(activeCapture.id);
      setIngestResult(res);
    } catch (err: any) {
      setError(err.message || 'Failed to ingest document capture into financial ledger');
    } finally {
      setIngesting(false);
    }
  };

  return (
    <div className="space-y-8 font-mono">
      {/* Page Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-6">
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <h1 className="text-2xl sm:text-3xl font-black text-white uppercase tracking-tight">
              Office Kit Phone Capture
            </h1>
            <Badge variant="demo">VERIFIED FINANCIAL INGESTION</Badge>
          </div>
          <p className="text-xs sm:text-sm text-slate-400 font-mono">
            Capture receipts & invoices from phone camera, verify metadata, and ingest into live ledger
          </p>
        </div>

        <Badge variant="cyan" className="py-2 px-3 gap-1.5 font-mono text-xs">
          <Smartphone className="w-4 h-4 text-[#00F0FF]" />
          PLUGGABLE EXTRACTION ADAPTER ACTIVE
        </Badge>
      </div>

      {/* Main Grid: Phone Viewport Capture & Review Form */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
        {/* Left: Mobile Phone Camera Frame Simulator (5 cols) */}
        <div className="lg:col-span-5 flex flex-col items-center">
          <div className="w-full max-w-sm bg-[#060709] border-2 border-white/20 rounded-[32px] p-4 space-y-4 shadow-[0_0_30px_rgba(0,240,255,0.1)] relative overflow-hidden">
            {/* Phone Notch */}
            <div className="w-32 h-4 bg-[#0E1116] mx-auto rounded-b-xl border-x border-b border-white/10 flex items-center justify-center">
              <div className="w-3 h-3 rounded-full bg-slate-800 border border-slate-700"></div>
            </div>

            {/* Document Type Selector Pills */}
            <div className="flex justify-between gap-1 p-1 bg-[#0E1116] border border-white/10 text-[10px]">
              {(['RECEIPT', 'INVOICE', 'EXPENSE'] as const).map((type) => (
                <button
                  key={type}
                  onClick={() => setDocType(type)}
                  className={`flex-1 py-1.5 text-center font-bold transition-all ${
                    docType === type ? 'bg-[#00F0FF] text-black shadow-[0_0_10px_rgba(0,240,255,0.4)]' : 'text-slate-400 hover:text-white'
                  }`}
                >
                  {type}
                </button>
              ))}
            </div>

            {/* Camera Viewport / Image Preview Screen */}
            <div className="relative aspect-[3/4] bg-[#0A0D12] border-2 border-dashed border-white/20 flex flex-col items-center justify-center overflow-hidden">
              {previewUrl || activeCapture?.fileUrlOrData ? (
                <div className="relative w-full h-full">
                  <img
                    src={previewUrl || activeCapture?.fileUrlOrData}
                    alt="Captured Financial Document"
                    className="w-full h-full object-cover"
                  />
                  {/* Scanline Animation Effect during upload */}
                  {uploading && (
                    <div className="absolute inset-0 bg-[#00F0FF]/10 animate-pulse flex items-center justify-center">
                      <div className="w-full h-1 bg-[#00F0FF] shadow-[0_0_15px_#00F0FF] animate-bounce"></div>
                    </div>
                  )}
                </div>
              ) : (
                <div className="text-center p-6 space-y-3">
                  <div className="w-12 h-12 bg-white/5 border border-white/10 flex items-center justify-center mx-auto text-[#00F0FF]">
                    <Camera className="w-6 h-6" />
                  </div>
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-white uppercase">Tap to Snap Photo</p>
                    <p className="text-[10px] text-slate-500">Supports phone camera & photo uploads</p>
                  </div>
                </div>
              )}

              {/* Hidden File Input Triggered by Overlay */}
              <input
                type="file"
                accept="image/*"
                capture="environment"
                onChange={handleFileChange}
                className="absolute inset-0 opacity-0 cursor-pointer w-full h-full"
                aria-label="Upload document photo"
              />
            </div>

            {/* Bottom Shutter Action Button */}
            <label className="w-full py-3 bg-[#0E1116] hover:bg-white/5 border border-white/20 text-white font-bold text-xs flex items-center justify-center gap-2 cursor-pointer transition-colors">
              <Camera className="w-4 h-4 text-[#00F0FF]" />
              <span>{uploading ? 'Processing Extraction...' : 'Capture Document Photo'}</span>
              <input
                type="file"
                accept="image/*"
                capture="environment"
                onChange={handleFileChange}
                className="hidden"
              />
            </label>
          </div>
        </div>

        {/* Right: Extracted Data Review & Ingestion Form (7 cols) */}
        <div className="lg:col-span-7 space-y-6">
          <Card variant="glow-cyan" className="space-y-6">
            <div className="flex items-center justify-between border-b border-white/10 pb-4">
              <div className="flex items-center gap-2">
                <Scan className="w-5 h-5 text-[#00F0FF]" />
                <div>
                  <h3 className="text-base font-bold text-white uppercase tracking-wider">
                    Extracted Field Review & Confirmation
                  </h3>
                  <p className="text-[11px] text-slate-400">
                    Review extracted synthetic metadata before committing to Flowwise ledger
                  </p>
                </div>
              </div>

              {activeCapture && (
                <Badge
                  variant={
                    activeCapture.status === 'CONFIRMED'
                      ? 'emerald'
                      : activeCapture.status === 'DISCARDED'
                      ? 'rose'
                      : 'cyan'
                  }
                  className="py-1 px-3 uppercase text-[10px]"
                >
                  {activeCapture.status}
                </Badge>
              )}
            </div>

            {/* Review Form Inputs */}
            <div className="space-y-4 text-xs">
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1.5">
                  <label className="text-slate-300 font-bold uppercase">Extracted Amount (₹)</label>
                  <Input
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="e.g. 2450.00"
                    className="text-sm font-bold text-[#00F0FF]"
                  />
                </div>

                <div className="space-y-1.5">
                  <label className="text-slate-300 font-bold uppercase">Vendor / Counterparty</label>
                  <Input
                    type="text"
                    value={vendor}
                    onChange={(e) => setVendor(e.target.value)}
                    placeholder="e.g. Metro Commercial Supplies [DEMO]"
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1.5">
                  <label className="text-slate-300 font-bold uppercase">Ledger Category</label>
                  <select
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    className="w-full bg-[#07080B] border border-white/15 text-slate-200 text-xs font-mono px-3 py-2 focus:outline-none focus:border-[#00F0FF]"
                  >
                    {['OPERATIONS', 'INVENTORY', 'LOGISTICS', 'UTILITIES', 'RENT'].map((c) => (
                      <option key={c} value={c}>
                        {c}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="space-y-1.5">
                  <label className="text-slate-300 font-bold uppercase">Invoice / Reference No.</label>
                  <Input
                    type="text"
                    value={reference}
                    onChange={(e) => setReference(e.target.value)}
                    placeholder="e.g. REC-8841"
                  />
                </div>
              </div>

              {/* Action Buttons: Confirm vs Discard */}
              <div className="flex flex-col sm:flex-row gap-3 pt-4 border-t border-white/10">
                <Button
                  variant="emerald"
                  size="lg"
                  onClick={handleConfirm}
                  disabled={!activeCapture || uploading || activeCapture.status === 'CONFIRMED'}
                  className="flex-1 gap-2"
                >
                  <CheckCircle2 className="w-4 h-4" />
                  <span>Confirm Document</span>
                </Button>

                <Button
                  variant="secondary"
                  size="lg"
                  onClick={handleDiscard}
                  disabled={!activeCapture || uploading || activeCapture.status === 'DISCARDED'}
                  className="flex-1 gap-2 text-rose-400 border-rose-500/40 hover:bg-rose-500/10"
                >
                  <XCircle className="w-4 h-4" />
                  <span>Discard Document</span>
                </Button>
              </div>

              {/* Sprint 12: Verified Financial Ingestion Action */}
              {activeCapture && activeCapture.status === 'CONFIRMED' && (
                <div className="p-4 bg-[#080E14] border border-[#00E599]/40 space-y-3">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-2 text-[#00E599] font-bold uppercase">
                      <ShieldCheck className="w-5 h-5" />
                      <span>Verified Document Ready for Ingestion</span>
                    </div>
                    <Badge variant="emerald" className="text-[9px]">READY FOR LEDGER</Badge>
                  </div>

                  <p className="text-[11px] text-slate-300">
                    Document status is verified. Click below to create financial transaction record in ledger with full audit provenance.
                  </p>

                  <Button
                    variant="cyan"
                    size="lg"
                    onClick={handleIngest}
                    disabled={ingesting}
                    className="w-full gap-2"
                  >
                    <Database className="w-4 h-4" />
                    <span>{ingesting ? 'Ingesting into Ledger...' : 'Verified → Add to Financial Ledger'}</span>
                  </Button>
                </div>
              )}

              {/* Ingestion Results Banner */}
              {ingestResult && (
                <div className="p-4 bg-[#070A0F] border border-[#00F0FF]/40 space-y-3 text-xs">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-2 text-[#00F0FF] font-bold uppercase">
                      <CheckCircle2 className="w-5 h-5 text-[#00E599]" />
                      <span>{ingestResult.alreadyIngested ? 'Already Ingested (Idempotent)' : 'Transaction Ledger Record Created'}</span>
                    </div>
                    <Badge variant="cyan" className="text-[9px]">SOURCE: OFFICE KIT</Badge>
                  </div>

                  <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 py-2 border-y border-white/10 text-[11px]">
                    <div>
                      <span className="text-slate-500 block text-[9px] uppercase">Ref No.</span>
                      <span className="font-bold text-white">{ingestResult.transactionReference}</span>
                    </div>
                    <div>
                      <span className="text-slate-500 block text-[9px] uppercase">Amount</span>
                      <span className="font-bold text-[#00F0FF]">{formatINR(ingestResult.amount)}</span>
                    </div>
                    <div>
                      <span className="text-slate-500 block text-[9px] uppercase">Category</span>
                      <span className="font-bold text-slate-200">{ingestResult.category}</span>
                    </div>
                    <div>
                      <span className="text-slate-500 block text-[9px] uppercase">Tx ID</span>
                      <span className="font-bold text-white">#{ingestResult.transactionId}</span>
                    </div>
                  </div>

                  <Link href="/dashboard/transactions" className="inline-flex items-center gap-1.5 text-xs text-[#00F0FF] hover:underline font-bold">
                    <span>View in Transactions Ledger</span>
                    <ExternalLink className="w-3.5 h-3.5" />
                  </Link>
                </div>
              )}
            </div>
          </Card>
        </div>
      </div>

      {/* Capture History List */}
      <Card className="space-y-4">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <div className="flex items-center gap-2">
            <FileText className="w-5 h-5 text-[#00F0FF]" />
            <h3 className="text-sm font-bold text-white uppercase tracking-wider">
              Recent Phone Document Captures
            </h3>
          </div>
          <Badge variant="cyan">{captures.length} CAPTURED DOCUMENTS</Badge>
        </div>

        {captures.length === 0 ? (
          <div className="py-8 text-center text-xs text-slate-500 font-mono">
            No phone captures logged yet. Use the camera viewport above to snap a receipt or invoice.
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs font-mono">
              <thead>
                <tr className="border-b border-white/10 text-slate-500 text-[10px] uppercase">
                  <th className="py-2 px-3">Type</th>
                  <th className="py-2 px-3">Vendor</th>
                  <th className="py-2 px-3">Category</th>
                  <th className="py-2 px-3 text-right">Extracted Amount</th>
                  <th className="py-2 px-3 text-center">Status</th>
                  <th className="py-2 px-3 text-right">Captured Time</th>
                  <th className="py-2 px-3 text-right">Action</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-white/5">
                {captures.map((cap) => (
                  <tr key={cap.id} className="hover:bg-white/5 transition-colors">
                    <td className="py-3 px-3 font-bold text-white">{cap.documentType}</td>
                    <td className="py-3 px-3 text-slate-300">{cap.extractedVendor}</td>
                    <td className="py-3 px-3 text-slate-400 text-[11px]">{cap.extractedCategory}</td>
                    <td className="py-3 px-3 text-right font-bold text-[#00F0FF]">
                      {formatINR(cap.extractedAmount)}
                    </td>
                    <td className="py-3 px-3 text-center">
                      <Badge
                        variant={
                          cap.status === 'CONFIRMED'
                            ? 'emerald'
                            : cap.status === 'DISCARDED'
                            ? 'rose'
                            : 'cyan'
                        }
                        className="text-[9px] py-0.5"
                      >
                        {cap.status}
                      </Badge>
                    </td>
                    <td className="py-3 px-3 text-right text-slate-500 text-[11px]">
                      {cap.capturedAt ? new Date(cap.capturedAt).toLocaleTimeString() : 'Just now'}
                    </td>
                    <td className="py-3 px-3 text-right">
                      <button
                        onClick={() => {
                          setActiveCapture(cap);
                          populateReviewForm(cap);
                        }}
                        className="text-[11px] text-[#00F0FF] hover:underline"
                      >
                        Select
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
}
