package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 光明招标计划
 *
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 13:57
 */
@Slf4j
@RestController
@Api(tags = "招标计划光明", value = "招标计划光明")
@RequestMapping("/v1/biddingPlanGm")
public class SecBiddingPlanGmController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "标段ID查询最新一条审批通过的金额")
    @GetMapping("/selectLastApprovedMoney")
    public BaseResponse selectLastApprovedMoney(@RequestParam String secId) {
        return secFeign.selectLastApprovedMoney(secId);
    }
}
