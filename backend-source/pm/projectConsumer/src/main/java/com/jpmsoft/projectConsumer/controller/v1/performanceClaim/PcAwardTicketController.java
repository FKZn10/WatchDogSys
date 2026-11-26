package com.jpmsoft.projectConsumer.controller.v1.performanceClaim;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Claim.PcAwardTicketRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "履约索赔-奖罚单", value = "履约索赔-奖罚单")
@RequestMapping("/claim/v1/awardTicket")
public class PcAwardTicketController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改奖罚单")
    @PostMapping("/createOrUpdatePcAwardTicket")
    public BaseResponse createOrUpdatePcAwardTicket(@RequestBody PcAwardTicketRequest request) {
        log.info("createOrUpdatePcAwardTicket");
        return baseFeign.createOrUpdatePcAwardTicket(request);
    }

    @ApiOperation(value = "分页查询奖罚单")
    @PostMapping("/getPcAwardTicketList")
    public BaseResponse getPcAwardTicketList(@RequestBody PcAwardTicketRequest request, @RequestHeader String token) {
        log.info("getPcAwardTicketList");
        return baseFeign.getPcAwardTicketList(request, token);
    }

    @ApiOperation(value = "ID查询奖罚单")
    @GetMapping("/getPcAwardTicketById")
    public BaseResponse getPcAwardTicketById(@RequestParam String atId) {
        log.info("getPcAwardTicketById");
        return baseFeign.getPcAwardTicketById(atId);
    }

    /**
     * 查询指定合同流程未结束的奖罚单数量
     *
     * @param contractId
     * @return
     */
    @GetMapping("/countUnfinishedFlowByContractId")
    public BaseResponse countUnFinishFlowByContractId(String contractId, Integer atType) {
        if (atType == null){
            atType = 1;// 惩罚单
        }
        return baseFeign.countUnFinishFlowByContractId(contractId,atType);
    }

}
