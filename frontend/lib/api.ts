export interface BackendMerchantDTO {
  id: number;
  businessName: string;
  displayName: string;
  businessType: string;
  industry: string;
  demoGstin: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendBusinessAccountDTO {
  id: number;
  merchantId: number;
  institutionName: string;
  accountType: string;
  maskedAccountRef: string;
  currentBalance: number;
  currency: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendMerchantDetailDTO {
  merchant: BackendMerchantDTO;
  accounts: BackendBusinessAccountDTO[];
  totalAvailableCash: number;
  connectedAccountsCount: number;
}

export interface BackendAccountDetailDTO {
  accountId: number;
  institutionName: string;
  accountType: string;
  maskedAccountRef: string;
  currentBalance: number;
  currency: string;
  status: string;
  cashContributionPct: number;
  totalCredits: number;
  totalDebits: number;
  netCashFlow: number;
  transactionCount: number;
}

export interface BackendMerchantWorkspaceDTO {
  merchantId: number;
  businessName: string;
  displayName: string;
  businessType: string;
  industry: string;
  demoGstin: string;
  totalAvailableCash: number;
  connectedAccountsCount: number;
  accounts: BackendAccountDetailDTO[];
  consolidatedNetCashFlow: number;
  consolidatedTransactionCount: number;
}

export interface BackendTransactionDTO {
  id: number;
  merchantId: number;
  businessAccountId: number;
  institutionName: string;
  transactionReference: string;
  transactionDate: string;
  description: string;
  amount: number;
  type: 'CREDIT' | 'DEBIT';
  category: string;
  subcategory?: string;
  counterparty: string;
  paymentMethod: string;
  status: 'SETTLED' | 'PENDING' | 'SCHEDULED';
  demoTag: string;
  sourceType?: string;
  sourceCaptureId?: number;
}

export interface BackendCategoryTotalDTO {
  category: string;
  type: 'CREDIT' | 'DEBIT';
  totalAmount: number;
  count: number;
}

export interface BackendTransactionSummaryDTO {
  totalCredits: number;
  totalDebits: number;
  netCashFlow: number;
  transactionCount: number;
  categoryTotals: BackendCategoryTotalDTO[];
}

export interface BackendMonthlyCashFlowDTO {
  month: string;
  inflow: number;
  outflow: number;
  netCashFlow: number;
}

export interface BackendCashFlowSummaryDTO {
  totalInflows: number;
  totalOutflows: number;
  netCashFlow: number;
  operatingInflows: number;
  operatingOutflows: number;
  averageMonthlyOutflow: number;
  burnRate: number;
  cashRunwayMonths: number;
  recurringExpensesEstimate: number;
  upcomingPayablePressure: number;
  liquidityStatus: 'OPTIMAL' | 'MODERATE' | 'CRITICAL';
}

export interface BackendHealthFactorDTO {
  factorName: string;
  score: number;
  maxScore: number;
  trend: 'IMPROVING' | 'STABLE' | 'DETERIORATING';
  explanation: string;
}

export interface BackendBusinessHealthDTO {
  overallScore: number;
  healthStatus: 'HEALTHY' | 'WATCH' | 'AT_RISK';
  factorScores: BackendHealthFactorDTO[];
  positiveSignals: string[];
  riskSignals: string[];
  summaryExplanation: string;
}

export interface BackendIntelligenceResponseDTO {
  question: string;
  answer: string;
  evidenceSummary: Record<string, any>;
  localAiActive: boolean;
  modelUsed: string;
  disclaimer: string;
}

export interface BackendCategoryMovementDTO {
  category: string;
  currentAmount: number;
  previousAmount: number;
  changeAmount: number;
  changePct: number;
  direction: 'INCREASED' | 'DECREASED' | 'STABLE';
}

export interface BackendTemporalSummaryDTO {
  currentMonth: string;
  previousMonth: string;
  currentInflow: number;
  previousInflow: number;
  inflowChangePct: number;
  inflowDirection: 'UP' | 'DOWN' | 'FLAT';
  currentOutflow: number;
  previousOutflow: number;
  outflowChangePct: number;
  outflowDirection: 'UP' | 'DOWN' | 'FLAT';
  currentNetCash: number;
  previousNetCash: number;
  netCashChangePct: number;
  netCashDirection: 'UP' | 'DOWN' | 'FLAT';
  categoryMovements: BackendCategoryMovementDTO[];
  anomalies: string[];
  insufficientHistory: boolean;
  historyMonthCount: number;
}

export interface BackendPeriodProjectionDTO {
  days: number;
  projectedInflow: number;
  projectedOutflow: number;
  projectedEndingCash: number;
  projectedRunwayMonths: number;
}

export interface BackendForecastSummaryDTO {
  currentAvailableCash: number;
  averageMonthlyInflow: number;
  averageMonthlyOutflow: number;
  projections: BackendPeriodProjectionDTO[];
  assumptions: string[];
  estimate: boolean;
}

export interface BackendScenarioRequestDTO {
  amount: number;
  category: string;
}

export interface BackendScenarioResultDTO {
  requestedAmount: number;
  category: string;
  baselineEndingCash: number;
  scenarioEndingCash: number;
  baselineRunwayMonths: number;
  scenarioRunwayMonths: number;
  cashImpact: number;
  runwayImpactMonths: number;
  riskStatus: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  assumptions: string[];
  estimate: boolean;
}

export interface BackendEvidenceItemDTO {
  metricName: string;
  value: any;
  unit: string;
  source: string;
  period: string;
  calculationType: 'ACTUAL' | 'ESTIMATE';
  assumptions: string;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED';
}

export interface BackendFinancialEvidenceSummaryDTO {
  question: string;
  intentCategory: string;
  evidenceItems: BackendEvidenceItemDTO[];
  assumptions: string[];
  overallStatus: string;
  conclusion: string;
}

export interface BackendEvaluationCaseResultDTO {
  caseId: string;
  question: string;
  category: string;
  responseText: string;
  grounded: boolean;
  numericalConsistent: boolean;
  relevant: boolean;
  evidenceCovered: boolean;
  fallbackUsed: boolean;
  latencyMs: number;
  score: number;
}

export interface BackendEvaluationSummaryDTO {
  runId: number;
  runTimestamp: string;
  benchmarkVersion: string;
  totalCases: number;
  overallScore: number;
  groundingScore: number;
  numericalConsistencyScore: number;
  relevanceScore: number;
  evidenceCoverageScore: number;
  unsupportedClaimsCount: number;
  fallbackRate: number;
  avgLatencyMs: number;
  caseResults: BackendEvaluationCaseResultDTO[];
}

export interface BackendDocumentCaptureRequestDTO {
  documentType: 'RECEIPT' | 'INVOICE' | 'EXPENSE';
  fileName?: string;
  fileData?: string;
  fileType?: string;
  fileSize?: number;
  amount?: number;
  vendorName?: string;
  category?: string;
}

export interface BackendDocumentCaptureResponseDTO {
  id: number;
  merchantId: number;
  documentType: 'RECEIPT' | 'INVOICE' | 'EXPENSE';
  fileName: string;
  fileType: string;
  fileSize: number;
  fileUrlOrData?: string;
  capturedAt: string;
  status: 'CAPTURED' | 'EXTRACTED' | 'CONFIRMED' | 'DISCARDED';
  extractedAmount: number;
  extractedVendor: string;
  extractedCategory: string;
  extractedDate: string;
  extractedTax: number;
  extractedReference: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendDocumentConfirmRequestDTO {
  amount?: number;
  vendorName?: string;
  category?: string;
  reference?: string;
}

export interface BackendDocumentIngestResponseDTO {
  captureId: number;
  transactionId: number;
  merchantId: number;
  amount: number;
  category: string;
  counterparty: string;
  transactionReference: string;
  sourceType: string;
  ingestionTimestamp: string;
  alreadyIngested: boolean;
}

export interface BackendFinancialActionDTO {
  id: number;
  merchantId: number;
  actionKey: string;
  title: string;
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  category: string;
  explanation: string;
  supportingEvidence: string;
  recommendedStep: string;
  status: 'OPEN' | 'DISMISSED' | 'RESOLVED';
  createdAt: string;
  updatedAt: string;
}

export interface BackendActionSummaryDTO {
  totalActions: number;
  highPriorityCount: number;
  mediumPriorityCount: number;
  lowPriorityCount: number;
  openCount: number;
  actions: BackendFinancialActionDTO[];
}

export interface BackendReceivableDTO {
  id: number;
  merchantId: number;
  counterparty: string;
  invoiceReference: string;
  invoiceAmount: number;
  amountReceived: number;
  outstandingAmount: number;
  invoiceDate: string;
  dueDate: string;
  status: 'CURRENT' | 'OVERDUE_1_30' | 'OVERDUE_31_60' | 'OVERDUE_60_PLUS' | 'PAID';
  daysOverdue: number;
}

export interface BackendReceivablesSummaryDTO {
  totalOutstanding: number;
  currentReceivables: number;
  overdue1To30Days: number;
  overdue31To60Days: number;
  overdue60PlusDays: number;
  totalOverdue: number;
  collectionRatePct: number;
  overdueRatioPct: number;
  largestOutstandingCounterparty: string;
  largestCounterpartyAmount: number;
  concentrationRatioPct: number;
  estimatedNearTermCollection: number;
  totalInvoicesCount: number;
  overdueInvoicesCount: number;
  receivables: BackendReceivableDTO[];
}

export interface BackendPayableDTO {
  id: number;
  merchantId: number;
  vendor: string;
  billReference: string;
  billAmount: number;
  amountPaid: number;
  outstandingAmount: number;
  billDate: string;
  dueDate: string;
  category: string;
  status: 'DUE_TODAY' | 'DUE_7_DAYS' | 'DUE_30_DAYS' | 'OVERDUE' | 'PAID';
  daysUntilDue: number;
}

export interface BackendPayablesSummaryDTO {
  totalOutstanding: number;
  dueToday: number;
  due7Days: number;
  due30Days: number;
  totalOverdue: number;
  totalPaid: number;
  paymentCoverageRatioPct: number;
  upcomingPayablePressure: number;
  largestVendorObligation: string;
  largestVendorAmount: number;
  totalBillsCount: number;
  overdueBillsCount: number;
  payables: BackendPayableDTO[];
}

export interface BackendWorkingCapitalSummaryDTO {
  netWorkingCapital: number;
  availableCash: number;
  receivablesOutstanding: number;
  payablesOutstanding: number;
  workingCapitalGap: number;
  currentCoverageRatio: number;
  nearTermCoverageRatio: number;
  cashConversionRiskStatus: 'OPTIMAL' | 'MODERATE' | 'HIGH_RISK';
  nearTermCollectionPotential: number;
  upcomingPayablePressure: number;
  topPressureDrivers: string[];
  summaryExplanation: string;
}

export interface BackendCommandCenterSnapshotDTO {
  overallFinancialStatus: 'HEALTHY' | 'WATCH' | 'AT_RISK';
  overallHealthScore: number;
  availableCash: number;
  netCashFlow: number;
  workingCapitalCoverage: number;
  receivablesPressure: number;
  payablesPressure: number;
  forecastRisk: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  top3Priorities: BackendFinancialActionDTO[];
  keyPositiveSignal: string;
  keyRiskSignal: string;
  whatChangedSummary: string;
  generatedAt: string;
}

export interface BackendReconciliationIssueDTO {
  id: string;
  transactionId?: number;
  issueType: 'DUPLICATE' | 'UNCATEGORIZED' | 'SUSPICIOUS_AMOUNT' | 'OFFICE_KIT_PENDING';
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  description: string;
  counterparty: string;
  amount: number;
  transactionDate: string;
  reconciliationStatus: 'UNREVIEWED' | 'RECONCILED' | 'IGNORED' | 'FLAGGED';
  evidenceDetails: string;
}

export interface BackendReconciliationSummaryDTO {
  totalTransactions: number;
  reconciledCount: number;
  unreviewedCount: number;
  ignoredCount: number;
  flaggedCount: number;
  duplicateIssuesCount: number;
  uncategorizedIssuesCount: number;
  suspiciousIssuesCount: number;
  officeKitPendingCount: number;
  reconciliationHealthPct: number;
  issues: BackendReconciliationIssueDTO[];
}

export interface BackendPaymentItemDTO {
  payableId: number;
  vendor: string;
  billReference: string;
  billAmount: number;
  outstandingAmount: number;
  billDate: string;
  dueDate: string;
  category: string;
  priority: 'P1_CRITICAL' | 'P2_HIGH' | 'P3_MEDIUM' | 'P4_DEFERRABLE';
  priorityReason: string;
  daysUntilDue: number;
  advisoryStatus: 'RECOMMENDED' | 'HOLD_NEEDS_FUNDS' | 'DEFERRED' | 'UNSCHEDULED';
}

export interface BackendCashManagementSummaryDTO {
  currentAvailableCash: number;
  upcoming7DayObligations: number;
  upcoming30DayObligations: number;
  expected7DayCollections: number;
  expected30DayCollections: number;
  projected7DayCashPosition: number;
  projected30DayCashPosition: number;
  safePaymentCapacity: number;
  paymentRiskStatus: 'SAFE' | 'CAUTION' | 'AT_RISK';
  topRecommendedPayments: BackendPaymentItemDTO[];
  summaryExplanation: string;
  calculationBasis: string;
  assumptions: string[];
  advisoryNotice: string;
}

export interface BackendPaymentPlanDTO {
  safePaymentCapacity: number;
  totalObligations: number;
  recommendedPaymentTotal: number;
  deferredPaymentTotal: number;
  prioritizedPayments: BackendPaymentItemDTO[];
  deferredPayments: BackendPaymentItemDTO[];
  executionAdvice: string;
}

export interface BackendCreateGoalRequestDTO {
  goalType: 'CASH_RESERVE' | 'WORKING_CAPITAL' | 'DEBT_REDUCTION' | 'RECEIVABLES_COLLECTION' | 'EXPENSE_REDUCTION';
  name: string;
  targetAmount: number;
  targetDate: string;
}

export interface BackendFinancialGoalDTO {
  id: number;
  merchantId: number;
  goalType: string;
  goalCategoryType: 'ACCUMULATION' | 'REDUCTION';
  name: string;
  targetAmount: number;
  currentAmount: number;
  baselineAmount: number;
  progressAmount: number;
  progressPct: number;
  remainingAmount: number;
  targetDate: string;
  daysRemaining: number;
  requiredMonthlyPace: number;
  projectedOutcome: number;
  riskStatus: 'ON_TRACK' | 'AT_RISK' | 'ACHIEVED' | 'EXPIRED' | 'ARCHIVED';
  statusExplanation: string;
  calculationSource: string;
}

export interface BackendCreateDecisionRequestDTO {
  actionId?: number;
  goalId?: number;
  decisionType: string;
  title: string;
  recommendation?: string;
  decisionNotes?: string;
  decisionDate?: string;
}

export interface BackendFinancialDecisionDTO {
  id: number;
  merchantId: number;
  actionId?: number;
  actionTitle?: string;
  goalId?: number;
  goalName?: string;
  decisionType: string;
  title: string;
  recommendation?: string;
  decisionStatus: 'PENDING' | 'ACCEPTED' | 'DECLINED' | 'COMPLETED';
  decisionNotes?: string;
  decisionDate: string;
  outcomeStatus: 'UNKNOWN' | 'POSITIVE' | 'NEGATIVE' | 'NEUTRAL';
  outcomeNotes?: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendDecisionOutcomeDTO {
  outcomeStatus: 'POSITIVE' | 'NEGATIVE' | 'NEUTRAL';
  outcomeNotes?: string;
}

export interface BackendDecisionSummaryDTO {
  totalDecisions: number;
  pendingCount: number;
  acceptedCount: number;
  declinedCount: number;
  completedCount: number;
  positiveOutcomeCount: number;
  negativeOutcomeCount: number;
  neutralOutcomeCount: number;
  unknownOutcomeCount: number;
  successRatePct: number;
}

export interface BackendFinancialInsightDTO {
  id: number;
  merchantId: number;
  insightType: string;
  title: string;
  severity: 'HIGH' | 'MEDIUM' | 'LOW';
  description: string;
  evidenceMetrics: string;
  detectedPeriod: string;
  status: 'NEW' | 'ACKNOWLEDGED' | 'DISMISSED';
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED';
  calculationType: 'ACTUAL' | 'ESTIMATE';
  assumptions?: string;
  createdAt: string;
  updatedAt: string;
}

export interface BackendInsightSummaryDTO {
  totalInsights: number;
  newCount: number;
  acknowledgedCount: number;
  dismissedCount: number;
  highSeverityCount: number;
  mediumSeverityCount: number;
  lowSeverityCount: number;
  sufficientHistory: boolean;
  patternEngineStatus: string;
}

export interface BackendScenarioSimulationRequestDTO {
  scenarioType?: 'BASELINE' | 'CAUTIOUS' | 'STRESS' | 'CUSTOM';
  name?: string;
  revenueModifierPct?: number;
  expenseModifierPct?: number;
  receivableCollectionPct?: number;
  payableAccelerationPct?: number;
  saveScenario?: boolean;
}

export interface BackendScenarioComparisonDTO {
  currentAvailableCash: number;
  currentMonthlyBurnRate: number;
  baselineScenario: BackendFinancialScenarioDTO;
  cautiousScenario: BackendFinancialScenarioDTO;
  stressScenario: BackendFinancialScenarioDTO;
  allScenarios: BackendFinancialScenarioDTO[];
  primaryRiskAlert: string;
  summaryAdvice: string;
}

export interface BackendDecisionOptionDTO {
  id?: number;
  optionKey: string;
  title: string;
  description: string;
  compositeScore: number;
  liquidityScore: number;
  coverageScore: number;
  goalScore: number;
  riskScore: number;
  urgencyScore: number;
  projected7dCash: number;
  projected30dCash: number;
  projected90dCash: number;
  riskStatus: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  goalImpactStatus: 'POSITIVE' | 'NEUTRAL' | 'NEGATIVE';
  assumptions: string;
  evidenceMetrics: string;
  rankOrder: number;
  estimate: boolean;
}

export interface BackendDecisionAnalysisDTO {
  id?: number;
  merchantId: number;
  analysisKey: string;
  title: string;
  recommendedOption: string;
  baselineScore: number;
  dataQualityStatus: 'SUFFICIENT' | 'INSUFFICIENT_DATA';
  inputFingerprint?: string;
  summaryExplanation: string;
  evaluatedAt: string;
  options: BackendDecisionOptionDTO[];
  advisoryNotice: string;
}

export interface BackendOptionPerformanceDTO {
  id?: number;
  optionKey: string;
  totalSampleCount: number;
  positiveOutcomeCount: number;
  negativeOutcomeCount: number;
  successRatePct: number;
  calibrationMultiplier: number;
  avgCashImpactVariance: number;
  accuracyStatus: 'ACCURATE' | 'OVERESTIMATED' | 'UNDERESTIMATED' | 'UNCALIBRATED';
}

export interface BackendDecisionCalibrationDTO {
  id?: number;
  merchantId: number;
  calibrationKey: string;
  totalEvaluatedDecisions: number;
  successfulDecisions: number;
  overallSuccessRatePct: number;
  confidenceLevel: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  dataCompletenessPct: number;
  summaryInsight: string;
  evaluatedAt: string;
  optionPerformances: BackendOptionPerformanceDTO[];
  recentDecisions: BackendFinancialDecisionDTO[];
  advisoryNotice: string;
}

export interface BackendRiskAlertDTO {
  id: number;
  merchantId: number;
  riskKey: string;
  riskType: string;
  severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  title: string;
  description: string;
  baselineValue: number;
  currentValue: number;
  changePct: number;
  thresholdValue: number;
  detectionWindow: string;
  status: 'OPEN' | 'ACKNOWLEDGED' | 'RESOLVED';
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evidenceMetrics: string;
  evaluatedAt: string;
}

export interface BackendRiskMonitorSummaryDTO {
  merchantId: number;
  compositeRiskHealthScore: number;
  overallRiskLevel: 'LOW_RISK' | 'MODERATE_RISK' | 'HIGH_RISK' | 'CRITICAL_RISK';
  totalAlertsCount: number;
  criticalCount: number;
  highCount: number;
  mediumCount: number;
  lowCount: number;
  openCount: number;
  alerts: BackendRiskAlertDTO[];
  recommendedRiskActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendRiskTrajectoryDTO {
  id: number;
  merchantId: number;
  riskKey: string;
  riskType: string;
  trajectoryDirection: 'IMPROVING' | 'STABLE' | 'WORSENING' | 'RESOLVED' | 'INSUFFICIENT_DATA';
  severityTransition: string;
  escalationVelocity: number;
  observedSnapshotsCount: number;
  baselineValue: number;
  currentValue: number;
  scoreDelta: number;
  resolutionTimeHours: number;
  recurrenceCount: number;
  evaluatedAt: string;
}

export interface BackendRiskTrajectorySummaryDTO {
  merchantId: number;
  compositeTrajectoryStatus: 'IMPROVING' | 'STABLE' | 'WORSENING' | 'INSUFFICIENT_DATA';
  totalTrackedRisks: number;
  worseningCount: number;
  stableCount: number;
  improvingCount: number;
  resolvedCount: number;
  avgResolutionTimeHours: number;
  trajectories: BackendRiskTrajectoryDTO[];
  escalationActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendFinancialAnomalyDTO {
  id: number;
  merchantId: number;
  anomalyKey: string;
  anomalyType: string;
  severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  title: string;
  description: string;
  baselineValue: number;
  observedValue: number;
  deviationPct: number;
  thresholdPct: number;
  detectionWindow: string;
  sampleSize: number;
  status: 'OPEN' | 'ACKNOWLEDGED' | 'RESOLVED';
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evidenceMetrics: string;
  evaluatedAt: string;
}

export interface BackendAnomalySummaryDTO {
  merchantId: number;
  totalAnomaliesCount: number;
  criticalCount: number;
  highCount: number;
  mediumCount: number;
  lowCount: number;
  openCount: number;
  anomalies: BackendFinancialAnomalyDTO[];
  recommendedAnomalyActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendSignalCorrelationDTO {
  id: number;
  merchantId: number;
  correlationKey: string;
  primaryTarget: string;
  likelyRootCause: string;
  correlationScore: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  contributingSignalsCount: number;
  matchedSignalsJson: string;
  rankingFormula: string;
  detectionWindow: string;
  evidenceMetrics: string;
  evaluatedAt: string;
}

export interface BackendCorrelationSummaryDTO {
  merchantId: number;
  totalCorrelationsCount: number;
  highConfidenceCount: number;
  topLikelyRootCause: string;
  correlations: BackendSignalCorrelationDTO[];
  recommendedRootCauseActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendFinancialInterventionDTO {
  id: number;
  merchantId: number;
  interventionKey: string;
  interventionType: string;
  title: string;
  description: string;
  priorityScore: number;
  urgencyScore: number;
  impactScore: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  expectedBenefit: string;
  riskIfIgnored: string;
  effortLevel: 'LOW' | 'MEDIUM' | 'HIGH';
  linkedRiskId?: number;
  linkedAnomalyId?: number;
  linkedCorrelationId?: number;
  linkedGoalId?: number;
  status: 'OPEN' | 'ACKNOWLEDGED' | 'COMPLETED' | 'DISMISSED';
  evidenceMetrics: string;
  assumptions: string;
  evaluatedAt: string;
}

export interface BackendInterventionSummaryDTO {
  merchantId: number;
  totalInterventionsCount: number;
  openCount: number;
  highPriorityCount: number;
  topFocusArea: string;
  interventions: BackendFinancialInterventionDTO[];
  recommendedInterventionActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendInterventionOutcomeDTO {
  id: number;
  merchantId: number;
  interventionId: number;
  interventionType: string;
  outcomeStatus: 'SUCCESSFUL' | 'PARTIAL' | 'INEFFECTIVE' | 'INSUFFICIENT_DATA';
  evaluationWindow: string;
  expectedBenefit: string;
  actualBenefit: string;
  benefitVariancePct: number;
  expectedCashImpact: number;
  actualCashImpact: number;
  cashImpactVariancePct: number;
  expectedRiskReduction: number;
  actualRiskReduction: number;
  goalImpactVariancePct: number;
  effectivenessScore: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evidenceMetrics: string;
  assumptions: string;
  evaluatedAt: string;
}

export interface BackendInterventionEffectivenessSummaryDTO {
  merchantId: number;
  totalEvaluatedOutcomesCount: number;
  successfulCount: number;
  partialCount: number;
  ineffectiveCount: number;
  insufficientDataCount: number;
  averageEffectivenessScore: number;
  outcomes: BackendInterventionOutcomeDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendStrategyLearningDTO {
  id: number;
  merchantId: number;
  strategyKey: string;
  interventionType: string;
  contextType: string;
  sampleCount: number;
  effectivenessScore: number;
  learningMultiplier: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evidenceMetrics: string;
  assumptions: string;
  evaluatedAt: string;
}

export interface BackendStrategyLearningSummaryDTO {
  merchantId: number;
  totalEvaluatedStrategiesCount: number;
  topPerformingInterventionType: string;
  highConfidenceCount: number;
  averageLearningMultiplier: number;
  learnings: BackendStrategyLearningDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendFinancialPlanItemDTO {
  id: number;
  planId: number;
  itemKey: string;
  interventionType: string;
  title: string;
  description: string;
  priorityScore: number;
  riskProtectionScore: number;
  financialImpactScore: number;
  urgencyScore: number;
  goalAlignmentScore: number;
  historicalEffectivenessScore: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  expectedBenefit: string;
  riskIfIgnored: string;
  horizon: string;
  rankOrder: number;
  evidenceMetrics: string;
}

export interface BackendFinancialPlanDTO {
  id: number;
  merchantId: number;
  planKey: string;
  horizon: string;
  status: 'DRAFT' | 'ACTIVE' | 'COMPLETED' | 'ARCHIVED';
  overallPlanScore: number;
  primaryFocusArea: string;
  summaryExplanation: string;
  assumptions: string;
  items: BackendFinancialPlanItemDTO[];
  evaluatedAt: string;
}

export interface BackendFinancialPlanSummaryDTO {
  merchantId: number;
  totalPlansCount: number;
  activeHorizon: string;
  activePlanScore: number;
  primaryFocusArea: string;
  activePlan: BackendFinancialPlanDTO;
  plans: BackendFinancialPlanDTO[];
  recommendedPlanActions: BackendFinancialActionDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendFinancialPlanOutcomeDTO {
  id: number;
  merchantId: number;
  planId: number;
  horizon: string;
  outcomeStatus: 'SUCCESSFUL' | 'PARTIAL' | 'INEFFECTIVE' | 'INSUFFICIENT_DATA';
  expectedScore: number;
  actualScore: number;
  scoreVariancePct: number;
  expectedCashImpact: number;
  actualCashImpact: number;
  cashVariancePct: number;
  riskReductionExpected: number;
  riskReductionActual: number;
  goalProgressExpected: number;
  goalProgressActual: number;
  effectivenessScore: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evidenceMetrics: string;
  assumptions: string;
  evaluatedAt: string;
}

export interface BackendPlanOptimizationDTO {
  id: number;
  merchantId: number;
  planContext: string;
  sampleCount: number;
  effectivenessScore: number;
  optimizationMultiplier: number;
  confidenceStatus: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  evaluatedAt: string;
}

export interface BackendFinancialPlanOutcomeSummaryDTO {
  merchantId: number;
  totalEvaluatedOutcomesCount: number;
  successfulCount: number;
  partialCount: number;
  ineffectiveCount: number;
  insufficientDataCount: number;
  averageEffectivenessScore: number;
  outcomes: BackendFinancialPlanOutcomeDTO[];
  optimizationFactors: BackendPlanOptimizationDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface BackendFinancialScenarioItemDTO {
  id: number;
  scenarioId: number;
  interventionType: string;
  interventionId: number;
  rankOrder: number;
  projectedImpact: number;
  projectedRiskReduction: number;
  projectedGoalImpact: number;
  evidenceMetrics: string;
}

export interface BackendFinancialScenarioDTO {
  id: number;
  merchantId: number;
  scenarioKey?: string;
  scenarioName?: string;
  scenarioType?: string;
  name?: string;
  description?: string;
  revenueModifierPct?: number;
  expenseModifierPct?: number;
  receivableCollectionPct?: number;
  payableAccelerationPct?: number;
  projected7dCash?: number;
  projected30dCash?: number;
  projected60dCash?: number;
  projected90dCash?: number;
  runwayMonths?: number;
  riskStatus?: 'FEASIBLE' | 'CAUTION' | 'HIGH_RISK';
  goalAchievable?: boolean;
  goalStatusDetail?: string;
  horizon?: string;
  status?: 'DRAFT' | 'EVALUATED' | 'ARCHIVED';
  baselineScore?: number;
  projectedScore?: number;
  scoreDelta?: number;
  projectedCashImpact?: number;
  projectedRiskReduction?: number;
  projectedGoalImpact?: number;
  confidenceStatus?: 'HIGH' | 'MODERATE' | 'LIMITED' | 'INSUFFICIENT_DATA';
  assumptions?: string;
  evidenceMetrics?: string;
  items?: BackendFinancialScenarioItemDTO[];
  evaluatedAt?: string;
}

export interface BackendFinancialScenarioSummaryDTO {
  merchantId: number;
  totalEvaluatedScenariosCount: number;
  activeHorizon: string;
  baselineScore: number;
  topProjectedScore: number;
  topRankedScenarioName: string;
  topRankedScenario: BackendFinancialScenarioDTO;
  scenarios: BackendFinancialScenarioDTO[];
  summaryExplanation: string;
  advisoryNotice: string;
}

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  error?: string;
  status: number;
  timestamp: string;
}

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export async function fetchMerchants(): Promise<BackendMerchantDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchants from backend (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'API returned failure status');
  return json.data;
}

export async function fetchMerchantDetail(id: number | string): Promise<BackendMerchantDetailDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant details for ID ${id} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantDetailDTO> = await res.json();
  if (!json.success) throw new Error(json.error || `Merchant not found with ID ${id}`);
  return json.data;
}

export async function fetchMerchantAccounts(id: number | string): Promise<BackendBusinessAccountDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${id}/accounts`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant accounts for ID ${id} (HTTP ${res.status})`);
  const json: ApiResponse<BackendBusinessAccountDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve accounts');
  return json.data;
}

export async function fetchMerchantTransactions(
  merchantId: number | string,
  filters?: { type?: string; category?: string; search?: string }
): Promise<BackendTransactionDTO[]> {
  const params = new URLSearchParams();
  if (filters?.type) params.append('type', filters.type);
  if (filters?.category) params.append('category', filters.category);
  if (filters?.search) params.append('search', filters.search);

  const queryString = params.toString() ? `?${params.toString()}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/transactions${queryString}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch transactions for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve transactions');
  return json.data;
}

export async function fetchMerchantTransactionSummary(merchantId: number | string): Promise<BackendTransactionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/transactions/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch transaction summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve transaction summary');
  return json.data;
}

export async function fetchMerchantCashFlowSummary(merchantId: number | string): Promise<BackendCashFlowSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-flow`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch cash flow summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCashFlowSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve cash flow summary');
  return json.data;
}

export async function fetchMerchantMonthlyCashFlow(merchantId: number | string): Promise<BackendMonthlyCashFlowDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-flow/monthly`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch monthly cash flow for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMonthlyCashFlowDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve monthly cash flow');
  return json.data;
}

export async function fetchMerchantHealth(merchantId: number | string): Promise<BackendBusinessHealthDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/health`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch business health for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendBusinessHealthDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve business health');
  return json.data;
}

export async function askFlowwiseIntelligence(
  merchantId: number | string,
  question: string
): Promise<BackendIntelligenceResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/intelligence/query`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ question }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to process intelligence query for ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendIntelligenceResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to generate AI response');
  return json.data;
}

export async function fetchMerchantEvidence(
  merchantId: number | string,
  question: string
): Promise<BackendFinancialEvidenceSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/intelligence/evidence`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ question }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to fetch financial evidence for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialEvidenceSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve financial evidence');
  return json.data;
}

export async function fetchMerchantTemporalSummary(merchantId: number | string): Promise<BackendTemporalSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/temporal/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch temporal summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTemporalSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve temporal summary');
  return json.data;
}

export async function fetchMerchantCategoryMovements(merchantId: number | string): Promise<BackendCategoryMovementDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/temporal/categories`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch category movements for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCategoryMovementDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve category movements');
  return json.data;
}

export async function fetchMerchantForecast(merchantId: number | string): Promise<BackendForecastSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/forecast`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch forecast summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendForecastSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve forecast summary');
  return json.data;
}

export async function simulateMerchantScenario(
  merchantId: number | string,
  scenario: BackendScenarioRequestDTO
): Promise<BackendScenarioResultDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/forecast/scenario`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(scenario),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to simulate scenario for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendScenarioResultDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to simulate scenario');
  return json.data;
}

export async function runEvaluation(): Promise<BackendEvaluationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/evaluation/run`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to execute evaluation suite (HTTP ${res.status})`);
  const json: ApiResponse<BackendEvaluationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to run evaluation');
  return json.data;
}

export async function fetchEvaluationSummary(): Promise<BackendEvaluationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/evaluation/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch evaluation summary (HTTP ${res.status})`);
  const json: ApiResponse<BackendEvaluationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve evaluation summary');
  return json.data;
}

export async function createDocumentCapture(
  merchantId: number | string,
  request: BackendDocumentCaptureRequestDTO
): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/office-kit/captures`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to create document capture for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to create document capture');
  return json.data;
}

export async function fetchMerchantCaptures(merchantId: number | string): Promise<BackendDocumentCaptureResponseDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/office-kit/captures`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch document captures for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve document captures');
  return json.data;
}

export async function fetchCaptureDetail(captureId: number | string): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch capture detail for ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve capture detail');
  return json.data;
}

export async function confirmDocumentCapture(
  captureId: number | string,
  confirmData?: BackendDocumentConfirmRequestDTO
): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/confirm`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(confirmData || {}),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to confirm document capture ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to confirm capture');
  return json.data;
}

export async function discardDocumentCapture(captureId: number | string): Promise<BackendDocumentCaptureResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/discard`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to discard document capture ID ${captureId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentCaptureResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to discard capture');
  return json.data;
}

export async function ingestDocumentCapture(captureId: number | string): Promise<BackendDocumentIngestResponseDTO> {
  const res = await fetch(`${API_BASE_URL}/office-kit/captures/${captureId}/ingest`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to ingest document capture ID ${captureId} into financial ledger (HTTP ${res.status})`);
  const json: ApiResponse<BackendDocumentIngestResponseDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to ingest capture into ledger');
  return json.data;
}

export async function fetchMerchantActions(merchantId: number | string): Promise<BackendActionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/actions`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch merchant actions for ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendActionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve merchant actions');
  return json.data;
}

export async function dismissAction(actionId: number | string): Promise<BackendFinancialActionDTO> {
  const res = await fetch(`${API_BASE_URL}/actions/${actionId}/dismiss`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to dismiss action ID ${actionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialActionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to dismiss action');
  return json.data;
}

export async function resolveAction(actionId: number | string): Promise<BackendFinancialActionDTO> {
  const res = await fetch(`${API_BASE_URL}/actions/${actionId}/resolve`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to resolve action ID ${actionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialActionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to resolve action');
  return json.data;
}

export async function fetchMerchantReceivables(merchantId: number | string): Promise<BackendReceivableDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/receivables`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch receivables for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReceivableDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve receivables');
  return json.data;
}

export async function fetchMerchantReceivablesSummary(merchantId: number | string): Promise<BackendReceivablesSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/receivables/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch receivables summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReceivablesSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve receivables summary');
  return json.data;
}

export async function fetchMerchantPayables(merchantId: number | string): Promise<BackendPayableDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/payables`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch payables for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPayableDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve payables');
  return json.data;
}

export async function fetchMerchantPayablesSummary(merchantId: number | string): Promise<BackendPayablesSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/payables/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch payables summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPayablesSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve payables summary');
  return json.data;
}

export async function fetchMerchantWorkingCapital(merchantId: number | string): Promise<BackendWorkingCapitalSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/working-capital`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch working capital for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendWorkingCapitalSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve working capital summary');
  return json.data;
}

export async function fetchMerchantCommandCenter(merchantId: number | string): Promise<BackendCommandCenterSnapshotDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/command-center`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch command center snapshot for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCommandCenterSnapshotDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve command center snapshot');
  return json.data;
}

export async function fetchMerchantWorkspace(merchantId: number | string): Promise<BackendMerchantWorkspaceDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/workspace`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch workspace for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendMerchantWorkspaceDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve merchant workspace');
  return json.data;
}

export async function fetchAccountSummary(
  merchantId: number | string,
  accountId: number | string
): Promise<BackendAccountDetailDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/accounts/${accountId}/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch summary for account ID ${accountId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendAccountDetailDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve account summary');
  return json.data;
}

export async function fetchMerchantReconciliation(merchantId: number | string): Promise<BackendReconciliationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/reconciliation`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch reconciliation summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReconciliationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve reconciliation summary');
  return json.data;
}

export async function fetchMerchantReconciliationIssues(merchantId: number | string): Promise<BackendReconciliationIssueDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/reconciliation/issues`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch reconciliation issues for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendReconciliationIssueDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve reconciliation issues');
  return json.data;
}

export async function reconcileTransaction(transactionId: number | string, notes?: string): Promise<BackendTransactionDTO> {
  const res = await fetch(`${API_BASE_URL}/transactions/${transactionId}/reconcile`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to reconcile transaction ID ${transactionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to reconcile transaction');
  return json.data;
}

export async function ignoreTransaction(transactionId: number | string, notes?: string): Promise<BackendTransactionDTO> {
  const res = await fetch(`${API_BASE_URL}/transactions/${transactionId}/ignore`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to ignore transaction ID ${transactionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendTransactionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to ignore transaction');
  return json.data;
}

export async function fetchMerchantCashManagement(merchantId: number | string): Promise<BackendCashManagementSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-management`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch cash management summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCashManagementSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve cash management summary');
  return json.data;
}

export async function fetchMerchantPaymentPlan(merchantId: number | string): Promise<BackendPaymentPlanDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/cash-management/payment-plan`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch payment plan for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPaymentPlanDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve payment plan');
  return json.data;
}

export async function fetchMerchantGoals(merchantId: number | string): Promise<BackendFinancialGoalDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/goals`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch financial goals for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialGoalDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve financial goals');
  return json.data;
}

export async function createMerchantGoal(
  merchantId: number | string,
  request: BackendCreateGoalRequestDTO
): Promise<BackendFinancialGoalDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/goals`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to create goal for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialGoalDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to create financial goal');
  return json.data;
}

export async function evaluateGoal(merchantId: number | string, goalId: number | string): Promise<BackendFinancialGoalDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/goals/${goalId}/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate goal ID ${goalId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialGoalDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate goal');
  return json.data;
}

export async function archiveGoal(merchantId: number | string, goalId: number | string): Promise<BackendFinancialGoalDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/goals/${goalId}/archive`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to archive goal ID ${goalId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialGoalDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to archive goal');
  return json.data;
}

export async function fetchMerchantDecisions(merchantId: number | string): Promise<BackendFinancialDecisionDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch decisions for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve decisions');
  return json.data;
}

export async function fetchMerchantDecisionSummary(merchantId: number | string): Promise<BackendDecisionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch decision summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDecisionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve decision summary');
  return json.data;
}

export async function createMerchantDecision(
  merchantId: number | string,
  request: BackendCreateDecisionRequestDTO
): Promise<BackendFinancialDecisionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to create decision for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to create financial decision');
  return json.data;
}

export async function acceptDecision(
  merchantId: number | string,
  decisionId: number | string,
  notes?: string
): Promise<BackendFinancialDecisionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions/${decisionId}/accept`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to accept decision ID ${decisionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to accept decision');
  return json.data;
}

export async function declineDecision(
  merchantId: number | string,
  decisionId: number | string,
  notes?: string
): Promise<BackendFinancialDecisionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions/${decisionId}/decline`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to decline decision ID ${decisionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to decline decision');
  return json.data;
}

export async function completeDecision(
  merchantId: number | string,
  decisionId: number | string,
  notes?: string
): Promise<BackendFinancialDecisionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions/${decisionId}/complete`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ notes }),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to complete decision ID ${decisionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to complete decision');
  return json.data;
}

export async function recordDecisionOutcome(
  merchantId: number | string,
  decisionId: number | string,
  outcome: BackendDecisionOutcomeDTO
): Promise<BackendFinancialDecisionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decisions/${decisionId}/outcome`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(outcome),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to record outcome for decision ID ${decisionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialDecisionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to record decision outcome');
  return json.data;
}

export async function fetchMerchantInsights(merchantId: number | string): Promise<BackendFinancialInsightDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/insights`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch insights for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInsightDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve insights');
  return json.data;
}

export async function fetchMerchantInsightSummary(merchantId: number | string): Promise<BackendInsightSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/insights/summary`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch insight summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendInsightSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve insight summary');
  return json.data;
}

export async function acknowledgeInsight(
  merchantId: number | string,
  insightId: number | string
): Promise<BackendFinancialInsightDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/insights/${insightId}/acknowledge`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to acknowledge insight ID ${insightId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInsightDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to acknowledge insight');
  return json.data;
}

export async function dismissInsight(
  merchantId: number | string,
  insightId: number | string
): Promise<BackendFinancialInsightDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/insights/${insightId}/dismiss`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to dismiss insight ID ${insightId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInsightDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to dismiss insight');
  return json.data;
}

export async function fetchMerchantScenarios(merchantId: number | string): Promise<BackendFinancialScenarioDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/scenarios`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch scenarios for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve scenarios');
  return json.data;
}

export async function fetchMerchantScenarioComparison(merchantId: number | string): Promise<BackendScenarioComparisonDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/scenarios/comparison`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch scenario comparison for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendScenarioComparisonDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve scenario comparison');
  return json.data;
}

export async function simulateScenario(
  merchantId: number | string,
  scenario: BackendScenarioSimulationRequestDTO | BackendScenarioRequestDTO
): Promise<BackendFinancialScenarioDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/scenarios/simulate`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(scenario),
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to simulate scenario for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to simulate scenario');
  return json.data;
}

export async function fetchMerchantDecisionIntelligence(merchantId: number | string): Promise<BackendDecisionAnalysisDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decision-intelligence`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch decision intelligence for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDecisionAnalysisDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve decision intelligence');
  return json.data;
}

export async function fetchLatestDecisionAnalysis(merchantId: number | string): Promise<BackendDecisionAnalysisDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decision-intelligence/analysis`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to evaluate decision analysis for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDecisionAnalysisDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate decision analysis');
  return json.data;
}

export async function fetchMerchantDecisionCalibration(merchantId: number | string): Promise<BackendDecisionCalibrationDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decision-calibration`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch decision calibration for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDecisionCalibrationDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve decision calibration');
  return json.data;
}

export async function fetchLatestDecisionPerformance(merchantId: number | string): Promise<BackendDecisionCalibrationDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/decision-calibration/performance`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to evaluate decision performance for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendDecisionCalibrationDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate decision performance');
  return json.data;
}

export async function fetchMerchantRiskMonitor(merchantId: number | string): Promise<BackendRiskMonitorSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-monitor`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch risk monitor summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskMonitorSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve risk monitor summary');
  return json.data;
}

export async function evaluateMerchantRisks(merchantId: number | string): Promise<BackendRiskMonitorSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-monitor/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate risk monitor for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskMonitorSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate risk monitor');
  return json.data;
}

export async function acknowledgeRiskAlert(
  merchantId: number | string,
  alertId: number | string
): Promise<BackendRiskAlertDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-alerts/${alertId}/acknowledge`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to acknowledge risk alert ID ${alertId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskAlertDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to acknowledge risk alert');
  return json.data;
}

export async function resolveRiskAlert(
  merchantId: number | string,
  alertId: number | string
): Promise<BackendRiskAlertDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-alerts/${alertId}/resolve`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to resolve risk alert ID ${alertId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskAlertDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to resolve risk alert');
  return json.data;
}

export async function fetchMerchantRiskHistory(merchantId: number | string): Promise<BackendRiskTrajectorySummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-history`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch risk trajectory history for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskTrajectorySummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve risk trajectory history');
  return json.data;
}

export async function evaluateMerchantRiskTrajectory(merchantId: number | string): Promise<BackendRiskTrajectorySummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/risk-history/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate risk trajectory for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendRiskTrajectorySummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate risk trajectory');
  return json.data;
}

export async function fetchMerchantAnomalies(merchantId: number | string): Promise<BackendAnomalySummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/anomalies`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch anomaly summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendAnomalySummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve anomaly summary');
  return json.data;
}

export async function evaluateMerchantAnomalies(merchantId: number | string): Promise<BackendAnomalySummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/anomalies/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate financial anomalies for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendAnomalySummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate financial anomalies');
  return json.data;
}

export async function acknowledgeAnomaly(
  merchantId: number | string,
  anomalyId: number | string
): Promise<BackendFinancialAnomalyDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/anomalies/${anomalyId}/acknowledge`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to acknowledge anomaly ID ${anomalyId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialAnomalyDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to acknowledge anomaly');
  return json.data;
}

export async function resolveAnomaly(
  merchantId: number | string,
  anomalyId: number | string
): Promise<BackendFinancialAnomalyDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/anomalies/${anomalyId}/resolve`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to resolve anomaly ID ${anomalyId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialAnomalyDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to resolve anomaly');
  return json.data;
}

export async function fetchMerchantCorrelations(merchantId: number | string): Promise<BackendCorrelationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/correlations`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch correlation summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCorrelationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve correlation summary');
  return json.data;
}

export async function evaluateMerchantCorrelations(merchantId: number | string): Promise<BackendCorrelationSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/correlations/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate signal correlations for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendCorrelationSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate signal correlations');
  return json.data;
}

export async function fetchMerchantInterventions(merchantId: number | string): Promise<BackendInterventionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch intervention summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendInterventionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve intervention summary');
  return json.data;
}

export async function evaluateMerchantInterventions(merchantId: number | string): Promise<BackendInterventionSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate interventions for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendInterventionSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate interventions');
  return json.data;
}

export async function acknowledgeIntervention(
  merchantId: number | string,
  interventionId: number | string
): Promise<BackendFinancialInterventionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions/${interventionId}/acknowledge`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to acknowledge intervention ID ${interventionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInterventionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to acknowledge intervention');
  return json.data;
}

export async function completeIntervention(
  merchantId: number | string,
  interventionId: number | string
): Promise<BackendFinancialInterventionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions/${interventionId}/complete`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to complete intervention ID ${interventionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInterventionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to complete intervention');
  return json.data;
}

export async function dismissIntervention(
  merchantId: number | string,
  interventionId: number | string
): Promise<BackendFinancialInterventionDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions/${interventionId}/dismiss`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to dismiss intervention ID ${interventionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialInterventionDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to dismiss intervention');
  return json.data;
}

export async function fetchMerchantInterventionOutcomes(merchantId: number | string): Promise<BackendInterventionEffectivenessSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/intervention-outcomes`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch intervention outcome summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendInterventionEffectivenessSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve intervention outcome summary');
  return json.data;
}

export async function evaluateInterventionOutcome(
  merchantId: number | string,
  interventionId: number | string,
  window?: string
): Promise<BackendInterventionOutcomeDTO> {
  const queryString = window ? `?window=${window}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/interventions/${interventionId}/outcome/evaluate${queryString}`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate outcome for intervention ID ${interventionId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendInterventionOutcomeDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate intervention outcome');
  return json.data;
}

export async function fetchMerchantStrategyLearning(merchantId: number | string): Promise<BackendStrategyLearningSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/strategy-learning`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch strategy learning summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendStrategyLearningSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve strategy learning summary');
  return json.data;
}

export async function evaluateStrategyLearning(merchantId: number | string): Promise<BackendStrategyLearningSummaryDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/strategy-learning/evaluate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate strategy learning for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendStrategyLearningSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate strategy learning');
  return json.data;
}

export async function fetchMerchantFinancialPlans(
  merchantId: number | string,
  horizon?: string
): Promise<BackendFinancialPlanSummaryDTO> {
  const queryString = horizon ? `?horizon=${horizon}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans${queryString}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch financial plans for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve financial plan summary');
  return json.data;
}

export async function fetchFinancialPlanById(
  merchantId: number | string,
  planId: number | string
): Promise<BackendFinancialPlanDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans/${planId}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch financial plan ID ${planId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve financial plan');
  return json.data;
}

export async function evaluateFinancialPlan(
  merchantId: number | string,
  horizon?: string
): Promise<BackendFinancialPlanSummaryDTO> {
  const queryString = horizon ? `?horizon=${horizon}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans/evaluate${queryString}`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate financial plan for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate financial plan');
  return json.data;
}

export async function activateFinancialPlan(
  merchantId: number | string,
  planId: number | string
): Promise<BackendFinancialPlanDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans/${planId}/activate`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to activate plan ID ${planId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to activate plan');
  return json.data;
}

export async function archiveFinancialPlan(
  merchantId: number | string,
  planId: number | string
): Promise<BackendFinancialPlanDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans/${planId}/archive`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to archive plan ID ${planId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to archive plan');
  return json.data;
}

export async function fetchMerchantPlanOutcomeSummary(
  merchantId: number | string,
  horizon?: string
): Promise<BackendFinancialPlanOutcomeSummaryDTO> {
  const queryString = horizon ? `?horizon=${horizon}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plan-outcomes${queryString}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch plan outcome summary for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanOutcomeSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve plan outcome summary');
  return json.data;
}

export async function fetchMerchantPlanOptimizationFactors(
  merchantId: number | string
): Promise<BackendPlanOptimizationDTO[]> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plan-optimization`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch plan optimization factors for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendPlanOptimizationDTO[]> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve plan optimization factors');
  return json.data;
}

export async function evaluatePlanOutcome(
  merchantId: number | string,
  planId: number | string,
  window?: string
): Promise<BackendFinancialPlanOutcomeDTO> {
  const queryString = window ? `?window=${window}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-plans/${planId}/outcome/evaluate${queryString}`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate plan outcome for plan ID ${planId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialPlanOutcomeDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate plan outcome');
  return json.data;
}

export async function fetchMerchantScenarioSummary(
  merchantId: number | string,
  horizon?: string
): Promise<BackendFinancialScenarioSummaryDTO> {
  const queryString = horizon ? `?horizon=${horizon}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-scenarios${queryString}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch financial scenarios for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve scenario summary');
  return json.data;
}

export async function fetchScenarioById(
  merchantId: number | string,
  scenarioId: number | string
): Promise<BackendFinancialScenarioDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-scenarios/${scenarioId}`, { cache: 'no-store' });
  if (!res.ok) throw new Error(`Failed to fetch scenario ID ${scenarioId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to retrieve scenario');
  return json.data;
}

export async function evaluateScenario(
  merchantId: number | string,
  horizon?: string,
  scenarioName?: string
): Promise<BackendFinancialScenarioSummaryDTO> {
  const params = new URLSearchParams();
  if (horizon) params.append('horizon', horizon);
  if (scenarioName) params.append('scenarioName', scenarioName);

  const queryString = params.toString() ? `?${params.toString()}` : '';
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-scenarios/evaluate${queryString}`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to evaluate scenario for merchant ID ${merchantId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioSummaryDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to evaluate scenario');
  return json.data;
}

export async function archiveScenario(
  merchantId: number | string,
  scenarioId: number | string
): Promise<BackendFinancialScenarioDTO> {
  const res = await fetch(`${API_BASE_URL}/merchants/${merchantId}/financial-scenarios/${scenarioId}/archive`, {
    method: 'POST',
    cache: 'no-store',
  });
  if (!res.ok) throw new Error(`Failed to archive scenario ID ${scenarioId} (HTTP ${res.status})`);
  const json: ApiResponse<BackendFinancialScenarioDTO> = await res.json();
  if (!json.success) throw new Error(json.error || 'Failed to archive scenario');
  return json.data;
}
