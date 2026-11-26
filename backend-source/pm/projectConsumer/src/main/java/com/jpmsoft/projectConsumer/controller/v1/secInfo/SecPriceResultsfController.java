package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "巡价结果", value = "巡价结果")
@RequestMapping("/v1/priceResultsf")
public class SecPriceResultsfController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改巡价结果")
    @PostMapping("/createOrUpdateSecPriceResultsf")
    public BaseResponse createOrUpdateSecPriceResultsf(@RequestBody SecPriceResultsfModel request) {
        log.info("createOrUpdateSecPriceResultsf");
        return secFeign.createOrUpdateSecPriceResultsf(request);
    }

    @ApiOperation(value = "分页查询巡价结果")
    @PostMapping("/getSecPriceResultsfList")
    public BaseResponse getSecPriceResultsfList(@RequestBody SecPriceResultsfModel request) {
        log.info("getSecPriceResultsfList");
        return secFeign.getSecPriceResultsfList(request);
    }


    @ApiOperation(value = "ID删除巡价结果")
    @GetMapping("/deleteSecPriceResultsfBySprId")
    public BaseResponse deleteSecPriceResultsfBySprId(@RequestParam String sprId) {
        log.info("deleteSecPriceResultsfBySprId");
        return secFeign.deleteSecPriceResultsfBySprId(sprId);
    }

    @ApiOperation(value = "ID查询巡价结果")
    @GetMapping("/getSecPriceResultsfBySprId")
    public BaseResponse getSecPriceResultsfBySprId(@RequestParam String sprId) {
        log.info("getSecPriceResultsfBySprId");
        return secFeign.getSecPriceResultsfBySprId(sprId);
    }
}
