package com.jpmsoft.projectConsumer.controller.v1.blxw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeCloseInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeInfoModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.BlxwInfoFeign;
import com.jpmsoft.projectConsumer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectConsumer.request.blxw.BlxwInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/BlxwInfo")
@Slf4j
public class BlxwInfoController {

    @Autowired
    private BlxwInfoFeign blxwInfoFeign;
    @PostMapping("/saveOrUpdateBlxwInfo")
    public BaseResponse saveOrUpdateBlxwInfo(@RequestBody BlxwInfoDto request) {
        log.info("createOrUpdateBlxwType");
        return blxwInfoFeign.saveOrUpdateBlxwInfo(request);
    }
    @PostMapping("/getBlxwInfoList")
    public BaseResponse getBlxwInfoList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        log.info("getBlxwInfoList");
        return blxwInfoFeign.getBlxwInfoList(request,token);
    }
    @GetMapping("/getBlxwInfoDetail")
    public BaseResponse getBlxwInfoDetail(@RequestParam String id) {
        log.info("getBlxwInfoDetail");
        return blxwInfoFeign.getBlxwInfoDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody BlxwInfoRequest request) {
        log.info("approvalData");
        return blxwInfoFeign.approvalData(request);
    }
    @PostMapping("/getBlxwInfoNoticeList")
    public BaseResponse getBlxwInfoNoticeList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("getBlxwInfoNoticeList");
        return blxwInfoFeign.getBlxwInfoNoticeList(request,token);
    }
    @GetMapping("/getBlxwInfoNoticeDetail")
    public BaseResponse getBlxwInfoNoticeDetail(@RequestParam String id) {
        log.info("getBlxwInfoNoticeDetail");
        return blxwInfoFeign.getBlxwInfoNoticeDetail(id);
    }

    @GetMapping("/getBlxwInfoPrintDetail")
    public BaseResponse getBlxwInfoPrintDetail(@RequestParam String id) {
        log.info("getBlxwInfoPrintDetail");
        return blxwInfoFeign.getBlxwInfoPrintDetail(id);
    }
    @PostMapping("/approvalNoticeData")
    public BaseResponse approvalNoticeData(@RequestBody BlxwNoticeInfoModel request) {
        log.info("approvalNoticeData");
        return blxwInfoFeign.approvalNoticeData(request);
    }

    @PostMapping("/getBlxwInfoNoticeCloseList")
    public BaseResponse getBlxwInfoNoticeCloseList(@RequestBody BlxwInfoRequest request,@RequestHeader String token) {
        log.info("getBlxwInfoNoticeCloseList");
        return blxwInfoFeign.getBlxwInfoNoticeCloseList(request);
    }
    @GetMapping("/getBlxwInfoNoticeCloseDetail")
    public BaseResponse getBlxwInfoNoticeCloseDetail(@RequestParam String id) {
        log.info("getBlxwInfoNoticeCloseDetail");
        return blxwInfoFeign.getBlxwInfoNoticeCloseDetail(id);
    }
    @PostMapping("/approvalNoticeCloseData")
    public BaseResponse approvalNoticeCloseData(@RequestBody BlxwNoticeCloseInfoModel request) {
        log.info("approvalNoticeCloseData");
        return blxwInfoFeign.approvalNoticeCloseData(request);
    }

    @PostMapping("/fresh")
    public BaseResponse fresh(@RequestBody BlxwNoticeCloseInfoModel request) {
        log.info("fresh");
        return blxwInfoFeign.fresh(request);
    }
}