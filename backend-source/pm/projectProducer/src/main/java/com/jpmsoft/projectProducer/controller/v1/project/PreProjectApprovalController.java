package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectApprovalModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProjectApprovalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/preProjectApproval")
public class PreProjectApprovalController {

    @Autowired
    private IPreProjectApprovalService preProjectApprovalService;
    @Autowired
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreProjectApproval")
    public BaseResponse createOrUpdatePreProjectApproval(@RequestBody PreProjectApprovalModel request) {
        log.info("createOrUpdatePreProjectApproval");
        return preProjectApprovalService.createOrUpdatePreProjectApproval(request);
    }
}
