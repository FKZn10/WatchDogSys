package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkorderRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaWorkorderService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/WorkOrder")
public class OaWorkorderController {
    @Resource
    private IOaWorkorderService oaWorkorderService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateOaWorkorder")
    public BaseResponse createOrUpdateOaWorkorder(@RequestBody OaWorkorderRequest request) {
        log.info("createOrUpdateOaWorkorder");
        return oaWorkorderService.createOrUpdateOaWorkorder(request);
    }

    @TenantId
    @PostMapping("/getOaWorkorderList")
    public BaseResponse getOaWorkorderList(@RequestBody OaWorkorderRequest request, @RequestHeader String token) {
        log.info("getOaWorkorderList");
        return oaWorkorderService.getOaWorkorderList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getOaWorkorderById")
    public BaseResponse getOaWorkorderById(@RequestParam String woId) {
        log.info("getOaWorkorderById");
        return oaWorkorderService.getOaWorkorderById(woId);
    }

    @PostMapping("/updateReplyInfo")
    public BaseResponse updateReplyInfo(@RequestBody OaWorkorderRequest request) {
        log.info("updateReplyInfo");
        return oaWorkorderService.updateReplyInfo(request);
    }
}
