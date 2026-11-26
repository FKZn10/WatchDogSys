package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "进度计划", value = "进度计划")
@RequestMapping("/plan/v1/ganttPlan")
public class GanttPlanController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改进度计划")
    @PostMapping("/createOrUpdateGanttPlan")
    public BaseResponse createOrUpdateGanttPlan(@RequestBody GanttPlanRequest request) {
        log.info("createOrUpdateGanttPlan");
        return baseFeign.createOrUpdateGanttPlan(request);
    }

    @ApiOperation(value = "新增或修改进度计划")
    @PostMapping("/createOrUpdateGanttPlanProjectOnly")
    public BaseResponse createOrUpdateGanttPlanProjectOnly(@RequestBody GanttPlanRequest request) {
        log.info("createOrUpdateGanttPlanProjectOnly");
        return baseFeign.createOrUpdateGanttPlanProjectOnly(request);
    }

    @ApiOperation(value = " 分页查询进度计划")
    @PostMapping("/getGanttPlanList")
    public BaseResponse getGanttPlanList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanList");
        return baseFeign.getGanttPlanList(request, token);
    }

    @ApiOperation(value = "分页查询进度计划（城建）")
    @PostMapping("/getGanttPlanListNJCJ")
    public BaseResponse getGanttPlanListNJCJ(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanListNJCJ");
        return baseFeign.getGanttPlanListNJCJ(request, token);
    }

    @ApiOperation(value = " 城建筛选条件")
    @PostMapping("/getGanttPlanClassInfo")
    public BaseResponse getGanttPlanClassInfo(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("getGanttPlanClassInfo");
        return baseFeign.getGanttPlanClassInfo(request, token);
    }

    @ApiOperation(value = "里程碑进度计划汇总分页查询")
    @PostMapping("/ganttPlanMilestoneList")
    public BaseResponse ganttPlanMilestoneList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("ganttPlanMilestoneList");
        return baseFeign.ganttPlanMilestoneList(request, token);
    }

    @ApiOperation(value = " ID查询进度计划")
    @GetMapping("/getGanttPlanByPlanId")
    public BaseResponse getGanttPlanByPlanId(@RequestParam String planId) {
        log.info("getGanttPlanByPlanId");
        return baseFeign.getGanttPlanByPlanId(planId);
    }

    @ApiOperation(value = " ID删除状态为未发起的进度计划")
    @GetMapping("/deleteUnStartGanttPlan")
    public BaseResponse deleteUnStartGanttPlan(@RequestParam String planId) {
        log.info("deleteUnStartGanttPlan");
        return baseFeign.deleteUnStartGanttPlan(planId);
    }

    @ApiOperation(value = "审批通过根据进度计划生成建设程序")
    @GetMapping("/approved2GenerateProjectCplTemp")
    public BaseResponse approved2GenerateProjectCplTemp(@RequestParam String planId) {
        log.info("approved2GenerateProjectCplTemp");
        return baseFeign.approved2GenerateProjectCplTemp(planId);
    }


    @ApiOperation(value = "项目计划阶段统计")
    @GetMapping("/statisticsProjectPlan")
    public BaseResponse statisticsProjectPlan(@RequestHeader String token) {
        log.info("statisticsProjectPlan");
        return baseFeign.statisticsProjectPlan(token);
    }

    @ApiOperation(value = "计划任务逾期排行")
    @PostMapping("/statisticsProJectPlanRanking")
    public BaseResponse statisticsProJectPlanRanking(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("statisticsProJectPlanRanking");
        return baseFeign.statisticsProJectPlanRanking(request, token);
    }


    @ApiOperation(value = "根据项目ID统计其计划数量")
    @GetMapping("/statisticsPlanTotalBypId")
    public BaseResponse statisticsPlanTotalBypId(@RequestParam String pId) {
        log.info("statisticsPlanTotalBypId");
        return baseFeign.statisticsPlanTotalBypId(pId);
    }

    @ApiOperation(value = "统计计划数量")
    @GetMapping("/statisticsPlanTotal")
    public BaseResponse statisticsPlanTotal() {
        log.info("statisticsPlanTotal");
        return baseFeign.statisticsPlanTotal();
    }

    @ApiOperation(value = "根据项目ID获取任务根节点")
    @GetMapping("/getGanttPlanAndTaskBypId")
    public BaseResponse getGanttPlanAndTaskBypId(@RequestParam String pId, @RequestParam(required = false) String type) {
        return baseFeign.getGanttPlanAndTaskBypId(pId, type);
    }

    @ApiOperation(value = "项目是否存在未作废得进度计划")
    @GetMapping("/projectExistGanttPlan")
    public BaseResponse projectExistGanttPlan(@RequestParam String pId, @RequestParam(required = false) String planId) {
        return baseFeign.projectExistGanttPlan(pId, planId);
    }

    /**
     * 统计逾期情况详情
     *
     * @return
     */
    @ApiOperation(value = "根据是否逾期统计任务完成情况列表")
    @PostMapping("/GanttPlanStatisticsList")
    public BaseResponse GanttPlanStatisticsList(@RequestBody GanttPlanRequest request, @RequestHeader String token) {
        log.info("GanttPlanStatisticsList");
        return baseFeign.GanttPlanStatisticsList(request, token);
    }

    @ApiOperation(value = "项目总进度计划编报台账")
    @PostMapping("/getGanttPlanLedgerNJCJ")
    public BaseResponse getGanttPlanLedgerNJCJ(@RequestBody GanttPlanRequest request) {
        log.info("getGanttPlanLedgerNJCJ");
        return baseFeign.getGanttPlanLedgerNJCJ(request);
    }

    @ApiOperation(value = "总进度计划管理台账")
    @PostMapping("/getPreprojectInfoLedgerNJCJ")
    public BaseResponse getPreprojectInfoLedgerNJCJ(@RequestBody GanttPlanRequest request) {
        log.info("getPreprojectInfoLedgerNJCJ");
        return baseFeign.getPreprojectInfoLedgerNJCJ(request);
    }
}
