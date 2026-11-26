package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDTO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任岗位接口。
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilityPost")
public class SafeResponsibilityPostController {

    @Resource
    private ISafeResponsibilityPostService postService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改岗位及职责。
     */
    @PostMapping("/createOrUpdateSafeResponsibilityPost")
    public BaseResponse createOrUpdateSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                                             @RequestHeader String token) {
        log.info("createOrUpdateSafeResponsibilityPost");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return postService.createOrUpdateSafeResponsibilityPost(request, user.getId(), user.getName());
    }

    /**
     * 多条件筛选岗位列表。
     */
    @PostMapping("/getSafeResponsibilityPostList")
    public BaseResponse getSafeResponsibilityPostList(@RequestBody SafeResponsibilityPostDTO request) {
        log.info("getSafeResponsibilityPostList");
        return postService.getSafeResponsibilityPostList(request);
    }

    /**
     * 删除岗位及其全部职责。
     */
    @PostMapping("/deleteSafeResponsibilityPost")
    public BaseResponse deleteSafeResponsibilityPost(@RequestBody SafeResponsibilityPostDTO request,
                                                     @RequestHeader String token) {
        log.info("deleteSafeResponsibilityPost");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return postService.deleteSafeResponsibilityPost(request, user.getId(), user.getName());
    }
}
