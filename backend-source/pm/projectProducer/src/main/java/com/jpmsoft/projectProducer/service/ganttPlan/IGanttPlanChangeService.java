package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanChange;


public interface IGanttPlanChangeService extends IService<GanttPlanChange> {

    BaseResponse createOrUpdateGanttPlanChange(GanttPlanChangeDTO request);


    BaseResponse getGanttPlanChangeList(GanttPlanChangeDTO request, String uid);

    BaseResponse getGanttPlanChangeById(String id);

    /**
     * 审批通过同步数据到源进度计划
     *
     * @param id
     * @return
     */
    BaseResponse approved2SyncGanttPlan(String id);

    /**
     * 城建
     * 审批通过同步数据到进度计划
     * 更新建设程序
     *
     * @param id
     * @return
     */
    BaseResponse approved2SyncGanttPlanAndProjectCp(String id);
}
