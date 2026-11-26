package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaRecordRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IConVisaRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conVisaRecord")
public class ConVisaRecordController {
    @Resource
    private IConVisaRecordService conVisaRecordService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConVisaRecord")
    public BaseResponse createOrUpdateConVisaRecord(@RequestBody ConVisaRecordRequest request) {
        log.info("createOrUpdateConVisaRecord");
        return conVisaRecordService.createOrUpdateConVisaRecord(request);
    }

    @TenantId
    @PostMapping("/getConVisaRecordList")
    public BaseResponse getConVisaRecordList(@RequestBody ConVisaRecordRequest request, @RequestHeader String token) {
        log.info("getConVisaRecordList");
        return conVisaRecordService.getConVisaRecordList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConVisaRecordByVrId")
    public BaseResponse getConVisaRecordByVrId(@RequestParam String vrId) {
        log.info("getConVisaRecordByVrId");
        return conVisaRecordService.getConVisaRecordByVrId(vrId);
    }
}
