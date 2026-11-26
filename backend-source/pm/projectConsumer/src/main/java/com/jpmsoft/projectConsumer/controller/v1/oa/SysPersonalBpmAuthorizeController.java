package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.SysPersonalBpmAuthorizeModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "AB授权", value = "AB授权")
@RequestMapping("/OA/abAuthorize")
public class SysPersonalBpmAuthorizeController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "创建或修改AB授权")
    @PostMapping("/createOrUpdatePersonalBpmAuthorize")
    public BaseResponse createOrUpdatePersonalBpmAuthorize(@RequestBody SysPersonalBpmAuthorizeModel request, @RequestHeader String token) {
        log.info("createOrUpdatePersonalBpmAuthorize");
        return oaFeign.createOrUpdatePersonalBpmAuthorize(request,token);
    }

    @ApiOperation(value = "分页查询当前用户AB授权")
    @PostMapping("/getSelfPersonalBpmAuthorizeList")
    public BaseResponse getSelfPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request, @RequestHeader String token) {
        log.info("getSelfPersonalBpmAuthorizeList");
        return oaFeign.getSelfPersonalBpmAuthorizeList(request, token);
    }

    @ApiOperation(value = "分页查询Ab授权")
    @PostMapping("/getPersonalBpmAuthorizeList")
    public BaseResponse getPersonalBpmAuthorizeList(@RequestBody SysPersonalBpmAuthorizeModel request) {
        log.info("getPersonalBpmAuthorizeList");
        return oaFeign.getPersonalBpmAuthorizeList(request);
    }

    @ApiOperation(value = "ID查询AB授权")
    @GetMapping("/getPersonalBpmAuthorizeById")
    public BaseResponse getPersonalBpmAuthorizeById(@RequestParam String baId) {
        log.info("getPersonalBpmAuthorizeById");
        return oaFeign.getPersonalBpmAuthorizeById(baId);
    }
}
