package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.GanttTasksCjDTO;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "进度计划-任务", value = "进度计划-任务")
@RequestMapping("/plan/v1/ganttTasks")
public class GanttTasksController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 统计逾期情况
     *
     * @return
     */
    @ApiOperation(value = "根据是否逾期统计任务完成情况")
    @PostMapping("/GanttPlanStatisticsByOverdue")
    public BaseResponse GanttPlanStatisticsByOverdue(@RequestBody(required = false) GanttTasksRequest request, @RequestHeader String token) {
        log.info("GanttPlanStatisticsByOverdue");
        return baseFeign.GanttPlanStatisticsByOverdue(request, token);
    }


    @ApiOperation(value = "分页查询任务逾期情况对应列表查询")
    @PostMapping("/ganttPlanTaskStatisticsList")
    public BaseResponse ganttPlanTaskStatisticsList(@RequestHeader String token, @RequestBody GanttTasksRequest request) {
        log.info("ganttPlanTaskStatisticsList");
        return baseFeign.ganttPlanTaskStatisticsList(token, request);
    }

    @ApiOperation(value = "分页查询逾期或者临期的任务")
    @PostMapping("/getGanttTasksByOverdue")
    public BaseResponse getGanttTasksByOverdue(@RequestBody GanttTasksRequest request, @RequestHeader String token) {
        log.info("getGanttTasksByOverdue");
        return baseFeign.getGanttTasksByOverdue(request, token);
    }

    @ApiOperation(value = "分页查询按期完成的任务")
    @PostMapping("/getGanttTasksByComplete")
    public BaseResponse getGanttTasksByComplete(@RequestBody GanttTasksRequest request, @RequestHeader String token) {
        log.info("getGanttTasksByComplete");
        return baseFeign.getGanttTasksByComplete(request, token);
    }

    @ApiOperation(value = "根据任务ID获取其下一级的任务列表")
    @PostMapping("/getSecondaryNodesByTaskId")
    public BaseResponse getSecondaryNodesByTaskId(@RequestBody GanttTasksRequest request) {
        log.info("getSecondaryNodesByTaskId");
        return baseFeign.getSecondaryNodesByTaskId(request);
    }

    @ApiOperation(value = "进度计划任务信息（导出）")
    @PostMapping("/getGanttTasksExport")
    public BaseResponse getGanttTasksExport(@RequestBody GanttPlanExportRequest request) {
        log.info("getGanttTasksExport");
        return baseFeign.getGanttTasksExport(request);
    }

    @ApiOperation(value = "进度计划任务修改实际开始时间")
    @PostMapping("/updateBeginTime")
    public BaseResponse updateBeginTime(@RequestBody GanttTasksRequest request) {
        log.info("updateBeginTime");
        return baseFeign.updateBeginTime(request);
    }

    @ApiOperation(value = "里程碑进度任务一键完成(使用文档模板PlanFeedback)")
    @GetMapping("/milestone2Complete")
    public BaseResponse milestone2Complete(@RequestParam String taskId, @RequestParam(required = false) String finishTime, @RequestHeader String token) {
        log.info("milestone2Complete");
        return baseFeign.milestone2Complete(taskId, finishTime, token);
    }

    @ApiOperation(value = "里程碑进度任务一键完成(使用文档模板MilestoneFeedback)")
    @PostMapping("/milestoneYjComplete")
    public BaseResponse milestoneYjComplete(@RequestBody MilestoneSubmitDTO request, @RequestHeader String token) {
        log.info("milestoneYjComplete");
        return baseFeign.milestoneYjComplete(request, token);
    }

    @ApiOperation(value = "根据taskID查询最新的任务状态")
    @GetMapping("/Cj/queryGanttTasksProcessByTaskID")
    public BaseResponse queryGanttTasksProcessByTaskID(@RequestParam String taskId) {
        return baseFeign.queryGanttTasksProcessByTaskID(taskId);
    }

    @ApiOperation("回写")
    @GetMapping("/Cj/rewriteGanttTasksByTaskID")
    public BaseResponse rewriteGanttTasksByTaskID(@RequestParam String taskId) {
        return baseFeign.rewriteGanttTasksByTaskID(taskId);
    }

    @ApiOperation("单项目计划看板列表查询")
    @PostMapping("/Cj/getGanttTasksByProject")
    public BaseResponse getGanttTasksByProject(@RequestBody GanttTasksCjDTO request) {
        return baseFeign.getGanttTasksByProject(request);
    }

    @ApiOperation("单项目计划看板查询条件")
    @PostMapping("/Cj/getGanttTasksByProjectClassInfo")
    public BaseResponse getGanttTasksByProjectClassInfo(@RequestBody GanttTasksCjDTO request) {
        return baseFeign.getGanttTasksByProjectClassInfo(request);
    }

    @ApiOperation("单项目计划看板里程碑节点查询")
    @PostMapping("/Cj/getCjMilestonesGanttTasks")
    public BaseResponse getCjMilestonesGanttTasks(@RequestBody GanttTasksRequest request) {
        return baseFeign.getCjMilestonesGanttTasks(request);
    }

    @ApiOperation("查询任务详情（逾期填报列表、取消填报列表）")
    @GetMapping("/Cj/getCjGanttTasksById")
    public BaseResponse getCjGanttTasksById(@RequestParam String taskId) {
        return baseFeign.getCjGanttTasksById(taskId);
    }

    /**
     * 检查任务是否已完成
     *
     * @param pId
     * @param taskName
     * @param gsId
     * @return
     */
    @ApiOperation("根据pid和任务名称查询其前置任务是否完成")
    @GetMapping("/Cj/checkGanttTaskCompleted")
    public BaseResponse checkGanttTaskCompleted(@RequestParam("pId") String pId, @RequestParam("taskName") String taskName, @RequestParam(value = "gsId", required = false) String gsId) {
        return baseFeign.checkGanttTaskCompleted(pId, taskName, gsId);
    }
}
