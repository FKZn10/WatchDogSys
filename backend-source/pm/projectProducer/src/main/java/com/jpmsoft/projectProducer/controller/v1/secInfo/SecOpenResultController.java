package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecOpenResultRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/openResult")
public class SecOpenResultController {

    @Resource
    private ISecOpenResultService secOpenResultService;


    @TenantId
    @PostMapping("/createOrUpdateSecOpenResult")
    public BaseResponse createOrUpdateSecOpenResult(@RequestBody SecOpenResultRequest request) {
        log.info("createOrUpdateSecOpenResult");
        return secOpenResultService.createOrUpdateSecOpenResult(request);
    }

    @GetMapping("/getSecOpenResultById")
    public BaseResponse getSecOpenResultById(@RequestParam String orId) {
        log.info("getSecOpenResultById");
        return secOpenResultService.getSecOpenResultById(orId);
    }

    @TenantId
    @PostMapping("/getSecOpenResultList")
    public BaseResponse getSecOpenResultList(@RequestBody SecOpenResultRequest request) {
        log.info("getSecOpenResultList");
        return secOpenResultService.getSecOpenResultList(request);
    }

    @TenantId
    @PostMapping("/getSecOpenResultListOne2Many")
    public BaseResponse getSecOpenResultListOne2Many(@RequestBody SecOpenResultRequest request) {
        log.info("getSecOpenResultListOne2Many");
        return secOpenResultService.getSecOpenResultListOne2Many(request);
    }
}
