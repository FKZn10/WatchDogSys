package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 司库接口
 *
 * @author tianhelin
 * @date 2025/12/2
 */
@FeignClient(name = "jpm-dock", contextId = "SKApi")
public interface SKApiFeign {
    /**
     * T0004
     *
     * @param no
     * @return
     */
    @GetMapping("/v1/skapi/selectBillsPayOrderNo")
    BaseResponse selectBillsPayOrderNo(@RequestParam String no);
}
