package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Plan.GanttPlanTemplateModel;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanTemplate;


public interface IGanttPlanTemplateService extends IService<GanttPlanTemplate> {

    BaseResponse createOrUpdateGanttPlanTemplate(GanttPlanTemplateModel request);

    BaseResponse getGanttPlanTemplateList(GanttPlanTemplateModel request);

    BaseResponse getGanttPlanTemplateById(String id);

    /**
     * 查询里程碑进度计划模板
     * 模板任务返回最子集
     * @return
     */
    BaseResponse getMilestoneGanttPlanTemp();
}
