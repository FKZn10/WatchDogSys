package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "代建合同实拨", value = "代建合同实拨")
@RequestMapping("/v1/aaAllocation")
public class AgentActualAllocationController {
    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改代建合同实拨")
    @PostMapping("/createOrUpdateAgentActualAllocation")
    public BaseResponse createOrUpdateAgentActualAllocation(@RequestBody AgentActualAllocationModel request) {
        log.info("createOrUpdateAgentActualAllocation");
        return conFeign.createOrUpdateAgentActualAllocation(request);
    }

    @ApiOperation(value = "分页查询代建合同实拨")
    @PostMapping("/getAgentActualAllocationList")
    public BaseResponse getAgentActualAllocationList(@RequestBody AgentActualAllocationModel request, @RequestHeader String token) {
        log.info("getAgentActualAllocationList");
        return conFeign.getAgentActualAllocationList(request, token);
    }

    @ApiOperation(value = "ID查询代建合同实拨")
    @GetMapping("/getAgentActualAllocationById")
    public BaseResponse getAgentActualAllocationById(@RequestParam String aalId) {
        log.info("getAgentActualAllocationById");
        return conFeign.getAgentActualAllocationById(aalId);
    }

    @ApiOperation(value = "根据合同id con_Id从资金实拨表中获取所有审批通过的实拨金额aalThisPay")
    @GetMapping("/getAalThisPayByConId")
    public BaseResponse getAalThisPayByConId(@RequestParam String conId) {
        log.info("getAalThisPayByConId");
        return conFeign.getAalThisPayByConId(conId);
    }

    @ApiOperation(value = "根据子合同id Aal_Agent_Con_Id 从资金实拨中获取审核通过金额Aal_This_Pay_Money_Exa")
    @GetMapping("/getAalThisPayMoneyByAgentConId")
    public BaseResponse getAalThisPayMoneyByAgentConId(@RequestParam String aalAgentConId) {
        log.info("getAalThisPayMoneyByAgentConId");
        return conFeign.getAalThisPayMoneyByAgentConId(aalAgentConId);
    }
}
