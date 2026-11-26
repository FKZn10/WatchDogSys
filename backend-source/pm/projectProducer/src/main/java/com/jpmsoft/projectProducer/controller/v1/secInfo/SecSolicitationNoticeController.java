package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSolicitationNoticeDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecSolicitationNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:44
 */
@Slf4j
@RestController
@RequestMapping("/v1/solicitationNotice")
public class SecSolicitationNoticeController {
    @Resource
    private ISecSolicitationNoticeService secSolicitationNoticeService;


    @TenantId
    @PostMapping("/createOrUpdateSecSolicitationNotice")
    public BaseResponse createOrUpdateSecSolicitationNotice(@RequestBody SecSolicitationNoticeDto request) {
        log.info("createOrUpdateSecSolicitationNotice");
        return secSolicitationNoticeService.createOrUpdateSecSolicitationNotice(request);
    }

    @TenantId
    @PostMapping("/getSecSolicitationNoticeList")
    public BaseResponse getSecSolicitationNoticeList(@RequestBody SecSolicitationNoticeDto request) {
        log.info("getSecSolicitationNoticeList");
        return secSolicitationNoticeService.getSecSolicitationNoticeList(request);
    }

    @GetMapping("/getSecSolicitationNoticeById")
    public BaseResponse getSecSolicitationNoticeById(@RequestParam String id) {
        log.info("getSecSolicitationNoticeById");
        return secSolicitationNoticeService.getSecSolicitationNoticeById(id);
    }

    @GetMapping("/getSecSolicitationNoticeLastApprovedBySecId")
    public BaseResponse getSecSolicitationNoticeLastApprovedBySecId(@RequestParam String secId) {
        log.info("getSecSolicitationNoticeLastApprovedBySecId");
        return secSolicitationNoticeService.getSecSolicitationNoticeLastApprovedBySecId(secId);
    }
}
