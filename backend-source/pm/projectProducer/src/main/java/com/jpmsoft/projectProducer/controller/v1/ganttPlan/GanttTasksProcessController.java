package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksProcessRequest;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ganttTasksProcess")
public class GanttTasksProcessController {
    @Resource
    private IGanttTasksProcessService ganttTasksProcessService;

    @PostMapping("/createOrUpdateGanttTasksProcess")
    public BaseResponse createOrUpdateGanttTasksProcess(@RequestBody GanttTasksProcessRequest request) {
        log.info("createOrUpdateGanttTasksProcess");
        return ganttTasksProcessService.createOrUpdateGanttTasksProcess(request);
    }

    @PostMapping("/getGanttTasksProcessList")
    public BaseResponse getGanttTasksProcessList(@RequestBody GanttTasksProcessRequest request) {
        log.info("getGanttTasksProcessList");
        return ganttTasksProcessService.getGanttTasksProcessList(request);
    }

    @GetMapping("/getGanttTasksProcessById")
    public BaseResponse getGanttTasksProcessById(@RequestParam String gtpId) {
        log.info("getGanttTasksProcessById");
        return ganttTasksProcessService.getGanttTasksProcessById(gtpId);
    }

    @GetMapping("/approve2RefreshGanttTask")
    public BaseResponse approve2RefreshGanttTask(@RequestParam String gtpId) {
        log.info("approve2RefreshGanttTask");
        return ganttTasksProcessService.approve2RefreshGanttTask(gtpId);
    }

    @PostMapping("/getGanttTasksProcessExport")
    public BaseResponse getGanttTasksProcessExport(@RequestBody GanttPlanExportRequest request) {
        log.info("getGanttTasksProcessExport");
        return ganttTasksProcessService.getGanttTasksProcessExport(request);
    }
    /**
     * 城建
     * 创建任务流程详情(一个任务最多对应两个任务流程详情)
     * 2025年6.20新增Gtp_End_Date字段，方便第一次填报即可添加开始和结束时间
     *
     */
    @PostMapping("/Cj/createOrUpdateGanttTasksProcess")
    public BaseResponse createOrUpdateGanttTasksProcessCj(@RequestBody GanttTasksProcessRequest request){
        return ganttTasksProcessService.createOrUpdateGanttTasksProcessCj(request);
    }
    @PostMapping("/Cj/getGanttTasksProcessById")
    public BaseResponse getGanttTasksProcessByIdCj(@RequestParam  String gtpId){
        return ganttTasksProcessService.getGanttTasksProcessByIdCj(gtpId);
    }
}
