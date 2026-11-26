package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanChangeDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 进度计划变更
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/23 11:24
 */
@Slf4j
@RestController
@Api(tags = "进度计划变更", value = "进度计划变更")
@RequestMapping("/plan/v1/ganttPlanChange")
public class GanttPlanChangeController {

    @Resource
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改进度计划变更")
    @PostMapping("/createOrUpdateGanttPlanChange")
    public BaseResponse createOrUpdateGanttPlanChange(@RequestBody GanttPlanChangeDTO request) {
        log.info("createOrUpdateGanttPlanChange");
        return baseFeign.createOrUpdateGanttPlanChange(request);
    }

    @ApiOperation(value = "分页查询进度计划变更")
    @PostMapping("/getGanttPlanChangeList")
    public BaseResponse getGanttPlanChangeList(@RequestBody GanttPlanChangeDTO request, @RequestHeader String token) {
        log.info("getGanttPlanChangeList");
        return baseFeign.getGanttPlanChangeList(request, token);
    }

    @ApiOperation(value = "ID查询进度计划变更")
    @GetMapping("/getGanttPlanChangeById")
    public BaseResponse getGanttPlanChangeById(@RequestParam String id) {
        log.info("getGanttPlanChangeById");
        return baseFeign.getGanttPlanChangeById(id);
    }

    @ApiOperation(value = "审批通过进度计划变更同步数据到进度计划")
    @GetMapping("/approved2SyncGanttPlan")
    public BaseResponse approved2SyncGanttPlan(@RequestParam String id) {
        log.info("approved2SyncGanttPlan");
        return baseFeign.approved2SyncGanttPlan(id);
    }


    @ApiOperation(value = "审批通过进度计划变更同步数据到进度计划")
    @GetMapping("/approved2SyncGanttPlanAndProjectCp")
    public BaseResponse approved2SyncGanttPlanAndProjectCp(@RequestParam String id) {
        log.info("approved2SyncGanttPlanAndProjectCp");
        return baseFeign.approved2SyncGanttPlanAndProjectCp(id);
    }

    @ApiOperation(value = "任务ID判断当前任务是否可以填报(未作废的变更记录中标记为删除则不能填报)")
    @GetMapping("/can2SubmitProcess")
    public BaseResponse can2SubmitProcess(@RequestParam String id) {
        log.info("can2SubmitProcess");
        return baseFeign.can2SubmitProcess(id);
    }
}
