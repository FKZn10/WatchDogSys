package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ContractPaymentPlanTemplateMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplate;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplateDl;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocationDtl;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import com.jpmsoft.projectProducer.service.contract.ContractPaymentPlanTemplateDlService;
import com.jpmsoft.projectProducer.service.contract.ContractPaymentPlanTemplateService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAllocationDtlService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.SystemParameterService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import dm.jdbc.filter.stat.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContractPaymentPlanTemplateServiceImpl extends ServiceImpl<ContractPaymentPlanTemplateMapper, ContractPaymentPlanTemplate> implements ContractPaymentPlanTemplateService {

    @Autowired
    private ContractPaymentPlanTemplateDlService contractPaymentPlanTemplateDlService;


    @Autowired
    private ISysConfigService iSysConfigService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private IFundAllocationDtlService iFundAllocationDtlService;

    @Override
    public BaseResponse getContractTemplate(ContractResultVo contractResultVo) {

        LambdaQueryWrapper<ContractPaymentPlanTemplate> queryWrapper = new LambdaQueryWrapper<>();
        if (!ValidateUtil.isNull(contractResultVo)) {
            if (ValidateUtil.isNotEmpty(contractResultVo.getContractTypeQuery())) {
                queryWrapper.eq(ContractPaymentPlanTemplate::getContractType, contractResultVo.getContractTypeQuery());
            }
        }
        IPage<ContractPaymentPlanTemplate> tes = baseMapper.selectPage(new Page<>(contractResultVo.getPage(), contractResultVo.getSize()), queryWrapper);

        List<String> collect = tes.getRecords().stream().map(e -> e.getId()).collect(Collectors.toList());
        if (ValidateUtil.isEmpty(collect)) {
            return BaseResponse.ok(tes.getRecords(), (int) tes.getTotal());
        }
        List<ContractPaymentPlanTemplateDl> tedls = contractPaymentPlanTemplateDlService.getContrActDataDl(collect);
        List<ContractResultVo> combinedResults = new ArrayList<>();
        for (ContractPaymentPlanTemplate template : tes.getRecords()) {
            List<ContractPaymentPlanTemplateDl> matchedTedls = tedls.stream()
                    .filter(dl -> dl.getTemplateId().equals(template.getId()))
                    .collect(Collectors.toList());
            ContractResultVo combinedResult = new ContractResultVo();
            combinedResult.setId(template.getId());
            combinedResult.setContractName(template.getContractName());
            combinedResult.setContractType(template.getContractType());
            combinedResult.setContractAmountBelow(template.getContractAmountBelow());
            combinedResult.setContractAmountUpper(template.getContractAmountUpper());
            combinedResult.setDataDl(matchedTedls);
            combinedResults.add(combinedResult);
        }
        return BaseResponse.ok(combinedResults, (int) tes.getTotal());
    }

    @Override
    public BaseResponse saveContractPaymentPlan(ContractResultVo contractResultVo) {
        if (ValidateUtil.isNull(contractResultVo)) {
            return BaseResponse.fail("缺少请求参数！");
        }

        if (ValidateUtil.isEmpty(contractResultVo.getContractType())) {
            return BaseResponse.fail("合同类型不能为空！");
        }

        ContractPaymentPlanTemplate con = new ContractPaymentPlanTemplate();
        BeanUtils.copyProperties(contractResultVo, con);
        con.setId(GenerateUUID.getUUID());
        con.setCreateTime(new Date());
        con.setUpdateTime(new Date());

        baseMapper.insert(con);

        return BaseResponse.ok();
    }

    //删除合同计划
    @Override
    public BaseResponse delContractTemplate(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail("合同计划id不能为空!");
        }
        baseMapper.deleteById(id);
        contractPaymentPlanTemplateDlService.delContractTemplateDl(id);

        return BaseResponse.ok();

    }

    //修改合同计划
    @Override
    public BaseResponse updateContractTemplate(ContractResultVo contractResultVo) {
        if (ValidateUtil.isEmpty(contractResultVo.getId())) {
            return BaseResponse.fail("合同计划id不能为空!");
        }
        ContractPaymentPlanTemplate con = new ContractPaymentPlanTemplate();
        BeanUtils.copyProperties(contractResultVo, con);
        con.setUpdateTime(new Date());
        baseMapper.updateById(con);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getContrcatDataList() {
        String jsonString = iSysConfigService.getConfigByCode("Contrcat_name_Code");
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Map<String, String>> list = JacksonUtils.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
        });

        Map<String, String> contractsMap = new HashMap<>();

        for (Map<String, String> contract : list) {
            contractsMap.put(contract.get("id"), contract.get("value"));
        }
        return BaseResponse.ok(contractsMap);

    }


    //支付条件类型
    @Override
    public BaseResponse getPaymentConditionType() {
        String jsonString = iSysConfigService.getConfigByCode("Contrcat_Payment_Type");

        JSONArray jsonArray = new JSONArray(jsonString);
        List<Map<String, String>> list = JacksonUtils.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
        });

        Map<String, String> contractsMap = new HashMap<>();

        for (Map<String, String> contract : list) {
            contractsMap.put(contract.get("id"), contract.get("value"));
        }
        return BaseResponse.ok(contractsMap);
    }

    @Override
    public BaseResponse getPaymentMethod() {
        String jsonString = iSysConfigService.getConfigByCode("Contrcat_Payment_Method");
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Map<String, String>> list = JacksonUtils.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
        });

        Map<String, String> contractsMap = new HashMap<>();

        for (Map<String, String> contract : list) {
            contractsMap.put(contract.get("id"), contract.get("value"));
        }
        return BaseResponse.ok(contractsMap);
    }

    @Override
    public BaseResponse getPaymentBasis() {
        String jsonString = iSysConfigService.getConfigByCode("Contrcat_Payment_Basis");
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Map<String, String>> list = JacksonUtils.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
        });

        Map<String, String> contractsMap = new HashMap<>();

        for (Map<String, String> contract : list) {
            contractsMap.put(contract.get("id"), contract.get("value"));
        }
        return BaseResponse.ok(contractsMap);
    }

    //查询
    @Override
    public BaseResponse getContrActDataList(ContractResultVo contractResultVo) {
        LambdaQueryWrapper<ContractPaymentPlanTemplate> queryWrapper = new LambdaQueryWrapper<>();
        if (ValidateUtil.isNotEmpty(contractResultVo.getContractType())) {
            queryWrapper.eq(ContractPaymentPlanTemplate::getContractType, contractResultVo.getContractType());
        }
        LambdaQueryWrapper<ContractPaymentPlanTemplate> con = queryWrapper.eq(ContractPaymentPlanTemplate::getContractAmountBelow, contractResultVo.getContrActAmt()).or().eq(ContractPaymentPlanTemplate::getContractAmountUpper, contractResultVo.getContrActAmt());
        List<ContractPaymentPlanTemplate> contractPaymentPlanTemplates = baseMapper.selectList(con);
        return BaseResponse.ok(contractPaymentPlanTemplates, contractPaymentPlanTemplates.size());
    }

    @Override
    public BaseResponse saveContractPaymentPlanBatch(List<ContractResultVo> list) {
        List<ContractPaymentPlanTemplate> lateList = new ArrayList<>();
        for (ContractResultVo contractResultVo : list) {
            if (ValidateUtil.isNotEmpty(contractResultVo.getContractType())) {
                return new BaseResponse("合同类型缺少!");
            }
            ContractPaymentPlanTemplate late = new ContractPaymentPlanTemplate();
            BeanUtils.copyProperties(contractResultVo, late);
            late.setId(GenerateUUID.getUUID());
            late.setCreateTime(new Date());
            late.setUpdateTime(new Date());
            lateList.add(late);
        }
        saveBatch(lateList);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPayableConDataList(ContractManageVo contractManageVo) {
        if (contractManageVo.getConAmts() != null && contractManageVo.getConAmts().size() > 0) {
            if (contractManageVo.getConAmts().get(0) != null && !contractManageVo.getConAmts().get(0).equals("")) {
                contractManageVo.setConAmtStr(new BigDecimal(contractManageVo.getConAmts().get(0)));
            }
            if (contractManageVo.getConAmts().get(1) != null && !contractManageVo.getConAmts().get(1).equals("")) {
                contractManageVo.setConAmtEnd(new BigDecimal(contractManageVo.getConAmts().get(1)));
            }
        }
        if (contractManageVo.getConSupplementAmts() != null && contractManageVo.getConSupplementAmts().size() > 0) {
            if (contractManageVo.getConSupplementAmts().get(0) != null && !contractManageVo.getConSupplementAmts().get(0).equals("")) {
                contractManageVo.setConSupplementAmtStr(new BigDecimal(contractManageVo.getConSupplementAmts().get(0)));
            }
            if (contractManageVo.getConSupplementAmts().get(1) != null && !contractManageVo.getConSupplementAmts().get(1).equals("")) {
                contractManageVo.setConSupplementAmtEnd(new BigDecimal(contractManageVo.getConSupplementAmts().get(1)));
            }
        }
        if (contractManageVo.getSettlementAmts() != null && contractManageVo.getSettlementAmts().size() > 0) {
            if (contractManageVo.getSettlementAmts().get(0) != null && !contractManageVo.getSettlementAmts().get(0).equals("")) {
                contractManageVo.setSettlementAmtStr(new BigDecimal(contractManageVo.getSettlementAmts().get(0)));
            }
            if (contractManageVo.getSettlementAmts().get(1) != null && !contractManageVo.getSettlementAmts().get(1).equals("")) {
                contractManageVo.setSettlementAmtEnd(new BigDecimal(contractManageVo.getSettlementAmts().get(1)));
            }
        }
        if (contractManageVo.getConApplyAmtApprovals() != null && contractManageVo.getConApplyAmtApprovals().size() > 0) {
            if (contractManageVo.getConApplyAmtApprovals().get(0) != null && !contractManageVo.getConApplyAmtApprovals().get(0).equals("")) {
                contractManageVo.setConApplyAmtApprovalStr(new BigDecimal(contractManageVo.getConApplyAmtApprovals().get(0)));
            }
            if (contractManageVo.getConApplyAmtApprovals().get(1) != null && !contractManageVo.getConApplyAmtApprovals().get(1).equals("")) {
                contractManageVo.setConApplyAmtApprovalEnd(new BigDecimal(contractManageVo.getConApplyAmtApprovals().get(1)));
            }
        }
        if (contractManageVo.getConApplyAmtApprovalComs() != null && contractManageVo.getConApplyAmtApprovalComs().size() > 0) {
            if (contractManageVo.getConApplyAmtApprovalComs().get(0) != null && !contractManageVo.getConApplyAmtApprovalComs().get(0).equals("")) {
                contractManageVo.setConApplyAmtApprovalComStr(new BigDecimal(contractManageVo.getConApplyAmtApprovalComs().get(0)));
            }
            if (contractManageVo.getConApplyAmtApprovalComs().get(1) != null && !contractManageVo.getConApplyAmtApprovalComs().get(1).equals("")) {
                contractManageVo.setConApplyAmtApprovalComEnd(new BigDecimal(contractManageVo.getConApplyAmtApprovalComs().get(1)));
            }
        }
        if (contractManageVo.getConDisAmtApprovals() != null && contractManageVo.getConDisAmtApprovals().size() > 0) {
            if (contractManageVo.getConDisAmtApprovals().get(0) != null && !contractManageVo.getConDisAmtApprovals().get(0).equals("")) {
                contractManageVo.setConDisAmtApprovalStr(new BigDecimal(contractManageVo.getConDisAmtApprovals().get(0)));
            }
            if (contractManageVo.getConDisAmtApprovals().get(1) != null && !contractManageVo.getConDisAmtApprovals().get(1).equals("")) {
                contractManageVo.setConDisAmtApprovalEnd(new BigDecimal(contractManageVo.getConDisAmtApprovals().get(1)));
            }
        }
        if (contractManageVo.getConDisApprovalCompleteds() != null && contractManageVo.getConDisApprovalCompleteds().size() > 0) {
            if (contractManageVo.getConDisApprovalCompleteds().get(0) != null && !contractManageVo.getConDisApprovalCompleteds().get(0).equals("")) {
                contractManageVo.setConDisApprovalCompletedStr(new BigDecimal(contractManageVo.getConDisApprovalCompleteds().get(0)));
            }
            if (contractManageVo.getConDisApprovalCompleteds().get(1) != null && !contractManageVo.getConDisApprovalCompleteds().get(1).equals("")) {
                contractManageVo.setConDisApprovalCompletedEnd(new BigDecimal(contractManageVo.getConDisApprovalCompleteds().get(1)));
            }
        }
        IPage<ContractManageVo> pageList = baseMapper.getPayableContrDataList(new Page<>(contractManageVo.getPage(), contractManageVo.getSize()), contractManageVo);
        return BaseResponse.ok(pageList.getRecords(), (int) pageList.getTotal());

    }

    @Override
    public BaseResponse updatePaymentState(ContractManageVo contractManageVo) {
        int i = baseMapper.updatePaymentState(contractManageVo);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPaymentStateContr(String cId) {
        Map<String, Object> map = baseMapper.getPaymentStateContr(cId);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getCompanyPaySum(ContractManageVo contractManageVo) {
        if (contractManageVo.getConAmts() != null && contractManageVo.getConAmts().size() > 0) {
            if (contractManageVo.getConAmts().get(0) != null && !contractManageVo.getConAmts().get(0).equals("")) {
                contractManageVo.setConAmtStr(new BigDecimal(contractManageVo.getConAmts().get(0)));
            }
            if (contractManageVo.getConAmts().get(1) != null && !contractManageVo.getConAmts().get(1).equals("")) {
                contractManageVo.setConAmtEnd(new BigDecimal(contractManageVo.getConAmts().get(1)));
            }
        }
        IPage<ContractManageVo> data = baseMapper.getCompanyPaySum(new Page<>(contractManageVo.getPage(), contractManageVo.getSize()), contractManageVo);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(data.getRecords())) {
            resultList = data.getRecords().stream()
                    .map(vo -> {
                        Map<String, Object> fieldMap = new HashMap<>();
                        fieldMap.put("companyPartyAName", vo.getCompanyPartyAName());
                        fieldMap.put("comPayAmts", vo.getConAmtStr());
                        return fieldMap;
                    })
                    .collect(Collectors.toList());
        }
        return BaseResponse.ok(resultList, (int) data.getTotal());
    }

    //资金年度计划情况统计
    @Override
    public BaseResponse getCapitalSituation(ContractManageVo contractManageVo) {
        //年度不传默认今年
        if (ValidateUtil.isEmpty(contractManageVo.getYear())) {
            contractManageVo.setYear(String.valueOf(LocalDateTime.now().getYear()));
        }
        if (contractManageVo.getConAmts() != null && contractManageVo.getConAmts().size() > 0) {
            if (contractManageVo.getConAmts().get(0) != null && !contractManageVo.getConAmts().get(0).equals("")) {
                contractManageVo.setConAmtStr(new BigDecimal(contractManageVo.getConAmts().get(0)));
            }
            if (contractManageVo.getConAmts().get(1) != null && !contractManageVo.getConAmts().get(1).equals("")) {
                contractManageVo.setConAmtEnd(new BigDecimal(contractManageVo.getConAmts().get(1)));
            }
        }
        if (contractManageVo.getConSupplementAmts() != null && contractManageVo.getConSupplementAmts().size() > 0) {
            if (contractManageVo.getConSupplementAmts().get(0) != null && !contractManageVo.getConSupplementAmts().get(0).equals("")) {
                contractManageVo.setConSupplementAmtStr(new BigDecimal(contractManageVo.getConSupplementAmts().get(0)));
            }
            if (contractManageVo.getConSupplementAmts().get(1) != null && !contractManageVo.getConSupplementAmts().get(1).equals("")) {
                contractManageVo.setConSupplementAmtEnd(new BigDecimal(contractManageVo.getConSupplementAmts().get(1)));
            }
        }
        if (contractManageVo.getSettlementAmts() != null && contractManageVo.getSettlementAmts().size() > 0) {
            if (contractManageVo.getSettlementAmts().get(0) != null && !contractManageVo.getSettlementAmts().get(0).equals("")) {
                contractManageVo.setSettlementAmtStr(new BigDecimal(contractManageVo.getSettlementAmts().get(0)));
            }
            if (contractManageVo.getSettlementAmts().get(1) != null && !contractManageVo.getSettlementAmts().get(1).equals("")) {
                contractManageVo.setSettlementAmtEnd(new BigDecimal(contractManageVo.getSettlementAmts().get(1)));
            }
        }
        IPage<ContractManageVo> data = baseMapper.getCapitalSituation(new Page<>(contractManageVo.getPage(), contractManageVo.getSize()), contractManageVo);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> statsMap = new HashMap<>();

        List<Map<String, Object>> dataList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(data.getRecords())) {
            List<String> conIds = data.getRecords().stream().map(ContractManageVo::getConId).collect(Collectors.toList());
            //查询拨付资金
            List<Map<String, Object>> disbur = baseMapper.getDisbursementAmt(conIds, contractManageVo.getYear());
            Map<String, List<Map<String, Object>>> disburMap = new HashMap<>();

            if (ValidateUtil.isNotEmpty(disbur)) {
                // 生成每个月的记录
                Set<String> conIdsSet = disbur.stream()
                        .map(record -> String.valueOf(record.get("conId")))
                        .collect(Collectors.toSet());

                for (String conId : conIdsSet) {
                    List<Map<String, Object>> monthlyPayments = new ArrayList<>();
                    for (int month = 1; month <= 12; month++) {
                        boolean found = false;
                        for (Map<String, Object> dis : disbur) {
                            if (String.valueOf(dis.get("conId")).equals(conId) &&
                                    Objects.equals(dis.get("month"), month)) {
                                monthlyPayments.add(new HashMap<>(dis));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Map<String, Object> emptyRecord = new HashMap<>();
                            emptyRecord.put("month", month);
                            emptyRecord.put("conId", conId);
                            emptyRecord.put("amt", 0);
                            monthlyPayments.add(emptyRecord);
                        }
                    }
                    disburMap.put(conId, monthlyPayments);
                }
            }
            //  // 为每个disburList都设置12个月
            data.getRecords().forEach(record -> {
                ContractManageVo vo = record;
                List<Map<String, Object>> disburLists = disburMap.getOrDefault(vo.getConId(), generateEmptyMonthlyPayments(vo.getConId()));
                vo.setDisburLists(disburLists);
            });
            Map<String, List<Map<String, Object>>> fundsMap = new HashMap<>();
            //查询支付资金
            List<Map<String, Object>> funds = baseMapper.getPaymentfundsAmt(conIds, contractManageVo.getYear());
            if (ValidateUtil.isNotEmpty(funds)) {
                // 生成每个月的记录
                Set<String> conIdsSet = funds.stream()
                        .map(record -> String.valueOf(record.get("conId")))
                        .collect(Collectors.toSet());

                for (String conId : conIdsSet) {
                    List<Map<String, Object>> monthlyPayments = new ArrayList<>();
                    for (int month = 1; month <= 12; month++) {
                        boolean found = false;
                        for (Map<String, Object> fund : funds) {
                            if (String.valueOf(fund.get("conId")).equals(conId) &&
                                    Objects.equals(fund.get("month"), month)) {
                                monthlyPayments.add(new HashMap<>(fund));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Map<String, Object> emptyRecord = new HashMap<>();
                            emptyRecord.put("month", month);
                            emptyRecord.put("conId", conId);
                            emptyRecord.put("amt", 0);
                            monthlyPayments.add(emptyRecord);
                        }
                    }
                    fundsMap.put(conId, monthlyPayments);
                }
            }
            // 为每个fundList都设置12个月
            data.getRecords().forEach(record -> {
                ContractManageVo vo = record;
                // 使用映射中的数据或创建空记录
                List<Map<String, Object>> fundLists = fundsMap.getOrDefault(vo.getConId(), generateEmptyMonthlyPayments(vo.getConId()));
                vo.setFundLists(fundLists);
            });
            dataList = data.getRecords().stream()
                    .map(record -> {
                        Map<String, Object> simplifiedRecord = new HashMap<>();
                        simplifiedRecord.put("conId", record.getConId());
                        simplifiedRecord.put("preName", record.getPreName());
                        simplifiedRecord.put("conName", record.getConName());
                        simplifiedRecord.put("companyPartybName", record.getCompanyPartybName());
                        simplifiedRecord.put("conAmtStr", record.getConAmtStr());
                        simplifiedRecord.put("conSupplementAmtStr", record.getConSupplementAmtStr());
                        simplifiedRecord.put("conApplyAmtApprovalStr", record.getConApplyAmtApprovalStr());
                        simplifiedRecord.put("companyPartyAName", record.getCompanyPartyAName());
                        simplifiedRecord.put("settlementAmtStr", record.getSettlementAmtStr());
                        simplifiedRecord.put("conPartyAAgent", record.getConPartyAAgent());
                        simplifiedRecord.put("fundLists", record.getFundLists());
                        simplifiedRecord.put("disburLists", record.getDisburLists());
                        return simplifiedRecord;
                    })
                    .collect(Collectors.toList());

            //把拨付资金和实际支付资金统计
            List<ContractManageVo> dataOTwo = baseMapper.getCapitalSituationTwo(contractManageVo);
            if (ValidateUtil.isNotEmpty(data.getRecords())) {
                List<String> conIdsTwo = dataOTwo.stream().map(ContractManageVo::getConId).collect(Collectors.toList());
                List<Map<String, Object>> disburTwo = baseMapper.getDisbursementAmt(conIdsTwo, contractManageVo.getYear());
                if (ValidateUtil.isNotEmpty(disburTwo)) {
                    Map<String, BigDecimal> di = disburofFundsSum(disburTwo, "disbur");
                    if (!di.isEmpty()) {
                        // 计算全年的总金额
                        BigDecimal disSum = di.values().stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        statsMap.put("disSum", disSum);
                    }
                    di.forEach((key, value) -> statsMap.put(key, value));
                    List<Map<String, Object>> fundsTwo = baseMapper.getPaymentfundsAmt(conIdsTwo, contractManageVo.getYear());
                    if (ValidateUtil.isNotEmpty(fundsTwo)) {
                        //查询拨付资金
                        Map<String, BigDecimal> fund = disburofFundsSum(fundsTwo, "funds");
                        if (!di.isEmpty()) {
                            // 计算全年的总金额
                            BigDecimal fundSum = fund.values().stream()
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            statsMap.put("fundSum", fundSum);
                        }
                        fund.forEach((key, value) -> statsMap.put(key, value));
                    }
                }
            }
            dataMap.put("data", dataList);
            dataMap.put("stats", statsMap);

        }
        return BaseResponse.ok(dataMap, (int) data.getTotal());
    }

    //生成12个月,对应月份赋值
    public static Map<String, BigDecimal> disburofFundsSum(List<Map<String, Object>> disbur, String str) {

        Map<Integer, BigDecimal> monthlyAmounts = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            monthlyAmounts.put(month, BigDecimal.ZERO);
        }
        for (Map<String, Object> entry : disbur) {
            Integer month = (Integer) entry.get("month");
            BigDecimal amount = (BigDecimal) entry.get("amt");
            BigDecimal currentAmount = monthlyAmounts.get(month);
            monthlyAmounts.put(month, currentAmount.add(amount));
        }
        Map<String, BigDecimal> stringKeyMonthlyAmounts = new LinkedHashMap<>();

        monthlyAmounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    int month = entry.getKey();
                    BigDecimal amount = entry.getValue();
                    String key = str + month;
                    stringKeyMonthlyAmounts.put(key, amount);
                });

        return stringKeyMonthlyAmounts;
    }

    @Override
    public BaseResponse getProgressPlan(ProgressDeclarationVo vo) {
        if (vo.getSettlementAmts() != null && vo.getSettlementAmts().size() > 0) {
            if (vo.getSettlementAmts().get(0) != null && !vo.getSettlementAmts().get(0).equals("")) {
                vo.setSettlementAmtStr(new BigDecimal(vo.getSettlementAmts().get(0)));
            }
            if (vo.getSettlementAmts().get(1) != null && !vo.getSettlementAmts().get(1).equals("")) {
                vo.setSettlementAmtEnd(new BigDecimal(vo.getSettlementAmts().get(1)));
            }
        }
        if (vo.getProgress() != null && vo.getProgress().size() > 0) {
            if (vo.getProgress().get(0) != null && !vo.getProgress().get(0).equals("")) {
                vo.setProgressStr(new BigDecimal(vo.getProgress().get(0)));
            }
            if (vo.getProgress().get(1) != null && !vo.getProgress().get(1).equals("")) {
                vo.setProgressEnd(new BigDecimal(vo.getProgress().get(1)));
            }
        }
        if (vo.getConAmts() != null && vo.getConAmts().size() > 0) {
            if (vo.getConAmts().get(0) != null && !vo.getConAmts().get(0).equals("")) {
                vo.setConAmtStr(new BigDecimal(vo.getConAmts().get(0)));
            }
            if (vo.getConAmts().get(1) != null && !vo.getConAmts().get(1).equals("")) {
                vo.setConAmtEnd(new BigDecimal(vo.getConAmts().get(1)));
            }
        }
        if (vo.getProgressDates() != null && vo.getProgressDates().size() > 0) {
            if (vo.getProgressDates().get(0) != null && !vo.getProgressDates().get(0).equals("")) {
                vo.setProgressDateStr(vo.getProgressDates().get(0));

            }
            if (vo.getProgressDates().get(1) != null && !vo.getProgressDates().get(1).equals("")) {
                vo.setProgressDateEnd(vo.getProgressDates().get(1));

            }
        }
        if (vo.getDeclareDates() != null && vo.getDeclareDates().size() > 0) {
            if (vo.getDeclareDates().get(0) != null && !vo.getDeclareDates().get(0).equals("")) {
                vo.setDeclareDateStr(vo.getDeclareDates().get(0));

            }
            if (vo.getDeclareDates().get(1) != null && !vo.getDeclareDates().get(1).equals("")) {
                vo.setDeclareDateEnd(vo.getDeclareDates().get(1));

            }
        }

        IPage<ProgressDeclarationVo> page = baseMapper.getProgressPlan(new Page<>(vo.getPage(), vo.getSize()), vo);

        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }


    @Override
    public BaseResponse contrBrowse(ContractBrowseVo vo) {
        if (vo.getConAmts() != null && vo.getConAmts().size() > 0) {
            if (vo.getConAmts().get(0) != null && !vo.getConAmts().get(0).equals("")) {
                vo.setConAmtStr(new BigDecimal(vo.getConAmts().get(0)));
            }
            if (vo.getConAmts().get(1) != null && !vo.getConAmts().get(1).equals("")) {
                vo.setConAmtEnd(new BigDecimal(vo.getConAmts().get(1)));
            }
        }
        if (vo.getConSupplementAmts() != null && vo.getConSupplementAmts().size() > 0) {
            if (vo.getConSupplementAmts().get(0) != null && !vo.getConSupplementAmts().get(0).equals("")) {
                vo.setConSupplementAmtStr(new BigDecimal(vo.getConSupplementAmts().get(0)));
            }
            if (vo.getConSupplementAmts().get(1) != null && !vo.getConSupplementAmts().get(1).equals("")) {
                vo.setConSupplementAmtEnd(new BigDecimal(vo.getConSupplementAmts().get(1)));
            }
        }
        if (vo.getSettlementAmts() != null && vo.getSettlementAmts().size() > 0) {
            if (vo.getSettlementAmts().get(0) != null && !vo.getSettlementAmts().get(0).equals("")) {
                vo.setSettlementAmtStr(new BigDecimal(vo.getSettlementAmts().get(0)));
            }
            if (vo.getSettlementAmts().get(1) != null && !vo.getSettlementAmts().get(1).equals("")) {
                vo.setSettlementAmtEnd(new BigDecimal(vo.getSettlementAmts().get(1)));
            }
        }
        IPage<ContractBrowseVo> pages = baseMapper.contrBrowse(new Page<>(vo.getPage(), vo.getSize()), vo);
        Map<String, Object> data = new HashMap<>();
        data.put("data", pages.getRecords());

        IPage<ContractBrowseVo> pagesSum = baseMapper.contrBrowse(new Page<>(1, pages.getTotal()), vo);
        Map<String, Object> stats = new HashMap<>();
        BigDecimal conAmtSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getConAmtStr())).map(item -> item.getConAmtStr()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("conAmtSum", conAmtSum); //合同金额
        BigDecimal conSupplementAmtSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getConSupplementAmtStr())).map(item -> item.getConSupplementAmtStr()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("conSupplementAmtSum", conSupplementAmtSum); //合同金额含补充合同
        BigDecimal settlementAmtSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getSettlementAmtStr())).map(item -> item.getSettlementAmtStr()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("settlementAmtSum", settlementAmtSum); //结算金额
        BigDecimal conApplyAmtApprovalComSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getConApplyAmtApprovalComStr())).map(item -> item.getConApplyAmtApprovalComStr()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("conApplyAmtApprovalComSum", conApplyAmtApprovalComSum); //资金申请金额
        BigDecimal conDisApprovalCompletedSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getConDisApprovalCompletedStr())).map(item -> item.getConDisApprovalCompletedStr()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("conDisApprovalCompletedSum", conDisApprovalCompletedSum); //资金拨付金额
        BigDecimal fundsPaymentSum = pagesSum.getRecords().stream().filter(e -> ValidateUtil.isNotNull(e.getFundsPaymentEnd())).map(item -> item.getFundsPaymentEnd()).reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("fundsPaymentSum", fundsPaymentSum); //资金支付金额
        data.put("stats", stats);

        return BaseResponse.ok(data, (int) pages.getTotal());
    }

    //资金拨付
    @Override
    public BaseResponse moneyDisbursement(ConPayDisbursementVo entvo) {
        if (ValidateUtil.isEmpty(entvo.getPanyId())) {
            return BaseResponse.fail("单位不能为空");
        }
        if (ValidateUtil.isEmpty(entvo.getBursementAmtTotal()) || entvo.getBursementAmtTotal().equals(new BigDecimal(0))) {
            return BaseResponse.fail("请录入本次拨款总额度");
        }
        /**
         * 按照月度逐月支付，   月度内部，
         * 小额支付优先，剩余的大额，按照比例支付。
         * 保证前面月份先符合支付条件的大额、小额能排在前面，
         * 在月度内符合条件的，小额度优先支付   。
         * 这样大额小额都能及时支付，尽量保证公平
         *
         *
         * 资金总量大于等于所有的自己申请总额，则直接全部足额拨付。
         *如果资金总量不足以全部足额拨付，则把所有待支付的资金申请按照申请时间的月度从前到后排列，每月为一个单位。
         *对于小于优先分配最低限额（最低限额可以调整）的申请，优先支付；
         */

        //获取系统运行参数
//         SystemParameter sys = systemParameterService.getData();

        String payPro = iSysConfigService.getConfigByCode("paymentAmountAndProportion");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(payPro);
        } catch (JsonProcessingException j) {
            j.printStackTrace();
        }
        //最低支付金额
        BigDecimal minThreshold = jsonNode.path("minPaymentAmount").decimalValue();

        //最低支付比例
        BigDecimal minPaymentRatio = jsonNode.path("minPaymentRatio").decimalValue();


        Map<String, Object> map = new HashMap<>();
        List<ConPayDisbursementVo> conList = baseMapper.moneyDisbursement(entvo);


        List<String> payIds = conList.stream().map(e -> e.getPayId()).collect(Collectors.toList());
        //查询是否已存在拨付，如果存在的就只查询没有拨付完成
        List<FundAllocationDtl> fundList = iFundAllocationDtlService.getiFundActionDtlPayList(payIds);

        conList = conList.stream().map(e->{
            BigDecimal allocationAmt = BigDecimal.ZERO;  //已经累计拨付的金额
            for (FundAllocationDtl fund : fundList) {
                if (fund.getPayId().equals(e.getPayId())) {
                    allocationAmt = allocationAmt.add(fund.getFadActualPayment() != null ? fund.getFadActualPayment() : BigDecimal.ZERO);
                }
            }
            //如果累计拨付已经拨完了，赋值0,不在拨付
            if(allocationAmt.compareTo(e.getPayApply()) >=0){
                e.setPayApply(BigDecimal.ZERO);
            }
//            else{
//                //如果本次支付金额+累计支付金额大于实际所需支付，就所需支付-累计支付，避免多付
//                if(e.getPayApply().add(allocationAmt).compareTo(e.getPayApply()) >=0){
//                    e.setPayApply(MathUtil.decSubtract(e.getPayApply(),allocationAmt));
//                }
//            }
            return e;

        }).collect(Collectors.toList());



        //过滤掉本次支付金额为0的
        conList =  conList.stream() .filter(e -> e.getPayApply() != null && e.getPayApply().compareTo(BigDecimal.ZERO) != 0)
                .collect(Collectors.toList());

        BigDecimal[] totalAmtArray = {entvo.getBursementAmtTotal()};// 总资金

        // 按照 PayCreateTime 的年月排序
        List<ConPayDisbursementVo> sortedList = conList.stream()
                .sorted(Comparator.comparing((ConPayDisbursementVo v1) -> YearMonth.from(v1.getPayCreateTime())))
                .collect(Collectors.toList());

        // 计算所有申请总额
        BigDecimal totalApplicationAmt = sortedList.stream()
                .map(ConPayDisbursementVo::getPayApply)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sortedList.stream().forEach(e->{
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (FundAllocationDtl fund : fundList) {
                if (fund.getPayId().equals(e.getPayId())) {
                    totalAmount = totalAmount.add(fund.getFadActualPayment() != null ? fund.getFadActualPayment() : BigDecimal.ZERO);
                }
            }
            e.setPayTotal(totalAmount);
        });

        if (totalAmtArray[0].compareTo(totalApplicationAmt) >= 0) {
            // 资金总量足够，直接全部足额拨付
            for (ConPayDisbursementVo pay : sortedList) {
                pay.setThisAllocationAmt(pay.getPayApply());
                totalAmtArray[0] = totalAmtArray[0].subtract(pay.getPayApply());
            }
            map.put("rmk", "全部申请足额拨付");

        } else {
            // 资金总量不足，按月度处理申请
            // 按月度分组
            // 按月度处理申请

            sortedList.stream()
                    .collect(Collectors.groupingBy(vo -> YearMonth.from(vo.getPayCreateTime())))
                    .forEach((yearMonth, monthlyList) -> {
                        // 计算剩余资金
                        BigDecimal remainingAmt = totalAmtArray[0];

                        // 优先处理小于最低限额的申请
                        List<ConPayDisbursementVo> lowPriorityList = monthlyList.stream()
                                .filter(v4 -> v4.getPayApply().compareTo(minThreshold) < 0)
                                .collect(Collectors.toList());

                        // 大于最低限额的申请
                        List<ConPayDisbursementVo> otherList = monthlyList.stream()
                                .filter(v5 -> v5.getPayApply().compareTo(minThreshold) >= 0)
                                .collect(Collectors.toList());

                        // 拨付小于最低限额的申请
                        for (ConPayDisbursementVo v6 : lowPriorityList) {
                            if (remainingAmt.compareTo(v6.getPayApply()) >= 0) {
                                v6.setThisAllocationAmt(v6.getPayApply());
                                remainingAmt = remainingAmt.subtract(v6.getPayApply()); // 更新剩余资金
                            } else {
                                if(ValidateUtil.isNotNull(v6.getPayTotal())){
                                    BigDecimal zhifu = v6.getPayApply().subtract(v6.getPayTotal());
                                    if(zhifu.compareTo(BigDecimal.ZERO) >= 0){
                                        BigDecimal actualPay =remainingAmt.min(zhifu);
                                        v6.setThisAllocationAmt(actualPay);
                                        remainingAmt = remainingAmt.subtract(actualPay);
                                    }
                                }else{
                                    v6.setThisAllocationAmt(remainingAmt);
                                    remainingAmt = BigDecimal.ZERO;
                                    break;
                                }
                            }
                        }

                        // 检查剩余资金是否足够支付其他申请
                        if (remainingAmt.compareTo(BigDecimal.ZERO) > 0 && !otherList.isEmpty()) {
                            // 按比例分配剩余资金
                            BigDecimal totalOtherApplicants = otherList.stream()
                                    .map(ConPayDisbursementVo::getPayApply)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            for (int i = 0; i < otherList.size(); i++) {
                                ConPayDisbursementVo v7 = otherList.get(i);

                                if (remainingAmt.compareTo(BigDecimal.ZERO) <= 0) {
                                    break; // 剩余资金为0，停止分配
                                }

                                BigDecimal minPayableAmt = v7.getPayApply().multiply(minPaymentRatio.divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);
                                if (remainingAmt.compareTo(minPayableAmt) < 0) {
                                    // 剩余资金不足以支付当前申请的5%，不予支付
//                                    remainingAmt = BigDecimal.ZERO;
                                    map.put("rmk", "剩余资金低于当前申请资金的" + minPaymentRatio + "%，不予支付");
                                    break;
                                }

                                BigDecimal allocationAmt;
                                if (i == otherList.size() - 1) {
                                    // 处理最后一个元素的逻辑
                                    allocationAmt = remainingAmt.min(v7.getPayApply()).setScale(2, BigDecimal.ROUND_HALF_UP);
                                } else {
                                    allocationAmt = v7.getPayApply().multiply(remainingAmt.divide(totalOtherApplicants, 2, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                    if (allocationAmt.compareTo(v7.getPayApply()) > 0) {
                                        allocationAmt = v7.getPayApply();
                                    }
                                }

                                v7.setThisAllocationAmt(allocationAmt);
                                remainingAmt = remainingAmt.subtract(allocationAmt); // 更新剩余资金
                            }
                        } else if (remainingAmt.compareTo(BigDecimal.ZERO) <= 0) {
                            // 剩余资金不足，记录信息
                            map.put("rmk", "本次支付到" + yearMonth + "的限额支付（限额：<" + minThreshold + "），剩余的资金不够下一笔支付，不能继续支付");
                        }
                        totalAmtArray[0] = remainingAmt; // 更新总资金
                    });
            if(totalAmtArray[0].compareTo(BigDecimal.ZERO)>0){
                map.put("rmk", "全部申请足额拨付");
            }
        }
        //累计拨付
        sortedList.stream().forEach(e->{
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (FundAllocationDtl fund : fundList) {
                if (fund.getPayId().equals(e.getPayId())) {
                    totalAmount = totalAmount.add(fund.getFadActualPayment() != null ? fund.getFadActualPayment() : BigDecimal.ZERO);
                }
            }
            //累计拨付
            e.setPayTotal(MathUtil.decAdd(totalAmount,e.getThisAllocationAmt()));
            //历史未支付
            e.setHistoricalAmount(e.getPayApply().subtract(e.getPayTotal()));
            // 账面实际支付到比例：有审计价：账面实际已付/审计价；有初审价：账面实际已付/初审价；有决算价：账面实际已付/决算价；什么都没有：账面实际已付/合同价
            if(ValidateUtil.isNotNull(e.getAsAuditedAmount())){
                e.setActualPaymentRatio(MathUtil.decimalDivide(e.getActualPaymentAmt(),e.getAsAuditedAmount()));
            }else if(ValidateUtil.isNotNull(e.getPaExamineApprove())){
                e.setActualPaymentRatio(MathUtil.decimalDivide(e.getActualPaymentAmt(),e.getPaExamineApprove()));
            }else if(ValidateUtil.isNotNull(e.getPFinal())){
                e.setActualPaymentRatio(MathUtil.decimalDivide(e.getActualPaymentAmt(),e.getPFinal()));
            }else{
                e.setActualPaymentRatio(MathUtil.decimalDivide(e.getActualPaymentAmt(),e.getConMoneyTotal()));
            }
            // 本次应支付到比例 有审计价：累计拨付(元)(含本次拨付)/审计价；有初审价：累计拨付(元)(含本次拨付)/初审价；有决算价：累计拨付(元)(含本次拨付)/决算价；什么都没有：累计拨付(元)(含本次拨付)/合同价
            if(ValidateUtil.isNotNull(e.getAsAuditedAmount())){
                e.setThisProportionPaymentRatio(MathUtil.decimalDivide(e.getPayTotal(),e.getAsAuditedAmount()));
            }else if(ValidateUtil.isNotNull(e.getPaExamineApprove())){
                e.setThisProportionPaymentRatio(MathUtil.decimalDivide(e.getPayTotal(),e.getPaExamineApprove()));
            }else if(ValidateUtil.isNotNull(e.getPFinal())){
                e.setThisProportionPaymentRatio(MathUtil.decimalDivide(e.getPayTotal(),e.getPFinal()));
            }else{
                e.setThisProportionPaymentRatio(MathUtil.decimalDivide(e.getPayTotal(),e.getConMoneyTotal()));
            }
        });





        map.put("amt", totalAmtArray[0]);
        map.put("data", sortedList);
        return BaseResponse.ok(map, conList.size());
    }


    @Override
    public BaseResponse saveMoneyDisbursement(ConPayDisbursementVo vo) {
        return null;
    }

    //生成12个月份的空记录
    private List<Map<String, Object>> generateEmptyMonthlyPayments(String conId) {
        List<Map<String, Object>> monthlyPayments = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> emptyRecord = new HashMap<>();
            emptyRecord.put("month", month);
            emptyRecord.put("conId", conId);
            emptyRecord.put("amt", 0);
            monthlyPayments.add(emptyRecord);
        }
        return monthlyPayments;
    }


}
