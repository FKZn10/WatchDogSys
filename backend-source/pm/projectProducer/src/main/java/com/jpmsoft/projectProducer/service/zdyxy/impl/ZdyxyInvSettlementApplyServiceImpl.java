package com.jpmsoft.projectProducer.service.zdyxy.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.MaintenancelStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.ConstructionCompletionConfirmMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApplyDtl;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionCompletionConfirm;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.zdyxy.IInvSettlementApplyDtlService;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyInvSettlementApplyService;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyMaintenanceRecordService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ZdyxyInvSettlementApplyServiceImpl
    extends ServiceImpl<InvSettlementApplyMapper, InvSettlementApply>
        implements ZdyxyInvSettlementApplyService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private ConstructionCompletionConfirmMapper constructionCompletionConfirmMapper;
    @Autowired
    private ZdyxyMaintenanceRecordService maintenanceRecordService;
    @Resource
    private IInvSettlementApplyDtlService invSettlementApplyDtlService;

    @Override
    public BaseResponse saveOrUpdateData(InvSettlementApplyModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto;
        if(StrUtil.isNotEmpty(request.getSaReportFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(request.getSaReportFile());
            supportDocumentDto.setSupportDocumentFileIDs(request.getSaReportFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(request.getSaAuditReport())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(request.getSaAuditReport());
            supportDocumentDto.setSupportDocumentFileIDs(request.getSaAuditReportIds());
            documentDtos.add(supportDocumentDto);
        }
        InvSettlementApply entity;
        String primaryId = request.getSaId();
        log.info("primaryId:{}", primaryId);
        if(StrUtil.isEmpty(primaryId)){
            List<InvSettlementApply> entities = baseMapper.selectList(
                    new QueryWrapper<InvSettlementApply>()
                            .eq("completion_confirm_id",request.getCompletionConfirmId())
                            .ne("sa_state",ApprovalStatusEnum.Invalid.getIndex()));
            if(CollectionUtil.isNotEmpty(entities)){
                return BaseResponse.fail("该报修单的施工完成确认已存在结算送审");
            }
            String seqCode = "InvSettlementApply";
            String code = generateCodeByTCode(seqCode,request);
            if (ValidateUtil.isEmpty(code))
                return new BaseResponse("编号生成失败");
            refershAutoNo(seqCode,request);
            request.setSaSerialNumber(code);
            primaryId = IdWorker.getGenerateID();
            entity = new InvSettlementApply();
            BeanUtils.copyProperties(request,entity);;
            entity.setSaId(primaryId);
            entity.setSaCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setSaStateTime(DateTime.now());
            entity.setSaState(flowStatus);
            ConstructionCompletionConfirm constructionCompletionConfirm = constructionCompletionConfirmMapper.selectById(request.getCompletionConfirmId());
            if (constructionCompletionConfirm != null){
                entity.setMrId(constructionCompletionConfirm.getMrId());
            }
            if (StrUtil.isNotEmpty(entity.getMrId())){
                MaintenanceRecord mr = maintenanceRecordService.getById(entity.getMrId());
                if (mr != null){
                    mr.setMrRepairState(MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex());
                    maintenanceRecordService.updateById(mr);
                }
            }
            saveFile(entity, documentDtos);
            //创建流程
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSaId(), request.getTId(), entity.getSaState() + "",
                    entity.getSaCreateUid(), entity.getSaCreateUname()
                    , entity.getSaCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.getTCode(), entity.getConId());
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(request,entity, "saCreateUid", "saCreateUname", "saCreateTime");
            ConstructionCompletionConfirm constructionCompletionConfirm = constructionCompletionConfirmMapper.selectById(request.getCompletionConfirmId());
            if (constructionCompletionConfirm != null){
                entity.setMrId(constructionCompletionConfirm.getMrId());
            }
            saveFile(entity, documentDtos);
            if (StrUtil.isNotEmpty(entity.getMrId())){
                MaintenanceRecord mr = maintenanceRecordService.getById(entity.getMrId());
                if (mr != null){
                    mr.setMrRepairState(MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex());
                    maintenanceRecordService.updateById(mr);
                }
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(InvSettlementApply entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(
                    Collections.singletonList(supportDocumentDto.getSupportDocument()),
                    Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getSaId());
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvalData(InvSettlementApplyModel request) {
        String id = request.getId();
        InvSettlementApply entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null) {
                return new BaseResponse("信息不存在，请刷新重试！");
            }

            fileEntity = sysFileService.getById(id);
            /*
              流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
              5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setSaState(Integer.valueOf(fileFlowStatus));
            entity.setSaStateTime(new Date());
            List<InvSettlementApplyDtl> applyDtlList = invSettlementApplyDtlService.list(Wrappers.<InvSettlementApplyDtl>lambdaQuery()
                    .eq(InvSettlementApplyDtl::getIsaId, entity.getSaId())
            );
            if (ValidateUtil.isNotEmpty(applyDtlList)) {
                Set<String> mrIds = applyDtlList.stream()
                        .map(InvSettlementApplyDtl::getMrId)
                        .filter(ValidateUtil::isNotEmpty)
                        .collect(Collectors.toSet());
                if (ValidateUtil.isNotEmpty(mrIds)) {
                    List<MaintenanceRecord> maintenanceRecords = maintenanceRecordService.list(Wrappers.<MaintenanceRecord>lambdaQuery()
                            .in(MaintenanceRecord::getMrId, mrIds)
                    );
                    Map<String, MaintenanceRecord> mrMap = maintenanceRecords.stream()
                            .collect(Collectors.toMap(MaintenanceRecord::getMrId, mr -> mr, (mr1, mr2) -> mr1));
                    List<MaintenanceRecord> updateMrList = new ArrayList<>();
                    for (InvSettlementApplyDtl invSettlementApplyDtl : applyDtlList) {
                        if (ValidateUtil.isNotEmpty(invSettlementApplyDtl.getMrId())) {
                            MaintenanceRecord mr = mrMap.get(invSettlementApplyDtl.getMrId());
                            if (mr != null) {
                                if (ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getSaState())) {
                                    mr.setMrRepairState(MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex());
                                } else if (ApprovalStatusEnum.Invalid.getIndex().equals(entity.getSaState())) {
                                    mr.setMrRepairState(MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex());
                                } else {
                                    mr.setMrRepairState(MaintenancelStatusEnum.Settlement_Submission_Approval.getIndex());
                                }
                                updateMrList.add(mr);
                            }
                        }
                    }
                    if (ValidateUtil.isNotEmpty(updateMrList)) {
                        maintenanceRecordService.updateBatchById(updateMrList);
                    }
                }
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(InvSettlementApplyModel request, String token) {
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<InvSettlementApplyModel> queryMap = new QueryMap<>(request);
        queryMap.put("confirmSerialNumber").put("constructionContractorName").put("saTitle")
                .put("saSerialNumber").put("saState").put("isSelect").put("createTimeRange");
        IPage<InvSettlementApplyModel> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementApplyModel> list = baseMapper.getList(queryMap.getQuery(),page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String saId) {
        InvSettlementApplyModel model = baseMapper.getDetail(saId);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }

    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    public String refershAutoNo(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/refershAutoNo?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:{}",JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}