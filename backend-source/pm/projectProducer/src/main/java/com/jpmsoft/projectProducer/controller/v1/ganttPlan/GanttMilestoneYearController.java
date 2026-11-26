package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttMilestoneYearService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/29
 */
@Slf4j
@RestController
@RequestMapping("/v1/ganttMilestoneYear")
public class GanttMilestoneYearController {
    @Resource
    private IGanttMilestoneYearService ganttMilestoneYearService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 生成年度计划
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/generateGanttMilestoneYear")
    public BaseResponse generateGanttMilestoneYear(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("generateGanttMilestoneYear");
        return ganttMilestoneYearService.generateGanttMilestoneYear(request, redisUtils.getUid(token));
    }

    /**
     * 判断年度计划是否存在
     *
     * @param year
     * @return
     */
    @GetMapping("/existsGanttMilestoneYear")
    public BaseResponse existsGanttMilestoneYear(@RequestParam("year") String year) {
        log.info("existsGanttMilestoneYear");
        return ganttMilestoneYearService.existsGanttMilestoneYear(year);
    }

    /**
     * 获取当前选择的年度计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getGanttMilestoneYearList")
    public BaseResponse getGanttMilestoneYearList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneYearList");
        return ganttMilestoneYearService.getGanttMilestoneYearList(request, redisUtils.getUid(token));
    }

    /**
     * 年度计划条件信息查询
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getGanttMilestoneYearClassInfo")
    public BaseResponse getGanttMilestoneYearClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneYearClassInfo");
        return ganttMilestoneYearService.getGanttMilestoneYearClassInfo(request, redisUtils.getUid(token));
    }
}
