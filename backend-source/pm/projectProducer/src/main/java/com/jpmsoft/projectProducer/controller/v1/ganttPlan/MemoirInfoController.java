package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirInfoDTO;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirCommentService;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirInfoService;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirPraiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 15:46
 */
@Slf4j
@RestController
@RequestMapping("/v1/memoir")
public class MemoirInfoController {

    @Resource
    private IMemoirInfoService memoirInfoService;

    @Resource
    private IMemoirCommentService memoirCommentService;
    @Resource
    private IMemoirPraiseService memoirPraiseService;

    @PostMapping("/createOrUpdateMemoirInfo")
    public BaseResponse createOrUpdateMemoirInfo(@RequestBody MemoirInfoDTO request) {
        log.info("createOrUpdateMemoirInfo");
        return memoirInfoService.createOrUpdateMemoirInfo(request);
    }


    @PostMapping("/getMemoirInfoList")
    public BaseResponse getMemoirInfoList(@RequestBody MemoirInfoDTO request, @RequestHeader String token) {
        log.info("getMemoirInfoList");
        return memoirInfoService.getMemoirInfoList(request, token);
    }

    @GetMapping("/approve2ComplateGanntTask")
    public BaseResponse approve2ComplateGanntTask(@RequestParam String id) {
        log.info("approve2ComplateGanntTask");
        return memoirInfoService.approve2ComplateGanntTask(id);
    }

    @GetMapping("/getMemoirInfoById")
    public BaseResponse getMemoirInfoById(@RequestParam String id) {
        log.info("getMemoirInfoById");
        return memoirInfoService.getMemoirInfoById(id);
    }

    @PostMapping("/memoirComment")
    public BaseResponse memoirComment(@RequestBody MemoirCommentDTO data, @RequestHeader String token) {
        log.info("memoirComment");
        return memoirCommentService.memoirComment(data, token);
    }

    @GetMapping("/deleteMemoirComment")
    public BaseResponse deleteMemoirComment(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteMemoirComment");
        return memoirCommentService.deleteMemoirComment(id, token);
    }

    @GetMapping("/memoirPraise")
    public BaseResponse memoirPraise(@RequestParam String id, @RequestHeader String token) {
        log.info("memoirPraise");
        return memoirPraiseService.memoirPraise(id, token);
    }

    @GetMapping("/deleteMemoirPraise")
    public BaseResponse deleteMemoirPraise(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteMemoirPraise");
        return memoirPraiseService.deleteMemoirPraise(id, token);
    }
}
