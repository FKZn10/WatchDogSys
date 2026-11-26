package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.ThirdUserRequest;
import com.jpmsoft.projectProducer.pojo.request.thirdDock.WorkFlowRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "jpm-dock", contextId = "jpm-dock")
public interface DockFeign {

    /**
     * 创建云上建工流程
     * @param request
     * @return
     */
    @PostMapping("/sso/workFlow/createWorkFLow")
    CommonResponse<String> createWorkFLow(@RequestBody WorkFlowRequest request);

    /**
     * 驳回后重新发起流程
     * @param request
     * @return
     */
    @PostMapping("/sso/workFlow/resubmitFLow")
    CommonResponse<String> resubmitFLow(@RequestBody WorkFlowRequest request);

    /**
     * 获取云上建工用户信息
     * @param userId
     * @return
     */
    @GetMapping("/sso/workFlow/getThirdUser")
    CommonResponse<ThirdUserRequest> getThirdUser(@RequestParam("userId") String userId);
}
