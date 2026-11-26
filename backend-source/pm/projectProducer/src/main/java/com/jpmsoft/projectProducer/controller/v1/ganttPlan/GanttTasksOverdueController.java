package com.jpmsoft.projectProducer.controller.v1.ganttPlan;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksOverdueDTO;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttTasksOverdueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/ganttTasksOverdue")
public class GanttTasksOverdueController {
    @Autowired
    private GanttTasksOverdueService ganttTasksOverdueService;

    @PostMapping("/createOrUpdateGanttOverdue")
    public BaseResponse createOrUpdateGanttPlan(@RequestBody GanttTasksOverdueDTO ganttTasksOverdue) {
        return ganttTasksOverdueService.createOrUpdateGanttPlan(ganttTasksOverdue);
    }

    @GetMapping("/getGanttTasksOverdueByTaskId")
    public BaseResponse getGanttTasksOverdueByTaskId(@RequestParam String taskId) {
        return BaseResponse.ok(ganttTasksOverdueService.getGanttTasksOverdueByTaskId(taskId));
    }

    @GetMapping("/getGanttTasksOverdueByTaskIdByPage")
    public BaseResponse getGanttTasksOverdueByTaskIdByPage(@RequestParam int page, @RequestParam int size, @RequestParam String taskId) {
        return ganttTasksOverdueService.getGanttTasksOverdueByTaskIdByPage(page, size, taskId);
    }

    @GetMapping("/getGanttTasksOverdueById")
    public BaseResponse getGanttTaskOverdueById(@RequestParam String id) {
        return ganttTasksOverdueService.getGanttTaskOverdueById(id);
    }

    @GetMapping("/existInApprovalTaskOverdue")
    public BaseResponse existInApprovalTaskOverdue(@RequestParam String taskId) {
        return ganttTasksOverdueService.existInApprovalTaskOverdue(taskId);
    }

}
