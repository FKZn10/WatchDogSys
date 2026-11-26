package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "招标方案", value = "招标方案")
@RequestMapping("/v1/biddingScheme")
public class SecBiddingSchemeController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标方案")
    @PostMapping("/createOrUpdateSecBiddingScheme")
    public BaseResponse createOrUpdateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request) {
        log.info("createOrUpdateSecBiddingScheme");
        return secFeign.createOrUpdateSecBiddingScheme(request);
    }

    @ApiOperation(value = "分页查询招标方案")
    @PostMapping("/getSecBiddingSchemeList")
    public BaseResponse getSecBiddingSchemeList(@RequestBody SecBiddingSchemeModel request) {
        log.info("getSecBiddingSchemeList");
        return secFeign.getSecBiddingSchemeList(request);
    }

    @ApiOperation(value = "ID查询招标方案")
    @GetMapping("/getSecBiddingSchemeById")
    public BaseResponse getSecBiddingSchemeById(@RequestParam String bsId) {
        log.info("getSecBiddingSchemeById");
        return secFeign.getSecBiddingSchemeById(bsId);
    }


    @ApiOperation(value = "修改招标方案")
    @PostMapping("/updateSecBiddingScheme")
    public BaseResponse updateSecBiddingScheme(@RequestBody SecBiddingSchemeModel request) {
        log.info("updateSecBiddingScheme");
        return secFeign.updateSecBiddingScheme(request);
    }
}
