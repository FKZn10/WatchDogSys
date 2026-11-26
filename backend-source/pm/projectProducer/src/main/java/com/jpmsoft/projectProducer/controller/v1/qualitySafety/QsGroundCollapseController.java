package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsGroundCollapseService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/qsGroundCollapse")
public class QsGroundCollapseController {

    @Resource
    private IQsGroundCollapseService qsGroundCollapseService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateQsGroundCollapse")
    public BaseResponse createOrUpdateQsGroundCollapse(@RequestBody QsGroundCollapseModel request) {
        log.info("createOrUpdateQsGroundCollapse");
        return qsGroundCollapseService.createOrUpdateQsGroundCollapse(request);
    }

    @TenantId
    @PostMapping("/getQsGroundCollapseList")
    public BaseResponse getQsGroundCollapseList(@RequestBody QsGroundCollapseModel request, @RequestHeader String token) {
        log.info("getQsGroundCollapseList");
        return qsGroundCollapseService.getQsGroundCollapseList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getQsGroundCollapseById")
    public BaseResponse getQsGroundCollapseById(@RequestParam String gcId) {
        log.info("getQsGroundCollapseById");
        return qsGroundCollapseService.getQsGroundCollapseById(gcId);
    }
}
