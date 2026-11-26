package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Plan.GanttPlanTemplateModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanTemplateService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ganttPlanTemplate")
public class GanttPlanTemplateController {

    @Resource
    private IGanttPlanTemplateService ganttPlanTemplateService;
    @Resource
    private IGanttTasksTemplateService ganttTasksTemplateService;


    @TenantId
    @PostMapping("/createOrUpdateGanttPlanTemplate")
    public BaseResponse createOrUpdateGanttPlanTemplate(@RequestBody GanttPlanTemplateModel request) {
        log.info("createOrUpdateGanttPlanTemplate");
        return ganttPlanTemplateService.createOrUpdateGanttPlanTemplate(request);
    }

    @TenantId
    @PostMapping("/getGanttPlanTemplateList")
    public BaseResponse getGanttPlanTemplateList(@RequestBody GanttPlanTemplateModel request) {
        log.info("getGanttPlanTemplateList");
        return ganttPlanTemplateService.getGanttPlanTemplateList(request);
    }

    @GetMapping("/getGanttPlanTemplateById")
    public BaseResponse getGanttPlanTemplateById(@RequestParam String id) {
        log.info("getGanttPlanTemplateById");
        return ganttPlanTemplateService.getGanttPlanTemplateById(id);
    }

    @GetMapping("/selectCurrentCplId")
    public BaseResponse selectCurrentCplId(@RequestParam String ptId) {
        log.info("selectCurrentCplId");
        return BaseResponse.ok(ganttTasksTemplateService.selectCurrentCplId(ptId));
    }

    @GetMapping("/getMilestoneGanttPlanTemp")
    public BaseResponse getMilestoneGanttPlanTemp() {
        log.info("getMilestoneGanttPlanTemp");
        return ganttPlanTemplateService.getMilestoneGanttPlanTemp();
    }
}
