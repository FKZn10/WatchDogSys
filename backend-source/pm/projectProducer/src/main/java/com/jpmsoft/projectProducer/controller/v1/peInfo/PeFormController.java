package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peForm")
public class PeFormController {

    @Resource
    private IPeFormService peFormService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeForm")
    public BaseResponse createOrUpdatePeForm(@RequestBody PeFormRequest request) {
        log.info("createOrUpdatePeForm");
        return peFormService.createOrUpdatePeForm(request);
    }

    @TenantId
    @PostMapping("/getPeFormList")
    public BaseResponse getPeFormList(@RequestBody PeFormRequest request, @RequestHeader String token) {
        log.info("getPeFormList");
        return peFormService.getPeFormList(request, redisUtils.getUid(token));
    }

    @GetMapping("/deletePeFormByFId")
    public BaseResponse deletePeFormByFId(@RequestParam String fId) {
        log.info("deletePeFormByFId");
        return peFormService.deletePeFormByFId(fId);
    }

    @GetMapping("/getPeFormCascadeInfoByFId")
    public BaseResponse getPeFormCascadeInfoByFId(@RequestParam String fId) {
        log.info("getPeFormCascadeInfoByFId");
        return peFormService.getPeFormCascadeInfo(fId);
    }
}
