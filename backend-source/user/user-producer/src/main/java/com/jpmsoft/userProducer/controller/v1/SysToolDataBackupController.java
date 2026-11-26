package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysToolDataBackupDto;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupQdService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "数据管理工具——数据删除、备份", value = "数据管理工具——数据删除、备份")
@RequestMapping("/v1/SysTool")
public class SysToolDataBackupController {

    @Resource
    ISysToolDataBackupService sysToolDataBackupService;

    @Resource
    ISysToolDataBackupQdService sysToolDataBackupQdService;
    @Resource
    private RedisUtils redisUtils;

    @Value("${system.deleteApiUser}")
    private  String deleteApiUser;
    @ApiOperation(value = "获取数据备份")
    @PostMapping("/GetSysToolDataBackupList")
    public BaseResponse GetSysToolDataBackupList(@RequestBody SysToolDataBackupDto request) {
        log.info("GetSysToolDataBackupList");
        return sysToolDataBackupService.GetSysToolDataBackupList(request);
    }


    @ApiOperation(value = "数据备份")
    @PostMapping("/DataBackup")
    public BaseResponse DataBackup(@RequestBody SysToolDataBackupDto request, @RequestHeader(required = false) String token) {
        log.info("DataBackup");
        LogUserInfoVo  userInfo=redisUtils.getUserInfoByToken(token);
        if(!userInfo.getCode().equals(deleteApiUser)){
            return  BaseResponse.fail("当前用户没有备份权限");
        }
        return sysToolDataBackupService.DataBackup(request);
    }
    @ApiOperation(value = "数据删除")
    @PostMapping("/DataDeletBatch")
    public BaseResponse DataDeletBatch(@RequestBody SysToolDataBackupDto request, @RequestHeader(required = false) String token) {
        log.info("DataDeletBatch");
        LogUserInfoVo  userInfo=redisUtils.getUserInfoByToken(token);
        if(!userInfo.getCode().equals(deleteApiUser)){
            return  BaseResponse.fail("当前用户没有删除权限");
        }
        request.setStdDeleteUid(userInfo.getId());
        request.setStdDeleteUname(userInfo.getName());
        return sysToolDataBackupService.DataDeletBatch(request);
    }

    @ApiOperation(value = "获取备份记录信息")
    @GetMapping("/GetSysToolDataBackupQdList")
    public BaseResponse GetSysToolDataBackupQdList(@RequestParam String sysId) {
        log.info("GetSysToolDataBackupQdList");
        return sysToolDataBackupQdService.GetSysToolDataBackupQdList(sysId);
    }

    @ApiOperation(value = "获取备份信息")
    @GetMapping("/GetSysToolDataBackupData")
    public BaseResponse GetSysToolDataBackupData(@RequestParam String id) {
        log.info("GetSysToolDataBackupData");
        return sysToolDataBackupQdService.GetSysToolDataBackupData(id);
    }


    @ApiOperation(value = "备份恢复")
    @GetMapping("/fullRestoration")
    public BaseResponse fullRestoration(@RequestParam String id, @RequestHeader(required = false) String token) {
        log.info("fullRestoration");
        LogUserInfoVo  userInfo=redisUtils.getUserInfoByToken(token);
        if(!userInfo.getCode().equals(deleteApiUser)){
            return  BaseResponse.fail("当前用户没有备份批量恢复权限");
        }
        return sysToolDataBackupQdService.fullRestoration(id,token);
    }


    @ApiOperation(value = "获取备份信息")
    @GetMapping("/restorationById")
    public BaseResponse restorationById(@RequestParam String id, @RequestHeader(required = false) String token) {
        log.info("restorationById");
        LogUserInfoVo  userInfo=redisUtils.getUserInfoByToken(token);
        if(!userInfo.getCode().equals(deleteApiUser)){
            return  BaseResponse.fail("当前用户没有备份恢复权限");
        }
        return sysToolDataBackupQdService.restorationById(id,token);
    }


}
