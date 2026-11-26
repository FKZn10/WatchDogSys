package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeResultService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peResult")
public class PeResultController {


    @Resource
    private IPeResultService peResultService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/signPeResult")
    public BaseResponse signPeResult(@RequestBody PeResultRequest request) {
        log.info("signPeResult");
        return peResultService.signPeResult(request);
    }

    @TenantId
    @PostMapping("/getPeResultList")
    public BaseResponse getPeResultList(@RequestBody PeResultRequest request, @RequestHeader String token) {
        log.info("getPeResultList");
        return peResultService.getPeResultList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getPeResultByRid")
    public BaseResponse getPeResultByRid(@RequestBody PeResultRequest request) {
        log.info("getPeResultByRid");
        return peResultService.getPeResultByRid(request);
    }
}
