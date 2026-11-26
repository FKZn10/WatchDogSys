package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignInitialRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignInitialService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designInitial")
public class DesignInitialController {
    @Resource
    private IDesignInitialService designInitialService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateDesignInitial")
    public BaseResponse createOrUpdateDesignInitial(@RequestBody DesignInitialRequest request) {
        log.info("createOrUpdateDesignInitial");
        return designInitialService.createOrUpdateDesignInitial(request);
    }

    @TenantId
    @PostMapping("/getDesignInitialList")
    public BaseResponse getDesignInitialList(@RequestBody DesignInitialRequest request, @RequestHeader String token) {
        log.info("getDesignInitialList");
        return designInitialService.getDesignInitialList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignInitialStatisticsList")
    public BaseResponse getDesignInitialStatisticsList(@RequestBody DesignInitialRequest request, @RequestHeader String token) {
        log.info("getDesignInitialStatisticsList");
        return designInitialService.getDesignInitialStatisticsList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignInitialListNoAuth")
    public BaseResponse getDesignInitialListNoAuth(@RequestBody DesignInitialRequest request) {
        log.info("getDesignInitialListNoAuth");
        return designInitialService.getDesignInitialListNoAuth(request);
    }

    @GetMapping("/deleteDesignInitialBydiId")
    public BaseResponse deleteDesignInitialBydiId(@RequestParam String diId) {
        log.info("deleteDesignInitialBydiId");
        return designInitialService.deleteDesignInitialBydiId(diId);
    }

    @GetMapping("/getDesignInitialBydiId")
    public BaseResponse getDesignInitialBydiId(@RequestParam String diId) {
        log.info("getDesignInitialBydiId");
        return designInitialService.getDesignInitialBydiId(diId);
    }

    @PostMapping("/updateDesignInitialApproveStatus")
    public BaseResponse updateDesignInitialApproveStatus(@RequestBody DesignInitialRequest request) {
        log.info("updateDesignInitialApproveStatus");
        return designInitialService.updateDesignInitialApproveStatus(request);
    }

}
