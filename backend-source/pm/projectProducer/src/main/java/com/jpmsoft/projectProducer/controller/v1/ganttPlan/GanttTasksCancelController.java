package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksCancelDTO;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttTasksCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/ganttTasksCancel")
public class GanttTasksCancelController {
    @Autowired
    private GanttTasksCancelService ganttTasksCancelService;

    @PostMapping("/createOrUpdateGanttCannel")
    public BaseResponse createOrUpdateGanttCannel(@RequestBody GanttTasksCancelDTO ganttTasksCancelDTO) {
        return ganttTasksCancelService.createOrUpdateGanttCannel(ganttTasksCancelDTO);
    }

    @GetMapping("/getGanttCannelByTaskId")
    public BaseResponse getGanttCannelByTaskId(@RequestParam String taskId) {
        return BaseResponse.ok(ganttTasksCancelService.getGanttTasksCannelByTaskId(taskId));
    }

    @GetMapping("/getGanttTasksOverdueByTaskIdByPage")
    public BaseResponse getGanttCannelByTaskIdByPage(@RequestParam int page, @RequestParam int size, @RequestParam String taskId) {
        return ganttTasksCancelService.getGanttTasksCannelByTaskIdByPage(page, size, taskId);
    }

    @GetMapping("/rewriteTask")
    public BaseResponse rewriteTask(@RequestParam String taskId) {
        return ganttTasksCancelService.rewriteTask(taskId);
    }

    @GetMapping("/getGanttCannelById")
    public BaseResponse getGanttCannelById(@RequestParam String id) {
        return ganttTasksCancelService.getGanttCannelById(id);
    }

    @GetMapping("/existApproveGanttCancel")
    public BaseResponse existApproveGanttCancel(@RequestParam String taskId) {
        return ganttTasksCancelService.existApproveGanttCancel(taskId);
    }
}
