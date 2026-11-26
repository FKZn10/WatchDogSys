package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignConstructionRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignConstructionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designConstruction")
public class DesignConstructionController {
    @Resource
    private IDesignConstructionService designConstructionService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateDesignConstruction")
    public BaseResponse createOrUpdateDesignConstruction(@RequestBody DesignConstructionRequest request,@RequestHeader String token) {
        log.info("createOrUpdateDesignConstruction");
        return designConstructionService.createOrUpdateDesignConstruction(request,token);
    }

    @TenantId
    @PostMapping("/getDesignConstructionList")
    public BaseResponse getDesignConstructionList(@RequestBody DesignConstructionRequest request, @RequestHeader String token) {
        log.info("getDesignConstructionList");
        return designConstructionService.getDesignConstructionList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignConstructionStatisticsList")
    public BaseResponse getDesignConstructionStatisticsList(@RequestBody DesignConstructionRequest request, @RequestHeader String token) {
        log.info("getDesignConstructionStatisticsList");
        return designConstructionService.getDesignConstructionStatisticsList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignConstructionListNoAuth")
    public BaseResponse getDesignConstructionListNoAuth(@RequestBody DesignConstructionRequest request) {
        log.info("getDesignConstructionListNoAuth");
        return designConstructionService.getDesignConstructionListNoAuth(request);
    }

    @GetMapping("/deleteDesignConstructionBydcId")
    public BaseResponse deleteDesignConstructionBydcId(@RequestParam String dcId, @RequestHeader String token) {
        log.info("deleteDesignConstructionBydcId");
        return designConstructionService.deleteDesignConstructionBydcId(dcId,token);
    }

    @GetMapping("/getDesignConstructionBydcId")
    public BaseResponse getDesignConstructionBydcId(@RequestParam String dcId) {
        log.info("getDesignConstructionBydcId");
        return designConstructionService.getDesignConstructionBydcId(dcId);
    }


    @PostMapping("/updateDesignConstructionApproveStatus")
    public BaseResponse updateDesignConstructionApproveStatus(@RequestBody DesignConstructionRequest request) {
        log.info("updateDesignConstructionApproveStatus");
        return designConstructionService.updateDesignConstructionApproveStatus(request);
    }

    /**
     * 下发状态切换
     *
     * @param dcId
     * @param issued
     * @return
     */
    @GetMapping("/updateDesignConstructionIssued")
    public BaseResponse updateDesignConstructionIssued(@RequestParam String dcId, @RequestParam(required = false) String issued) {
        log.info("updateDesignConstructionIssued");
        return designConstructionService.updateDesignConstructionIssued(dcId, issued);
    }
}
