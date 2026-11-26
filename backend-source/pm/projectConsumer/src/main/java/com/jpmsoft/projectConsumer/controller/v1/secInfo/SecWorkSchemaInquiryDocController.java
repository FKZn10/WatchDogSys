package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaInquiryDocDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/workSchemaInquiryDoc")
public class SecWorkSchemaInquiryDocController {

    @Resource
    private SecFeign secFeign;

    @PostMapping("/createOrUpdateSecWorkSchemaInquiryDoc")
    public BaseResponse createOrUpdateSecWorkSchemaInquiryDoc(@RequestBody SecWorkSchemaInquiryDocDTO request) {
        log.info("createOrUpdateSecWorkSchemaInquiryDoc");
        return secFeign.createOrUpdateSecWorkSchemaInquiryDoc(request);
    }

    @PostMapping("/getSecWorkSchemaInquiryDocList")
    public BaseResponse getSecWorkSchemaInquiryDocList(@RequestBody SecWorkSchemaInquiryDocDTO request) {
        log.info("getSecWorkSchemaInquiryDocList");
        return secFeign.getSecWorkSchemaInquiryDocList(request);
    }

    @GetMapping("/getSecWorkSchemaInquiryDocByWsidId")
    public BaseResponse getSecWorkSchemaInquiryDocByWsidId(@RequestParam String wsidId) {
        log.info("getSecWorkSchemaInquiryDocByWsidId");
        return secFeign.getSecWorkSchemaInquiryDocByWsidId(wsidId);
    }

    @GetMapping("/getSecWorkSchemaInquiryDocBySecId")
    public BaseResponse getSecWorkSchemaInquiryDocBySecId(@RequestParam String secId) {
        log.info("getSecWorkSchemaInquiryDocBySecId");
        return secFeign.getSecWorkSchemaInquiryDocBySecId(secId);
    }

}
