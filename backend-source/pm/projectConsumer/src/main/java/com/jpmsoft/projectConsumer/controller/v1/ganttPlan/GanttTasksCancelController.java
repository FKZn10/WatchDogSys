package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksCancelDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "进度计划-任务取消", value = "进度计划-任务取消")
@RequestMapping("/plan/v1/ganttTasksCancel")
public class GanttTasksCancelController {
    @Autowired
    private BaseFeign baseFeign;

    @PostMapping("/createOrUpdateGanttCannel")
    public BaseResponse createOrUpdateGanttCannel(@RequestBody GanttTasksCancelDTO ganttTasksCancelDTO) {
        return baseFeign.createOrUpdateGanttCannel(ganttTasksCancelDTO);
    }

    @GetMapping("/getGanttCannelByTaskId")
    public BaseResponse getGanttCannelByTaskId(@RequestParam String taskId) {
        return baseFeign.getGanttTasksCannelByTaskId(taskId);
    }

    @GetMapping("/getGanttTasksOverdueByTaskIdByPage")
    public BaseResponse getGanttCannelByTaskIdByPage(@RequestParam int page, @RequestParam int size, @RequestParam String taskId) {
        return baseFeign.getGanttTasksCannelByTaskIdByPage(page, size, taskId);
    }

    @GetMapping("/rewriteTask")
    public BaseResponse rewriteTask(@RequestParam String taskId) {
        return baseFeign.rewriteTask(taskId);
    }

    @GetMapping("/getGanttCannelById")
    public BaseResponse getGanttCannelById(@RequestParam String id) {
        return baseFeign.getGanttCannelById(id);
    }

    @ApiOperation(value = "判断任务下是否存在非作废的取消申请")
    @GetMapping("/existApproveGanttCancel")
    public BaseResponse existApproveGanttCancel(@RequestParam String id) {
        return baseFeign.existApproveGanttCancel(id);
    }
}
