package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterSignRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterSignService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/GuaranteeLetter")
public class GuaranteeLetterSignController {
    @Resource
    private IGuaranteeLetterSignService guaranteeLetterSignService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateGuaranteeLetterSign")
    public BaseResponse createOrUpdateGuaranteeLetterSign(@RequestBody GuaranteeLetterSignRequest request) {
        log.info("createOrUpdateGuaranteeLetterSign");
        return guaranteeLetterSignService.createOrUpdateGuaranteeLetterSign(request);
    }

    @TenantId
    @PostMapping("/getGuaranteeLetterSignList")
    public BaseResponse getGuaranteeLetterSignList(@RequestBody GuaranteeLetterSignRequest request, @RequestHeader String token) {
        log.info("getGuaranteeLetterSignList");
        return guaranteeLetterSignService.getGuaranteeLetterSignList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getGuaranteeLetterSignByglsId")
    public BaseResponse getGuaranteeLetterSignByglsId(@RequestParam String glsId) {
        log.info("getGuaranteeLetterSignByglsId");
        return guaranteeLetterSignService.getGuaranteeLetterSignByglsId(glsId);
    }

    @GetMapping("/getGuaranteeLetterSignByContract")
    public BaseResponse getGuaranteeLetterSignByContract(@RequestParam String conId) {
        log.info("getGuaranteeLetterSignByContract");
        return guaranteeLetterSignService.getGuaranteeLetterSignByContract(conId);
    }


    @GetMapping("/getGuaranteeLetterExistence")
    public BaseResponse getGuaranteeLetterExistence(@RequestParam String conId){
        log.info("getGuaranteeLetterExistence");
        return guaranteeLetterSignService.getGuaranteeLetterExistence(conId);
    }

}
