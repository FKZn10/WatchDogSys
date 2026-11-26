package com.jpmsoft.projectConsumer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "监理管理-监理规划及细则", value = "监理管理-监理规划及细则")
@RequestMapping("/v1/conSupPlan")
public class ConSupPlanController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改监理规划及细则")
    @PostMapping("/createOrUpdateConSupPlan")
    public BaseResponse createOrUpdateConSupPlan(@RequestBody ConSupPlanRequest request) {
        log.info("createOrUpdateConSupPlan");
        return baseFeign.createOrUpdateConSupPlan(request);
    }

    @ApiOperation(value = "分页查询监理规划及细则")
    @PostMapping("/getConSupPlanList")
    public BaseResponse getConSupPlanList(@RequestBody ConSupPlanRequest request, @RequestHeader String token) {
        log.info("getConSupPlanList");
        return baseFeign.getConSupPlanList(request, token);
    }

    @ApiOperation(value = "ID查询监理规划及细则")
    @GetMapping("/getConSupPlanById")
    public BaseResponse getConSupPlanById(@RequestParam String spId) {
        log.info("getConSupPlanById");
        return baseFeign.getConSupPlanById(spId);
    }
}
