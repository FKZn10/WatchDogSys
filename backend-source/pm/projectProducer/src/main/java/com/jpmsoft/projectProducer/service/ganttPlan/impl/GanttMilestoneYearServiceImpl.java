package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.common.model.response.ganttPlan.GanttMilestoneStatisticsResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttMilestoneYearMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneYear;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttMilestoneStatisticsService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttMilestoneYearService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/5/29
 */
@Service
public class GanttMilestoneYearServiceImpl extends ServiceImpl<GanttMilestoneYearMapper, GanttMilestoneYear> implements IGanttMilestoneYearService {
    @Resource
    private GanttMilestoneStatisticsService ganttMilestoneStatisticsService;

    /**
     * 生成年度计划
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    @Transactional
    public BaseResponse generateGanttMilestoneYear(GanttMilestoneStatisticsRequest request, String uId) {
        if (ValidateUtil.isEmpty(request) || ValidateUtil.isEmpty(request.getIcmReportingYear())) {
            return BaseResponse.fail("参数错误");
        }

        // 年度计划生成的查询条件不能有其他筛选存在，重新创建筛选条件，防止前端传值错误
        GanttMilestoneStatisticsRequest query = new GanttMilestoneStatisticsRequest();
        query.setIsPlan("00");
        query.setPage(1);
        query.setSize(Integer.MAX_VALUE);
        query.setPCategory(Collections.singletonList("01"));
        query.setIcmReportingYear(request.getIcmReportingYear());
        query.setIcmReportingMonth("0");

        // 查询数据
        BaseResponse response = ganttMilestoneStatisticsService.getGanttMilestoneList(query, uId);
        if (!response.getSuccess() || ValidateUtil.isEmpty(response.getData())) {
            return BaseResponse.fail("当前年份没有计划项目");
        }

        @SuppressWarnings("unchecked")
        List<GanttMilestoneStatisticsResponse> dataList = (List<GanttMilestoneStatisticsResponse>) response.getData();

        List<GanttMilestoneYear> models = dataList.stream().map(data -> {
            GanttMilestoneYear model = new GanttMilestoneYear();
            model.setGmsId(data.getId());
            model.setYear(request.getIcmReportingYear());
            return model;
        }).collect(Collectors.toList());

        if (models.isEmpty()) {
            return BaseResponse.fail("当前年份没有计划项目");
        }

        return BaseResponse.ok(this.saveBatch(models));
    }

    /**
     * 判断年度计划是否存在
     *
     * @param year
     * @return
     */
    @Override
    public BaseResponse existsGanttMilestoneYear(String year) {
        if (ValidateUtil.isEmpty(year) || "undefined".equalsIgnoreCase(year)) {
            return BaseResponse.ok(false);
        }
        return BaseResponse.ok(lambdaQuery().eq(GanttMilestoneYear::getYear, year).count() > 0);
    }

    /**
     * 获取当前选择的年度计划列表
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneYearList(GanttMilestoneStatisticsRequest request, String uId) {
        if (ValidateUtil.isEmpty(request) || ValidateUtil.isEmpty(request.getIcmReportingYear()) || ValidateUtil.isEmpty(request.getIcmReportingMonth())) {
            return BaseResponse.fail("参数错误");
        }
        List<String> ids = getGanttMilestoneYearList(request.getIcmReportingYear());
        if (ids.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList());
        }
        request.setIcmReportingMonth("0");
        return ganttMilestoneStatisticsService.getGanttMilestoneListByIds(request, uId, ids);
    }

    @Override
    public List<String> getGanttMilestoneYearList(String year) {
        if (ValidateUtil.isEmpty(year)) {
            return Collections.emptyList();
        }
        List<GanttMilestoneYear> list = lambdaQuery().eq(GanttMilestoneYear::getYear, year).select(GanttMilestoneYear::getGmsId).list();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map(GanttMilestoneYear::getGmsId).collect(Collectors.toList());
    }

    /**
     * 年度计划条件信息查询
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getGanttMilestoneYearClassInfo(GanttMilestoneStatisticsRequest request, String uId) {
        if (ValidateUtil.isEmpty(request) || ValidateUtil.isEmpty(request.getIcmReportingYear()) || ValidateUtil.isEmpty(request.getIcmReportingMonth())) {
            return BaseResponse.fail("参数错误");
        }
        List<GanttMilestoneYear> list = lambdaQuery().eq(GanttMilestoneYear::getYear, request.getIcmReportingYear()).list();
        if (list.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<String> ids = list.stream().map(GanttMilestoneYear::getGmsId).collect(Collectors.toList());
        request.setIcmReportingMonth("0");
        return ganttMilestoneStatisticsService.getGanttMilestoneClassInfoByIds(request, uId, ids);
    }
}
