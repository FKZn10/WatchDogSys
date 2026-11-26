package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.dto.AiRuleConfigurationDTO;
import com.jpmsoft.userProducer.pojo.entity.AiRuleConfiguration;
import com.jpmsoft.userProducer.service.IAiRuleConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "提示词管理/规则管理", value = "提示词管理/规则管理")
@RequestMapping("/v1/aiRule")
public class AiRuleConfigurationController {

    @Resource
    private IAiRuleConfigurationService AiRuleConfigurationService;

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "/getById")
    public BaseResponse getById(@RequestParam String id) {
        return BaseResponse.ok(AiRuleConfigurationService.getById(id));
    }

    @ApiOperation(value = "ID删除")
    @GetMapping(value = "/removeById")
    public BaseResponse removeById(@RequestParam String id) {
        AiRuleConfigurationService.removeById(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "规则xml生成")
    @GetMapping(value = "/generationRulesXml")
    public BaseResponse generationRulesXml(@RequestParam String id) {
        return BaseResponse.ok(AiRuleConfigurationService.generationRulesXml(id));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/removeBatchByIds")
    public BaseResponse removeBatchByIds(@RequestBody List<String> ids) {
        AiRuleConfigurationService.removeBatchByIds(ids);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AiRuleConfiguration code) {
        if (ValidateUtil.isEmpty(code.getId())) code.setId(GenerateUUID.getUUID());
        AiRuleConfigurationService.saveOrUpdate(code);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getAiRuleConfigurationPage")
    public Object getAiRuleConfigurationPage(@RequestBody AiRuleConfigurationDTO code) {
        LambdaQueryWrapper<AiRuleConfiguration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(code.getType()), AiRuleConfiguration::getType, code.getType());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getStatus()), AiRuleConfiguration::getStatus, code.getStatus());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getCreateUid()), AiRuleConfiguration::getCreateUid, code.getCreateUid());
        wrapper.like(ValidateUtil.isNotEmpty(code.getCreateUname()), AiRuleConfiguration::getCreateUname, code.getCreateUname());
        wrapper.like(ValidateUtil.isNotEmpty(code.getPrompt()), AiRuleConfiguration::getPrompt, code.getPrompt());
        IPage<AiRuleConfiguration> iPage = AiRuleConfigurationService.page(new Page<>(code.getPage(), code.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @ApiOperation(value = "提示词管理/规则管理导入")
    @PostMapping("/importAiRuleConfiguration")
    public BaseResponse importAiRuleConfiguration(@RequestParam MultipartFile request) {
        log.info("importAiRuleConfiguration");
        return AiRuleConfigurationService.importAiRuleConfiguration(request);
    }

    @ApiOperation(value = "提示词管理/规则管理导出")
    @PostMapping("/exportAiRuleConfiguration")
    public BaseResponse exportAiRuleConfiguration(@RequestBody List<String> request) {
        log.info("exportAiRuleConfiguration");
        return AiRuleConfigurationService.exportAiRuleConfiguration(request);
    }

}
