package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.export.response.GanttTasksProcessModel;
import com.jpmsoft.common.model.request.Plan.GanttTasksProcessRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.Plan.GanttTasksProcessResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksProcessResponseCJ;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.model.response.Plan.TaskProcessRateDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksProcessMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksProcess;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksProcessService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class GanttTasksProcessImpl extends ServiceImpl<GanttTasksProcessMapper, GanttTasksProcess> implements IGanttTasksProcessService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private UserProducerFeign userProducerFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttTasksProcess(GanttTasksProcessRequest request) {
        if (request.getGtpRate() == null || request.getTaskId() == null)
            return BaseResponse.fail(ResMsg.notReqParam);
        if (request.getGtpRate() > 100F || request.getGtpRate() < 0F)
            return BaseResponse.fail(ResMsg.correctScale);
        QueryWrapper<GanttTasksProcess> wrapper = new QueryWrapper<>();
        wrapper.ne("Gtp_State", ApprovalStatusEnum.Invalid.getIndex());
        wrapper.eq("Task_Id", request.getTaskId());
        if (!ValidateUtil.isEmpty(request.getGtpId()))
            wrapper.ne("Gtp_Id", request.getGtpId());
        List<GanttTasksProcess> datas = baseMapper.selectList(wrapper);
        Float countRate = 0F;
        for (GanttTasksProcess data : datas) {
            countRate += data.getGtpRate();
        }
        if (countRate + request.getGtpRate() > 100F)
            return BaseResponse.fail(ResMsg.correctScale);
        GanttTasksProcess entity = new GanttTasksProcess();
        if (ValidateUtil.isEmpty(request.getGtpId())) {
            BeanUtils.copyProperties(request, entity);
            entity.setGtpId(GenerateUUID.getUUID());
            entity.setGtpCreateTime(new Date());
            entity.setGtpStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getGtpId(), request.gettId(), entity.getGtpState() + "", entity.getGtpCreateUid(), entity.getGtpCreateUname()
                    , entity.getGtpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getGtpScenePhotos(), request.getGtpLiveVideo(), request.getGtpSignatureConfirm()), entity.getGtpId());
        } else {
            entity = baseMapper.selectById(request.getGtpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setGtpRate(request.getGtpRate());
            entity.setGtpDate(request.getGtpDate());
            entity.setGtpRemark(request.getGtpRemark());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getGtpId(), request.gettId(), entity.getGtpState() + "", entity.getGtpCreateUid(), entity.getGtpCreateUname()
                    , entity.getGtpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getGtpScenePhotos(), request.getGtpLiveVideo(), request.getGtpSignatureConfirm()),
                    Arrays.asList(request.getFileIds(), request.getGtpScenePhotosIds(), request.getGtpLiveVideoIds(), request.getGtpSignatureConfirmIds()), entity.getGtpId());
        }
        return BaseResponse.ok(entity.getGtpId());
    }

    @Override
    public BaseResponse getGanttTasksProcessList(GanttTasksProcessRequest request) {
        QueryMap<GanttTasksProcessRequest> params = new QueryMap<>(request);
        params.put("taskId").put("conId").put("pId").put("planDId");
        IPage<GanttTasksProcessResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttTasksProcessResponse> datas = baseMapper.getGanttTasksProcessList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGanttTasksProcessById(String gtpId) {
        GanttTasksProcessResponse datas = baseMapper.getGanttTasksProcessById(gtpId);
        if (datas != null && datas.getTaskId() != null) {
            GanttTasksResponse tasks = ganttTasksService.getGanttTasksById(datas.getTaskId());
            datas.setGanttTasks(tasks);
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse approve2RefreshGanttTask(String gtpId) {
        GanttTasksProcess gtp = baseMapper.selectById(gtpId);
        TaskProcessRateDTO tpr = baseMapper.selectGtpRateSumByTask(gtp.getTaskId());
        if (tpr != null) {
            tpr.setTaskId(gtp.getTaskId());
            ganttTasksService.process2RefreshGanttTask(tpr);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<GanttTasksProcess> getGanttTasksProcessByPlanId(String planId) {
        return baseMapper.getGanttTasksProcessByPlanId(planId);
    }

    @Override
    public BaseResponse getGanttTasksProcessExport(GanttPlanExportRequest request) {
        if (request.getpIds() == null)
            return BaseResponse.fail(ResMsg.notReqParam);
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Gtp_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "ASC" : request.getSortType());
        LogicUtil.putParamToMap(params, request.getGtpDate(), "gtpDateStart", "gtpDateEnd");
        params.put("planName", ValidateUtil.isEmpty(request.getPlanName()) ? null : request.getPlanName());
        params.put("planType", ValidateUtil.isEmpty(request.getPlanType()) ? null : request.getPlanType());
        params.put("planIds", ValidateUtil.isEmpty(request.getPlanIds()) ? null : request.getPlanIds());
        List<GanttTasksProcessModel> datas = baseMapper.getGanttTasksProcessExport(params, request.getpIds());
        // Integer total = baseMapper.getGanttTasksProcessExportCount(params, request.getpIds());
        return BaseResponse.ok(datas, 0);
    }

    @Override
    public void milestone2Generate(MilestoneSubmitDTO data, Date time, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(data.getTCode())) data.setTCode("PlanFeedback");;
        FileTemplateTree tmp = userProducerFeign.getFileTemplateByCode(data.getTCode());
        if (tmp == null) throw new JPMException(String.format(ResMsg.notFoundFileTmp, data.getTCode()));
        GanttTasksProcessRequest request = new GanttTasksProcessRequest();
        request.setTaskId(data.getTaskId());
        request.setFileRelationId(GenerateUUID.getUUID());
        request.settId(tmp.gettId());
        request.settCode(tmp.gettCode());
        request.setFileTitle(tmp.gettName());
        request.setFileCode(tmp.gettCode());
        request.setGtpCreateTime(new Date());
        if (ValidateUtil.isNotNull(time))
            request.setGtpDate(time);
        else
            request.setGtpDate(new Date());
        request.setGtpRate(100F);
        request.setGtpCreateUid(user.getId());
        request.setGtpCreateUname(user.getName());
        request.setGtpState(ApprovalStatusEnum.Already_Over.getIndex());
        request.setGtpStateTime(request.getGtpCreateTime());
        //附件
        request.setGtpScenePhotos(data.getGtpScenePhotos());
        request.setGtpLiveVideo(data.getGtpScenePhotos());
        request.setGtpSignatureConfirm(data.getGtpScenePhotos());
        request.setFileRelationId(data.getGtpScenePhotos());
        createOrUpdateGanttTasksProcess(request);
    }


    @Override
    public BaseResponse createOrUpdateGanttTasksProcessCj(GanttTasksProcessRequest request) {
//        Long times = getTimes(request);
        int times=1;
        if(!ValidateUtil.isEmpty(request.getGtpEndDate())){
            times=2;
        }
        QueryWrapper<GanttTasksProcess> wrapper = new QueryWrapper<>();
        wrapper.ne("Gtp_State", ApprovalStatusEnum.Invalid.getIndex());
        wrapper.eq("Task_Id", request.getTaskId());
        if (!ValidateUtil.isEmpty(request.getGtpId()))
            wrapper.ne("Gtp_Id", request.getGtpId());

        GanttTasksProcess entity = new GanttTasksProcess();
        if (ValidateUtil.isEmpty(request.getGtpId())) {
            BeanUtils.copyProperties(request, entity);
            entity.setGtpId(GenerateUUID.getUUID());
            entity.setGtpCreateTime(new Date());
            entity.setGtpStateTime(null);
            entity.setGtpUploadTimes(times);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getGtpId(), request.gettId(), entity.getGtpState() + "", entity.getGtpCreateUid(), entity.getGtpCreateUname()
                    , entity.getGtpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getGtpScenePhotos(), request.getGtpLiveVideo(), request.getGtpSignatureConfirm()), entity.getGtpId());
        } else {
            entity = baseMapper.selectById(request.getGtpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setGtpDate(request.getGtpDate());
            entity.setGtpRemark(request.getGtpRemark());
            entity.setGtpUploadTimes(times);
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getGtpId(), request.gettId(), entity.getGtpState() + "", entity.getGtpCreateUid(), entity.getGtpCreateUname()
                    , entity.getGtpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getGtpScenePhotos(), request.getGtpLiveVideo(), request.getGtpSignatureConfirm()),
                    Arrays.asList(request.getFileIds(), request.getGtpScenePhotosIds(), request.getGtpLiveVideoIds(), request.getGtpSignatureConfirmIds()), entity.getGtpId());
        }
        return BaseResponse.ok(entity.getGtpId());
    }

    @Override
    public BaseResponse getGanttTasksProcessByIdCj(String gtpId) {
        GanttTasksProcessResponseCJ entity = baseMapper.getGanttTasksProcessByIdCj(gtpId);
        if(Objects.isNull(entity))
            return BaseResponse.fail("未查询到");
        return BaseResponse.ok(entity);

    }

    @Override
    public List<GanttTasksProcessResponse> getTasksProcessByTaskId(String taskId) {
        return baseMapper.getTasksProcessByTaskId(taskId) ;
    }

    private Long getTimes(GanttTasksProcessRequest request){
        String taskId = request.getTaskId();
        return lambdaQuery()
                .eq(GanttTasksProcess::getTaskId, taskId)
                .ne(GanttTasksProcess::getGtpState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(GanttTasksProcess::getGtpCreateTime)
                .count()+1;
    }

}
