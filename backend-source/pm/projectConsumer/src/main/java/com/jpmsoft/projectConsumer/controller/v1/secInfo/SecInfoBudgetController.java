package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "工程概算", value = "工程概算")
@RequestMapping("/v1/secInfoBudget")
public class SecInfoBudgetController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "项目费用ID查询工程概算")
    @GetMapping("/getSecInfoBudgetList")
    public BaseResponse getSecInfoBudgetList(@RequestParam String sibCost) {
        log.info("getSecInfoBudgetList");
        return secFeign.getSecInfoBudgetList(sibCost);
    }
}
