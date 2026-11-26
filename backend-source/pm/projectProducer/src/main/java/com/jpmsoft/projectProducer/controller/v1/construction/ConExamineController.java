package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConExamineService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/conExamine")
public class ConExamineController {

    @Resource
    private IConExamineService conExamineService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateConExamine")
    public BaseResponse createOrUpdateConExamine(@RequestBody ConExamineModel request) {
        log.info("createOrUpdateConExamine");
        return conExamineService.createOrUpdateConExamine(request);
    }

    @TenantId
    @PostMapping("/getConExamineList")
    public BaseResponse getConExamineList(@RequestBody ConExamineModel request, @RequestHeader String token) {
        log.info("getConExamineList");
        return conExamineService.getConExamineList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConExamineById")
    public BaseResponse getConExamineById(@RequestParam String exId) {
        log.info("getConExamineById");
        return conExamineService.getConExamineById(exId);
    }
}
