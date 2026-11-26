package com.jpmsoft.userProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.BlSmsSendRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jpm-dock", contextId = "dock")
public interface DockFeign {
    /**
     * 北林短信平台发送短信
     *
     * @param sms
     * @return
     */
    @PostMapping("/blSms/sendBlSms")
    BaseResponse sendBlSms(@RequestBody BlSmsSendRequest sms);

    /**
     * 销毁CXF客户端资源
     *
     * @return
     */
    @GetMapping("/blSms/destroyResources")
    BaseResponse destroyResources();
}
