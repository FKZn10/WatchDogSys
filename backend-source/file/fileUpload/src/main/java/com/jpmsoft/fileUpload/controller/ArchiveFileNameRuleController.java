package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameDTO;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameRuleDTO;
import com.jpmsoft.fileUpload.service.IArchiveFileNameRuleService;
import com.jpmsoft.fileUpload.service.IArchiveFileNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:32
 */
@Slf4j
@RestController
@Api(tags = "归档文件名称配置", value = "归档文件名称配置")
@RequestMapping("/archive")
public class ArchiveFileNameRuleController {

    @Resource
    private IArchiveFileNameRuleService archiveFileNameRuleService;
    @Resource
    private IArchiveFileNameService archiveFileNameService;


    @ApiOperation(value = "创建修改归档文件名称配置")
    @PostMapping("/createOrUpdateArchiveFileName")
    public BaseResponse createOrUpdateArchiveFileName(@RequestBody ArchiveFileNameDTO request, @RequestHeader String token) {
        return archiveFileNameService.createOrUpdate(request, token);
    }

    @ApiOperation(value = "分页查询归档文件名称配置")
    @PostMapping("/getArchiveFileNameList")
    public BaseResponse getArchiveFileNameList(@RequestBody ArchiveFileNameDTO request) {
        return archiveFileNameService.getArchiveFileNameList(request);
    }

    @ApiOperation(value = "ID删除归档文件名称配置")
    @GetMapping("/deleteArchiveFileNameById")
    public BaseResponse deleteArchiveFileNameById(@RequestParam String id) {
        return archiveFileNameService.deleteById(id);
    }


    @ApiOperation(value = "创建修改归档文件名称规则")
    @PostMapping("/createOrUpdateArchiveFileRuleName")
    public BaseResponse createOrUpdateArchiveFileRuleName(@RequestBody ArchiveFileNameRuleDTO request) {
        return archiveFileNameRuleService.createOrUpdate(request);
    }

    @ApiOperation(value = "分页查询归档文件名称规则")
    @PostMapping("/getArchiveFileNameRuleList")
    public BaseResponse getArchiveFileNameRuleList(@RequestBody ArchiveFileNameRuleDTO request) {
        return archiveFileNameRuleService.getArchiveFileNameRuleList(request);
    }

    @ApiOperation(value = "ID删除归档文件名称规则")
    @GetMapping("/deleteArchiveFileNameRuleById")
    public BaseResponse deleteArchiveFileNameRuleById(@RequestParam String id) {
        return archiveFileNameRuleService.deleteById(id);
    }

}
