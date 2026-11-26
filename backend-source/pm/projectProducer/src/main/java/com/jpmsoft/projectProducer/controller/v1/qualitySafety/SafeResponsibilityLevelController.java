package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityLevelDTO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityLevelService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任层级接口。
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilityLevel")
public class SafeResponsibilityLevelController {
    @Resource
    private ISafeResponsibilityLevelService levelService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改安全责任层级。
     */
    @PostMapping("/createOrUpdateSafeResponsibilityLevel")
    public BaseResponse createOrUpdateSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                              @RequestHeader String token) {
        log.info("createOrUpdateSafeResponsibilityLevel");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return levelService.createOrUpdateSafeResponsibilityLevel(request, user.getId(), user.getName());
    }

    /**
     * 多条件筛选层级树，结果保留命中节点的全部祖先。
     */
    @PostMapping("/getSafeResponsibilityLevelTree")
    public BaseResponse getSafeResponsibilityLevelTree(@RequestBody SafeResponsibilityLevelDTO request) {
        log.info("getSafeResponsibilityLevelTree");
        return levelService.getSafeResponsibilityLevelTree(request);
    }

    /**
     * 删除空安全责任层级。
     */
    @PostMapping("/deleteSafeResponsibilityLevel")
    public BaseResponse deleteSafeResponsibilityLevel(@RequestBody SafeResponsibilityLevelDTO request,
                                                      @RequestHeader String token) {
        log.info("deleteSafeResponsibilityLevel");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return levelService.deleteSafeResponsibilityLevel(request, user.getId(), user.getName());
    }
}
