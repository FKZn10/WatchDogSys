package com.jpmsoft.projectConsumer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupOnSiteRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "监理管理-旁站记录", value = "监理管理-旁站记录")
@RequestMapping("/v1/conSupOnSite")
public class ConSupOnSiteController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改旁站记录")
    @PostMapping("/createOrUpdateConSupOnSite")
    public BaseResponse createOrUpdateConSupOnSite(@RequestBody ConSupOnSiteRequest request) {
        log.info("createOrUpdateConSupOnSite");
        return baseFeign.createOrUpdateConSupOnSite(request);
    }

    @ApiOperation(value = "分页查询旁站记录")
    @PostMapping("/getConSupOnSiteList")
    public BaseResponse getConSupOnSiteList(@RequestBody ConSupOnSiteRequest request, @RequestHeader String token) {
        log.info("getConSupOnSiteList");
        return baseFeign.getConSupOnSiteList(request, token);
    }

    @ApiOperation(value = "ID查询旁站记录")
    @GetMapping("/getConSupOnSiteById")
    public BaseResponse getConSupOnSiteById(@RequestParam String osId) {
        log.info("getConSupOnSiteById");
        return baseFeign.getConSupOnSiteById(osId);
    }
}
