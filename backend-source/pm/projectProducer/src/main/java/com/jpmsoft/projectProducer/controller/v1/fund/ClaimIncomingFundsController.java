package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.fund.ClaimIncomingFundsRequest;
import com.jpmsoft.projectProducer.service.fund.IClaimIncomingFundsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 来款认领控制器
 *
 * @author tianhelin
 * @date 2025/11/17
 */
@Slf4j
@RestController
@RequestMapping("/v1/claimIncomingFunds")
public class ClaimIncomingFundsController {
    @Resource
    private IClaimIncomingFundsService claimIncomingFundsService;

    /**
     * 创建或更新来款认领
     *
     * @param request 来款认领请求对象
     * @return 操作结果
     */
    @PostMapping("/createOrUpdateClaimIncomingFunds")
    public BaseResponse createOrUpdateClaimIncomingFunds(@RequestBody ClaimIncomingFundsRequest request) {
        log.info("createOrUpdateClaimIncomingFunds");
        return claimIncomingFundsService.createOrUpdateClaimIncomingFunds(request);
    }

    /**
     * 来款认领同步到收款收据
     *
     * @param request
     * @return
     */
    @PostMapping("/syncToPaymentReceipt")
    public BaseResponse syncToPaymentReceipt(@RequestBody ClaimIncomingFundsRequest request) {
        log.info("syncToPaymentReceipt");
        return claimIncomingFundsService.syncToPaymentReceipt(request);
    }

    /**
     * 获取来款认领分页数据
     *
     * @param request 来款认领请求对象
     * @return 分页数据
     */
    @PostMapping("/getClaimIncomingFundsPage")
    public BaseResponse getClaimIncomingFundsPage(@RequestBody ClaimIncomingFundsRequest request) {
        log.info("getClaimIncomingFundsPage");
        return claimIncomingFundsService.getClaimIncomingFundsPage(request);
    }

    /**
     * 根据项目id获取来款认领信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getClaimIncomingFundsByPid")
    public BaseResponse getClaimIncomingFundsByPid(@RequestBody ClaimIncomingFundsRequest request) {
        log.info("getClaimIncomingFundsByPid");
        return claimIncomingFundsService.getClaimIncomingFundsByPid(request);
    }

    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId
     * @return
     */
    @GetMapping("/getGroupFundsCumulative")
    public BaseResponse getGroupFundsCumulative(@RequestParam String pId) {
        log.info("getGroupFundsCumulative");
        return claimIncomingFundsService.getGroupFundsCumulative(pId);
    }
}