package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemDtlModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaProblemDtlService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/ProblemDtl")
public class OaProblemDtlController {
    @Resource
    private IOaProblemDtlService oaProblemDtlService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getOaProblemDtlList")
    public BaseResponse getOaProblemDtlList(@RequestBody OaProblemDtlModel request, @RequestHeader String token) {
        log.info("getOaProblemDtlList");
        return oaProblemDtlService.getOaProblemDtlList(request, redisUtils.getUid(token));
    }
}
