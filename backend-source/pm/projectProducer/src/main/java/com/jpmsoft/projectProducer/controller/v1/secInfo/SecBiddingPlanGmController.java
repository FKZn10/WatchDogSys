package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanGmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 光明招标计划
 *
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 13:57
 */
@Slf4j
@RestController
@RequestMapping("/v1/biddingPlanGm")
public class SecBiddingPlanGmController {

    @Resource
    private ISecBiddingPlanGmService secBiddingPlanGmService;

    @GetMapping("selectLastApprovedMoney")
    public BaseResponse selectLastApprovedMoney(@RequestParam String secId) {
        return secBiddingPlanGmService.selectLastApprovedMoney(secId);
    }
}
