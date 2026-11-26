package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecNoticeRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/notice")
public class SecNoticeController {
    @Resource
    private ISecNoticeService secNoticeService;

    @TenantId
    @PostMapping("/createOrUpdateSecNotice")
    public BaseResponse createOrUpdateSecNotice(@RequestBody SecNoticeRequest request) {
        log.info("createOrUpdateSecNotice");
        return secNoticeService.createOrUpdateSecNotice(request);
    }

    @GetMapping("/deleteSecNoticeByNid")
    public BaseResponse deleteSecNoticeByNid(@RequestParam String nId) {
        log.info("deleteSecNoticeByNid");
        return secNoticeService.deleteSecNoticeByNid(nId);
    }

    @GetMapping("/getSecNoticeByNid")
    public BaseResponse getSecNoticeByNid(@RequestParam String nId) {
        log.info("getSecNoticeByNid");
        return secNoticeService.getSecNoticeByNid(nId);
    }


    @GetMapping("/getSecNoticeLastApprovedBySecId")
    public BaseResponse getSecNoticeLastApprovedBySecId(@RequestParam String secId) {
        log.info("getSecNoticeLastApprovedBySecId");
        return secNoticeService.getSecNoticeLastApprovedBySecId(secId);
    }


    @TenantId
    @PostMapping("/getSecNoticeList")
    public BaseResponse getSecNoticeList(@RequestBody SecNoticeRequest request) {
        log.info("getSecNoticeList");
        return secNoticeService.getSecNoticeList(request);
    }
}
