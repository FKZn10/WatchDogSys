package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksProcessRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "进度计划-任务完成情况", value = "进度计划-任务完成情况")
@RequestMapping("/plan/v1/ganttTasksProcess")
public class GanttTasksProcessController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改任务完成情况")
    @PostMapping("/createOrUpdateGanttTasksProcess")
    public BaseResponse createOrUpdateGanttTasksProcess(@RequestBody GanttTasksProcessRequest request) {
        log.info("createOrUpdateGanttTasksProcess");
        return baseFeign.createOrUpdateGanttTasksProcess(request);
    }

    @ApiOperation(value = "分页查询任务完成情况")
    @PostMapping("/getGanttTasksProcessList")
    public BaseResponse getGanttTasksProcessList(@RequestBody GanttTasksProcessRequest request) {
        log.info("getGanttTasksProcessList");
        return baseFeign.getGanttTasksProcessList(request);
    }

    @ApiOperation(value = "ID查询任务完成情况")
    @GetMapping("/getGanttTasksProcessById")
    public BaseResponse getGanttTasksProcessById(@RequestParam String gtpId) {
        log.info("getGanttTasksProcessById");
        return baseFeign.getGanttTasksProcessById(gtpId);
    }

    @ApiOperation(value = "任务完成情况填报审批通过刷新任务完成度")
    @GetMapping("/approve2RefreshGanttTask")
    public BaseResponse approve2RefreshGanttTask(@RequestParam String gtpId) {
        log.info("approve2RefreshGanttTask");
        return baseFeign.approve2RefreshGanttTask(gtpId);
    }


    @ApiOperation(value = "进度计划办理信息(导出)")
    @PostMapping("/getGanttTasksProcessExport")
    public BaseResponse getGanttTasksProcessExport(@RequestBody GanttPlanExportRequest request) {
        log.info("getGanttTasksProcessExport");
        return baseFeign.getGanttTasksProcessExport(request);
    }


    @ApiOperation("创建任务流程详情(一个任务最多对应两个任务流程详情)")
    @PostMapping("/Cj/createOrUpdateGanttTasksProcess")
    public BaseResponse createOrUpdateGanttTasksProcessCj(@RequestBody GanttTasksProcessRequest request){
        return baseFeign.createOrUpdateGanttTasksProcessCj(request);
    }

    @ApiOperation("根据详情表的主键查询相关信息")
    @PostMapping("/Cj/getGanttTasksProcessById")
    public BaseResponse getGanttTasksProcessByIdCj(@RequestParam  String gtpId){
        return baseFeign.getGanttTasksProcessByIdCj(gtpId);
    }

}
