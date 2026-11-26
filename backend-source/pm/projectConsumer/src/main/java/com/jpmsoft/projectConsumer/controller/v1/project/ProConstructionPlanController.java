package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目建设计划", value = "项目建设计划")
@RequestMapping("/v1/proConstPlan")
public class ProConstructionPlanController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目建设计划")
    @PostMapping("/createOrUpdateProConstructionPlan")
    public BaseResponse createOrUpdateProConstructionPlan(@RequestBody ProConstructionPlanModel request) {
        log.info("createOrUpdateProConstructionPlan");
        return preFeign.createOrUpdateProConstructionPlan(request);
    }

    @ApiOperation(value = "分页查询项目建设计划")
    @PostMapping("/getProConstructionPlanList")
    public BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanModel request) {
        log.info("getProConstructionPlanList");
        return preFeign.getProConstructionPlanList(request);
    }

    @ApiOperation(value = "ID查询项目建设计划")
    @GetMapping("/getProConstructionPlanById")
    public BaseResponse getProConstructionPlanById(@RequestParam String pcpId) {
        log.info("getProConstructionPlanById");
        return preFeign.getProConstructionPlanById(pcpId);
    }
}
