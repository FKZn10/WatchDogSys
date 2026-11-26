package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDutyDTO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostDutyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任岗位职责明细接口。
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilityPostDuty")
public class SafeResponsibilityPostDutyController {

    @Resource
    private ISafeResponsibilityPostDutyService postDutyService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改岗位职责。
     */
    @PostMapping("/createOrUpdateSafeResponsibilityPostDuty")
    public BaseResponse createOrUpdateSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                                 @RequestHeader String token) {
        log.info("createOrUpdateSafeResponsibilityPostDuty");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return postDutyService.createOrUpdateSafeResponsibilityPostDuty(request, user.getId(), user.getName());
    }

    /**
     * 多条件筛选岗位职责列表。
     */
    @PostMapping("/getSafeResponsibilityPostDutyList")
    public BaseResponse getSafeResponsibilityPostDutyList(@RequestBody SafeResponsibilityPostDutyDTO request) {
        log.info("getSafeResponsibilityPostDutyList");
        return postDutyService.getSafeResponsibilityPostDutyList(request);
    }

    /**
     * 删除岗位职责。
     */
    @PostMapping("/deleteSafeResponsibilityPostDuty")
    public BaseResponse deleteSafeResponsibilityPostDuty(@RequestBody SafeResponsibilityPostDutyDTO request,
                                                         @RequestHeader String token) {
        log.info("deleteSafeResponsibilityPostDuty");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return postDutyService.deleteSafeResponsibilityPostDuty(request, user.getId(), user.getName());
    }
}
