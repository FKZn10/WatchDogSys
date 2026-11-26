package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultAppealRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeResultAppealService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/resultAppeal")
public class PeResultAppealController {

    @Resource
    private IPeResultAppealService peResultAppealService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateResultAppeal")
    public BaseResponse createOrUpdateResultAppeal(@RequestBody PeResultAppealRequest request) {
        log.info("createOrUpdateResultAppeal");
        return peResultAppealService.createOrUpdateResultAppeal(request);
    }

    @TenantId
    @PostMapping("/getResultAppealList")
    public BaseResponse getResultAppealList(@RequestBody PeResultAppealRequest request, @RequestHeader String token) {
        log.info("getResultAppealList");
        return peResultAppealService.getResultAppealList(request, redisUtils.getUid(token));
    }

    @PostMapping("/deleteResultAppealById")
    public BaseResponse deleteResultAppealById(@RequestBody PeResultAppealRequest request) {
        log.info("deleteResultAppealById");
        return peResultAppealService.deleteResultAppealById(request);
    }
}
