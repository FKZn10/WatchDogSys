package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.service.investment.IFundPlanModifyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/fundPlanModify")
public class FundPlanModifyController {
    @Resource
    private IFundPlanModifyService fundPlanModifyService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/createFundPlanModify")
    public BaseResponse createFundPlanModify(@RequestParam String ifrId) {
        log.info("createFundPlanModify");
        return fundPlanModifyService.createFundPlanModify(ifrId);
    }


    @GetMapping("/getFundPlanModifyBypPid")
    public BaseResponse getFundPlanModifyBypPid(@RequestParam String pId) {
        log.info("getFundPlanModifyBypPid");
        return fundPlanModifyService.getFundPlanModifyBypPid(pId);
    }


    @TenantId
    @PostMapping("/getFundPlanModifyList")
    public BaseResponse getFundPlanModifyList(@RequestBody FundPlanModifyDto request, @RequestHeader String token) {
        log.info("getFundPlanModifyList");
        return fundPlanModifyService.getFundPlanModifyList(request, redisUtils.getUid(token));
    }


    @PostMapping("/updateFundPlanModify")
    public BaseResponse updateFundPlanModify(@RequestBody FundPlanModifyDto request) {
        log.info("updateFundPlanModify");
        return fundPlanModifyService.updateFundPlanModify(request);
    }

    @GetMapping("/getFundPlanModifyByPidAndYear")
    public BaseResponse getFundPlanModifyByPidAndYear(@RequestParam String pId, @RequestParam Integer year, @RequestParam String newDate) {
        log.info("getFundPlanModifyByPidAndYear");
        return fundPlanModifyService.getFundPlanModifyByPidAndYear(pId, year, newDate);
    }


    @GetMapping("/getFundPlanModifyHandleBypPid")
    public BaseResponse getFundPlanModifyHandleBypPid(@RequestParam String pId) {
        log.info("getFundPlanModifyHandleBypPid");
        return fundPlanModifyService.getFundPlanModifyHandleBypPid(pId);
    }


}
