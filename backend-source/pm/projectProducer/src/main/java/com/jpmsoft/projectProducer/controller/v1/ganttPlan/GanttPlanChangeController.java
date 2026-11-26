package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanChangeDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanChangeService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
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
@RequestMapping("/v1/ganttPlanChange")
public class GanttPlanChangeController {

    @Resource
    private IGanttPlanChangeService ganttPlanChangeService;

    @Resource
    private IGanttTasksChangeService ganttTasksChangeService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateGanttPlanChange")
    public BaseResponse createOrUpdateGanttPlanChange(@RequestBody GanttPlanChangeDTO request) {
        log.info("createOrUpdateGanttPlanChange");
        return ganttPlanChangeService.createOrUpdateGanttPlanChange(request);
    }


    @TenantId
    @PostMapping("/getGanttPlanChangeList")
    public BaseResponse getGanttPlanChangeList(@RequestBody GanttPlanChangeDTO request, @RequestHeader String token) {
        log.info("getGanttPlanChangeList");
        return ganttPlanChangeService.getGanttPlanChangeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getGanttPlanChangeById")
    public BaseResponse getGanttPlanChangeById(@RequestParam String id) {
        log.info("getGanttPlanChangeById");
        return ganttPlanChangeService.getGanttPlanChangeById(id);
    }

    @GetMapping("/approved2SyncGanttPlan")
    public BaseResponse approved2SyncGanttPlan(@RequestParam String id) {
        log.info("approved2SyncGanttPlan");
        return ganttPlanChangeService.approved2SyncGanttPlan(id);
    }


    @GetMapping("/approved2SyncGanttPlanAndProjectCp")
    public BaseResponse approved2SyncGanttPlanAndProjectCp(@RequestParam String id) {
        log.info("approved2SyncGanttPlanAndProjectCp");
        return ganttPlanChangeService.approved2SyncGanttPlanAndProjectCp(id);
    }


    @GetMapping("/can2SubmitProcess")
    public BaseResponse can2SubmitProcess(@RequestParam String id) {
        log.info("can2SubmitProcess");
        return BaseResponse.ok(ganttTasksChangeService.can2SubmitProcess(id));
    }
}
