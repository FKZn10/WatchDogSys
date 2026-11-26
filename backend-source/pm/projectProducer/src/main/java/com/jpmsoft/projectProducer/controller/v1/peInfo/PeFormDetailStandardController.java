package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailStandardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peFormDetail")
public class PeFormDetailStandardController {
    @Resource
    private IPeFormDetailStandardService peFormDetailStandardService;

    @TenantId
    @PostMapping("/createOrUpdatePeFormStandard")
    public BaseResponse createOrUpdatePeFormStandard(@RequestBody PeFormDetailStandardRequest request) {
        log.info("createOrUpdatePeFormStandard");
        return peFormDetailStandardService.createOrUpdatePeFormStandard(request);
    }

    @TenantId
    @PostMapping("/getPeFormStandardList")
    public BaseResponse getPeFormStandardList(@RequestBody PeFormDetailStandardRequest request) {
        log.info("getPeFormStandardList");
        return peFormDetailStandardService.getPeFormStandardList(request);
    }

    @GetMapping("/deletePeFormStandardById")
    public BaseResponse deletePeFormStandardById(@RequestParam String sId) {
        log.info("deletePeFormStandardById");
        return peFormDetailStandardService.deletePeFormStandardById(sId);
    }
}
