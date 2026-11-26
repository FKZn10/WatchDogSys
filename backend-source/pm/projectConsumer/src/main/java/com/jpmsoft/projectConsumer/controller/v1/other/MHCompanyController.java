package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "框架协议单位查询", value = "框架协议单位查询")
@RestController
@RequestMapping("/v1/MHCompanyController")
@Slf4j
public class MHCompanyController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 根据申请类型查询单位列表
     *
     * @param applyType 申请类型
     * @param token     用户token
     * @return 单位列表（单位名称、单位类型、单位联系人、联系人方式、累计合同暂估价、批次编码、使用合同暂估价、剩余合同暂估价）
     */
    @ApiOperation(value = "根据申请类型查询单位列表")
    @GetMapping("/getCompanyListByApplyType")
    public BaseResponse getCompanyListByApplyType(@RequestParam String applyType,
                                                  @RequestHeader("token") String token) {
        log.info("getCompanyListByApplyType, applyType: {}", applyType);
        return baseFeign.getCompanyListByApplyType(applyType, token);
    }
}
