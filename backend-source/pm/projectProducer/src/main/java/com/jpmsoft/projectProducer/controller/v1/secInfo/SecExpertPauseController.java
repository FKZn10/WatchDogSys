package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertPauseDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertPauseService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:25
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/pause")
public class SecExpertPauseController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISecExpertPauseService secExpertPauseService;


    @TenantId
    @PostMapping("/createOrUpdateSecExpertPause")
    public BaseResponse createOrUpdateSecExpertPause(@RequestBody SecExpertPauseDTO request) {
        log.info("createOrUpdateSecExpertPause");
        return secExpertPauseService.createOrUpdateSecExpertPause(request);
    }

    @TenantId
    @PostMapping("/getSecExpertPauseList")
    public BaseResponse getSecExpertPauseList(@RequestBody SecExpertPauseDTO request, @RequestHeader String token) {
        log.info("getSecExpertPauseList");
        return secExpertPauseService.getSecExpertPauseList(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSecExpertPauseListClass")
    public BaseResponse getSecExpertPauseListClass(@RequestBody SecExpertPauseDTO request, @RequestHeader String token) {
        log.info("getSecExpertPauseListClass");
        return secExpertPauseService.getSecExpertPauseListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertPauseById")
    public BaseResponse getSecExpertPauseById(@RequestParam String id) {
        log.info("getSecExpertPauseById");
        return secExpertPauseService.getSecExpertPauseById(id);
    }

    @GetMapping("/approveSecExpertPauseById")
    public BaseResponse approveSecExpertPauseById(@RequestParam String id) {
        log.info("approveSecExpertPauseById");
        return secExpertPauseService.approveSecExpertPauseById(id);
    }

    /**
     * 定时任务审批通过暂停专家
     *
     * @return
     */
    @GetMapping("/approveSecExpertPause")
    public BaseResponse approveSecExpertPause() {
        log.info("approveSecExpertPause");
        return secExpertPauseService.approveSecExpertPause();
    }
}
