package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.ConContractRequestNew;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.Contract.ConContractResponseNew;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvBudgetDeclareResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.DockFeign;
import com.jpmsoft.projectProducer.feign.IntelligentSafetyFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConEcMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvBudgetDeclareDtlMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvCompleteMonthMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysParticipantUnitMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementEnd;
import com.jpmsoft.projectProducer.pojo.entity.other.*;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataFileRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.MainDataRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import com.jpmsoft.projectProducer.pojo.response.InvestmentControlLedgerHBModel;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import com.jpmsoft.projectProducer.pojo.vo.project.ProApprovalInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.contract.*;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareService;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementEndService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ConContractImpl extends ServiceImpl<ConContractMapper, ConContract> implements IConContractService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private IConPayService conPayService;

    @Resource
    private Sender sender;

    @Resource
    private IInvSettlementEndService invSettlementEndService;

    @Resource
    private IConContractMoneyDtlService conContractMoneyDtlService;

    @Resource
    private IConContractPayPlanService conContractPayPlanService;

    @Resource
    private IConEstimateService conEstimateService;

    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private IConPayeeDtlService conPayeeDtlService;
    @Resource
    private ISysConfigService sysConfigService;
    @Value("${spring.profiles.active}")
    private String active;

    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;

    @Resource
    private IntelligentSafetyFeign intelligentSafetyFeign;
    @Resource
    private ISysCompanyService sysCompanyService;

    @Resource
    private InvSettlementApplyMapper invSettlementApplyMapper;

    @Resource
    private ConEcMapper conEcMapper;

    @Resource
    private IInvBudgetDeclareService invBudgetDeclareService;

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private IConSecondMemberService conSecondMemberService;

    @Resource
    private DockFeign dockFeign;

    @Resource
    private IBGContactOrderService bgContactOrderService;

    @Resource
    private PreProjectMapper preProjectMapper;
    @Resource
    private IConConsortiumDtlService conConsortiumDtlService;
    @Resource
    private InvBudgetDeclareDtlMapper invBudgetDeclareDtlMapper;
    @Resource
    private InvCompleteMonthMapper invCompleteMonthMapper;
    @Resource
    private IProSourcesFundsService proSourcesFundsService;
    @Resource
    private SysParticipantUnitMapper sysParticipantUnitMapper;
    @Resource
    private IContractPricePaymentInfoService contractPricePaymentInfoService;

    @Override
    public BaseResponse selectChContractSettlement(String conId) {
        return BaseResponse.ok(baseMapper.selectChContractSettlement(conId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateContract(ConContractRequest request) {
        //编码生成
        String codeRule = setContractCode(request);
        //新增修改操作
        ConContract entity = createOrUpdateEntity(request);
        conContractPayPlanService.createOrUpdateConContractPayPlan(request.getPayPlanModels(), entity.getConId());
        //判断合同的标段是否关联概算等信息
        SecInfoResponse secInfoResponse = (SecInfoResponse) secInfoService.getSecInfoById(entity.getConSectionId()).getData();
        if (ValidateUtil.isNotNull(secInfoResponse)) {
            List<ConEstimateModel> conEstimateModels = secInfoResponse.getSecInfoBudgets().stream().map(n -> {
                ConEstimateModel model = new ConEstimateModel();
                model.setCeId(GenerateUUID.getUUID());
                model.setConId(request.getConId());
                model.setCeCost(n.getSibCost());
                model.setCeCostName(n.getSibCostName());
                model.setCeMainCost(n.getSibMainCost());
                model.setCeMainCostName(n.getSibMainCostName());
                model.setCeCompany(n.getSibCompany());
                model.setCeQuantities(n.getSibQuantities());
                model.setCeUnitPrice(n.getSibUnitPrice());
                model.setCeInvestment(n.getSibInvestment());
                model.setCeRemarks(n.getSibRemarks());
                model.setCeTotalExpenses(n.getSibTotalExpenses());
                model.setCeExpenseType(n.getSibExpenseType());
                model.setCeStageType(n.getSibStageType());
                return model;
            }).collect(Collectors.toList());
            conEstimateService.createOrUpdateConEstimate(conEstimateModels, entity.getConId());
        }

        if (!CollectionUtils.isEmpty(request.getConConsortiumDtl())) {
            //存在乙方单位资金规划信息
            conConsortiumDtlService.createOrUpdateConConsortiumDtl(request.getConConsortiumDtl(), entity.getConId());
        }
        // 合同收款单位明细
        conPayeeDtlService.createOrUpdateConPayeeDtl(request.getPayeeDtlModels(), entity.getConId(), entity.getConCreateUserId(), entity.getConCreateUserName());
        if (ValidateUtil.isNotEmpty(codeRule))
            apiUtilsService.refershAutoNoByCustomized(codeRule, request.getConProjectId(), getMainConId(request, codeRule), request.getConSectionId(), request);
        contractPricePaymentInfoService.createOrUpdateContractPricePaymentInfo(request.getContractPricePaymentInfos(), entity.getConId());
        return BaseResponse.ok(entity.getConId());
    }

    @Override
    public BaseResponse saveForGZ(ConContractRequestDto request) {
        ConContract saveEntity = new ConContract();
        ConContract queryEntity = this.getById(request.getConId());
        BeanUtils.copyProperties(request, saveEntity);
        if (null == queryEntity) {
            saveEntity.setConCreateUserId("68D319413F904195A859973BD4C5702D");
            saveEntity.setConCreateUserName("admin");
            saveEntity.setConCreateTime(new Date());
            saveEntity.setConApprovalState(ApprovalStatusEnum.Already_Over.getIndex());
            saveEntity.setConApprovalTime(new Date());
            this.save(saveEntity);
        } else {
            this.updateById(saveEntity);
        }

        //合同支付金额
        ContractEnterVO contractEnterVO = new ContractEnterVO();
        contractEnterVO.setProjectId(request.getConProjectId());
        contractEnterVO.setContractId(request.getConId());
        contractEnterVO.setContractPayMoney(request.getPayAmount().toString());
        intelligentSafetyFeign.insert(contractEnterVO);

        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse BjCreateOrUpdateContract(ConContractRequest request) {
        //编码生成
        String codeRule = setContractCode(request);
        //新增修改操作
        ConContract entity = createOrUpdateEntity(request);
        //region 推送
        String opType = "create";
        if (ValidateUtil.isEmpty(request.getConId()))
            opType = "update";
        sendMsg2Runbath(entity, opType);
        //endregion
        if (ValidateUtil.isNotEmpty(codeRule))
            apiUtilsService.refershAutoNoByCustomized(codeRule, request.getConProjectId(), getMainConId(request, codeRule), request.getConSectionId(), request);
        return BaseResponse.ok(entity.getConId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "conId", codeParam = "conCode", method = "setConCode", codeRule = "ConContract", beanClass = ConContractModel.class)
    public BaseResponse createOrUpdateContractWithMoneyPay(ConContractModel request) {
        ConContract entity;
        //region前置判断
        if (contractIsExist(request.getConCode(), request.getConId(), request.getTeId()))
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (secContractExist(request.getConIsHistory(), request.getConSup(), request.getConRelieveConId(), request.getConProjectId(), request.getConSectionId(), request.getConId()))
            return BaseResponse.fail(ResMsg.secConExist);
        //endregion
        //region 新增修改操作
        if (ValidateUtil.isEmpty(request.getConId())) {
            entity = new ConContract();
            BeanUtils.copyProperties(request, entity);
            entity.setConId(GenerateUUID.getUUID());
            entity.setConCreateTime(new Date());
            entity.setConApprovalState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setConApprovalTime(null);
            //城建通用-服务合同
            if ("0".equals(request.getConCategory())) {
                BigDecimal bigDecimal2 = new BigDecimal(10000);
                if (ValidateUtil.isNotNull(request.getConBidAmt())) {
                    BigDecimal ConBidAmt = request.getConBidAmt().multiply(bigDecimal2);
                    entity.setConBidAmt(ConBidAmt);
                }
                if (ValidateUtil.isNotNull(request.getConBrightAmt())) {
                    BigDecimal ConBrightAmt = request.getConBrightAmt().multiply(bigDecimal2);
                    entity.setConBrightAmt(ConBrightAmt);
                }
            } else {
                entity.setConBidAmt(request.getConBidAmt());
                entity.setConBrightAmt(request.getConBrightAmt());
            }
            if (ValidateUtil.isNull(request.getConTimeConsuming()) && ValidateUtil.isNotNull(request.getConBasisTime()))
                entity.setConTimeConsuming(DateUtil.daysCalculation(request.getConBasisTime(), entity.getConCreateTime()));
            baseMapper.insert(entity);
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getConAcProxy(), request.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo(), request.getConBudgetFile(), request.getConNoticeReport()), entity.getConId());
        } else {
            entity = baseMapper.selectById(request.getConId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
            ConContract pojo = new ConContract();
            BeanUtils.copyProperties(request, pojo);
            //以下参数不可更改
            pojo.setConCreateUserId(entity.getConCreateUserId());
            pojo.setConCreateUserName(entity.getConCreateUserName());
            pojo.setConCreateTime(entity.getConCreateTime());
            pojo.setConApprovalState(entity.getConApprovalState());
            pojo.setConApprovalTime(entity.getConApprovalTime());
            if ("0".equals(request.getConCategory())) {
                BigDecimal bigDecimal2 = new BigDecimal(10000);
                if (ValidateUtil.isNotNull(request.getConBidAmt())) {
                    BigDecimal ConBidAmt = request.getConBidAmt().multiply(bigDecimal2);
                    pojo.setConBidAmt(ConBidAmt);
                }
                if (ValidateUtil.isNotNull(request.getConBrightAmt())) {
                    BigDecimal ConBrightAmt = request.getConBrightAmt().multiply(bigDecimal2);
                    pojo.setConBrightAmt(ConBrightAmt);
                }
            } else {
                pojo.setConBidAmt(request.getConBidAmt());
                pojo.setConBrightAmt(request.getConBrightAmt());
            }
            baseMapper.updateById(pojo);
            sysFileService.FileHelper(pojo, request.gettId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getConAcProxy(), entity.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo(), request.getConBudgetFile(), request.getConNoticeReport()),
                    Arrays.asList(request.getFileIds(), request.getConAcProxyIds(), request.getConWordIds(), request.getConScanIds(), request.getConBasisFileIds(), request.getConStartsToIds(), request.getConBudgetFileIds(), request.getConNoticeReportIds()), pojo.getConId());
        }
        conContractMoneyDtlService.createOrUpdateConContractMoneyDtl(request.getMoneyDtlModels(), entity.getConId());
        conContractPayPlanService.createOrUpdateConContractPayPlan(request.getPayPlanModels(), entity.getConId());
        conPayeeDtlService.createOrUpdateConPayeeDtl(request.getPayeeDtlModels(), entity.getConId(), request.getConCreateUserId(), request.getConCreateUserName());
        conSecondMemberService.createOrUpdateConSecondMember(request.getConSecondMember(), entity.getConId());
        contractPricePaymentInfoService.createOrUpdateContractPricePaymentInfo(request.getContractPricePaymentInfos(), entity.getConId());

        //判断合同的标段是否关联概算等信息
        SecInfoResponse secInfoResponse = (SecInfoResponse) secInfoService.getSecInfoById(entity.getConSectionId()).getData();
        if (ValidateUtil.isNotNull(secInfoResponse)) {
            List<ConEstimateModel> conEstimateModels = secInfoResponse.getSecInfoBudgets().stream().map(n -> {
                ConEstimateModel model = new ConEstimateModel();
                model.setCeId(GenerateUUID.getUUID());
                model.setConId(request.getConId());
                model.setCeCost(n.getSibCost());
                model.setCeCostName(n.getSibCostName());
                model.setCeMainCost(n.getSibMainCost());
                model.setCeMainCostName(n.getSibMainCostName());
                model.setCeCompany(n.getSibCompany());
                model.setCeQuantities(n.getSibQuantities());
                model.setCeUnitPrice(n.getSibUnitPrice());
                model.setCeInvestment(n.getSibInvestment());
                model.setCeRemarks(n.getSibRemarks());
                model.setCeTotalExpenses(n.getSibTotalExpenses());
                model.setCeExpenseType(n.getSibExpenseType());
                model.setCeStageType(n.getSibStageType());
                return model;
            }).collect(Collectors.toList());
            conEstimateService.createOrUpdateConEstimate(conEstimateModels, entity.getConId());
        }
        //endregion
        return BaseResponse.ok(entity.getConId());
    }

    @Override
    public BaseResponse getContractWithMoneyPayList(ConContractModel request, String uId) {
        String authSql = sysUserService.onlyCreateorAndApprover("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Organization.getIndex(), "Con_Approval_State", "Con_Id");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        //region 筛选条件组装
        ConContractRequest req = new ConContractRequest();
        BeanUtils.copyProperties(request, req);
        QueryMap<ConContractRequest> params = assembleScreen(authSql, req);
        //endregion
        IPage<ConContractModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractModel> data = baseMapper.getContractWithMoneyPayList(page, params.getQuery());
        data = data.stream().distinct().collect(Collectors.toList());
        setContractModelCompanyName(data);
        log.info("data:{}", JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getContractList(ConContractRequest request, String uId) {
        return getList(request, getAuthSql(uId, request.getConCategory()));
    }

    /**
     * 获取合同列表（广工）
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getContractGgList(ConContractRequest request, String uId) {
        if (StrUtil.isNotEmpty(request.getConName())) {
            request.setConName(request.getConName().trim());
        }
        if (StrUtil.isNotEmpty(request.getConCode())) {
            request.setConCode(request.getConCode().trim());
        }
        QueryMap<ConContractRequest> params = assembleScreen(getAuthSql(uId, request.getConCategory()), request);
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractGgList(page, params.getQuery());
        List<String> conIds = data.stream().map(ConContractResponse::getConId).collect(Collectors.toList());
        //合同支付汇总
        List<ConPayResponse> conPaylist = conPayService.getPayList(conIds);

        List<ConPayeeDtlModel> conPayeeDtlList = baseMapper.getConPayeeDtlList(conIds);
        Map<String, List<ConPayeeDtlModel>> conPayeeMap = conPayeeDtlList.stream().collect(Collectors.groupingBy(ConPayeeDtlModel::getConId));

        List<ConEstimateModel> conEstimateList = baseMapper.getConEstimateList(conIds);
        Map<String, List<ConEstimateModel>> conEstimateMap = conEstimateList.stream().collect(Collectors.groupingBy(ConEstimateModel::getConId));

        List<String> pIds = data.stream().map(ConContractResponse::getConProjectId).collect(Collectors.toList());
        List<ProSourcesFundsModel> funds = proSourcesFundsService.getProSourcesFundsByPids(pIds);
        Map<String, List<ProSourcesFundsModel>> fundMap = funds.stream().collect(Collectors.groupingBy(ProSourcesFundsModel::getpId));

        //实时合同价
        List<InvSettlementEnd> invendList = invSettlementEndService.getInvConIdList(conIds);
        data.forEach(d -> {
            d.setProSourcesFunds(fundMap.get(d.getConProjectId()));
            d.setConPayeeDtls(conPayeeMap.get(d.getConId()));
            d.setConEstimate(conEstimateMap.get(d.getConId()));
            d.setConEvaluateAmt(MathUtil.decSubtract(d.getConProvisionalPe(), d.getConEvaluateAmt()));
            d.setConEvaluateMoneyTotal(d.getConMoneyTotal());
            BigDecimal reduce = conPaylist.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply())).filter(e -> ValidateUtil.isNotNull(e.getPayState()) && e.getPayState().equals(7) && ValidateUtil.isNotNull(e.getConId()) && e.getConId().equals(d.getConId())).map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);
            d.setConPayApplySum(reduce);
            Optional<InvSettlementEnd> list = invendList.stream().filter(e -> d.getConId().equals(e.getConId()) && e.getState().equals(7)).max(Comparator.comparing(InvSettlementEnd::getStateTime));
            if (list.isPresent()) {
                BigDecimal lem = MathUtil.decSubtract(list.get().getSaeAuditReport(), list.get().getSaeIncreaseDecrease());
                d.setInvSettlementSum(lem);  //呈批价
            } else {
                d.setInvSettlementSum(d.getConMoneyTotal());  //合同价
            }

        });
        Map<String, Object> dataMap = new HashMap<>();
        setContractCompanyName(data);

        if (ValidateUtil.isNotNull(request.getIsCounted()) && request.getIsCounted().equals(1)) {
            List<ConContractResponse> conMoneyTotalSum = baseMapper.getContractListAmtSum(params.getQuery());
            dataMap.put("conMoneyTotalSum", conMoneyTotalSum.stream().filter(e -> ValidateUtil.isNotNull(e.getConMoneyTotal())).map(e -> e.getConMoneyTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
            Set<String> validConIds = conMoneyTotalSum.stream()
                    .map(ConContractResponse::getConId)
                    .filter(ValidateUtil::isNotNull)
                    .collect(Collectors.toSet());
            dataMap.put("data", data);
            //conPayApplySum 合同支付总额
            dataMap.put("conPayApplySum", conPaylist.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply()) && validConIds.contains(e.getConId())).map(d -> d.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add));
            Map<String, InvSettlementEnd> latestRecords = invendList.stream()
                    .filter(e -> validConIds.contains(e.getConId()))
                    .collect(Collectors.toMap(
                            InvSettlementEnd::getConId,
                            Function.identity(),
                            (existing, replacement) -> {
                                Date existingTime = existing.getStateTime();
                                Date replacementTime = replacement.getStateTime();
                                if (existingTime == null && replacementTime == null) {
                                    return existing;
                                } else if (existingTime == null) {
                                    return replacement;
                                } else if (replacementTime == null) {
                                    return existing;
                                } else {
                                    return existingTime.compareTo(replacementTime) > 0 ? existing : replacement;
                                }
                            }
                    ));
            BigDecimal invendSum = latestRecords.values().stream()
                    .map(e -> {
                        BigDecimal saeAuditReport = ValidateUtil.isNotNull(e.getSaeAuditReport()) ? e.getSaeAuditReport() : BigDecimal.ZERO;
                        BigDecimal saeIncreaseDecrease = ValidateUtil.isEmpty(e.getSaeIncreaseDecrease()) ? BigDecimal.ZERO : e.getSaeIncreaseDecrease();
                        return saeAuditReport.subtract(saeIncreaseDecrease != null ? saeIncreaseDecrease : BigDecimal.ZERO);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Set<String> existingConIds = latestRecords.keySet();
            BigDecimal totalAmount = conMoneyTotalSum.stream()
                    .filter(con -> !existingConIds.contains(con.getConId()))
                    .map(ConContractResponse::getConMoneyTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //实时合同价总额
            dataMap.put("invendSum", invendSum.add(totalAmount));

        }
        return BaseResponse.ok(dataMap.isEmpty() ? data : dataMap, (int) page.getTotal());
    }

    @Override
    public BaseResponse getContractListByPermission(ConContractRequest request, String uId) {
        SysUser user = sysUserService.getById(uId);
        QueryMap<ConContractRequest> params = assembleScreen(" where 1=1 ", request);
        params.put("authCpy", user.getCId());//合同权限单位授权
        params.put("authUser", uId);//合同权限用户授权
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractListByPermission(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public List<ConContractResponse> getContractListExcel(ConContractRequest request, String uId) {
        return getListExcel(request, getAuthSql(uId, request.getConCategory()));
    }


    @Override
    public BaseResponse getContractListForDangerousProject(ConContractRequest request, String uId) {
        return getListForDangerousProject(request, getAuthSql(uId, request.getConCategory()));
    }

    @Override
    public BaseResponse getContractListNoAuth(ConContractRequest request, LogUserInfoVo user) {
        return getList(request, " where 1=1 ");
    }

    @Override
    public BaseResponse getContractPartialDataList(ConContractRequest request, LogUserInfoVo user) {
        QueryMap<ConContractRequest> params = assembleScreen(" where 1=1 ", request);
        List<ContractPartialVO> data = baseMapper.getContractPartialDataList(params.getQuery());
        return BaseResponse.ok(data);
    }

    public BaseResponse getListForDangerousProject(ConContractRequest request, String authSql) {
        QueryMap<ConContractRequest> params = assembleScreen(authSql, request);
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractListForDangerousProject(page, params.getQuery());
        data.forEach(d -> {
            d.setConEvaluateAmt(MathUtil.decSubtract(d.getConProvisionalPe(), d.getConEvaluateAmt()));
            d.setConEvaluateMoneyTotal(d.getConMoneyTotal());
        });
        setContractCompanyName(data);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    public BaseResponse getList(ConContractRequest request, String authSql) {
        if (StrUtil.isNotEmpty(request.getConName())) {
            request.setConName(request.getConName().trim());
        }
        if (StrUtil.isNotEmpty(request.getConCode())) {
            request.setConCode(request.getConCode().trim());
        }
        QueryMap<ConContractRequest> params = assembleScreen(authSql, request);
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractList(page, params.getQuery());
        List<String> conIds = data.stream().map(ConContractResponse::getConId).collect(Collectors.toList());
        //合同支付汇总
        List<ConPayResponse> conPaylist = conPayService.getPayList(conIds);
        //实时合同价
        List<InvSettlementEnd> invendList = invSettlementEndService.getInvConIdList(conIds);
        // 合同价款及支付信息
        List<ContractPricePaymentInfoDTO> paymentInfos = contractPricePaymentInfoService.getPaymentInfoList(conIds);
        Map<String, List<ContractPricePaymentInfoDTO>> paymentInfoMap = paymentInfos.stream().collect(Collectors.groupingBy(ContractPricePaymentInfoDTO::getConId));
        data.forEach(d -> {
            d.setConEvaluateAmt(MathUtil.decSubtract(d.getConProvisionalPe(), d.getConEvaluateAmt()));
            d.setConEvaluateMoneyTotal(d.getConMoneyTotal());
            BigDecimal reduce = conPaylist.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply())).filter(e -> ValidateUtil.isNotNull(e.getPayState()) && e.getPayState().equals(7) && ValidateUtil.isNotNull(e.getConId()) && e.getConId().equals(d.getConId())).map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);
            d.setConPayApplySum(reduce);
            Optional<InvSettlementEnd> list = invendList.stream().filter(e -> d.getConId().equals(e.getConId()) && e.getState().equals(7)).max(Comparator.comparing(InvSettlementEnd::getStateTime));
            if (list.isPresent()) {
                BigDecimal lem = MathUtil.decSubtract(list.get().getSaeAuditReport(), list.get().getSaeIncreaseDecrease());
                d.setInvSettlementSum(lem);  //呈批价
            } else {
                d.setInvSettlementSum(d.getConMoneyTotal());  //合同价
            }
            d.setContractPricePaymentInfos(paymentInfoMap.get(d.getConId()));
        });
        Map<String, Object> dataMap = new HashMap<>();
        setContractCompanyName(data);

        if (ValidateUtil.isNotNull(request.getIsCounted()) && request.getIsCounted().equals(1)) {
            List<ConContractResponse> conMoneyTotalSum = baseMapper.getContractListAmtSum(params.getQuery());
            dataMap.put("conMoneyTotalSum", conMoneyTotalSum.stream().filter(e -> ValidateUtil.isNotNull(e.getConMoneyTotal())).map(e -> e.getConMoneyTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
            Set<String> validConIds = conMoneyTotalSum.stream()
                    .map(ConContractResponse::getConId)
                    .filter(ValidateUtil::isNotNull)
                    .collect(Collectors.toSet());
            dataMap.put("data", data);
            //conPayApplySum 合同支付总额
            dataMap.put("conPayApplySum", conPaylist.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply()) && validConIds.contains(e.getConId())).map(d -> d.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add));
            Map<String, InvSettlementEnd> latestRecords = invendList.stream()
                    .filter(e -> validConIds.contains(e.getConId()))
                    .collect(Collectors.toMap(
                            InvSettlementEnd::getConId,
                            Function.identity(),
                            (existing, replacement) -> {
                                Date existingTime = existing.getStateTime();
                                Date replacementTime = replacement.getStateTime();
                                if (existingTime == null && replacementTime == null) {
                                    return existing;
                                } else if (existingTime == null) {
                                    return replacement;
                                } else if (replacementTime == null) {
                                    return existing;
                                } else {
                                    return existingTime.compareTo(replacementTime) > 0 ? existing : replacement;
                                }
                            }
                    ));
            BigDecimal invendSum = latestRecords.values().stream()
                    .map(e -> {
                        BigDecimal saeAuditReport = ValidateUtil.isNotNull(e.getSaeAuditReport()) ? e.getSaeAuditReport() : BigDecimal.ZERO;
                        BigDecimal saeIncreaseDecrease = ValidateUtil.isEmpty(e.getSaeIncreaseDecrease()) ? BigDecimal.ZERO : e.getSaeIncreaseDecrease();
                        return saeAuditReport.subtract(saeIncreaseDecrease != null ? saeIncreaseDecrease : BigDecimal.ZERO);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Set<String> existingConIds = latestRecords.keySet();
            BigDecimal totalAmount = conMoneyTotalSum.stream()
                    .filter(con -> !existingConIds.contains(con.getConId()))
                    .map(ConContractResponse::getConMoneyTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //实时合同价总额
            dataMap.put("invendSum", invendSum.add(totalAmount));

        }
        //TODO 增加合同支付金额， 实时合同价


        return BaseResponse.ok(dataMap.isEmpty() ? data : dataMap, (int) page.getTotal());
    }

    public List<ConContractResponse> getListExcel(ConContractRequest request, String authSql) {
        QueryMap<ConContractRequest> params = assembleScreen(authSql, request);
//        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractListExcel(params.getQuery());
        List<String> conIds = data.stream().map(e -> e.getConId()).collect(Collectors.toList());
        //合同支付汇总
        List<ConPayResponse> conPaylist = conPayService.getPayList(conIds);

        //实时合同价
        List<InvSettlementEnd> invendList = invSettlementEndService.getInvConIdList(conIds);
        data.forEach(d -> {
            d.setConEvaluateAmt(MathUtil.decSubtract(d.getConProvisionalPe(), d.getConEvaluateAmt()));
            d.setConEvaluateMoneyTotal(d.getConMoneyTotal());
            d.setConEvaluateMoneyTotal(d.getConMoneyTotal());
            BigDecimal reduce = conPaylist.stream().filter(e -> ValidateUtil.isNotNull(e.getPayState()) && e.getPayState().equals(7) && ValidateUtil.isNotNull(e.getConId()) && e.getConId().equals(d.getConId())).map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);
            d.setConPayApplySum(reduce);
            Optional<InvSettlementEnd> list = invendList.stream().filter(e -> d.getConId().equals(e.getConId())).max(Comparator.comparing(InvSettlementEnd::getStateTime));
            if (list.isPresent()) {
                BigDecimal lem = MathUtil.decSubtract(list.get().getSaeAuditReport(), list.get().getSaeIncreaseDecrease());
                d.setInvSettlementSum(lem);  //呈批价
            } else {
                d.setInvSettlementSum(d.getConMoneyTotal());  //合同价
            }
        });
        Map<String, Object> dataMap = new HashMap<>();
        setContractCompanyName(data);
        if (ValidateUtil.isNotNull(request.getIsCounted()) && request.getIsCounted().equals(1)) {
            List<ConContractResponse> conMoneyTotalSum = baseMapper.getContractListAmtSum(params.getQuery());
            dataMap.put("conMoneyTotalSum", conMoneyTotalSum.stream().filter(e -> ValidateUtil.isNotNull(e.getConMoneyTotal())).map(e -> e.getConMoneyTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
            dataMap.put("data", data);
        }

        return data;
    }


    @Override
    public BaseResponse getContractBudgetList(ContractBudgetDto request, String uId) {
        QueryMap<ConContractRequest> screen = assembleScreen(getAuthSql(uId, null), request);
        QueryMap<ContractBudgetDto> params = new QueryMap<>(request);
        params.setQuery(screen.getQuery());
        params.put("bddName").put("bddCode").put("excludeCategory");
        if (ValidateUtil.isNotEmpty(request.getConBudgetId())) {
            List<String> bddIds = invBudgetDeclareDtlService.getAllChildIdByBddId(request.getConBudgetId());
            //概算ID获取不到概算ID集合可能是错误的ID
            if (CollectionUtils.isEmpty(bddIds)) return BaseResponse.ok();
            params.put("bddIds", bddIds);
        }
        IPage<ContractBudgetVo> page = new Page<>(request.getPage(), request.getSize());
        //查询默认排除施工合同以及年度合同
        List<ContractBudgetVo> data = baseMapper.getContractBudgetList(page, params.getQuery());
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getConBudgetSum(String pId) {
        ConBudgetSumVo data = baseMapper.getConBudgetSum(pId);
        return BaseResponse.ok(data);
    }

    @Override
    public CommonResponse<BigDecimal> getConMoneyTotal(List<String> pIds) {
        BigDecimal conMoneyTotalSum = baseMapper.getConMoneyTotalSum(pIds);
        return CommonResponse.ok(conMoneyTotalSum);
    }

    private String getAuthSql(String uId, String conCategory) {
        if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("6")) {
            //年度合同
            return sysUserService.onlyInnerAndPartyB("Con_Contract", uId, "Con_Create_User_Id", "Con_Approval_State");
        } else if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("7")) {
            //经费合同 没有项目的数据按合同部门鉴权 有项目的数据按正常项目鉴权
            String authsql = sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
            authsql = authsql.replaceFirst(" where ", " where  (( mn.Con_Project_Id is null and ") + ")  or ( mn.Con_Project_Id is not null and ";
            String projectAuth = sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State");
            projectAuth = projectAuth.replaceFirst(" where ", authsql) + ") )";
            return projectAuth;
            // return sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
        } else {//其他合同
            String authSql = sysUserService.onlyCreateorAndApprover("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State", "Con_Id");
            if (ValidateUtil.isEmpty(authSql)) throw new JPMException(ResMsg.noPermission);
            return authSql;
        }
    }


    @Override
    public BaseResponse getContractWithMoneyPayByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail(ResMsg.notReqParam);
        ConContractResponse entity = baseMapper.getContractByConId(conId);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        setResCompany(entity);
        ConContractModel data = new ConContractModel();
        BeanUtils.copyProperties(entity, data);
        data.setMoneyDtlModels(conContractMoneyDtlService.getConContractMoneyDtlByConId(conId));
        data.setPayPlanModels(conContractPayPlanService.getConContractPayPlanByConId(conId));
        data.setPayeeDtlModels(conPayeeDtlService.getConPayeeDtlByConId(conId));
        data.setConEvaluateAmt(MathUtil.decSubtract(entity.getConEvaluatePeAmt(), entity.getConEvaluateAmt()));
        data.setConSecondMember(conSecondMemberService.getConSecondMemberByConId(conId));
        data.setContractPricePaymentInfos(contractPricePaymentInfoService.getPaymentInfoList(Collections.singletonList(conId)));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getContractByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail(ResMsg.notReqParam);
        ConContractResponse entity = baseMapper.getContractByConId(conId);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        setResCompany(entity);
        entity.setPayPlanModels(conContractPayPlanService.getConContractPayPlanByConId(conId));
        entity.setConEvaluateAmt(MathUtil.decSubtract(entity.getConEvaluatePeAmt(), entity.getConEvaluateAmt()));
        entity.setConSecondMember(conSecondMemberService.getConSecondMemberByConId(conId));
        if (!ValidateUtil.isEmpty(entity.getFileRelationId())) {
            List<FileResponse> files = sysFileAnnexService.getFileByFileRelationId(entity.getFileRelationId());
            entity.setFiles(files == null ? new ArrayList<>() : files);
        }
        List<conConsortiumModel> list = conConsortiumDtlService.slectConConsortiumDtlListByConId(conId);
        if (!CollectionUtils.isEmpty(list)) {
            entity.setConConsortiumDtl(list);
        }
        entity.setPayeeDtlModels(conPayeeDtlService.getConPayeeDtlByConId(conId));
        entity.setContractPricePaymentInfos(contractPricePaymentInfoService.getPaymentInfoList(Collections.singletonList(conId)));
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse getConEvaluateAmt(String conId) {
        return null;
    }

    @Override
    public BaseResponse getContractByConIds(List<String> conIds) {
        if (conIds != null && conIds.size() > 0) {
            return BaseResponse.ok(baseMapper.selectBatchIds(conIds));
        } else {
            return BaseResponse.ok(new ArrayList<>());
        }

    }

    @Override
    public BaseResponse getRelieveContractByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail(ResMsg.notReqParam);
        ConContractResponse entity = baseMapper.getRelieveContractByConId(conId);
        return BaseResponse.ok(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteContractByIds(String ids) {
        String[] contractIds = ids.split(",");
        List<String> list = Arrays.asList(contractIds);
        baseMapper.deleteBatchIds(list);
        //同步删除文档
        sysFileService.removeByIds(list);
        sysFileService.deleteByFileIds(list);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse approvalContract(ConContractRequest request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        ConContract entity = baseMapper.selectById(request.getConId());
        if (!ValidateUtil.isEmpty(entity.getConCategory())
                && entity.getConCategory().equals("5")
                && Objects.equals(entity.getConApprovalState(), ApprovalStatusEnum.Already_Over.getIndex())
                && !ValidateUtil.isEmpty(entity.getConRelieveConId())) {
            //更新主合同为提前解除
            baseMapper.updateContractConState("03", entity.getConRelieveConId());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getContractStatisticsGroupConType(String teId) {
        return BaseResponse.ok(baseMapper.getContractStatisticsGroupConType(teId));
    }

    @Override
    public BaseResponse getContractStatisticsOverview(String teId) {
        Map data = baseMapper.getContractStatisticsOverview(teId);
        if (data != null) {
            data.put("companyCount", baseMapper.getContractPartyBNum(teId));
            data.put("companyToday", baseMapper.getContractPartyBNumToday(teId));
        }
        return BaseResponse.ok(data);
    }

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getContractStatisticsOverviewList(ConContractRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractStatisticsOverviewList(page, request, teId);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getContractStatisticsSigning(String teId) {
        List<Map<String, Object>> datas = baseMapper.getContractStatisticsSigning(teId);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String[] months = new String[month];
        Integer[] recordTotal = new Integer[month];
        BigDecimal[] amtTotal = new BigDecimal[month];
        if (datas != null && datas.size() > 1) {
            datas.forEach(data -> {
                if (data.containsKey("monNum")) {
                    if (Integer.parseInt(data.get("monNum").toString()) <= month)
                        recordTotal[Integer.parseInt(data.get("monNum").toString()) - 1] = Integer.parseInt(data.get("recordTotal").toString());
                    amtTotal[Integer.parseInt(data.get("monNum").toString()) - 1] = new BigDecimal(data.get("amtTotal").toString());
                }
            });
        }

        for (int i = 0; i < month; i++) {
            months[i] = i + 1 + "";
            if (recordTotal[i] == null) recordTotal[i] = 0;
            if (amtTotal[i] == null) amtTotal[i] = new BigDecimal(0);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("months", months);
        result.put("recordTotal", recordTotal);
        result.put("amtTotal", amtTotal);
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getContractStatisticsBiddingType(String teId) {
        List<Map<String, Object>> datas = baseMapper.getContractStatisticsBiddingType(teId);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse BjPushContractWhenConPay(String payId) {
        ConPay conPay = conPayService.getById(payId);
        if (Objects.equals(conPay.getPayState(), ApprovalStatusEnum.Already_Over.getIndex()) || Objects.equals(conPay.getPayState(), ApprovalStatusEnum.Invalid.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        ConContract con = baseMapper.selectById(conPay.getConId());
        Map<String, BigDecimal> data = baseMapper.getAdditionalExpenditure(conPay.getConId(), payId);
        BigDecimal payManagementExpense = BigDecimal.ZERO;
        BigDecimal paySporadicExpenditure = BigDecimal.ZERO;
        BigDecimal payOtherExpensesPayable = BigDecimal.ZERO;
        //BigDecimal payTotalP = BigDecimal.ZERO;
        if (data != null) {
            payManagementExpense = data.getOrDefault("payManagementExpense", BigDecimal.ZERO);
            paySporadicExpenditure = data.getOrDefault("paySporadicExpenditure", BigDecimal.ZERO);
            payOtherExpensesPayable = data.getOrDefault("payOtherExpensesPayable", BigDecimal.ZERO);
        }
        ConContractRequest request = new ConContractRequest();
        BeanUtils.copyProperties(con, request);
        request.setPayManagementExpense(payManagementExpense.add(conPay.getPayManagementExpense() == null ? BigDecimal.ZERO : conPay.getPayManagementExpense()));
        request.setPaySporadicExpenditure(paySporadicExpenditure.add(conPay.getPaySporadicExpenditure() == null ? BigDecimal.ZERO : conPay.getPaySporadicExpenditure()));
        request.setPayOtherExpensesPayable(payOtherExpensesPayable.add(conPay.getPayOtherExpensesPayable() == null ? BigDecimal.ZERO : conPay.getPayOtherExpensesPayable()));
        request.setPayTotalP(new BigDecimal(Float.toString(conPay.getPayTotalP())));
        request.setConPayType(conPay.getPayType());
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("action", "pushContractWhenConPay");
        sendData.put("data", JacksonUtils.toJSONString(request));
        sender.send(JacksonUtils.toJSONString(sendData));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateContractCodeById(String conId, String conCode) {
        ConContract con = baseMapper.selectById(conId);
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        try {
            conCode = URLDecoder.decode(conCode, "UTF-8");
            con.setConCode(conCode);
            baseMapper.updateById(con);
            return BaseResponse.ok();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
            return BaseResponse.fail(ResMsg.actError);
        }
    }

    @Override
    public BaseResponse updateContractKeyInfoById(ConContractRequest request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        ConContract con = baseMapper.selectById(request.getConId());
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        con.setConDate(request.getConDate());
        con.setConState(request.getConState());
        con.setConExecution(request.getConExecution());
        con.setConScan(request.getConScan());
        con.setConScanUp(request.getConScanUp());
        con.setConUsingDate(request.getConUsingDate());
        con.setConUsingCopies(request.getConUsingCopies());
        con.setConStartDate(request.getConStartDate());
        con.setConStartsTo(request.getConStartsTo());
        con.setConUsingType(request.getConUsingType());
        con.setConCode(request.getConCode());
        con.setTeId(request.getTeId());
        con.setConOriginalCode(request.getConOriginalCode());
        con.setConDeclareMoney(request.getConDeclareMoney());
        if ("0".equals(request.getConCategory())) {
            BigDecimal bigDecimal2 = new BigDecimal(10000);
            if (ValidateUtil.isNotNull(request.getConBidAmt())) {
                BigDecimal ConBidAmt = request.getConBidAmt().multiply(bigDecimal2);
                con.setConBidAmt(ConBidAmt);
            }
            if (ValidateUtil.isNotNull(request.getConBrightAmt())) {
                BigDecimal ConBrightAmt = request.getConBrightAmt().multiply(bigDecimal2);
                con.setConBrightAmt(ConBrightAmt);
            }
        } else {
            con.setConBidAmt(request.getConBidAmt());
            con.setConBrightAmt(request.getConBrightAmt());
        }

        baseMapper.updateById(con);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getConScan(), request.getConStartsTo())
                , Arrays.asList(request.getConScanIds(), request.getConStartsToIds()), con.getConId());
        sendMsg2Runbath(con, "update");
        return BaseResponse.ok(con.getConId());
    }

    @Override
    public BaseResponse updateContractBudget(String conId, String bddId) {
        ConContract con = baseMapper.selectById(conId);
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        con.setConBudgetId(bddId);
        baseMapper.updateById(con);
        return BaseResponse.ok();
    }

    @Override
    public BigDecimal getConMoneyTotalSum(List<String> conIds) {
        return baseMapper.getConMoneyTotalSum(conIds);
    }

    @Override
    public BigDecimal getConMoneyTotalSum(String pId) {
        return baseMapper.getConMoneyTotalSumByPid(pId);
    }


    /**
     * 新增修改前置判断
     * 判断合同编码是否重复
     *
     * @param conCode 合同编码
     * @param conId   合同ID
     */
    private Boolean contractIsExist(String conCode, String conId, String teId) {
        //合同编码配置后置生成
        String pgc = sysConfigService.getConfigByCode("endProcessGenerateCode");
        if (ValidateUtil.isNotEmpty(pgc) && pgc.equals("1")) return false;
        //配置了合同不校验编码重复
        String str = sysConfigService.getConfigByCode("checkContractCodeOnly");
        if (ValidateUtil.isEmpty(str) || !str.equals("1")) return false;
        QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Con_Code", conCode);
        wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Invalid.getIndex());
        if (ValidateUtil.isNotEmpty(conId)) wrapper.ne("Con_Id", conId);
        return baseMapper.selectCount(wrapper) > 0;
    }

    private Boolean secContractExist(Integer histroy, String conSup, String reliveId, String pId, String secId, String conId) {
        String conf = sysConfigService.getConfigByCode("secContractUnique");
        if (ValidateUtil.isNotEmpty(conf) && !conf.equals("1")) return false;
        if (histroy != null && histroy == 1) return false;//历史合同
        if (ValidateUtil.isNotEmpty(conSup) && (conSup.equals("是") || conSup.equals("农民工专户协议"))) return false;
        if (ValidateUtil.isNotEmpty(reliveId)) return false; //解除合同
        if (ValidateUtil.isEmpty(pId)) return false;//无项目合同 年度合同
        if (ValidateUtil.isNotEmpty(secId)) {
            QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(conId)) wrapper.ne("Con_Id", conId);
            wrapper.eq("Con_Section_Id", secId);
            wrapper.eq("Con_Project_Id", pId);
            wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Invalid.getIndex());
            wrapper.ne(ValidateUtil.isNotEmpty(conId) && "否".equalsIgnoreCase(conSup), "Con_Master_Con_Id", conId);
            return baseMapper.exists(wrapper);
        }
        return false;
    }


    @Override
    public Boolean secContractExist(String secId, String pId) {
        String conf = sysConfigService.getConfigByCode("secContractUnique");
        if (ValidateUtil.isNotEmpty(conf) && !conf.equals("1")) return false;
        QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Section_Id", secId);
        wrapper.eq("Con_Project_Id", pId);
        wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse getSecContract(String secId, String pId) {
        QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Section_Id", secId);
        wrapper.eq("Con_Project_Id", pId);
        wrapper.eq("Con_Approval_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("Con_Approval_Time");
        List<ConContract> contractList = baseMapper.selectList(wrapper);
        ConContractResponse conContractResponse = new ConContractResponse();
        if (CollectionUtil.isNotEmpty(contractList)) {
            BeanUtils.copyProperties(contractList.get(0), conContractResponse);
        }
        return BaseResponse.ok(conContractResponse);
    }

    @Override
    public BaseResponse getConContractByPid(String id) {
        return BaseResponse.ok(baseMapper.getConContractByPid(id));
    }

    @Override
    public List<ConContract> getConContractBySecId(String secId) {
        return baseMapper.getConContractBySecId(secId);
    }

    /**
     * 组装筛选条件
     * List查询
     */
    private QueryMap<ConContractRequest> assembleScreen(String authSql, ConContractRequest request) {
        QueryMap<ConContractRequest> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("conId").put("conCategory").put("conApprovalState").put("pId").put("conScanUp").put("evaluateContract").put("conOriginalCode")
                .put("conType").put("conName").put("conCode").put("conProjectId").put("conIsHistory").put("conEvaluateId").put("conIsSupply")
                .put("conProjectName").put("conState").put("conExecution").put("conPartyBPm").put("pAdvanceFund").put("pCorporateRisk").put("conInvolveFunds")
                .put("conPartyBPmConsNu").put("conPartyBPmIdcard").put("conBiddingType").put("conUsingType").put("conEvaluateName").put("conSerialNumber")
                .put("conPartyBLegal").put("conPartyBAgent").put("conPartyBPoxyNum").put("conIsBidding").put("excludeSeven").put("conYear").put("conMoneyTotal")
                .put("conPartyBAgentTel").put("conTdName").put("conTdNum").put("conTdMobileNumber").put("filterSubChangeAmt").put("conOpenBidding")
                .put("conConsortium").put("conMultiSubject").put("conSup").put("onPartyBPmTel").put("conProfessionalType").put("conPurchaseType")
                .put("conCreateUserName").put("conCurrency").put("conPartyAName").put("conPartyAOtherName").put("conEvaluateCode").put("conPayCategory")
                .put("conPartyBName").put("conMasterConId").put("conSectionId").put("bUnitId").put("conTypes").put("containState").put("blSearch")
                .put(request.getConMoneyTotals(), "moneyTotalStart", "moneyTotalEnd")
                .put(request.getConTotalDurations(), "totalDurationStart", "totalDurationEnd")
                .put(request.getConPlanStartDates(), "planStartDateStart", "planStartDateEnd")
                .put(request.getConPlanEndDates(), "planEndDateStart", "planEndDateEnd")
                .put(request.getConCreateTimes(), "createTimeStart", "createTimeEnd")
                .put(request.getConDates(), "conDateStart", "conDateEnd").put("filterSettlement").put("conPb").put("excludeConMulti")
                .put(request.getConStartDates(), "startDateStart", "startDateEnd")
                .put(request.getCompleteDates(), "completeDateStart", "completeDateEnd")
                .put(request.getEndTimes(), "endTimeStart", "endTimeEnd")
                .put("stateFilterType").put("pCampus").put("pType").put("conTerm").put("pBuildingScale").put("fsCostCode").put("excludeConIds")
                .put("excludeConTypes")
                .put("conNames").put("conCodes").put("pNames").put("pName")
                .put("conPartyANames").put("conPartyBNames")
                .put("conCreateUserNames").put("conApprovalStates").put("conSups")
                .put("conCategoryDn");
        if (ValidateUtil.isNotEmpty(request.getIncludeCategoryStr())) {
            query.put("includeCategoryStr", request.getIncludeCategoryStr().split(","));
        }
        if (ValidateUtil.isNotEmpty(request.getExcludeCategoryStr())) {
            query.put("excludeCategoryStr", request.getExcludeCategoryStr().split(","));
        }
        return query;
    }

    /**
     * 北京项目修改时推送
     */
    private void sendMsg2Runbath(ConContract entity, String opType) {
        if (!active.equals("bj")) return;
        //region 北京推送
        try {
            if (Objects.equals(entity.getConApprovalState(), ApprovalStatusEnum.Already_Over.getIndex())) {
                //只有审批后的大合同才推送更新
                if (!ValidateUtil.isEmpty(entity.getConType())) {
                    log.info("合同类型：" + entity.getConType().substring(0, 2));
                    if (entity.getConType().substring(0, 2).equals("01")) {
                        log.info("send 北京推送");
                        //推送合同信息
                        Map<String, Object> sendData = new HashMap<>();
//                        if (opType.equals("update")) {
//                            sendData.put("action", "pushContractUpdate");
//                            sendData.put("data", JacksonUtils.toJSONString(entity));
//                            sender.send(JacksonUtils.toJSONString(sendData));
//                        }
                        //推送标段信息（小项目+合同信息）
//                        sendData = new HashMap<>();
                        sendData.put("action", "pushProSec");
                        sendData.put("data", entity.getConId());
                        sendData.put("opType", opType);
                        sender.send(JacksonUtils.toJSONString(sendData));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("推送数据异常" + ex.getMessage(), ex);
        }
        //endregion
    }


    /**
     * 创建修改合同
     */
    private ConContract createOrUpdateEntity(ConContractRequest request) {
        ConContract entity;
        //region 前置校验
        boolean skipCheck = Boolean.TRUE.equals(request.getSkipCodeCheck());
        if (!skipCheck) {
            if (contractIsExist(request.getConCode(), request.getConId(), request.getTeId()))
                throw new JPMException(ResMsg.codeIsRepeat);

            if (secContractExist(request.getConIsHistory(), request.getConSup(),
                    request.getConRelieveConId(), request.getConProjectId(),
                    request.getConSectionId(), request.getConId()))
                throw new JPMException(ResMsg.secConExist);
        }
        //endregion
        //region 新增修改操作
        if (ValidateUtil.isEmpty(request.getConId())) {
            entity = new ConContract();
            BeanUtils.copyProperties(request, entity);
            entity.setConId(GenerateUUID.getUUID());
            if (ValidateUtil.isNull(request.getSkipApproval()) || !request.getSkipApproval()) {
                entity.setConCreateTime(new Date());
                entity.setConApprovalState(ApprovalStatusEnum.Not_Started.getIndex());
                entity.setConApprovalTime(null);
            } else {
                entity.setConCreateTime(request.getConCreateTime());
                entity.setConApprovalState(request.getConApprovalState());
                entity.setConApprovalTime(request.getConApprovalTime());
            }
            if (request.getConCategory().equals("0")) {
                BigDecimal bigDecimal2 = new BigDecimal(10000);
                if (ValidateUtil.isNotNull(request.getConBidAmt())) {
                    BigDecimal ConBidAmt = request.getConBidAmt().multiply(bigDecimal2);
                    entity.setConBidAmt(ConBidAmt);
                }
                if (ValidateUtil.isNotNull(request.getConBrightAmt())) {
                    BigDecimal ConBrightAmt = request.getConBrightAmt().multiply(bigDecimal2);
                    entity.setConBrightAmt(ConBrightAmt);
                }
            } else {
                entity.setConBidAmt(request.getConBidAmt());
                entity.setConBrightAmt(request.getConBrightAmt());
            }
            if (ValidateUtil.isNull(request.getConTimeConsuming()) && ValidateUtil.isNotNull(request.getConBasisTime()))
                entity.setConTimeConsuming(DateUtil.daysCalculation(request.getConBasisTime(), entity.getConCreateTime()));
            baseMapper.insert(entity);
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getConAcProxy(), request.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo(), request.getConBudgetFile(), request.getConNoticeReport()), entity.getConId());

        } else {
            entity = baseMapper.selectById(request.getConId());
            if (entity == null) throw new JPMException(ResMsg.infoNotExist);
            ConContract pojo = new ConContract();
            BeanUtils.copyProperties(request, pojo);
            //以下参数不可更改
            pojo.setConCreateUserId(entity.getConCreateUserId());
            pojo.setConCreateUserName(entity.getConCreateUserName());
            pojo.setConCreateTime(entity.getConCreateTime());
            pojo.setConApprovalState(entity.getConApprovalState());
            pojo.setConApprovalTime(entity.getConApprovalTime());
            if (ValidateUtil.isNotEmpty(request.getConCategory()) && request.getConCategory().equals("0")) {
                BigDecimal bigDecimal2 = new BigDecimal(10000);
                if (ValidateUtil.isNotNull(request.getConBidAmt())) {
                    BigDecimal ConBidAmt = request.getConBidAmt().multiply(bigDecimal2);
                    entity.setConBidAmt(ConBidAmt);
                }
                if (ValidateUtil.isNotNull(request.getConBrightAmt())) {
                    BigDecimal ConBrightAmt = request.getConBrightAmt().multiply(bigDecimal2);
                    entity.setConBrightAmt(ConBrightAmt);
                }
            } else {
                entity.setConBidAmt(request.getConBidAmt());
                entity.setConBrightAmt(request.getConBrightAmt());
            }
            baseMapper.updateById(pojo);
            sysFileService.FileHelper(pojo, request.gettId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getConAcProxy(), entity.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo(), request.getConBudgetFile(), request.getConNoticeReport())
                    , Arrays.asList(request.getFileIds(), request.getConAcProxyIds(), request.getConWordIds(), request.getConScanIds(), request.getConBasisFileIds(), request.getConStartsToIds(), request.getConBudgetFileIds(), request.getConNoticeReportIds()), pojo.getConId());

        }
        //endregion
        return entity;
    }

    @Override
    public String generateContractCode(String id) {
        ConContract con = baseMapper.selectById(id);
        if (con == null) return null;
        //region 编码生成
        String seqCode = "ConContract";
        String conId = con.getConId();
        //createOrUpdateContractWithMoneyPay 创建不区分类型
        if (ValidateUtil.isNull(con.getConIsBidding())) {
            //历史合同编码规则
            if (!ValidateUtil.isEmpty(con.getConSup()) && con.getConSup().equals("是")) {
                seqCode = "ConSupContract";
                conId = con.getConMasterConId();
            }
            //解除合同
            if (!ValidateUtil.isEmpty(con.getConRelieveConId())) {
                seqCode = "RelieveContract";
                conId = con.getConRelieveConId();
            }
        }
        if (con.getConIsHistory() == 0 && apiUtilsService.sequenceRestEnable(seqCode)) {
            //非历史合同才生成编码
            String code = apiUtilsService.generateCodeByCustomized(seqCode, con.getConProjectId(), conId, con.getConSectionId(), con);
            if (ValidateUtil.isEmpty(code)) return null;
            apiUtilsService.refershAutoNoByCustomized(seqCode, con.getConProjectId(), conId, con.getConSectionId(), con);
            return code;
        }
        return con.getConCode();
    }

    @Override
    public boolean can2ProcessVoidingTheContract(String id) {
        //在资金计划中选择了合同
        Integer funPlanCount = baseMapper.getFundPlanContractCount(id);
        return funPlanCount == null || funPlanCount <= 0;
    }

    @Override
    public BaseResponse getContractFundStatistics(ContractFundStatisticsDTO request, String uId) {
        if (ValidateUtil.isEmpty(request.getYear())) return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<ContractFundStatisticsDTO> queryMap = new QueryMap<>(request);
        if (ValidateUtil.isEmpty(request.getPId()))
            queryMap.put("authSql", sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Organization.getIndex(), "Con_Approval_State"));
        queryMap.put("pId").put("pName").put("pCode").put("conCode").put("conName").put("conPartyAName").put("year").put("isYear")
                .put("conPartyBName").put("conType").put("conState").put("conExecution").put("conEvaluateId").put("conEvaluateName").put("conEvaluateCode").put("childProject");
        IPage<ContractFundStatisticsVO> page = new Page<>(request.getPage(), request.getSize());
        // 递归查询多级项目
        if (ValidateUtil.isNotEmpty(request.getChildProject())) {
            List<PreProject> allProjects = preProjectService.list(Wrappers.<PreProject>lambdaQuery()
                    .select(PreProject::getpId, PreProject::getpParentId));
            Set<String> allPids = new HashSet<>();
            if (ValidateUtil.isNotEmpty(request.getPId())) {
                allPids.add(request.getPId());
                if (CollectionUtil.isNotEmpty(allProjects)) {
                    Map<String, List<PreProject>> parentMap = allProjects.stream()
                            .filter(p -> ValidateUtil.isNotEmpty(p.getpParentId()))
                            .collect(Collectors.groupingBy(PreProject::getpParentId));
                    Queue<String> queue = new LinkedList<>();
                    queue.offer(request.getPId());
                    while (!queue.isEmpty()) {
                        String currentId = queue.poll();
                        List<PreProject> children = parentMap.get(currentId);
                        if (CollectionUtil.isNotEmpty(children)) {
                            for (PreProject child : children) {
                                String childId = child.getpId();
                                if (ValidateUtil.isNotEmpty(childId) && !allPids.contains(childId)) {
                                    allPids.add(childId);
                                    queue.offer(childId);
                                }
                            }
                        }
                    }
                }
                queryMap.put("allPids", allPids);
            }
        }

        List<ContractFundStatisticsVO> data = baseMapper.getContractFundStatistics(page, queryMap.getQuery());
        ContractFundStatisticsSumVO sum;
        if (CollectionUtils.isEmpty(data)) sum = new ContractFundStatisticsSumVO();
        else {
            sum = baseMapper.getContractFundStatisticsSum(queryMap.getQuery());
            sum.setPayApprovalRateSum(MathUtil.decimalDivide(sum.getPayApprovalTotalSum(), sum.getConMoneyTotalSum(), 4));
            sum.setIsPaymentRateSum(MathUtil.decimalDivide(sum.getIsPaymentAmtTotalSum(), sum.getConMoneyTotalSum(), 4));
            sum.setData(data.stream().map(d -> {
                d.setIsPaymentRateTotal(MathUtil.decimalDivide(d.getIsPaymentAmtTotal(), d.getConMoneyTotal(), 4));
                d.setPayApprovalRate(MathUtil.decimalDivide(d.getPayApprovalTotal(), d.getConMoneyTotal(), 4));
                d.setEcfApplyRate(MathUtil.decimalDivide(d.getEcfApplyMoneyTotal(), d.getConMoneyTotal(), 4));
                d.setEcApplyRate(MathUtil.decimalDivide(d.getEcApplyMoneyTotal(), d.getConMoneyTotal(), 4));
                return d;
            }).collect(Collectors.toList()));
        }
        return BaseResponse.ok(sum, Long.valueOf(page.getTotal()).intValue());
    }


    /**
     * 合同编码生成规则
     */
    private String setContractCode(ConContractRequest request) {
        //region 编码生成
        String seqCode = "ConContract";
        String conId = request.getConId();
        //历史合同编码规则
        if (!ValidateUtil.isEmpty(request.getConSup()) && request.getConSup().equals("是")) {
            seqCode = "ConSupContract";
            conId = request.getConMasterConId();
        }
        //解除合同
        if (!ValidateUtil.isEmpty(request.getConRelieveConId())) {
            seqCode = "RelieveContract";
            conId = request.getConRelieveConId();
        }
        //缺省为非历史合同
        if (request.getConIsHistory() == null) request.setConIsHistory(0);
        if (!sysConfigService.endProcessGenerateCode()) {
            if (ValidateUtil.isEmpty(request.getConId()) && request.getConIsHistory() == 0 && apiUtilsService.sequenceRestEnable(seqCode)) {
                //非历史合同才生成编码
                request.setConCode(apiUtilsService.generateCodeByCustomized(seqCode, request.getConProjectId(), conId, request.getConSectionId(), request));
                if (ValidateUtil.isEmpty(request.getConCode())) throw new JPMException(ResMsg.codeGenerError);
                return seqCode;
            }
        }
        //endregion
        return null;
    }

    private String getMainConId(ConContractRequest request, String codeRule) {
        if (ValidateUtil.isEmpty(codeRule)) return request.getConId();
        if (codeRule.equals("RelieveContract")) return request.getConRelieveConId();
        if (codeRule.equals("ConSupContract")) return request.getConMasterConId();
        return request.getConId();
    }

    /**
     * 查询赋值合同单位名称字段
     * 分段查询 10+单位链表查询影响性能
     *
     * @param data
     */
    private void setContractCompanyName(List<ConContractResponse> data) {
        if (!CollectionUtils.isEmpty(data)) {
            Set<String> cpyIds = new HashSet<>();
            for (ConContractResponse con : data) {
                hashSetAdd(cpyIds, con.getConPartyA1Id());
                hashSetAdd(cpyIds, con.getConPartyA2Id());
                hashSetAdd(cpyIds, con.getConPartyA3Id());
                hashSetAdd(cpyIds, con.getConPartyA4Id());
                hashSetAdd(cpyIds, con.getConPartyA5Id());
                hashSetAdd(cpyIds, con.getConPartyB1());
                hashSetAdd(cpyIds, con.getConPartyB2());
                hashSetAdd(cpyIds, con.getConPartyB3());
                hashSetAdd(cpyIds, con.getConPartyB4());
                hashSetAdd(cpyIds, con.getConPartyB5());
            }
            List<SysCompany> cpys = sysCompanyService.getCompanyByIds(new ArrayList<>(cpyIds));
            if (!CollectionUtils.isEmpty(cpys)) {
                data.forEach(d -> {
                    d.setConPartyA1Name(getCompanyName(cpys, d.getConPartyA1Id()));
                    d.setConPartyA2Name(getCompanyName(cpys, d.getConPartyA2Id()));
                    d.setConPartyA3Name(getCompanyName(cpys, d.getConPartyA3Id()));
                    d.setConPartyA4Name(getCompanyName(cpys, d.getConPartyA4Id()));
                    d.setConPartyA5Name(getCompanyName(cpys, d.getConPartyA5Id()));

                    d.setConPartyB1Name(getCompanyName(cpys, d.getConPartyB1()));
                    d.setConPartyB2Name(getCompanyName(cpys, d.getConPartyB2()));
                    d.setConPartyB3Name(getCompanyName(cpys, d.getConPartyB3()));
                    d.setConPartyB4Name(getCompanyName(cpys, d.getConPartyB4()));
                    d.setConPartyB5Name(getCompanyName(cpys, d.getConPartyB5()));
                });
            }
        }
    }

    private void hashSetAdd(Set<String> cpyIds, String id) {
        if (ValidateUtil.isNotEmpty(id)) cpyIds.add(id);
    }

    private String getCompanyName(List<SysCompany> cpys, String id) {
        if (ValidateUtil.isNotEmpty(id)) {
            List<SysCompany> cpy = cpys.stream().filter(f -> f.getcId().equals(id)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cpy)) return cpy.stream().findFirst().get().getcName();
        }
        return null;
    }


    /**
     * 查询赋值合同单位名称字段
     * 分段查询 10+单位链表查询影响性能
     *
     * @param data
     */
    private void setContractModelCompanyName(List<ConContractModel> data) {
        if (!CollectionUtils.isEmpty(data)) {
            Set<String> cpyIds = new HashSet<>();
            for (ConContractModel con : data) {
                hashSetAdd(cpyIds, con.getConPartyA1Id());
                hashSetAdd(cpyIds, con.getConPartyA2Id());
                hashSetAdd(cpyIds, con.getConPartyA3Id());
                hashSetAdd(cpyIds, con.getConPartyA4Id());
                hashSetAdd(cpyIds, con.getConPartyA5Id());
                hashSetAdd(cpyIds, con.getConPartyB1());
                hashSetAdd(cpyIds, con.getConPartyB2());
                hashSetAdd(cpyIds, con.getConPartyB3());
                hashSetAdd(cpyIds, con.getConPartyB4());
                hashSetAdd(cpyIds, con.getConPartyB5());
            }
            List<SysCompany> cpys = sysCompanyService.getCompanyByIds(new ArrayList<>(cpyIds));
            if (!CollectionUtils.isEmpty(cpys)) {
                data.forEach(d -> {
                    d.setConPartyA1Name(getCompanyName(cpys, d.getConPartyA1Id()));
                    d.setConPartyA2Name(getCompanyName(cpys, d.getConPartyA2Id()));
                    d.setConPartyA3Name(getCompanyName(cpys, d.getConPartyA3Id()));
                    d.setConPartyA4Name(getCompanyName(cpys, d.getConPartyA4Id()));
                    d.setConPartyA5Name(getCompanyName(cpys, d.getConPartyA5Id()));

                    d.setConPartyB1Name(getCompanyName(cpys, d.getConPartyB1()));
                    d.setConPartyB2Name(getCompanyName(cpys, d.getConPartyB2()));
                    d.setConPartyB3Name(getCompanyName(cpys, d.getConPartyB3()));
                    d.setConPartyB4Name(getCompanyName(cpys, d.getConPartyB4()));
                    d.setConPartyB5Name(getCompanyName(cpys, d.getConPartyB5()));
                });
            }
        }
    }

    private void setResCompany(ConContractResponse data) {
        if (ValidateUtil.isNotNull(data)) {
            Set<String> cpyIds = new HashSet<>();
            hashSetAdd(cpyIds, data.getConPartyA1Id());
            hashSetAdd(cpyIds, data.getConPartyA2Id());
            hashSetAdd(cpyIds, data.getConPartyA3Id());
            hashSetAdd(cpyIds, data.getConPartyA4Id());
            hashSetAdd(cpyIds, data.getConPartyA5Id());
            hashSetAdd(cpyIds, data.getConPartyBId());
            hashSetAdd(cpyIds, data.getConPartyB1());
            hashSetAdd(cpyIds, data.getConPartyB2());
            hashSetAdd(cpyIds, data.getConPartyB3());
            hashSetAdd(cpyIds, data.getConPartyB4());
            hashSetAdd(cpyIds, data.getConPartyB5());
            hashSetAdd(cpyIds, data.getConPartyB6());
            hashSetAdd(cpyIds, data.getConPartyB7());
            hashSetAdd(cpyIds, data.getConPartyB8());
            hashSetAdd(cpyIds, data.getConPartyB9());
            hashSetAdd(cpyIds, data.getConPartyB10());
            hashSetAdd(cpyIds, data.getConPartyOther1Id());
            hashSetAdd(cpyIds, data.getConPartyOther2Id());
            hashSetAdd(cpyIds, data.getConPartyOther3Id());
            hashSetAdd(cpyIds, data.getConPartyOther4Id());
            hashSetAdd(cpyIds, data.getConPartyOther5Id());
            hashSetAdd(cpyIds, data.getConPartyOther6Id());
            hashSetAdd(cpyIds, data.getConPartyOther7Id());
            hashSetAdd(cpyIds, data.getConPartyOther8Id());
            hashSetAdd(cpyIds, data.getConPartyOther9Id());
            hashSetAdd(cpyIds, data.getConPartyOther10Id());
            List<SysCompany> cpys = sysCompanyService.getCompanyByIds(new ArrayList<>(cpyIds));
            if (!CollectionUtils.isEmpty(cpys)) {
                data.setConPartyA1Name(getCompanyName(cpys, data.getConPartyA1Id()));
                data.setConPartyA2Name(getCompanyName(cpys, data.getConPartyA2Id()));
                data.setConPartyA3Name(getCompanyName(cpys, data.getConPartyA3Id()));
                data.setConPartyA4Name(getCompanyName(cpys, data.getConPartyA4Id()));
                data.setConPartyA5Name(getCompanyName(cpys, data.getConPartyA5Id()));
                data.setConPartyBName(getCompanyName(cpys, data.getConPartyBId()));
                data.setConPartyB1Name(getCompanyName(cpys, data.getConPartyB1()));
                data.setConPartyB2Name(getCompanyName(cpys, data.getConPartyB2()));
                data.setConPartyB3Name(getCompanyName(cpys, data.getConPartyB3()));
                data.setConPartyB4Name(getCompanyName(cpys, data.getConPartyB4()));
                data.setConPartyB5Name(getCompanyName(cpys, data.getConPartyB5()));
                data.setConPartyB6Name(getCompanyName(cpys, data.getConPartyB6()));
                data.setConPartyB7Name(getCompanyName(cpys, data.getConPartyB7()));
                data.setConPartyB8Name(getCompanyName(cpys, data.getConPartyB8()));
                data.setConPartyB9Name(getCompanyName(cpys, data.getConPartyB9()));
                data.setConPartyB10Name(getCompanyName(cpys, data.getConPartyB10()));
                data.setConPartyOther1Name(getCompanyName(cpys, data.getConPartyOther1Id()));
                data.setConPartyOther2Name(getCompanyName(cpys, data.getConPartyOther2Id()));
                data.setConPartyOther3Name(getCompanyName(cpys, data.getConPartyOther3Id()));
                data.setConPartyOther4Name(getCompanyName(cpys, data.getConPartyOther4Id()));
                data.setConPartyOther5Name(getCompanyName(cpys, data.getConPartyOther5Id()));
                data.setConPartyOther6Name(getCompanyName(cpys, data.getConPartyOther6Id()));
                data.setConPartyOther7Name(getCompanyName(cpys, data.getConPartyOther7Id()));
                data.setConPartyOther8Name(getCompanyName(cpys, data.getConPartyOther8Id()));
                data.setConPartyOther9Name(getCompanyName(cpys, data.getConPartyOther9Id()));
                data.setConPartyOther10Name(getCompanyName(cpys, data.getConPartyOther10Id()));
            }
        }
    }


    @Override
    public BaseResponse updateConContractApproveStatus(ConContractRequest request) {
        String conId = request.getConId();
        SysFile sysFile = sysFileService.getById(conId);
        String fileRelationId = sysFile.getFileRelationId();
        String fileflowstatus = sysFile.getFileflowstatus();

        ConContract contract = this.getById(conId);

        if ("7".equals(fileflowstatus) && "否".equals(request.getConSup())) {
            //查询云上建工用户信息
            CommonResponse<ThirdUserRequest> thirdUserCommonResponse = dockFeign.getThirdUser(contract.getConCreateUserId());
            if (thirdUserCommonResponse.isSuccess() && thirdUserCommonResponse.getData() != null) {
                ThirdUserRequest thirdUserRequest = thirdUserCommonResponse.getData();

                contract.setThirdUserId(thirdUserRequest.getUserId());
                //创建流程
                WorkFlowRequest workFlowRequest = new WorkFlowRequest();
                workFlowRequest.setRequestName("工程代建部关于" + request.getConPartyBName() + "签订" + request.getConName());
                workFlowRequest.setWorkflowId(sysConfigService.getConfigByCode("YSJG_CON_CONTRACT_WFID"));
                workFlowRequest.setThirdUserId(thirdUserRequest.getUserId());
                workFlowRequest.setBizId(conId);
                workFlowRequest.setBizType("ConContract");

                if (StrUtil.isNotBlank(contract.getThirdRequestId())) {
                    workFlowRequest.setRequestId(contract.getThirdRequestId());
                }

                List<MainDataRequest> mainData = new ArrayList<>();
                MainDataRequest nrzy = new MainDataRequest();
                nrzy.setFieldName("nrzy");
                nrzy.setFieldValue(request.getConContentSummary());
                mainData.add(nrzy);

                MainDataRequest cpsx = new MainDataRequest();
                cpsx.setFieldName("cpsx");
                cpsx.setFieldValue(request.getConApprovalMatters());
                mainData.add(cpsx);

                MainDataRequest sqr = new MainDataRequest();
                sqr.setFieldName("sqr");//申请人
                sqr.setFieldValue(thirdUserRequest.getUserId());
                mainData.add(sqr);

                MainDataRequest sqbm = new MainDataRequest();
                sqbm.setFieldName("sqbm");//申请部门
                sqbm.setFieldValue(thirdUserRequest.getDepartmentId());
                mainData.add(sqbm);

                MainDataRequest sqdw = new MainDataRequest();
                sqdw.setFieldName("sqdw");//申请单位
                sqdw.setFieldValue(thirdUserRequest.getSubCompanyId());
                mainData.add(sqdw);

                MainDataRequest sqrq = new MainDataRequest();
                sqrq.setFieldName("sqrq");//申请日期
                sqrq.setFieldValue(DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                mainData.add(sqrq);

                //附件
                if (StrUtil.isNotBlank(fileRelationId)) {
                    String rootDirectory = sysConfigService.getConfigByCode("rootDirectory");
                    String archiveDirectory = sysConfigService.getConfigByCode("archiveDirectory");
                    LambdaQueryWrapper<SysFileannex> fileQueryWrapper = new LambdaQueryWrapper<>();
                    fileQueryWrapper.eq(SysFileannex::getFileRelationId, fileRelationId);
                    fileQueryWrapper.eq(SysFileannex::getUploadStatus, 1);
                    List<SysFileannex> fileList = sysFileAnnexService.list(fileQueryWrapper);
                    if (CollectionUtil.isNotEmpty(fileList)) {
                        List<MainDataFileRequest> fileMainDatas = new ArrayList<>();
                        for (SysFileannex sysFileannex : fileList) {
                            String fileDir;
                            if (sysFileannex.getFileLocation() == 1) {
                                fileDir = archiveDirectory;
                            } else {
                                fileDir = rootDirectory;
                            }
                            MainDataFileRequest fileData = new MainDataFileRequest();
                            fileData.setFileName(sysFileannex.getFileName());
                            fileData.setFilePath("base64:" + Base64.encode(new File(fileDir + sysFileannex.getFilePath())));
                            fileMainDatas.add(fileData);
                        }

                        MainDataRequest fj = new MainDataRequest();
                        fj.setFieldName("fj");
                        fj.setFieldValue(fileMainDatas);
                        mainData.add(fj);
                    }

                }

                workFlowRequest.setMainData(mainData);
                CommonResponse<String> workFLowCreateResponse;
                if (StrUtil.isNotBlank(contract.getThirdRequestId())) {
                    //驳回后重新提交流程
                    workFLowCreateResponse = dockFeign.resubmitFLow(workFlowRequest);
                } else {
                    //创建流程
                    workFLowCreateResponse = dockFeign.createWorkFLow(workFlowRequest);
                }
                if (workFLowCreateResponse.isSuccess() && StrUtil.isNotBlank(workFLowCreateResponse.getData())) {
                    contract.setThirdRequestId(workFLowCreateResponse.getData());
                    //默认审批中
                    contract.setThirdStatus("1");
                }
                this.updateById(contract);
            }
        }
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse chekConAmountOver(String pId, BigDecimal amount) {
        Map<String, String> map = new HashMap<>();
        String str = sysConfigService.getConfigByCode("ConAmountWarning");
        if (ValidateUtil.isEmpty(str)) {
            map.put("flag", "false");
            map.put("message", "不存在配置项,请联系管理员!");
            return BaseResponse.ok(map);
        }
        BigDecimal budgetAmount = BigDecimal.ZERO;
        //获取概算金额
        InvBudgetDeclareResponse invBudgetDeclareResponse = (InvBudgetDeclareResponse) invBudgetDeclareService.getInvBudgetDeclareAndDtlByPid(pId).getData();
        if (ValidateUtil.isNotNull(invBudgetDeclareResponse)) {
            budgetAmount = invBudgetDeclareResponse.getBdAuditAmt();
        } else {
            ProApprovalInfoVo proApprovalInfo = (ProApprovalInfoVo) preProjectService.getProApprovalInfo(pId).getData();
            if (ValidateUtil.isNotNull(proApprovalInfo.getPApproveBudget())) {
                budgetAmount = proApprovalInfo.getPApproveBudget();
            } else {
                map.put("flag", "false");
                map.put("message", "未填写概算金额,请联系管理员!");
                return BaseResponse.ok(map);
            }
        }
        if (budgetAmount.compareTo(BigDecimal.ZERO) == 0) {
            map.put("flag", "false");
            map.put("message", "概算金额为0,请重新确认!");
            return BaseResponse.ok(map);
        }
        QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Project_Id", pId);
        wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<ConContract> contractList = baseMapper.selectList(wrapper).stream().collect(Collectors.toList());
        BigDecimal conAmount = BigDecimal.ZERO;
        //sql 获取合同集合中已结算合同

        QueryWrapper<InvSettlementApply> ISAwrapper = new QueryWrapper<>();
        ISAwrapper.eq("P_Id", pId);
        ISAwrapper.ne("Sa_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<InvSettlementApply> invSettlementApplyList = invSettlementApplyMapper.selectList(ISAwrapper).stream().collect(Collectors.toList());

        QueryWrapper<ConEc> CEwrapper = new QueryWrapper<>();
        CEwrapper.eq("P_Id", pId);
        CEwrapper.ne("Ec_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<ConEc> conCeList = conEcMapper.selectList(CEwrapper).stream().collect(Collectors.toList());


        for (ConContract item : contractList) {
            //判断该合同是否有结算
            InvSettlementApply invSettlementApplyResponse = invSettlementApplyList.stream().filter(n -> n.getConId().equals(item.getConId())).sorted(Comparator.comparing(InvSettlementApply::getSaStateTime).reversed()).findFirst().orElse(null);
            if (ValidateUtil.isNotNull(invSettlementApplyResponse)) {
                //读取结算金额
                conAmount = conAmount.add(invSettlementApplyResponse.getSaApprovedAmt());
            } else {
                //没有结算的话，取合同金额+合同下所有变更金额之和
                List<ConEc> conEcResponseList = conCeList.stream().filter(n -> n.getConId().equals(item.getConId())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(conEcResponseList)) {
                    BigDecimal finalMoney = conEcResponseList.stream().map(ConEc::getEcFinalMoney).
                            filter(Objects::nonNull).
                            reduce(BigDecimal.ZERO, NumberUtil::add);
                    conAmount = conAmount.add(finalMoney).add(item.getConMoneyTotal());
                }
            }
        }
        //根据配置项，获取预警比例
        JSONObject json = JSONObject.parseObject(str);
        BigDecimal rate = new BigDecimal(json.getString("rate"));
        BigDecimal totalAmount = (conAmount.add(amount)).divide(budgetAmount, 2);
        BigDecimal resultAmount = budgetAmount.multiply(rate.divide(new BigDecimal(100), 2));
        if (totalAmount.compareTo(resultAmount) > 0) {
            map.put("flag", "false");
            map.put("message", "动态投资额达到概算投资额的" + rate + "%，存在概算突破风险");
            return BaseResponse.ok(map);
        } else {
            map.put("flag", "true");
            map.put("message", "");
            return BaseResponse.ok(map);
        }
    }


    @Override
    public BaseResponse getContractStampDuty(ContractStampDutyModel data) {
        List<ContractStampDutyModel> contractStampDutyModel = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        String str = sysConfigService.getConfigByCode("ContractStampDutyType");
        if (ValidateUtil.isEmpty(str)) {
            map.put("flag", "false");
            map.put("message", "不存在配置项,请联系管理员!");
            return BaseResponse.ok(map);
        }
        JSONObject json = JSONObject.parseObject(str);


        QueryMap<ContractStampDutyModel> queryMap = new QueryMap<>(data);

        queryMap.put("pName").put("conName").put("unitConCode").put("conType").put("conCode").put("signDate").put("conSecondParty").put("conMoney")
                .put("taxableAmount").put("taxRate").put("taxAmount").put("type");
        IPage<ContractStampDutyModel> page = new Page<>(data.getPage(), data.getSize());
        List<ContractStampDutyModel> contractList = baseMapper.getContractStampDuty(page, queryMap.getQuery());


        //集中建设项目
        if ("1".equals(data.getType())) {

            contractStampDutyModel = contractList.stream().map(ndata -> {
                ContractStampDutyModel item = new ContractStampDutyModel();
                BeanUtils.copyProperties(ndata, item);
                if (ValidateUtil.isNotNull(item.getSignDate())) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM");
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = fmt.parse(item.getSignDate());
                        String SignDate = formatter.format(date);
                        item.setSignDate(SignDate);
                    } catch (ParseException e) {
                    }
                }
                if (ValidateUtil.isNotNull(item.getTaxRate())) {

                    item.setTaxRate(item.getTaxRate().multiply(new BigDecimal(100)));
                }
                item.setType("集中建设项目");
                return item;
            }).collect(Collectors.toList());
        } else {
            contractStampDutyModel = contractList.stream().map(ndata -> {
                ContractStampDutyModel item = new ContractStampDutyModel();
                BeanUtils.copyProperties(ndata, item);
                if (ValidateUtil.isNotNull(item.getSignDate())) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM");
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = fmt.parse(item.getSignDate());
                        String SignDate = formatter.format(date);
                        item.setSignDate(SignDate);
                    } catch (ParseException e) {
                    }
                }
                if (ValidateUtil.isNotNull(item.getTaxRate())) {

                    item.setTaxRate(item.getTaxRate().multiply(new BigDecimal(100)));
                }
                //调整合同印花税税目类型
                String conType = json.getString(item.getConType());
                item.setConType(conType);
                item.setType("主体代建、社会代建等其他项目");
                return item;
            }).collect(Collectors.toList());
        }
        return BaseResponse.ok(contractStampDutyModel, Long.valueOf(page.getTotal()).intValue());
    }


    @Override
    public Workbook exportContractStampDuty(ContractStampDutyModel request, String uId) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        QueryMap<ContractStampDutyModel> params = new QueryMap<>(request);
        params.put("pName").put("conName").put("unitConCode").put("conType").put("conCode").put("signDate").put("conSecondParty").put("conMoney")
                .put("taxableAmount").put("taxRate").put("taxAmount").put("type");


//        IPage<ContractStampDutyModel> page = new Page<>(request.getPage(), request.getSize());
//        List<ContractStampDutyModel> datas = baseMapper.getContractStampDuty(page,params.getQuery());

        List<ContractStampDutyModel> datas = baseMapper.getContractStampDutyList(params.getQuery());
        JSONArray array = DictionaryUtils.getTreeList("JPMsoft_HTLX");
        String str = sysConfigService.getConfigByCode("ContractStampDutyType");
        JSONObject json = JSONObject.parseObject(str);
        String[] rowTitle = new String[]{"项目名称", "合同编号", "单位合同编号", "合同类型", "合同名称", "订立日期", "对方单位名称", "合同金额（元）", "应纳税金额（不含税价）", "税率(%)", "印花税税额"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("集中建设项目");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(datas)) {
            for (ContractStampDutyModel model : datas) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(model.getPName());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(model.getConCode());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(model.getUnitConCode());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                try {
                    cell.setCellValue(DictionaryUtils.processDictList(array, model.getConType()));
                } catch (Exception e) {
                    e.printStackTrace();
                    cell.setCellValue("");
                }
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(model.getConName());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = fmt.parse(model.getSignDate());
                    cell.setCellValue(null == model.getSignDate() ? "" : DateFormatUtil.dateToString(date, DateFormatUtil.FORMAT_PATTERN_OF_MONTH_CHINESE));
                } catch (Exception e) {
                    cell.setCellValue("");
                }
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(model.getConSecondParty());
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(null == model.getConMoney() ? "" : model.getConMoney().toString());
                cell.setCellStyle(style);


                cell = row.createCell(8);
                cell.setCellValue(null == model.getTaxableAmount() ? "" : model.getTaxableAmount().toString());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                BigDecimal taxRate = null;
                if (ValidateUtil.isNotNull(model.getTaxRate()))
                    taxRate = model.getTaxRate().multiply(new BigDecimal(100));
                cell.setCellValue(null == taxRate ? "" : taxRate.toString());
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(null == model.getTaxAmount() ? "" : model.getTaxAmount().toString());
                cell.setCellStyle(style);
            }
        }


        String[] rowTitle2 = new String[]{"合同编号", "单位合同编号", "税目", "订立日期", "合同名称", "对方单位名称", "合同金额（元）", "税率(%)", "印花税税额", "项目名称"};

        // 创建一个工作表(sheet)
        Sheet sheet2 = workbook.createSheet("主体代建、社会代建等其他项目");
        for (int i = 0; i < rowTitle2.length; i++) {
            sheet2.setColumnWidth(i, 6000);
        }

        CellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setBorderTop(BorderStyle.MEDIUM);
        style2.setBorderBottom(BorderStyle.MEDIUM);
        style2.setBorderLeft(BorderStyle.MEDIUM);
        style2.setBorderRight(BorderStyle.MEDIUM);

        int rowNum2 = 0;
        Row row2 = sheet2.createRow(rowNum2);
        Cell cell2;
        rowNum2++;
        for (int i = 0; i < rowTitle2.length; i++) {
            cell2 = row2.createCell(i);
            cell2.setCellValue(rowTitle2[i]);
            cell2.setCellStyle(style2);
        }

        if (!CollectionUtils.isEmpty(datas)) {
            for (ContractStampDutyModel model : datas) {
                row2 = sheet2.createRow(rowNum2++);

                cell2 = row2.createCell(0);
                cell2.setCellValue(model.getConCode());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(1);
                cell2.setCellValue(model.getUnitConCode());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(2);
                //调整合同印花税税目类型
                String conType = json.getString(model.getConType());
                cell2.setCellValue(conType);
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(3);
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = fmt.parse(model.getSignDate());
                    cell2.setCellValue(null == model.getSignDate() ? "" : DateFormatUtil.dateToString(date, DateFormatUtil.FORMAT_PATTERN_OF_MONTH_CHINESE));
                } catch (Exception e) {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(4);
                cell2.setCellValue(model.getConName());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(5);
                cell2.setCellValue(model.getConSecondParty());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(6);
                cell2.setCellValue(null == model.getConMoney() ? "" : model.getConMoney().toString());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(7);
                BigDecimal taxRate = null;
                if (ValidateUtil.isNotNull(model.getTaxRate()))
                    taxRate = model.getTaxRate().multiply(new BigDecimal(100));
                cell2.setCellValue(null == taxRate ? "" : taxRate.toString());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(8);
                cell2.setCellValue(null == model.getTaxAmount() ? "" : model.getTaxAmount().toString());
                cell2.setCellStyle(style2);

                cell2 = row2.createCell(9);
                cell2.setCellValue(model.getPName());
                cell2.setCellStyle(style2);
            }
        }


        return workbook;
    }

    @Override
    public BigDecimal getContractMoney(String conId) {
        QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
        wrapper.eq("con_id", conId).or(e -> {
            e.eq("Con_Master_Con_Id", conId)
                    .eq("Con_Approval_State", ApprovalStatusEnum.Already_Over.getIndex())
                    .eq("Con_Sup", "是")
                    .eq("Con_Sup_Is_Change_Money", "是");
        });
        List<ConContract> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            BigDecimal conMoney = data.stream().filter(f -> f.getConId().equals(conId)).findFirst().get().getConMoneyTotal();
            List<ConContract> bcs = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getConMasterConId()) && f.getConMasterConId().equals(conId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(bcs)) {
                for (ConContract e : bcs) {
                    if (ValidateUtil.isNotNull(e.getConSupChangeMonType())) {
                        if (e.getConSupChangeMonType().equals(1))
                            conMoney = MathUtil.decAdd(conMoney, e.getConSupChangeMoney());
                        else
                            conMoney = MathUtil.decSubtract(conMoney, e.getConSupChangeMoney());
                    }
                }
            }
            return conMoney;
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BaseResponse getConPayPartyBPayList(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail("缺少必填参数");
        LambdaQueryWrapper<ConContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConContract::getConId, conId);
        ConContract con = baseMapper.selectOne(queryWrapper);
        List<String> data = new ArrayList<>();
        List<Map<String, Object>> dataMap = new ArrayList<>();
        if (ValidateUtil.isNotNull(con)) {
            data.add(con.getConPartyBId());
            if (ValidateUtil.isNotNull(con.getConConsortium())) {
                if (con.getConConsortium().equals("是")) {
                    data.add(ValidateUtil.isEmpty(con.getConPartyB1()) ? "" : con.getConPartyB1());
                    data.add(ValidateUtil.isEmpty(con.getConPartyB2()) ? "" : con.getConPartyB2());
                    data.add(ValidateUtil.isEmpty(con.getConPartyB3()) ? "" : con.getConPartyB3());
                    data.add(ValidateUtil.isEmpty(con.getConPartyB4()) ? "" : con.getConPartyB4());
                    data.add(ValidateUtil.isEmpty(con.getConPartyB5()) ? "" : con.getConPartyB5());
                }
            }
            List<SysCompany> companyByIds = sysCompanyService.getCompanyByIds(data);

            dataMap = companyByIds.stream().map(e -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", e.getcId());
                map.put("name", e.getcName());
                return map;
            }).collect(Collectors.toList());
        }
        return BaseResponse.ok(dataMap);
    }

    @Override
    public BaseResponse getContractLedgerForWJT(ContractLedgerForWJTParam param) {
        Page<ContractLedgerForWJTVO> contractLedgerForWJTVOPage = baseMapper.getContractLedgerForWJT(param, new Page<>(param.getPage(), param.getSize()));
        List<ContractLedgerForWJTVO> contractVoList = contractLedgerForWJTVOPage.getRecords();
        List<String> contractIdList = contractVoList.stream().map(e -> e.getContractId()).collect(Collectors.toList());
        if (CollUtil.isEmpty(contractIdList)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Map<String, ContractLedgerForWJTVO> contractVoMap = contractVoList.stream().collect(Collectors.toMap(e -> e.getContractId(), e -> e));
        List<ConPay> conPayList = conPayService.list(new LambdaQueryWrapper<ConPay>()
                .select(ConPay::getConId, ConPay::getPayApproval, ConPay::getPayInvoice)
                .eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex())
                .in(CollUtil.isNotEmpty(contractIdList), ConPay::getConId, contractIdList));
        for (ConPay conPay : conPayList) {
            String conId = conPay.getConId();
            if (conId == null) {
                continue;
            }
            BigDecimal payApproval = conPay.getPayApproval();
            BigDecimal payInvoice = conPay.getPayInvoice();
            ContractLedgerForWJTVO contractLedgerForWJTVO = contractVoMap.get(conId);
            if (contractLedgerForWJTVO == null) {
                continue;
            }
            contractLedgerForWJTVO.setTotalPay(NumberUtil.add(contractLedgerForWJTVO.getTotalPay(), payApproval));
            contractLedgerForWJTVO.setTotalInvoice(NumberUtil.add(contractLedgerForWJTVO.getTotalInvoice(), payInvoice));
        }

        Map<String, BigDecimal> contractChangeAmountMap = bgContactOrderService.getContractChangeAmountSum(contractIdList);
        for (ContractLedgerForWJTVO contractLedgerForWJTVO : contractVoList) {
            String contractId = contractLedgerForWJTVO.getContractId();
            BigDecimal contractChangeAmount = contractChangeAmountMap.getOrDefault(contractId, BigDecimal.ZERO);
            contractLedgerForWJTVO.setTotalChange(contractChangeAmount);
            String contractMoneyRate = contractLedgerForWJTVO.getContractMoneyRate();
            if ("费率".equals(contractMoneyRate)) {
                contractLedgerForWJTVO.setContractScale(contractLedgerForWJTVO.getContractMoneyTentative());
            } else {
                contractLedgerForWJTVO.setContractScale(contractLedgerForWJTVO.getContractMoneyTotal());
            }
        }

        return BaseResponse.ok(contractVoList, (int) contractLedgerForWJTVOPage.getTotal());
    }

    @Override
    public BaseResponse updateContractInfoWJTById(ConContractRequest request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        ConContract con = baseMapper.selectById(request.getConId());
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        con.setConDate(request.getConDate());
        con.setConConfirmDate(request.getConConfirmDate());//签订确认时间
        con.setConScan(request.getConScan());//原件扫描件
        baseMapper.updateById(con);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getConScan()), Arrays.asList(request.getConScanIds()), con.getConId());
        return BaseResponse.ok(con.getConId());
    }


    @Override
    public BaseResponse updateContractInfoWJTQD(ConContractRequest request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        ConContract con = baseMapper.selectById(request.getConId());
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        con.setConDate(request.getConDate());
        con.setConConfirmDate(request.getConConfirmDate());//签订确认时间
        con.setConScan(request.getConScan());//原件扫描件
        con.setIsUploaded("01"); //上传附件状态
        baseMapper.updateById(con);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getConScan()), Arrays.asList(request.getConScanIds()), con.getConId());
        return BaseResponse.ok(con.getConId());
    }

    @Override
    public BaseResponse getContractSecurityMoney(String conId) {
        ConContract con = baseMapper.selectById(conId);
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        ContractSecurityMoneyVO csm = new ContractSecurityMoneyVO();
        csm.setConScMoney(con.getConScMoney());
        csm.setPaySecurityThisSum(baseMapper.getContractSecurityMoney(conId));
        return BaseResponse.ok(csm);
    }


    @Override
    public List<ConContract> getConContractpId(String pId) {
        LambdaQueryWrapper<ConContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConContract::getConProjectId, pId);
        return baseMapper.selectList(queryWrapper);
    }

    //尾款
    @Override
    public BaseResponse getBalancePaymentDataList(ProgressDeclarationMatchDTO dto, String uId) {
        String authSql = getAuthSql(uId, null);
        QueryMap<ProgressDeclarationMatchDTO> query = new QueryMap<>(dto);
        query.put("authSql", authSql).put("pName").put("conName").put("conCode").put("conPartyBName").put("conPartyAAgent");
        IPage<ConContractResponse> page = new Page<>(dto.getPage(), dto.getSize());
        List<ConContractResponse> data = baseMapper.getBalancePaymentDataList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    //结算审计
    @Override
    public BaseResponse getTCSettlementAuditDataList(ProgressDeclarationMatchDTO dto, String uId) {
        String authSql = getAuthSql(uId, null);
        QueryMap<ProgressDeclarationMatchDTO> query = new QueryMap<>(dto);
        query.put("authSql", authSql).put("pName").put("conName").put("conCode").put("conPartyBName").put("conPartyAAgent");
        IPage<ConContractResponse> page = new Page<>(dto.getPage(), dto.getSize());
        List<ConContractResponse> data = baseMapper.getTCSettlementAuditDataList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    //初步审计
    @Override
    public BaseResponse getPreliminaryAuditDataList(ProgressDeclarationMatchDTO dto, String uId) {
        String authSql = getAuthSql(uId, null);
        QueryMap<ProgressDeclarationMatchDTO> query = new QueryMap<>(dto);
        query.put("authSql", authSql).put("pName").put("conName").put("conCode").put("conPartyBName").put("conPartyAAgent");
        IPage<ConContractResponse> page = new Page<>(dto.getPage(), dto.getSize());
        List<ConContractResponse> data = baseMapper.getPreliminaryAuditDataList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    //竣工决算
    @Override
    public BaseResponse getFinalAccountDataList(ProgressDeclarationMatchDTO dto, String uId) {
        String authSql = getAuthSql(uId, null);
        QueryMap<ProgressDeclarationMatchDTO> query = new QueryMap<>(dto);
        query.put("authSql", authSql).put("pName").put("conName").put("conCode").put("conPartyBName").put("conPartyAAgent").put("faApprovalUnitName");
        IPage<ConContractResponse> page = new Page<>(dto.getPage(), dto.getSize());
        List<ConContractResponse> data = baseMapper.getFinalAccountDataList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    //查询合同列表关联计划，只查询建立了某种计划的合同
    @Override
    public BaseResponse getBalancePaymentPlanDataList(ProgressDeclarationMatchDTO dto, String uId) {
        String authSql = getAuthSql(uId, null);
        QueryMap<ProgressDeclarationMatchDTO> query = new QueryMap<>(dto);
        query.put("authSql", authSql).put("pName").put("pId").put("conName").put("conCode").put("conPartyBName").put("conPartyAAgent").put("pddPayType");
        IPage<ConContractResponse> page = new Page<>(dto.getPage(), dto.getSize());
        List<ConContractResponse> data = baseMapper.getBalancePaymentPlanDataList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public Boolean getCompleteById(String conId) {
        if (baseMapper.getgetCompleteById(conId) > 0) {
            return true;
        }
        return false;
    }


    @Override
    public BaseResponse getConpayByIds(ConPayRequest request) {
        ConPayResponse data = new ConPayResponse();
        if (ValidateUtil.isNotEmpty(request.getConId())) {
            data.setConId(request.getConId());
            LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayState, 7);
            List<ConPay> conPayList = conPayService.list(queryWrapper);

            // 初始化所有字段为0
            BigDecimal payTotalLastSum = BigDecimal.ZERO; // 已支付
            BigDecimal payFarmersTotalSum = BigDecimal.ZERO; // 工人工资累计已支付金额
            Integer payNumberSum = 0; // 付款总期数
            BigDecimal payWorkAmountTotalSum = BigDecimal.ZERO; // 工程款累计已支付金额
            BigDecimal payDeductScThisSum = BigDecimal.ZERO; // 安全文明措施费累计支付金额
            BigDecimal payChangePayTotalThisSum = BigDecimal.ZERO; // 变更签证累计已支付金额
            BigDecimal payApplyTotal = BigDecimal.ZERO; // 本次
            Float payTotalLastP = 0.0f; // 已支付占合同价比例
            Float payProportion = 0.0f; // 签证金额占暂列金比例
            BigDecimal payAomount = BigDecimal.ZERO;
            BigDecimal payFarmersExpenditure = BigDecimal.ZERO;

            BigDecimal payFarmersTotal = BigDecimal.ZERO;
//            BigDecimal payWorkAmountTotal = BigDecimal.ZERO;


            for (ConPay conPay : conPayList) {
                payAomount = conPay.getPayAomount();
                payFarmersExpenditure = conPay.getPayFarmersExpenditure();
                Integer payNumber = conPay.getPayNumber();
                if (conPay.getPayApply() != null) {
                    payApplyTotal = conPay.getPayApply();
                }
                if (conPay.getPayFarmersTotal() != null) {
                    payFarmersTotal = conPay.getPayFarmersTotal();
                }

                if (payAomount != null) {
                    //payTotalLastSum = payTotalLastSum.add(payAomount); // 累加工程款金额
                    payWorkAmountTotalSum = payWorkAmountTotalSum.add(payAomount); // 累加工程款累计已支付金额
                }
                // 已支付
                if (conPay.getPayTotalLast() != null) {
                    payTotalLastSum = payTotalLastSum.add(conPay.getPayApply());// 累加本次已支付金额
                }

                if (payFarmersExpenditure != null) {
                    payFarmersTotalSum = payFarmersTotalSum.add(payFarmersExpenditure); // 累加工人工资金额
                }

                if (payNumber != null) {
                    payNumberSum += payNumber; // 累加付款总期数
                }
            }

            // 设置返回值
            data.setPayTotalLast(payTotalLastSum);
            data.setPayFarmersTotal(payFarmersTotalSum);
            data.setPayNumberSum(payNumberSum);
            data.setPayWorkAmountTotal(payWorkAmountTotalSum);
            data.setPayApply(payApplyTotal);
            data.setPayAomount(payAomount);
            data.setPayFarmersExpenditure(payFarmersExpenditure);
            data.setPayFarmersTotal(payFarmersTotal);

            // 计算比例
            if (payTotalLastSum.compareTo(BigDecimal.ZERO) != 0 && conPayList.size() > 0) {
                BigDecimal payApply = conPayList.get(0).getPayApply();
                if (payApply != null && payApply.compareTo(BigDecimal.ZERO) != 0) {
                    payTotalLastP = payTotalLastSum.divide(payApply, 2, RoundingMode.HALF_UP).floatValue();
                }
            }
            data.setPayTotalP(payTotalLastP);

            String payType = request.getPayType();
            if (ValidateUtil.isNotEmpty(payType)) {
                if (payType.equals("09")) {
                    // 安全文明措施费
                    LambdaQueryWrapper<ConPay> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayType, "09").eq(ConPay::getPayState, 7);
                    List<ConPay> conPays = conPayService.list(wrapper);

                    for (ConPay conPay : conPays) {
                        BigDecimal payDeductScThis = conPay.getPayDeductScThis();
                        if (payDeductScThis != null) {
                            payDeductScThisSum = payDeductScThisSum.add(payDeductScThis);
                        }
                    }

                    data.setPayDeductScThis(payDeductScThisSum);
                } else if (payType.equals("08")) {
                    // 变更签证  暂列金
                    LambdaQueryWrapper<ConPay> query = new LambdaQueryWrapper<>();
                    query.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayType, "08").eq(ConPay::getPayState, 7);
                    List<ConPay> list = conPayService.list(query);

                    for (ConPay conPay : list) {
                        BigDecimal payChangePayTotalThis = conPay.getPayChangePayTotalThis();
                        if (payChangePayTotalThis != null) {
                            payChangePayTotalThisSum = payChangePayTotalThisSum.add(payChangePayTotalThis);
                        }
                    }

                    data.setPayChangePayTotalThis(payChangePayTotalThisSum);

                    ConContract conContract = getById(data.getConId());
                    BigDecimal conMoneyTentative = conContract != null ? conContract.getConMoneyTentative() : BigDecimal.ZERO;
                    /**
                     *  此判断是根据变更签证累计支付金额不能超过合同暂列金
                     */
/*                    if (conMoneyTentative != null && conMoneyTentative.compareTo(payChangePayTotalThisSum) < 0) {
                        data.setConForm("false");
                    }*/

                    if (payChangePayTotalThisSum.compareTo(BigDecimal.ZERO) != 0 && conMoneyTentative.compareTo(BigDecimal.ZERO) != 0) {
                        payProportion = payChangePayTotalThisSum.divide(conMoneyTentative, 2, RoundingMode.HALF_UP).floatValue();
                    }

                    data.setPayProportion(payProportion);
                }
            }
        }

        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConpayByIdsGDGY(ConPayRequest request) {
        ConPayResponse data = new ConPayResponse();
        if (ValidateUtil.isNotEmpty(request.getConId())) {
            data.setConId(request.getConId());
            LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex());
            List<ConPay> conPayList = conPayService.list(queryWrapper);

            // 初始化所有字段为0
            BigDecimal payTotalLastSum = BigDecimal.ZERO; // 已支付
            BigDecimal payFarmersTotalSum = BigDecimal.ZERO; // 工人工资累计已支付金额
            Integer payNumberSum = 0; // 付款总期数
            BigDecimal payWorkAmountTotalSum = BigDecimal.ZERO; // 工程款累计已支付金额
            BigDecimal payDeductScThisSum = BigDecimal.ZERO; // 安全文明措施费累计支付金额
            BigDecimal payChangePayTotalThisSum = BigDecimal.ZERO; // 变更签证累计已支付金额
            BigDecimal payApplyTotal = BigDecimal.ZERO; // 本次
            Float payTotalLastP = 0.0f; // 已支付占合同价比例
            Float payProportion = 0.0f; // 签证金额占暂列金比例
            BigDecimal payAomount = BigDecimal.ZERO;
            BigDecimal payFarmersExpenditure = BigDecimal.ZERO;

            BigDecimal payFarmersTotal = BigDecimal.ZERO;
//            BigDecimal payWorkAmountTotal = BigDecimal.ZERO;
            // 退质保金金额
            BigDecimal payRefundSum = BigDecimal.ZERO;

            for (ConPay conPay : conPayList) {
                payAomount = conPay.getPayAomount();
                payFarmersExpenditure = conPay.getPayFarmersExpenditure();
                Integer payNumber = conPay.getPayNumber();

                if (conPay.getPayFarmersTotal() != null) {
                    payFarmersTotal = conPay.getPayFarmersTotal();
                }

                if (payFarmersExpenditure != null) {
                    payFarmersTotalSum = payFarmersTotalSum.add(payFarmersExpenditure); // 累加工人工资金额
                }

                if (payNumber != null) {
                    payNumberSum += payNumber; // 累加付款总期数
                }
                // 累计质保金
                if ("04".equals(conPay.getPayType())) {
                    // 把本条的 payApply 累加到 payRefund
                    payRefundSum = payRefundSum.add(conPay.getPayApply());
                    continue;
                }

                if (conPay.getPayApply() != null) {
                    payApplyTotal = conPay.getPayApply();
                }
                if (payAomount != null) {
                    //payTotalLastSum = payTotalLastSum.add(payAomount); // 累加工程款金额
                    payWorkAmountTotalSum = payWorkAmountTotalSum.add(payAomount); // 累加工程款累计已支付金额
                }
                // 已支付
                if (conPay.getPayTotalLast() != null) {
                    payTotalLastSum = payTotalLastSum.add(conPay.getPayApply());// 累加本次已支付金额
                }

            }

            // 设置返回值
            data.setPayTotalLast(payTotalLastSum);
            data.setPayFarmersTotal(payFarmersTotalSum);
            data.setPayNumberSum(payNumberSum);
            data.setPayWorkAmountTotal(payWorkAmountTotalSum);
            data.setPayApply(payApplyTotal);
            data.setPayAomount(payAomount);
            data.setPayFarmersExpenditure(payFarmersExpenditure);
            data.setPayFarmersTotal(payFarmersTotal);
            data.setPayRefundSum(payRefundSum);

            // 计算比例
            if (payTotalLastSum.compareTo(BigDecimal.ZERO) != 0 && !conPayList.isEmpty()) {
                BigDecimal payApply = conPayList.get(0).getPayApply();
                if (payApply != null && payApply.compareTo(BigDecimal.ZERO) != 0) {
                    payTotalLastP = payTotalLastSum.divide(payApply, 2, RoundingMode.HALF_UP).floatValue();
                }
            }
            data.setPayTotalP(payTotalLastP);

            String payType = request.getPayType();
            if (ValidateUtil.isNotEmpty(payType)) {
                if ("09".equals(payType)) {
                    // 安全文明措施费
                    LambdaQueryWrapper<ConPay> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayType, "09").eq(ConPay::getPayState, 7);
                    List<ConPay> conPays = conPayService.list(wrapper);

                    for (ConPay conPay : conPays) {
                        BigDecimal payDeductScThis = conPay.getPayDeductScThis();
                        if (payDeductScThis != null) {
                            payDeductScThisSum = payDeductScThisSum.add(payDeductScThis);
                        }
                    }

                    data.setPayDeductScThis(payDeductScThisSum);
                } else if ("08".equals(payType)) {
                    // 变更签证  暂列金
                    LambdaQueryWrapper<ConPay> query = new LambdaQueryWrapper<>();
                    query.eq(ConPay::getConId, data.getConId()).eq(ConPay::getPayType, "08").eq(ConPay::getPayState, 7);
                    List<ConPay> list = conPayService.list(query);

                    for (ConPay conPay : list) {
                        BigDecimal payChangePayTotalThis = conPay.getPayChangePayTotalThis();
                        if (payChangePayTotalThis != null) {
                            payChangePayTotalThisSum = payChangePayTotalThisSum.add(payChangePayTotalThis);
                        }
                    }

                    data.setPayChangePayTotalThis(payChangePayTotalThisSum);

                    ConContract conContract = getById(data.getConId());
                    BigDecimal conMoneyTentative = conContract != null ? conContract.getConMoneyTentative() : BigDecimal.ZERO;
                    /**
                     *  此判断是根据变更签证累计支付金额不能超过合同暂列金
                     */
/*                    if (conMoneyTentative != null && conMoneyTentative.compareTo(payChangePayTotalThisSum) < 0) {
                        data.setConForm("false");
                    }*/

                    if (payChangePayTotalThisSum.compareTo(BigDecimal.ZERO) != 0 && conMoneyTentative.compareTo(BigDecimal.ZERO) != 0) {
                        payProportion = payChangePayTotalThisSum.divide(conMoneyTentative, 2, RoundingMode.HALF_UP).floatValue();
                    }

                    data.setPayProportion(payProportion);
                }
            }
        }

        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getContractListNew(ConContractRequestNew request, String uId) {
        String authSql = getAuthSql(uId, request.getConCategory());
        IPage<ConContractRequestNew> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.getContractListNew(page, request, authSql);
        BigDecimal totalMoney = baseMapper.getTotalMoney(request, authSql);
        ConContractResponseNew dataWithMoney = new ConContractResponseNew();
        if (data != null) {
            dataWithMoney.setData(data);
        }
        dataWithMoney.setTotalMoney(totalMoney);
        return BaseResponse.ok(dataWithMoney, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMainContractList(ConContractRequest request, String uId) {
        Integer page = request.getPage();
        Integer size = request.getSize();
        request.setPage(1);
        request.setSize(9999);
        BaseResponse baseResponse = getList(request, getAuthSql(uId, request.getConCategory()));
        List<ConContractResponse> data = (List<ConContractResponse>) baseResponse.getData();
        // 使用 Iterator 安全地移除元素
        Iterator<ConContractResponse> iterator = data.iterator();
        while (iterator.hasNext()) {
            ConContractResponse c = iterator.next();
            if (Arrays.asList("0", "1", "2", "3").contains(c.getConCategory()) && "是".equals(c.getConSup())) {
                iterator.remove();
            }
        }
        //手动对结果分页
        List<ConContractResponse> pageData = data.stream().skip((page - 1) * size).limit(size).collect(Collectors.toList());


        //获取主合同id
        List<String> ids=pageData.stream().map(ConContractResponse::getConId).collect(Collectors.toList());
        List<ConContract> conContracts = new ArrayList<>();

        if (!ids.isEmpty()) {
            LambdaQueryWrapper<ConContract> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(ConContract::getConMasterConId, ids);
            //查询主合同的补充合同
            conContracts = baseMapper.selectList(queryWrapper);
        }


        for (ConContractResponse item : pageData) {
            List<ConContract> contract = conContracts.stream().filter(obj -> obj.getConMasterConId().equals(item.getConId())).collect(Collectors.toList());

            if(ValidateUtil.isNotEmpty(contract)){
                for (ConContract i : contract) {
                    // 修复空指针异常：先检查 getConSupChangeMonType() 是否为 null
                    if(i.getConSupChangeMonType() != null && 1==(i.getConSupChangeMonType()))
                        item.setConMoneyTotal(Optional.ofNullable(item.getConMoneyTotal()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(i.getConSupChangeMoney()).orElse(BigDecimal.ZERO)));
                    else if(i.getConSupChangeMonType() != null && 0==(i.getConSupChangeMonType())){
                        item.setConMoneyTotal(Optional.ofNullable(item.getConMoneyTotal()).orElse(BigDecimal.ZERO).subtract(Optional.ofNullable(i.getConSupChangeMoney()).orElse(BigDecimal.ZERO)));
                    }
                }
            }
        }
        return BaseResponse.ok(pageData,data.size());
    }

    @Override
    public List<ConContractResponse> getContractListByName(Set<String> request, String uid) {
        if (request == null || request.isEmpty()) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<ConContract> cWrapper = new LambdaQueryWrapper<>();
        cWrapper.in(ConContract::getConName, request);
        List<ConContract> contracts = list(cWrapper);

        List<ConContractResponse> responseList = contracts.stream()
                .map(contract -> {
                    ConContractResponse response = new ConContractResponse();
                    BeanUtils.copyProperties(contract, response); // 注意参数顺序：目标在前
                    return response;
                })
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public BaseResponse getInvestmentControlLedgerHB(ConContractRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getpId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 获取合同概算申报的列表详情信息
        List<InvestmentControlLedgerHBModel> budgetDeclareDtlList = invBudgetDeclareDtlMapper.getInvBudgetDeclareDtlDetailsByPid(request.getpId());
        if (ValidateUtil.isEmpty(budgetDeclareDtlList)) {
            return BaseResponse.fail("请先配置项目概算");
        }
        // 合同id
        List<String> conIds = budgetDeclareDtlList.stream()
                .map(InvestmentControlLedgerHBModel::getConId)
                .filter(Objects::nonNull)
                .filter(id -> !id.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
        // 获取报备变更信息
        List<ConEcResponse> conEcList = conEcMapper.getReportingChangesByPidHB(conIds);
        Map<String, BigDecimal> conEcMap = conEcList.stream()
                .collect(Collectors.toMap(ConEcResponse::getConId, ConEcResponse::getEcApplyMoney, (key1, key2) -> key2));
        // 获取确定（备案）变更信息
        List<EngineeringKeepRecord> engineeringKeepRecordList = conEcMapper.getDetermineChangesByPidHB(conIds);
        Map<String, BigDecimal> engineeringKeepRecordMap = engineeringKeepRecordList.stream()
                .collect(Collectors.toMap(EngineeringKeepRecord::getConId, EngineeringKeepRecord::getEkrSdje, (key1, key2) -> key2));
        // 获取累计产值
        List<InvCompleteMonth> completeMonthList = invCompleteMonthMapper.getInvestmentCumulativeByPidHB(conIds);
        Map<String, BigDecimal> completeMonthMap = completeMonthList.stream()
                .collect(Collectors.toMap(InvCompleteMonth::getConId, InvCompleteMonth::getIcmMonthlyInvestment, (key1, key2) -> key2));
        // 累计付款金额
        List<ConPay> conPayList = conPayService.getConPayListByPidHB(conIds);
        Map<String, BigDecimal> conPayMap = conPayList.stream()
                .collect(Collectors.toMap(ConPay::getConId, ConPay::getPayApply, (key1, key2) -> key2));
        // 处理最终赋值
        budgetDeclareDtlList.forEach(dtl -> {
            String conId = dtl.getConId();
            // 获取报备变更信息
            BigDecimal ecApplyMoney = conEcMap.getOrDefault(conId, BigDecimal.ZERO);
            // 获取确定（备案）变更信息
            BigDecimal ekrSdje = engineeringKeepRecordMap.getOrDefault(conId, BigDecimal.ZERO);
            // 获取累计产值
            BigDecimal icmMonthlyInvestment = completeMonthMap.getOrDefault(conId, BigDecimal.ZERO);
            // 获取累计付款金额
            BigDecimal payApply = conPayMap.getOrDefault(conId, BigDecimal.ZERO);
            // 赋值
            dtl.setEcApplyMoney(ecApplyMoney);
            dtl.setEkrSdje(ekrSdje);
            dtl.setChangeCumulative(dtl.getEcApplyMoney().add(dtl.getEkrSdje()));
            dtl.setChangeConCumulative(dtl.getChangeCumulative().add(dtl.getConMoneyTotal() != null ? dtl.getConMoneyTotal() : BigDecimal.ZERO));
            // if (dtl.getBddTotal().compareTo(BigDecimal.ZERO) == 0) {
            //     dtl.setChangeConRate(BigDecimal.ZERO);
            // } else {
            //     dtl.setChangeConRate(dtl.getChangeConCumulative().divide(dtl.getBddTotal(), 2, RoundingMode.HALF_UP));
            // }
            dtl.setInvestment(icmMonthlyInvestment);
            dtl.setPayApply(payApply);
            // if (dtl.getChangeConCumulative().compareTo(BigDecimal.ZERO) == 0) {
            //     dtl.setPayRate(BigDecimal.ZERO);
            // } else {
            //     dtl.setPayRate(dtl.getPayApply().divide(dtl.getChangeConCumulative(), 2, RoundingMode.HALF_UP));
            // }
        });
        return BaseResponse.ok(budgetDeclareDtlList);
    }

    @Override
    public BaseResponse updateContractById(ConContractRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getConId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConContract entity = new ConContract();
        BeanUtils.copyProperties(request, entity);
        if (this.updateById(entity)) {
            return BaseResponse.ok(entity.getConId());
        } else {
            return BaseResponse.fail(ResMsg.actError);
        }
    }

    @Override
    public List<ConContractResponse> exportContractFiles(ConContractRequest request) {
        if (ValidateUtil.isNull(request)) {
            return Collections.emptyList();
        }
        QueryMap<ConContractRequest> params = assembleScreen(" where 1=1 ", request);
        IPage<ConContractResponse> page = new Page<>(1, Long.MAX_VALUE);
        return baseMapper.getContractList(page, params.getQuery());
    }

    @Override
    public BaseResponse getContractStatistics(ConContractRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Map<String, Object> statistics = baseMapper.getContractStatistics(request);
        return BaseResponse.ok(statistics);
    }

    @Override
    public BaseResponse getFinishedContractMoneyByCompanyIds(List<String> companyIds, String teId) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<String> validCompanyIds = companyIds.stream()
                .filter(ValidateUtil::isNotEmpty)
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(validCompanyIds)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        BigDecimal totalMoney = baseMapper.getFinishedContractMoneyByCompanyIds(validCompanyIds, teId);
        return BaseResponse.ok(totalMoney == null ? BigDecimal.ZERO : totalMoney);
    }

    /**
     * 同步合同乙方单位到项目参建单位
     *
     * @param conId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToParticipantUnit(String conId, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        ConContract con = this.getById(conId);
        if (ValidateUtil.isNull(con)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNull(con.getConApprovalState()) || !con.getConApprovalState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        if (ValidateUtil.isEmpty(con.getConProjectId()) || ValidateUtil.isEmpty(con.getConPartyBId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 读取映射配置
        String config = sysConfigService.getConfigByCode("ContractUnitMapping");
        if (ValidateUtil.isEmpty(config)) {
            return BaseResponse.fail();
        }

        // 解析配置
        List<Map<String, Object>> list = (List<Map<String, Object>>) (List<?>) JSONUtil.toList(config, Map.class);
        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.fail();
        }

        // key=合同类型(conType), value=单位类型(cType)
        Map<String, String> conTypeToCType = new HashMap<>();

        // “其他单位”默认类型：取 conTypes 为空的那条配置
        String defaultOtherCType = null;

        for (Map<String, Object> item : list) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            Object cTypeObj = item.get("cType");
            String cTypeText = cTypeObj == null ? null : cTypeObj.toString().trim();

            Object conTypesObj = item.get("conTypes");
            String conTypesText = conTypesObj == null ? null : conTypesObj.toString().trim();

            // 兼容旧字段名（防止已有历史配置）
            if (ValidateUtil.isEmpty(cTypeText)) {
                Object oldCTypesObj = item.get("cTypes");
                cTypeText = oldCTypesObj == null ? null : oldCTypesObj.toString().trim();
            }
            if (ValidateUtil.isEmpty(conTypesText)) {
                Object oldconTypeObj = item.get("conType");
                conTypesText = oldconTypeObj == null ? null : oldconTypeObj.toString().trim();
            }

            if (ValidateUtil.isEmpty(cTypeText)) {
                continue;
            }

            // cType按一个值使用（如果写了逗号，取第一个有效值）
            String cType = Arrays.stream(cTypeText.split(","))
                    .map(String::trim)
                    .filter(s -> !ValidateUtil.isEmpty(s))
                    .findFirst()
                    .orElse(null);

            if (ValidateUtil.isEmpty(cType)) {
                continue;
            }

            // “其他单位”默认类型
            if ("other".equalsIgnoreCase(conTypesText) || ValidateUtil.isEmpty(conTypesText)) {
                defaultOtherCType = cType;
                continue;
            }

            List<String> conTypeList = Arrays.stream(conTypesText.split(","))
                    .map(String::trim)
                    .filter(s -> !ValidateUtil.isEmpty(s))
                    .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(conTypeList)) {
                defaultOtherCType = cType;
                continue;
            }

            for (String conType : conTypeList) {
                conTypeToCType.put(conType, cType);
            }
        }

        // 当前合同类型命中则用映射，未命中则用“其他单位”
        String conType = ValidateUtil.isEmpty(con.getConType()) ? "" : con.getConType().trim();
        String targetCType = conTypeToCType.get(conType);
        if (ValidateUtil.isEmpty(targetCType)) {
            targetCType = defaultOtherCType;
        }

        // 没有可用类型
        if (ValidateUtil.isEmpty(targetCType)) {
            return BaseResponse.fail();
        }

        Long existCount = sysParticipantUnitMapper.selectCount(
                new LambdaQueryWrapper<SysParticipantUnit>()
                        .eq(SysParticipantUnit::getPId, con.getConProjectId())
                        .eq(SysParticipantUnit::getCId, con.getConPartyBId())
                        .eq(SysParticipantUnit::getCType, targetCType)
        );
        if (ValidateUtil.isNotNull(existCount) && existCount > 0) {
            return BaseResponse.ok();
        }

        // 写入项目参建单位
        SysParticipantUnit unit = new SysParticipantUnit();
        unit.setPuId(GenerateUUID.getUUID());
        unit.setPId(con.getConProjectId());
        unit.setCId(con.getConPartyBId());
        unit.setCType(targetCType);
        unit.setPuCreateTime(new Date());
        if (ValidateUtil.isNotNull(user)) {
            unit.setPuCreateUid(user.getId());
            unit.setPuCreateUname(user.getName());
        }

        sysParticipantUnitMapper.insert(unit);
        return BaseResponse.ok(unit.getPuId());
    }

    /**
     * 按合同列表权限及筛选条件获取可用的分类字段和值。
     *
     * @param request 合同列表筛选条件
     * @param uId 当前用户ID
     * @return 分类字段及其可选值
     */
    @Override
    public BaseResponse getContractListClassInfo(ConContractRequest request, String uId) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ConContractRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            QueryMap<ConContractRequest> params = assembleScreen(getAuthSql(uId, request.getConCategory()), request);
            List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, params.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getContractListClassInfo error", e);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }
}
