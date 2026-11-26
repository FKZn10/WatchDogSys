package com.jpmsoft.projectProducer.controller.v1.performanceClaim;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.model.request.Claim.PcAwardTicketRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.constants.AtTypeEnum;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcAwardTicket;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcAwardTicketService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/v1/awardTicket")
public class PcAwardTicketController {

    @Resource
    private IPcAwardTicketService pcAwardTicketService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePcAwardTicket")
    public BaseResponse createOrUpdatePcAwardTicket(@RequestBody PcAwardTicketRequest request) {
        log.info("createOrUpdatePcAwardTicket");
        return pcAwardTicketService.createOrUpdatePcAwardTicket(request);
    }

    @TenantId
    @PostMapping("/getPcAwardTicketList")
    public BaseResponse getPcAwardTicketList(@RequestBody PcAwardTicketRequest request, @RequestHeader String token) {
        log.info("getPcAwardTicketList");
        return pcAwardTicketService.getPcAwardTicketList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getPcAwardTicketById")
    public BaseResponse getPcAwardTicketById(@RequestParam String atId) {
        log.info("getPcAwardTicketById");
        return pcAwardTicketService.getPcAwardTicketById(atId);
    }

    /**
     * 查询指定合同流程未结束的奖罚单数量
     *
     * @param contractId
     * @return
     */
    @GetMapping("/countUnfinishedFlowByContractId")
    public BaseResponse countUnFinishFlowByContractId(String contractId, Integer atType) {
        if (atType == null) {
            atType = AtTypeEnum.PUNISH.getCode();
        }
        long count = pcAwardTicketService.count(new LambdaQueryWrapper<PcAwardTicket>()
                .eq(PcAwardTicket::getConId, contractId)
                .eq(PcAwardTicket::getAtType, atType)
                .in(PcAwardTicket::getAtState, Arrays.asList(
                                FlowStatusEnum.Null.getIndex(), FlowStatusEnum.Send.getIndex(),
                        FlowStatusEnum.SendBack.getIndex(), FlowStatusEnum.SendBackStart.getIndex(),
                        FlowStatusEnum.New.getIndex(), FlowStatusEnum.Pause.getIndex(),
                        FlowStatusEnum.ChangeFlow.getIndex())));
        return BaseResponse.ok(count);
    }
}
