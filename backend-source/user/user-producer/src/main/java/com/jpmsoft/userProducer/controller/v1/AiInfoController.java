package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.AiPromptGenerateDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.dto.AIDockConfigInfoDTO;
import com.jpmsoft.userProducer.pojo.dto.AIInfoDTO;
import com.jpmsoft.userProducer.pojo.entity.AIInfo;
import com.jpmsoft.userProducer.service.IAIInfoService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "AI配置信息", value = "AI配置信息")
@RequestMapping("/v1/aiInfo")
public class AiInfoController {

    @Resource
    private IAIInfoService AIInfoService;

    @ApiOperation(value = "AI提示词XML生成")
    @GetMapping(value = "/getAiPromptXml")
    public BaseResponse getAiPromptXml(@RequestParam String type) {
        return BaseResponse.ok(AIInfoService.getAiPromptXml(type));
    }

    @ApiOperation(value = "AI提示词XML生成")
    @PostMapping(value = "/getAiPromptXmlByBody")
    public BaseResponse getAiPromptXmlByBody(@RequestBody AiPromptGenerateDTO apg) {
        return BaseResponse.ok(AIInfoService.getAiPromptXml(apg));
    }

    @ApiOperation(value = "获取AI配置信息（API密钥、接口地址、模型名称等）")
    @GetMapping(value = "/getAIInfo")
    public BaseResponse getAIInfo(@RequestParam String type) {
        return BaseResponse.ok(AIInfoService.getAIInfo(type));
    }


    @ApiOperation(value = "ID查询")
    @GetMapping(value = "/getById")
    public BaseResponse getById(@RequestParam String id) {
        return BaseResponse.ok(AIInfoService.getById(id));
    }

    @ApiOperation(value = "ID删除")
    @GetMapping(value = "/removeById")
    public BaseResponse removeById(@RequestParam String id) {
        AIInfoService.removeById(id);
        return BaseResponse.ok();
    }


    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/removeBatchByIds")
    public BaseResponse removeBatchByIds(@RequestBody List<String> ids) {
        AIInfoService.removeBatchByIds(ids);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AIInfo code) {
        if (ValidateUtil.isEmpty(code.getId())) code.setId(GenerateUUID.getUUID());
        AIInfoService.saveOrUpdate(code);
        return BaseResponse.ok();
    }


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getAIInfoPage")
    public Object getAIInfoPage(@RequestBody AIInfoDTO code) {
        LambdaQueryWrapper<AIInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(code.getAiType()), AIInfo::getAiType, code.getAiType());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getStatus()), AIInfo::getStatus, code.getStatus());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getCreateUid()), AIInfo::getCreateUid, code.getCreateUid());
        wrapper.like(ValidateUtil.isNotEmpty(code.getAiModel()), AIInfo::getAiModel, code.getAiModel());
        wrapper.like(ValidateUtil.isNotEmpty(code.getCreateUname()), AIInfo::getCreateUname, code.getCreateUname());
        IPage<AIInfo> iPage = AIInfoService.page(new Page<>(code.getPage(), code.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @ApiOperation(value = "AI配置信息导入")
    @PostMapping("/importAiInfo")
    public BaseResponse importAiInfo(@RequestParam MultipartFile request) {
        log.info("importAiInfo");
        return AIInfoService.importAiInfo(request);
    }

    @ApiOperation(value = "AI配置信息导出")
    @PostMapping("/exportAiInfo")
    public BaseResponse exportAiInfo(@RequestBody List<String> request) {
        log.info("exportAiInfo");
        return AIInfoService.exportAiInfo(request);
    }


}
