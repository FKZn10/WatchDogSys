package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskTemplateRelationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/30 8:57
 */
@Slf4j
@RestController
@RequestMapping("/riskWarn/v1/riskTemplateRelation")
public class RiskTemplateRelationController {
    @Resource
    private IRiskTemplateRelationService riskTemplateRelationService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateTmpRelation")
    public BaseResponse createOrUpdateTmpRelation(@RequestBody RiskTemplateRelationDTO request) {
        return riskTemplateRelationService.createOrUpdate(request);
    }

    @PostMapping("/createOrUpdateTmpRelationBath")
    public BaseResponse createOrUpdateTmpRelationBath(@RequestBody List<RiskTemplateRelationDTO> request, @RequestHeader String token) {
        return riskTemplateRelationService.createOrUpdateBath(request,redisUtils.getTeId(token));
    }

    @TenantId
    @PostMapping("/getTmpRelationList")
    public BaseResponse getTmpRelationList(@RequestBody RiskTemplateRelationDTO request, @RequestHeader String token) {
        return riskTemplateRelationService.queryList(request, redisUtils.getUid(token));
    }

    @GetMapping("/deleteTmpRelationById")
    public BaseResponse deleteTmpRelationById(@RequestParam String id) {
        return riskTemplateRelationService.deleteById(id);
    }

}
