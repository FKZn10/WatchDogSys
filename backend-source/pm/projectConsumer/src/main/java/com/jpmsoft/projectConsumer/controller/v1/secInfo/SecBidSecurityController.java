package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidSecurityModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "投标保证金", value = "投标保证金")
@RequestMapping("/v1/bidSecurity")
public class SecBidSecurityController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改投标保证金")
    @PostMapping("/createOrUpdateSecBidSecurity")
    public BaseResponse createOrUpdateSecBidSecurity(@RequestBody SecBidSecurityModel request) {
        log.info("createOrUpdateSecBidSecurity");
        return secFeign.createOrUpdateSecBidSecurity(request);
    }

    @ApiOperation(value = "分页查询投标保证金")
    @PostMapping("/getSecBidSecurityList")
    public BaseResponse getSecBidSecurityList(@RequestBody SecBidSecurityModel request) {
        log.info("getSecBidSecurityList");
        return secFeign.getSecBidSecurityList(request);
    }

    @ApiOperation(value = "ID查询投标保证金")
    @GetMapping("/getSecBidSecurityById")
    public BaseResponse getSecBidSecurityById(@RequestParam String sbsId) {
        log.info("getSecBidSecurityById");
        return secFeign.getSecBidSecurityById(sbsId);
    }
}
