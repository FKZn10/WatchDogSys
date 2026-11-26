package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.dto.AiConversationLogDTO;
import com.jpmsoft.userProducer.pojo.entity.AiConversationLog;
import com.jpmsoft.userProducer.service.IAiConversationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "AI问答日志", value = "AI问答日志")
@RequestMapping("/v1/aiConversationLog")
public class AiConversationLogController {

    @Resource
    private IAiConversationLogService AiConversationLogService;

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "/getById")
    public BaseResponse getById(@RequestParam String id) {
        return BaseResponse.ok(AiConversationLogService.getById(id));
    }

    @ApiOperation(value = "ID删除")
    @GetMapping(value = "/removeById")
    public BaseResponse removeById(@RequestParam String id) {
        AiConversationLogService.removeById(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/removeBatchByIds")
    public BaseResponse removeBatchByIds(@RequestBody List<String> ids) {
        AiConversationLogService.removeBatchByIds(ids);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AiConversationLog code) {
        if (ValidateUtil.isEmpty(code.getId())) code.setId(GenerateUUID.getUUID());
        AiConversationLogService.saveOrUpdate(code);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getAiConversationLogPage")
    public Object getAiConversationLogPage(@RequestBody AiConversationLogDTO code) {
        LambdaQueryWrapper<AiConversationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(code.getAiType()), AiConversationLog::getAiType, code.getAiType());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getCreateUid()), AiConversationLog::getCreateUid, code.getCreateUid());
        wrapper.like(ValidateUtil.isNotEmpty(code.getClPrompt()), AiConversationLog::getClPrompt, code.getClPrompt());
        wrapper.like(ValidateUtil.isNotEmpty(code.getCreateUname()), AiConversationLog::getCreateUname, code.getCreateUname());
        IPage<AiConversationLog> iPage = AiConversationLogService.page(new Page<>(code.getPage(), code.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }
}
