package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanTimes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GanttDataImportMapper extends BaseMapper<GanttMilestoneStatistics> {

    List<GanttMilestoneStatistics> dataImport();


    List<GanttMilestoneStatistics> List();

    void batchInsert(List<GanttMilestoneStatistics> list);


    List<GanttMilestoneStatistics> getProjectList();


    List<GanttPlanTimes> getProjectTimes();

}

