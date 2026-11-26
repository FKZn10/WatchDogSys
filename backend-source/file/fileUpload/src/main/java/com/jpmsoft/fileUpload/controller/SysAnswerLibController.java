package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.service.ISysAnswerLibService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 11:04
 */
@Slf4j
@RestController
@Api(tags = "智能问答-问答知识库", value = "智能问答-问答知识库")
@RequestMapping("/answer")
public class SysAnswerLibController {
    @Resource
    private ISysAnswerLibService iSysAnswerLibService;

    @ApiOperation(value = "创建修改问答知识库")
    @PostMapping("/createOrUpdateSysAnswerLib")
    public BaseResponse createOrUpdateSysAnswerLib(@RequestBody SysAnswerLibDTO request,@RequestHeader String token) {
        return iSysAnswerLibService.createOrUpdateSysAnswerLib(request,token);
    }

    @ApiOperation(value = "分页查询问答知识库")
    @PostMapping("/getSysAnswerLibList")
    public BaseResponse getSysAnswerLibList(@RequestBody SysAnswerLibDTO request) {
        return iSysAnswerLibService.getSysAnswerLibList(request);
    }

    @ApiOperation(value = "ID查询问答知识库详情")
    @GetMapping("/getSysAnswerLibById")
    public BaseResponse getSysAnswerLibById(@RequestParam String id) {
        return iSysAnswerLibService.getSysAnswerLibById(id);
    }

    /**
     * 检索问题
     *
     * @param requset
     * @return
     */
    @ApiOperation(value = "检索问题")
    @PostMapping("/searchProblem")
    public BaseResponse searchProblem(@RequestParam SysAnswerLibDTO requset) {
        return iSysAnswerLibService.searchProblem(requset);
    }
}
