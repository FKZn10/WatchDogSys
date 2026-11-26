package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.FilterObject;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditChangeLogModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.SettlementFirstAuditChangeLogMapper;
import com.jpmsoft.projectProducer.mapper.investment.SettlementFirstAuditMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAudit;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAuditChangeLog;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.request.investment.SettlementFirstAuditRequest;
import com.jpmsoft.projectProducer.service.investment.ISettlementFirstAuditService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ISettlementFirstAuditServiceImpl extends ServiceImpl<SettlementFirstAuditMapper, SettlementFirstAudit> implements ISettlementFirstAuditService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SettlementFirstAuditChangeLogMapper settlementFirstAuditChangeLogMapper;
    @Override
    public BaseResponse saveOrUpdateSettlementFirstAudit(SettlementFirstAuditModel request, String uid) {
        log.info("SettlementFirstAuditModel:{}", JacksonUtils.toJSONString(request));
        String primaryId = request.getId();
        if(StrUtil.isEmpty(primaryId)){
            primaryId = GenerateUUID.getUUID();
            SettlementFirstAudit entity = new SettlementFirstAudit();
            BeanUtil.copyProperties(request,entity);
            entity.setId(primaryId);
            entity.setCreateUid(uid);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setDelFlag(1);
            entity.setStatus(1);
            //上下班时间加上年月份存储
            log.info("SettlementFirstAudit:{}", JacksonUtils.toJSONString(entity));
            baseMapper.insert(entity);
            List<SupportDocumentDto> documentDtos = getSupportDocumentDtos(request);
            for (SupportDocumentDto supportDocumentDto:documentDtos){
                sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()),
                        Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
            }
            if(!sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){
                return BaseResponse.fail("新增失败");
            }
        }else {
            SettlementFirstAudit entity = baseMapper.selectById(primaryId);
            if(entity == null){
                return new BaseResponse("信息不存在！");
            }else {
                List<SupportDocumentDto> documentDtos = getSupportDocumentDtos(request);
                for (SupportDocumentDto supportDocumentDto:documentDtos){
                    sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()),
                            Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
                }
                BeanUtil.copyProperties(request,entity);
                baseMapper.updateById(entity);
            }
        }
        return BaseResponse.ok(primaryId);
    }

    private static List<SupportDocumentDto> getSupportDocumentDtos(SettlementFirstAuditModel request) {
        return getSupportDocumentDtoLists(request.getFileRelationId(), request.getFileIds(), request.getSfaSettlementFirstDecisionDoc(), request.getSfaSettlementFirstDecisionDocs(), request.getSfaSettlementDocUrl(), request.getSfaSettlementDocUrls());
    }

    @Override
    public BaseResponse getSettlementFirstAuditList(SettlementFirstAuditRequest request) {
        QueryMap<SettlementFirstAuditRequest> params = new QueryMap<>(request);
        List<ClassInfoAnnotation> projectClassInfoAnnotations = getClassInfoAnnotations();
        for (ClassInfoAnnotation classInfoAnnotation: projectClassInfoAnnotations){
            params.put(classInfoAnnotation.getValue());
        }
        IPage<SettlementFirstAuditModel> page = new Page<>(request.getPage(), request.getSize());
        List<SettlementFirstAuditModel> datas = baseMapper.getSettlementFirstAuditList(params.getQuery(),page);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getSettlementFirstAuditId(String id) {
        SettlementFirstAuditModel settlementFirstAuditModel = baseMapper.selectModelById(id);
        if(settlementFirstAuditModel == null){
            return BaseResponse.fail("信息不存在！");
        }
        List<ConEcResponse> conEcResponseList = baseMapper.getContractInfo(settlementFirstAuditModel.getContractId());
        if (CollectionUtil.isNotEmpty(conEcResponseList)){
            BigDecimal contractTotalMoneyChange = BigDecimal.ZERO;
            for (ConEcResponse conEcResponse : conEcResponseList){
                contractTotalMoneyChange = contractTotalMoneyChange.add(conEcResponse.getEcTotalMoneyC());
            }
            settlementFirstAuditModel.setContractTotalMoneyChange(contractTotalMoneyChange);
        }
        List<SettlementFirstAuditChangeLogModel> settlementFirstAuditChangeLogModels
                = settlementFirstAuditChangeLogMapper.getSettlementFirstAuditChangeLogs(id);
        settlementFirstAuditModel.setChangeLogModels(settlementFirstAuditChangeLogModels);
        return BaseResponse.ok(settlementFirstAuditModel);
    }

    @Override
    public BaseResponse getSettlementFirstAuditClassInfo(SettlementFirstAuditRequest request) {
        List<ClassInfoAnnotation> settlementFirstAuditClassInfoAnnotations = getClassInfoAnnotations();
        settlementFirstAuditClassInfoAnnotations=settlementFirstAuditClassInfoAnnotations.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        log.info("settlementFirstAuditClassInfoAnnotations:{}",JacksonUtils.toJSONString(settlementFirstAuditClassInfoAnnotations));
        return BaseResponse.ok(settlementFirstAuditClassInfoAnnotations);
    }

    @Override
    public BaseResponse saveOrUpdateSettlementFirstAuditChangeLog(SettlementFirstAuditChangeLogModel request, String uid) {
        log.info("SettlementFirstAuditChangeLogModel:{}", JacksonUtils.toJSONString(request));
        String primaryId = request.getId();
        if(StrUtil.isEmpty(primaryId)){
            primaryId = GenerateUUID.getUUID();
            SettlementFirstAuditChangeLog entity = new SettlementFirstAuditChangeLog();
            BeanUtil.copyProperties(request,entity);
            entity.setChangeTime(DateTime.now());
            entity.setId(primaryId);
            entity.setCreateUid(uid);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            //上下班时间加上年月份存储
            log.info("SettlementFirstAudit:{}", JacksonUtils.toJSONString(entity));
            settlementFirstAuditChangeLogMapper.insert(entity);
            List<SupportDocumentDto> documentDtos = getSupportDocumentDtos(request);
            for (SupportDocumentDto supportDocumentDto:documentDtos){
                sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()),
                        Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
            }
            if(!sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){
                return BaseResponse.fail("新增失败");
            }
        }else {
            SettlementFirstAuditChangeLog entity = settlementFirstAuditChangeLogMapper.selectById(primaryId);
            if(entity == null){
                return new BaseResponse("信息不存在！");
            }else {
                BeanUtil.copyProperties(request,entity);
                entity.setChangeTime(DateTime.now());
                List<SupportDocumentDto> documentDtos = getSupportDocumentDtos(request);
                for (SupportDocumentDto supportDocumentDto:documentDtos){
                    sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()),
                            Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
                }
                settlementFirstAuditChangeLogMapper.updateById(entity);
            }
        }
        return BaseResponse.ok(primaryId);
    }
    private static List<SupportDocumentDto> getSupportDocumentDtos(SettlementFirstAuditChangeLogModel request) {
        return getSupportDocumentDtoLists(request.getFileRelationId(), request.getFileIds(), request.getSfaSettlementFirstDecisionDoc(), request.getSfaSettlementFirstDecisionDocs(), request.getSfaSettlementDocUrl(), request.getSfaSettlementDocUrls());
    }

    private static List<SupportDocumentDto> getSupportDocumentDtoLists(String fileRelationId, String fileIds,
                                                                       String sfaSettlementFirstDecisionDoc, String sfaSettlementFirstDecisionDocs,
                                                                       String sfaSettlementDocUrl, String sfaSettlementDocUrls) {
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto documentDto =new SupportDocumentDto();
        documentDto.setSupportDocument(fileRelationId);
        documentDto.setSupportDocumentFileIDs(fileIds);
        documentDtos.add(documentDto);
        documentDto.setSupportDocument(sfaSettlementFirstDecisionDoc);
        documentDto.setSupportDocumentFileIDs(sfaSettlementFirstDecisionDocs);
        documentDtos.add(documentDto);
        documentDto.setSupportDocument(sfaSettlementDocUrl);
        documentDto.setSupportDocumentFileIDs(sfaSettlementDocUrls);
        documentDtos.add(documentDto);
        return documentDtos;
    }

    @Override
    public BaseResponse getSettlementFirstAuditChangeLogList(SettlementFirstAuditModel request) {
        QueryMap<SettlementFirstAuditModel> params = new QueryMap<>(request);
        params.put("sfaId",request.getId());
        IPage<SettlementFirstAuditModel> page = new Page<>(request.getPage(), request.getSize());
        List<SettlementFirstAuditModel> datas =
                settlementFirstAuditChangeLogMapper.getSettlementFirstAuditChangeLogList(params.getQuery(),page);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse approveChangeLogData(SettlementFirstAuditChangeLogModel request) {
        String id = request.getId();
        SettlementFirstAuditChangeLog entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = settlementFirstAuditChangeLogMapper.selectById(id);
            if (entity == null)
                return BaseResponse.fail("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            settlementFirstAuditChangeLogMapper.updateById(entity);
            if (entity.getState() == ApprovalStatusEnum.Already_Over.getIndex()){
                SettlementFirstAudit settlementFirstAudit = baseMapper.selectById(entity.getSfaId());
                BeanUtil.copyProperties(entity,settlementFirstAudit);
                baseMapper.updateById(settlementFirstAudit);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse loadContractInfo(SettlementFirstAuditModel request) {
        String contractId = request.getContractId();
        if (StrUtil.isEmpty(contractId)){
            return BaseResponse.fail("缺少请求参数");
        }
        List<ConEcResponse> conEcResponseList = baseMapper.getContractInfo(contractId);
        if (CollectionUtil.isNotEmpty(conEcResponseList)){
            BigDecimal contractTotalMoneyChange = BigDecimal.ZERO;
            for (ConEcResponse conEcResponse : conEcResponseList){
                contractTotalMoneyChange = contractTotalMoneyChange.add(conEcResponse.getEcTotalMoneyC());
            }
            request.setContractTotalMoneyChange(contractTotalMoneyChange);
        }
        return BaseResponse.ok(request);
    }

    @Override
    public BaseResponse getSettlementFirstAuditChangeLogId(String id) {
        SettlementFirstAuditChangeLogModel settlementFirstAuditChangeLogModel
                = settlementFirstAuditChangeLogMapper.selectModelById(id);
        if(settlementFirstAuditChangeLogModel == null){
            return BaseResponse.fail("信息不存在！");
        }
        List<ConEcResponse> conEcResponseList = baseMapper.getContractInfo(settlementFirstAuditChangeLogModel.getContractId());
        if (CollectionUtil.isNotEmpty(conEcResponseList)){
            BigDecimal contractTotalMoneyChange = BigDecimal.ZERO;
            for (ConEcResponse conEcResponse : conEcResponseList){
                contractTotalMoneyChange = contractTotalMoneyChange.add(conEcResponse.getEcTotalMoneyC());
            }
            settlementFirstAuditChangeLogModel.setContractTotalMoneyChange(contractTotalMoneyChange);
        }
        return BaseResponse.ok(settlementFirstAuditChangeLogModel);
    }

    @Override
    public BaseResponse deleteSettlementFirstAudit(SettlementFirstAuditModel request) {
        SettlementFirstAudit settlementFirstAudit = baseMapper.selectById(request.getId());
        if (settlementFirstAudit == null){
            return BaseResponse.fail("信息不存在！");
        }
        settlementFirstAudit.setDelFlag(2);
        baseMapper.updateById(settlementFirstAudit);
        return BaseResponse.ok();
    }

    private List<ClassInfoAnnotation> getClassInfoAnnotations() {
        List<ClassInfoAnnotation> settlementFirstAuditClassInfoAnnotations = new ArrayList<>();
        try {
            settlementFirstAuditClassInfoAnnotations = ClassInfoUtils.getClassInfo(SettlementFirstAuditRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : settlementFirstAuditClassInfoAnnotations) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        if (classInfoAnnotation.getType().equals("company")){
                            List<FilterObject> dictionaryValues =baseMapper.getCompanyValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        }else if (classInfoAnnotation.getType().equals("person")){
                            List<FilterObject> dictionaryValues =baseMapper.getPersonValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        }else if (classInfoAnnotation.getType().equals("project")){
                            List<FilterObject> dictionaryValues =baseMapper.getProjectNameByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        }else if (classInfoAnnotation.getType().equals("contract")){
                            List<FilterObject> dictionaryValues =baseMapper.getContractNameByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryFilterValues(dictionaryValues);
                        }
                        else{
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }

                    }
                }
            }
            return settlementFirstAuditClassInfoAnnotations;
        } catch (Exception e) {
            log.error("getClassInfoAnnotations error:{}",e);
            return settlementFirstAuditClassInfoAnnotations;
        }
    }

    @Override
    public Map<String, Object> getConReviewAmount(String conId) {
        LambdaQueryWrapper<SettlementFirstAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettlementFirstAudit::getContractId,conId);
         List<SettlementFirstAudit> auditList = baseMapper.selectList(queryWrapper);
         Map<String,Object> map = new HashMap<>();
         if(!CollectionUtils.isEmpty(auditList)){
              BigDecimal reduce = auditList.stream().filter(e -> ValidateUtil.isNotNull(e.getSfaSettlementFirstPrice())).map(e -> e.getSfaSettlementFirstPrice())
                     .reduce(BigDecimal.ZERO, BigDecimal::add);
              map.put("sfasettlementfirstprice",reduce);
         }
        return map;
    }
}
