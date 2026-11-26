package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityCopyDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilitySystemDTO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilitySystemService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任体系接口。
 */
@Slf4j
@RestController
@RequestMapping("/v1/safeResponsibilitySystem")
public class SafeResponsibilitySystemController {
    @Resource
    private ISafeResponsibilitySystemService systemService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改安全责任体系。
     */
    @PostMapping("/createOrUpdateSafeResponsibilitySystem")
    public BaseResponse createOrUpdateSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                               @RequestHeader String token) {
        log.info("createOrUpdateSafeResponsibilitySystem");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return systemService.createOrUpdateSafeResponsibilitySystem(request, user.getId(), user.getName());
    }

    /**
     * 多条件筛选安全责任体系列表。
     */
    @PostMapping("/getSafeResponsibilitySystemList")
    public BaseResponse getSafeResponsibilitySystemList(@RequestBody SafeResponsibilitySystemDTO request) {
        log.info("getSafeResponsibilitySystemList");
        return systemService.getSafeResponsibilitySystemList(request);
    }

    /**
     * 多条件筛选安全责任体系树。
     */
    @PostMapping("/getSafeResponsibilitySystemTree")
    public BaseResponse getSafeResponsibilitySystemTree(@RequestBody SafeResponsibilitySystemDTO request) {
        log.info("getSafeResponsibilitySystemTree");
        return systemService.getSafeResponsibilitySystemTree(request);
    }

    /**
     * 删除安全责任体系及全部下级业务数据。
     */
    @PostMapping("/deleteSafeResponsibilitySystem")
    public BaseResponse deleteSafeResponsibilitySystem(@RequestBody SafeResponsibilitySystemDTO request,
                                                       @RequestHeader String token) {
        log.info("deleteSafeResponsibilitySystem");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return systemService.deleteSafeResponsibilitySystem(request, user.getId(), user.getName());
    }

    /**
     * 复制安全责任体系模板。
     */
    @PostMapping("/copySafeResponsibilitySystem")
    public BaseResponse copySafeResponsibilitySystem(@RequestBody SafeResponsibilityCopyDTO request,
                                                     @RequestHeader String token) {
        log.info("copySafeResponsibilitySystem");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return systemService.copySafeResponsibilitySystem(request, user.getId(), user.getName());
    }
}
