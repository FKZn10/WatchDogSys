package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.KeyValueDTO;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTaskCjStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.ScheduleExpirationTimeDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectProducer.pojo.response.PreprojectInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface GanttPlanMapper extends BaseMapper<GanttPlan> {

    List<Object> getValueByCode(@Param("value") String dbValue, @Param("params") Map<String, Object> query);


    List<GanttPlanResponse> getGanttPlanList(@Param("page") IPage<GanttPlanResponse> page, @Param("params") Map<String, Object> params);

    GanttPlanResponse getGanttPlanByPlanId(@Param("planId") String planId);

    List<GanttPlanResponse> statisticsProJectPlanRanking(@Param("params") Map<String, Object> params);

    Integer statisticsProJectPlanRankingCount(@Param("params") Map<String, Object> params);

    List<GanttPlanResponse> getGanttPlanAndTaskBypId(@Param("pId") String pId, @Param("types") List<String> types);

    @Select("select mn.* from  Gantt_Plan mn ${authSql} and Plan_State!=3")
    List<GanttPlan> statisticsProjectPlan(@Param("authSql") String authSql);

    List<GanttPlanResponse> ganttPlanStatisticsList(@Param("page") IPage<GanttPlanResponse> page, @Param("params") Map<String, Object> params);

    /**
     * 查询审批通过的项目总数
     *
     * @return
     */
    Integer getProjectCount(@Param("teId") String teId);


    List<KeyValueDTO> ganttPlanStatisticsProjectStage(@Param("pIds") List<String> pIds);

    List<PreprojectInfoResponse> getPreprojectListInfo(@Param("page") Page<PreprojectInfoResponse> page, @Param("params") Map<String, Object> query, @Param("conf") ScheduleExpirationTimeDTO conf);

    @Deprecated
    Integer getPreprojectListInfoCount(@Param("authSql") String authSql, @Param("uId") String uId, @Param("pName") String pName, @Param("codes") List<String> codes, @Param("conf") ScheduleExpirationTimeDTO conf);

    List<Map<String, Object>> getPreprojectListInfoValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query, @Param("conf") ScheduleExpirationTimeDTO conf);

    GanttTaskCjStatisticsDTO getTaskImportanceInfo(@Param("authSql") String authSql, @Param("uId") String uId, @Param("params") PreProjectPage params, @Param("codes") List<String> codes, @Param("conf") ScheduleExpirationTimeDTO conf);

    List<GanttTasksResponse> getTaskPIdInfo(@Param("page") IPage<PreprojectInfoResponse> page, @Param("params") Map<String, Object> query, @Param("conf") ScheduleExpirationTimeDTO conf);

    List<Map<String, Object>> getTasksClassInfoValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query, @Param("conf") ScheduleExpirationTimeDTO scheduleExpirationTime);

    /**
     * 根据用户id获取到用户参与审核、沟通、抄送的项目进度id集合
     *
     * @param uId
     * @return
     */
    Set<String> getPlanidsByUid(@Param("uId") String uId);

    List<GanttPlanResponse> getGanttPlanLedgerNJCJ(@Param("page") IPage<GanttPlanResponse> page, @Param("params") Map<String, Object> query);

    Map<String, Object> getGanttPlanLedgerNJCJStats(@Param("params") Map<String, Object> query);

    List<GanttPlanResponse> getPreprojectInfoLedgerNJCJ(@Param("page") IPage<GanttPlanResponse> page, @Param("params") Map<String, Object> query);

    Map<String, Object> getPreprojectInfoLedgerNJCJStats(@Param("params") Map<String, Object> query);
}
