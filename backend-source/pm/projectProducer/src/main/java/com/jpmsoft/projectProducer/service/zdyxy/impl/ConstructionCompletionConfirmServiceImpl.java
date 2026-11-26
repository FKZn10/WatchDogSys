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
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionCompletionConfirmModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceRecordMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.ConstructionCompletionConfirmMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApplyDtl;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionCompletionConfirm;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.zdyxy.ConstructionCompletionConfirmService;
import com.jpmsoft.projectProducer.service.zdyxy.IInvSettlementApplyDtlService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConstructionCompletionConfirmServiceImpl
    extends ServiceImpl<ConstructionCompletionConfirmMapper, ConstructionCompletionConfirm>
        implements ConstructionCompletionConfirmService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private MaintenanceRecordMapper maintenanceRecordMapper;
    @Autowired
    private InvSettlementApplyMapper invSettlementApplyMapper;
    @Resource
    private IInvSettlementApplyDtlService invSettlementApplyDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse saveOrUpdateData(ConstructionCompletionConfirmModel model) {
        if (model == null){
            return BaseResponse.fail("缺少请求参数");
        }
        if (StrUtil.isEmpty(model.getMrId())){
            return BaseResponse.fail("缺少报修单ID");
        }
        if (StrUtil.isEmpty(model.getConstructionImage())){
            return BaseResponse.fail("缺少工程形象照片");
        }
        if (StrUtil.isEmpty(model.getConstructionDrawing())){
            return BaseResponse.fail("缺少竣工图纸");
        }

        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(model));
        ConstructionCompletionConfirm entity;
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto;
        if(StrUtil.isNotEmpty(model.getConstructionImage())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(model.getConstructionImage());
            supportDocumentDto.setSupportDocumentFileIDs(model.getConstructionImageIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(model.getConstructionDrawing())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(model.getConstructionDrawing());
            supportDocumentDto.setSupportDocumentFileIDs(model.getConstructionDrawingIds());
            documentDtos.add(supportDocumentDto);
        }
        String primaryId = model.getId();
        log.info("primaryId:{}", primaryId);
        if(StrUtil.isEmpty(primaryId)){
            List<ConstructionCompletionConfirm> entities = baseMapper.selectList(
                    new QueryWrapper<ConstructionCompletionConfirm>()
                            .eq("mr_id",model.getMrId()).ne("state",ApprovalStatusEnum.Invalid.getIndex()));
            if(CollectionUtil.isNotEmpty(entities)){
                return BaseResponse.fail("该报修单已存在施工完成确认");
            }
            primaryId = IdWorker.getGenerateID();
            entity = new ConstructionCompletionConfirm();
            BeanUtils.copyProperties(model,entity);
            String seqCode = "ConstructionCompletionConfirm";
            String code = generateCodeByTCode(seqCode,model);
            if (ValidateUtil.isEmpty(code))
                return new BaseResponse("编号生成失败");
            refershAutoNo(seqCode,model);
            entity.setConfirmSerialNumber(code);
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setStateTime(DateTime.now());
            entity.setState(flowStatus);
            saveFile(entity, documentDtos);
            //创建流程
            baseMapper.insert(entity);
            MaintenanceRecord mr = maintenanceRecordMapper.selectById(entity.getMrId());
            if(mr!=null){
                mr.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex());
                maintenanceRecordMapper.updateById(mr);
            }
            if(!sysFileService.FileHelper(entity,model,"create",model.getFileRelationId())){
                return BaseResponse.fail("新增失败");
            }
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(model,entity, "createUid", "createUname", "createTime");
            saveFile(entity, documentDtos);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(ConstructionCompletionConfirm entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(
                    Collections.singletonList(supportDocumentDto.getSupportDocument()),
                    Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }
    @Override
    public BaseResponse approvalData(ConstructionCompletionConfirmModel request) {
        String id = request.getId();
        ConstructionCompletionConfirm entity;
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
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
            MaintenanceRecord mr = maintenanceRecordMapper.selectById(entity.getMrId());
            if (mr != null){
                if (ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getState())){
                    mr.setMrRepairState(MaintenancelStatusEnum.Construction_Completion_Confirmation_Finish.getIndex());
                }else if (ApprovalStatusEnum.Invalid.getIndex().equals(entity.getState())){
                    mr.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex());
                }else {
                    mr.setMrRepairState(MaintenancelStatusEnum.Construction_Completion_Confirmation_Approval.getIndex());
                }
                maintenanceRecordMapper.updateById(mr);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(ConstructionCompletionConfirmModel request, String token) {
        String authSql = sysUserService.getAuthSql("construction_completion_confirm", redisUtils.getUid(token), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "state", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        log.info("查询列表getList1:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryWrapper<InvSettlementApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("sa_state", ApprovalStatusEnum.Invalid.getIndex());
        List<InvSettlementApply> saList = invSettlementApplyMapper.selectList(queryWrapper);
        boolean hasValidData = false;
        if (CollectionUtil.isNotEmpty(saList)) {
            List<String> saIds = saList.stream().map(InvSettlementApply::getSaId).collect(Collectors.toList());
            List<InvSettlementApplyDtl> applyDtlList = invSettlementApplyDtlService.list(Wrappers.<InvSettlementApplyDtl>lambdaQuery()
                    .in(InvSettlementApplyDtl::getIsaId, saIds)
                    .select(InvSettlementApplyDtl::getIsaId)
            );
            if (ValidateUtil.isNotEmpty(applyDtlList)) {
                hasValidData = true;
            }
        }
        if (!hasValidData) {
            request.setIsSelect(0);
        }
        QueryMap<ConstructionCompletionConfirmModel> queryMap = new QueryMap<>(request);
        queryMap.put("mrSerialNumber").put("constructionUnitName")
                .put("constructionUnit").put("constructionContractor")
                .put("constructionSite").put("auditUnitName")
                .put("constructionContractorName").put("confirmSerialNumber")
                .put("state").put("isSelect").put("createTimeRange").put("pName").put("authSql", authSql);
        log.info("查询列表getList2:{}", JSON.toJSONString(queryMap, JSONWriter.Feature.ReferenceDetection));
        IPage<ConstructionCompletionConfirmModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConstructionCompletionConfirmModel> list = baseMapper.getList(queryMap.getQuery(),page);
        log.info("查询列表getList3:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String id) {
        ConstructionCompletionConfirmModel model = baseMapper.getDetail(id);
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