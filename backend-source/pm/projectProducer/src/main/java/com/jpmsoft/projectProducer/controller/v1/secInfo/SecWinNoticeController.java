package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecWinNoticeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/winNotice")
public class SecWinNoticeController {
    @Resource
    private ISecWinNoticeService secWinNoticeService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateWinNotice")
    public BaseResponse createOrUpdateWinNotice(@RequestBody SecWinNoticeRequest request) {
        log.info("createOrUpdateWinNotice");
        return secWinNoticeService.createOrUpdateWinNotice(request);
    }

    @TenantId
    @PostMapping("/getWinNoticeList")
    public BaseResponse getWinNoticeList(@RequestBody SecWinNoticeRequest request) {
        log.info("getWinNoticeList");
        return secWinNoticeService.getWinNoticeList(request);
    }

    @TenantId
    @PostMapping("/getWinNoticeListOne2Many")
    public BaseResponse getWinNoticeListOne2Many(@RequestBody SecWinNoticeRequest request) {
        log.info("getWinNoticeListOne2Many");
        return secWinNoticeService.getWinNoticeListOne2Many(request);
    }

    @GetMapping("/getWinNoticeByWnId")
    public BaseResponse getWinNoticeByWnId(@RequestParam String wnId) {
        log.info("getWinNoticeByWnId");
        return secWinNoticeService.getWinNoticeByWnId(wnId);
    }

    @GetMapping("/getLatestApproveWinNotice")
    public BaseResponse getLatestApproveWinNotice(@RequestParam String secId) {
        log.info("getLatestApproveWinNotice");
        return secWinNoticeService.getLatestApproveWinNotice(secId);
    }

    @GetMapping("/deleteWinNoticeByWnId")
    public BaseResponse deleteWinNoticeByWnId(@RequestParam String wnId) {
        log.info("deleteWinNoticeByWnId");
        return secWinNoticeService.deleteWinNoticeByWnId(wnId);
    }

    @GetMapping("/getWinNoticeCompleteBySecId")
    public BaseResponse getWinNoticeCompleteBySecId(@RequestParam String secId) {
        log.info("getWinNoticeCompleteBySecId");
        return secWinNoticeService.getWinNoticeCompleteBySecId(secId);
    }

    @PostMapping("/getWinNoticeListClass")
    public BaseResponse getWinNoticeListClass(@RequestBody SecWinNoticeRequest request, @RequestHeader String token) {
        log.info("getWinNoticeListClass");
        return secWinNoticeService.getWinNoticeListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getWinNoticeListClassInfo")
    public BaseResponse getWinNoticeListClassInfo(@RequestBody SecWinNoticeRequest request, @RequestHeader String token) {
        log.info("getWinNoticeListClassInfo");
        return secWinNoticeService.getWinNoticeListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getWinningBidInformation")
    public BaseResponse getWinningBidInformation(@RequestParam String secId) {
        log.info("getWinningBidInformation");
        return secWinNoticeService.getWinningBidInformation(secId);
    }
}
