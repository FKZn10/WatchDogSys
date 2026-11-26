package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProcurementFilingModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementFilingService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/procurementFiling")
public class SecProcurementFilingController {

    @Resource
    private ISecProcurementFilingService secProcurementFilingService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecProcurementFiling")
    public BaseResponse createOrUpdateSecProcurementFiling(@RequestBody SecProcurementFilingModel request) {
        log.info("createOrUpdateSecProcurementFiling");
        return secProcurementFilingService.createOrUpdateSecProcurementFiling(request);
    }

    @TenantId
    @PostMapping("/getSecProcurementFilingList")
    public BaseResponse getSecProcurementFilingList(@RequestBody SecProcurementFilingModel request, @RequestHeader String token) {
        log.info("getSecProcurementFilingList");
        return secProcurementFilingService.getSecProcurementFilingList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecProcurementFilingById")
    public BaseResponse getSecProcurementFilingById(@RequestParam String pfId) {
        log.info("getSecProcurementFilingById");
        return secProcurementFilingService.getSecProcurementFilingById(pfId);
    }

    @GetMapping("/getSecProcurementFilingLastByPid")
    public BaseResponse getSecProcurementFilingLastByPid(@RequestParam String pId) {
        log.info("getSecProcurementFilingLastByPid");
        return secProcurementFilingService.getSecProcurementFilingLastByPid(pId);
    }

    @GetMapping("/approve2GenerateSecInfo")
    public BaseResponse approve2GenerateSecInfo(@RequestParam String pfId) {
        log.info("approve2GenerateSecInfo");
        return secProcurementFilingService.approve2GenerateSecInfo(pfId);
    }
}
