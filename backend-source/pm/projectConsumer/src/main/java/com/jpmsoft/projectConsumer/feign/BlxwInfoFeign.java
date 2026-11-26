package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeCloseInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwTypeModel;
import com.jpmsoft.projectConsumer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectConsumer.request.blxw.BlxwInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "project-producer", contextId = "blxwinfo")
public interface BlxwInfoFeign {

    @PostMapping("/v1/BlxwType/createOrUpdateBlxwType")
    BaseResponse createOrUpdateBlxwType(@RequestBody BlxwTypeModel request);
    @GetMapping("/v1/BlxwType/deleteBlxwTypeById")
    BaseResponse deleteBlxwTypeById(@RequestParam String id);
    @PostMapping("/v1/BlxwType/getBlxwTypeTreeList")
    BaseResponse getBlxwTypeTreeList(@RequestBody BlxwTypeModel request,@RequestHeader String token);
    @GetMapping("/v1/BlxwType/upOrDownBlxwType")
    BaseResponse upOrDownBlxwType(@RequestParam String id,@RequestParam String action,@RequestParam String token);
    @GetMapping("/v1/BlxwType/getBlxwTypeByParentId")
    BaseResponse getBlxwTypeByParentId(@RequestParam String parentId);

    @PostMapping("/v1/BlxwInfo/saveOrUpdateBlxwInfo")
    BaseResponse saveOrUpdateBlxwInfo(@RequestBody BlxwInfoDto request);
    @PostMapping("/v1/BlxwInfo/getBlxwInfoList")
    BaseResponse getBlxwInfoList(@RequestBody BlxwInfoRequest request,@RequestHeader String token);
    @GetMapping("/v1/BlxwInfo/getBlxwInfoDetail")
    BaseResponse getBlxwInfoDetail(@RequestParam String id);
    @PostMapping("/v1/BlxwInfo/approvalData")
    BaseResponse approvalData(@RequestBody BlxwInfoRequest request);

    @GetMapping("/v1/BlxwInfo/getBlxwInfoNoticeDetail")
    BaseResponse getBlxwInfoNoticeDetail(@RequestParam String id);
    @PostMapping("/v1/BlxwInfo/getBlxwInfoNoticeList")
    BaseResponse getBlxwInfoNoticeList(@RequestBody BlxwInfoRequest request,@RequestHeader String token);
    @GetMapping("/v1/BlxwInfo/getBlxwInfoPrintDetail")
    BaseResponse getBlxwInfoPrintDetail(@RequestParam String id);
    @PostMapping("/v1/BlxwInfo/approvalNoticeData")
    BaseResponse approvalNoticeData(@RequestBody BlxwNoticeInfoModel request);
    @PostMapping("/v1/BlxwInfo/getBlxwInfoNoticeCloseList")
    BaseResponse getBlxwInfoNoticeCloseList(@RequestBody BlxwInfoRequest request);
    @GetMapping("/v1/BlxwInfo/getBlxwInfoNoticeCloseDetail")
    BaseResponse getBlxwInfoNoticeCloseDetail(@RequestParam String id);
    @PostMapping("/v1/BlxwInfo/approvalNoticeCloseData")
    BaseResponse approvalNoticeCloseData(@RequestBody BlxwNoticeCloseInfoModel request);
    @PostMapping("/v1/BlxwInfo/fresh")
    BaseResponse fresh(@RequestBody BlxwNoticeCloseInfoModel request);
}