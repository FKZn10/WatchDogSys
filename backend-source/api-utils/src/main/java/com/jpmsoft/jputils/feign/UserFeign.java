package com.jpmsoft.jputils.feign;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 9:36
 */
@FeignClient(name = "user-producer", contextId = "userp")
public interface UserFeign {
    /**
     * code获取配置信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v1/SystemManage/GetConfigValueByCode")
    BaseResponse GetConfigValueByCode(@RequestParam String code);
}
