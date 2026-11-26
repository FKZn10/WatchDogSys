package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Plan.GanttPlanTemplateModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "进度计划模板", value = "进度计划模板")
@RequestMapping("/plan/v1/ganttPlanTemplate")
public class GanttPlanTemplateController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改进度计划模板")
    @PostMapping("/createOrUpdateGanttPlanTemplate")
    public BaseResponse createOrUpdateGanttPlanTemplate(@RequestBody GanttPlanTemplateModel request) {
        log.info("createOrUpdateGanttPlanTemplate");
        return baseFeign.createOrUpdateGanttPlanTemplate(request);
    }

    @ApiOperation(value = "分页查询进度计划模板")
    @PostMapping("/getGanttPlanTemplateList")
    public BaseResponse getGanttPlanTemplateList(@RequestBody GanttPlanTemplateModel request) {
        log.info("getGanttPlanTemplateList");
        return baseFeign.getGanttPlanTemplateList(request);
    }

    @ApiOperation(value = "ID查询进度计划模板")
    @GetMapping("/getGanttPlanTemplateById")
    public BaseResponse getGanttPlanTemplateById(@RequestParam String id) {
        log.info("getGanttPlanTemplateById");
        return baseFeign.getGanttPlanTemplateById(id);
    }

    @ApiOperation(value = "查看当前模板已经关联的建设程序库ID集合")
    @GetMapping("/selectCurrentCplId")
    public BaseResponse selectCurrentCplId(@RequestParam String ptId) {
        log.info("selectCurrentCplId");
        return baseFeign.selectCurrentCplId(ptId);
    }

    @ApiOperation(value = "获取里程碑进度计划模板")
    @GetMapping("/getMilestoneGanttPlanTemp")
    public BaseResponse getMilestoneGanttPlanTemp() {
        log.info("getMilestoneGanttPlanTemp");
        return baseFeign.getMilestoneGanttPlanTemp();
    }

}
