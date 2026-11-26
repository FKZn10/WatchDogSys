package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peFormDetail")
public class PeFormDetailController {

    @Resource
    private IPeFormDetailService peFormDetailService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeFormDetail")
    public BaseResponse createOrUpdatePeFormDetail(@RequestBody PeFormDetailRequest request) {
        log.info("createOrUpdatePeFormDetail");
        return peFormDetailService.createOrUpdatePeFormDetail(request);
    }

    @TenantId
    @PostMapping("/getPeFormDetailList")
    public BaseResponse getPeFormDetailList(@RequestBody PeFormDetailRequest request, @RequestHeader String token) {
        log.info("getPeFormDetailList");
        return peFormDetailService.getPeFormDetailList(request, redisUtils.getUid(token));
    }

    @GetMapping("/deletePeFormDetailByFdId")
    public BaseResponse deletePeFormDetailByFdId(@RequestParam String fdId) {
        log.info("deletePeFormDetailByFdId");
        return peFormDetailService.deletePeFormDetailByFdId(fdId);
    }
}
