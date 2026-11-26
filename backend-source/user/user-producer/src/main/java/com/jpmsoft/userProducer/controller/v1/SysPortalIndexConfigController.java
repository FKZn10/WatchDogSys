package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexConfigDto;
import com.jpmsoft.userProducer.service.ISysPortalIndexConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/sysPortalIndexConfig")
public class SysPortalIndexConfigController{


    private final ISysPortalIndexConfigService sysPortalIndexConfigService;

    /**
     * 创建门户设置
     */
    @PostMapping("/createOrUpdateSysPortalIndexConfig")
    public BaseResponse createOrUpdateSysPortalIndexConfig(@RequestBody SysPortalIndexConfigDto request,String token) {
        log.info("createOrUpdateSysPortalIndexConfig");
        return sysPortalIndexConfigService.createOrUpdateSysPortalIndexConfig(request,token);
    }

    /**
     * 更新门户状态
     */
    @PostMapping("/updatePortalStateById")
    public BaseResponse updatePortalStateById(@RequestBody SysPortalIndexConfigDto request,@RequestHeader String token) {

        log.info("updatePortalStateById");
        return sysPortalIndexConfigService.updatePortalStateById(request,token);
    }

    @GetMapping("/getSysPortalIndexConfigById")
    public BaseResponse getSysPortalIndexConfigById(@RequestParam("id") String id){
        log.info("getSysPortalIndexConfigById");
        return sysPortalIndexConfigService.getSysPortalIndexConfigById(id);
    }

    /**
     * 列表查询
     */
    @PostMapping("/getSysPortalIndexConfigList")
    public BaseResponse getSysPortalIndexConfigList(@RequestBody SysPortalIndexConfigDto request){
        log.info("getSysPortalIndexConfigList");
        return sysPortalIndexConfigService.getSysPortalIndexConfigList(request);
    }

    /**
     * 删除门户设置
     */
    @GetMapping("/deleteSysPortalIndexConfigById")
    public BaseResponse deleteSysPortalIndexConfigById(@RequestParam String id){
        log.info("deleteSysPortalIndexConfigById");
        return sysPortalIndexConfigService.deleteSysPortalIndexConfigById(id);
    }

    /**
     * 根据用户id
     * 获取用户角色，根据角色匹配子表，查到主表返回
     */
    @GetMapping("/queryComponentListByUserInfo")
    public BaseResponse queryComponentListByUserInfo(@RequestHeader String token){
        log.info("queryComponentListByUserId");
        return sysPortalIndexConfigService.queryComponentListByUserInfo(token);
    }



}
