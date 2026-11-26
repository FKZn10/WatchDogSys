package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectDtlRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectDtlService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/collectDtl")
public class PeInfoCollectDtlController {
    @Resource
    private IPeInfoCollectDtlService peInfoCollectDtlService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 查询未生成清单的所有履约评价
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getPeInfoNotGenerateCollectDtl")
    public BaseResponse getPeInfoNotGenerateCollectDtl(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token) {
        log.info("getPeInfoNotGenerateCollectDtl");
        return peInfoCollectDtlService.getPeInfoNotGenerateCollectDtl(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getPeInfoCollectDtlList")
    public BaseResponse getPeInfoCollectDtlList(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token) {
        log.info("getPeInfoCollectDtlList");
        return peInfoCollectDtlService.getPeInfoCollectDtlList(request, redisUtils.getUid(token));
    }
}
