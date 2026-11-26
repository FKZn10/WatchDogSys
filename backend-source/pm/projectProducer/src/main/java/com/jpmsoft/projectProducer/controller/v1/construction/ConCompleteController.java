package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConCompleteService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conComplete")
public class ConCompleteController {

    @Resource
    private IConCompleteService conCompleteService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConComplete")
    public BaseResponse createOrUpdateConComplete(@RequestBody ConCompleteModel request) {
        log.info("createOrUpdateConComplete");
        return conCompleteService.createOrUpdateConComplete(request);
    }

    @TenantId
    @PostMapping("/getConCompleteList")
    public BaseResponse getConCompleteList(@RequestBody ConCompleteModel request, @RequestHeader String token) {
        log.info("getConCompleteList");
        return conCompleteService.getConCompleteList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConCompleteById")
    public BaseResponse getConCompleteById(@RequestParam String coId) {
        log.info("getConCompleteById");
        return conCompleteService.getConCompleteById(coId);
    }
}
