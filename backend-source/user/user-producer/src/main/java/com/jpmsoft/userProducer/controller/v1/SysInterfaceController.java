package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterface;
import com.jpmsoft.userProducer.service.ISysInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 11:29
 */
@Slf4j
@Api(tags = "接口管理", value = "接口管理")
@RestController
@RequestMapping("/system/v1/interface")
public class SysInterfaceController {

    @Resource
    private ISysInterfaceService sysInterfaceService;

    @ApiOperation(value = "新增修改接口管理")
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody SysInterfaceDTO request, @RequestHeader String token) {
        log.info("createOrUpdate");
        return sysInterfaceService.createOrUpdate(request, token);
    }

    @ApiOperation(value = "分页查询接口管理")
    @PostMapping("/getSysInterfaceList")
    public BaseResponse getSysInterfaceList(@RequestBody SysInterfaceDTO request) {
        log.info("getSysInterfaceList");
        return sysInterfaceService.getSysInterfaceList(request);
    }

    @ApiOperation(value = "ID查询")
    @GetMapping("/getSysInterfaceById")
    public BaseResponse getSysInterfaceById(@RequestParam String id) {
        log.info("getSysInterfaceById");
        return sysInterfaceService.getSysInterfaceById(id);
    }

    @ApiOperation(value = "ID删除接口管理")
    @GetMapping("/delSysInterfaceById")
    public BaseResponse delSysInterfaceById(@RequestParam String id) {
        log.info("delSysInterfaceById");
        return sysInterfaceService.delSysInterfaceById(id);
    }


    @ApiOperation(value = "刷新缓存")
    @GetMapping("/refreshRedis")
    public BaseResponse refreshRedis() {
        log.info("refreshRedis");
        return BaseResponse.ok(sysInterfaceService.refreshRedis());
    }

    @ApiOperation(value = "获取接口管理")
    @GetMapping("/getAll")
    public List<SysInterface> getAll(){
       return sysInterfaceService.getAll();
    }
}
