package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPrequalificationReportRequest;
import com.jpmsoft.projectProducer.service.secInfo.ISecPrequalificationReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 资格预审情况报告
 */
@Slf4j
@RestController
@RequestMapping("/v1/srequalificationReport")
public class SecPrequalificationReportController {
    @Resource
    private ISecPrequalificationReportService secPrequalificationReportService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/getSecPrequalificationReportList")
    public BaseResponse getSecPrequalificationReportList(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token) {
        log.info("getSecPrequalificationReportList");
        return secPrequalificationReportService.getSecPrequalificationReportList(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSecPrequalificationReportClassInfo")
    public BaseResponse getSecPrequalificationReportClassInfo(@RequestBody SecPrequalificationReportRequest request, @RequestHeader String token) {
        log.info("getSecPrequalificationReportClassInfo");
        return secPrequalificationReportService.getSecPrequalificationReportClassInfo(request, redisUtils.getUserInfoByToken(token));
    }
}
