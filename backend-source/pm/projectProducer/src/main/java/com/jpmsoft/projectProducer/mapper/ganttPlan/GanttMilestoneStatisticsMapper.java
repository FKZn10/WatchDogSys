package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.common.model.response.ganttPlan.GanttMilestoneStatisticsResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MonthlyReportDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.EndPlanAnnex;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.OutProjectVo;
import com.jpmsoft.projectProducer.pojo.vo.ganttPlan.MonthlyReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Mapper
public interface GanttMilestoneStatisticsMapper extends BaseMapper<GanttMilestoneStatistics> {
    /**
     * 获取计划管理列表
     *
     * @param page
     * @param request
     * @param authSql
     * @param lastDayOfMonth
     * @param isPlanMonth
     * @return
     */
    List<GanttMilestoneStatisticsResponse> getGanttMilestoneList(
            @Param("page") Page<GanttMilestoneStatisticsRequest> page,
            @Param("params") GanttMilestoneStatisticsRequest request,
            @Param("authSql") String authSql,
            @Param("lastDayOfMonth") String lastDayOfMonth,
            @Param("isPlanMonth") Boolean isPlanMonth,
            @Param("ids") List<String> ids
    );

    /**
     * 获取所有值
     *
     * @param selectClause
     * @param params
     * @param authSql
     * @param lastDayOfMonth
     * @param isPlanMonth
     * @return
     */
    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") GanttMilestoneStatisticsRequest params,
            @Param("authSql") String authSql,
            @Param("lastDayOfMonth") String lastDayOfMonth,
            @Param("isPlanMonth") Boolean isPlanMonth,
            @Param("ids") List<String> ids
    );

    MonthlyReportVO getMonthlyReport(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    MonthlyReportVO getYearPlan(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getCompleteOutTimeProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getRecordsOutTimeProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getRemoveOutTimeProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getAccountOutTimeProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getRecordsOutOfPlanProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getRemoveOutOfPlanProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getCompleteOutOfPlanProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<OutProjectVo> getAccountOutOfPlanProjects(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<EndPlanAnnex> getEndPlanInAnnex(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);

    List<EndPlanAnnex> getEndPlanOutAnnex(@Param("request") MonthlyReportDTO request, @Param("authSql") String authSql);
}
