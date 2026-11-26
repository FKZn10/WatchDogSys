package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignConceptService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designConcept")
public class DesignConceptController {

    @Resource
    private IDesignConceptService designConceptService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateDesignConcept")
    public BaseResponse createOrUpdateDesignConcept(@RequestBody DesignConceptModel request) {
        log.info("getDesignConceptList");
        return designConceptService.createOrUpdateDesignConcept(request);
    }

    @TenantId
    @PostMapping("/getDesignConceptList")
    public BaseResponse getDesignConceptList(@RequestBody DesignConceptModel request, @RequestHeader String token) {
        log.info("getDesignConceptList");
        return designConceptService.getDesignConceptList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignConceptListNoAuth")
    public BaseResponse getDesignConceptListNoAuth(@RequestBody DesignConceptModel request) {
        log.info("getDesignConceptListNoAuth");
        return designConceptService.getDesignConceptListNoAuth(request);
    }

    @GetMapping("/getDesignConceptById")
    public BaseResponse getDesignConceptById(@RequestParam String dcId) {
        log.info("getDesignConceptById");
        return designConceptService.getDesignConceptById(dcId);
    }

    @PostMapping("/updateDesignConceptApproveStatus")
    public BaseResponse updateDesignConceptApproveStatus(@RequestBody DesignConceptModel request) {
        log.info("updateDesignConceptApproveStatus");
        return designConceptService.updateDesignConceptApproveStatus(request);
    }


}
