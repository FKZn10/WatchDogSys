package com.jpmsoft.projectProducer.controller.v1.blxw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeCloseInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeInfoModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;
import com.jpmsoft.projectProducer.service.blxw.IBlxwInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/BlxwInfo")
@Slf4j
public class BlxwInfoController {

    @Autowired
    private IBlxwInfoService blxwInfoService;
    @PostMapping("/saveOrUpdateBlxwInfo")
    public BaseResponse saveOrUpdateBlxwInfo(@RequestBody BlxwInfoDto request) {
        log.info("saveOrUpdateBlxwInfo");
        return blxwInfoService.saveOrUpdateBlxwInfo(request);
    }
    @PostMapping("/getBlxwInfoList")
    public BaseResponse getBlxwInfoList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("getBlxwInfoList");
        return blxwInfoService.getBlxwInfoList(request,token);
    }
    @GetMapping("/getBlxwInfoDetail")
    public BaseResponse getBlxwInfoDetail(@RequestParam String id) {
        log.info("getBlxwInfoDetail");
        return blxwInfoService.getBlxwInfoDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody BlxwInfoRequest request) {
        log.info("approvalData");
        return blxwInfoService.approvalData(request);
    }
    @PostMapping("/getBlxwInfoNoticeList")
    public BaseResponse getBlxwInfoNoticeList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("getBlxwInfoNoticeList");
        return blxwInfoService.getBlxwInfoNoticeList(request);
    }
    @GetMapping("/getBlxwInfoNoticeDetail")
    public BaseResponse getBlxwInfoNoticeDetail(@RequestParam String id) {
        log.info("getBlxwInfoNoticeDetail");
        return blxwInfoService.getBlxwInfoNoticeDetail(id);
    }

    @GetMapping("/getBlxwInfoPrintDetail")
    public BaseResponse getBlxwInfoPrintDetail(@RequestParam String id) {
        log.info("getBlxwInfoPrintDetail");
        return blxwInfoService.getBlxwInfoPrintDetail(id);
    }
    @PostMapping("/approvalNoticeData")
    public BaseResponse approvalNoticeData(@RequestBody BlxwNoticeInfoModel request) {
        log.info("approvalNoticeData");
        log.info("request:{}",JacksonUtils.toJSONString(request));
        return blxwInfoService.approvalNoticeData(request);
    }
    @PostMapping("/getBlxwInfoNoticeCloseList")
    public BaseResponse getBlxwInfoNoticeCloseList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("getBlxwInfoNoticeCloseList");
        return blxwInfoService.getBlxwInfoNoticeCloseList(request);
    }
    @GetMapping("/getBlxwInfoNoticeCloseDetail")
    public BaseResponse getBlxwInfoNoticeCloseDetail(@RequestParam String id) {
        log.info("getBlxwInfoNoticeCloseDetail");
        return blxwInfoService.getBlxwInfoNoticeCloseDetail(id);
    }
    @PostMapping("/approvalNoticeCloseData")
    public BaseResponse approvalNoticeCloseData(@RequestBody BlxwNoticeCloseInfoModel request) {
        log.info("approvalNoticeCloseData");
        log.info("request:{}",JacksonUtils.toJSONString(request));
        return blxwInfoService.approvalNoticeCloseData(request);
    }


    @PostMapping("/fresh")
    public BaseResponse fresh(@RequestBody BlxwNoticeCloseInfoModel request) {
        log.info("fresh");
        return blxwInfoService.fresh(request);
    }
}