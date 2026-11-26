package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvProjectPlanRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "投资项目计划", value = "投资项目计划")
@RequestMapping("/inv/v1/invProjectPlan")
public class InvProjectPlanController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "获取资金下达列表")
    @PostMapping("/getInvProjectPlanList")
    public BaseResponse getInvProjectPlanList(@RequestBody InvProjectPlanRequest request, @RequestHeader String token) {
        log.info("getInvProjectPlanList");
        return invFeign.getInvProjectPlanList(request, token);
    }

    @ApiOperation(value = "本年度累计下达金额和累计下达金额")
    @GetMapping("/getInvProPlanHistoryAmt")
    public BaseResponse getInvProPlanHistoryAmt(@RequestParam String pId, @RequestParam String year) {
        log.info("getInvProPlanHistoryAmt");
        return invFeign.getInvProPlanHistoryAmt(pId, year);
    }


    @ApiOperation(value = "ID查询资金计划")
    @GetMapping("/getInvProjectPlanById")
    public BaseResponse getInvProjectPlanById(@RequestParam String ippId) {
        log.info("getInvProjectPlanById");
        return invFeign.getInvProjectPlanById(ippId);
    }

    @ApiOperation(value = "根据项目id、年度获取”剩余下达资金大于0“的投资计划信息")
    @GetMapping("/getInvProjectPlanByPIdAndYear")
    public BaseResponse getInvProjectPlanByPIdAndYear(@RequestParam String pId, @RequestParam Integer year,@RequestParam String fundsType) {
        log.info("getInvProjectPlanByPIdAndYear");
        return invFeign.getInvProjectPlanByPIdAndYear(pId, year,fundsType);
    }

    @ApiOperation(value = "创建修改资金计划")
    @PostMapping("/createOrUpdateInvProjectPlan")
    public BaseResponse createOrUpdateInvProjectPlan(@RequestBody InvProjectPlanRequest request) {
        log.info("createOrUpdateInvProjectPlan");
        return invFeign.createOrUpdateInvProjectPlan(request);
    }
}
