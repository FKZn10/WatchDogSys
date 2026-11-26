package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标清标", value = "招标清标")
@RequestMapping("/v1/brightMoon")
public class SecBrightMoonController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标清标")
    @PostMapping("/createOrUpdateSecBrightMoon")
    public BaseResponse createOrUpdateSecBrightMoon(@RequestBody SecBrightMoonModel request) {
        log.info("createOrUpdateSecBrightMoon");
        return secFeign.createOrUpdateSecBrightMoon(request);
    }

    @ApiOperation(value = "分页查询招标清标")
    @PostMapping("/getSecBrightMoonList")
    public BaseResponse getSecBrightMoonList(@RequestBody SecBrightMoonModel request) {
        log.info("getSecBrightMoonList");
        return secFeign.getSecBrightMoonList(request);
    }

    @ApiOperation(value = "ID查询招标清标")
    @GetMapping("/getSecBrightMoonById")
    public BaseResponse getSecBrightMoonById(@RequestParam String sbmId) {
        log.info("getSecBrightMoonById");
        return secFeign.getSecBrightMoonById(sbmId);
    }
}
