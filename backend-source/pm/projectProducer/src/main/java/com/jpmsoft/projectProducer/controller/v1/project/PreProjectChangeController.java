package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectChangeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProjectChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/projectChange")
public class PreProjectChangeController {

    @Resource
    private IPreProjectChangeService preProjectChangeService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreProjectChange")
    public BaseResponse createOrUpdatePreProjectChange(@RequestBody PreProjectChangeModel request, @RequestHeader String token) {
        log.info("createOrUpdatePreProjectChange");
        return preProjectChangeService.createOrUpdatePreProjectChange(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPreProjectChangeList")
    public BaseResponse getPreProjectChangeList(@RequestBody PreProjectChangeModel request, @RequestHeader String token) {
        log.info("getPreProjectChangeList");
        return preProjectChangeService.getPreProjectChangeList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getPreProjectChangeById")
    public BaseResponse getPreProjectChangeById(@RequestParam String pcId) {
        log.info("getPreProjectChangeById");
        return preProjectChangeService.getPreProjectChangeById(pcId);
    }

    @GetMapping("/approvalSyncProInfo")
    public BaseResponse approvalSyncProInfo(@RequestParam String pcId, @RequestHeader String token) {
        log.info("approvalSyncProInfo");
        return preProjectChangeService.approvalSyncProInfo(pcId, redisUtils.getUid(token));
    }
}
