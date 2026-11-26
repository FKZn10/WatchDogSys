package com.jpmsoft.projectConsumer.controller.v1.projectKanban;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
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
@Api(tags = "项目看板金额", value = "项目看板金额")
@RequestMapping("/v1/projectFund")
public class ProjectFundController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "查询项目看板金额")
    @GetMapping("/getProjectFund")
    public BaseResponse getProjectFund(@RequestParam String pId) {
        log.info("getProjectFund");
        return baseFeign.getProjectFund(pId);
    }

}
