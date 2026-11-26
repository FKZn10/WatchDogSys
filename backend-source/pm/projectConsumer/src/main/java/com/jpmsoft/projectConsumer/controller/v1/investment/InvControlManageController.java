package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvControlManageModel;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "投资控制", value = "投资控制")
@RequestMapping("/inv/v1/invControlManage")
public class InvControlManageController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改投资控制")
    @PostMapping("/createOrUpdateInvControlManage")
    public BaseResponse createOrUpdateInvControlManage(@RequestBody InvControlManageModel request) {
        log.info("createOrUpdateInvControlManage");
        return invFeign.createOrUpdateInvControlManage(request);
    }

    @ApiOperation(value = "分页查询投资控制")
    @PostMapping("/getInvControlManageList")
    public BaseResponse getInvControlManageList(@RequestBody InvControlManageModel request, @RequestHeader String token) {
        log.info("getInvControlManageList");
        return invFeign.getInvControlManageList(request, token);
    }

    @ApiOperation(value = "ID查询投资控制")
    @GetMapping("/getInvControlManageById")
    public BaseResponse getInvControlManageById(@RequestParam String icmId) {
        log.info("getInvControlManageById");
        return invFeign.getInvControlManageById(icmId);
    }
}
