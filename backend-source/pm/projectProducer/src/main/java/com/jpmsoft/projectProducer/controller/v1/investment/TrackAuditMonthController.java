package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementSecondService;
import com.jpmsoft.projectProducer.service.investment.ITrackAuditMonthService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author: shanzhi
 * @date: 2024/06/28 17:10
 */
@Slf4j
@RestController
@RequestMapping("/v1/trackAuditMonth")
public class TrackAuditMonthController {
    @Resource
    private ITrackAuditMonthService trackAuditMonthService;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getTrackAuditMonthByConId")
    public BaseResponse getTrackAuditMonthByConId(@RequestParam String conId,@RequestParam(value = "month", required = false) String month) {
        log.info("getInvSettlementSecondById");
        return trackAuditMonthService.getTrackAuditMonthByConId(conId,month);
    }

    @GetMapping("/getTrackAuditMonthListByPId")
    public BaseResponse getTrackAuditMonthListByPId(@RequestParam String pId) {
        log.info("getTrackAuditMonthListByPId");
        return trackAuditMonthService.getTrackAuditMonthListByPId(pId);
    }
    /**
     * 审批
     *
     * @param tamId
     * @return
     */
    @TenantId
    @GetMapping("/approvalTrackAuditMonth")
    public BaseResponse approvalTrackAuditMonth(@RequestParam String tamId) {
        log.info("approvalTrackAuditMonth");
        return trackAuditMonthService.approvalTrackAuditMonth(tamId);
    }
}
