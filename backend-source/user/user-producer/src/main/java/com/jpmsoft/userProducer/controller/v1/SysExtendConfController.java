package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.service.ISysExtendConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:39
 */
@Slf4j
@Api(tags = "文档模板-扩展配置", value = "文档模板-扩展配置")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysExtendConfController {

    @Resource
    private ISysExtendConfService sysExtendConfService;


    @ApiOperation(value = "文档模板ID查询扩展配置")
    @GetMapping("/getSysExtendConfByTid")
    public BaseResponse getSysExtendConfByTid(@RequestParam String id) {
        log.info("getSysExtendConfByTid");
        return sysExtendConfService.getSysExtendConfByTid(id);
    }


    @ApiOperation(value = "文档模板code查询扩展配置")
    @GetMapping("/getSysExtendConfByTCode")
    public BaseResponse getSysExtendConfByTCode(@RequestParam String code,@RequestHeader(required = false) String token) {
        log.info("getSysExtendConfByTCode");
        return sysExtendConfService.getSysExtendConfByTCode(code,token);
    }


    @ApiOperation(value = "多个文档模板code查询扩展配置")
    @PostMapping("/getSysExtendConfByTCodeBath")
    public BaseResponse getSysExtendConfByTCodeBath(@RequestBody List<String> codes) {
        log.info("getSysExtendConfByTCodeBath");
        return sysExtendConfService.getSysExtendConfByTCodes(codes);
    }
}
