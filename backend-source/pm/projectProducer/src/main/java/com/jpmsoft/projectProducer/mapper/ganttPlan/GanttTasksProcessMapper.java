package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.export.response.GanttTasksProcessModel;
import com.jpmsoft.common.model.response.Plan.GanttTasksProcessResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksProcessResponseCJ;
import com.jpmsoft.common.model.response.Plan.TaskProcessRateDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface GanttTasksProcessMapper extends BaseMapper<GanttTasksProcess> {

    List<GanttTasksProcessResponse> getGanttTasksProcessList(@Param("page") IPage<GanttTasksProcessResponse> page, @Param("params") Map<String, Object> params);

    GanttTasksProcessResponse getGanttTasksProcessById(@Param("gtpId") String gtpId);

    List<GanttTasksProcess> getGanttTasksProcessByPlanId(@Param("planId") String planId);


    List<GanttTasksProcessModel> getGanttTasksProcessExport(@Param("params") Map<String, Object> params, @Param("pIds") List<String> pIds);

    Integer getGanttTasksProcessExportCount(@Param("params") Map<String, Object> params, @Param("pIds") List<String> pIds);

    /**
     * 查询任务完成度与最后的完成时间（填报可能多条）
     *
     * @param taskId
     * @return
     */
    @Select("select sum(Gtp_Rate) as rate,max(Gtp_Date) as \"completeDate\"  from Gantt_Tasks_Process where Task_Id=#{taskId} and Gtp_State=7")
    TaskProcessRateDTO selectGtpRateSumByTask(String taskId);

    GanttTasksProcessResponseCJ getGanttTasksProcessByIdCj(String gtpId);

    List<GanttTasksProcessResponse> getTasksProcessByTaskId(String taskId);
}
