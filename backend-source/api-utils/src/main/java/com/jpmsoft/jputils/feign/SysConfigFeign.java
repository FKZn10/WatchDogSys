package com.jpmsoft.jputils.feign;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基础配置
 */
@FeignClient(name = "user-producer", contextId = "sysConfig")
public interface SysConfigFeign {

    /**
     * 批量code获取配置信息
     * @param codes
     * @return
     */
    @GetMapping("/v1/SystemManage/GetConfigValuesByCodes")
    BaseResponse GetConfigValuesByCodes(@RequestParam String codes);

}
