package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportClassInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportRequest;
import com.jpmsoft.projectProducer.service.secInfo.ISecEvalReportService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/evalReport")
public class SecEvalReportController {

    @Resource
    private ISecEvalReportService secEvalReportService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateEvalReport")
    public BaseResponse createOrUpdateEvalReport(@RequestBody SecEvalReportRequest request) {
        log.info("createOrUpdateEvalReport");
        return secEvalReportService.createOrUpdateEvalReport(request);
    }

    @GetMapping("/cancelSecEvalReportByErId")
    public BaseResponse cancelSecEvalReportByErId(@RequestParam String erId) {
        log.info("cancelSecEvalReportByErId");
        return secEvalReportService.cancelSecEvalReportByErId(erId);
    }

    @GetMapping("/getSecEvalReportByErId")
    public BaseResponse getSecEvalReportByErId(@RequestParam String erId) {
        log.info("getSecEvalReportByErId");
        return secEvalReportService.getSecEvalReportByErId(erId);
    }

    @GetMapping("/getSecEvalReportBySecId")
    public BaseResponse getSecEvalReportBySecId(@RequestParam String secId) {
        log.info("getSecEvalReportBySrId");
        return secEvalReportService.getSecEvalReportBySecId(secId);
    }

    @PostMapping("/getSecEvalReportList")
    public BaseResponse getSecEvalReportList(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token) {
        log.info("getSecEvalReportList");
        return secEvalReportService.getSecEvalReportList(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSecEvalReportListClassInfo")
    public BaseResponse getSecEvalReportListClassInfo(@RequestBody SecEvalReportClassInfoRequest request, @RequestHeader String token) {
        log.info("getSecEvalReportListClassInfo");
        return secEvalReportService.getSecEvalReportListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSecEvalReportNoAuthList")
    public BaseResponse getSecEvalReportNoAuthList(@RequestBody SecEvalReportClassInfoRequest request) {
        log.info("getSecEvalReportNoAuthList");
        return secEvalReportService.getSecEvalReportNoAuthList(request);
    }
}
