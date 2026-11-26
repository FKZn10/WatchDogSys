package com.jpmsoft.fileUpload.feign;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "zhbg-server", contextId = "zhbg-server")
public interface OaFeign {
    @GetMapping("/v1/OA/Notice/getOaNoticeByNid")
    BaseResponse getOaNoticeByNid(@RequestParam String nId);
}
