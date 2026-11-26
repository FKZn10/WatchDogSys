package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneYear;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/29
 */
public interface IGanttMilestoneYearService extends IService<GanttMilestoneYear> {
    /**
     * 生成年度计划
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse generateGanttMilestoneYear(GanttMilestoneStatisticsRequest request, String uId);

    /**
     * 判断年度计划是否存在
     *
     * @param year
     * @return
     */
    BaseResponse existsGanttMilestoneYear(String year);

    /**
     * 获取当前选择的年度计划列表
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getGanttMilestoneYearList(GanttMilestoneStatisticsRequest request, String uId);

    List<String> getGanttMilestoneYearList(String year);

    /**
     * 年度计划条件信息查询
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getGanttMilestoneYearClassInfo(GanttMilestoneStatisticsRequest request, String uId);
}
