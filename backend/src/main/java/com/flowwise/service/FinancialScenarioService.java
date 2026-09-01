package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialScenario;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialScenarioRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FinancialScenarioService {

    private final MerchantRepository merchantRepository;
    private final FinancialScenarioRepository scenarioRepository;
    private final CashFlowService cashFlowService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final WorkingCapitalService workingCapitalService;
    private final FinancialGoalService goalService;

    public FinancialScenarioService(MerchantRepository merchantRepository,
                                    FinancialScenarioRepository scenarioRepository,
                                    CashFlowService cashFlowService,
                                    ReceivablesService receivablesService,
                                    PayablesService payablesService,
                                    WorkingCapitalService workingCapitalService,
                                    FinancialGoalService goalService) {
        this.merchantRepository = merchantRepository;
        this.scenarioRepository = scenarioRepository;
        this.cashFlowService = cashFlowService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.workingCapitalService = workingCapitalService;
        this.goalService = goalService;
    }

    public FinancialScenarioDTO simulateScenario(Long merchantId, ScenarioSimulationRequestDTO request) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        validateInputBounds(request);

        FinancialScenarioDTO simulated = calculateScenarioProjections(merchant, request);

        if (request.isSaveScenario()) {
            FinancialScenario scenario = saveOrUpdateScenarioEntity(merchant, request, simulated);
            return mapToDTO(scenario, simulated.getGoalStatusDetail());
        }

        return simulated;
    }

    @Transactional(readOnly = true)
    public ScenarioComparisonDTO getScenarioComparison(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        // Simulate Baseline, Cautious, and Stress scenarios
        ScenarioSimulationRequestDTO baseReq = new ScenarioSimulationRequestDTO("BASELINE", "Baseline Current Operations", BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("100.00"), new BigDecimal("100.00"), false);
        ScenarioSimulationRequestDTO cautReq = new ScenarioSimulationRequestDTO("CAUTIOUS", "Cautious Market Dip", new BigDecimal("-10.00"), new BigDecimal("5.00"), new BigDecimal("80.00"), new BigDecimal("100.00"), false);
        ScenarioSimulationRequestDTO stressReq = new ScenarioSimulationRequestDTO("STRESS", "Stress Liquidity Contraction", new BigDecimal("-25.00"), new BigDecimal("15.00"), new BigDecimal("50.00"), new BigDecimal("100.00"), false);

        FinancialScenarioDTO baseDTO = calculateScenarioProjections(merchant, baseReq);
        FinancialScenarioDTO cautDTO = calculateScenarioProjections(merchant, cautReq);
        FinancialScenarioDTO stressDTO = calculateScenarioProjections(merchant, stressReq);

        List<FinancialScenarioDTO> allScenarios = new ArrayList<>(List.of(baseDTO, cautDTO, stressDTO));

        // Add saved custom scenarios if present
        List<FinancialScenario> savedEntities = scenarioRepository.findByMerchantIdOrderByCreatedAtDesc(merchantId);
        for (FinancialScenario s : savedEntities) {
            if ("CUSTOM".equalsIgnoreCase(s.getScenarioType())) {
                allScenarios.add(mapToDTO(s, "Custom Saved Scenario"));
            }
        }

        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        String alert = "HIGH_RISK".equalsIgnoreCase(stressDTO.getRiskStatus())
                ? "STRESS ALERT: Projected liquidity contracts to ₹" + stressDTO.getProjected90dCash() + " under stress scenario."
                : "LIQUIDITY STABLE: Baseline cash runway provides " + baseDTO.getRunwayMonths() + " months coverage.";

        String advice = "Maintain minimum ₹" + baseDTO.getProjected30dCash().multiply(new BigDecimal("0.20")).setScale(0, RoundingMode.HALF_UP)
                + " liquid reserve buffer to insulate against cautious scenario revenue dips.";

        return new ScenarioComparisonDTO(
                (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"),
                cashFlow.getBurnRate() != null ? cashFlow.getBurnRate() : BigDecimal.ZERO,
                baseDTO,
                cautDTO,
                stressDTO,
                allScenarios,
                alert,
                advice
        );
    }

    @Transactional(readOnly = true)
    public List<FinancialScenarioDTO> getMerchantScenarios(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        ScenarioComparisonDTO comparison = getScenarioComparison(merchantId);
        return comparison.getAllScenarios();
    }

    private FinancialScenarioDTO calculateScenarioProjections(Merchant merchant, ScenarioSimulationRequestDTO req) {
        Long mId = merchant.getId();

        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(mId);
        ReceivablesSummaryDTO receivables = receivablesService.getReceivablesSummary(mId);
        PayablesSummaryDTO payables = payablesService.getPayablesSummary(mId);

        BigDecimal availableCash = (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0)
                ? cashFlow.getOperatingInflows() : new BigDecimal("485000");

        BigDecimal avgInflow = (cashFlow.getTotalInflows() != null && cashFlow.getTotalInflows().compareTo(BigDecimal.ZERO) > 0)
                ? cashFlow.getTotalInflows() : new BigDecimal("185000");

        BigDecimal avgOutflow = (cashFlow.getTotalOutflows() != null && cashFlow.getTotalOutflows().compareTo(BigDecimal.ZERO) > 0)
                ? cashFlow.getTotalOutflows() : new BigDecimal("136000");

        BigDecimal revMod = req.getRevenueModifierPct() != null ? req.getRevenueModifierPct() : BigDecimal.ZERO;
        BigDecimal expMod = req.getExpenseModifierPct() != null ? req.getExpenseModifierPct() : BigDecimal.ZERO;
        BigDecimal recPct = req.getReceivableCollectionPct() != null ? req.getReceivableCollectionPct() : new BigDecimal("100.00");
        BigDecimal payPct = req.getPayableAccelerationPct() != null ? req.getPayableAccelerationPct() : new BigDecimal("100.00");

        BigDecimal inflowMult = BigDecimal.ONE.add(revMod.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        BigDecimal outflowMult = BigDecimal.ONE.add(expMod.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        BigDecimal recMult = recPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal payMult = payPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        BigDecimal modInflow = avgInflow.multiply(inflowMult);
        BigDecimal modOutflow = avgOutflow.multiply(outflowMult);

        BigDecimal rec7d = receivables.getEstimatedNearTermCollection().multiply(recMult);
        BigDecimal rec30d = receivables.getTotalOutstanding().multiply(recMult);
        BigDecimal rec60d = rec30d.multiply(new BigDecimal("1.2"));
        BigDecimal rec90d = rec30d.multiply(new BigDecimal("1.5"));

        BigDecimal pay7d = payables.getDue7Days().multiply(payMult);
        BigDecimal pay30d = payables.getDue30Days().multiply(payMult);
        BigDecimal pay60d = pay30d.multiply(new BigDecimal("1.2"));
        BigDecimal pay90d = pay30d.multiply(new BigDecimal("1.5"));

        // Projections
        BigDecimal proj7d = availableCash.add(modInflow.subtract(modOutflow).multiply(new BigDecimal("7")).divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP)).add(rec7d).subtract(pay7d).max(BigDecimal.ZERO);
        BigDecimal proj30d = availableCash.add(modInflow.subtract(modOutflow)).add(rec30d).subtract(pay30d).max(BigDecimal.ZERO);
        BigDecimal proj60d = availableCash.add(modInflow.subtract(modOutflow).multiply(new BigDecimal("2"))).add(rec60d).subtract(pay60d).max(BigDecimal.ZERO);
        BigDecimal proj90d = availableCash.add(modInflow.subtract(modOutflow).multiply(new BigDecimal("3"))).add(rec90d).subtract(pay90d).max(BigDecimal.ZERO);

        // Runway Math
        BigDecimal netBurn = modOutflow.subtract(modInflow);
        BigDecimal runway;
        if (netBurn.compareTo(BigDecimal.ZERO) <= 0) {
            runway = new BigDecimal("99.00");
        } else {
            runway = availableCash.divide(netBurn, 2, RoundingMode.HALF_UP).max(BigDecimal.ZERO);
        }

        // Risk Status
        String riskStatus;
        if (proj30d.compareTo(BigDecimal.ZERO) <= 0 || runway.compareTo(BigDecimal.ONE) < 0) {
            riskStatus = "HIGH_RISK";
        } else if (runway.compareTo(new BigDecimal("3.00")) < 0) {
            riskStatus = "CAUTION";
        } else {
            riskStatus = "FEASIBLE";
        }

        // Goal Achievability Check
        boolean goalAchievable = true;
        String goalDetail = "Active goals achievable under scenario projections.";

        List<FinancialGoalDTO> activeGoals = goalService.getMerchantGoals(mId);
        for (FinancialGoalDTO g : activeGoals) {
            if ("AT_RISK".equalsIgnoreCase(g.getRiskStatus()) || proj90d.compareTo(g.getTargetAmount()) < 0) {
                if ("STRESS".equalsIgnoreCase(req.getScenarioType()) || "HIGH_RISK".equalsIgnoreCase(riskStatus)) {
                    goalAchievable = false;
                    goalDetail = "Goal '" + g.getName() + "' target of ₹" + g.getTargetAmount() + " is AT RISK under " + req.getScenarioType() + " liquidity contraction.";
                    break;
                }
            }
        }

        String assumptions = "Model Inputs: Inflow Mod=" + revMod + "%, Outflow Mod=" + expMod 
                + "%, Collection Rate=" + recPct + "%. Base Cash=₹" + availableCash 
                + ", Net Burn=₹" + netBurn + "/mo.";

        return new FinancialScenarioDTO(
                null,
                mId,
                req.getScenarioType() != null ? req.getScenarioType() : "CUSTOM",
                req.getName() != null ? req.getName() : "Custom Scenario",
                "Deterministic " + req.getScenarioType() + " projection model",
                revMod,
                expMod,
                recPct,
                payPct,
                proj7d,
                proj30d,
                proj60d,
                proj90d,
                runway,
                riskStatus,
                goalAchievable,
                goalDetail,
                assumptions,
                true,
                Instant.now().toString(),
                Instant.now().toString()
        );
    }

    private void validateInputBounds(ScenarioSimulationRequestDTO req) {
        if (req.getReceivableCollectionPct() != null) {
            if (req.getReceivableCollectionPct().compareTo(BigDecimal.ZERO) < 0 || req.getReceivableCollectionPct().compareTo(new BigDecimal("100.00")) > 0) {
                throw new IllegalArgumentException("receivableCollectionPct must be between 0.00 and 100.00");
            }
        }
        if (req.getPayableAccelerationPct() != null) {
            if (req.getPayableAccelerationPct().compareTo(BigDecimal.ZERO) < 0 || req.getPayableAccelerationPct().compareTo(new BigDecimal("200.00")) > 0) {
                throw new IllegalArgumentException("payableAccelerationPct must be between 0.00 and 200.00");
            }
        }
    }

    private FinancialScenario saveOrUpdateScenarioEntity(Merchant merchant, ScenarioSimulationRequestDTO req, FinancialScenarioDTO dto) {
        Optional<FinancialScenario> existing = scenarioRepository.findByMerchantIdAndScenarioType(merchant.getId(), req.getScenarioType());

        FinancialScenario s = existing.orElseGet(FinancialScenario::new);
        s.setMerchant(merchant);
        s.setScenarioType(req.getScenarioType() != null ? req.getScenarioType() : "CUSTOM");
        s.setName(req.getName() != null ? req.getName() : "Custom Scenario");
        s.setDescription(dto.getDescription());
        s.setRevenueModifierPct(dto.getRevenueModifierPct());
        s.setExpenseModifierPct(dto.getExpenseModifierPct());
        s.setReceivableCollectionPct(dto.getReceivableCollectionPct());
        s.setPayableAccelerationPct(dto.getPayableAccelerationPct());
        s.setProjected7dCash(dto.getProjected7dCash());
        s.setProjected30dCash(dto.getProjected30dCash());
        s.setProjected60dCash(dto.getProjected60dCash());
        s.setProjected90dCash(dto.getProjected90dCash());
        s.setRunwayMonths(dto.getRunwayMonths());
        s.setRiskStatus(dto.getRiskStatus());
        s.setGoalAchievable(dto.getGoalAchievable());
        s.setAssumptions(dto.getAssumptions());

        return scenarioRepository.save(s);
    }

    private FinancialScenarioDTO mapToDTO(FinancialScenario s, String goalDetail) {
        return new FinancialScenarioDTO(
                s.getId(),
                s.getMerchant().getId(),
                s.getScenarioType(),
                s.getName(),
                s.getDescription(),
                s.getRevenueModifierPct(),
                s.getExpenseModifierPct(),
                s.getReceivableCollectionPct(),
                s.getPayableAccelerationPct(),
                s.getProjected7dCash(),
                s.getProjected30dCash(),
                s.getProjected60dCash(),
                s.getProjected90dCash(),
                s.getRunwayMonths(),
                s.getRiskStatus(),
                s.getGoalAchievable(),
                goalDetail,
                s.getAssumptions(),
                true,
                s.getCreatedAt().toString(),
                s.getUpdatedAt().toString()
        );
    }
}
