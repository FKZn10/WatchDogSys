package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.FundPlanModifyMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundPlanModify;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFundRelease;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.vo.investment.FundPlanModifyHandleVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentPlanVo;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyFlowService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.investment.IFundPlanModifyService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareService;
import com.jpmsoft.projectProducer.service.investment.IInvFundReleaseService;
import com.jpmsoft.projectProducer.service.investment.IInvProjectPlanService;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FundPlanModifyImpl extends ServiceImpl<FundPlanModifyMapper, FundPlanModify> implements IFundPlanModifyService {

    private static final String SORT_FPM_YEAR_CAPITAL_SCALE = "fpmYearCapitalScale";
    private static final String SORT_REMAINING_AMOUNT = "remainingAmount";
    private static final String SORT_FPM_RELEASE_AMOUNT_TOTAL_YEAR = "fpmReleaseAmountTotalYear";
    private static final String SORT_FPM_RELEASE_AMOUNT_TOTAL = "fpmReleaseAmountTotal";
    private static final String SORT_ACTUAL_PAYMENT = "actualPayment";
    private static final Set<String> CALCULATED_SORT_FIELDS = new HashSet<>(Arrays.asList(
            SORT_FPM_YEAR_CAPITAL_SCALE,
            SORT_REMAINING_AMOUNT,
            SORT_FPM_RELEASE_AMOUNT_TOTAL_YEAR,
            SORT_FPM_RELEASE_AMOUNT_TOTAL,
            SORT_ACTUAL_PAYMENT
    ));

    @Resource
    private IInvFundReleaseService invFundReleaseService;
    @Resource
    private IInvProjectPlanService invProjectPlanService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvBudgetDeclareService invBudgetDeclareService;
    @Resource
    private IConPayService conPayService;

    @Resource
    private IConPayApplyFlowService conPayApplyFlowService;

    @Resource
    private ISysDictionaryService sysDictionaryService;


    @Override
    public BaseResponse createFundPlanModify(String ifrId) {
        InvFundRelease obj = invFundReleaseService.getById(ifrId);
        if (obj != null) {
            if (obj.getState() == 7) {
                //开始生调整记录信息
                QueryWrapper<InvProjectPlan> wrapper = new QueryWrapper<>();
                wrapper.eq("Ifr_Id", ifrId);
                List<InvProjectPlan> list = invProjectPlanService.list(wrapper);
                if (list.size() > 0) {
                    List<String> pIdList = list.stream().map(n -> n.getPId()).collect(Collectors.toList());
                    List<String> yearList = list.stream().map(n -> n.getIppYear()).collect(Collectors.toList());
                    Map<String, Object> params = new HashMap<>();
                    params.put("pIds", pIdList);
                    params.put("fpmYears", yearList);
                    List<FundPlanModifyDto> fundPlanModifyList = baseMapper.getFundPlanModifyByPidsAndYears(params);
                    list.forEach(n -> {
                        List<FundPlanModifyDto> newList = fundPlanModifyList.stream()
                                .filter(k -> ValidateUtil.isNotNull(k.getFpmYear()) && k.getFpmYear().toString().equals(n.getIppYear())
                                        && k.getpId().equals(n.getPId())).collect(Collectors.toList());
                        if (newList.size() == 0) {
                            FundPlanModify newObj = new FundPlanModify();
                            newObj.setFpmId(GenerateUUID.getUUID());
                            newObj.setFpmYear(Integer.parseInt(n.getIppYear()));
                            newObj.setPId(n.getPId());
                            newObj.setFpmBudgetItemName(n.getIppBudgetItemName());
                            baseMapper.insert(newObj);
                        }
                    });
                }
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getFundPlanModifyList(FundPlanModifyDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<FundPlanModifyDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName");
        boolean calculatedSort = isCalculatedSort(request);
        if (calculatedSort) {
            params.put("needPage", false);
            params.put("sortFiled", null);
            params.put("sortSideFiled", null);
            params.put("sortType", null);
        }
        List<FundPlanModifyDto> list = baseMapper.getFundPlanModifyList(params.getQuery());
        Integer count = baseMapper.getFundPlanModifyListCount(params.getQuery());

        Map<String, Object> data = new HashMap<>();
        if (list.size() > 0) {
            fillCalculatedAmountFields(list, request.getFpmYear());
            if (calculatedSort) {
                sortByCalculatedField(list, request);
                list = pageList(list, request);
            }

            data.put("dataList", list);
            putFundPlanModifyStatistics(data, list, request.getFpmYear());
        }
        return BaseResponse.ok(data, count);
    }

    private void putFundPlanModifyStatistics(Map<String, Object> data, List<FundPlanModifyDto> list, Integer fpmYear) {
        if (list.size() == 0) {
            return;
        }
        QueryWrapper<FundPlanModify> FundPlanModifyWrapper = new QueryWrapper<>();
        List<FundPlanModify> fundPlanModifyList = baseMapper.selectList(FundPlanModifyWrapper);
        if (fundPlanModifyList.size() > 0) {
            data.put("fpmReceivedFunds", fundPlanModifyList.stream().filter(n -> n.getFpmReceivedFunds() != null).map(n -> n.getFpmReceivedFunds()).reduce(BigDecimal.ZERO, BigDecimal::add));
            data.put("fpmNotFunds", fundPlanModifyList.stream().filter(n -> n.getFpmNotFunds() != null).map(n -> n.getFpmNotFunds()).reduce(BigDecimal.ZERO, BigDecimal::add));
            List<String> pIds = list.stream().map(n -> n.getpId()).collect(Collectors.toList());
            List<InvProjectPlan> ippList = invProjectPlanService.getInvProjectPlanListByPids(pIds);
            List<InvProjectPlan> maxYearList = ippList.stream().filter(e -> ValidateUtil.isNotNull(e.getIppYear()) && Integer.parseInt(e.getIppYear()) <= fpmYear).collect(Collectors.toList());
            List<InvProjectPlan> addPpList = maxYearList.stream().filter(e -> !e.getIppReleaseType().equals("减少")).collect(Collectors.toList());
            List<InvProjectPlan> reducePpList = maxYearList.stream().filter(e -> e.getIppReleaseType().equals("减少")).collect(Collectors.toList());
            BigDecimal addTotal = addPpList.stream().map(p -> p.getIppReleaseFunds()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal reduceTotal = reducePpList.stream().map(p -> p.getIppReleaseFunds()).reduce(BigDecimal.ZERO, BigDecimal::add);
            data.put("fpmReleaseAmountTotal", addTotal.subtract(reduceTotal));
        } else {
            data.put("fpmReceivedFunds", BigDecimal.ZERO);
            data.put("fpmNotFunds", BigDecimal.ZERO);
            data.put("fpmReleaseAmountTotal", BigDecimal.ZERO);
        }
    }

    private void fillCalculatedAmountFields(List<FundPlanModifyDto> list, Integer fpmYear) {
        List<String> pIds = list.stream()
                .map(FundPlanModifyDto::getpId)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (pIds.size() == 0) {
            return;
        }

        List<InvProjectPlan> ippList = invProjectPlanService.getInvProjectPlanListByPids(pIds);
        Map<String, List<InvProjectPlan>> ippMap = ippList.stream()
                .filter(n -> ValidateUtil.isNotEmpty(n.getPId()))
                .collect(Collectors.groupingBy(InvProjectPlan::getPId));

        QueryWrapper<ConPayApplyFlow> conPayPojoWrapper = new QueryWrapper<>();
        conPayPojoWrapper.in("P_Id", pIds);
        conPayPojoWrapper.eq("State", "7");
        List<ConPayApplyFlow> conPayApplyFlowList = conPayApplyFlowService.list(conPayPojoWrapper);
        
        // 构建按项目、按文号的支付金额映射，剩余金额和前端详情页保持同一文号口径。
        Map<String, Map<String, BigDecimal>> paymentByProjectAndDocMap = new HashMap<>();
        for (ConPayApplyFlow paf : conPayApplyFlowList) {
            String pId = paf.getPId();
            if (ValidateUtil.isEmpty(pId)) {
                continue;
            }
            String cpafApplyDetails = paf.getCpafApplyDetails();
            if (ValidateUtil.isNotEmpty(cpafApplyDetails)) {
                List<Map<String, Object>> jsonCpafApplys = JacksonUtils.parseObject(cpafApplyDetails, new TypeReference<List<Map<String, Object>>>() {});
                if (jsonCpafApplys != null && !jsonCpafApplys.isEmpty()) {
                    for (Map<String, Object> detail : jsonCpafApplys) {
                        String code = detail.get("code") != null ? detail.get("code").toString() : "";
                        BigDecimal cpafApplication = detail.get("cpafApplication") != null ? 
                            new BigDecimal(detail.get("cpafApplication").toString()) : BigDecimal.ZERO;
                        
                        if (ValidateUtil.isNotEmpty(code) && cpafApplication.compareTo(BigDecimal.ZERO) > 0) {
                            paymentByProjectAndDocMap.computeIfAbsent(pId, k -> new HashMap<>())
                                    .merge(code, cpafApplication, BigDecimal::add);
                        }
                    }
                }
            }
        }
        
        // 计算项目总支付金额
        Map<String, BigDecimal> actualPaymentMap = conPayApplyFlowList.stream()
                .filter(n -> ValidateUtil.isNotEmpty(n.getPId()))
                .collect(Collectors.groupingBy(
                        ConPayApplyFlow::getPId,
                        Collectors.mapping(n -> defaultBigDecimal(n.getCpafApplication()), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        list.forEach(n -> {
            List<InvProjectPlan> ppList = ippMap.getOrDefault(n.getpId(), Collections.emptyList());
            BigDecimal fpmYearCapitalScale = BigDecimal.ZERO;
            BigDecimal fpmReleaseAmountTotalYear = BigDecimal.ZERO;
            BigDecimal fpmReleaseAmountTotal = BigDecimal.ZERO;
            BigDecimal remainingAmount = BigDecimal.ZERO;
            List<InvProjectPlan> yearPlanList = ppList.stream()
                    .filter(plan -> fpmYear != null && String.valueOf(fpmYear).equals(plan.getIppYear()))
                    .collect(Collectors.toList());
            Map<String, BigDecimal> docPaymentMap = paymentByProjectAndDocMap.getOrDefault(n.getpId(), Collections.emptyMap());

            for (InvProjectPlan plan : ppList) {
                BigDecimal releaseFunds = defaultBigDecimal(plan.getIppReleaseFunds());
                BigDecimal signedReleaseFunds = "减少".equals(plan.getIppReleaseType()) ? releaseFunds.negate() : releaseFunds;
                fpmReleaseAmountTotal = fpmReleaseAmountTotal.add(signedReleaseFunds);
                if (isPlanYearLessOrEqual(plan.getIppYear(), fpmYear)) {
                    fpmReleaseAmountTotalYear = fpmReleaseAmountTotalYear.add(signedReleaseFunds);
                    if (fpmYear != null && String.valueOf(fpmYear).equals(plan.getIppYear())) {
                        fpmYearCapitalScale = fpmYearCapitalScale.add(signedReleaseFunds);
                    }
                }
            }

            for (InvProjectPlan addPlan : yearPlanList) {
                if (!("新增".equals(addPlan.getIppReleaseType()) || "增加".equals(addPlan.getIppReleaseType()))) {
                    continue;
                }
                BigDecimal releaseFunds = defaultBigDecimal(addPlan.getIppReleaseFunds());
                BigDecimal reduceMoney = yearPlanList.stream()
                        .filter(reducePlan -> "减少".equals(reducePlan.getIppReleaseType()))
                        .filter(reducePlan -> ValidateUtil.isNotEmpty(addPlan.getIppDocNumber()))
                        .filter(reducePlan -> ValidateUtil.isNotEmpty(reducePlan.getIppPlanDocNo()))
                        .filter(reducePlan -> reducePlan.getIppPlanDocNo().equals(addPlan.getIppDocNumber()))
                        .map(reducePlan -> defaultBigDecimal(reducePlan.getIppReleaseFunds()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal paidMoney = docPaymentMap.getOrDefault(addPlan.getIppDocNumber(), BigDecimal.ZERO);
                remainingAmount = remainingAmount.add(releaseFunds.subtract(reduceMoney).subtract(paidMoney));
            }

            // actualPayment保持为项目总支付金额
            BigDecimal actualPayment = actualPaymentMap.getOrDefault(n.getpId(), BigDecimal.ZERO);
            
            n.setFpmYearCapitalScale(fpmYearCapitalScale);
            n.setFpmReleaseAmountTotalYear(fpmReleaseAmountTotalYear);
            n.setFpmReleaseAmountTotal(fpmReleaseAmountTotal);
            n.setActualPayment(actualPayment);
            n.setRemainingAmount(remainingAmount);
        });
    }

    private boolean isPlanYearLessOrEqual(String planYear, Integer fpmYear) {
        if (ValidateUtil.isEmpty(planYear) || fpmYear == null) {
            return false;
        }
        return Integer.parseInt(planYear) <= fpmYear;
    }

    private boolean isCalculatedSort(FundPlanModifyDto request) {
        if (ValidateUtil.isEmpty(request.getSortType())) {
            return false;
        }
        return isCalculatedSortField(request.getSortFiled()) || isCalculatedSortField(request.getSortSideFiled());
    }

    private boolean isCalculatedSortField(String sortField) {
        return getCalculatedSortField(sortField) != null;
    }

    private String getCalculatedSortField(String sortField) {
        if (ValidateUtil.isEmpty(sortField)) {
            return null;
        }
        String normalizedSortField = normalizeSortField(sortField);
        for (String calculatedSortField : CALCULATED_SORT_FIELDS) {
            if (normalizeSortField(calculatedSortField).equals(normalizedSortField)) {
                return calculatedSortField;
            }
        }
        return null;
    }

    private String normalizeSortField(String sortField) {
        return sortField.replace("_", "").toLowerCase();
    }

    private void sortByCalculatedField(List<FundPlanModifyDto> list, FundPlanModifyDto request) {
        String sortField = getCalculatedSortField(request.getSortFiled());
        if (sortField == null) {
            sortField = getCalculatedSortField(request.getSortSideFiled());
        }
        String calculatedSortField = sortField;
        Comparator<FundPlanModifyDto> comparator = Comparator.comparing(n -> getCalculatedSortValue(n, calculatedSortField));
        if ("DESC".equalsIgnoreCase(request.getSortType())) {
            comparator = comparator.reversed();
        }
        list.sort(comparator);
    }

    private BigDecimal getCalculatedSortValue(FundPlanModifyDto dto, String sortField) {
        switch (sortField) {
            case SORT_FPM_YEAR_CAPITAL_SCALE:
                return defaultBigDecimal(dto.getFpmYearCapitalScale());
            case SORT_FPM_RELEASE_AMOUNT_TOTAL_YEAR:
                return defaultBigDecimal(dto.getFpmReleaseAmountTotalYear());
            case SORT_FPM_RELEASE_AMOUNT_TOTAL:
                return defaultBigDecimal(dto.getFpmReleaseAmountTotal());
            case SORT_ACTUAL_PAYMENT:
                return defaultBigDecimal(dto.getActualPayment());
            case SORT_REMAINING_AMOUNT:
                return defaultBigDecimal(dto.getRemainingAmount());
            default:
                return BigDecimal.ZERO;
        }
    }

    private List<FundPlanModifyDto> pageList(List<FundPlanModifyDto> list, FundPlanModifyDto request) {
        int start = ValidateUtil.isNotNull(request.getPageStart()) ? request.getPageStart() : 0;
        int size = request.getSize() == 0 ? 10 : request.getSize();
        if (start >= list.size()) {
            return new ArrayList<>();
        }
        int end = Math.min(start + size, list.size());
        return new ArrayList<>(list.subList(start, end));
    }

    private BigDecimal defaultBigDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    @Override
    public BaseResponse getFundPlanModifyBypPid(String pId) {
        List<String> pIds = new ArrayList<>();
        pIds.add(pId);
        Map<String, Object> params = new HashMap<>();
        params.put("pIds", pIds);
        List<FundPlanModifyDto> fundPlanModifyList = baseMapper.getFundPlanModifyByPidsAndYears(params);

        List<InvProjectPlan> invProjectPlanList = invProjectPlanService.getInvProjectPlanListByPids(pIds);
        QueryWrapper<ConPayApplyFlow> conPayPojoWrapper = new QueryWrapper<>();
        conPayPojoWrapper.eq("P_Id", pId);
        conPayPojoWrapper.eq("State", "7");
        List<ConPayApplyFlow> ConPayApplyFlowList = conPayApplyFlowService.list(conPayPojoWrapper);
        List<FundPlanModifyDto> newList = new ArrayList<>();
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("Bd_State", 7);
        for (int i = 0; i < pIds.size(); i++) {
            if (i == 0) {
                wrapper.eq("P_Id", pIds.get(i));
            } else {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
        }
        List<InvBudgetDeclare> bdlList = invBudgetDeclareService.list(wrapper);
        fundPlanModifyList.forEach(n -> {
            List<InvBudgetDeclare> bdPList = bdlList.stream().filter(ite -> ite.getPId().equals(n.getpId())).collect(Collectors.toList());
            if (bdPList.size() > 0) {
                n.setBdAuditAmt(bdPList.stream().map(k -> k.getBdAuditAmt()).reduce(BigDecimal.ZERO, BigDecimal::add));
            } else {
                n.setBdAuditAmt(BigDecimal.ZERO);
            }
            newList.add(n);
        });

        Map<String, Object> data = new HashMap<>();
        data.put("fundPlanModifyList", newList);
        data.put("invProjectPlanList", invProjectPlanList);
        data.put("ConPayApplyFlowList", ConPayApplyFlowList);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getFundPlanModifyHandleBypPid(String pId) {

        List<String> pIds = new ArrayList<>();
        pIds.add(pId);
        Map<String, Object> params = new HashMap<>();
        params.put("pIds", pIds);
        List<FundPlanModifyDto> fundPlanModifyList = baseMapper.getFundPlanModifyByPidsAndYears(params);

        //获取字典配置信息
       List<SysDictionary>  dicList= sysDictionaryService.getSysDictionaryByCode("JPMsoft_TZKZZJLY");

        List<InvProjectPlan> invProjectPlanList = invProjectPlanService.getInvProjectPlanListByPids(pIds);
        QueryWrapper<ConPayApplyFlow> conPayPojoWrapper = new QueryWrapper<>();
        conPayPojoWrapper.eq("P_Id", pId);
        conPayPojoWrapper.eq("State", "7");
        List<ConPayApplyFlow> ConPayApplyFlowList = conPayApplyFlowService.list(conPayPojoWrapper);
        List<FundPlanModifyDto> newList = new ArrayList<>();
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("Bd_State", 7);
        for (int i = 0; i < pIds.size(); i++) {
            if (i == 0) {
                wrapper.eq("P_Id", pIds.get(i));
            } else {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
        }
        List<InvBudgetDeclare> bdlList = invBudgetDeclareService.list(wrapper);

        List<FundPlanModifyHandleVo> data = new ArrayList<>();

        List<Integer> yearList = new ArrayList<>();
        fundPlanModifyList = fundPlanModifyList.stream().sorted(Comparator.comparing(FundPlanModifyDto::getFpmYear)).collect(Collectors.toList());
        yearList = fundPlanModifyList.stream().map(n -> n.getFpmYear()).collect(Collectors.toList());
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(yearList);
        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
        BigDecimal cumulativeAmount = BigDecimal.ZERO;
        for (Integer n : listWithoutDuplicates) {
            FundPlanModifyHandleVo obj = new FundPlanModifyHandleVo();
            obj.setFpmYear(n);
            BigDecimal yearCumulativeAmount = BigDecimal.ZERO;
            List<FundPlanModifyDto> fpdList = fundPlanModifyList.stream().filter(k -> k.getFpmYear() == n).collect(Collectors.toList());
            FundPlanModifyDto fundPlanModify = fpdList.get(0);
            List<InvProjectPlan> InvProjectPlans = invProjectPlanList.stream().filter(k -> k.getIppYear().equals(n.toString())).collect(Collectors.toList());

            BigDecimal remainingAmount = BigDecimal.ZERO;
            obj.setFormList(fundPlanModify);
            if (InvProjectPlans.size() > 0) {
                InvProjectPlans = InvProjectPlans.stream().sorted(Comparator.comparing(InvProjectPlan::getIppCreateTime)).collect(Collectors.toList());
                BigDecimal cpafApplyDetailsCumulative = BigDecimal.ZERO;
                List<InvestmentPlanVo> ippList=new ArrayList<>();
                for (InvProjectPlan ase : InvProjectPlans) {
                    InvestmentPlanVo aseNew=new  InvestmentPlanVo();
                    if ("新增".equals(ase.getIppReleaseType()) || "增加".equals(ase.getIppReleaseType())) {
                        BigDecimal reduceMoney = BigDecimal.ZERO;
                        for (InvProjectPlan r : InvProjectPlans) {
                            if (ValidateUtil.isNotEmpty(ase.getIppDocNumber())&&ValidateUtil.isNotEmpty(r.getIppPlanDocNo())&&r.getIppPlanDocNo().equals(ase.getIppDocNumber()) && "减少".equals(r.getIppReleaseType())) {
                                reduceMoney = reduceMoney.add(r.getIppReleaseFunds());
                            }
                        }
                        //计算实际支付的金额
                        BigDecimal CpafApplys = BigDecimal.ZERO;
                        for (ConPayApplyFlow paf : ConPayApplyFlowList) {
                            String newCpafApplyDetails = paf.getCpafApplyDetails();
                            if (ValidateUtil.isNotEmpty(newCpafApplyDetails)) {
                                List<Map<String, Object>> jsonCpafApplys = JacksonUtils.parseObject(newCpafApplyDetails, new TypeReference<List<Map<String, Object>>>() {
                                });
                                for (Map<String, Object> tse : jsonCpafApplys) {
                                    String  code="";
                                    if(tse.get("code")!=null){
                                        code=tse.get("code").toString();
                                    }
                                    if (ValidateUtil.isNotEmpty(ase.getIppDocNumber())&& ase.getIppDocNumber().equals(code)) {
                                        if(tse.get("cpafApplication")!=null){
                                            BigDecimal cpafApplication = new BigDecimal(tse.get("cpafApplication").toString());
                                            CpafApplys = CpafApplys.add(cpafApplication);
                                            cpafApplyDetailsCumulative = cpafApplyDetailsCumulative.add(cpafApplication);
                                        }

                                    }
                                }
                            }
                        }
                        BigDecimal ippReleaseFunds =BigDecimal.ZERO;
                        if(ase.getIppReleaseFunds()!=null&&ase.getIppReleaseFunds()!=BigDecimal.ZERO&&reduceMoney!=BigDecimal.ZERO){
                            ippReleaseFunds = ase.getIppReleaseFunds().divide(reduceMoney);
                        }
                        //累计直接计划下达金额
                        cumulativeAmount = cumulativeAmount.add(ippReleaseFunds);
                        //当年累计资金计划下达金额
                        yearCumulativeAmount = yearCumulativeAmount.add(ippReleaseFunds);
                        if(ippReleaseFunds!=BigDecimal.ZERO&&CpafApplys!=BigDecimal.ZERO) {
                            aseNew.setRemainingAmount(ippReleaseFunds.divide(CpafApplys));
                        }else{
                            aseNew.setRemainingAmount(BigDecimal.ZERO);
                        }
                        aseNew.setPaidMoney(CpafApplys);
                    }else{
                        aseNew.setRemainingAmount(BigDecimal.ZERO);
                    }
                    remainingAmount=remainingAmount.add(aseNew.getRemainingAmount());
                    aseNew.setIppDocNumber(ase.getIppDocNumber());
                    aseNew.setIppReleaseType(ase.getIppReleaseType());
                    aseNew.setIppPlanDocNo(ase.getIppPlanDocNo());
                    aseNew.setIppReleaseFunds(ase.getIppReleaseFunds());
                    aseNew.setIppRemarks(ase.getIppRemarks());
                    aseNew.setIppSourceFunds(ase.getIppSourceFunds());
//                    aseNew.setCumulativeAmount();
                    List<SysDictionary>  ParentList=dicList.stream().filter(l->l.getdCode()!=null&&l.getdCode().equals(ase.getIppSourceFunds())).collect(Collectors.toList());
                    if(ParentList.size()>0){
                        if("0".equals(ParentList.get(0).getdParentid())){
                            aseNew.setTitle(ParentList.get(0).getdName());
                        }else{
                            aseNew.setTitle(sysDictionaryService.getTitle(ParentList.get(0).getdName(),ParentList.get(0).getdParentid(),dicList));
                        }
                    }
                    ippList.add(aseNew);
                }

                for(int i=0;i<ippList.size();i++){
                    InvestmentPlanVo newObj=ippList.get(i);
                    newObj.setCumulativeAmount(ippList.stream().filter(l->l.getIppSourceFunds().equals(newObj.getIppSourceFunds())).map(l->l.getRemainingAmount()).reduce(BigDecimal.ZERO,BigDecimal::add));
                    ippList.set(i,newObj);
                }
                obj.setRemainingAmount(remainingAmount);
                obj.setYearCumulativeAmount(yearCumulativeAmount);
                obj.setCpafApplyDetailsCumulative(cpafApplyDetailsCumulative);
                fundPlanModify.setFpmReleaseAmountTotal(cumulativeAmount);
                obj.setFormList(fundPlanModify);
                obj.setInvestmentPlans(ippList);
            }
            data.add(obj);
        }
        return BaseResponse.ok(data, 0);
    }



    @Override
    public BaseResponse updateFundPlanModify(FundPlanModifyDto request) {
        FundPlanModify obj = new FundPlanModify();
        BeanUtils.copyProperties(request, obj);
        baseMapper.updateById(obj);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getFundPlanModifyByPidAndYear(String pId, Integer year, String newDate) {
        List<String> pIds = new ArrayList<>();
        pIds.add(pId);
        List<Integer> years = new ArrayList<>();
        years.add(year);
        Map<String, Object> params = new HashMap<>();
        params.put("pIds", pIds);
        params.put("fpmYears", years);
        List<InvProjectPlan> invProjectPlanList = invProjectPlanService.getInvProjectPlanListByPids(pIds);
        QueryWrapper<ConPay> conPayPojoWrapper = new QueryWrapper<>();
        conPayPojoWrapper.eq("P_Id", pId);
        conPayPojoWrapper.ne("Pay_State", "0");
        conPayPojoWrapper.ne("Pay_State", "3");
        conPayPojoWrapper.le("Pay_State_Time", newDate);
        List<ConPay> conPayList = conPayService.list(conPayPojoWrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("invProjectPlanList", invProjectPlanList);
        data.put("conPayPojoList", conPayList);
        return BaseResponse.ok(data, 1);
    }

}
