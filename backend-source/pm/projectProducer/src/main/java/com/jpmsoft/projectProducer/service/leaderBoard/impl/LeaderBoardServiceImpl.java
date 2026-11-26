package com.jpmsoft.projectProducer.service.leaderBoard.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.MaintenancelStatusEnum;
import com.jpmsoft.common.model.request.loadBoard.*;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementEndVO;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.leaderBoard.LeaderBoardMapper;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceRecordMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPTSpotCheckMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.AnnualPlanMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.ConstructionCompletionConfirmMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.InvSettlementPayMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordStatisticsDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsStatisticsConfigDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsStatisticsDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApplyDtl;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.AnnualPlan;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionCompletionConfirm;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.InvSettlementPay;
import com.jpmsoft.projectProducer.pojo.vo.leaderBoard.*;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.service.leaderBoard.LeaderBoardService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.TaskProcessinService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsTypeService;
import com.jpmsoft.projectProducer.service.zdyxy.IInvSettlementApplyDtlService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
    @Autowired
    private LeaderBoardMapper leaderBoardMapper;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private IQsTypeService qsTypeService;
    @Autowired
    private QsPTSpotCheckMapper qsPTSpotCheckMapper;
    @Autowired
    private ConstructionCompletionConfirmMapper constructionCompletionConfirmMapper;
    @Autowired
    private MaintenanceRecordMapper maintenanceRecordMapper;
    @Autowired
    private InvSettlementApplyMapper invSettlementApplyMapper;
    @Autowired
    private InvSettlementPayMapper invSettlementPayMapper;
    @Autowired
    private AnnualPlanMapper annualPlanMapper;
    @Resource
    private IInvSettlementApplyDtlService invSettlementApplyDtlService;

    private String getSysDictionaryIdByCode(String code){
        return leaderBoardMapper.getSysDictionaryIdByCode(code);
    }
    private List<String> getSysDictionaryCodeByParentId(String parentId){
        //log.info("sysDictionaryCode:{}", JacksonUtils.toJSONString(sysDictionaryCode));
        return leaderBoardMapper.getSysDictionaryCodeByParentId(parentId);
    }
    private List<String> getSysDictionaryCodeChildByParentCode(String parentCode){
        List<SysDictionary> sysDictionarys = getSysDictionaryChildByParentCode(parentCode);
        //log.info("sysDictionaryCode:{}", JacksonUtils.toJSONString(sysDictionaryCode));
        return sysDictionarys.stream().map(SysDictionary::getdCode).collect(Collectors.toList());
    }
    private List<SysDictionary> getSysDictionaryChildByParentCode(String parentCode){
        List<SysDictionary> sysDictionaryCodes =new ArrayList<>();
        List<SysDictionary> sysDictionaryList = leaderBoardMapper.getSysDictionaryByParentId(parentCode);
        if (CollectionUtil.isNotEmpty(sysDictionaryList)){
            for (SysDictionary sysDictionary : sysDictionaryList) {
                sysDictionaryCodes.add(sysDictionary);
                List<SysDictionary> sysDictionaryListChild = getSysDictionaryChildByParentCode(sysDictionary.getdCode());
                if(CollectionUtil.isNotEmpty(sysDictionaryListChild)){
                    sysDictionaryCodes.addAll(sysDictionaryListChild);
                }
            }
        }
        return sysDictionaryCodes;
    }
    private List<String> getSysDictionaryCodeChildByParentId(String parentId){
        List<SysDictionary> sysDictionarys = getSysDictionaryChildByParentId(parentId);
        //log.info("sysDictionaryCode:{}", JacksonUtils.toJSONString(sysDictionaryCode));
        return sysDictionarys.stream().map(SysDictionary::getdCode).collect(Collectors.toList());
    }
    private List<SysDictionary> getSysDictionaryChildByParentId(String parentId){
        List<SysDictionary> sysDictionaryCodes =new ArrayList<>();
        List<SysDictionary> sysDictionaryList = leaderBoardMapper.getSysDictionaryByParentId(parentId);
        if (CollectionUtil.isNotEmpty(sysDictionaryList)){
           for (SysDictionary sysDictionary : sysDictionaryList) {
               sysDictionaryCodes.add(sysDictionary);
               List<SysDictionary> sysDictionaryListChild = getSysDictionaryChildByParentId(sysDictionary.getdId());
               if(CollectionUtil.isNotEmpty(sysDictionaryListChild)){
                   sysDictionaryCodes.addAll(sysDictionaryListChild);
               }
           }
         }
        return sysDictionaryCodes;
    }
    private List<SysDictionary> getSysDictionaryByParentId(String parentId) {
        return leaderBoardMapper.getSysDictionaryByParentId(parentId);
    }
    private List<String> findDescendants(String parentId, Map<String, QsType> nodeMap) {
        List<String> allIds = new ArrayList<>();
        if (!nodeMap.containsKey(parentId)) {
            return allIds;
        }
        allIds.add(parentId);
        for (QsType qsType : nodeMap.values()) {
            if (qsType.getQstParentId() != null && qsType.getQstParentId().equals(parentId)) {
                allIds.add(qsType.getQstId());
                allIds.addAll(findDescendants(qsType.getQstId(), nodeMap));
            }
        }
        Set<String> set = new HashSet<>(allIds);
        return new ArrayList<>(set);
    }

    private void setProjectStatusInfoSumOrMoney(List<PreProjectLeaderBoardInfoVo> projectStatusInfoVos, List<String> projectTypeCodesQtMoney, PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo, boolean flag1) {
        if (flag1||StrUtil.isEmpty(preProjectLeaderBoardInfoVo.getProjectStatus())){
            projectTypeCodesQtMoney.add(preProjectLeaderBoardInfoVo.getProjectStatus());
            List<PreProjectLeaderBoardInfoVo> qtProjectLeaderBoardInfoVoList = projectStatusInfoVos
                    .stream().filter(e->e.getProjectStatus()
                            .equals("99")).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(qtProjectLeaderBoardInfoVoList)){
                addQtSumOrMoney(preProjectLeaderBoardInfoVo, qtProjectLeaderBoardInfoVoList);
            }else {
                PreProjectLeaderBoardInfoVo qtProjectLeaderBoardInfoVo = getProjectLeaderBoardInfoVo(preProjectLeaderBoardInfoVo);
                projectStatusInfoVos.add(qtProjectLeaderBoardInfoVo);
            }
        }else {
            projectStatusInfoVos.add(preProjectLeaderBoardInfoVo);
        }
    }

    private static PreProjectLeaderBoardInfoVo getProjectLeaderBoardInfoVo(PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo) {
        PreProjectLeaderBoardInfoVo qtProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
        qtProjectLeaderBoardInfoVo.setProjectStatus("99");
        qtProjectLeaderBoardInfoVo.setProjectSum(preProjectLeaderBoardInfoVo.getProjectSum());
        qtProjectLeaderBoardInfoVo.setProjectSumRate(preProjectLeaderBoardInfoVo.getProjectSumRate());
        qtProjectLeaderBoardInfoVo.setProjectInvTotal(preProjectLeaderBoardInfoVo.getProjectInvTotal());
        qtProjectLeaderBoardInfoVo.setProjectInvTotalRate(preProjectLeaderBoardInfoVo.getProjectInvTotalRate());
        return qtProjectLeaderBoardInfoVo;
    }

    private void addQtSumOrMoney(PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo, List<PreProjectLeaderBoardInfoVo> qtProjectLeaderBoardInfoVoList) {
        PreProjectLeaderBoardInfoVo qtProjectLeaderBoardInfoVo = qtProjectLeaderBoardInfoVoList.get(0);
        qtProjectLeaderBoardInfoVo.setProjectSum(qtProjectLeaderBoardInfoVo.getProjectSum()
                .add(preProjectLeaderBoardInfoVo.getProjectSum()));
        qtProjectLeaderBoardInfoVo.setProjectSumRate(qtProjectLeaderBoardInfoVo.getProjectSumRate()
                .add(preProjectLeaderBoardInfoVo.getProjectSumRate()));
        qtProjectLeaderBoardInfoVo.setProjectInvTotal(qtProjectLeaderBoardInfoVo.getProjectInvTotal()
                .add(preProjectLeaderBoardInfoVo.getProjectInvTotal()));
        qtProjectLeaderBoardInfoVo.setProjectInvTotalRate(qtProjectLeaderBoardInfoVo.getProjectInvTotalRate()
                .add(preProjectLeaderBoardInfoVo.getProjectInvTotalRate()));
    }

    private void setProjectTypeInfoSumOrMoney(List<PreProjectLeaderBoardInfoVo> projectTypeInfoVos, List<String> projectTypeCodesQtSum, PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo, boolean flag1) {
        if (flag1||StrUtil.isEmpty(preProjectLeaderBoardInfoVo.getProjectType())){
            projectTypeCodesQtSum.add(preProjectLeaderBoardInfoVo.getProjectType());
            List<PreProjectLeaderBoardInfoVo> qtProjectLeaderBoardInfoVoList = projectTypeInfoVos
                    .stream().filter(e->e.getProjectType()
                            .equals("99")).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(qtProjectLeaderBoardInfoVoList)){
                addQtSumOrMoney(preProjectLeaderBoardInfoVo, qtProjectLeaderBoardInfoVoList);
            }else {
                PreProjectLeaderBoardInfoVo qtProjectLeaderBoardInfoVo = getPreProjectLeaderBoardInfoVo(preProjectLeaderBoardInfoVo);
                projectTypeInfoVos.add(qtProjectLeaderBoardInfoVo);
            }
        }else {
            projectTypeInfoVos.add(preProjectLeaderBoardInfoVo);
        }
    }

    private static PreProjectLeaderBoardInfoVo getPreProjectLeaderBoardInfoVo(PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo) {
        PreProjectLeaderBoardInfoVo qtProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
        qtProjectLeaderBoardInfoVo.setProjectType("99");
        qtProjectLeaderBoardInfoVo.setProjectSum(preProjectLeaderBoardInfoVo.getProjectSum());
        qtProjectLeaderBoardInfoVo.setProjectSumRate(preProjectLeaderBoardInfoVo.getProjectSumRate());
        qtProjectLeaderBoardInfoVo.setProjectInvTotal(preProjectLeaderBoardInfoVo.getProjectInvTotal());
        qtProjectLeaderBoardInfoVo.setProjectInvTotalRate(preProjectLeaderBoardInfoVo.getProjectInvTotalRate());
        return qtProjectLeaderBoardInfoVo;
    }
    private Map<String, QsType> getQsTypeMap() {
        List<QsType> qsTypeList  = qsTypeService.list();
        Map<String, QsType> qsTypeMap = new HashMap<>();
        for (QsType qsType : qsTypeList) {
            qsTypeMap.put(qsType.getQstId(), qsType);
        }
        return qsTypeMap;
    }
    @Override
    public BaseResponse getPreProjectInfo(ProjectLoadRequest request) {
        Map<String, Object> data = new HashMap<>();
        List<PreProjectResponse> preProjectResponses = leaderBoardMapper.getPreProjectInfo();
        //log.info("preProjectResponses:{}", JacksonUtils.toJSONString(preProjectResponses));
        List<PreProjectLeaderBoardInfoVo> projectTypeInfoVos = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectTypeInfoVos2 = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectTypeInfoVosSum = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectTypeInfoVosMoney = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectStatusInfoVos = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectStatusInfoVos2 = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectStatusInfoVosSum = new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectStatusInfoVosMoney= new ArrayList<>();
        List<PreProjectLeaderBoardInfoVo> projectYearInfoVos = new ArrayList<>();
        List<SysDictionary> projectTypes=getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_XMLB"));
        List<String> projectTypeCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_XMLB"));
        List<SysDictionary> projectStatus=getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_XMStatus"));
        List<String> projectStatusCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_XMStatus"));
        BigDecimal projectSumAll = BigDecimal.ZERO;
        BigDecimal projectInvTotalAll = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(preProjectResponses)){
            List<PreProjectResponse> projectSumTypeAllInfoVos = preProjectResponses.stream()
                    .filter(preProjectResponse -> preProjectResponse.getpType() != null)
                    .collect(Collectors.toList());
            //log.info("projectSumAllInfoVos:{}", JacksonUtils.toJSONString(projectSumTypeAllInfoVos));
            projectSumAll = BigDecimal.valueOf(projectSumTypeAllInfoVos.size());
            if (projectSumAll.compareTo(BigDecimal.ZERO) > 0){
                projectInvTotalAll =  preProjectResponses.stream()
                        .filter(preProjectResponse -> preProjectResponse.getpType() != null
                                && preProjectResponse.getpInvTotal() != null)
                        .map(PreProjectResponse::getpInvTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                Map<String, List<PreProjectResponse>> pTypemap = preProjectResponses.stream()
                        .filter(preProjectResponse -> preProjectResponse.getpType() != null)
                        .collect(Collectors.groupingBy(PreProjectResponse::getpType));
                for (Map.Entry<String, List<PreProjectResponse>> entry : pTypemap.entrySet()) {
                    List<PreProjectResponse> responses = entry.getValue();
                    BigDecimal projectSum = BigDecimal.valueOf(responses.size());
                    BigDecimal projectSumRate = projectSum.divide(projectSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal projectInvTotal = responses.stream()
                            .map(PreProjectResponse::getpInvTotal)
                            .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal projectInvTotalRate = projectInvTotal.divide(projectInvTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
                    preProjectLeaderBoardInfoVo.setProjectType(entry.getKey());
                    projectTypes.stream().filter(item -> item.getdCode().equals(entry.getKey())).findFirst().ifPresent(sysDictionary -> preProjectLeaderBoardInfoVo.setProjectTypeName(sysDictionary.getdName()));
                    preProjectLeaderBoardInfoVo.setProjectSum(projectSum);
                    preProjectLeaderBoardInfoVo.setProjectSumRate(projectSumRate);
                    if (projectInvTotal.compareTo(BigDecimal.ZERO)== 0){
                        preProjectLeaderBoardInfoVo.setProjectInvTotal(BigDecimal.ZERO);
                    }else {
                        preProjectLeaderBoardInfoVo.setProjectInvTotal(projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
                    }
                    preProjectLeaderBoardInfoVo.setProjectInvTotalRate(projectInvTotalRate);
                    projectTypeInfoVos2.add(preProjectLeaderBoardInfoVo);
                }
                if (CollectionUtil.isNotEmpty(projectTypeCodes)){
                    for (String projectTypeCode : projectTypeCodes) {
                        PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
                        preProjectLeaderBoardInfoVo.setProjectType(projectTypeCode);
                        projectTypes.stream().filter(item -> item.getdCode().equals(projectTypeCode)).findFirst().ifPresent(sysDictionary -> preProjectLeaderBoardInfoVo.setProjectTypeName(sysDictionary.getdName()));
                        List<PreProjectResponse> responses = preProjectResponses.stream()
                                .filter(preProjectResponse -> preProjectResponse.getpType() != null
                                        && projectTypeCode.equals(preProjectResponse.getpType()))
                                .collect(Collectors.toList());
                        BigDecimal projectSum = BigDecimal.valueOf(responses.size());
                        preProjectLeaderBoardInfoVo.setProjectSum(projectSum);
                        BigDecimal projectSumRate = projectSum.divide(projectSumAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                        preProjectLeaderBoardInfoVo.setProjectSumRate(projectSumRate);
                        BigDecimal projectInvTotal = responses.stream()
                                .filter(preProjectResponse -> preProjectResponse.getpType() != null
                                        && projectTypeCode.equals(preProjectResponse.getpType()))
                                .map(PreProjectResponse::getpInvTotal)
                                .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (projectInvTotal.compareTo(BigDecimal.ZERO)==0){
                            preProjectLeaderBoardInfoVo.setProjectInvTotal(BigDecimal.ZERO);
                        }else {
                            preProjectLeaderBoardInfoVo.setProjectInvTotal(projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
                        }
                        BigDecimal projectInvTotalRate = projectInvTotal.divide(projectInvTotalAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                        preProjectLeaderBoardInfoVo.setProjectInvTotalRate(projectInvTotalRate);
                        projectTypeInfoVos.add(preProjectLeaderBoardInfoVo);
                    }
                }
            }
            List<PreProjectResponse> projectSumStatusAllInfoVos = preProjectResponses.stream()
                    .filter(preProjectResponse -> preProjectResponse.getpProjectStatus() != null)
                    .collect(Collectors.toList());
            //log.info("projectSumStatusAllInfoVos:{}", JacksonUtils.toJSONString(projectSumStatusAllInfoVos));
            projectSumAll = BigDecimal.valueOf(projectSumStatusAllInfoVos.size());
            if (projectSumAll.compareTo(BigDecimal.ZERO)> 0){
                projectInvTotalAll =  preProjectResponses.stream()
                        .filter(preProjectResponse -> preProjectResponse.getpProjectStatus() != null
                                && preProjectResponse.getpInvTotal() != null)
                        .map(PreProjectResponse::getpInvTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
               Map<String, List<PreProjectResponse>> pStatusMap = preProjectResponses.stream()
                       .filter(preProjectResponse -> preProjectResponse.getpProjectStatus() != null)
                       .collect(Collectors.groupingBy(PreProjectResponse::getpProjectStatus));
               for (Map.Entry<String, List<PreProjectResponse>> entry : pStatusMap.entrySet()) {
                   List<PreProjectResponse> responses = entry.getValue();
                   BigDecimal projectSum = BigDecimal.valueOf(responses.size());
                   BigDecimal projectSumRate = projectSum.divide(projectSumAll, 4, RoundingMode.HALF_UP)
                           .multiply(BigDecimal.valueOf(100));
                   BigDecimal projectInvTotal = responses.stream().map(PreProjectResponse::getpInvTotal)
                           .filter(Objects::nonNull)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
                   BigDecimal projectInvTotalRate = projectInvTotal.divide(projectInvTotalAll, 4, RoundingMode.HALF_UP)
                           .multiply(BigDecimal.valueOf(100));
                   PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
                   preProjectLeaderBoardInfoVo.setProjectStatus(entry.getKey());
                   projectStatus.stream().filter(item -> item.getdCode().equals(entry.getKey()))
                           .findFirst().ifPresent(sysDictionary -> preProjectLeaderBoardInfoVo.setProjectStatusName(sysDictionary.getdName()));
                   preProjectLeaderBoardInfoVo.setProjectSum(projectSum);
                   preProjectLeaderBoardInfoVo.setProjectSumRate(projectSumRate);
                   if (projectInvTotal.compareTo(BigDecimal.ZERO) == 0){
                       preProjectLeaderBoardInfoVo.setProjectInvTotal(BigDecimal.ZERO);
                   }else{
                       preProjectLeaderBoardInfoVo.setProjectInvTotal(projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
                   }
                   preProjectLeaderBoardInfoVo.setProjectInvTotalRate(projectInvTotalRate);
                   projectStatusInfoVos2.add(preProjectLeaderBoardInfoVo);
               }
                if (CollectionUtil.isNotEmpty(projectStatusCodes)){
                    for (String projectStatusCode : projectStatusCodes) {
                        PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
                        preProjectLeaderBoardInfoVo.setProjectStatus(projectStatusCode);
                        projectStatus.stream().filter(item -> item.getdCode().equals(projectStatusCode))
                                .findFirst().ifPresent(sysDictionary -> preProjectLeaderBoardInfoVo.setProjectStatusName(sysDictionary.getdName()));
                        List<PreProjectResponse> responses = preProjectResponses.stream()
                                .filter(preProjectResponse -> preProjectResponse.getpProjectStatus() != null
                                        && projectStatusCode.equals(preProjectResponse.getpProjectStatus()))
                                .collect(Collectors.toList());
                        BigDecimal projectSum = BigDecimal.valueOf(responses.size());
                        preProjectLeaderBoardInfoVo.setProjectSum(projectSum);
                        BigDecimal projectSumRate = projectSum.divide(projectSumAll, 4, RoundingMode.HALF_UP)
                                 .multiply(BigDecimal.valueOf(100));
                        preProjectLeaderBoardInfoVo.setProjectSumRate(projectSumRate);
                        BigDecimal projectInvTotal = responses.stream()
                                .filter(preProjectResponse -> preProjectResponse.getpProjectStatus() != null
                                        && projectStatusCode.equals(preProjectResponse.getpProjectStatus()))
                                .map(PreProjectResponse::getpInvTotal)
                                .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (projectInvTotal.compareTo(BigDecimal.ZERO)==0){
                            preProjectLeaderBoardInfoVo.setProjectInvTotal(BigDecimal.ZERO);
                        }else {
                            preProjectLeaderBoardInfoVo.setProjectInvTotal(projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));

                        }
                        BigDecimal projectInvTotalRate = projectInvTotal.divide(projectInvTotalAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                        preProjectLeaderBoardInfoVo.setProjectInvTotalRate(projectInvTotalRate);
                        projectStatusInfoVos.add(preProjectLeaderBoardInfoVo);
                    }
                }
            }
            projectSumAll = BigDecimal.valueOf(preProjectResponses.stream()
                    .filter(preProjectResponse -> preProjectResponse.getProjectYear() != null)
                    .count());
            if (projectSumAll.compareTo(BigDecimal.ZERO) > 0){
                projectInvTotalAll =  preProjectResponses.stream()
                        .filter(preProjectResponse -> preProjectResponse.getProjectYear() != null
                                && preProjectResponse.getpInvTotal() != null)
                        .map(PreProjectResponse::getpInvTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                Map<Integer,List<PreProjectResponse>> pYearMap = preProjectResponses.stream()
                        .filter(preProjectResponse -> preProjectResponse.getProjectYear() != null)
                        .collect(Collectors.groupingBy(PreProjectResponse::getProjectYear));
                for (Map.Entry<Integer, List<PreProjectResponse>> entry : pYearMap.entrySet()) {
                    List<PreProjectResponse> responses = entry.getValue();
                    BigDecimal projectSum = BigDecimal.valueOf(responses.size());
                    BigDecimal projectSumRate = projectSum.divide(projectSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal projectInvTotal = responses.stream().map(PreProjectResponse::getpInvTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal projectInvTotalRate = projectInvTotal.divide(projectInvTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo = new PreProjectLeaderBoardInfoVo();
                    preProjectLeaderBoardInfoVo.setYear(entry.getKey());
                    preProjectLeaderBoardInfoVo.setProjectSum(projectSum);
                    preProjectLeaderBoardInfoVo.setProjectSumRate(projectSumRate);
                    if (projectInvTotal.compareTo(BigDecimal.ZERO)==0){
                        preProjectLeaderBoardInfoVo.setProjectInvTotal(BigDecimal.ZERO);
                    }else {
                        preProjectLeaderBoardInfoVo.setProjectInvTotal(projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
                    }
                    preProjectLeaderBoardInfoVo.setProjectInvTotalRate(projectInvTotalRate);
                    projectYearInfoVos.add(preProjectLeaderBoardInfoVo);
                }
                projectYearInfoVos =projectYearInfoVos.stream().sorted(Comparator.comparing(PreProjectLeaderBoardInfoVo::getYear)).collect(Collectors.toList());
            }
        }

        SysDictionary qt = new SysDictionary();
        qt.setdId(IdWorker.getGenerateID());
        qt.setdCode("99");
        qt.setdName("其他");
        qt.setdSort(99);
        projectTypes.add(qt);
        projectTypeCodes.add("99");
        projectStatus.add(qt);
        projectStatusCodes.add("99");
        List<String> projectTypeCodesQtSum=new ArrayList<>();
        List<String> projectStatusCodesQtSum=new ArrayList<>();
        List<String> projectTypeCodesQtMoney=new ArrayList<>();
        List<String> projectStatusCodesQtMoney=new ArrayList<>();
        if (CollectionUtil.isNotEmpty(projectTypeInfoVos2)){
            for (PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo : projectTypeInfoVos2) {
                boolean flag1 = preProjectLeaderBoardInfoVo.getProjectSumRate().compareTo(new BigDecimal(5))<0;
                boolean flag2 = preProjectLeaderBoardInfoVo.getProjectInvTotalRate().compareTo(new BigDecimal(5))<0;
                setProjectTypeInfoSumOrMoney(projectTypeInfoVosSum, projectTypeCodesQtSum, preProjectLeaderBoardInfoVo, flag1);
                setProjectTypeInfoSumOrMoney(projectTypeInfoVosMoney, projectTypeCodesQtMoney, preProjectLeaderBoardInfoVo, flag2);
            }
        }
        if (CollectionUtil.isNotEmpty(projectStatusInfoVos2)){
            for (PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo : projectStatusInfoVos2) {
                boolean flag1=preProjectLeaderBoardInfoVo.getProjectSumRate().compareTo(new BigDecimal(5))<0;
                boolean flag2=preProjectLeaderBoardInfoVo.getProjectInvTotalRate().compareTo(new BigDecimal(5))<0;
                setProjectStatusInfoSumOrMoney(projectStatusInfoVosSum, projectStatusCodesQtSum, preProjectLeaderBoardInfoVo, flag1);
                setProjectStatusInfoSumOrMoney(projectStatusInfoVosMoney, projectStatusCodesQtMoney, preProjectLeaderBoardInfoVo, flag2);
            }
        }
        if(CollectionUtil.isNotEmpty(request.getYearRange())){
            log.info("开始时间-结束时间：{} ",request.getYearRange());
            List<Integer> yearRange = request.getYearRange().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            log.info("开始时间：{} -结束时间：{}",yearRange.get(0),yearRange.get(1));
            if (yearRange.size()>=1&&yearRange.get(0)!=null){
                projectYearInfoVos = projectYearInfoVos.stream()
                        .filter(n -> n.getYear()  >= yearRange.get(0)).collect(Collectors.toList());
            }
            if (yearRange.size()==2&&yearRange.get(1)!=null){
                projectYearInfoVos = projectYearInfoVos.stream()
                        .filter(n -> n.getYear()  <= yearRange.get(1)).collect(Collectors.toList());
            }
            projectYearInfoVos = projectYearInfoVos.stream().sorted(Comparator.comparing(PreProjectLeaderBoardInfoVo::getYear)).collect(Collectors.toList());
        }
        BigDecimal projectSumYearAll = BigDecimal.ZERO;
        BigDecimal projectInvTotalYearAll = BigDecimal.ZERO;
        for (PreProjectLeaderBoardInfoVo preProjectLeaderBoardInfoVo : projectYearInfoVos) {
            projectSumYearAll = projectSumYearAll.add(preProjectLeaderBoardInfoVo.getProjectSum());
            projectInvTotalYearAll = projectInvTotalYearAll.add(preProjectLeaderBoardInfoVo.getProjectInvTotal());
            preProjectLeaderBoardInfoVo.setProjectSumYearAll(projectSumYearAll);
            preProjectLeaderBoardInfoVo.setProjectInvTotalYearAll(projectInvTotalYearAll);
        }
        projectYearInfoVos = projectYearInfoVos.stream().sorted(Comparator.comparing(PreProjectLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
        data.put("projectSumAll",projectSumAll);
        data.put("projectInvTotalAll", projectInvTotalAll.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
        data.put("projectTypes", projectTypes.stream().sorted(Comparator.comparing(SysDictionary::getdSort)).collect(Collectors.toList()));
        data.put("projectTypeCodes", projectTypeCodes);
        data.put("projectStatus", projectStatus.stream().sorted(Comparator.comparing(SysDictionary::getdSort)).collect(Collectors.toList()));
        data.put("projectStatusCodes", projectStatusCodes);
        data.put("projectTypeCodesQtSum", projectTypeCodesQtSum);
        data.put("projectStatusCodesQtSum", projectStatusCodesQtSum);
        data.put("projectTypeCodesQtMoney", projectTypeCodesQtMoney);
        data.put("projectStatusCodesQtMoney", projectStatusCodesQtMoney);
        data.put("projectTypeInfoVos", projectTypeInfoVos);
        data.put("projectTypeInfoVos2", projectTypeInfoVos2);
        data.put("projectTypeInfoVosSum", projectTypeInfoVosSum);
        data.put("projectTypeInfoVosMoney", projectTypeInfoVosMoney);
        data.put("projectStatusInfoVos", projectStatusInfoVos);
        data.put("projectStatusInfoVos2", projectStatusInfoVos2);
        data.put("projectStatusInfoVosSum", projectStatusInfoVosSum);
        data.put("projectStatusInfoVosMoney", projectStatusInfoVosMoney);
        data.put("projectYearInfoVos", projectYearInfoVos);
        log.info("data:{}", JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPreProjectInfoList(ProjectLoadRequest request) {
        log.info("ProjectLoadRequest:{}", JacksonUtils.toJSONString(request));
        QueryMap<ProjectLoadRequest> params = new QueryMap<>(request);
        params.put("projectName").put("projectCode").put("projectType").put("projectStatus")
                .put("projectTypes").put("projectStatuses").put("projectUseUnit").put("year");//列表查询最新的数据
        IPage<PreProjectResponse> page = new Page<>(request.getPage(), request.getSize());
        log.info("ProjectLoadRequest params:{}", JacksonUtils.toJSONString(params));
        List<PreProjectResponse> preProjectResponses = leaderBoardMapper.getPreProjectInfoList(params.getQuery(), page);
        return BaseResponse.ok(preProjectResponses);
    }

    @Override
    public BaseResponse getContractInfo(ContractLoadRequest request) {
        Map<String,Object> data = new HashMap<>();
        List<ConContractResponse> contractResponses = leaderBoardMapper.getContractInfo();
        List<ContractLeaderBoardInfoVo> conTypeInfoVos = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVos2 = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVosSum = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVosMoney = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conStatusInfoVos = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conStatusInfoVos2 = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conStatusInfoVosSum = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conStatusInfoVosMoney = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conYearInfoVos = new ArrayList<>();
        List<String> conTypesQtSum=new ArrayList<>();
        List<String> conTypesQtMoney=new ArrayList<>();
        List<String> conStatusCodesQtSum=new ArrayList<>();
        List<String> conStatusCodesQtMoney=new ArrayList<>();
        List<SysDictionary> conTypes = getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_HTLX"));
        List<String> conTypeCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_HTLX"));
        List<String> conTypeCodesOther =new ArrayList<>();
        List<SysDictionary> conStatus = getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_HTZT"));
        List<String> conStatusCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_HTZT"));
        BigDecimal contractSumAll = BigDecimal.ZERO;
        BigDecimal contractMoneyTotalAll = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(contractResponses)) {
            contractSumAll = BigDecimal.valueOf(contractResponses.size());//filter(conContractResponse -> conContractResponse.getConType() != null).
            log.info("contractSumAll:{}",contractSumAll);
            if (contractSumAll.compareTo(BigDecimal.ZERO)>0){
                contractMoneyTotalAll = contractResponses.stream()
                        .map(ConContractResponse::getConMoneyTotal)
                        .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                log.info("contractMoneyTotalAll:{}",contractMoneyTotalAll);
                List<ConContractResponse> conContractResponsesNull = contractResponses.stream()
                        .filter(conContractResponse -> StrUtil.isEmpty(conContractResponse.getConType()))
                        .collect(Collectors.toList());
                log.info("conContractResponsesNull:{}",JacksonUtils.toJSONString(conContractResponsesNull));
                if (CollectionUtil.isNotEmpty(conContractResponsesNull)) {
                    BigDecimal contractSum = BigDecimal.valueOf(conContractResponsesNull.size());
                    BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractMoneyTotal = conContractResponsesNull.stream()
                            .map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setContractType(null);
                    contractLeaderBoardInfoVo.setContractTypeName("");
                    contractLeaderBoardInfoVo.setContractSum(contractSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                    contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                    conTypeInfoVos.add(contractLeaderBoardInfoVo);
                    conTypeInfoVos2.add(contractLeaderBoardInfoVo);
                }
                Map<String, List<ConContractResponse>> conTypeMap = contractResponses.stream()
                        .filter(conContractResponse -> StrUtil.isNotEmpty(conContractResponse.getConType()))
                        .collect(Collectors.groupingBy(ConContractResponse::getConType));
                List<String> conTypeInfoVos2ConType= new ArrayList<>();
                for(Map.Entry<String, List<ConContractResponse>> entry : conTypeMap.entrySet()) {
                    List<ConContractResponse> responses = entry.getValue();
                    BigDecimal contractSum = BigDecimal.valueOf(responses.size());
                    BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractMoneyTotal = responses.stream()
                            .map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setContractType(entry.getKey());
                    contractLeaderBoardInfoVo.setContractTypeName(conTypes.stream()
                            .filter(sysDictionary -> sysDictionary.getdCode().equals(entry.getKey()))
                            .findFirst().map(SysDictionary::getdName).orElse(""));
                    contractLeaderBoardInfoVo.setContractSum(contractSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                    contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                    conTypeInfoVos.add(contractLeaderBoardInfoVo);
                }
                if(CollectionUtil.isNotEmpty(conTypes)){
                    for (SysDictionary conTypeCode : conTypes) {
                          ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                          contractLeaderBoardInfoVo.setContractType(conTypeCode.getdCode());
                          contractLeaderBoardInfoVo.setContractTypeName(conTypeCode.getdName());
                          List<String> conTypeCodeList = getSysDictionaryCodeChildByParentId(conTypeCode.getdId());
                          conTypeCodeList.add(conTypeCode.getdCode());
                          conTypeInfoVos2ConType.add(conTypeCode.getdCode());
                          conTypeInfoVos2ConType.addAll(conTypeCodeList);
                          List<ConContractResponse> responses = contractResponses.stream()
                                  .filter(conContractResponse -> conContractResponse.getConType() != null
                                          && conTypeCodeList.contains(conContractResponse.getConType()))
                                  .collect(Collectors.toList());
                          contractLeaderBoardInfoVo.setContractSum(BigDecimal.valueOf(responses.size()));
                          contractLeaderBoardInfoVo.setContractSumRate(contractLeaderBoardInfoVo.getContractSum()
                                  .divide(contractSumAll, 4, RoundingMode.HALF_UP)
                                  .multiply(BigDecimal.valueOf(100)));
                          BigDecimal contractMoneyTotal = responses.stream()
                                  .map(ConContractResponse::getConMoneyTotal)
                                  .filter(Objects::nonNull)
                                  .reduce(BigDecimal.ZERO, BigDecimal::add);
                          contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                          contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotal
                                  .divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                                  .multiply(BigDecimal.valueOf(100)));
                          conTypeInfoVos2.add(contractLeaderBoardInfoVo);
                    }
                }
                List<String> conTypeInfoVosConType = conTypeInfoVos.stream()
                        .map(ContractLeaderBoardInfoVo::getContractType).collect(Collectors.toList());
                conTypeInfoVos2ConType=conTypeInfoVos2ConType.stream().distinct().collect(Collectors.toList());
                log.info("conTypeInfoVosConType:{}",JacksonUtils.toJSONString(conTypeInfoVosConType));
                log.info("conTypeInfoVos2ConType:{}",JacksonUtils.toJSONString(conTypeInfoVos2ConType));
                for (String ConType : conTypeInfoVosConType){
                    if (!conTypeInfoVos2ConType.contains(ConType) &&StrUtil.isNotEmpty(ConType)){
                        conTypeCodesOther.add(ConType);
                    }
                }
                log.info("conTypeCodesOther:{}",JacksonUtils.toJSONString(conTypeCodesOther));
                for (String conTypeCode : conTypeCodesOther){
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo =conTypeInfoVos2.stream().filter(
                            n -> n.getContractType()==null).findFirst().orElse(null);
                    List<String> conTypeCodeList = getSysDictionaryCodeChildByParentCode(conTypeCode);
                    conTypeCodeList.add(conTypeCode);
                    List<ConContractResponse> responses = contractResponses.stream()
                            .filter(conContractResponse -> conContractResponse.getConType() != null
                                    && conTypeCodeList.contains(conContractResponse.getConType()))
                            .collect(Collectors.toList());
                    if (contractLeaderBoardInfoVo != null) {
                        contractLeaderBoardInfoVo.setContractSum(contractLeaderBoardInfoVo.getContractSum()
                                .add(BigDecimal.valueOf(responses.size())));
                        contractLeaderBoardInfoVo.setContractSumRate(contractLeaderBoardInfoVo.getContractSumRate()
                                .add(BigDecimal.valueOf(responses.size())
                                        .divide(contractSumAll, 4, RoundingMode.HALF_UP)
                                        .multiply(BigDecimal.valueOf(100))));
                        BigDecimal contractMoneyTotal = responses.stream()
                                .map(ConContractResponse::getConMoneyTotal)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractLeaderBoardInfoVo.getContractMoneyTotalRate()
                                .add(contractMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                                        .multiply(BigDecimal.valueOf(100))));
                    }else {
                        BigDecimal contractSum = BigDecimal.valueOf(responses.size());
                        BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                        BigDecimal contractMoneyTotal = responses.stream()
                                .map(ConContractResponse::getConMoneyTotal)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll,
                                        4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                        contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                        contractLeaderBoardInfoVo.setContractType(null);
                        contractLeaderBoardInfoVo.setContractTypeName("");
                        contractLeaderBoardInfoVo.setContractSum(contractSum);
                        contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                        contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                        conTypeInfoVos2.add(contractLeaderBoardInfoVo);
                    }
                }
                List<ConContractResponse> conContractResponsesNullState = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getConState() == null)
                        .collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(conContractResponsesNullState)) {
                    BigDecimal contractSum = BigDecimal.valueOf(conContractResponsesNullState.size());
                    BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractMoneyTotal = conContractResponsesNullState.stream()
                            .map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setContractType(null);
                    contractLeaderBoardInfoVo.setContractTypeName("");
                    contractLeaderBoardInfoVo.setContractSum(contractSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                    contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                    conStatusInfoVos.add(contractLeaderBoardInfoVo);
                }
                Map<String, List<ConContractResponse>> conStatusMap = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getConState() != null)
                        .collect(Collectors.groupingBy(ConContractResponse::getConState));
                for (Map.Entry<String, List<ConContractResponse>> entry : conStatusMap.entrySet()) {
                    List<ConContractResponse> responses = entry.getValue();
                    BigDecimal contractSum = BigDecimal.valueOf(responses.size());
                    BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractMoneyTotal = responses.stream().map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll, 4,
                            RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setContractStatus(entry.getKey());
                    contractLeaderBoardInfoVo.setContractStatusName(conStatus.stream()
                            .filter(sysDictionary -> sysDictionary.getdCode().equals(entry.getKey()))
                            .findFirst().map(SysDictionary::getdName).orElse(""));
                    contractLeaderBoardInfoVo.setContractSum(contractSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                    contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                    conStatusInfoVos.add(contractLeaderBoardInfoVo);
                }
                if (CollectionUtil.isNotEmpty(conStatusCodes)){
                    for (String conStatusCode : conStatusCodes) {
                        ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                        contractLeaderBoardInfoVo.setContractStatus(conStatusCode);
                        contractLeaderBoardInfoVo.setContractStatusName(conStatus.stream()
                                .filter(sysDictionary -> sysDictionary.getdCode().equals(conStatusCode))
                                .findFirst().map(SysDictionary::getdName).orElse(""));
                        List<ConContractResponse> responses = contractResponses.stream()
                                .filter(conContractResponse -> conContractResponse.getConState() != null
                                        && conStatusCode.equals(conContractResponse.getConState()))
                                .collect(Collectors.toList());
                        contractLeaderBoardInfoVo.setContractSum(BigDecimal.valueOf(responses.size()));
                        contractLeaderBoardInfoVo.setContractSumRate(contractLeaderBoardInfoVo.getContractSum()
                                .divide(contractSumAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100)));
                        BigDecimal contractMoneyTotal = responses.stream()
                                .map(ConContractResponse::getConMoneyTotal)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                        contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractLeaderBoardInfoVo.getContractMoneyTotal()
                                .divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100)));
                        conStatusInfoVos2.add(contractLeaderBoardInfoVo);
                    }
                }
            }
            SysDictionary qt = new SysDictionary();
            qt.setdId(IdWorker.getGenerateID());
            qt.setdCode("199");
            qt.setdName("其他");
            qt.setdSort(99);
            conTypes.add(qt);
            conTypeCodes.add("199");
            conStatus.add(qt);
            conStatusCodes.add("199");
            if (CollectionUtil.isNotEmpty(conTypeInfoVos2)){
                for (ContractLeaderBoardInfoVo contractLeaderBoardInfo : conTypeInfoVos2) {
                    boolean flag=contractLeaderBoardInfo.getContractSumRate().compareTo(new BigDecimal(5))<0;
                    boolean flag2=contractLeaderBoardInfo.getContractMoneyTotalRate().compareTo(new BigDecimal(5))<0;
                    conTypeInfoVosSumOrMoney(conTypeInfoVosSum, conTypesQtSum, contractLeaderBoardInfo, flag,conTypeCodes);
                    conTypeInfoVosSumOrMoney(conTypeInfoVosMoney, conTypesQtMoney, contractLeaderBoardInfo, flag2,conTypeCodes);
                }
            }
            if (CollectionUtil.isNotEmpty(conStatusInfoVos)){
                for (ContractLeaderBoardInfoVo contractLeaderBoardInfo : conStatusInfoVos) {
                    boolean flag=contractLeaderBoardInfo.getContractSumRate().compareTo(new BigDecimal(5))<0;
                    boolean flag2=contractLeaderBoardInfo.getContractMoneyTotalRate().compareTo(new BigDecimal(5))<0;
                    conStatusInfoVosSumOrMoney(conStatusInfoVosSum, conStatusCodesQtSum, contractLeaderBoardInfo, flag,conStatusCodes);
                    conStatusInfoVosSumOrMoney(conStatusInfoVosMoney, conStatusCodesQtMoney, contractLeaderBoardInfo, flag2,conStatusCodes);
                }
            }
            log.info("conTypeInfoVosSum：{}",JacksonUtils.toJSONString(conTypeInfoVosSum));
            log.info("conTypeInfoVosMoney：{}",JacksonUtils.toJSONString(conTypeInfoVosMoney));
            log.info("conStatusInfoVosSum：{}",JacksonUtils.toJSONString(conStatusInfoVosSum));
            log.info("conStatusInfoVosMoney：{}",JacksonUtils.toJSONString(conStatusInfoVosMoney));
            setOtherType(conTypeInfoVosSum, contractSumAll, contractMoneyTotalAll);
            setOtherType(conTypeInfoVosMoney, contractSumAll, contractMoneyTotalAll);
            setOtherStatus(conStatusInfoVosSum, contractSumAll, contractMoneyTotalAll);
            setOtherStatus(conStatusInfoVosMoney, contractSumAll, contractMoneyTotalAll);
            log.info("conTypeInfoVosSum：{}",JacksonUtils.toJSONString(conTypeInfoVosSum));
            log.info("conTypeInfoVosMoney：{}",JacksonUtils.toJSONString(conTypeInfoVosMoney));
            log.info("conStatusInfoVosSum：{}",JacksonUtils.toJSONString(conStatusInfoVosSum));
            log.info("conStatusInfoVosMoney：{}",JacksonUtils.toJSONString(conStatusInfoVosMoney));
            contractSumAll = BigDecimal.valueOf(contractResponses.stream()
                    .filter(conContractResponse -> conContractResponse.getContractYear() != null).count());
            if (contractSumAll.compareTo(BigDecimal.ZERO)>0){
                contractMoneyTotalAll = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getContractYear() != null
                                && conContractResponse.getConMoneyTotal() != null)
                        .map(ConContractResponse::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                Map<Integer, List<ConContractResponse>> conYearMap = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getContractYear() != null)
                        .collect(Collectors.groupingBy(ConContractResponse::getContractYear));
                for (Map.Entry<Integer, List<ConContractResponse>> entry : conYearMap.entrySet()) {
                    List<ConContractResponse> responses = entry.getValue();
                    BigDecimal contractSum = BigDecimal.valueOf(responses.size());
                    BigDecimal contractSumRate = contractSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractMoneyTotal = responses.stream()
                            .map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setYear(entry.getKey());
                    contractLeaderBoardInfoVo.setContractSum(contractSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractSumRate);
                    contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotal);
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractMoneyTotalRate);
                    conYearInfoVos.add(contractLeaderBoardInfoVo);
                }
                conYearInfoVos =conYearInfoVos.stream().sorted(Comparator.comparing(ContractLeaderBoardInfoVo::getYear)).collect(Collectors.toList());
            }
        }
        if(CollectionUtil.isNotEmpty(request.getYearRange())){
            log.info("开始时间-结束时间：{} ",request.getYearRange());
            List<Integer> yearRange = request.getYearRange().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            log.info("开始时间：{} -结束时间：{}",yearRange.get(0),yearRange.get(1));
            if (!yearRange.isEmpty() &&yearRange.get(0)!=null){
                conYearInfoVos = conYearInfoVos.stream()
                        .filter(n -> n.getYear()  >= yearRange.get(0)).collect(Collectors.toList());
            }
            if (yearRange.size()==2&&yearRange.get(1)!=null){
                conYearInfoVos = conYearInfoVos.stream()
                        .filter(n -> n.getYear()  <= yearRange.get(1)).collect(Collectors.toList());
            }
            conYearInfoVos = conYearInfoVos.stream().sorted(Comparator.comparing(ContractLeaderBoardInfoVo::getYear)).collect(Collectors.toList());
        }
        BigDecimal contractSumYearAll = BigDecimal.ZERO;
        BigDecimal contractMoneyTotalYearAll = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(conYearInfoVos)) {
            for (ContractLeaderBoardInfoVo contractLeaderBoardInfo : conYearInfoVos) {
                contractSumYearAll = contractSumYearAll.add(contractLeaderBoardInfo.getContractSum());
                contractLeaderBoardInfo.setContractMoneyTotal(contractLeaderBoardInfo.getContractMoneyTotal().divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
                contractMoneyTotalYearAll = contractMoneyTotalYearAll.add(contractLeaderBoardInfo.getContractMoneyTotal());
                contractLeaderBoardInfo.setContractSumYearAll(contractSumYearAll);
                contractLeaderBoardInfo.setContractMoneyTotalYearAll(contractMoneyTotalYearAll);
            }
        }
        setContractMoneyTotal(conTypeInfoVosMoney);
        setContractMoneyTotal(conStatusInfoVosMoney);
        log.info("conTypeInfoVosSum：{}",JacksonUtils.toJSONString(conTypeInfoVosSum));
        log.info("conTypeInfoVosMoney：{}",JacksonUtils.toJSONString(conTypeInfoVosMoney));
        log.info("conStatusInfoVosSum：{}",JacksonUtils.toJSONString(conStatusInfoVosSum));
        log.info("conStatusInfoVosMoney：{}",JacksonUtils.toJSONString(conStatusInfoVosMoney));
        conYearInfoVos = conYearInfoVos.stream().sorted(Comparator.comparing(ContractLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
        data.put("contractSumAll", contractSumAll);
        data.put("contractMoneyTotalAll", contractMoneyTotalAll.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
        data.put("conType", conTypes);
        data.put("conTypeCodes", conTypeCodes);
        data.put("conTypesQtSum", conTypesQtSum);
        data.put("conTypesQtMoney", conTypesQtMoney);
        data.put("conTypeInfoVos", conTypeInfoVos);
        data.put("conTypeInfoVos2", conTypeInfoVos2);
        data.put("conTypeInfoVosSum", conTypeInfoVosSum);
        data.put("conTypeInfoVosMoney", conTypeInfoVosMoney);
        data.put("conStatus", conStatus);
        data.put("conStatusCodes", conStatusCodes);
        data.put("conStatusInfoVos", conStatusInfoVos);
        data.put("conStatusInfoVos2", conStatusInfoVos2);
        data.put("conStatusInfoVosSum", conStatusInfoVosSum);
        data.put("conStatusInfoVosMoney", conStatusInfoVosMoney);
        data.put("conStatusCodesQtSum", conStatusCodesQtSum.stream().distinct().collect(Collectors.toList()));
        data.put("conStatusCodesQtMoney", conStatusCodesQtMoney.stream().distinct().collect(Collectors.toList()));
        data.put("conYearInfoVos", conYearInfoVos);
        log.info("data:{}", JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    private static void setContractMoneyTotal(List<ContractLeaderBoardInfoVo> contractLeaderBoardInfoVos) {
        for (ContractLeaderBoardInfoVo data : contractLeaderBoardInfoVos){
            data.setContractMoneyTotal(data.getContractMoneyTotal()
                    .divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
        }
    }

    private static void setOtherType(List<ContractLeaderBoardInfoVo> conTypeInfoVosSum, BigDecimal contractSumAll, BigDecimal contractMoneyTotalAll) {
        if (CollectionUtil.isNotEmpty(conTypeInfoVosSum)){
            List<ContractLeaderBoardInfoVo> conTypeInfoVosSum2 = conTypeInfoVosSum.stream().filter(n ->
                    !"199".equals(n.getContractType())).collect(Collectors.toList());
            ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = conTypeInfoVosSum.stream()
                    .filter(n ->"199".equals(n.getContractType())).findFirst().orElse(null);
            BigDecimal contractSum2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractSum).reduce(BigDecimal::add).get();
            BigDecimal  contractSumRate2All =conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractSumRate).reduce(BigDecimal::add).get();
            BigDecimal contractMoneyTotal2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractMoneyTotal).reduce(BigDecimal::add).get();
            BigDecimal contractMoneyTotalRate2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractMoneyTotalRate).reduce(BigDecimal::add).get();
            if (contractLeaderBoardInfoVo==null){
                contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                contractLeaderBoardInfoVo.setContractType("199");
                contractLeaderBoardInfoVo.setContractTypeName("其他");
                contractLeaderBoardInfoVo.setContractStatus("199");
                contractLeaderBoardInfoVo.setContractStatusName("其他");
                contractLeaderBoardInfoVo.setContractSum(contractSumAll.subtract(contractSum2All));
                contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotalAll.subtract(contractMoneyTotal2All));
                contractLeaderBoardInfoVo.setContractSumRate(BigDecimal.valueOf(100).subtract(contractSumRate2All));
                contractLeaderBoardInfoVo.setContractMoneyTotalRate(BigDecimal.valueOf(100).subtract(contractMoneyTotalRate2All));
                conTypeInfoVosSum.add(contractLeaderBoardInfoVo);
            }else {
                contractLeaderBoardInfoVo.setContractSum(contractSumAll.subtract(contractSum2All));
                contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotalAll.subtract(contractMoneyTotal2All));
                contractLeaderBoardInfoVo.setContractSumRate(BigDecimal.valueOf(100).subtract(contractSumRate2All));
                contractLeaderBoardInfoVo.setContractMoneyTotalRate(BigDecimal.valueOf(100).subtract(contractMoneyTotalRate2All));
            }

        }
    }
    private static void setOtherStatus(List<ContractLeaderBoardInfoVo> conTypeInfoVosSum, BigDecimal contractSumAll, BigDecimal contractMoneyTotalAll) {
        if (CollectionUtil.isNotEmpty(conTypeInfoVosSum)){
            List<ContractLeaderBoardInfoVo> conTypeInfoVosSum2 = conTypeInfoVosSum.stream().filter(n ->
                    !"199".equals(n.getContractStatus())).collect(Collectors.toList());
            ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = conTypeInfoVosSum.stream()
                    .filter(n ->"199".equals(n.getContractStatus())).findFirst().orElse(null);
            BigDecimal contractSum2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractSum).reduce(BigDecimal::add).get();
            BigDecimal  contractSumRate2All =conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractSumRate).reduce(BigDecimal::add).get();
            BigDecimal contractMoneyTotal2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractMoneyTotal).reduce(BigDecimal::add).get();
            BigDecimal contractMoneyTotalRate2All = conTypeInfoVosSum2.stream()
                    .map(ContractLeaderBoardInfoVo::getContractMoneyTotalRate).reduce(BigDecimal::add).get();
            if (contractLeaderBoardInfoVo==null){
                contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                contractLeaderBoardInfoVo.setContractStatus("199");
                contractLeaderBoardInfoVo.setContractStatusName("其他");
                contractLeaderBoardInfoVo.setContractSum(contractSumAll.subtract(contractSum2All));
                contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotalAll.subtract(contractMoneyTotal2All));
                contractLeaderBoardInfoVo.setContractSumRate(BigDecimal.valueOf(100).subtract(contractSumRate2All));
                contractLeaderBoardInfoVo.setContractMoneyTotalRate(BigDecimal.valueOf(100).subtract(contractMoneyTotalRate2All));
                conTypeInfoVosSum.add(contractLeaderBoardInfoVo);
            }else {
                contractLeaderBoardInfoVo.setContractSum(contractSumAll.subtract(contractSum2All));
                contractLeaderBoardInfoVo.setContractMoneyTotal(contractMoneyTotalAll.subtract(contractMoneyTotal2All));
                contractLeaderBoardInfoVo.setContractSumRate(BigDecimal.valueOf(100).subtract(contractSumRate2All));
                contractLeaderBoardInfoVo.setContractMoneyTotalRate(BigDecimal.valueOf(100).subtract(contractMoneyTotalRate2All));
            }

        }
    }

    private void conStatusInfoVosSumOrMoney(List<ContractLeaderBoardInfoVo> conStatusInfoVosSum,
                                            List<String> conTypesQtMoney, ContractLeaderBoardInfoVo contractLeaderBoardInfo,
                                            boolean flag,List<String> conStatusCodes) {
        if (flag||StrUtil.isEmpty(contractLeaderBoardInfo.getContractStatus())){
            conTypesQtMoney.add(contractLeaderBoardInfo.getContractStatus());
        }else {
            conStatusInfoVosSum.add(contractLeaderBoardInfo);
        }
    }



    private void conTypeInfoVosSumOrMoney(List<ContractLeaderBoardInfoVo> conTypeInfoVosSum, List<String> conTypesQtSum,
                                          ContractLeaderBoardInfoVo contractLeaderBoardInfo, boolean flag,List<String> conTypeCodes) {
        if (flag||StrUtil.isEmpty(contractLeaderBoardInfo.getContractType())||
                !conTypeCodes.contains(contractLeaderBoardInfo.getContractType())){
            conTypesQtSum.add(contractLeaderBoardInfo.getContractType());
        }else {
            conTypeInfoVosSum.add(contractLeaderBoardInfo);
        }
    }

    @Override
    public BaseResponse getContractInfoList(ContractLoadRequest request) {
        log.info("ContractLoadRequest:{}", JacksonUtils.toJSONString(request));
        if (CollectionUtil.isNotEmpty(request.getContractTypes())&& request.getContractTypes().size()>=2){
            List<String> contractTypes = new ArrayList<>();
            for (String contractType : request.getContractTypes()){
                contractTypes.addAll(getSysDictionaryCodeChildByParentCode(contractType));
            }
            request.setContractTypes(contractTypes);
        }
        QueryMap<ContractLoadRequest> params = new QueryMap<>(request);
        params.put("contractType").put("contractStatus").put("projectName").put("projectCode")
                .put("contractName").put("contractCode").put("year").put("contractTypes")
                .put("contractStatuses");//列表查询最新的数据
        log.info("ContractLoadRequest params:{}", JacksonUtils.toJSONString(params));
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> conContractResponse = leaderBoardMapper.getContractInfoList(params.getQuery(), page);
        return BaseResponse.ok(conContractResponse);
    }

    @Override
    public BaseResponse getSiteManagementInfo(SiteManagementRequest request) {
        log.info("SiteManagementRequest:{}", JacksonUtils.toJSONString(request));
        String code="inspectionStatistics";
        long begin=System.currentTimeMillis();
        log.info("getSiteManagementInfo begin time :" + begin);
        String inspectionStatisticsConfig = sysConfigService.getConfigByCode(code);
        List<QsStatisticsConfigDto> statisticsConfigList=new ArrayList<>();
        if(ValidateUtil.isNotEmpty(inspectionStatisticsConfig)){
            statisticsConfigList= JSON.parseArray(inspectionStatisticsConfig,QsStatisticsConfigDto.class);
        }
        Map<String, QsType> qsTypeMap = getQsTypeMap();
        String json = sysConfigService.getConfigByCode("ProblemDescriptionStatistics");
        JSONArray jsonArray = new JSONArray(json);
        List<String> allIds0 = new ArrayList<>();
        List<String> allIds1  =  new ArrayList<>();
        if(!CollectionUtils.isEmpty(jsonArray)){
            JSONObject json0 = jsonArray.getJSONObject(0);  //质量
            JSONObject json1 = jsonArray.getJSONObject(1);  //安全
            allIds0 = findDescendants(json0.getStr("parentQstId"), qsTypeMap);//质量
            allIds1 = findDescendants(json1.getStr("parentQstId"), qsTypeMap); //安全
        }
        Map<String, Integer> qualityCount = new HashMap<>();  //质量
        Map<String, Integer> safetyCount = new HashMap<>();  //安全
        List<QsStatisticsDto> statisticsDtoList= BeanUtils.copyList(statisticsConfigList, QsStatisticsDto.class);
        log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
        List<QsPlanTaskVo> qsPlanTasksAll = leaderBoardMapper.getQsPlanTask();
        List<String> ptIds = qsPlanTasksAll.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
        List<QsRecordResponse> qsRecordsAll = leaderBoardMapper.getQsRecordList(ptIds);
        log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
        BigDecimal questionSum= BigDecimal.ZERO;
        BigDecimal questionFinishedSum =BigDecimal.ZERO;
        BigDecimal questionFinishedRate =BigDecimal.ZERO;
        BigDecimal questionFinishedLateRate =BigDecimal.ZERO;
        BigDecimal questionFinishedOnTimeSum;
        BigDecimal questionFinishedOnTimeRate =BigDecimal.ZERO;
        List<QsLeaderBoardInfoVo> qsLeaderBoardInfoVoYears =new ArrayList<>();
        List<QsLeaderBoardInfoVo> qsLeaderBoardInfoVoMonths =new ArrayList<>();
        List<Integer> years =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(qsPlanTasksAll)&&CollectionUtil.isNotEmpty(qsRecordsAll)){
            questionSum= BigDecimal.valueOf(qsRecordsAll.size());
            List<QsRecordResponse> questionFinished=qsRecordsAll.stream()
                    .filter(item -> "03".equals(item.getQsrRectifyStatus())).collect(Collectors.toList());
            questionFinishedSum =
                    BigDecimal.valueOf(questionFinished.size());
            questionFinishedRate = calculateRate(questionFinishedSum, questionSum);
            //实际整改时间大于 要求整改时间即为逾期
            final BigDecimal[] questionFinishedLate = {BigDecimal.ZERO};
            questionFinished.stream().filter(item -> item.getQsrRectifyActualTime() != null).forEach(item -> {
                if (item.getQsrRectifyActualTime().compareTo(item.getQsrRectifyRequireTime())>0) {
                    questionFinishedLate[0] = questionFinishedLate[0].add(BigDecimal.ONE);
                }
            });
            questionFinishedLateRate = calculateRate(questionFinishedLate[0], questionFinishedSum);
            //一次整改通过
            List<QsPlanTaskVo> questionFinishedOnTime = qsPlanTasksAll.stream()
                    .filter(qsPlanTaskVo -> qsPlanTaskVo.getQptIsCheck() == 0).collect(Collectors.toList());
            log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
            questionFinishedOnTimeSum= BigDecimal.valueOf(questionFinishedOnTime.size());
            log.info("getSiteManagementInfo questionFinishedOnTimeSum:{}", questionFinishedOnTimeSum);
            questionFinishedOnTimeRate = calculateRate(questionFinishedOnTimeSum, questionFinishedSum);
            //问题整改数量占比
            for (QsStatisticsDto qsStatisticsDto : statisticsDtoList) {
                QsRecordStatisticsDto qsRecordStatisticsDto=new QsRecordStatisticsDto();
                List<QsRecordResponse> qsRecords = qsRecordsAll.stream().filter(qsRecord ->ValidateUtil.isNotEmpty(qsRecord.getQstPath())
                        &&qsRecord.getQstPath().contains(qsStatisticsDto.getParentQstId())
                ).collect(Collectors.toList());
                List<QsRecordResponse> importantQuestions = qsRecords.stream().filter(qsRecord -> qsRecord.getQstIsImportant() != null && qsRecord.getQstIsImportant()==1).collect(Collectors.toList());
                int questionNum=CollectionUtil.isEmpty(qsRecords)?0:qsRecords.size();
                int importantQuestionNum=CollectionUtil.isEmpty(importantQuestions)?0:importantQuestions.size();
                qsRecordStatisticsDto.setQuestionNum(questionNum);
                qsRecordStatisticsDto.setImportantQuestionNum(importantQuestionNum);

                if(qsStatisticsDto.getTitle().equals("质量") || qsStatisticsDto.getTitle().equals("安全") ){
                    for (QsRecordResponse qsRecord : qsRecords) {
                        String qstPath = qsRecord.getQstPath();
                        String ptId = qsRecord.getPtId();
                        String[] ids = qstPath.split("/");
                        for (String id : ids) {
                            if (allIds0.contains(id)) {
                                qualityCount.put(ptId, qualityCount.getOrDefault(ptId, 0) + 1);
                            } else if (allIds1.contains(id)) {
                                safetyCount.put(ptId, safetyCount.getOrDefault(ptId, 0) + 1);
                            }
                        }
                    }
                }
                List<QsRecordResponse> rectificationList = qsRecords.stream().filter(qsRecord -> StrUtil.isNotBlank(qsRecord.getQsrRectifyStatus()) && "03".equals(qsRecord.getQsrRectifyStatus())).collect(Collectors.toList());
                Integer rectificationNum=CollectionUtil.isEmpty(rectificationList)?0:rectificationList.size();
                qsRecordStatisticsDto.setRectificationNum(rectificationNum);

                List<QsRecordResponse> importantRectificationList = rectificationList.stream().filter(qsRecord -> qsRecord.getQstIsImportant() != null && qsRecord.getQstIsImportant()==1).collect(Collectors.toList());
                Integer importantRectificationNum=CollectionUtil.isEmpty(importantRectificationList)?0:importantRectificationList.size();
                qsRecordStatisticsDto.setImportantRectificationNum(importantRectificationNum);

                double rectificationRate=0;
                if(questionNum==0){
                    rectificationRate=0;
                }else{
                    rectificationRate=((double)rectificationNum/questionNum)*100.0;
                }
                qsRecordStatisticsDto.setRectificationRate(String.format("%.2f", rectificationRate));


                double importantRectificationRate=0;
                if(importantQuestionNum==0){
                    importantRectificationRate=0;
                }else{
                    importantRectificationRate=((double)importantRectificationNum/importantQuestionNum)*100.0;
                }
                qsRecordStatisticsDto.setImportantRectificationRate(String.format("%.2f", importantRectificationRate));

                qsStatisticsDto.setQsRecordStatisticsDto(qsRecordStatisticsDto);
            }
            //年度问题统计
            Map<Integer,List<QsPlanTaskVo>> qsPlanTaskVoYearMap = qsPlanTasksAll.stream()
                    .filter(e-> ValidateUtil.isNotNull(e.getQptYear()))
                    .collect(Collectors.groupingBy(QsPlanTaskVo::getQptYear));
            years = qsPlanTaskVoYearMap.keySet().stream().sorted().collect(Collectors.toList());
            for (Integer year : years){
                for (int i = 1; i <= 12; i++){
                    QsLeaderBoardInfoVo qsLeaderBoardInfoVo = new QsLeaderBoardInfoVo();
                    qsLeaderBoardInfoVo.setYear(year);
                    qsLeaderBoardInfoVo.setMonth(i);
                    qsLeaderBoardInfoVoMonths.add(qsLeaderBoardInfoVo);
                }
            }
            List<QsLeaderBoardInfoVo> finalQsLeaderBoardInfoVoYears = qsLeaderBoardInfoVoYears;
            List<QsLeaderBoardInfoVo> finalQsLeaderBoardInfoVoMonths = qsLeaderBoardInfoVoMonths;
            qsPlanTaskVoYearMap.forEach((year, qsPlanTaskVos)->{
                QsLeaderBoardInfoVo qsLeaderBoardInfoVo = finalQsLeaderBoardInfoVoYears
                        .stream().filter(n -> year.equals(n.getYear())).findFirst().orElse(null);
                if(qsLeaderBoardInfoVo==null){
                    qsLeaderBoardInfoVo = new QsLeaderBoardInfoVo();
                    qsLeaderBoardInfoVo.setYear(year);
                    List<String> yearPtIds =qsPlanTaskVos.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
                    List<QsRecordResponse> qsRecordResponses =qsRecordsAll.stream().filter(item ->
                            yearPtIds.contains(item.getPtId())).collect(Collectors.toList());
                    BigDecimal questionSumYear = BigDecimal.valueOf(qsRecordResponses.size());
                    BigDecimal questionFinishedSumYear = BigDecimal.valueOf((int) qsRecordResponses.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count());
                    qsLeaderBoardInfoVo.setQuestionSum(questionSumYear);
                    qsLeaderBoardInfoVo.setQuestionFinishedSum(questionFinishedSumYear);
                    finalQsLeaderBoardInfoVoYears.add(qsLeaderBoardInfoVo);
                }
                //月度问题统计
                Map<Integer, List<QsPlanTaskVo>> qsPlanTaskVoMonthMap =
                        qsPlanTaskVos.stream().filter(item -> year.equals(item.getQptYear()))
                                .filter(e-> ValidateUtil.isNotNull(e.getQptMonth()))
                                .collect(Collectors.groupingBy(QsPlanTaskVo::getQptMonth));
                qsPlanTaskVoMonthMap.forEach((month, qsPlanTaskVosMonth) -> {
                    QsLeaderBoardInfoVo qsLeaderBoardInfoVoMonth= finalQsLeaderBoardInfoVoMonths
                            .stream().filter(item -> month.equals(item.getMonth())&&
                                    year.equals(item.getYear())).findFirst().orElse(null);
                    if (qsLeaderBoardInfoVoMonth == null){
                        qsLeaderBoardInfoVoMonth = new QsLeaderBoardInfoVo();
                        qsLeaderBoardInfoVoMonth.setYear(year);
                        qsLeaderBoardInfoVoMonth.setMonth(month);
                        List<String> monthPtIds =qsPlanTaskVosMonth.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
                        List<QsRecordResponse> qsRecordResponseMonths = qsRecordsAll.stream().filter(n -> monthPtIds.contains(n.getPtId())).collect(Collectors.toList());
                        BigDecimal questionSumMonth = BigDecimal.valueOf(qsRecordResponseMonths.size());
                        BigDecimal questionFinishedSumMonth = BigDecimal.valueOf((int) qsRecordResponseMonths.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count());
                        qsLeaderBoardInfoVoMonth.setQuestionSum(questionSumMonth);
                        qsLeaderBoardInfoVoMonth.setQuestionFinishedSum(questionFinishedSumMonth);
                        finalQsLeaderBoardInfoVoMonths.add(qsLeaderBoardInfoVoMonth);
                    }else {
                        List<String> monthPtIds =qsPlanTaskVosMonth.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
                        List<QsRecordResponse> qsRecordResponseMonths = qsRecordsAll.stream().filter(n -> monthPtIds.contains(n.getPtId())).collect(Collectors.toList());
                        BigDecimal questionSumMonth = BigDecimal.valueOf(qsRecordResponseMonths.size());
                        BigDecimal questionFinishedSumMonth = BigDecimal.valueOf((int) qsRecordResponseMonths.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count());
                        qsLeaderBoardInfoVoMonth.setQuestionSum(questionSumMonth);
                        qsLeaderBoardInfoVoMonth.setQuestionFinishedSum(questionFinishedSumMonth);
                    }
                });
            });
            log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
            if (CollectionUtil.isNotEmpty(request.getYearRange()) && CollectionUtil.isNotEmpty(qsLeaderBoardInfoVoYears)){
                log.info("开始时间-结束时间：{} ",request.getYearRange());
                List<Integer> yearRange = request.getYearRange().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
                log.info("开始时间：{} -结束时间：{}",yearRange.get(0),yearRange.get(1));
                Integer startYear=yearRange.get(0);
                Integer endYear=yearRange.get(1);
                qsLeaderBoardInfoVoYears=qsLeaderBoardInfoVoYears.stream()
                        .filter(n->n.getYear()>=startYear&&n.getYear()<=endYear).collect(Collectors.toList());
                qsLeaderBoardInfoVoYears.stream().sorted(Comparator.comparing(QsLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
            }
            if (request.getYear()!=null && CollectionUtil.isNotEmpty(qsLeaderBoardInfoVoMonths)){

                qsLeaderBoardInfoVoMonths=qsLeaderBoardInfoVoMonths.stream()
                        .filter(n-> Objects.equals(n.getYear(), request.getYear())).collect(Collectors.toList());
                //根据年份月份倒序排序qsLeaderBoardInfoVoMonths
                // 倒序排序：先按年份降序，再按月份降序
                qsLeaderBoardInfoVoMonths.sort((o1, o2) -> {
                    // 先比较年份
                    int yearComparison = Integer.compare(o2.getYear(), o1.getYear());
                    if (yearComparison != 0) {
                        return yearComparison;
                    }
                    // 年份相同的情况下，比较月份
                    return Integer.compare(o1.getMonth(), o2.getMonth());
                });

            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("questionSum", questionSum);
        map.put("questionFinishedSum", questionFinishedSum);
        map.put("questionFinishedRate", questionFinishedRate);
        map.put("questionFinishedLateRate", questionFinishedLateRate);
        map.put("questionFinishedOnTimeRate", questionFinishedOnTimeRate);
        map.put("statisticsDtoList", statisticsDtoList);
        map.put("years", years);
        map.put("qsLeaderBoardInfoVoYears", qsLeaderBoardInfoVoYears);
        map.put("qsLeaderBoardInfoVoMonths", qsLeaderBoardInfoVoMonths);
        log.info("map:{}", JacksonUtils.toJSONString(map));
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getSiteManagementInfoCompare(SiteManagementRequest request) {
        log.info("getSiteManagementInfoCompare");
        long begin=System.currentTimeMillis();
        List<QsPlanTaskVo> qsPlanTasksAll = leaderBoardMapper.getQsPlanTask();
        List<String> ptIds = qsPlanTasksAll.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
        List<QsRecordResponse> qsRecordsAll = leaderBoardMapper.getQsRecordList(ptIds);
        log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
        List<QsLeaderBoardInfoVo> qsLeaderBoardInfoVoMonths =new ArrayList<>();
        List<QsLeaderBoardInfoCompareVo> qsLeaderBoardInfoVoCompareMonths =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(qsPlanTasksAll)&&CollectionUtil.isNotEmpty(qsRecordsAll)){
            Map<Integer,List<QsPlanTaskVo>> qsPlanTaskVoYearMap = qsPlanTasksAll.stream()
                    .filter(e-> ValidateUtil.isNotNull(e.getQptYear()))
                    .collect(Collectors.groupingBy(QsPlanTaskVo::getQptYear));
            List<Integer> years = qsPlanTaskVoYearMap.keySet().stream().sorted().collect(Collectors.toList());
            for (Integer year : years){
                for (int i = 1; i <= 12; i++){
                    QsLeaderBoardInfoVo qsLeaderBoardInfoVo = new QsLeaderBoardInfoVo();
                    qsLeaderBoardInfoVo.setYear(year);
                    qsLeaderBoardInfoVo.setMonth(i);
                    qsLeaderBoardInfoVoMonths.add(qsLeaderBoardInfoVo);
                }
            }
            List<QsLeaderBoardInfoVo> finalQsLeaderBoardInfoVoMonths = qsLeaderBoardInfoVoMonths;
            qsPlanTaskVoYearMap.forEach((year, qsPlanTaskVos)->{
                //月度问题统计
                Map<Integer, List<QsPlanTaskVo>> qsPlanTaskVoMonthMap =
                        qsPlanTaskVos.stream().filter(item -> year.equals(item.getQptYear()))
                                .filter(e-> ValidateUtil.isNotNull(e.getQptMonth()))
                                .collect(Collectors.groupingBy(QsPlanTaskVo::getQptMonth));
                qsPlanTaskVoMonthMap.forEach((month, qsPlanTaskVosMonth) -> {
                    QsLeaderBoardInfoVo qsLeaderBoardInfoVoMonth= finalQsLeaderBoardInfoVoMonths
                            .stream().filter(item -> month.equals(item.getMonth())&&
                                    year.equals(item.getYear())).findFirst().orElse(null);
                    if (qsLeaderBoardInfoVoMonth == null){
                        qsLeaderBoardInfoVoMonth = new QsLeaderBoardInfoCompareVo();
                        qsLeaderBoardInfoVoMonth.setYear(year);
                        qsLeaderBoardInfoVoMonth.setMonth(month);
                        List<String> monthPtIds =qsPlanTaskVosMonth.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
                        List<QsRecordResponse> qsRecordResponseMonths = qsRecordsAll.stream().filter(n -> monthPtIds.contains(n.getPtId())).collect(Collectors.toList());
                        BigDecimal questionSumMonth = BigDecimal.valueOf(qsRecordResponseMonths.size());
                        BigDecimal questionFinishedSumMonth = BigDecimal.valueOf((int) qsRecordResponseMonths.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count());
                        qsLeaderBoardInfoVoMonth.setQuestionSum(questionSumMonth);
                        qsLeaderBoardInfoVoMonth.setQuestionFinishedSum(questionFinishedSumMonth);
                        finalQsLeaderBoardInfoVoMonths.add(qsLeaderBoardInfoVoMonth);
                    }else {
                        List<String> monthPtIds =qsPlanTaskVosMonth.stream().map(QsPlanTaskVo::getId).collect(Collectors.toList());
                        List<QsRecordResponse> qsRecordResponseMonths = qsRecordsAll.stream().filter(n -> monthPtIds.contains(n.getPtId())).collect(Collectors.toList());
                        BigDecimal questionSumMonth = BigDecimal.valueOf(qsRecordResponseMonths.size());
                        BigDecimal questionFinishedSumMonth = BigDecimal.valueOf((int) qsRecordResponseMonths.stream().filter(item -> "03".equals(item.getQsrRectifyStatus())).count());
                        qsLeaderBoardInfoVoMonth.setQuestionSum(questionSumMonth);
                        qsLeaderBoardInfoVoMonth.setQuestionFinishedSum(questionFinishedSumMonth);
                    }
                });
            });
            log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
            if (CollectionUtil.isNotEmpty(request.getMonthYearCompareRange()) && CollectionUtil.isNotEmpty(qsLeaderBoardInfoVoMonths)){
                log.info("主-对比时间：{} ",request.getMonthYearCompareRange());
                List<Integer> yearRange = request.getMonthYearCompareRange();
                log.info("主时间：{} -对比时间：{}",yearRange.get(0),yearRange.get(1));
                Integer mainYear=yearRange.get(0);
                Integer comparedYear=yearRange.get(1);
                if (mainYear!=null && comparedYear!=null){
                    for (QsLeaderBoardInfoVo vo : qsLeaderBoardInfoVoMonths){
                        if (mainYear.equals(vo.getYear())&& vo.getMonth()!=null){
                            QsLeaderBoardInfoCompareVo compareVo = new QsLeaderBoardInfoCompareVo();
                            BeanUtils.copyProperties(vo,compareVo);
                            QsLeaderBoardInfoVo compared = qsLeaderBoardInfoVoMonths.stream()
                                    .filter(n -> comparedYear.equals(n.getYear())
                                            && vo.getMonth().equals(n.getMonth())).findFirst().orElse(null);
                            BigDecimal questionSumMonthCompare = compared != null ? compared.getQuestionSum() : BigDecimal.ZERO;
                            BigDecimal questionFinishedSumMonthCompare = compared != null ? compared.getQuestionFinishedSum() : BigDecimal.ZERO;
                            compareVo.setQuestionSumCompare(questionSumMonthCompare);
                            compareVo.setQuestionFinishedSumCompare(questionFinishedSumMonthCompare);
                            qsLeaderBoardInfoVoCompareMonths.add(compareVo);
                        }
                    }
                }
            }
            log.info("性能监控getSiteManagementInfo time :" + (System.currentTimeMillis()-begin));
            if ( CollectionUtil.isNotEmpty(qsLeaderBoardInfoVoCompareMonths)){
                //根据年份月份倒序排序qsLeaderBoardInfoVoMonths
                // 倒序排序：先按年份降序，再按月份降序
                qsLeaderBoardInfoVoCompareMonths.sort((o1, o2) -> {
                    // 先比较年份
                    int yearComparison = Integer.compare(o2.getYear(), o1.getYear());
                    if (yearComparison != 0) {
                        return yearComparison;
                    }
                    // 年份相同的情况下，比较月份
                    return Integer.compare(o1.getMonth(), o2.getMonth());
                });

            }
        }
        return BaseResponse.ok(qsLeaderBoardInfoVoCompareMonths);
    }

    @Override
    public BaseResponse getSiteManagementInfoList(SiteManagementRequest request) {
        QueryMap<SiteManagementRequest> params = new QueryMap<>(request);
        params.put("year").put("isFinished").put("month");
        log.info("getSiteManagementInfoList params:{}", JacksonUtils.toJSONString(params));
        IPage<QsLeaderBoardInfoListVo> page = new Page<>(request.getPage(), request.getSize());
        List<QsLeaderBoardInfoListVo> qsLeaderBoardInfoVoList
                =leaderBoardMapper.getSiteManagementInfoList(params.getQuery(),page);
        for (QsLeaderBoardInfoListVo d : qsLeaderBoardInfoVoList) {
            d.setQsFinishRate(MathUtil.decimalDivide(new BigDecimal(d.getQsFinishCount()), new BigDecimal(d.getQsRecordCount()),4, RoundingMode.DOWN));
            d.setQsReplyRate(MathUtil.decimalDivide(new BigDecimal(d.getQsReplyCount()), new BigDecimal(d.getQsRecordCount()),4,RoundingMode.DOWN));
        }
        return BaseResponse.ok(qsLeaderBoardInfoVoList);
    }

    @Override
    public BaseResponse getPayInfo(PayLoadRequest request) {
        Map<String,Object> data = new HashMap<>();
        List<SysDictionary> conState = getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_HTZT"));
        Map<String,String> conStateNameMap = new HashMap<>();
        conState.forEach(e -> conStateNameMap.put(e.getdCode(),e.getdName()));
        List<String> conStateCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_HTZT"));
        List<SysDictionary> conTypes = getSysDictionaryByParentId(getSysDictionaryIdByCode("JPMsoft_HTLX"));
        Map<String,String> conTypeNameMap = new HashMap<>();
        conTypes.forEach(e -> conTypeNameMap.put(e.getdCode(),e.getdName()));
        List<String> conTypeCodes = getSysDictionaryCodeByParentId(getSysDictionaryIdByCode("JPMsoft_HTLX"));
        List<ContractLeaderBoardInfoVo> conTypeInfoVos = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVos2 = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVosSum = new ArrayList<>();
        List<ContractLeaderBoardInfoVo> conTypeInfoVosMoney = new ArrayList<>();
        List<String> conTypesQtMoney=new ArrayList<>();
        List<String> conTypesQtSum=new ArrayList<>();
        BigDecimal contractSumAll;
        BigDecimal contractMoneyTotalAll;
        List<PreProjectResponse> preProjectResponses = leaderBoardMapper.getPreProjectInfo();
        List<String> pIds = preProjectResponses.stream().map(PreProjectResponse::getpId) .filter(StrUtil::isNotEmpty).distinct().collect(Collectors.toList());
        List<ConContractResponse> contractResponses = leaderBoardMapper.getContractInfo();
        contractResponses =contractResponses.stream().filter(n->pIds.contains(n.getConProjectId())).collect(Collectors.toList());
        List<String> conIds = contractResponses.stream().map(ConContractResponse::getConId)
                .filter(StrUtil::isNotEmpty).distinct().collect(Collectors.toList());
        List<ConPayResponse> conPayResponses = leaderBoardMapper.getConPayResponse();
        conPayResponses = conPayResponses.stream().filter(n->conIds.contains(n.getConId())).collect(Collectors.toList());
        List<InvSettlementEndVO> invSettlementEnds = leaderBoardMapper.getInvSettlementEnd();
        invSettlementEnds=invSettlementEnds.stream().filter(n->conIds.contains(n.getConId())).collect(Collectors.toList());
        List<InvSettlementEndVO> finalInvSettlementEnds = invSettlementEnds;
        contractResponses.forEach(e -> {
            InvSettlementEndVO invSettlementEnd = finalInvSettlementEnds.stream()
                    .filter(n -> n.getConId().equals(e.getConId()))
                    .findFirst().orElse(null);
            if (invSettlementEnd != null &&
                    invSettlementEnd.getSaeAuditReport()!=null){
                //结算审定金额B-结算审核追加费
                e.setConMoneyTotal(invSettlementEnd.getSaeAuditReport().subtract(invSettlementEnd.getSaeIncreaseDecrease()));
            }
        });
        BigDecimal projectInvTotal =
                preProjectResponses.stream().map(PreProjectResponse::getpInvTotal)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal contractMoneyTotal =
                contractResponses.stream().map(ConContractResponse::getConMoneyTotal)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal contractMoneyTotalRate = contractMoneyTotal.divide(projectInvTotal,4,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        BigDecimal contractSum = BigDecimal.valueOf(contractResponses.size());
        List<PayLeaderBoardInfoVo> payLeaderBoardInfoVos =new ArrayList<>();
        Map<Integer, List<ConContractResponse>> contractResponsesMap = contractResponses.stream()
                .collect(Collectors.groupingBy(ConContractResponse::getContractYear));
        List<PayLeaderBoardInfoVo> finalPayLeaderBoardInfoVos = payLeaderBoardInfoVos;
        List<ConPayResponse> finalConPayResponses = conPayResponses;
        List<PayLeaderBoardInfoVo> conStatePayLeaderBoardInfoVos=new ArrayList<>();
        for (String contractState:conStateCodes){
            contractResponsesMap.forEach((year, yearContractResponses)->{
                PayLeaderBoardInfoVo payLeaderBoardInfoVo = finalPayLeaderBoardInfoVos.stream().filter(e ->
                        year.equals(e.getYear()) && contractState.equals(e.getConState())).findFirst().orElse(new PayLeaderBoardInfoVo());
                payLeaderBoardInfoVo.setYear(year);
                payLeaderBoardInfoVo.setConState(contractState);
                payLeaderBoardInfoVo.setConStateName(conStateNameMap.get(contractState));
                List<ConContractResponse> conContractStateResponses = yearContractResponses.stream()
                        .filter(e -> contractState.equals(e.getConState()))
                        .collect(Collectors.toList());
                List<String> conStateIds = conContractStateResponses.stream()
                        .map(ConContractResponse::getConId)
                        .filter(StrUtil::isNotBlank)
                        .collect(Collectors.toList());
                BigDecimal contractMoney = conContractStateResponses.stream()
                        .map(ConContractResponse::getConMoneyTotal)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                payLeaderBoardInfoVo.setContractMoney(contractMoney);
                BigDecimal payMoney = finalConPayResponses.stream()
                        .filter(n -> conStateIds.contains(n.getConId())
                                && contractState.equals(n.getConState()))
                        .map(ConPayResponse::getPayApply)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                payLeaderBoardInfoVo.setPayMoney(payMoney);
                finalPayLeaderBoardInfoVos.add(payLeaderBoardInfoVo);
            });
        }
        payLeaderBoardInfoVos=payLeaderBoardInfoVos.stream().distinct().collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(request.getYearRange2())){
            contractResponses=contractResponses.stream().filter(n->n.getContractYear()!=null).collect(Collectors.toList());
            log.info("开始时间-结束时间：{} ",request.getYearRange2());
            List<Integer> yearRange = request.getYearRange2().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            if (yearRange.size()>=1&&yearRange.get(0)!=null){
                log.info("开始时间：{} ",yearRange.get(0));
                contractResponses = contractResponses.stream()
                        .filter(n -> n.getContractYear()  >= yearRange.get(0)).collect(Collectors.toList());
            }
            if (yearRange.size()==2&&yearRange.get(1)!=null){
                log.info("结束时间：{}",yearRange.get(1));
                contractResponses = contractResponses.stream()
                        .filter(n -> n.getContractYear()  <= yearRange.get(1)).collect(Collectors.toList());
            }
        }
        if(CollectionUtil.isNotEmpty(contractResponses)) {
            contractSumAll = BigDecimal.valueOf(contractResponses.stream()
                    .filter(conContractResponse -> conContractResponse.getConType() != null).count());
            if (contractSumAll.compareTo(BigDecimal.ZERO)>0){
                contractMoneyTotalAll = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getConType() != null
                                && conContractResponse.getConMoneyTotal() != null)
                        .map(ConContractResponse::getConMoneyTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                Map<String, List<ConContractResponse>> conTypeMap = contractResponses.stream()
                        .filter(conContractResponse -> conContractResponse.getConType() != null)
                        .collect(Collectors.groupingBy(ConContractResponse::getConType));
                for (Map.Entry<String, List<ConContractResponse>> entry : conTypeMap.entrySet()) {
                    List<ConContractResponse> responses = entry.getValue();
                    BigDecimal contractTypeSum = BigDecimal.valueOf(responses.size());
                    BigDecimal contractTypeSumRate = contractTypeSum.divide(contractSumAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    BigDecimal contractTypeMoneyTotal = responses.stream()
                            .map(ConContractResponse::getConMoneyTotal)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal contractTypeMoneyTotalRate = contractTypeMoneyTotal.divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                    contractLeaderBoardInfoVo.setContractType(entry.getKey());
                    contractLeaderBoardInfoVo.setContractTypeName(conTypeNameMap.get(entry.getKey()));
                    contractLeaderBoardInfoVo.setContractSum(contractTypeSum);
                    contractLeaderBoardInfoVo.setContractSumRate(contractTypeSumRate);
                    if (contractTypeMoneyTotal.compareTo(BigDecimal.ZERO)==0){
                        contractLeaderBoardInfoVo.setContractMoneyTotal(BigDecimal.ZERO);
                    }else {
                        contractLeaderBoardInfoVo.setContractMoneyTotal(contractTypeMoneyTotal.divide(BigDecimal.valueOf(100000000), 10, RoundingMode.HALF_UP));
                    }
                    contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractTypeMoneyTotalRate);
                    conTypeInfoVos2.add(contractLeaderBoardInfoVo);
                }
                if (CollectionUtil.isNotEmpty(conTypes)){
                    for (SysDictionary conTypeCode : conTypes) {
                        ContractLeaderBoardInfoVo contractLeaderBoardInfoVo = new ContractLeaderBoardInfoVo();
                        contractLeaderBoardInfoVo.setContractType(conTypeCode.getdCode());
                        contractLeaderBoardInfoVo.setContractTypeName(conTypeCode.getdName());
                        List<String> conTypeCodeList = getSysDictionaryCodeChildByParentId(conTypeCode.getdId());
                        List<ConContractResponse> responses = contractResponses.stream()
                                .filter(conContractResponse -> conContractResponse.getConType() != null
                                        && conTypeCodeList.contains(conContractResponse.getConType()))
                                .collect(Collectors.toList());
                        contractLeaderBoardInfoVo.setContractSum(BigDecimal.valueOf(responses.size()));
                        contractLeaderBoardInfoVo.setContractSumRate(contractLeaderBoardInfoVo.getContractSum()
                                .divide(contractSumAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100)));
                        BigDecimal contractTypeMoneyTotal = responses.stream()
                                .filter(conContractResponse -> conContractResponse.getConType() != null
                                        && conTypeCodeList.contains(conContractResponse.getConType()))
                                .map(ConContractResponse::getConMoneyTotal)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (contractTypeMoneyTotal.compareTo(BigDecimal.ZERO)==0){
                            contractLeaderBoardInfoVo.setContractMoneyTotal(BigDecimal.ZERO);
                        }else {
                            contractLeaderBoardInfoVo.setContractMoneyTotal(contractTypeMoneyTotal);
                        }
                        contractLeaderBoardInfoVo.setContractMoneyTotalRate(contractLeaderBoardInfoVo.getContractMoneyTotal()
                                .divide(contractMoneyTotalAll, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100)));
                        if (contractMoneyTotal.compareTo(BigDecimal.ZERO)==0){
                            contractLeaderBoardInfoVo.setContractMoneyTotal(BigDecimal.ZERO);
                        }else {
                            contractLeaderBoardInfoVo.setContractMoneyTotal(contractTypeMoneyTotal.divide(BigDecimal.valueOf(100000000), 10, RoundingMode.HALF_UP));
                        }
                        conTypeInfoVos.add(contractLeaderBoardInfoVo);
                    }
                }
            }
        }
        if(CollectionUtil.isNotEmpty(conTypeInfoVos)){
            for (ContractLeaderBoardInfoVo contractLeaderBoardInfo : conTypeInfoVos) {
                boolean flag=contractLeaderBoardInfo.getContractSumRate().compareTo(new BigDecimal(5))<0;
                boolean flag2=contractLeaderBoardInfo.getContractMoneyTotalRate().compareTo(new BigDecimal(5))<0;
                conTypeInfoVosSumOrMoney(conTypeInfoVosSum, conTypesQtSum, contractLeaderBoardInfo, flag, conTypeCodes);
                conTypeInfoVosSumOrMoney(conTypeInfoVosMoney, conTypesQtMoney, contractLeaderBoardInfo, flag2, conTypeCodes);
            }
        }
        if(CollectionUtil.isNotEmpty(request.getYearRange())){
            payLeaderBoardInfoVos=payLeaderBoardInfoVos.stream().filter(n->n.getYear()!=null).collect(Collectors.toList());
            log.info("开始时间-结束时间：{} ",request.getYearRange());
            List<Integer> yearRange = request.getYearRange().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            if (yearRange.size()>=1&&yearRange.get(0)!=null){
                log.info("开始时间：{} ",yearRange.get(0));
                payLeaderBoardInfoVos = payLeaderBoardInfoVos.stream()
                        .filter(n -> n.getYear()  >= yearRange.get(0)).collect(Collectors.toList());
            }
            if (yearRange.size()==2&&yearRange.get(1)!=null){
                log.info("结束时间：{}",yearRange.get(1));
                payLeaderBoardInfoVos = payLeaderBoardInfoVos.stream()
                        .filter(n -> n.getYear()  <= yearRange.get(1)).collect(Collectors.toList());
            }
            payLeaderBoardInfoVos = payLeaderBoardInfoVos.stream().sorted(Comparator.comparing(PayLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
        }
        for (String contractState:conStateCodes){
            PayLeaderBoardInfoVo payLeaderBoardInfoVo = conStatePayLeaderBoardInfoVos.stream().filter(e ->
                    contractState.equals(e.getConState())).findFirst().orElse(new PayLeaderBoardInfoVo());
            payLeaderBoardInfoVo.setConState(contractState);
            payLeaderBoardInfoVo.setConStateName(conStateNameMap.get(contractState));
            BigDecimal contractMoney =payLeaderBoardInfoVos.stream()
                    .filter(f -> contractState.equals(f.getConState()))
                    .map(PayLeaderBoardInfoVo::getContractMoney)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            payLeaderBoardInfoVo.setContractMoney(contractMoney.divide(BigDecimal.valueOf(10000),6,RoundingMode.HALF_UP));
            BigDecimal payMoney =payLeaderBoardInfoVos.stream()
                    .filter(f -> contractState.equals(f.getConState()))
                    .map(PayLeaderBoardInfoVo::getPayMoney)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            payLeaderBoardInfoVo.setPayMoney(payMoney.divide(BigDecimal.valueOf(10000),6,RoundingMode.HALF_UP));
            conStatePayLeaderBoardInfoVos.add(payLeaderBoardInfoVo);
        }
        data.put("projectInvTotal",projectInvTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
        data.put("contractMoneyTotal",contractMoneyTotal.divide(BigDecimal.valueOf(100000000),10,RoundingMode.HALF_UP));
        data.put("contractMoneyTotalRate",contractMoneyTotalRate);
        data.put("contractSum",contractSum);
        data.put("conState",conState);
        data.put("conStateCodes",conStateCodes);
        data.put("payLeaderBoardInfoVos",conStatePayLeaderBoardInfoVos);
        data.put("conType", conTypes);
        data.put("conTypeCodes", conTypeCodes);
        data.put("conTypesQtSum", conTypesQtSum);
        data.put("conTypesQtMoney", conTypesQtMoney);
        data.put("conTypeInfoVos", conTypeInfoVos);
        data.put("conTypeInfoVos2", conTypeInfoVos2);
        data.put("conTypeInfoVosSum", conTypeInfoVosSum);
        data.put("conTypeInfoVosMoney", conTypeInfoVosMoney);
        log.info("data:{}",JSON.toJSONString(data, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPayInfoList(PayLoadRequest request) {
        log.info("PayLoadRequest:{}", JacksonUtils.toJSONString(request));
        QueryMap<PayLoadRequest> params = new QueryMap<>(request);
        params.put("contractState").put("yearRange");//列表查询最新的数据
        log.info("getPayInfoList params:{}", JacksonUtils.toJSONString(params));
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> conContractResponse = leaderBoardMapper.getPayInfoList(params.getQuery(), page);
        return BaseResponse.ok(conContractResponse);
    }

    @Override
    public BaseResponse getPlanInfo(PlanLoadRequest request) {
        Map<String, Object> data = new HashMap<>();
        List<PlanLeaderBoardInfoVo> yearBoardInfoVoList = new ArrayList<>();
        List<PlanLeaderBoardInfoVo> monthBoardInfoVoList = new ArrayList<>();
        List<InvPlanYearResponse> invPlanYearResponses = leaderBoardMapper.getInvPlanYears();
        List<String> ipvIds = invPlanYearResponses.stream().map(InvPlanYearResponse::getIpvId).collect(Collectors.toList());
        List<InvMonthlyScheduleResponse> invMonthlyScheduleResponses = leaderBoardMapper.getInvMonthlySchedule(ipvIds);
        Map<String, List<InvPlanYearResponse>> invPlanYearResponseMap = invPlanYearResponses.stream()
                .collect(Collectors.groupingBy(InvPlanYearResponse::getIpvDeclareYear));
        List<ConPayResponse> conPayResponses = leaderBoardMapper.getConPayResponse2();
        final BigDecimal[] totalInv = {BigDecimal.ZERO};
        List<PlanLeaderBoardInfoVo> finalMonthBoardInfoVoList = monthBoardInfoVoList;
        List<PlanLeaderBoardInfoVo> finalYearBoardInfoVoList = yearBoardInfoVoList;
        invPlanYearResponseMap.forEach((ipvDeclareYear, invPlanYearResponse) -> {
            Integer  year= Integer.parseInt(ipvDeclareYear);
            PlanLeaderBoardInfoVo planLeaderBoardInfoVo = finalYearBoardInfoVoList.stream().filter(n
                    -> year.equals(n.getYear())).findFirst().orElse(new PlanLeaderBoardInfoVo());
            planLeaderBoardInfoVo.setYear(Integer.valueOf(ipvDeclareYear));
            List<String> ipvYearIds = invPlanYearResponse.stream()
                    .map(InvPlanYearResponse::getIpvId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            List<InvMonthlyScheduleResponse> invYearMonthlyScheduleResponses = invMonthlyScheduleResponses.stream()
                    .filter(n -> ipvYearIds.contains(n.getIpvId())).collect(Collectors.toList());
            planLeaderBoardInfoVo.setFundPlan(invYearMonthlyScheduleResponses.stream()
                    .map(InvMonthlyScheduleResponse::getImsPlannedInv)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            List<String>  ipvYearConIds = invPlanYearResponse.stream()
                    .map(InvPlanYearResponse::getConId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            List<ConPayResponse> conPayIpvYearResponses = conPayResponses.stream()
                    .filter(n -> ipvYearConIds.contains(n.getConId())&& year.equals(n.getPayYear()))
                    .collect(Collectors.toList());
            BigDecimal fundActual=conPayIpvYearResponses.stream()
                    .map(ConPayResponse::getPayApply)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalInv[0] = totalInv[0].add(fundActual);
            planLeaderBoardInfoVo.setFundActual(fundActual);
            planLeaderBoardInfoVo.setFundActualTotal(totalInv[0]);
            finalYearBoardInfoVoList.add(planLeaderBoardInfoVo);
            final BigDecimal[] totalMonthInv = {BigDecimal.ZERO};
            Map<Integer, List<InvMonthlyScheduleResponse>> monthlyScheduleMap = invMonthlyScheduleResponses.stream()
                    .collect(Collectors.groupingBy(InvMonthlyScheduleResponse::getImsMonth));
            monthlyScheduleMap.forEach((month, monthlyScheduleResponses) -> {
                PlanLeaderBoardInfoVo monthlySchedulePlanLeaderBoardInfoVo = finalMonthBoardInfoVoList.stream().filter(
                        n -> year.equals(n.getYear())
                                && month.equals(n.getMonth())).findFirst().orElse(new PlanLeaderBoardInfoVo());
                monthlySchedulePlanLeaderBoardInfoVo.setYear(year);
                monthlySchedulePlanLeaderBoardInfoVo.setMonth(month);
                monthlySchedulePlanLeaderBoardInfoVo.setFundPlan(monthlyScheduleResponses.stream()
                        .map(InvMonthlyScheduleResponse::getImsPlannedInv)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                List<ConPayResponse> conPayIpvYearMonthResponses = conPayIpvYearResponses.stream()
                        .filter(n ->  month.equals(n.getPayMonth()))
                        .collect(Collectors.toList());
                BigDecimal fundMonthActual=conPayIpvYearMonthResponses.stream()
                        .map(ConPayResponse::getPayApply)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                totalMonthInv[0] = totalMonthInv[0].add(fundMonthActual);
                monthlySchedulePlanLeaderBoardInfoVo.setFundActual(fundMonthActual);
                monthlySchedulePlanLeaderBoardInfoVo.setFundActualTotal(totalMonthInv[0]);
                finalMonthBoardInfoVoList.add(monthlySchedulePlanLeaderBoardInfoVo);
            });
        });
        if(CollectionUtil.isNotEmpty(request.getYearRange())){
            yearBoardInfoVoList=yearBoardInfoVoList.stream().filter(n->n.getYear()!=null).collect(Collectors.toList());
            monthBoardInfoVoList=monthBoardInfoVoList.stream().filter(n->n.getYear()!=null).collect(Collectors.toList());
            log.info("开始时间-结束时间：{} ",request.getYearRange());
            List<Integer> yearRange = request.getYearRange().stream().filter(Objects::nonNull).sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            if (yearRange.size()>=1&&yearRange.get(0)!=null){
                log.info("开始时间：{} ",yearRange.get(0));
                yearBoardInfoVoList = yearBoardInfoVoList.stream()
                        .filter(n -> n.getYear()  >= yearRange.get(0)).collect(Collectors.toList());
                monthBoardInfoVoList = monthBoardInfoVoList.stream()
                        .filter(n -> n.getYear()  >= yearRange.get(0)).collect(Collectors.toList());
            }
            if (yearRange.size()==2&&yearRange.get(1)!=null){
                log.info("结束时间：{}",yearRange.get(1));
                yearBoardInfoVoList = yearBoardInfoVoList.stream()
                        .filter(n -> n.getYear()  <= yearRange.get(1)).collect(Collectors.toList());
                monthBoardInfoVoList = monthBoardInfoVoList.stream()
                        .filter(n -> n.getYear()  <= yearRange.get(1)).collect(Collectors.toList());
            }
            monthBoardInfoVoList = monthBoardInfoVoList.stream().sorted(Comparator.comparing(PlanLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
            yearBoardInfoVoList = yearBoardInfoVoList.stream().sorted(Comparator.comparing(PlanLeaderBoardInfoVo::getYear).reversed()).collect(Collectors.toList());
        }
        data.put("yearBoardInfoVoList", yearBoardInfoVoList);
        data.put("monthBoardInfoVoList", monthBoardInfoVoList);
        log.info("data:{}",JSON.toJSONString(data, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPlanInfoList(PlanLoadRequest request) {
        log.info("PayLoadRequest:{}", JacksonUtils.toJSONString(request));
        QueryMap<PlanLoadRequest> params = new QueryMap<>(request);
        params.put("year").put("projectName").put("contractName");//列表查询最新的数据
        log.info("getPlanInfoList params:{}", JacksonUtils.toJSONString(params));
        IPage<PlanLeaderBoardInfoListVo> page = new Page<>(request.getPage(), request.getSize());
        List<PlanLeaderBoardInfoListVo> planLeaderBoardInfoListVo = leaderBoardMapper.getPlanInfoList(params.getQuery(), page);
        return BaseResponse.ok(planLeaderBoardInfoListVo);
    }

    @Override
    public BaseResponse getAnnualMaintenanceBudgetInfo(ProjectLoadRequest request) {
        // if (request.getYear()==null){
        //     return BaseResponse.fail("缺失年份参数");
        // }
        // Date startDate = DateUtil.parse(request.getYear() + "-01-01");
        // Date endDate = DateUtil.parse(request.getYear()+1 + "-01-01");
        AnnualMaintenanceBudgetVo annualMaintenanceBudgetVo = new AnnualMaintenanceBudgetVo();
        // AnnualPlan annualPlan = annualPlanMapper.selectOne(new QueryWrapper<AnnualPlan>()
        //         .eq("year", request.getYear()).eq("state", ApprovalStatusEnum.Already_Over.getIndex()));

        if (ValidateUtil.isEmpty(request.getId())){
            return BaseResponse.fail("缺失计划ID参数");
        }
        AnnualPlan annualPlan = annualPlanMapper.selectById(request.getId());

        if (annualPlan == null) {
            log.error("查询失败：ID为 {} 的年度计划不存在", request.getId());
            return BaseResponse.fail("未找到年度计划数据");
        }

        boolean hasTimeRange = annualPlan.getStartTime() != null && annualPlan.getEndTime() != null;

        QueryWrapper<MaintenanceRecord> maintenanceRecordqueryWrapper = new QueryWrapper<>();

        maintenanceRecordqueryWrapper.between(hasTimeRange, "mr_report_time", annualPlan.getStartTime(), annualPlan.getEndTime())
                .notIn("mr_state", Arrays.asList(ApprovalStatusEnum.Not_Started.getIndex(),ApprovalStatusEnum.Returned_Initiator.getIndex(),
                        ApprovalStatusEnum.Invalid.getIndex(),ApprovalStatusEnum.Returned.getIndex()));

        if (StrUtil.isNotEmpty(request.getProjectName())){
            maintenanceRecordqueryWrapper.like("p_name", request.getProjectName());
        }
        List<MaintenanceRecord> mrList = maintenanceRecordMapper.selectList(maintenanceRecordqueryWrapper);
        List<String> mrIdList = mrList.stream().map(MaintenanceRecord::getMrId).collect(Collectors.toList());
        QueryWrapper<ConstructionCompletionConfirm> ccQueryWrapper =new QueryWrapper<ConstructionCompletionConfirm>()
                .ne("state", ApprovalStatusEnum.Invalid.getIndex());
        if(CollectionUtil.isNotEmpty(mrIdList)){
            ccQueryWrapper.in("mr_id", mrIdList);
        }
        List<ConstructionCompletionConfirm> ccList = constructionCompletionConfirmMapper.selectList(ccQueryWrapper);
        List<String> ccIdList = ccList.stream().map(ConstructionCompletionConfirm::getId).collect(Collectors.toList());
        QueryWrapper<InvSettlementApply> saQueryWrapper =new QueryWrapper<InvSettlementApply>()
                .ne("sa_state", ApprovalStatusEnum.Invalid.getIndex());

        List<InvSettlementApplyDtl> applyDtlList = invSettlementApplyDtlService.list(Wrappers.<InvSettlementApplyDtl>lambdaQuery().in(ValidateUtil.isNotEmpty(ccIdList),InvSettlementApplyDtl::getCompletionConfirmId, ccIdList));
        List<String> isaId = applyDtlList.stream().map(InvSettlementApplyDtl::getIsaId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(isaId)){
            saQueryWrapper.in("sa_Id", isaId);
        }
        List<InvSettlementApply> saList = invSettlementApplyMapper.selectList(saQueryWrapper);
        List<String> saIdList = saList.stream().map(InvSettlementApply::getSaId).collect(Collectors.toList());
        QueryWrapper<InvSettlementPay> payQueryWrapper =new QueryWrapper<InvSettlementPay>()
                .ne("state", ApprovalStatusEnum.Invalid.getIndex());
        if (CollectionUtil.isNotEmpty(saIdList)){
            payQueryWrapper.in("inv_settlement_apply_id", saIdList);
        }

        List<InvSettlementPay> payList = invSettlementPayMapper.selectList(payQueryWrapper);

        Integer repairApproval = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Repair_Project_Application_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer repairApprovalFinish = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Repair_Project_Application_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer constructionCompletionConfirmApproval = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Construction_Completion_Confirmation_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer constructionCompletionConfirmFinish = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Construction_Completion_Confirmation_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer settlementSubmissionApproval = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer settlementSubmissionFinish = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer suspendingPaymentApproval = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Warranty_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();
        Integer suspendingPaymentFinish = mrList.stream().filter(n ->
                MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex().equals(n.getMrRepairState())
                        || MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex().equals(n.getMrRepairState())).collect(Collectors.toList()).size();

        annualMaintenanceBudgetVo.setRepairApproval(repairApproval);
        annualMaintenanceBudgetVo.setRepairApprovalFinish(repairApprovalFinish);

        annualMaintenanceBudgetVo.setConstructionCompletionConfirmApproval(constructionCompletionConfirmApproval);
        annualMaintenanceBudgetVo.setConstructionCompletionConfirmFinish(constructionCompletionConfirmFinish);

        annualMaintenanceBudgetVo.setSettlementSubmissionApproval(settlementSubmissionApproval);
        annualMaintenanceBudgetVo.setSettlementSubmissionFinish(settlementSubmissionFinish);

        annualMaintenanceBudgetVo.setSuspendingPaymentApproval(suspendingPaymentApproval);
        annualMaintenanceBudgetVo.setSuspendingPaymentFinish(suspendingPaymentFinish);

        // 质保金
        List<InvSettlementPayModel> notReturnedInvSettlementPay = invSettlementPayMapper.getNotReturnedInvSettlementPay();
        if (ValidateUtil.isNotEmpty(notReturnedInvSettlementPay)) {
            notReturnedInvSettlementPay = notReturnedInvSettlementPay.stream().filter(n -> saIdList.contains(n.getInvSettlementApplyId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(notReturnedInvSettlementPay)) {

                annualMaintenanceBudgetVo.setGuaranteeAmountNotReturned(
                        notReturnedInvSettlementPay.stream()
                                .map(InvSettlementPayModel::getZbjPayAmount)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                );

                annualMaintenanceBudgetVo.setGuaranteeNotReturnedCount(notReturnedInvSettlementPay.size());
            } else {
                annualMaintenanceBudgetVo.setGuaranteeAmountNotReturned(BigDecimal.ZERO);
                annualMaintenanceBudgetVo.setGuaranteeNotReturnedCount(0);
            }
        } else {
            annualMaintenanceBudgetVo.setGuaranteeAmountNotReturned(BigDecimal.ZERO);
            annualMaintenanceBudgetVo.setGuaranteeNotReturnedCount(0);
        }

        //计划预算金额总额
        BigDecimal planBudgetTotal;
        //预算金额实用总额
        BigDecimal budgetTotal ;
        //计划预算金额剩余总额 =计划预算金额总额-预算金额实用总额
        BigDecimal planBudgetTotalSurplus;
        //计划支付金额总额
        BigDecimal planPayTotal;
        //支付金额实付总额
        BigDecimal payTotal;
        //计划支付金额剩余总额
        BigDecimal planPayTotalSurplus;
        //预算金额使用占比: 预算金额实用金额/计算预算金额总额
        BigDecimal budgetTotalUse;
        //支付金额使用占比:支付金额实付总额/计划支付金额总额
        BigDecimal payTotalUse;
        //计划预算金额总额
        planBudgetTotal  =annualPlan!=null?annualPlan.getPreAmount().multiply(BigDecimal.valueOf(10000)) : BigDecimal.ZERO;
        annualMaintenanceBudgetVo.setPlanBudgetTotal(planBudgetTotal);
        //预算金额实用总额
        budgetTotal = mrList.stream().map(MaintenanceRecord::getMrReportAmountPre).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        annualMaintenanceBudgetVo.setBudgetTotal(budgetTotal);
        //BigDecimal budgetTotal = saList.stream().map(InvSettlementApply::getSaAmountTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        //计划预算金额剩余总额 =计划预算金额总额-预算金额实用总额
        planBudgetTotalSurplus = planBudgetTotal.subtract(budgetTotal);
        annualMaintenanceBudgetVo.setPlanBudgetTotalSurplus(planBudgetTotalSurplus);
        //计划支付金额总额
        planPayTotal = annualPlan!=null? annualPlan.getPayAmount().multiply(BigDecimal.valueOf(10000)) : BigDecimal.ZERO;
        annualMaintenanceBudgetVo.setPlanPayTotal(planPayTotal);
        //支付金额实付总额
        payTotal = payList.stream().map(InvSettlementPay::getPayAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        annualMaintenanceBudgetVo.setPayTotal(payTotal);
        //计划支付金额剩余总额
        planPayTotalSurplus = planPayTotal.subtract(payTotal);
        annualMaintenanceBudgetVo.setPlanPayTotalSurplus(planPayTotalSurplus);
        //预算金额使用占比: 预算金额实用金额/计算预算金额总额
        budgetTotalUse =calculateRate(budgetTotal, planBudgetTotal);
        annualMaintenanceBudgetVo.setBudgetTotalUse(budgetTotalUse);
        //支付金额使用占比:支付金额实付总额/计划支付金额总额
        payTotalUse =calculateRate(payTotal, planPayTotal);
        annualMaintenanceBudgetVo.setPayTotalUse(payTotalUse);

        List<AnnualMaintenanceBudgetListVo> annualMaintenanceBudgetListVos = new ArrayList<>();
        if (annualPlan != null && annualPlan.getStartTime() != null && annualPlan.getEndTime() != null) {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(annualPlan.getStartTime());
            startCal.set(Calendar.DAY_OF_MONTH, 1);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(annualPlan.getEndTime());
            endCal.set(Calendar.DAY_OF_MONTH, 1);

            // 遍历开始年月到结束年月
            while (!startCal.after(endCal)) {
                AnnualMaintenanceBudgetListVo n = new AnnualMaintenanceBudgetListVo();
                n.setYear(startCal.get(Calendar.YEAR));
                n.setMonth(startCal.get(Calendar.MONTH) + 1);
                n.setRepairProjectNum(0);
                n.setRepairProjectBudget(BigDecimal.ZERO);
                annualMaintenanceBudgetListVos.add(n);
                startCal.add(Calendar.MONTH, 1);
            }
        }

        if (CollectionUtil.isNotEmpty(mrList)) {
            mrList.forEach(n -> {
                if (n.getMrReportTime() == null) {
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(n.getMrReportTime());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;

                // 匹配年份和月份
                AnnualMaintenanceBudgetListVo annualMaintenanceBudgetListVo =
                        annualMaintenanceBudgetListVos.stream()
                                .filter(m -> m.getYear().equals(year) && m.getMonth().equals(month))
                                .findFirst().orElse(null);

                if (annualMaintenanceBudgetListVo != null) {
                    annualMaintenanceBudgetListVo.setRepairProjectNum(annualMaintenanceBudgetListVo.getRepairProjectNum() + 1);
                    if (n.getMrReportAmountPre() != null) {
                        annualMaintenanceBudgetListVo.setRepairProjectBudget(annualMaintenanceBudgetListVo.getRepairProjectBudget()
                                .add(n.getMrReportAmountPre()));
                    }
                }
            });
        }
        annualMaintenanceBudgetVo.setAnnualMaintenanceBudgetList(annualMaintenanceBudgetListVos);
        log.info("annualVo:{}", JSON.toJSONString(annualMaintenanceBudgetVo, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(annualMaintenanceBudgetVo);
    }

    @Override
    public BaseResponse getAnnualMaintenanceBudgetInfoList(ProjectLoadRequest request) {

        if (ValidateUtil.isEmpty(request.getId())){
            return BaseResponse.fail("缺失计划ID参数");
        }

        AnnualPlan annualPlan = annualPlanMapper.selectById(request.getId());
        if (annualPlan == null) {
            return BaseResponse.fail("未找到对应的年度计划");
        }

        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<ProjectLoadRequest> queryMap = new QueryMap<>(request);

        queryMap.put("year").put("month");
        queryMap.put("startTime", annualPlan.getStartTime());
        queryMap.put("endTime", annualPlan.getEndTime());
        if (StrUtil.isNotEmpty(request.getListType())){
//            if ("repairApproval".equals(request.getListType())){
//                queryMap.put("mrRepairState", "01");
//            }else if ("repairApprovalFinish".equals(request.getListType())){
//                queryMap.put("mrRepairState", "02");
//            }else if ("constructionCompletionConfirmApproval".equals(request.getListType())){
//                queryMap.put("mrRepairState", "03");
//            }else if ("constructionCompletionConfirmFinish".equals(request.getListType())){
//                queryMap.put("mrRepairState", "04");
//            }else if ("settlementSubmissionApproval".equals(request.getListType())){
//                queryMap.put("mrRepairState", "05");
//            }else if ("settlementSubmissionFinish".equals(request.getListType())){
//                queryMap.put("mrRepairState", "06");
//            }else if ("suspendingPaymentApproval".equals(request.getListType())){
//                queryMap.put("mrRepairState", "07");
//            }else if ("suspendingPaymentFinish".equals(request.getListType())){
//                queryMap.put("mrRepairState", "08");
//            }
            List<String> mrRepairStateList = new ArrayList<>();
            if ("repairApproval".equals(request.getListType())){
                mrRepairStateList.add("01");
                mrRepairStateList.add("02");
                mrRepairStateList.add("03");
                mrRepairStateList.add("04");
                mrRepairStateList.add("05");
                mrRepairStateList.add("06");
                mrRepairStateList.add("07");
                mrRepairStateList.add("08");
                mrRepairStateList.add("09");
                mrRepairStateList.add("10");
                queryMap.put("mrRepairStates", mrRepairStateList);
            }else if ("constructionCompletionConfirmApproval".equals(request.getListType())){
                mrRepairStateList.add("03");
                mrRepairStateList.add("04");
                mrRepairStateList.add("05");
                mrRepairStateList.add("06");
                mrRepairStateList.add("07");
                mrRepairStateList.add("08");
                mrRepairStateList.add("09");
                mrRepairStateList.add("10");
                queryMap.put("mrRepairStates", mrRepairStateList);
            }else if ("settlementSubmissionApproval".equals(request.getListType())){
                mrRepairStateList.add("05");
                mrRepairStateList.add("06");
                mrRepairStateList.add("07");
                mrRepairStateList.add("08");
                mrRepairStateList.add("09");
                mrRepairStateList.add("10");
                queryMap.put("mrRepairStates", mrRepairStateList);
            }else if ("suspendingPaymentApproval".equals(request.getListType())){
                mrRepairStateList.add("07");
                mrRepairStateList.add("08");
                mrRepairStateList.add("09");
                mrRepairStateList.add("10");
                queryMap.put("mrRepairStates", mrRepairStateList);
            }
        }
        log.info("查询列表:{}", JSON.toJSONString(queryMap, JSONWriter.Feature.ReferenceDetection));
        IPage<MaintenanceRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordModel> list = maintenanceRecordMapper.getList2(queryMap.getQuery(),page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    private BigDecimal calculateRate(BigDecimal childCount, BigDecimal allCount) {
        BigDecimal rate = BigDecimal.ZERO;
        if (allCount.compareTo(BigDecimal.ZERO) != 0) {
            rate = childCount.divide(allCount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        return rate;
    }
}