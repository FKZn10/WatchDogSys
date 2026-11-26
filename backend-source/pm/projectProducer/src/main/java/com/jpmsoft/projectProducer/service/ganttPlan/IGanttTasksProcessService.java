package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksProcessRequest;
import com.jpmsoft.common.model.response.Plan.GanttTasksProcessResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksProcess;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.Date;
import java.util.List;


public interface IGanttTasksProcessService extends IService<GanttTasksProcess> {

    BaseResponse createOrUpdateGanttTasksProcess(GanttTasksProcessRequest request);

    BaseResponse getGanttTasksProcessList(GanttTasksProcessRequest request);

    BaseResponse getGanttTasksProcessById(String gtpId);

    BaseResponse approve2RefreshGanttTask(String gtpId);

    List<GanttTasksProcess> getGanttTasksProcessByPlanId(String planId);

    /**
     * 北京项目
     * 导出
     *
     * @param request
     * @return
     */
    BaseResponse getGanttTasksProcessExport(GanttPlanExportRequest request);

    /**
     * 里程碑进度计划自动生成
     *
     * @return
     */
    void milestone2Generate(MilestoneSubmitDTO request, Date time, LogUserInfoVo user);

    BaseResponse createOrUpdateGanttTasksProcessCj(GanttTasksProcessRequest request);

    BaseResponse getGanttTasksProcessByIdCj(String gtpId);

    List<GanttTasksProcessResponse> getTasksProcessByTaskId(String taskId);
}
