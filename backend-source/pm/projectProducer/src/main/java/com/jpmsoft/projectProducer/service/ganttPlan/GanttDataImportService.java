package com.jpmsoft.projectProducer.service.ganttPlan;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;

public interface GanttDataImportService extends IService<GanttMilestoneStatistics> {
    BaseResponse dataImport();
}
