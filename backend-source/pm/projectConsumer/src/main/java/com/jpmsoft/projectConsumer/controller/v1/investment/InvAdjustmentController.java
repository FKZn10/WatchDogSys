package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "调整事项模板", value = "调整事项")
@RequestMapping("/inv/v1/invAdjustment")
public class InvAdjustmentController {
    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改调整事项")
    @PostMapping("/createOrUpdateInvAdjustment")
    public BaseResponse createOrUpdateInvAdjustment(@RequestBody InvAdjustmentRequest request) {
        log.info("createOrUpdateInvAdjustment");
        return invFeign.createOrUpdateInvAdjustment(request);
    }

    @ApiOperation(value = "分页查询调整事项")
    @PostMapping("/getInvAdjustmentList")
    public BaseResponse getInvAdjustmentList(@RequestBody InvAdjustmentRequest request, @RequestHeader String token) {
        log.info("getInvAdjustmentList");
        return invFeign.getInvAdjustmentList(request, token);
    }

    @ApiOperation(value = "ID查询调整事项")
    @GetMapping("/getInvAdjustmentByIaId")
    public BaseResponse getInvAdjustmentByIaId(@RequestParam String iaId) {
        log.info("getInvAdjustmentByIaId");
        return invFeign.getInvAdjustmentByIaId(iaId);
    }

    @ApiOperation(value = "ID删除调整事项")
    @GetMapping("/deleteInvAdjustmentByIaId")
    public BaseResponse deleteInvAdjustmentByIaId(@RequestParam String iaId) {
        log.info("deleteInvAdjustmentByIaId");
        return invFeign.deleteInvAdjustmentByIaId(iaId);
    }
}
