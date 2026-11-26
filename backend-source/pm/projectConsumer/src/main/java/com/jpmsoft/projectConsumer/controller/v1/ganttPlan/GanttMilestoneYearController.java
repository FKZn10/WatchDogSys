package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/29
 */
@Slf4j
@RestController
@Api(tags = "里程碑计划汇总", value = "里程碑计划汇总")
@RequestMapping("/v1/ganttMilestoneYear")
public class GanttMilestoneYearController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 生成年度计划
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "年度计划生成")
    @PostMapping("/generateGanttMilestoneYear")
    public BaseResponse generateGanttMilestoneYear(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("generateGanttMilestoneYear");
        return baseFeign.generateGanttMilestoneYear(request, token);
    }

    /**
     * 判断年度计划是否存在
     *
     * @param year
     * @return
     */
    @ApiOperation(value = "判断年度计划是否存在")
    @GetMapping("/existsGanttMilestoneYear")
    public BaseResponse existsGanttMilestoneYear(@RequestParam("year") String year) {
        log.info("existsGanttMilestoneYear");
        return baseFeign.existsGanttMilestoneYear(year);
    }

    /**
     * 获取当前选择的年度计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取年度计划列表")
    @PostMapping("/getGanttMilestoneYearList")
    public BaseResponse getGanttMilestoneYearList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneYearList");
        return baseFeign.getGanttMilestoneYearList(request, token);
    }

    /**
     * 年度计划条件信息查询
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "年度计划条件信息查询")
    @PostMapping("/getGanttMilestoneYearClassInfo")
    public BaseResponse getGanttMilestoneYearClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneYearClassInfo");
        return baseFeign.getGanttMilestoneYearClassInfo(request, token);
    }
}
