package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.export.response.GanttTasksExportModel;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTasksCjDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface GanttTasksMapper extends BaseMapper<GanttTasks> {

    Integer updateGanttTaskState(@Param("planId") String planId, @Param("cplId") String cplId, @Param("stateTime") Date stateTime);

    List<GanttTasksResponse> getGanttTasksByOverdue(@Param("params") Map<String, Object> params);

    Integer getGanttTasksByOverdueCount(@Param("params") Map<String, Object> params);


    List<GanttTasksResponse> getGanttTasksByComplete(@Param("page") IPage<GanttTasksResponse> page, @Param("params") Map<String, Object> params);


    List<GanttTasks> getAllGanttTaskByPlanState(@Param("authSql") String authSql, @Param("params") Map<String, Object> params);


    /**
     * 根据项目获取其所有计划任务根节点
     *
     * @param pId
     * @return
     */
    List<GanttTasksResponse> getRootGanttTasksBypId(@Param("pId") String pId, @Param("types") List<String> types);

    /**
     * 根据父节点获取下级节点
     *
     * @param params
     * @return
     */
    List<GanttTasksResponse> getSecondaryNodesByTaskId(@Param("params") Map<String, Object> params);

    Integer getSecondaryNodesByTaskIdCount(@Param("params") Map<String, Object> params);


    List<GanttTasksExportModel> getGanttTasksExport(@Param("params") Map<String, Object> params, @Param("pIds") List<String> pIds);


    Integer getGanttTasksCount(@Param("teId") String teId);

    Integer getGanttTasksCompCount(@Param("progress") Float progress, @Param("teId") String teId);


    List<GanttTasksResponse> getAllTaskByPlanId(@Param("planId") String planId);

    List<GanttTasksResponse> getAllTaskByPlanIds(@Param("planIds") List<String> planIds);

    List<GanttTasksResponse> ganttPlanTaskStatisticsList(@Param("page") IPage<GanttTasksResponse> page, @Param("params") Map<String, Object> params);

    @Select("select * from Gantt_Tasks where Plan_Id=(select Plan_Id from Gantt_Tasks where Task_Id=#{taskId})")
    List<GanttTasks> getAllTaskByTaskId(@Param("taskId") String taskId);

    List<Object> getValueByCode(@Param("value") String dbValue, @Param("params") GanttTasksCjDTO request);

    List<GanttTasksResponse> getGanttTasksByProject(@Param("params") GanttTasksCjDTO request);

    List<GanttTasksResponse> getCjMilestonesGanttTasks(@Param("params") GanttTasksRequest request);

    GanttTasksResponse getCjGanttTasksById(@Param("taskId") String taskId);

}
