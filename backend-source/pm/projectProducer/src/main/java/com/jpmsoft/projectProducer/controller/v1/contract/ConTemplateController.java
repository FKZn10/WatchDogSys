package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTemplateDto;
import com.jpmsoft.projectProducer.service.contract.IConTemplateService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/8 10:34
 */
@Slf4j
@RestController
@RequestMapping("/v1/conTemplate")
public class ConTemplateController {

    @Resource
    private IConTemplateService conTemplateService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createConTemplate")
    public BaseResponse createConTemplate(@RequestBody ConTemplateDto request) {
        log.info("createConTemplate");
        return conTemplateService.createConTemplate(request);
    }

    @PostMapping("/updateConTemplate")
    public BaseResponse updateConTemplate(@RequestBody ConTemplateDto request) {
        log.info("updateConTemplate");
        return conTemplateService.updateConTemplate(request);
    }

    @TenantId
    @PostMapping("/getConTemplateList")
    public BaseResponse getConTemplateList(@RequestBody ConTemplateDto request, @RequestHeader String token) {
        log.info("getConTemplateList");
        return conTemplateService.getConTemplateList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConTemplateById")
    public BaseResponse getConTemplateById(@RequestParam String id) {
        log.info("getConTemplateById");
        return conTemplateService.getConTemplateById(id);
    }

    @PostMapping("/updateConTemplateHistory")
    public BaseResponse updateConTemplateHistory(@RequestBody ConTemplateDto request) {
        log.info("updateConTemplateHistory");
        return conTemplateService.updateConTemplateHistory(request);
    }
}
