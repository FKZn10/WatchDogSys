package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ganttPlan")
public class GanttPlanController {

    @Resource
    private IGanttPlanService ganttPlanService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateGanttPlan")
    public BaseResponse createOrUpdateGanttPlan(@RequestBody GanttPlanRequest request) {
        log.info("createOrUpdateGanttPlan");
        return ganttPlanService.createOrUpdateGanttPlan(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateGanttPlanProjectOnly")
    public BaseResponse createOrUpdateGanttPlanProjectOnly(@RequestBody GanttPlanRequest request) {
        log.info("createOrUpdateGanttPlanProjectOnly");
        return ganttPlanService.createOrUpdateGanttPlanProjectOnly(request);
    }

    @TenantId
    @PostMapping("/getGanttPlanList")
    public BaseResponse getGanttPlanList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanList");
        return ganttPlanService.getGanttPlanList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getGanttPlanListNJCJ")
    public BaseResponse getGanttPlanListNJCJ(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanListNJCJ");
        return ganttPlanService.getGanttPlanListNJCJ(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getGanttPlanClassInfo")
    public BaseResponse getGanttPlanClassInfo(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanClassInfo");
        return ganttPlanService.getGanttPlanClassInfo(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/ganttPlanMilestoneList")
    public BaseResponse ganttPlanMilestoneList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("ganttPlanMilestoneList");
        return ganttPlanService.ganttPlanMilestoneList(request, redisUtils.getUid(token));
    }

    /**
     * 统计逾期情况详情
     *
     * @return
     */
    @TenantId
    @PostMapping("/GanttPlanStatisticsList")
    public BaseResponse GanttPlanStatisticsList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("GanttPlanStatisticsList");
        return ganttPlanService.GanttPlanStatisticsList(redisUtils.getUid(token), request);
    }

    @GetMapping("/getGanttPlanByPlanId")
    public BaseResponse getGanttPlanByPlanId(@RequestParam String planId) {
        log.info("getGanttPlanByPlanId");
        return ganttPlanService.getGanttPlanByPlanId(planId);
    }

    @GetMapping("/deleteUnStartGanttPlan")
    public BaseResponse deleteUnStartGanttPlan(@RequestParam String planId) {
        log.info("deleteUnStartGanttPlan");
        return ganttPlanService.deleteUnStartGanttPlan(planId);
    }


    @GetMapping("/approved2GenerateProjectCplTemp")
    public BaseResponse approved2GenerateProjectCplTemp(@RequestParam String planId) {
        log.info("approved2GenerateProjectCplTemp");
        return ganttPlanService.approved2GenerateProjectCplTemp(planId);
    }

    @GetMapping("/statisticsProjectPlan")
    public BaseResponse statisticsProjectPlan(@RequestHeader String token) {
        log.info("statisticsProjectPlan");
        return ganttPlanService.statisticsProjectPlan(redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/statisticsProJectPlanRanking")
    public BaseResponse statisticsProJectPlanRanking(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("statisticsProJectPlanRanking");
        return ganttPlanService.statisticsProJectPlanRanking(request, redisUtils.getUid(token));
    }

    @GetMapping("/statisticsPlanTotalBypId")
    public BaseResponse statisticsPlanTotalBypId(@RequestParam String pId) {
        log.info("statisticsPlanTotalBypId");
        return ganttPlanService.statisticsPlanTotalBypId(pId);
    }

    @GetMapping("/statisticsPlanTotal")
    public BaseResponse statisticsPlanTotal(@RequestHeader String token) {
        log.info("statisticsPlanTotal");
        return ganttPlanService.statisticsPlanTotal(redisUtils.getTeId(token));
    }

    @GetMapping("/getGanttPlanAndTaskBypId")
    public BaseResponse getGanttPlanAndTaskBypId(@RequestParam String pId, @RequestParam(required = false) String type) {
        log.info("getGanttPlanAndTaskBypId");
        return ganttPlanService.getGanttPlanAndTaskBypId(pId, type);
    }

    @GetMapping("/projectExistGanttPlan")
    public BaseResponse projectExistGanttPlan(@RequestParam String pId, @RequestParam(required = false) String planId) {
        log.info("projectExistGanttPlan");
        return ganttPlanService.projectExistGanttPlan(pId, planId);
    }

    @PostMapping("/getGanttPlanLedgerNJCJ")
    public BaseResponse getGanttPlanLedgerNJCJ(@RequestBody GanttPlanRequest request) {
        log.info("getGanttPlanLedgerNJCJ");
        return ganttPlanService.getGanttPlanLedgerNJCJ(request);
    }

    @PostMapping("/getPreprojectInfoLedgerNJCJ")
    public BaseResponse getPreprojectInfoLedgerNJCJ(@RequestBody GanttPlanRequest request) {
        log.info("getPreprojectInfoLedgerNJCJ");
        return ganttPlanService.getPreprojectInfoLedgerNJCJ(request);
    }
}
