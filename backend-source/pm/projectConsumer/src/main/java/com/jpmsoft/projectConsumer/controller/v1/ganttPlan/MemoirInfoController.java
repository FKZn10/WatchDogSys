package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.MemoirInfoDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 15:46
 */
@Slf4j
@RestController
@Api(tags = "大事记", value = "大事记")
@RequestMapping("/v1/memoir")
public class MemoirInfoController {

    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增修改大事记")
    @PostMapping("/createOrUpdateMemoirInfo")
    public BaseResponse createOrUpdateMemoirInfo(@RequestBody MemoirInfoDTO request) {
        log.info("createOrUpdateMemoirInfo");
        return baseFeign.createOrUpdateMemoirInfo(request);
    }

    @ApiOperation(value = "分页查询大事记（带点赞和评论信息）")
    @PostMapping("/getMemoirInfoList")
    public BaseResponse getMemoirInfoList(@RequestBody MemoirInfoDTO request, @RequestHeader String token) {
        log.info("getMemoirInfoList");
        return baseFeign.getMemoirInfoList(request, token);
    }

    @ApiOperation(value = "审批通过完成进度计划任务")
    @GetMapping("/approve2ComplateGanntTask")
    public BaseResponse approve2ComplateGanntTask(@RequestParam String id) {
        log.info("approve2ComplateGanntTask");
        return baseFeign.approve2ComplateGanntTask(id);
    }

    @ApiOperation(value = "ID查询")
    @GetMapping("/getMemoirInfoById")
    public BaseResponse getMemoirInfoById(@RequestParam String id) {
        log.info("getMemoirInfoById");
        return baseFeign.getMemoirInfoById(id);
    }

    @ApiOperation(value = "新增评论")
    @PostMapping("/memoirComment")
    public BaseResponse memoirComment(@RequestBody MemoirCommentDTO data, @RequestHeader String token) {
        log.info("memoirComment");
        return baseFeign.memoirComment(data, token);
    }

    @ApiOperation(value = "删除评论")
    @GetMapping("/deleteMemoirComment")
    public BaseResponse deleteMemoirComment(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteMemoirComment");
        return baseFeign.deleteMemoirComment(id, token);
    }

    @ApiOperation(value = "点赞")
    @GetMapping("/memoirPraise")
    public BaseResponse memoirPraise(@RequestParam String id, @RequestHeader String token) {
        log.info("memoirPraise");
        return baseFeign.memoirPraise(id, token);
    }

    @ApiOperation(value = "删除点赞")
    @GetMapping("/deleteMemoirPraise")
    public BaseResponse deleteMemoirPraise(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteMemoirPraise");
        return baseFeign.deleteMemoirPraise(id, token);
    }
}
