package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaProblemService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/Problem")
public class OaProblemController {
    @Resource
    private IOaProblemService oaProblemService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateOaProblem")
    public BaseResponse createOrUpdateOaProblem(@RequestBody OaProblemModel request) {
        log.info("createOrUpdateOaProblem");
        return oaProblemService.createOrUpdateOaProblem(request);
    }

    @TenantId
    @PostMapping("/getOaProblemList")
    public BaseResponse getOaProblemList(@RequestBody OaProblemModel request, @RequestHeader String token) {
        log.info("getOaProblemList");
        return oaProblemService.getOaProblemList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getOaProblemById")
    public BaseResponse getOaProblemById(@RequestParam String pmId) {
        log.info("getOaProblemById");
        return oaProblemService.getOaProblemById(pmId);
    }
}
