package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "风险点文档模板关联配置", value = "风险点文档模板关联配置")
@RequestMapping("/riskWarn/v1/riskTemplateRelation")
public class RiskTemplateRelationController {
    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增修改风险点文档模板关联配置")
    @PostMapping("/createOrUpdateTmpRelation")
    public BaseResponse createOrUpdateTmpRelation(@RequestBody RiskTemplateRelationDTO request) {
        return baseFeign.createOrUpdateTmpRelation(request);
    }

    @ApiOperation(value = "批量新增修改风险点文档模板关联配置（根据tid删除旧数据重新批量插入新数据）")
    @PostMapping("/createOrUpdateTmpRelationBath")
    public BaseResponse createOrUpdateTmpRelationBath(@RequestBody List<RiskTemplateRelationDTO> request) {
        return baseFeign.createOrUpdateTmpRelationBath(request);
    }


    @ApiOperation(value = "分页查询风险点文档模板关联配置")
    @PostMapping("/getTmpRelationList")
    public BaseResponse getTmpRelationList(@RequestBody RiskTemplateRelationDTO request, @RequestHeader String token) {
        return baseFeign.getTmpRelationList(request, token);
    }

    @ApiOperation(value = "id删除")
    @GetMapping("/deleteTmpRelationById")
    public BaseResponse deleteTmpRelationById(@RequestParam String id) {
        return baseFeign.deleteTmpRelationById(id);
    }
}
