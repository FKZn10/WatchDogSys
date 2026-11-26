package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkReportRequest;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaWorkReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/OA/WorkReport")
public class OaWorkReportController {
    @Resource
    private IOaWorkReportService oaWorkReportService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateOaWorkReport")
    public BaseResponse createOrUpdateOaWorkReport(@RequestBody OaWorkReportRequest request) {
        log.info("createOrUpdateOaWorkReport");
        return oaWorkReportService.createOrUpdateOaWorkReport(request);
    }

    @TenantId
    @PostMapping("/getOaWorkReportList")
    public BaseResponse getOaWorkReportList(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportList");
        return oaWorkReportService.getOaWorkReportList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getOaWorkReportListOnlyDep")
    public BaseResponse getOaWorkReportListOnlyDep(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportListOnlyDep");
        return oaWorkReportService.getOaWorkReportListOnlyDep(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getOaWorkReportSumList")
    public BaseResponse getOaWorkReportSumList(@RequestBody OaWorkReportRequest request, @RequestHeader String token) {
        log.info("getOaWorkReportSumList");
        return oaWorkReportService.getOaWorkReportSumList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getOaWorkReportListNoAuth")
    public BaseResponse getOaWorkReportListNoAuth(@RequestBody OaWorkReportRequest request) {
        log.info("getOaWorkReportListNoAuth");
        return oaWorkReportService.getOaWorkReportListNoAuth(request);
    }


    @GetMapping("/getOaWorkReportById")
    public BaseResponse getOaWorkReportById(@RequestParam String wrId) {
        log.info("getOaWorkReportById");
        return oaWorkReportService.getOaWorkReportById(wrId);
    }

    /*********************/
    @PostMapping("/getOaWorkReportStandingbook")
    public BaseResponse getOaWorkReportStandingbook(@RequestBody RequestBean requestBean, @RequestHeader String token) {
        log.info("getOaWorkReportStandingbook");
        return oaWorkReportService.getOaWorkReportStandingbook(requestBean, redisUtils.getUid(token));
    }

    @PostMapping("/getOaWorkReportNeverSub")
    public BaseResponse getOaWorkReportNeverSub(@RequestBody RequestBean requestBean) {
        log.info("getOaWorkReportNeverSub");
        return oaWorkReportService.getOaWorkReportNeverSub(requestBean);
    }

    @PostMapping("/countOaWorkReport")
    public BaseResponse countOaWorkReport(@RequestBody List<String> bizIds, @RequestParam("type") String type) {
        log.info("countOaWorkReport");
        return oaWorkReportService.countOaWorkReport(bizIds, type);
    }
}
