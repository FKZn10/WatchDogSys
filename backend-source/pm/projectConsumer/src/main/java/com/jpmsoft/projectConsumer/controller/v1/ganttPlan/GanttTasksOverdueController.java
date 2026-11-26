package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttTasksOverdueDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "进度计划-任务逾期填报", value = "进度计划-任务逾期填报")
@RequestMapping("/plan/v1/ganttTasksOverdue")
public class GanttTasksOverdueController {
    @Resource
    private BaseFeign baseFeign;


    @ApiOperation(value = "创建或更新逾期")
    @PostMapping("/createOrUpdateGanttOverdue")
    public BaseResponse createOrUpdateGanttPlanCj(@RequestBody GanttTasksOverdueDTO ganttTasksOverdueDTO){
        return baseFeign.createOrUpdateGanttPlanCj(ganttTasksOverdueDTO);
    }

    @ApiOperation(value = "获取任务的所有逾期情况")
    @GetMapping("/getGanttTasksOverdueByTaskId")
    public BaseResponse getGanttTasksOverdueByTaskId(@RequestParam String taskId){
        return baseFeign.getGanttTasksOverdueByTaskIdCj(taskId);
    }

    @ApiOperation(value = "分页获取任务的所有逾期情况")
    @GetMapping("/getGanttTasksOverdueByTaskIdByPage")
    public BaseResponse getGanttTasksOverdueByTaskIdByPage(@RequestParam int page,@RequestParam int size,@RequestParam String  taskId){
        return baseFeign.getGanttTasksOverdueByTaskIdByPageCj(page,size,taskId);
    }

    @ApiOperation(value = "通过主键获取逾期情况")
    @GetMapping("/getGanttTasksOverdueById")
    public BaseResponse getGanttTaskOverdueById(@RequestParam String id){
        return baseFeign.getGanttTaskOverdueById(id);
    }
    @ApiOperation(value = "判断任务下是否存在审批中的逾期填报")
    @GetMapping("/existInApprovalTaskOverdue")
    public BaseResponse existInApprovalTaskOverdue(@RequestParam String taskId){
        return baseFeign.existInApprovalTaskOverdue(taskId);
    }

}
