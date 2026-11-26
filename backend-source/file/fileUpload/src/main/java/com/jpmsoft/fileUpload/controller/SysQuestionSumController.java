package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.fileUpload.service.ISysQuestionSumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 13:46
 */
@Slf4j
@RestController
@Api(tags = "智能问答-用户问题记录", value = "智能问答-用户问题记录")
@RequestMapping("/questionSum")
public class SysQuestionSumController {

    @Resource
    private ISysQuestionSumService sysQuestionSumService;


    @ApiOperation(value = "分页查询用户问题记录")
    @PostMapping("/getSysQuestionSumList")
    public BaseResponse getSysQuestionSumList(@RequestBody BaseModeRequest request) {
        return sysQuestionSumService.getSysQuestionSumList(request);
    }
}
