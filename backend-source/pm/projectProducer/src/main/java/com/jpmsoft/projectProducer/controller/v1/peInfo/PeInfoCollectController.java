package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/collect")
public class PeInfoCollectController {
    @Resource
    private IPeInfoCollectService peInfoCollectService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdatePeInfoCollect")
    public BaseResponse createOrUpdatePeInfoCollect(@RequestBody PeInfoCollectRequest request) {
        log.info("createOrUpdatePeInfoCollect");
        return peInfoCollectService.createOrUpdatePeInfoCollect(request);
    }

    @GetMapping("/deletePeInfoCollectByPecId")
    public BaseResponse deletePeInfoCollectByPecId(@RequestParam String pecId) {
        log.info("deletePeInfoCollectByPecId");
        return peInfoCollectService.deletePeInfoCollectByPecId(pecId);
    }

    @PostMapping("/updatePeInfoCollectFlowState")
    public BaseResponse updatePeInfoCollectFlowState(@RequestBody PeInfoCollectRequest request) {
        log.info("updatePeInfoCollectFlowState");
        return peInfoCollectService.updatePeInfoCollectFlowState(request);
    }

    @TenantId
    @PostMapping("/getPeInfoCollectList")
    public BaseResponse getPeInfoCollectList(@RequestBody PeInfoCollectRequest request) {
        log.info("getPeInfoCollectList");
        return peInfoCollectService.getPeInfoCollectList(request);
    }

    @GetMapping("/getPeInfoCollectByPecId")
    public BaseResponse getPeInfoCollectByPecId(@RequestParam String pecId) {
        log.info("getPeInfoCollectByPecId");
        return peInfoCollectService.getPeInfoCollectByPecId(pecId);
    }

    @GetMapping("/getPeInfoCollectExportByPecId")
    public BaseResponse getPeInfoCollectExportByPecId(@RequestParam String pecId, @RequestHeader String token) {
        log.info("getPeInfoCollectExportByPecId");
        return peInfoCollectService.getPeInfoCollectExportByPecId(pecId, redisUtils.getUid(token));
    }

    @GetMapping("/deletePeInfoCollectDtlWhenBackByPecId")
    public BaseResponse deletePeInfoCollectDtlWhenBackByPecId(@RequestParam String pecId) {
        log.info("deletePeInfoCollectDtlWhenBackByPecId");
        return peInfoCollectService.deletePeInfoCollectDtlWhenBackByPecId(pecId);
    }
}
