package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementEndDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementEndService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invSettlementEnd")
public class InvSettlementEndController {

    @Resource
    private IInvSettlementEndService invSettlementEndService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvSettlementEnd")
    public BaseResponse createOrUpdateInvSettlementEnd(@RequestBody InvSettlementEndDTO request) {
        log.info("createOrUpdateInvSettlementEnd");
        return invSettlementEndService.createOrUpdateInvSettlementEnd(request);
    }


    @PostMapping("/updateInvSettlementEndOnlyInfo")
    public BaseResponse updateInvSettlementEndOnlyInfo(@RequestBody InvSettlementEndDTO request) {
        log.info("updateInvSettlementEndOnlyInfo");
        return invSettlementEndService.updateInvSettlementEndOnlyInfo(request);
    }

    @TenantId
    @PostMapping("/getInvSettlementEndList")
    public BaseResponse getInvSettlementEndList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token) {
        log.info("getInvSettlementEndList");
        return invSettlementEndService.getInvSettlementEndList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getInvSettlementEndNoAuthList")
    public BaseResponse getInvSettlementEndNoAuthList(@RequestBody InvSettlementEndDTO request, @RequestHeader String token) {
        log.info("getInvSettlementEndNoAuthList");
        return invSettlementEndService.getInvSettlementEndNoAuthList(request, redisUtils.getUid(token));
    }

    /**
     * 定时任务管理配置定时调用 不对外开放调用方法
     *
     * @return
     */
    @GetMapping("/refreshSettlementEndOtherTableInfo")
    public BaseResponse refreshSettlementEndOtherTableInfo() {
        log.info("refreshSettlementEndOtherTableInfo");
        return invSettlementEndService.refreshSettlementEndOtherTableInfo();
    }

    @GetMapping("/getInvSettlementEndBySaeId")
    public BaseResponse getInvSettlementEndBySaeId(@RequestParam String saId) {
        log.info("getInvSettlementEndBySaeId");
        return invSettlementEndService.getInvSettlementEndBySaeId(saId);
    }

    @GetMapping("/deleteInvSettlementEndBySaeId")
    public BaseResponse deleteInvSettlementEndBySaeId(@RequestParam String saId) {
        log.info("deleteInvSettlementEndBySaeId");
        return invSettlementEndService.deleteInvSettlementEndBySaeId(saId);
    }

    @GetMapping("/getLastApprovalInvSettlementEndByConId")
    public BaseResponse getLastApprovalInvSettlementEndByConId(@RequestParam String conId) {
        log.info("getLastApprovalInvSettlementEndByConId");
        return invSettlementEndService.getLastApprovalInvSettlementEndByConId(conId);
    }

    @GetMapping("/getLastApprovalInvSettlementEndByPayId")
    public BaseResponse getLastApprovalInvSettlementEndByPayId(@RequestParam String id) {
        log.info("getLastApprovalInvSettlementEndByPayId");
        return invSettlementEndService.getLastApprovalInvSettlementEndByPayId(id);
    }

}
