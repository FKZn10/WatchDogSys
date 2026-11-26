package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeReadRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaNoticeReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/NoticeRead")
public class OaNoticeReadController {

    @Resource
    private IOaNoticeReadService oaNoticeReadService;

    @TenantId
    @PostMapping("/createOaNoticeRead")
    public BaseResponse createOaNoticeRead(@RequestBody OaNoticeReadRequest request) {
        log.info("createOaNoticeRead");
        return oaNoticeReadService.createOaNoticeRead(request);
    }

    @PostMapping("/updateOaNoticeRead")
    public BaseResponse updateOaNoticeRead(@RequestBody OaNoticeReadRequest request) {
        log.info("updateOaNoticeRead");
        return oaNoticeReadService.updateOaNoticeRead(request);
    }

    @GetMapping("/deleteOaNoticeReadByNrId")
    public BaseResponse deleteOaNoticeReadByNrId(@RequestParam String nrId) {
        log.info("deleteOaNoticeReadByNrId");
        return oaNoticeReadService.deleteOaNoticeReadByNrId(nrId);
    }

    @GetMapping("/getOaNoticeReadListByNid")
    public BaseResponse getOaNoticeReadListByNid(@RequestParam("nId") String nId, @RequestParam(value = "name",required = false)String name ,@RequestParam(value = "isRead",required = false)String isRead) {
        log.info("getOaNoticeReadListByNid");
        return oaNoticeReadService.getOaNoticeReadListByNid(nId,name,isRead);
    }

    @TenantId
    @PostMapping("/getOaNoticeReadList")
    public BaseResponse getOaNoticeReadList(@RequestBody OaNoticeReadRequest request) {
        log.info("getOaNoticeReadList");
        return oaNoticeReadService.getOaNoticeReadList(request);
    }
}
