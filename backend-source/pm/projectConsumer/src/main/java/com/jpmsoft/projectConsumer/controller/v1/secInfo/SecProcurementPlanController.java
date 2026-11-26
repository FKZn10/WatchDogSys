package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecProcurementPlanDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/v1/procurementPlan")
public class SecProcurementPlanController {

    @Autowired
    private SecFeign secFeign;



    @ApiOperation(value = "创建或修改招采计划")
    @PostMapping("/createOrUpdateSecProcurementPlan")
    public BaseResponse createOrUpdateSecProcurementPlan(@RequestBody SecProcurementPlanDto request) {
        log.info("createOrUpdateSecProcurementPlan");
        return secFeign.createOrUpdateSecProcurementPlan(request);
    }

    @ApiOperation(value = "获取项目id获取项目概算树")
    @GetMapping("/getSecProcurementPlanAmountTree")
    public BaseResponse getSecProcurementPlanAmountTree(@RequestParam String pId) {
        return secFeign.getSecProcurementPlanAmountTree(pId);
    }

    @ApiOperation(value = "获取招采计划列表")
    @PostMapping("/getSecProcurementPlanList")
    public BaseResponse getSecProcurementPlanList(@RequestBody SecProcurementPlanDto request, @RequestHeader String token) {
        log.info("getSecProcurementPlanList");
        return secFeign.getSecProcurementPlanList(request, token);
    }

    @ApiOperation(value = "id获取招采计划")
    @GetMapping("/getSecProcurementPlanById")
    public BaseResponse getSecProcurementPlanById(@RequestParam String ppId) {
        log.info("getSecProcurementPlanById");
        return secFeign.getSecProcurementPlanById(ppId);
    }

    @ApiOperation(value = "项目获取招采计划列表")
    @GetMapping("/getSecProcurementPlanByPid")
    public BaseResponse getSecProcurementPlanByPid(@RequestParam String pId) {
        log.info("getSecProcurementPlanByPid");
        return secFeign.getSecProcurementPlanByPid(pId);
    }


}
