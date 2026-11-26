package com.jpmsoft.projectProducer.service.zdyxy.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.MaintenancelStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyMaintenanceRecordService;
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

@Slf4j
@Service
public class ZdyxyMaintenanceRecordServiceImpl
    extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord>
        implements ZdyxyMaintenanceRecordService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse saveOrUpdateData(MaintenanceRecordModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto;
        if(StrUtil.isNotEmpty(request.getMrReportFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(request.getMrReportFile());
            supportDocumentDto.setSupportDocumentFileIDs(request.getMrReportFileIds());
            documentDtos.add(supportDocumentDto);
        }
        MaintenanceRecord entity;
        String primaryId = request.getMrId();
        log.info("primaryId:{}", primaryId);
        if(StrUtil.isEmpty(primaryId)){
            String seqCode = "MaintenanceRecord";
            String code = generateCodeByTCode(seqCode,request);
            if (ValidateUtil.isEmpty(code))
                return new BaseResponse("编号生成失败");
            refershAutoNo(seqCode,request);
            request.setMrSerialNumber(code);
            primaryId = IdWorker.getGenerateID();
            entity = new MaintenanceRecord();
            BeanUtils.copyProperties(request,entity);;
            entity.setMrId(primaryId);
            entity.setMrCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setMrStateTime(DateTime.now());
            entity.setMrState(flowStatus);
            entity.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval.getIndex());
            saveFile(entity, documentDtos);
            //创建流程
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getMrId());
            sysFileService.saveFile(entity.getMrId(), request.getTId(), entity.getMrState() + "",
                    entity.getMrCreateUid(), entity.getMrCreateUname()
                    , entity.getMrCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.getTCode(), entity.getConId());
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(request,entity, "mrCreateUid", "mrCreateUname", "mrCreateTime");
            saveFile(entity, documentDtos);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getMrId());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(MaintenanceRecord entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(
                    Collections.singletonList(supportDocumentDto.getSupportDocument()),
                    Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getMrId());
        }
    }
    @Override
    public BaseResponse approvalData(MaintenanceRecordModel request) {
        String id = request.getId();
        MaintenanceRecord entity;
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
            entity.setMrState(Integer.valueOf(fileFlowStatus));
            entity.setMrStateTime(new Date());
            if (ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getMrState())){
                entity.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex());
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(MaintenanceRecordModel request, String token) {
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<MaintenanceRecordModel> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("mrSerialNumber").put("mrReportDepartmentName").put("mrState").put("isSelect").put("createTimeRange")
                .put("authSql", " where 1=1 ")
                .put("excludeSelectedRenovatioChanges",request.getExcludeSelectedRenovatioChanges());
        queryMap.put("excludeSelectedRenovatioVisa",request.getExcludeSelectedRenovatioVisa());
        IPage<MaintenanceRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordModel> list = baseMapper.getList(queryMap.getQuery(),page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getListAuth(MaintenanceRecordModel request, String token) {
        String uId = redisUtils.getUid(token);
        String authSql = sysUserService.getAuthSql("Maintenance_Record", uId, "Mr_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "Mr_State", "Mr_Id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<MaintenanceRecordModel> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("mrSerialNumber").put("mrReportDepartmentName").put("mrState").put("isSelect").put("createTimeRange")
                .put("authSql", authSql)
                .put("excludeSelectedRenovatioChanges",request.getExcludeSelectedRenovatioChanges());
        queryMap.put("excludeSelectedRenovatioVisa",request.getExcludeSelectedRenovatioVisa());
        IPage<MaintenanceRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordModel> list = baseMapper.getList(queryMap.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String mrId) {
        MaintenanceRecordModel model = baseMapper.getDetail(mrId);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse setMrRepairState(MaintenanceRecordModel request) {
        setRepairState(request);
        return BaseResponse.ok(request);
    }
    @Override
    public void setRepairState(MaintenanceRecordModel request) {
        MaintenanceRecord entity = baseMapper.selectById(request.getMrId());
        entity.setMrRepairState(request.getMrRepairState());
        baseMapper.updateById(entity);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncApplicationWarrantyStatus(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<MaintenanceRecordModel> list = baseMapper.getMaintenanceRecordListByAwId(id);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok();
        }
        for (MaintenanceRecordModel model : list) {
            if (ApprovalStatusEnum.Not_Started.getIndex().equals(model.getAwState())) {
                break;
            } else if (ApprovalStatusEnum.Already_Over.getIndex().equals(model.getAwState())) {
                model.setMrRepairState("10");
            } else {
                model.setMrRepairState("09");
            }
        }
        List<MaintenanceRecord> records = BeanUtils.copyList(list, MaintenanceRecord.class);
        this.updateBatchById(records);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateRepairRecordStatus() {
        // 一次性完成所有逻辑回退
        int updatedRows = baseMapper.updateRepairRecordStatusAllInOne();
        log.info("自动回退流程状态完成，共处理 {} 条记录", updatedRows);
        return BaseResponse.ok();
    }
}
