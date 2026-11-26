package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "项目建设计划清单", value = "项目建设计划清单")
@RequestMapping("/v1/proConstPlanList")
public class ProConstructionPlanListController {
    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改建设计划清单流程表单")
    @PostMapping("/createOrUpdateProConstructionPlanList")
    public BaseResponse createOrUpdateProConstructionPlanList(@RequestBody ProConstructionPlanListModel request) {
        log.info("createOrUpdateProConstructionPlanList");
        return preFeign.createOrUpdateProConstructionPlanList(request);
    }

    @ApiOperation(value = "分页项目建设计划清单")
    @PostMapping("/getProConstructionPlanList")
    public BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanListModel request) {
        log.info("getProConstructionPlanList");
        return preFeign.getProConstructionPlanList(request);
    }

    @ApiOperation(value = "ID查询项目建设计划清单")
    @GetMapping("/getProConstructionPlanListById")
    public BaseResponse getProConstructionPlanListById(@RequestParam String cplId) {
        log.info("getProConstructionPlanListById");
        return preFeign.getProConstructionPlanListById(cplId);
    }

    @ApiOperation(value = "分页查询项目建设计划（带子表信息）")
    @PostMapping("/getProConstructionPlanSelectList")
    public BaseResponse getProConstructionPlanSelectList(@RequestBody ProConstructionPlanListModel request) {
        log.info("getProConstructionPlanSelectList");
        return preFeign.getProConstructionPlanSelectList(request);
    }


    @ApiOperation(value = "项目建设计划Id查询项目建设计划清单")
    @GetMapping("/getProConstructionPlanListByPcpId")
    public BaseResponse getProConstructionPlanListByPcpId(@RequestParam String pcpId) {
        log.info("getProConstructionPlanListByPcpId");
        return preFeign.getProConstructionPlanListByPcpId(pcpId);
    }
}
