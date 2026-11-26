package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectDtlRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "考核分汇总清单", value = "考核分汇总清单")
@RequestMapping("/v1/collectDtl")
public class PeInfoCollectDtlController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    /**
     * 查询未生成清单的所有履约评价
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询未生成清单的所有履约评价")
    @PostMapping("/getPeInfoNotGenerateCollectDtl")
    public BaseResponse getPeInfoNotGenerateCollectDtl(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token) {
        log.info("getPeInfoNotGenerateCollectDtl");
        return peInfoFeign.getPeInfoNotGenerateCollectDtl(request, token);
    }


    @ApiOperation(value = "获取考核分汇总清单列表")
    @PostMapping("/getPeInfoCollectDtlList")
    public BaseResponse getPeInfoCollectDtlList(@RequestBody PeInfoCollectDtlRequest request, @RequestHeader String token) {
        log.info("getPeInfoCollectDtlList");
        return peInfoFeign.getPeInfoCollectDtlList(request, token);
    }
}
