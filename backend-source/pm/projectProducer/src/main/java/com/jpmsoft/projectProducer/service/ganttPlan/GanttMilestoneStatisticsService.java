package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MonthlyReportDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
public interface GanttMilestoneStatisticsService extends IService<GanttMilestoneStatistics> {
    /**
     * 获取计划管理列表
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getGanttMilestoneList(GanttMilestoneStatisticsRequest request, String uId);

    /**
     * 查询计划管理信息
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getGanttMilestoneClassInfo(GanttMilestoneStatisticsRequest request, String uId);

    /**
     * 导出年度收尾计划列表
     *
     * @param request
     * @param uid
     * @return
     */
    Workbook ganttMilestoneListExportExcel(GanttMilestoneStatisticsRequest request, String uid);

    /**
     * 获取计划管理月报
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getMonthlyReport(MonthlyReportDTO request, String uId);

//    BaseResponse getMonthlyReport(MonthlyReportDTO request);

    /**
     * 根据id查询计划管理信息
     *
     * @param request
     * @param uId
     * @param ids
     * @return
     */
    BaseResponse getGanttMilestoneListByIds(GanttMilestoneStatisticsRequest request, String uId, List<String> ids);

    /**
     * 根据id查询计划管理信息
     *
     * @param request
     * @param uId
     * @param ids
     * @return
     */
    BaseResponse getGanttMilestoneClassInfoByIds(GanttMilestoneStatisticsRequest request, String uId, List<String> ids);
}

