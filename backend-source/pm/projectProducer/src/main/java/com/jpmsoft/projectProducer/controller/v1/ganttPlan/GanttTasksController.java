package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTasksCjDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ganttTasks")
public class GanttTasksController {
    @Resource
    private IGanttTasksService ganttTasksService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 统计逾期情况
     *
     * @return
     */
    @PostMapping("/GanttPlanStatisticsByOverdue")
    public BaseResponse GanttPlanStatisticsByOverdue(@RequestBody(required = false) GanttTasksRequest request, @RequestHeader String token) {
        log.info("GanttPlanStatisticsByOverdue");
        return ganttTasksService.GanttPlanStatisticsByOverdue(request, redisUtils.getUid(token));
    }

    /**
     * 任务逾期情况对应列表查询
     *
     * @return
     */
    @PostMapping("/ganttPlanTaskStatisticsList")
    public BaseResponse ganttPlanTaskStatisticsList(@RequestHeader String token, @RequestBody GanttTasksRequest request) {
        log.info("ganttPlanTaskStatisticsList");
        return ganttTasksService.ganttPlanTaskStatisticsList(redisUtils.getUid(token), request);
    }

    @PostMapping("/getGanttTasksByOverdue")
    public BaseResponse getGanttTasksByOverdue(@RequestBody GanttTasksRequest request, @RequestHeader String token) {
        log.info("getGanttTasksByOverdue");
        return ganttTasksService.getGanttTasksByOverdue(request, redisUtils.getUid(token));
    }

    @PostMapping("/getGanttTasksByComplete")
    public BaseResponse getGanttTasksByComplete(@RequestBody GanttTasksRequest request, @RequestHeader String token) {
        log.info("getGanttTasksByComplete");
        return ganttTasksService.getGanttTasksByComplete(request, redisUtils.getUid(token));
    }

    @PostMapping("/getSecondaryNodesByTaskId")
    public BaseResponse getSecondaryNodesByTaskId(@RequestBody GanttTasksRequest request) {
        log.info("getSecondaryNodesByTaskId");
        return ganttTasksService.getSecondaryNodesByTaskId(request);
    }

    @PostMapping("/getGanttTasksExport")
    public BaseResponse getGanttTasksExport(@RequestBody GanttPlanExportRequest request) {
        log.info("getGanttTasksExport");
        return ganttTasksService.getGanttTasksExport(request);
    }

    @PostMapping("/updateBeginTime")
    public BaseResponse updateBeginTime(@RequestBody GanttTasksRequest request) {
        log.info("updateBeginTime");
        return ganttTasksService.updateBeginTime(request);
    }

    @GetMapping("/milestone2Complete")
    public BaseResponse milestone2Complete(@RequestParam String taskId, @RequestParam(required = false) String finishTime, @RequestHeader String token) {
        log.info("milestone2Complete");
        return ganttTasksService.milestone2Complete(taskId, finishTime, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/milestoneYjComplete")
    public BaseResponse milestoneYjComplete(@RequestBody MilestoneSubmitDTO request, @RequestHeader String token) {
        log.info("milestoneYjComplete");
        return ganttTasksService.milestone2Complete(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 根据taskID查询最新的任务流程详情表(第一/二条记录+State)
     */
    @GetMapping("/Cj/queryGanttTasksProcessByTaskID")
    public BaseResponse queryGanttTasksProcessByTaskID(@RequestParam String taskId) {
        return ganttTasksService.queryGanttTasksProcessByTaskID(taskId);
    }

    /**
     * 回写
     */
    @GetMapping("/Cj/rewriteGanttTasksByTaskID")
    public BaseResponse rewriteGanttTasksByTaskID(@RequestParam String taskId) {
        return ganttTasksService.rewriteGanttTasksByTaskID(taskId);
    }

    @PostMapping("/Cj/getGanttTasksByProject")
    public BaseResponse getGanttTasksByProject(@RequestBody GanttTasksCjDTO request) {
        return ganttTasksService.getGanttTasksByProject(request);
    }

    @PostMapping("/Cj/getGanttTasksByProjectClassInfo")
    public BaseResponse getGanttTasksByProjectClassInfo(@RequestBody GanttTasksCjDTO request) {
        return ganttTasksService.getGanttTasksByProjectClassInfo(request);
    }

    @PostMapping("/Cj/getCjMilestonesGanttTasks")
    public BaseResponse getCjMilestonesGanttTasks(@RequestBody GanttTasksRequest request) {
        return ganttTasksService.getCjMilestonesGanttTasks(request);
    }

    @GetMapping("/Cj/getCjGanttTasksById")
    public BaseResponse getCjGanttTasksById(@RequestParam String taskId) {
        return ganttTasksService.getCjGanttTasksById(taskId);
    }

    /**
     * 检查任务是否已完成
     *
     * @param pId
     * @param taskName
     * @param gsId
     * @return
     */
    @GetMapping("/Cj/checkGanttTaskCompleted")
    public BaseResponse checkGanttTaskCompleted(@RequestParam("pId") String pId, @RequestParam("taskName") String taskName, @RequestParam(value = "gsId", required = false) String gsId) {
        return ganttTasksService.checkGanttTaskCompleted(pId, taskName, gsId);
    }
}

