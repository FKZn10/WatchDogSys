package com.jpmsoft.projectProducer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvcrRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.visaChange.IConSvcrService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSvcr")
public class ConSvcrController {

    @Resource
    private IConSvcrService conSvcrService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConSvcr")
    public BaseResponse createOrUpdateConSvcr(@RequestBody ConSvcrRequest request) {
        log.info("createOrUpdateConSvcr");
        return conSvcrService.createOrUpdateConSvcr(request);
    }

    @TenantId
    @PostMapping("/getConSvcrList")
    public BaseResponse getConSvcrList(@RequestBody ConSvcrRequest request, @RequestHeader String token) {
        log.info("getConSvcrList");
        return conSvcrService.getConSvcrList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConSvcrById")
    public BaseResponse getConSvcrById(@RequestParam String svcrId) {
        log.info("getConSvcrById");
        return conSvcrService.getConSvcrById(svcrId);
    }
}
