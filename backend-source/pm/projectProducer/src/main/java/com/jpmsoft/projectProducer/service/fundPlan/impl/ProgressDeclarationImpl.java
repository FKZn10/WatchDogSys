package com.jpmsoft.projectProducer.service.fundPlan.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvFinalAccountsApplyResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.audit.AuditSettlementMapper;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundAllocationDtlMapper;
import com.jpmsoft.projectProducer.mapper.fundPlan.ProgressDeclarationMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvFinalAccountsApplyMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.*;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditSettlement;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclaration;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.BalancePayment;
import com.jpmsoft.projectProducer.pojo.entity.investment.PreliminaryAudit;
import com.jpmsoft.projectProducer.service.audit.IAuditSettlementService;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.fundPlan.IProgressDeclarationDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IProgressDeclarationService;
import com.jpmsoft.projectProducer.service.investment.BalancePaymentService;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyService;
import com.jpmsoft.projectProducer.service.investment.PreliminaryAuditService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:01
 */
@Service
public class ProgressDeclarationImpl extends FileImpl<ProgressDeclarationMapper,ProgressDeclaration, ProgressDeclarationDTO>
        implements IProgressDeclarationService {

    @Resource
    private IProgressDeclarationDtlService progressDeclarationDtlService;

    @Resource
    private IConContractPayPlanService iConContractPayPlanService;

    @Resource
    private IConContractService iConContractService;

    @Resource
    private FundAllocationDtlMapper fundAllocationDtlMapper;

    @Resource
    private IProgressDeclarationDtlService iProgressDeclarationDtlService;

    @Resource
    private InvFinalAccountsApplyMapper invFinalAccountsApplyMapper;

    @Resource
    private IConPayService iConPayService;

    @Resource
    private PreliminaryAuditService preliminaryAuditService;

    @Resource
    private AuditSettlementMapper auditSettlementMapper;

    @Resource
    private BalancePaymentService balancePaymentService;

    @Resource
    private IAuditSettlementService iAuditSettlementService;

    @Resource
    private IInvFinalAccountsApplyService iInvFinalAccountsApplyService;

    @Resource
    private ISysFileService iSysFileService;






    @Override
    public BaseResponse createOrUpdateProgressDeclaration(ProgressDeclarationDTO request) {
        ProgressDeclaration entity = new ProgressDeclaration();
        createOrUpdateFileEntity(entity, request);
        progressDeclarationDtlService.createOrUpdate(request.getProgressDeclarationDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProgressDeclarationById(String id) {
        ProgressDeclarationDTO data = baseMapper.getProgressDeclarationById(id);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        data.setProgressDeclarationDtls(progressDeclarationDtlService.selectByPdId(id));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProgressDeclarationList(ProgressDeclarationDTO request) {
        IPage<ProgressDeclarationDTO> iPage = new Page<>(request.getPage(), request.getSize());
        List<ProgressDeclarationDTO> data = baseMapper.getProgressDeclarationList(iPage, request);
        Long total = iPage.getTotal();
        return BaseResponse.ok(data, total.intValue());
    }

    @Override
    public BaseResponse getPlanNodes(String conId) {
        ProgressDeclarationDtl data = baseMapper.getPlanNodes(conId);
        List<ConContractPayPlanModel> listData = iConContractPayPlanService.getConContractPayPlanByConId(conId);

        Map<String, Object> map = new HashMap<>();
        if(ValidateUtil.isNotNull(data)){
            map.put("pddThisNode", ValidateUtil.isNotNull(data.getPddThisNode()) ? data.getPddThisNode() : "");
        }else{
            map.put("pddThisNode","");
        }

        map.put("data", listData);

        return BaseResponse.ok(map);
    }

    //进度申报 匹配节点
    @Override
    public BaseResponse progressDeclarationMatch(ProgressDeclarationMatchDTO dto) {
        //   合同预付款   合同竣工  	竣工验收
        Map<String, Object> mapData = new HashMap<>();
        BigDecimal contractMoney = iConContractService.getContractMoney(dto.getConId());  //合同价
        List<ConContractPayPlan> conPaymentPlan = iConContractPayPlanService.getConPaymentPlan(dto.getConId());  //支付计划
        List<ProgressDeclarationDtl> dtlList =  baseMapper.getPlanNodeLists(dto.getConId());
        BigDecimal leijizhifu  =  dtlList.stream().filter(e-> ValidateUtil.isNotNull(e.getPddMoney())).map(e->e.getPddMoney()).reduce(BigDecimal.ZERO,BigDecimal::add);
        List<FundAllocationDtlDTO> fundAllocationDtlList = fundAllocationDtlMapper.getFundAllocationDtlList(dto.getConId());
        if (dto.getPddPayType().equals("JPMsoft_ZFJHTJLX_HTYFK") || dto.getPddPayType().equals("JPMsoft_ZFJHTJLX_HTJG") || dto.getPddPayType().equals("JPMsoft_ZFJHTJLX_JGYS")) {
            if (ValidateUtil.isAllEmpty(dto.getConId(), dto.getPddProgressNodeId()))
                return BaseResponse.fail("缺少必填参数");

            ConContractPayPlan conp = new ConContractPayPlan();
            if (!CollectionUtils.isEmpty(conPaymentPlan)) {
                conp = conPaymentPlan.stream().filter(e -> e.getCppId().equals(dto.getPddProgressNodeId())).collect(Collectors.toList()).get(0);
            }
            // 合同预付款
            if (ValidateUtil.isNotNull(conp)) {
                if (conp.getCppMode().equals("按比例") || conp.getCppMode().equals("01")) {
                    //支付基础是合同价
                    if (conp.getCppBasics().equals("JPMsoft_ZFJC_HTJ")) {
                        //转换百分比
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(contractMoney, decimalPercentage).subtract(leijizhifu));

                    } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_MCJLZ")) { //每次计量值
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppConditionValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPaymentTo(), decimalPercentage).subtract(leijizhifu));
                    } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_LJJLZ")) { //累计计量值
                       BigDecimal b =  dtlList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getPddPayType()) && e.getPddPayType().equals("JPMsoft_ZFJHTJLX_GCJD") && ValidateUtil.isNotEmpty(e.getPddScheduleType()) &&  e.getPddScheduleType().equals("03")).map(e-> e.getPddStoichiometric()).reduce(BigDecimal.ZERO,BigDecimal::add);
                        mapData.put("paymentAmt", b.subtract(leijizhifu));
                    }
                    mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));  //本次可以申请
                    mapData.put("appliedFor",leijizhifu);
                    mapData.put("thisApplicationAccumulated",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).add(leijizhifu));  //累计申请（含本次）
                    mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付

                } else {   //按金额
                        if (conp.getCppMoney().compareTo(contractMoney) >= 0) {
                            mapData.put("paymentAmt", contractMoney);
                        } else {
                            mapData.put("paymentAmt", conp.getCppMoney());
                        }
                        mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                        mapData.put("appliedFor",leijizhifu);
                        mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                        mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                }
            } else {
                mapData.put("paymentAmt", 0);
            }
            mapData.put("sort", conp);  //第几个计划
        } else if (dto.getPddPayType().equals("JPMsoft_ZFJHTJLX_GCJD")) {  //合同进度
            if (dto.getConProgressType().equals("按百分比") || dto.getConProgressType().equals("01")) {
                if (!CollectionUtils.isEmpty(conPaymentPlan)) {
                    Optional<ConContractPayPlan> result = conPaymentPlan.stream()
                            .filter(e -> {
                                try {
                                    e.setCppZftjLx(ValidateUtil.isNotNull(e.getCppZftjLx()) ? e.getCppZftjLx() : "0");
                                    return new BigDecimal(e.getCppZftjLx()).compareTo(dto.getPddProgress()) <= 0;
                                } catch (NumberFormatException ex) {
                                    return false; // 如果转换失败，忽略该条记录
                                }
                            })
                            .max((e1, e2) -> {
                                try {
                                    return new BigDecimal(e1.getCppZftjLx()).compareTo(new BigDecimal(e2.getCppZftjLx()));
                                } catch (NumberFormatException ex) {
                                    return 0; // 如果转换失败，视为相等
                                }
                            });
                    // 处理结果
                    ConContractPayPlan selectedPlan = result.orElse(null);
                    if(ValidateUtil.isNotNull(selectedPlan)){
                        if (selectedPlan.getCppMode().equals("按比例") || selectedPlan.getCppMode().equals("01")) {
                            BigDecimal decimalPercentage = new BigDecimal(selectedPlan.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                            mapData.put("paymentAmt", MathUtil.decMultiply(contractMoney, decimalPercentage).subtract(leijizhifu));
                            mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));  //本次可以申请
                            mapData.put("appliedFor",leijizhifu);
                            mapData.put("thisApplicationAccumulated",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).add(leijizhifu));  //累计申请（含本次）
                            mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                        }else{
                            if(selectedPlan.getCppSort() > 1){   //合同进度按金额会存在跳过预付款的情况，所以判断节点是大于1就是跳过的，就是前面的预付款节点也拿来做数据
                                List<ConContractPayPlan> result1 = findPlansByCppSort(conPaymentPlan, selectedPlan.getCppSort());
                                BigDecimal reduce = result1.stream().filter(e -> ValidateUtil.isNotNull(e.getCppMoney())).map(e -> e.getCppMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                if (selectedPlan.getCppMoney().compareTo(contractMoney) >= 0) {
                                    mapData.put("paymentAmt", contractMoney);
                                }else {
                                    mapData.put("paymentAmt", reduce);
                                }
                                mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).subtract(leijizhifu));  //本次可以申请
                                mapData.put("appliedFor",leijizhifu);
                                mapData.put("thisApplicationAccumulated",mapData.get("paymentAmt"));  //累计应付（含本次）
                                mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                            }else{
                                if (selectedPlan.getCppMoney().compareTo(contractMoney) >= 0) {
                                    mapData.put("paymentAmt", contractMoney);
                                } else {
                                    mapData.put("paymentAmt", selectedPlan.getCppMoney());
                                }
                                mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                                mapData.put("appliedFor",leijizhifu);
                                mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                                mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                            }
                        }

                    }
                    mapData.put("sort", selectedPlan);  //第几个计划
                }
            } else if (dto.getConProgressType().equals("按进度节点") || dto.getConProgressType().equals("02")) {
                List<ConContractPayPlan> collect = conPaymentPlan.stream().filter(e -> e.getCppId().equals(dto.getPddProgressNodeId())).collect(Collectors.toList());
                ConContractPayPlan  plan = new ConContractPayPlan();
                if(!CollectionUtils.isEmpty(collect)){
                    plan = collect.get(0);
                }
                if(ValidateUtil.isNotNull(plan)){
                    if (plan.getCppMode().equals("按比例") || plan.getCppMode().equals("01")) {
                        BigDecimal decimalPercentage = new BigDecimal(plan.getCppConditionValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(contractMoney, decimalPercentage).subtract(leijizhifu));
                        mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));  //本次可以申请
                        mapData.put("appliedFor",leijizhifu);
                        mapData.put("thisApplicationAccumulated",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).add(leijizhifu));  //累计申请（含本次）
                        mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                    }else{
                        if(plan.getCppSort() > 1){
                            List<ConContractPayPlan> result1 = findPlansByCppSort(conPaymentPlan, plan.getCppSort() );
                            BigDecimal reduce = result1.stream().filter(e -> ValidateUtil.isNotNull(e.getCppMoney())).map(e -> e.getCppMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                            if (plan.getCppMoney().compareTo(contractMoney) >= 0) {
                                mapData.put("paymentAmt", contractMoney);
                            }else {
                                mapData.put("paymentAmt", reduce);
                            }
                            mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).subtract(leijizhifu));  //本次可以申请
                            mapData.put("appliedFor",leijizhifu);
                            mapData.put("thisApplicationAccumulated",mapData.get("paymentAmt"));  //累计应付（含本次）
                            mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                        }else{
                            if (plan.getCppMoney().compareTo(contractMoney) >= 0) {
                                mapData.put("paymentAmt", contractMoney);
                            } else {
                                mapData.put("paymentAmt", plan.getCppMoney());
                            }
                            mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                            mapData.put("appliedFor",leijizhifu);
                            mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                            mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                        }
                    }
                    mapData.put("sort", plan);  //第几个计划
                }else{
                    mapData.put("paymentAmt",0);
                }

            } else if (dto.getConProgressType().equals("按计量") || dto.getConProgressType().equals("03")) {
                List<ConContractPayPlan> collect = conPaymentPlan.stream().filter(e-> ValidateUtil.isNotNull(e.getCppConJdlx()) && e.getCppConJdlx().equals("03")).collect(Collectors.toList());
                ConContractPayPlan plan = new ConContractPayPlan();
                if(!CollectionUtils.isEmpty(collect)){
                    plan = collect.get(0);
                }
                if(ValidateUtil.isNotNull(plan)){
                    BigDecimal decimalPercentage = new BigDecimal(plan.getCppConditionValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPddStoichiometric(), decimalPercentage));
                    mapData.put("sort", plan);  //第几个计划
                }else{
                    mapData.put("paymentAmt",0);
                }
                mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                mapData.put("appliedFor",leijizhifu);
                mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
            }
        }
        return BaseResponse.ok(mapData);
    }

    @Override
    public BaseResponse getContractDeclarationList(ProgressDeclarationMatchDTO dto) {

        Page<ProgressContractPlanDTO> planList  =  baseMapper.getContractDeclarationList(new Page<>(dto.getPage(),dto.getSize()),dto);
        List<String> ids = planList.getRecords().stream().map(e -> e.getConId()).collect(Collectors.toList());
        List<ProgressContractPlanDTO>  dataList =  iProgressDeclarationDtlService.selectAllByContractList(ids);
        List<ProgressDeclarationDtl>  dataAmtList  = iProgressDeclarationDtlService.pddMoneyMountyAmt(ids);
        List<ConContractPayPlan> conPaymentPlan = iConContractPayPlanService.getConPaymentPlanList(ids);
        for (ProgressContractPlanDTO record : planList.getRecords()) {
            List<ProgressContractPlanDTO> collect = dataList.stream().filter(e -> e.getConId().equals(record.getConId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(conPaymentPlan)){
                List<ConContractPayPlan> payPlan = conPaymentPlan.stream().filter(e ->ValidateUtil.isNotNull(e.getCppConditionType()) &&  e.getCppConditionType().equals("JPMsoft_ZFJHTJLX_GCJD")).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(payPlan)){
                    record.setPddPayType(payPlan.get(0).getCppConJdlx());
                }
            }
            if(!CollectionUtils.isEmpty(collect)){

                record.setPddContantDate(collect.get(0).getPddContantDate());
                record.setPddContant(collect.get(0).getPddContant());
            }
            if(!CollectionUtils.isEmpty(dataAmtList)){
                BigDecimal reduce = dataAmtList.stream().filter(e -> e.getConId().equals(record.getConId())).filter(e-> ValidateUtil.isNotNull(e.getPddMoney())).map(e -> e.getPddMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                record.setPddMoneySum(reduce);
            }
            BigDecimal contractMoney = iConContractService.getContractMoney(record.getConId());
            record.setConMoneyTotal(contractMoney);
        }
        return BaseResponse.ok(planList.getRecords(),(int)planList.getTotal());
    }

    @Override
    public BaseResponse progerGeneratePayment(ProgressDeclarationMatchDTO dto) {
        Map<String, Object> mapData = new HashMap<>();
        //走进度申报
        if(dto.getPddPayType().equals("fund")){
            if(ValidateUtil.isEmpty(dto.getId()))return  BaseResponse.fail("缺少必填参数");
            List<ProgressDeclarationDtlDTO> declaList = iProgressDeclarationDtlService.getiProgressDeclarationId(dto.getId());  //进度申报
            List<String> conIds = declaList.stream().map(e -> e.getConId()).collect(Collectors.toList());
            Map<String,Object> mapPlan = new HashMap<>();
            List<ConPay> conPayList  = new ArrayList<>();
            for (ProgressDeclarationDtlDTO pro : declaList) {
                ConPay conPay = new ConPay();
                conPay.setPayId(GenerateUUID.getUUID());
                conPay.setPayCreateUname(pro.getCreateUname());
                conPay.setPayCreateUid(pro.getCreateUid());
                conPay.setPayCreateTime(new Date());
                conPay.setPayState(7);
                conPay.setPayStateTime(new Date());
                conPay.setPayApply(pro.getPddMoney());
                conPay.setPId(pro.getPId());
                conPay.setConId(pro.getConId());
                conPay.setPayApplicationType("05");
                conPay.setPayRemark("进度申报，系统自动生成资金申请" + pro.getPddRemark());
                ConContractPayPlan payPlan =  iConContractPayPlanService.getConPaymentPlanConIdNode(pro.getConId(),pro.getPddThisNode());
                if(ValidateUtil.isNotNull(payPlan)){
                    conPay.setCppId(payPlan.getCppId());
                }
                conPayList.add(conPay);
                insertOrUpdate(mapPlan, pro.getConId(), pro.getPddThisNode());
            }
            if(!CollectionUtils.isEmpty(conPayList)){
                iConPayService.saveBatch(conPayList);
                for (Map.Entry<String, Object> entry : mapPlan.entrySet()) {
                    String key = entry.getKey(); // 合同ID
                    Integer value = (Integer) entry.getValue(); // 节点ID
                    for (int i = 1; i <= value; i++) {
                        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ConContractPayPlan::getConId,key);
                        queryWrapper.eq(ConContractPayPlan::getCppSort,i);
                        ConContractPayPlan plan = new ConContractPayPlan();
                        plan.setCppShouldApply("1");
                        iConContractPayPlanService.update(plan,queryWrapper);
                    }
                }
            }
        }else{  //走四个功能
            if(ValidateUtil.isAllEmpty(dto.getId(),dto.getConId()))return  BaseResponse.fail("缺少必填参数");
            List<ProgressDeclarationDtl> dtlList =  baseMapper.getPlanNodeLists(dto.getConId());
            List<ConContractPayPlan> conPaymentPlan = iConContractPayPlanService.getConPaymentPlan(dto.getConId());  //支付计划
            if(CollectionUtils.isEmpty(conPaymentPlan)) return BaseResponse.fail("该合同没有创建计划");
            BigDecimal contractMoney = iConContractService.getContractMoney(dto.getConId());  //合同价
            List<FundAllocationDtlDTO> fundAllocationDtlList = fundAllocationDtlMapper.getFundAllocationDtlList(dto.getConId());
            List<ConPayResponse> payList = iConPayService.getPayList(Arrays.asList(dto.getConId()));
            BigDecimal leijizhifu = payList.stream().map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);
            ConContractPayPlan conp = new ConContractPayPlan();  //获取最后一条支付计划
            String isSave  = "";  //是否保存
            String type;  //支付条件类型
            String msg ;
            if(dto.getPddPayType().equals("01")){
                type = "JPMsoft_ZFJHTJLX_JGJS";
                msg = "竣工决算";
            }else if(dto.getPddPayType().equals("02")){
                type = "JPMsoft_ZFJHTJLX_CBSJ";
                msg = "初步审计";
            }else if(dto.getPddPayType().equals("03")){
                type = "JPMsoft_ZFJHTJLX_JSSJ";
                msg = "结算审计";
            }else if(dto.getPddPayType().equals("04")){
                type = "JPMsoft_ZFJHTJLX_WK_RQ";
                msg = "尾款";
            } else {
                type = "";
                msg = "";
            }
            if(!CollectionUtils.isEmpty(conPaymentPlan)){
                if(dto.getPddPayType().equals("04")){
                    conp = conPaymentPlan.stream().filter(e-> e.getCppId().equals(dto.getCppId())).collect(Collectors.toList()).get(0);
                }else{
                    Optional<ConContractPayPlan> maxPlan = conPaymentPlan.stream().filter(e-> type.equals(e.getCppConditionType()))
                            .max((p1, p2) -> Integer.compare(p1.getCppSort(), p2.getCppSort()));
                    conp = maxPlan.orElse(null);
                }

            }
            //生成资金申请
            ConPay conPay = new ConPay();
            if (ValidateUtil.isNotNull(conp)) {
                if (conp.getCppMode().equals("按比例") || conp.getCppMode().equals("01")) {
                    BigDecimal paymentAmt = BigDecimal.ZERO;
                    if(dto.getPddPayType().equals("01")){//竣工决算
                        InvFinalAccountsApplyResponse inv = invFinalAccountsApplyMapper.getInvFinalAccountsApplyByfaId(dto.getId());
                        paymentAmt = ValidateUtil.isNotNull(inv.getFaApprovedAmt()) ? inv.getFaApprovedAmt() : BigDecimal.ZERO;
                        if(inv.getFaState().equals(7)){
                            isSave = "1";
                            conPay.setPId(inv.getpId());
                            conPay.setConId(inv.getConId());
                            conPay.setPayCreateTime(new Date());
                            conPay.setPayCreateUid(inv.getFaCreateUid());
                            conPay.setPayCreateUname(inv.getFaCreateUname());
                            conPay.setPayApplicationType("01");
                            conPay.setPayRemark("竣工决算，系统自动生成资金申请" + inv.getFaRemark());
                        }
                    }else if (dto.getPddPayType().equals("02")){//初步审计
                        PreliminaryAudit audit =  preliminaryAuditService.getpreliminaryById(dto.getId());
                        paymentAmt = ValidateUtil.isNotNull(audit.getPaExamineApprove()) ? audit.getPaExamineApprove() : BigDecimal.ZERO;
                        if(audit.getState().equals(7)){
                            isSave = "1";
                            conPay.setPId(audit.getPId());
                            conPay.setConId(audit.getConId());
                            conPay.setPayCreateTime(new Date());
                            conPay.setPayCreateUid(audit.getCreateUid());
                            conPay.setPayCreateUname(audit.getCreateUname());
                            conPay.setPayApplicationType("02");
                            conPay.setPayRemark("初步审计，系统自动生成资金申请" + audit.getPaRemarks());
                        }

                    }else if (dto.getPddPayType().equals("03")){//结算审计
                        AuditSettlementModel settlement = auditSettlementMapper.getAuditSettlementById(dto.getId());
                        paymentAmt = ValidateUtil.isNotNull(settlement.getAsAuditedAmount()) ? settlement.getAsAuditedAmount() : BigDecimal.ZERO;
                        if(settlement.getAsState().equals(7)){
                            isSave = "1";
                            conPay.setPId(settlement.getpId());
                            conPay.setConId(settlement.getConId());
                            conPay.setPayCreateTime(new Date());
                            conPay.setPayCreateUid(settlement.getAsCreateUid());
                            conPay.setPayCreateUname(settlement.getAsCreateUname());
                            conPay.setPayApplicationType("03");
                            conPay.setPayRemark("结算审计，系统自动生成资金申请" + settlement.getAsRemark());
                        }

                    }else if (dto.getPddPayType().equals("04")){//尾款
                        BalancePayment balancePayment =  balancePaymentService.getBalancePaymentById(dto.getId());
                        paymentAmt = ValidateUtil.isNotNull(balancePayment.getBpActualApplication()) ? balancePayment.getBpActualApplication() : BigDecimal.ZERO;
                        if(balancePayment.getState().equals(7)){
                            isSave = "1";
                            conPay.setPId(balancePayment.getPId());
                            conPay.setConId(balancePayment.getConId());
                            conPay.setPayCreateTime(new Date());
                            conPay.setPayCreateUid(balancePayment.getCreateUid());
                            conPay.setPayCreateUname(balancePayment.getCreateUname());
                            conPay.setPayApplicationType("04");
                            conPay.setPayRemark("尾款申请，系统自动生成资金申请" + balancePayment.getBpRemark());
                        }
                    }
                    //支付基础
                    if (conp.getCppBasics().equals("JPMsoft_ZFJC_HTJ")) {//合同价
                        //转换百分比
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(contractMoney, decimalPercentage).subtract(leijizhifu));
                    } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_MCJLZ")) { //每次计量值
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppConditionValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPaymentTo(), decimalPercentage).subtract(leijizhifu));
                    } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_LJJLZ")) { //累计计量值
                        BigDecimal b =  dtlList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getPddPayType()) && e.getPddPayType().equals("JPMsoft_ZFJHTJLX_GCJD") && ValidateUtil.isNotEmpty(e.getPddScheduleType()) &&  e.getPddScheduleType().equals("03")).map(e-> e.getPddStoichiometric()).reduce(BigDecimal.ZERO,BigDecimal::add);
                        mapData.put("paymentAmt", b.subtract(leijizhifu));

//                        BigDecimal leiji = conPaymentPlan.stream().filter(e -> e.getCppBasics().equals("JPMsoft_ZFJC_LJJLZ") && ValidateUtil.isNotNull(e.getCppConditionValue())).map(e -> new BigDecimal(e.getCppConditionValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
//                        mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPaymentTo(), leiji).subtract(leijizhifu));
                    }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_JGJSJ")) { //竣工决算价
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                    }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_CBSDJ")) { //初步审定价
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                    }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_SJJ")) { //结算审计价
                        BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                    }
                    mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));  //本次可以申请
                    mapData.put("appliedFor",leijizhifu);
                    mapData.put("thisApplicationAccumulated",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).add(leijizhifu));  //累计申请（含本次）
                    mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                }else{
                    if (conp.getCppMoney().compareTo(contractMoney) >= 0) {
                        mapData.put("paymentAmt", contractMoney);
                    } else {
                        mapData.put("paymentAmt", conp.getCppMoney());
                    }
                    mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                    mapData.put("appliedFor",leijizhifu);
                    mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                    mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
                }


                mapData.put("sort", conp);  //第几个计划
                //保存到资金申请
                if(new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).compareTo(BigDecimal.ZERO) < 0){
                    return BaseResponse.fail("该合同" + msg +"支付节点已申报或申报金额为零，无法创建");
                }
                if(isSave.equals("1")){
                    conPay.setPayId(GenerateUUID.getUUID());
                    conPay.setPayApply(new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));
                    conPay.setPayState(7);
                    conPay.setPayStateTime(new Date());
                    conPay.setCppId(conp.getCppId());
                    if(new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).compareTo(BigDecimal.ZERO) > 0 ){
                        iConPayService.save(conPay);

                        ConContractPayPlan conplan = new ConContractPayPlan();
                        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ConContractPayPlan::getCppId,conp.getCppId());
                        conplan.setCppShouldApply("1");  //是否申请 1申请  0不申请
                        iConContractPayPlanService.update(conplan,queryWrapper);
                    }
                }
            } else {
                return BaseResponse.fail("该合同无" + msg +"节点计划");
            }
        }
//        sysFileService.saveFile(conPay.getPayId(), dto.gettId(), conPay.getPayState() + "", "", conPay.getPayCreateUname(), conPay.getPayCreateTime(), null, dto.getFileTitle(), dto.getFileCode(), "create", dto.getFileRelationId(), null, dto.gettCode(), null);
//        sysFileAnnexService.addFormRefreshRelationFile(dto.getFileRelationId(), conPay.getPayId());


        return BaseResponse.ok(mapData);
    }

    @Override
    public BaseResponse updateContrCompletionData(ProgressDeclarationMatchDTO dto) {
        if(dto.getPddPayType().equals("1")){  //竣工验收
            if(ValidateUtil.isEmpty(dto.getId()))return BaseResponse.fail("缺少必填参数");
            List<ProgressDeclarationDtl> ipdata = iProgressDeclarationDtlService.selectProgerssData(dto.getId());
            List<ConContract> list = new ArrayList<>();
            for (ProgressDeclarationDtl ipdatum : ipdata) {
                ConContract con  = new ConContract();
                con.setConId(ipdatum.getConId());
                con.setConEndDate(ipdatum.getPddJgDate());
                list.add(con);
            }
            if(!CollectionUtils.isEmpty(list)){

                iConContractService.updateBatchById(list);
            }
        }else{
            AuditSettlement auditSettlement =  iAuditSettlementService.selectSettlementById(dto.getId());
            if(ValidateUtil.isNotNull(auditSettlement)){
                LambdaQueryWrapper<ConContract> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ConContract::getConId,auditSettlement.getConId());
                ConContract con = new ConContract();
                con.setConAuditDate(auditSettlement.getAsDateOfApproval());
                iConContractService.update(con,queryWrapper);
            }
        }

        return BaseResponse.ok();
    }

    //尾款申请
    @Override
    public BaseResponse delBalancePaymentById(String id,String conId) {
        List<ConPay> list = iConPayService.getConAndPayApplicationType(conId,"05");
        if(!CollectionUtils.isEmpty(list)){
            return BaseResponse.fail("已存在资金申请不能删除节点");
        }else{
        balancePaymentService.removeById(id);
        iSysFileService.removeById(id);
        baseMapper.deleteSysTaskEntityId(id);
        baseMapper.deleteSysTaskProcessinEntityId(id);
        return BaseResponse.ok();
        }
    }

    //竣工决算
    @Override
    public BaseResponse delInvFinalAccountsApplyById(String id,String conId) {

        List<ConPay> list = iConPayService.getConAndPayApplicationType(conId,"01");
        if(!CollectionUtils.isEmpty(list)){
            return BaseResponse.fail("已存在资金申请不能删除节点");
        }else{
            iInvFinalAccountsApplyService.removeById(id);
            iSysFileService.removeById(id);
            baseMapper.deleteSysTaskEntityId(id);
            baseMapper.deleteSysTaskProcessinEntityId(id);
            return BaseResponse.ok();
        }
    }
    //初步审计
    @Override
    public BaseResponse delPreliminaryAuditById(String id,String conId) {
        List<ConPay> list = iConPayService.getConAndPayApplicationType(conId,"02");
        if(!CollectionUtils.isEmpty(list)){
            return BaseResponse.fail("已存在资金申请不能删除节点");
        }else{
        preliminaryAuditService.removeById(id);
        iSysFileService.removeById(id);
        baseMapper.deleteSysTaskEntityId(id);
        baseMapper.deleteSysTaskProcessinEntityId(id);
        return BaseResponse.ok();
        }
    }

    //结算审计
    @Override
    public BaseResponse delAuditSettlementById(String id,String conId) {
        List<ConPay> list = iConPayService.getConAndPayApplicationType(conId,"03");
        if(!CollectionUtils.isEmpty(list)){
            return BaseResponse.fail("已存在资金申请不能删除节点");
        }else{
        iAuditSettlementService.removeById(id);
        iSysFileService.removeById(id);
        baseMapper.deleteSysTaskEntityId(id);
        baseMapper.deleteSysTaskProcessinEntityId(id);
        return BaseResponse.ok();
        }
    }

    @Override
    public BaseResponse getAccordingThePlan(ProgressDeclarationMatchDTO dto) {
        Map<String, Object> mapData = new HashMap<>();
        BigDecimal contractMoney = iConContractService.getContractMoney(dto.getConId());  //合同价
        List<ProgressDeclarationDtl> dtlList =  baseMapper.getPlanNodeLists(dto.getConId());
        List<ConContractPayPlan> conPaymentPlan = iConContractPayPlanService.getConPaymentPlan(dto.getConId());  //支付计划
        if(CollectionUtils.isEmpty(conPaymentPlan)) return BaseResponse.fail("该合同没有创建计划");
        List<ConPayResponse> payList = iConPayService.getPayList(Arrays.asList(dto.getConId()));
        List<FundAllocationDtlDTO> fundAllocationDtlList = fundAllocationDtlMapper.getFundAllocationDtlList(dto.getConId());
        BigDecimal leijizhifu = payList.stream().map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);
        ConContractPayPlan conp = new ConContractPayPlan();
        if (!CollectionUtils.isEmpty(conPaymentPlan)) {
            conp = conPaymentPlan.stream().filter(e -> e.getCppId().equals(dto.getPddProgressNodeId())).collect(Collectors.toList()).get(0);
        }
        if (ValidateUtil.isNotNull(conp)) {
            if(ValidateUtil.isNotNull(conp.getCppConditionType()) && conp.getCppConditionType().equals("JPMsoft_ZFJHTJLX_WK_RQ")){ //尾款
                ConContract con = iConContractService.getById(dto.getConId());
                // 根据 cppZftjUnit 动态调整日期
                if(ValidateUtil.isNull(con.getConAuditDate())){
                    return BaseResponse.fail("该合同不满足尾款申请条件，无法发起");
                }
                Date dateAudit = adjustDate(con.getConAuditDate(), conp.getCppZftjNumber(), conp.getCppZftjUnit());
                Date date = new Date();
                String str = "";
                if(conp.getCppZftjLx().equals("settlement")){
                    str = "结算审计";
                }else if (conp.getCppZftjLx().equals("completed")){
                    str = "工程竣工";
                }else{
                    str = conp.getCppZftjLx();
                }
                if(!date.before(dateAudit) || !date.equals(dateAudit)){
                    return BaseResponse.fail("当前节点支付条款为:" + str + "(" + DateUtil.format(con.getConAuditDate(), DatePattern.NORM_DATE_PATTERN) +  ")"
                            +"完成" + conp.getCppZftjNumber() + conp.getCppZftjUnit() + "后可以支付； 应付款日期为；" + DateUtil.format(dateAudit, DatePattern.NORM_DATE_PATTERN)
                            +"以后；当前未达到该尾款节点支付条件，不允许申请当前节点费用!!"
                    );
                }
            }
            if (conp.getCppMode().equals("按比例") || conp.getCppMode().equals("01")) {
                //支付基础
                if (conp.getCppBasics().equals("JPMsoft_ZFJC_HTJ")) {//合同价
                    //转换百分比
                    BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(contractMoney, decimalPercentage).subtract(leijizhifu));
                } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_MCJLZ")) { //每次计量值
                    if(dto.getPaymentTo().compareTo(BigDecimal.ZERO) <=0){
                        return BaseResponse.fail("每次计量值金额为0，该节点不允许支付");
                    }
                    BigDecimal decimalPercentage = new BigDecimal(conp.getCppConditionValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPaymentTo(), decimalPercentage).subtract(leijizhifu));
                } else if (conp.getCppBasics().equals("JPMsoft_ZFJC_LJJLZ")) { //累计计量值
                    BigDecimal b =  dtlList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getPddPayType()) && e.getPddPayType().equals("JPMsoft_ZFJHTJLX_GCJD") && ValidateUtil.isNotEmpty(e.getPddScheduleType()) &&  e.getPddScheduleType().equals("03")).map(e-> e.getPddStoichiometric()).reduce(BigDecimal.ZERO,BigDecimal::add);
                    mapData.put("paymentAmt", b.subtract(leijizhifu));
//                    BigDecimal leiji = conPaymentPlan.stream().filter(e -> e.getCppBasics().equals("JPMsoft_ZFJC_LJJLZ") && ValidateUtil.isNotNull(e.getCppConditionValue())).map(e -> new BigDecimal(e.getCppConditionValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
//                    mapData.put("paymentAmt", MathUtil.decMultiply(dto.getPaymentTo(), leiji).subtract(leijizhifu));
                }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_JGJSJ")) { //竣工决算价
                    InvFinalAccountsApplyResponse inv = invFinalAccountsApplyMapper.getInvFinalAccountsApplyConId(dto.getConId());
                    BigDecimal paymentAmt = ValidateUtil.isNotNull(inv.getFaApprovedAmt()) ? inv.getFaApprovedAmt() : BigDecimal.ZERO;
                    if(paymentAmt.compareTo(BigDecimal.ZERO) <= 0){
                        return BaseResponse.fail("竣工决算金额为0，该节点不允许支付");
                    }
                    BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_CBSDJ")) { //初步审定价
                    PreliminaryAudit audit =  preliminaryAuditService.getpreliminaryConId(dto.getConId());
                    BigDecimal paymentAmt  = ValidateUtil.isNotNull(audit.getPaExamineApprove()) ? audit.getPaExamineApprove() : BigDecimal.ZERO;
                    if(paymentAmt.compareTo(BigDecimal.ZERO) <= 0){
                        return BaseResponse.fail("初步审定金额为0，该节点不允许支付");
                    }
                    BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                }else if (conp.getCppBasics().equals("JPMsoft_ZFJC_SJJ")) { //结算审计价
                    AuditSettlementModel settlement = auditSettlementMapper.getAuditSettlementConId(dto.getConId());

                    BigDecimal paymentAmt  = ValidateUtil.isNotNull(settlement.getAsAuditedAmount()) ? settlement.getAsAuditedAmount() : BigDecimal.ZERO;
                    if(paymentAmt.compareTo(BigDecimal.ZERO) <= 0){
                        return BaseResponse.fail("结算审计金额为0，该节点不允许支付");
                    }
                    BigDecimal decimalPercentage = new BigDecimal(conp.getCppRatioPayTo()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    mapData.put("paymentAmt", MathUtil.decMultiply(paymentAmt, decimalPercentage).subtract(leijizhifu));
                }
                mapData.put("thisApplication",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))));  //本次可以申请
                mapData.put("appliedFor",leijizhifu);
                mapData.put("thisApplicationAccumulated",new BigDecimal(String.valueOf(mapData.get("paymentAmt"))).add(leijizhifu));  //累计申请（含本次）
                mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付

            }else{
                if (conp.getCppMoney().compareTo(contractMoney) >= 0) {
                    mapData.put("paymentAmt", contractMoney);
                } else {
                    mapData.put("paymentAmt", conp.getCppMoney());
                }
                mapData.put("thisApplication",mapData.get("paymentAmt"));  //本次可以申请
                mapData.put("appliedFor",leijizhifu);
                mapData.put("thisApplicationAccumulated",leijizhifu.add(new BigDecimal(String.valueOf(mapData.get("paymentAmt")))));  //累计申请（含本次）
                mapData.put("accumulatedDisbursement",fundAllocationDtlList.stream().filter(e->ValidateUtil.isNotNull(e.getFadActualPayment())).map(e->e.getFadActualPayment()).reduce(BigDecimal.ZERO,BigDecimal::add));  //累计拨付
            }
        }
        return BaseResponse.ok(mapData);
    }


    public static Date adjustDate(Date baseDate, int number, String unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);

        switch (unit) {
            case "年":
                calendar.add(Calendar.YEAR, number); // 增加年份
                break;
            case "月":
                calendar.add(Calendar.MONTH, number); // 增加月份
                break;
            case "日":
                calendar.add(Calendar.DAY_OF_MONTH, number); // 增加天数
                break;
            default:
                throw new IllegalArgumentException("不支持的单位: " + unit);
        }

        return calendar.getTime(); // 返回调整后的日期
    }

    public static List<ConContractPayPlan> findPlansByCppSort(List<ConContractPayPlan> conPaymentPlan, int targetNumber) {
        List<ConContractPayPlan> result = new ArrayList<>();

        // 遍历支付计划列表
        for (ConContractPayPlan plan : conPaymentPlan) {
            int cppSort = plan.getCppSort(); // 获取当前支付计划的 CppSort 值

            // 判断是否在目标数字范围内
            if (cppSort >= 1 && cppSort <= targetNumber) {
                result.add(plan); // 添加到结果列表
            }
        }

        return result;
    }
    public static void insertOrUpdate(Map<String, Object> map, String key, int value) {
        if (map.containsKey(key)) {
            // 如果已经存在key，比较当前 value 和已有 value 的大小
            int existingValue = (Integer) map.get(key);
            if (value > existingValue) {
                map.put(key, value); // 更新为更大的 value
            }
        } else {
            // 如果不存在该key，直接插入
            map.put(key, value);
        }
    }
}