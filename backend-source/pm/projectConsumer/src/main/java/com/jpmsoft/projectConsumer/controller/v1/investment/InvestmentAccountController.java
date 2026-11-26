package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "投资控制台账", value = "投资控制台账")
@RequestMapping("/inv/v1/InvestmentAccount")
public class InvestmentAccountController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "查询项目投资情况")
    @PostMapping("/getInvestmentDate")
    public BaseResponse getInvestmentDate(@RequestHeader String token) {
        log.info("getInvestmentDate");
        return invFeign.getInvestmentDate(token);
    }
}
