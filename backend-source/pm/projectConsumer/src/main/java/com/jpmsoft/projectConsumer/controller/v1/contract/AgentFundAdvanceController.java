package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceModel;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "代建资金预拨", value = "代建资金预拨")
@RequestMapping("/v1/afAdvance")
public class AgentFundAdvanceController {
    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增或修改代建资金预拨")
    @PostMapping("/createOrUpdateAgentFundAdvance")
    public BaseResponse createOrUpdateAgentFundAdvance(@RequestBody AgentFundAdvanceModel request) {
        log.info("createOrUpdateAgentFundAdvance");
        return conFeign.createOrUpdateAgentFundAdvance(request);
    }

    @ApiOperation(value = "分页查询代建资金预拨")
    @PostMapping("/getAgentFundAdvanceList")
    public BaseResponse getAgentFundAdvanceList(@RequestBody AgentFundAdvanceModel request, @RequestHeader String token) {
        log.info("getAgentFundAdvanceList");
        return conFeign.getAgentFundAdvanceList(request, token);
    }

    @ApiOperation(value = "ID查询代建资金预拨")
    @GetMapping("/getAgentFundAdvanceById")
    public BaseResponse getAgentFundAdvanceById(@RequestParam String afaId) {
        log.info("getAgentFundAdvanceById");
        return conFeign.getAgentFundAdvanceById(afaId);
    }

    @ApiOperation(value = "根据合同id con_Id从资金预拨表中获取所有审批通过的金额afaThisMoney")
    @GetMapping("/getAfaThisMoneyByConId")
    public BaseResponse getAfaThisMoneyByConId(@RequestParam String conId) {
        log.info("getAfaThisMoneyByConId");
        return conFeign.getAfaThisMoneyByConId(conId);
    }

    @ApiOperation(value = "根据子合同idAal_Agent_Con_Id从资金实拨中获取审核通过金额Aal_This_Pay_Money_Exa")
    @GetMapping("/getAfalThisMoneyByAfalConId")
    public BaseResponse getAfalThisMoneyByAfalConId(@RequestParam String afalConId) {
        log.info("getAfalThisMoneyByAfalConId");
        return conFeign.getAfalThisMoneyByAfalConId(afalConId);
    }
}
