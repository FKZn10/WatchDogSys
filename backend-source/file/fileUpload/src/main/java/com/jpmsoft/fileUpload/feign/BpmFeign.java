package com.jpmsoft.fileUpload.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bpm-server", contextId = "bpm-server")
public interface BpmFeign {
    @PostMapping("/v1/flow/getApproveList")
    BaseResponse getApproveList(@RequestBody FilerecordRequest request, @RequestHeader(value = "token", required = false) String token, @RequestParam(value = "year", required = false) String year);
}