package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminDocRequest;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminDocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/determinDoc")
public class SecDeterminDocController {

    @Resource
    private ISecDeterminDocService secDeterminDocService;

    @PostMapping("/createOrUpdateDeterminDoc")
    public BaseResponse createOrUpdateDeterminDoc(@RequestBody SecDeterminDocRequest request) {
        log.info("createOrUpdateDeterminDoc");
        return secDeterminDocService.createOrUpdateDeterminDoc(request);
    }

    @GetMapping("/cancelSecDeterminDocByBdId")
    public BaseResponse cancelSecDeterminDocByBdId(@RequestParam String bdId) {
        log.info("cancelSecDeterminDocByBdId");
        return secDeterminDocService.cancelSecDeterminDocByBdId(bdId);
    }

    @GetMapping("/getSecDeterminDocByBdId")
    public BaseResponse getSecDeterminDocByBdId(@RequestParam String bdId) {
        log.info("getSecDeterminDocByBdId");
        return secDeterminDocService.getSecDeterminDocByBdId(bdId);
    }
}
