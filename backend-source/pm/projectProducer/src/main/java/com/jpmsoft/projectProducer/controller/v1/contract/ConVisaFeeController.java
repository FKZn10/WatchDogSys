package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaFeeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IConVisaFeeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conVisaFee")
public class ConVisaFeeController {
    @Resource
    private IConVisaFeeService conVisaFeeService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConVisaFee")
    public BaseResponse createOrUpdateConVisaFee(@RequestBody ConVisaFeeRequest request) {
        log.info("createOrUpdateConVisaFee");
        return conVisaFeeService.createOrUpdateConVisaFee(request);
    }

    @TenantId
    @PostMapping("/getConVisaFeeList")
    public BaseResponse getConVisaFeeList(@RequestBody ConVisaFeeRequest request, @RequestHeader String token) {
        log.info("getConVisaFeeList");
        return conVisaFeeService.getConVisaFeeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConVisaFeeByVfId")
    public BaseResponse getConVisaFeeByVfId(@RequestParam String vfId, @RequestParam(required = false) Integer conWhere) {
        log.info("getConVisaFeeByVfId");
        return conVisaFeeService.getConVisaFeeByVfId(vfId, conWhere);
    }

    @GetMapping("/getVisaAmt")
    public BaseResponse  getVisaAmt(@RequestParam String conId){
        log.info("getVisaAmt");
        return conVisaFeeService.getVisaAmt(conId);

    }
}
