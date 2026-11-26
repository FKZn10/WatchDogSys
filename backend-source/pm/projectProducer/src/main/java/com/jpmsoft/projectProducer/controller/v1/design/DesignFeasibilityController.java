package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignFeasibilityService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designFeasibility")
public class DesignFeasibilityController {

    @Resource
    private IDesignFeasibilityService designFeasibilityService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDesignFeasibility")
    public BaseResponse createOrUpdateDesignFeasibility(@RequestBody DesignFeasibilityModel request) {
        log.info("getDesignFeasibilityList");
        return designFeasibilityService.createOrUpdateDesignFeasibility(request);
    }

    @TenantId
    @PostMapping("/getDesignFeasibilityList")
    public BaseResponse getDesignFeasibilityList(@RequestBody DesignFeasibilityModel request, @RequestHeader String token) {
        log.info("getDesignFeasibilityList");
        return designFeasibilityService.getDesignFeasibilityList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignFeasibilityListNoAuth")
    public BaseResponse getDesignFeasibilityListNoAuth(@RequestBody DesignFeasibilityModel request) {
        log.info("getDesignFeasibilityListNoAuth");
        return designFeasibilityService.getDesignFeasibilityListNoAuth(request);
    }

    @GetMapping("/getDesignFeasibilityById")
    public BaseResponse getDesignFeasibilityById(@RequestParam String dfId) {
        log.info("getDesignFeasibilityById");
        return designFeasibilityService.getDesignFeasibilityById(dfId);
    }


    @PostMapping("/updateDesignFeasibilityApproveStatus")
    public BaseResponse updateDesignFeasibilityApproveStatus(@RequestBody DesignFeasibilityModel request) {
        log.info("updateDesignFeasibilityApproveStatus");
        return designFeasibilityService.updateDesignFeasibilityApproveStatus(request);
    }

}
