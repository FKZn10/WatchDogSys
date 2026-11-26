package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPrequalificationReportRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 资格预审情况报告
 */
@Slf4j
@RestController
@Api(tags = "资格预审情况报告", value = "资格预审情况报告")
@RequestMapping("/v1/srequalificationReport")
public class SecPrequalificationReportController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation("分页查询资格预审情况报告")
    @PostMapping("/getSecPrequalificationReportList")
    public BaseResponse getSecPrequalificationReportList(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token) {
        log.info("getSecPrequalificationReportList");
        return secFeign.getSecPrequalificationReportList(request, token);
    }

    @ApiOperation("获取资格预审情况报告多条件查询信息")
    @PostMapping("/getSecPrequalificationReportClassInfo")
    public BaseResponse getSecPrequalificationReportClassInfo(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token) {
        log.info("getSecPrequalificationReportClassInfo");
        return secFeign.getSecPrequalificationReportClassInfo(request, token);
    }
}
