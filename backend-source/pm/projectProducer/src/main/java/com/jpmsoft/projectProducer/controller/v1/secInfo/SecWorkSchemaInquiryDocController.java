package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaDTO;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaInquiryDocDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecWorkSchemaInquiryDocService;
import com.jpmsoft.projectProducer.service.secInfo.ISecWorkSchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/workSchemaInquiryDoc")
public class SecWorkSchemaInquiryDocController {

    @Resource
    private ISecWorkSchemaInquiryDocService secWorkSchemaInquiryDocService;


    @TenantId
    @PostMapping("/createOrUpdateSecWorkSchemaInquiryDoc")
    public BaseResponse createOrUpdateSecWorkSchemaInquiryDoc(@RequestBody SecWorkSchemaInquiryDocDTO request) {
        log.info("createOrUpdateSecWorkSchemaInquiryDoc");
        return secWorkSchemaInquiryDocService.createOrUpdateSecWorkSchemaInquiryDoc(request);
    }

    @TenantId
    @PostMapping("/getSecWorkSchemaInquiryDocList")
    public BaseResponse getSecWorkSchemaInquiryDocList(@RequestBody SecWorkSchemaInquiryDocDTO request) {
        log.info("getSecWorkSchemaInquiryDocList");
        return secWorkSchemaInquiryDocService.getSecWorkSchemaInquiryDocList(request);
    }

    @GetMapping("/getSecWorkSchemaInquiryDocByWsidId")
    public BaseResponse getSecWorkSchemaInquiryDocByWsidId(@RequestParam String wsidId) {
        log.info("getSecWorkSchemaInquiryDocByWsidId");
        return secWorkSchemaInquiryDocService.getSecWorkSchemaInquiryDocByWsidId(wsidId);
    }

    @GetMapping("/getSecWorkSchemaInquiryDocBySecId")
    public BaseResponse getSecWorkSchemaInquiryDocBySecId(@RequestParam String secId) {
        log.info("getSecWorkSchemaInquiryDocBySecId");
        return secWorkSchemaInquiryDocService.getSecWorkSchemaInquiryDocBySecId(secId);
    }

}
