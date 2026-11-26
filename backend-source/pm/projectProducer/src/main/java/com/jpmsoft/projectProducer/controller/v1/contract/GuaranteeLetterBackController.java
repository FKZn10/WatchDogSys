package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterBackRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterBackService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/GuaranteeLetter")
public class GuaranteeLetterBackController {
    @Resource
    private IGuaranteeLetterBackService guaranteeLetterBackService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateGuaranteeLetterBack")
    public BaseResponse createOrUpdateGuaranteeLetterBack(@RequestBody GuaranteeLetterBackRequest request) {
        log.info("createOrUpdateGuaranteeLetterBack");
        return guaranteeLetterBackService.createOrUpdateGuaranteeLetterBack(request);
    }

    @Deprecated
    @TenantId
    @PostMapping("/getGuaranteeLetterBackList")
    public BaseResponse getGuaranteeLetterBackList(@RequestBody GuaranteeLetterBackRequest request, @RequestHeader String token) {
        log.info("getGuaranteeLetterBackList");
        return guaranteeLetterBackService.getGuaranteeLetterBackList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getGuaranteeLetterBackByglbId")
    public BaseResponse getGuaranteeLetterBackByglbId(@RequestParam String glbId) {
        log.info("getGuaranteeLetterBackByglbId");
        return guaranteeLetterBackService.getGuaranteeLetterBackByglbId(glbId);
    }

    @GetMapping("/getGuaranteeLetterBackAndSignByglbId")
    public BaseResponse getGuaranteeLetterBackAndSignByglbId(@RequestParam String glbId) {
        log.info("getGuaranteeLetterBackAndSignByglbId");
        return guaranteeLetterBackService.getGuaranteeLetterBackAndSignByglbId(glbId);
    }
}
